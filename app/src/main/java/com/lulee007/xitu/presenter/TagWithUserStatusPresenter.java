package com.lulee007.xitu.presenter;

import com.google.gson.Gson;
import com.lulee007.xitu.base.XTBasePresenter;
import com.lulee007.xitu.models.Subscribe;
import com.lulee007.xitu.models.Tag;
import com.lulee007.xitu.services.CommonSaveService;
import com.lulee007.xitu.services.SubscribeService;
import com.lulee007.xitu.services.TagService;
import com.lulee007.xitu.util.AuthUserHelper;
import com.lulee007.xitu.view.ITagWithUserStatsView;
import com.orhanobut.logger.Logger;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.functions.Func2;

/**
 * User: lulee007@live.com
 * Date: 2015-12-18
 * Time: 17:54
 */
public class TagWithUserStatusPresenter extends XTBasePresenter<ITagWithUserStatsView> {

    private TagService tagService;
    private SubscribeService subscribeService;


    public TagWithUserStatusPresenter(ITagWithUserStatsView view) {
        super(view);
        tagService = new TagService();
        subscribeService = new SubscribeService();

    }

    @Override
    protected HashMap<String, String> buildRequestParams(String where, int skip) {
        HashMap<String, String> hotTagParams = new HashMap<String, String>();
        hotTagParams.put("order", "-entriesCount");
        hotTagParams.put("limit", pageOffset + "");
        hotTagParams.put("where", where);
        if (skip > 0) {
            hotTagParams.put("skip", skip + "");
        }
        return hotTagParams;
    }

    //    @NonNull
    @Override
    protected HashMap<String, String> buildRequestParams(String where) {
        return buildRequestParams(where, 0);
    }


