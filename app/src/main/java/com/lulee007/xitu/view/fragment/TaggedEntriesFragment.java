package com.lulee007.xitu.view.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lulee007.xitu.R;
import com.lulee007.xitu.TagFollowGuideActivity;
import com.lulee007.xitu.adapter.EntryCardItemAdapter;
import com.lulee007.xitu.models.Entry;
import com.lulee007.xitu.presenter.TaggedEntriesPresenter;
import com.lulee007.xitu.util.DataStateViewHelper;
import com.lulee007.xitu.view.IEntriesView;

/**
 * A simple {@link Fragment} subclass.
 */
public class TaggedEntriesFragment extends BaseListFragment<Entry> implements IEntriesView {

    private static final int ON_TAG_FOLLOW_ACTIVITY_REQUEST=1;

    public TaggedEntriesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = super.onCreateView(inflater, container, savedInstanceState);
        mListAdapter = new EntryCardItemAdapter();
        mUltimateRecyclerView.setAdapter(mListAdapter);
        mUltimateRecyclerView.addItemDividerDecoration(this.getContext());
        mPresenter = new TaggedEntriesPresenter(this);
        mPresenter.loadNew();
        mDataStateViewHelper.setNoDataViewRsid(R.layout.no_entries);
        mDataStateViewHelper.setNoDataBtnId(R.id.add_tag_btn);
        return view;
    }


    @Override
    public void onNoDataButtonClick() {
        startActivityForResult(new Intent(getContext(), TagFollowGuideActivity.class), ON_TAG_FOLLOW_ACTIVITY_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==ON_TAG_FOLLOW_ACTIVITY_REQUEST){
            mDataStateViewHelper.setView(DataStateViewHelper.DateState.LOADING);
            mPresenter.refresh();
        }else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
