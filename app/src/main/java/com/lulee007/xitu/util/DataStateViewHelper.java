package com.lulee007.xitu.util;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.lulee007.xitu.R;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;

/**
 * User: lulee007@live.com
 * Date: 2015-12-11
 * Time: 12:29
 */
public class DataStateViewHelper {

    private int loadingViewRsid;
    private int noDataViewRsid;
    private int errorViewRsid;
    private DataStateViewListener dataStateViewListener;
    private View currentView;


    public enum DateState {

        LOADING,
        NO_DATA,
        ERROR,
        CONTENT

    }

    public interface DataStateViewListener {
        void onErrorRetry();
    }

    public void setDataStateViewListener(DataStateViewListener listener) {
        this.dataStateViewListener = listener;
    }

    private UltimateRecyclerView ultimateRecyclerView;

    public DataStateViewHelper(UltimateRecyclerView ultimateRecyclerView) {
        this.ultimateRecyclerView = ultimateRecyclerView;
        this.loadingViewRsid = R.layout.loading_data_progressbar;
        this.noDataViewRsid = R.layout.common_no_data;
        this.errorViewRsid = R.layout.common_error;
    }

    public void setView(DateState state) {
        switch (state) {
            case LOADING:
                setStateView(this.loadingViewRsid);
                break;
            case NO_DATA:
                setStateView(this.noDataViewRsid);
                break;
            case ERROR:
                setStateView(errorViewRsid);
                break;
            default:
                ultimateRecyclerView.mRecyclerView.setVisibility(View.VISIBLE);
                ultimateRecyclerView.hideEmptyView();

        }
    }

    private void setStateView(int rsid) {
        if (rsid < 1)
            return;
        View view = ultimateRecyclerView.getEmptyView();

        if (view != null) {

            /**
             * 注意，view 外面必须有层layout包裹
             */
            ViewGroup viewGroup = (ViewGroup)view;
            viewGroup.removeAllViewsInLayout();
            View contentView = LayoutInflater.from(viewGroup.getContext()).inflate(rsid, null);
            if (contentView instanceof LinearLayout) {
                contentView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            } else if (contentView instanceof RelativeLayout) {
                contentView.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            }
            viewGroup.addView(contentView);
            if (rsid == errorViewRsid) {
                contentView.findViewById(R.id.retry_btn).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (dataStateViewListener != null) {
                            dataStateViewListener.onErrorRetry();
                        }
                    }
                });
            }

        } else {
            ultimateRecyclerView.setEmptyView(rsid);
        }
        ultimateRecyclerView.mRecyclerView.setVisibility(View.GONE);
        ultimateRecyclerView.showEmptyView();

    }

    public void setLoadingViewRsid(int id) {
        this.loadingViewRsid = id;
    }


}