    private HashMap<String, String> buildSubscribeParams(String where) {
        HashMap<String, String> hotTagParams = new HashMap<String, String>();

        try {
            hotTagParams.put("where",
                    String.format("{\"tag\":{\"$in\":%s},\"user\":{\"__type\":\"Pointer\",\"className\":\"_User\",\"objectId\":\"%s\"}}",
                            where
                            , AuthUserHelper.getInstance().getUser().getString("objectId")));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return hotTagParams;
    }

    @Override
    public void loadNew() {
        final List<Tag> allTagList = new ArrayList<>();
        final List<HashMap<String, String>> allTagJsonList = new ArrayList<>();
        final List<String> singleSubcribeId = new ArrayList<>(1);
        Subscription subscription =
                Observable
                        .zip(tagService.getTags(buildRequestParams("{\"hot\":true}")),
                                tagService.getTags(buildRequestParams("{\"hot\":{\"$ne\":true}}")),
                                new Func2<List<Tag>, List<Tag>, List<Tag>>() {
                                    @Override
                                    public List<Tag> call(List<Tag> tags, List<Tag> tags2) {
                                        List<Tag> all = new ArrayList<>();
                                        all.addAll(tags);
                                        all.addAll(tags2);
                                        return all;
                                    }
                                }
                        )
                                // 先保存所有的tags
                        .doOnNext(new Action1<List<Tag>>() {
                            @Override
                            public void call(List<Tag> tags) {
//                              Logger.d("on save all tags");
                                allTagList.clear();
                                allTagList.addAll(tags);
                                allTagJsonList.clear();
                                for (Tag tag : allTagList) {
                                    HashMap<String, String> json = new HashMap<String, String>();

                                    json.put("__type", "Pointer");
                                    json.put("className", "Tag");
                                    json.put("objectId", tag.getObjectId());
                                    allTagJsonList.add(json);
                                }
                            }
                        })
                                //查询当前tags中是已经订阅的 Subscribe 的tagId
                        .flatMap(new Func1<List<Tag>, Observable<String>>() {
                            @Override
                            public Observable<String> call(List<Tag> tags) {
                                return subscribeService.getSubscribes(buildSubscribeParams(new Gson().toJson(allTagJsonList)))
                                        .flatMap(new Func1<List<Subscribe>, Observable<Subscribe>>() {
                                            @Override
                                            public Observable<Subscribe> call(List<Subscribe> subscribes) {
                                                return Observable.from(subscribes);
                                            }
                                        })
                                        .map(new Func1<Subscribe, String>() {
                                            @Override
                                            public String call(Subscribe subscribe) {
                                                singleSubcribeId.clear();
                                                singleSubcribeId.add(subscribe.getObjectId());
                                                return subscribe.getTag().getObjectId();
                                            }
                                        });
                            }
                        })
                                // 过滤 已订阅的，并且设置为已订阅
                        .flatMap(new Func1<String, Observable<Tag>>() {
                            @Override
                            public Observable<Tag> call(final String s) {
                                return Observable.from(allTagList)
                                        .filter(new Func1<Tag, Boolean>() {
                                            @Override
                                            public Boolean call(Tag tag) {
                                                return tag.getObjectId().equals(s);
                                            }
                                        }).map(new Func1<Tag, Tag>() {
                                            @Override
                                            public Tag call(Tag tag) {
                                                tag.setIsSubscribed(true);
                                                tag.setSubscribedId(singleSubcribeId.get(0));
                                                return tag;
                                            }
                                        });
                            }
                        })
                        .observeOn(AndroidSchedulers.mainThread())

                        .doOnCompleted(new Action0() {
                            @Override
                            public void call() {
                                if (allTagList.size() == 0) {
                                    mView.noData();
                                }
                                mView.addNew(allTagList);
                                if (allTagList.size() < pageOffset) {
                                    mView.noMore();
                                }
                            }
                        })
                        .subscribe(new Action1<Tag>() {
                                       @Override
                                       public void call(Tag tag) {

                                       }
                                   },
                                new Action1<Throwable>() {
                                    @Override
                                    public void call(Throwable throwable) {
                                        Logger.e("load new :" + throwable.getMessage(), throwable);
                                        mView.addNewError();
                                    }
                                }

                        );
        addSubscription(subscription);
    }

    @Override
    public void refresh() {

    }

    @Override
    public void loadMore() {
        final List<Tag> allTagList = new ArrayList<>();
        final List<HashMap<String, String>> allTagJsonList = new ArrayList<>();
        Subscription subscription = tagService.getTags(buildRequestParams("{\"hot\":{\"$ne\":true}}", pageIndex * pageOffset))
                .doOnNext(new Action1<List<Tag>>() {
                    @Override
                    public void call(List<Tag> tags) {
                        allTagList.addAll(tags);
                        allTagJsonList.clear();
                        for (Tag tag : allTagList) {
                            HashMap<String, String> json = new HashMap<String, String>();

                            json.put("__type", "Pointer");
                            json.put("className", "Tag");
                            json.put("objectId", tag.getObjectId());
                            allTagJsonList.add(json);
                        }
                    }
                })
                        //查询当前tags中是已经订阅的 Subscribe 的tagId
                .flatMap(new Func1<List<Tag>, Observable<String>>() {
                    @Override
                    public Observable<String> call(List<Tag> tags) {
                        return subscribeService.getSubscribes(buildSubscribeParams(new Gson().toJson(allTagJsonList)))
                                .flatMap(new Func1<List<Subscribe>, Observable<Subscribe>>() {
                                    @Override
                                    public Observable<Subscribe> call(List<Subscribe> subscribes) {
                                        return Observable.from(subscribes);
                                    }
                                })
                                .map(new Func1<Subscribe, String>() {
                                    @Override
                                    public String call(Subscribe subscribe) {
                                        return subscribe.getTag().getObjectId();
                                    }
                                });
                    }
                })
                        // 过滤 已订阅的，并且设置为已订阅
                .flatMap(new Func1<String, Observable<Tag>>() {
                    @Override
                    public Observable<Tag> call(final String s) {
                        return Observable.from(allTagList)
                                .filter(new Func1<Tag, Boolean>() {
                                    @Override
                                    public Boolean call(Tag tag) {
                                        return tag.getObjectId().equals(s);
                                    }
                                }).map(new Func1<Tag, Tag>() {
                                    @Override
                                    public Tag call(Tag tag) {
                                        tag.setIsSubscribed(true);
                                        return tag;
                                    }
                                });
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Logger.e("load new :", throwable);
                        mView.addMoreError();
                    }
                })
                .doOnCompleted(new Action0() {
                    @Override
                    public void call() {
                        onLoadMoreComplete(allTagList);
                    }
                }).subscribe();
        addSubscription(subscription);

    }

    public void unSubscribeTag(String subscribedId, final int position) {
        Subscription subscription = subscribeService.unSubscribe(subscribedId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean aBoolean) {
                        ((ITagWithUserStatsView) mView).onUnSubscribeTag(position);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        ((ITagWithUserStatsView) mView).onUnSubscribeTagError();
                    }
                });
        addSubscription(subscription);
    }

    public void subscribeTag(String cid, final int position) {
        Subscription subscription = new CommonSaveService().saveSubscription(cid)
                .throttleFirst(500,TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<JSONObject>() {
                    @Override
                    public void call(JSONObject o) {
                        Logger.json(o.toString());
                        try {
                            String objectId=o.getString("objectId");
                            ((ITagWithUserStatsView) mView).onSubscribeTag(objectId,position);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        ((ITagWithUserStatsView) mView).onSubscribeTagError();
                    }
                });
        addSubscription(subscription);
    }
}

