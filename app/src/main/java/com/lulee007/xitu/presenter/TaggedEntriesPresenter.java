package com.lulee007.xitu.presenter;

import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.lulee007.xitu.base.XTBasePresenter;
import com.lulee007.xitu.models.Collection;
import com.lulee007.xitu.models.Entry;
import com.lulee007.xitu.models.Subscribe;
import com.lulee007.xitu.models.Tag;
import com.lulee007.xitu.services.CollectionService;
import com.lulee007.xitu.services.CommonSaveService;
import com.lulee007.xitu.services.EntryService;
import com.lulee007.xitu.services.SubscribeService;
import com.lulee007.xitu.util.AuthUserHelper;
import com.lulee007.xitu.view.IEntriesView;
import com.orhanobut.logger.Logger;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * User: lulee007@live.com
 * Date: 2015-12-15
 * Time: 13:57
 */
public class TaggedEntriesPresenter extends XTBasePresenter<IEntriesView> {

    private EntryService entryService;
    private SubscribeService subscribeService;
    private String createdAt;

    public TaggedEntriesPresenter(IEntriesView view) {
        super(view);
        entryService = new EntryService();
        subscribeService = new SubscribeService();
        pageOffset = 30;
    }

    @Override
    protected HashMap<String, String> buildRequestParams(@NonNull String where, int skip) {
        HashMap<String, String> entryParams = new HashMap<>();
        entryParams.put("order", "-createdAt");
        entryParams.put("limit", pageOffset + "");
        entryParams.put("where", String.format("{\"tags\":{\"$in\":%s}}", where));
        entryParams.put("include", "user,user.installation");
        if (skip > 0) {
            entryParams.put("skip", skip + "");
        }
        return entryParams;
    }

    @NonNull
    @Override
    protected HashMap<String, String> buildRequestParams(String where) {
        return buildRequestParams(where, 0);
    }

    private HashMap<String, String> buildSubscribeListParams(HashMap userMap) {
        String userWhereString =new Gson().toJson(AuthUserHelper.getInstance().getUser());
        if (userMap != null) {
            userWhereString = new Gson().toJson(userMap);
        }
        HashMap<String, String> params = new HashMap<>();
        params.put("limit", "500");
        params.put("order", "-createAt");
        params.put("include", "tag");
        params.put("where", String.format("{\"user\":%s}", userWhereString));
        return params;
    }


