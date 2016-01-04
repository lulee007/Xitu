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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
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


        hotTagParams.put("where",
                String.format("{\"tag\":{\"$in\":%s},\"user\":{\"__type\":\"Pointer\",\"className\":\"_User\",\"objectId\":\"%s\"}}",
                        where
                        , AuthUserHelper.getInstance().getUser().get("objectId")));


        return hotTagParams;
    }

    @Override
    public void loadNew() {
        Subscription subscription = Observable
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
                .flatMap(new Func1<List<Tag>, Observable<List<Tag>>>() {
                    @Override
                    public Observable<List<Tag>> call(List<Tag> tags) {
                        return buildTagWithUserStatus(tags);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<Tag>>() {
                               @Override
                               public void call(List<Tag> tags) {
                                   if (tags.size() == 0) {
                                       mView.noData();
                                   }
                                   mView.addNew(tags);
                                   if (tags.size() < pageOffset) {
                                       mView.noMore();
                                   }
                               }
                           },
                        new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                mView.addNewError();
                            }
                        });
        addSubscription(subscription);
    }

    @Override
    public void refresh() {

    }

    @Override
    public void loadMore() {
        Subscription subscription = tagService.getTags(buildRequestParams("{\"hot\":{\"$ne\":true}}", pageIndex * pageOffset))
                .flatMap(new Func1<List<Tag>, Observable<List<Tag>>>() {
                    @Override
                    public Observable<List<Tag>> call(List<Tag> tags) {
                        return buildTagWithUserStatus(tags);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<Tag>>() {
                               @Override
                               public void call(List<Tag> tags) {
                                   onLoadMoreComplete(tags);
                               }
                           },
                        new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                Logger.e("load new :", throwable);
                                mView.addMoreError();
                            }
                        }
                );
        addSubscription(subscription);

    }

    private Observable<List<Tag>> buildTagWithUserStatus(List<Tag> tags) {
        return Observable.just(tags)
                //查询当前tags中是已经订阅的 Subscribe 的tagId
                .flatMap(new Func1<List<Tag>, Observable<List<Tag>>>() {
                    @Override
                    public Observable<List<Tag>> call(final List<Tag> tags) {
                        if (!AuthUserHelper.getInstance().isLoggedIn()) {
                            return Observable.just(tags);
                        } else {
                            final List<HashMap<String, String>> allTagJsonList = new ArrayList<>();
                            final List<String> singleSubcribeId = new ArrayList<>(1);

                            for (Tag tag : tags) {
                                HashMap<String, String> json = new HashMap<String, String>();
                                json.put("__type", "Pointer");
                                json.put("className", "Tag");
                                json.put("objectId", tag.getObjectId());
                                allTagJsonList.add(json);
                            }


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
                                    })
                                    .flatMap(new Func1<String, Observable<Integer>>() {
                                        @Override
                                        public Observable<Integer> call(final String s) {
                                            return Observable.from(tags)
                                                    .filter(new Func1<Tag, Boolean>() {
                                                        @Override
                                                        public Boolean call(Tag tag) {
                                                            return tag.getObjectId().equals(s);
                                                        }
                                                    }).map(new Func1<Tag, Integer>() {
                                                        @Override
                                                        public Integer call(Tag tag) {
                                                            tag.setIsSubscribed(true);
                                                            tag.setSubscribedId(singleSubcribeId.get(0));
                                                            return Integer.valueOf(0);
                                                        }
                                                    });
                                        }
                                    })

                                    .map(new Func1<Integer, List<Tag>>() {
                                        @Override
                                        public List<Tag> call(Integer integer) {
                                            return tags;
                                        }
                                    })
                                    .switchIfEmpty(Observable.just(tags))
                                    ;
                        }

                    }
                });
    }


    public void unSubscribeTag(String subscribedId, final int position) {
        Subscription subscription = subscribeService.unSubscribe(subscribedId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean aBoolean) {
                        mView.onUnSubscribeTag(position);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mView.onUnSubscribeTagError();
                    }
                });
        addSubscription(subscription);
    }

    public void subscribeTag(String cid, final int position) {
        if(AuthUserHelper.getInstance().isLoggedIn()) {
            Subscription subscription = new CommonSaveService().saveSubscription(cid)
                    .throttleFirst(500, TimeUnit.MILLISECONDS)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<String>() {
                        @Override
                        public void call(String o) {
                            Logger.json(o.toString());
                            mView.onSubscribeTag(o, position);
                        }
                    }, new Action1<Throwable>() {
                        @Override
                        public void call(Throwable throwable) {
                            mView.onSubscribeTagError();
                        }
                    });
            addSubscription(subscription);
        }else {
            mView.showNeedLoginDialog();
        }
    }
}

