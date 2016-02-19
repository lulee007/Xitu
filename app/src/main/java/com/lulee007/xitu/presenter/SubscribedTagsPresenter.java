package com.lulee007.xitu.presenter;

import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.lulee007.xitu.base.XTBasePresenter;
import com.lulee007.xitu.models.Subscribe;
import com.lulee007.xitu.models.Tag;
import com.lulee007.xitu.services.SubscribeService;
import com.lulee007.xitu.util.AuthUserHelper;
import com.lulee007.xitu.view.ITagFollowGuideView;

import java.util.HashMap;
import java.util.List;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * User: lulee007@live.com
 * Date: 2015-12-18
 * Time: 17:11
 */
public class SubscribedTagsPresenter extends XTBasePresenter<ITagFollowGuideView> {

    private SubscribeService subscribeService;

    public void setUserId(String userId) {
        this.userId = userId;
    }

    private String userId;

    public SubscribedTagsPresenter(ITagFollowGuideView view) {
        super(view);
        pageOffset = 100;
        subscribeService = new SubscribeService();
        userId = AuthUserHelper.getInstance().getUser() == null ? null : (String) AuthUserHelper.getInstance().getUser().get("objectId");

    }

    @Override
    protected HashMap<String, String> buildRequestParams(String where, int skip) {
        /**
         * include	tag
         limit	100
         where	{"user":{"__type":"Pointer","className":"_User","objectId":"563c1d9xxxx749ea071246"}}
         order	-createdAt
         */
        HashMap<String, String> params = new HashMap<>();
        params.put("include", "tag");
        params.put("limit", pageOffset + "");
        params.put("where", String.format("{\"user\":{\"__type\":\"Pointer\",\"className\":\"_User\",\"objectId\":\"%s\"}}", where));
        params.put("order", "-createdAt");
        if (skip > 0) {
            params.put("skip", skip + "");
        }
        return params;
    }

    @NonNull
    @Override
    protected HashMap<String, String> buildRequestParams(String where) {
        return buildRequestParams(where, 0);
    }

    @Override
    public void loadNew() {
        Subscription subscription = subscribeService.getSubscribes(buildRequestParams(userId))
                .flatMap(new Func1<List<Subscribe>, Observable<Subscribe>>() {
                    @Override
                    public Observable<Subscribe> call(List<Subscribe> subscribes) {
                        return Observable.from(subscribes);
                    }
                })
                .map(new Func1<Subscribe, Tag>() {
                    @Override
                    public Tag call(Subscribe subscribe) {
                        Tag tag = subscribe.getTag();
                        tag.setIsSubscribed(true);
                        tag.setSubscribedId(subscribe.getObjectId());
                        return tag;
                    }
                })
                .toList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<Tag>>() {
                    @Override
                    public void call(List<Tag> entries) {
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
                    }
                });
        addSubscription(subscription);
    }

    @Override
    public void refresh() {

    }

    @Override
    public void loadMore() {
        Subscription subscription = subscribeService.getSubscribes(buildRequestParams(userId, pageOffset * pageIndex))
                .flatMap(new Func1<List<Subscribe>, Observable<Subscribe>>() {
                    @Override
                    public Observable<Subscribe> call(List<Subscribe> subscribes) {
                        return Observable.from(subscribes);
                    }
                })
                .map(new Func1<Subscribe, Tag>() {
                    @Override
                    public Tag call(Subscribe subscribe) {
                        Tag tag = Tag.objectFromData(new Gson().toJson(subscribe.getTag().toString()));
                        tag.setIsSubscribed(true);
                        return tag;
                    }
                })
                .toList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<Tag>>() {
                    @Override
                    public void call(List<Tag> entries) {
                        onLoadMoreComplete(entries);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mView.addMoreError();
                    }
                });
        addSubscription(subscription);
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
}
