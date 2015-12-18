package com.lulee007.xitu.presenter;

import android.support.annotation.NonNull;

import com.lulee007.xitu.base.XTBasePresenter;
import com.lulee007.xitu.models.Tag;
import com.lulee007.xitu.services.TagService;
import com.lulee007.xitu.view.ITagFollowGuideView;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func2;

/**
 * User: lulee007@live.com
 * Date: 2015-12-09
 * Time: 14:28
 */
public class TagFollowGuidePresenter extends XTBasePresenter<ITagFollowGuideView> {

    private TagService tagService;


    private List<Tag> followedTags = new ArrayList<>();

    public TagFollowGuidePresenter(ITagFollowGuideView view) {
        super(view);
        tagService = new TagService();
    }

    /**
     * 刷新，初始化加载Tag数据
     */
    public void loadNew() {
        HashMap<String, String> hotTagParams = buildRequestParams("{\"hot\":true}");
        HashMap<String, String> normalTagParams = buildRequestParams("{\"hot\":{\"$ne\":true}}");
        Subscription loadNewTagsSubscription = Observable.zip(
                tagService.getTags(hotTagParams),
                tagService.getTags(normalTagParams),
                new Func2<List<Tag>, List<Tag>, HashMap<String, List<Tag>>>() {
                    @Override
                    public HashMap<String, List<Tag>> call(List<Tag> o, List<Tag> o2) {
                        HashMap<String, List<Tag>> tagsMap = new HashMap<String, List<Tag>>();
                        tagsMap.put("hot", o);
                        tagsMap.put("normal", o2);
                        return tagsMap;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<HashMap<String, List<Tag>>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.e(e, "get hot tags error");
                        mView.addNewError();
                    }

                    @Override
                    public void onNext(HashMap<String, List<Tag>> o) {
                        List<Tag> tags = new ArrayList<Tag>();
                        tags.addAll(o.get("hot"));
                        tags.addAll(o.get("normal"));
                        mView.addNew(tags);

                        if (o.get("normal").size() == 0) {
                            mView.noData();
                        }
                        if (o.get("normal").size() < pageOffset) {
                            mView.noMore();
                        }

                    }
                });
        addSubscription(loadNewTagsSubscription);
    }

    @Override
    public void refresh() {

    }

    public void loadMore() {
        HashMap<String, String> normalTagParams = buildRequestParams("{\"hot\":{\"$ne\":true}}", pageIndex * pageOffset);
        Subscription loadMoreSubscription = tagService.getTags(normalTagParams)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Tag>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.addMoreError();
                        Logger.e(e, "get hot tags error");
                    }

                    @Override
                    public void onNext(List<Tag> items) {
                        onLoadMoreComplete(items);
                    }
                });
        addSubscription(loadMoreSubscription);
    }

    /**
     * 构建查询参数
     *
     * @param where {hot:true} etc.
     * @param skip  skip count
     * @return HashMap
     */
    @NonNull
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

    /**
     * 构建查询参数
     *
     * @param where {hot:true} etc.
     * @return HashMap
     */
    @NonNull
    protected HashMap<String, String> buildRequestParams(String where) {
        HashMap<String, String> hotTagParams = buildRequestParams(where, 0);
        return hotTagParams;
    }

    public void addTag(Tag tag) {
        if (this.followedTags.contains(tag)) {
            this.followedTags.remove(tag);
        } else {
            this.followedTags.add(tag);
        }
        if (this.followedTags.size() > 0) {
            mView.showConfirm();
        }
    }

    public List<Tag> getFollowedTags() {
        return followedTags;
    }


}
