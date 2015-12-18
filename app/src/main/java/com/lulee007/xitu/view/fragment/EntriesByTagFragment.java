package com.lulee007.xitu.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lulee007.xitu.EntryWebPageActivity;
import com.lulee007.xitu.adapter.EntryListItemAdapter;
import com.lulee007.xitu.models.Entry;
import com.lulee007.xitu.presenter.EntriesByTagFragmentPresenter;
import com.lulee007.xitu.view.IEntriesByTagFragmentView;

/**
 * User: lulee007@live.com
 * Date: 2015-12-17
 * Time: 17:23
 */
public class EntriesByTagFragment extends BaseListFragment<Entry> implements IEntriesByTagFragmentView {

    public static final String BUNDLE_KEY_ENTRY_TYPE = "entry_type";
    public static final String BUNDLE_KEY_TAG_NAME = "tag_name";

    public EntriesByTagFragment() {

    }


    public static EntriesByTagFragment newInstance(String type, String tag) {

        Bundle args = new Bundle();
        args.putString(BUNDLE_KEY_ENTRY_TYPE, type);
        args.putString(BUNDLE_KEY_TAG_NAME, tag);
        EntriesByTagFragment fragment = new EntriesByTagFragment();
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
            EntriesByTagFragmentPresenter p = new EntriesByTagFragmentPresenter(this);
            p.setOrderBy(args.getString(BUNDLE_KEY_ENTRY_TYPE));
            p.setWhere(args.getString(BUNDLE_KEY_TAG_NAME));
            mPresenter = p;
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
