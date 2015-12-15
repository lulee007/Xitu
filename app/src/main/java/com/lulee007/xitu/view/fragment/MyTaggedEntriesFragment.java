package com.lulee007.xitu.view.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lulee007.xitu.R;
import com.lulee007.xitu.base.XTBaseFragment;
import com.lulee007.xitu.models.Entry;
import com.lulee007.xitu.presenter.RecommendedEntriesPresenter;
import com.lulee007.xitu.util.DataStateViewHelper;
import com.lulee007.xitu.view.IEntriesView;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyTaggedEntriesFragment extends XTBaseFragment implements IEntriesView,DataStateViewHelper.DataStateViewListener {

    private UltimateRecyclerView ultimateRecyclerView;
    private RecommendedEntriesPresenter recommendedEntriesPresenter;


    public MyTaggedEntriesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_entries, container, false);

        return view;
    }


    @Override
    public void refresh(List<Entry> entries) {

    }

    @Override
    public void refreshNoContent() {

    }

    @Override
    public void refreshError() {

    }

    @Override
    public void addMore(List<Entry> moreItems) {

    }

    @Override
    public void addNew(List<Entry> newItems) {

    }

    @Override
    public void addNewError() {

    }

    @Override
    public void addMoreError() {

    }

    @Override
    public void noMore() {

    }

    @Override
    public void noData() {

//        (view.findViewById(R.id.add_tag_btn)).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getContext(), TagFollowGuideActivity.class);
//                getActivity().startActivity(intent);
//            }
//        });
    }

    @Override
    public void onErrorRetry() {

    }

    @Override
    public void onLoadMoreErrorRetry() {

    }
}
