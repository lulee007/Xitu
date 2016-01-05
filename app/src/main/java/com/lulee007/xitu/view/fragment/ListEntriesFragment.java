package com.lulee007.xitu.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lulee007.xitu.EntryWebPageActivity;
import com.lulee007.xitu.adapter.EntryListItemAdapter;
import com.lulee007.xitu.models.Entry;
import com.lulee007.xitu.presenter.CollectionPresenter;
import com.lulee007.xitu.presenter.HistoryPresenter;
import com.lulee007.xitu.presenter.ListEntriesFragmentPresenter;
import com.lulee007.xitu.util.AuthUserHelper;
import com.lulee007.xitu.view.IEntriesByTagFragmentView;

import java.util.HashMap;
import java.util.Map;

/**
 * User: lulee007@live.com
 * Date: 2015-12-17
 * Time: 17:23
 */
public class ListEntriesFragment extends BaseListFragment<Entry> implements IEntriesByTagFragmentView {

    public static final String BUNDLE_KEY_REQUEST_TYPE = "request_type";
    public static final String BUNDLE_KEY_TAG_NAME = "tag_name";
    private static final String BUNDLE_KEY_AUTHOR_ID = "author_id";

    public ListEntriesFragment() {

    }


    public static ListEntriesFragment newInstance(int type, String tag) {

        Bundle args = new Bundle();
        args.putInt(BUNDLE_KEY_REQUEST_TYPE, type);
        if (tag != null)
            args.putString(BUNDLE_KEY_TAG_NAME, tag);
        ListEntriesFragment fragment = new ListEntriesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static ListEntriesFragment newInstanceForRecommended(int type) {
        return newInstance(type, null);
    }

    public static ListEntriesFragment newInstanceForAuthor(int type, String authorId) {
        Bundle args = new Bundle();
        args.putInt(BUNDLE_KEY_REQUEST_TYPE, type);

        args.putString(BUNDLE_KEY_AUTHOR_ID, authorId);
        ListEntriesFragment fragment = new ListEntriesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static ListEntriesFragment newInstanceForHistory() {
        Bundle args = new Bundle();
        args.putInt(BUNDLE_KEY_REQUEST_TYPE, HistoryPresenter.BY_HISTORY);
        ListEntriesFragment fragment = new ListEntriesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        mUltimateRecyclerView.addItemDividerDecoration(this.getContext());

        mListAdapter = new EntryListItemAdapter();
        mListAdapter.setItemListener(this);
        mUltimateRecyclerView.setAdapter(mListAdapter);

        Bundle args = getArguments();
        if (args != null) {
            ListEntriesFragmentPresenter p = null;
            int type = args.getInt(BUNDLE_KEY_REQUEST_TYPE);
            Map<String, String> whereMap = null;
            switch (type) {
                case ListEntriesFragmentPresenter.BY_RECOMMENDED:
                    p = new ListEntriesFragmentPresenter(this, type, null);
                    break;
                case ListEntriesFragmentPresenter.BY_USER:
                    whereMap = new HashMap<>();
                    whereMap.put("authorId", args.getString(BUNDLE_KEY_AUTHOR_ID));
                    p = new ListEntriesFragmentPresenter(this, type, whereMap);

                    break;
                case ListEntriesFragmentPresenter.BY_TAG_HOT:
                case ListEntriesFragmentPresenter.BY_TAG_LATEST:
                    whereMap = new HashMap<>();
                    whereMap.put("tagsTitleArray", args.getString(BUNDLE_KEY_TAG_NAME));
                    p = new ListEntriesFragmentPresenter(this, type, whereMap);
                    break;
                case HistoryPresenter.BY_HISTORY:
                    mPresenter = new HistoryPresenter(this);
                    break;
                case CollectionPresenter.BY_COLLECTION:
                    mPresenter=new CollectionPresenter(this,args.getString(BUNDLE_KEY_AUTHOR_ID));
                    break;
            }
            if (p != null)
                mPresenter = p;
            if (mPresenter != null)
                mPresenter.loadNew();

        }

        return view;
    }



    @Override
    public void onItemClick(Object item) {
        Intent intent = EntryWebPageActivity.buildEntryWebPageParams(
                getContext(),
                ((Entry) item).getUrl(),
                ((Entry) item).getUser().getUsername(),
                ((Entry) item).getUser().getAvatar_large(),
                ((Entry) item).getUser().getObjectId(),
                ((Entry) item).getCommentsCount(),
                ((Entry) item).getCollectionCount()
        );
        startActivity(intent);
    }


}
