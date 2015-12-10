package com.lulee007.xitu.presenter;

import com.lulee007.xitu.models.Tag;
import com.lulee007.xitu.services.TagService;
import com.lulee007.xitu.view.ITagFollowGuideView;
import com.orhanobut.logger.Logger;

import java.util.HashMap;
import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func2;

/**
 * User: lulee007@live.com
 * Date: 2015-12-09
 * Time: 14:28
 */
public class TagFollowGuidePresenter {

    private ITagFollowGuideView tagFollowGuideView;

    private TagService tagService;

    public TagFollowGuidePresenter(ITagFollowGuideView view) {
        tagFollowGuideView = view;
        tagService = new TagService();
    }

    public void loadNewTags() {
        HashMap hotTagParams = new HashMap<String, String>();
        hotTagParams.put("order", "-entriesCount");
        hotTagParams.put("limit", "100");
        hotTagParams.put("where", "{\"hot\":true}");
        HashMap normalTagParams = new HashMap<String, String>();
        normalTagParams.put("order", "-entriesCount");
        normalTagParams.put("limit", "100");
        normalTagParams.put("where", "{\"hot\":{\"$ne\":true}}");
        Observable.zip(
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
                        tagFollowGuideView.addNewError();
                    }

                    @Override
                    public void onNext(HashMap<String, List<Tag>> o) {
                        tagFollowGuideView.addNewTags(o);
                    }
                });
    }


    public void loadMore(int lastItemPosition) {
        HashMap normalTagParams = new HashMap<String, String>();
        normalTagParams.put("order", "-entriesCount");
        normalTagParams.put("limit", "100");
        normalTagParams.put("skip", lastItemPosition);
        normalTagParams.put("where", "{\"hot\":{\"$ne\":true}}");
        tagService.getTags(normalTagParams)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Tag>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.e(e, "get hot tags error");
                    }

                    @Override
                    public void onNext(List<Tag> o) {
                        tagFollowGuideView.addMoreTags(o);
                        if (o.size() < 100) {
                            tagFollowGuideView.noMore();
                        }

                    }
                });
    }
}