    public void loadNew() {
        if(!AuthUserHelper.getInstance().isLoggedIn()){
            mView.noData();
            return;
        }
        Subscription subscription = getSubscribedTags()
                .flatMap(new Func1<List<HashMap>, Observable<List<Entry>>>() {
                    @Override
                    public Observable<List<Entry>> call(List<HashMap> hashMaps) {
                        String tagWhereString = new Gson().toJson(hashMaps);
                        return entryService.getEntryList(buildRequestParams(tagWhereString));
                    }
                })
                .flatMap(new Func1<List<Entry>, Observable<List<Entry>>>() {
                    @Override
                    public Observable<List<Entry>> call(final List<Entry> entries) {
                        return getEntriesWithCollections(entries);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<Entry>>() {
                    @Override
                    public void call(List<Entry> entries) {
                        if (entries.size() == 0) {
                            mView.noData();
                            return;
                        }
                        createdAt = entries.get(0).getCreatedAt();
                        mView.addNew(entries);
                        if (entries.size() < pageOffset) {
                            mView.noMore();
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mView.addNewError();
                        Logger.e(throwable, "loadNew error");
                    }
                });
        addSubscription(subscription);
    }

    private Observable<List<Entry>> getEntriesWithCollections(final List<Entry> entries) {
        if (entries == null || entries.size() == 0) {
            return Observable.just(entries);
        }
        return Observable.from(entries)
                .map(new Func1<Entry, HashMap<String, String>>() {
                    @Override
                    public HashMap<String, String> call(Entry entry) {
                        HashMap<String, String> map = new HashMap<String, String>();
                        map.put("__type", "Pointer");
                        map.put("className", "Entry");
                        map.put("objectId", entry.getObjectId());
                        return map;
                    }
                })
                .toList()
                .flatMap(new Func1<List<HashMap<String, String>>, Observable<List<Collection>>>() {
                    @Override
                    public Observable<List<Collection>> call(List<HashMap<String, String>> hashMaps) {
                        String where = String.format("{\"entry\":{\"$in\":%s},\"user\":%s}",
                                new Gson().toJson(hashMaps),
                                new Gson().toJson(AuthUserHelper.getInstance().getUser()));

                        HashMap<String, String> params = new HashMap<String, String>();
                        params.put("include", "Entry");
                        params.put("where", where);
                        return new CollectionService().getCollection(params);
                    }
                })
                .flatMap(new Func1<List<Collection>, Observable<Collection>>() {
                    @Override
                    public Observable<Collection> call(List<Collection> collections) {
                        return Observable.from(collections);
                    }
                })
                .flatMap(new Func1<Collection, Observable<List<Entry>>>() {
                    @Override
                    public Observable<List<Entry>> call(final Collection collection) {
                        return Observable.from(entries)
                                .filter(new Func1<Entry, Boolean>() {
                                    @Override
                                    public Boolean call(Entry entry) {
                                        return entry.getObjectId().equals(collection.getEntry().getObjectId());
                                    }
                                })
                                .map(new Func1<Entry, Entry>() {
                                    @Override
                                    public Entry call(Entry entry) {
                                        Logger.d(entry.getObjectId());
                                        entry.setCollection(collection);
                                        return entry;
                                    }
                                })
                                .toList()
                                .map(new Func1<List<Entry>, List<Entry>>() {
                                    @Override
                                    public List<Entry> call(List<Entry> entry) {
                                        return entries;
                                    }
                                });
                    }
                })
                .switchIfEmpty(Observable.just(entries))
                ;
    }

    @NonNull
    private Observable<List<HashMap>> getSubscribedTags() {
        return subscribeService.getSubscribes(buildSubscribeListParams(null))
                .flatMap(new Func1<List<Subscribe>, Observable<Subscribe>>() {
                    @Override
                    public Observable<Subscribe> call(List<Subscribe> subscribes) {
                        return Observable.from(subscribes);
                    }
                })
                .map(new Func1<Subscribe, HashMap>() {
                    @Override
                    public HashMap call(Subscribe subscribe) {
                        HashMap<String, String> tagMap = new HashMap<String, String>();
                        Tag tag = subscribe.getTag();
                        if (tag != null) {
                            tagMap.put("__type", "Pointer");
                            tagMap.put("objectId", tag.getObjectId());
                            tagMap.put("className", "Tag");
                            return tagMap;
                        } else {
                            return tagMap;
                        }
                    }
                })
                .toList();
    }

    @Override
    public void refresh() {
        Subscription su = getSubscribedTags()
                .flatMap(new Func1<List<HashMap>, Observable<List<Entry>>>() {
                    @Override
                    public Observable<List<Entry>> call(List<HashMap> hashMaps) {
                        HashMap<String, String> params = buildRequestParams("");
                        JSONObject json = new JSONObject();
                        try {
                            json.put("tags", new Gson().toJson(hashMaps));
                            json.put("createdAt", String.format("{\"$gt\":{\"__type\":\"Date\",\"iso\":\"%s\"}}", createdAt));
                            params.put("where", json.toString());

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        return entryService.getEntryList(params);
                    }
                })
                .flatMap(new Func1<List<Entry>, Observable<List<Entry>>>() {
                    @Override
                    public Observable<List<Entry>> call(List<Entry> entries) {
                        return getEntriesWithCollections(entries);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<Entry>>() {
                    @Override
                    public void call(List<Entry> entries) {
                        if (entries.size() == 0) {
                            mView.refreshNoContent();
                            return;
                        }
                        createdAt = entries.get(0).getCreatedAt();

                        mView.refresh(entries);

                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mView.refreshError();
                    }
                });
        addSubscription(su);
    }

    @Override
    public void loadMore() {
        Subscription subscription = getSubscribedTags()
                .flatMap(new Func1<List<HashMap>, Observable<List<Entry>>>() {
                    @Override
                    public Observable<List<Entry>> call(List<HashMap> hashMaps) {

                        return entryService.getEntryList(buildRequestParams(new Gson().toJson(hashMaps), pageOffset * pageIndex));
                    }
                })
                .flatMap(new Func1<List<Entry>, Observable<List<Entry>>>() {
                    @Override
                    public Observable<List<Entry>> call(List<Entry> entries) {
                        return getEntriesWithCollections(entries);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<Entry>>() {
                    @Override
                    public void call(List<Entry> entries) {
                        onLoadMoreComplete(entries);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                    }
                });
        addSubscription(subscription);
    }


    public void onCollectViewClick(final Entry entry, final int position) {
        Collection collection = entry.getCollection();
        Subscription subscription = null;
        if (collection != null) {
            subscription = new CollectionService().unSubscribeEntry(collection.getObjectId())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<Boolean>() {
                                   @Override
                                   public void call(Boolean aBoolean) {
                                       if (aBoolean == Boolean.TRUE) {
                                           entry.setCollectionCount(entry.getCollectionCount() - 1);
                                           entry.setCollection(null);
                                           mView.onUnCollect(position);
                                       }
                                   }
                               },
                            new Action1<Throwable>() {
                                @Override
                                public void call(Throwable throwable) {
                                    mView.onUnCollectError();
                                }
                            }
                    );
        } else {
            subscription = new CommonSaveService().saveCollectEntry(entry.getObjectId())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<String>() {
                                   @Override
                                   public void call(String s) {
                                       Collection newCollection = new Collection();
                                       newCollection.setObjectId(s);
                                       entry.setCollection(newCollection);
                                       entry.setCollectionCount(entry.getCollectionCount() + 1);
                                       mView.onCollected(position);
                                   }
                               },
                            new Action1<Throwable>() {
                                @Override
                                public void call(Throwable throwable) {
                                    mView.onCollectError();
                                }
                            }
                    );
        }
        addSubscription(subscription);
    }
}
