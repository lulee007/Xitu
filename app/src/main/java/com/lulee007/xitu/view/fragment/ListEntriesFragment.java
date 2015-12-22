package com.lulee007.xitu.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lulee007.xitu.EntryWebPageActivity;
import com.lulee007.xitu.adapter.EntryListItemAdapter;
import com.lulee007.xitu.models.Entry;
import com.lulee007.xitu.presenter.ListEntriesFragmentPresenter;
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

    public static ListEntriesFragment newInstance(int type) {
        return newInstance(type, null);
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
            switch (type) {
                case ListEntriesFragmentPresenter.BY_RECOMMENDED:
                case ListEntriesFragmentPresenter.BY_USER:
                    p = new ListEntriesFragmentPresenter(this, type, null);
                    break;
                case ListEntriesFragmentPresenter.BY_TAG_HOT:
                case ListEntriesFragmentPresenter.BY_TAG_LATEST:
                    Map<String, String> whereMap = new HashMap<>();
                    whereMap.put("tagsTitleArray", args.getString(BUNDLE_KEY_TAG_NAME));
                    p = new ListEntriesFragmentPresenter(this, type, whereMap);
                    break;
            }
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
                ((Entry) item).getUser().getAvatar_large()
        );
        startActivity(intent);
    }
}
