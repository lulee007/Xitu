package com.lulee007.xitu.util;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.jakewharton.rxbinding.view.RxView;
import com.lulee007.xitu.R;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;
import com.marshalchen.ultimaterecyclerview.UltimateViewAdapter;

/**
 * User: lulee007@live.com
 * Date: 2015-12-11
 * Time: 12:29
 */
public class DataStateViewHelper {

    private int loadingViewRsid;
    private int noDataViewRsid;
    private int errorViewRsid;
    private int loadMoreErrorViewRsid;
    private int errorRetryBtnId;
    private int loadMoreErrorRetryBtnId;
    private int noDataBtnId;
    private DataStateViewListener dataStateViewListener;
    private View currentView;


    public enum DateState {

        LOADING,
        NO_DATA,
        ERROR,
        CONTENT,
        LOAD_MORE_ERROR,
        LOADING_MORE

    }

    public interface DataStateViewListener {
        void onErrorRetry();

        void onLoadMoreErrorRetry();

        void onNoDataButtonClick();
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
        this.errorRetryBtnId=R.id.retry_btn;

        this.loadMoreErrorViewRsid = R.layout.common_load_more_error;
        this.loadMoreErrorRetryBtnId=R.id.retry_btn;

    }

    public void setView(DateState state) {
        switch (state) {
            case LOADING:
                setStateOfContentView(loadingViewRsid);
                break;
            case NO_DATA:
                setStateOfContentView(noDataViewRsid);
                break;
            case ERROR:
                setStateOfContentView(errorViewRsid);
                break;
            case LOAD_MORE_ERROR:
                setStateOfLoadingMoreView(loadMoreErrorViewRsid);
                break;
            case LOADING_MORE:
                setStateOfLoadingMoreView(loadingViewRsid);
                break;
            default:
                ultimateRecyclerView.mRecyclerView.setVisibility(View.VISIBLE);
                ultimateRecyclerView.hideEmptyView();

        }
    }

    //// TODO: 15/12/11  修改后listview顶部heaerview有空白bug 如果ultimateRecyclerView setParallaxHeader
    private void setStateOfLoadingMoreView(int rsid) {
        ViewGroup loadingViewGroup= (ViewGroup) ((UltimateViewAdapter) ultimateRecyclerView.getAdapter()).getCustomLoadMoreView();
        if(loadingViewGroup!=null){
            loadingViewGroup.removeAllViewsInLayout();
        }
        View loadingView = LayoutInflater.from(ultimateRecyclerView.getContext()).inflate(rsid, null);
        if (loadingView instanceof LinearLayout) {
            loadingView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        } else if (loadingView instanceof RelativeLayout) {
            loadingView.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        }
        if(rsid==loadMoreErrorViewRsid){
            loadingView.findViewById(this.loadMoreErrorRetryBtnId).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (dataStateViewListener != null) {
                        dataStateViewListener.onLoadMoreErrorRetry();
                    }
                }
            });
            }

        loadingViewGroup.addView(loadingView);
    }

    private void setStateOfContentView(int rsid) {
        if (rsid < 1)
            return;
        View view = ultimateRecyclerView.getEmptyView();

        if (view != null) {

            /**
             * 注意，view 外面必须有层layout包裹
             */
            ViewGroup viewGroup = (ViewGroup) view;
            viewGroup.removeAllViewsInLayout();
            View contentView = LayoutInflater.from(viewGroup.getContext()).inflate(rsid, null);
            if (contentView instanceof LinearLayout) {
                contentView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            } else if (contentView instanceof RelativeLayout) {
                contentView.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            }
            viewGroup.addView(contentView);
            if (rsid == errorViewRsid) {
                contentView.findViewById(errorRetryBtnId).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (dataStateViewListener != null) {
                            dataStateViewListener.onErrorRetry();
                        }
                    }
                });
            }else if(rsid==noDataViewRsid && noDataBtnId>0){
                View btn=contentView.findViewById(noDataBtnId);
                if(btn!=null){

                    btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dataStateViewListener.onNoDataButtonClick();
                        }
                    });
                }
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

    public void setNoDataViewRsid(int noDataViewRsid) {
        this.noDataViewRsid = noDataViewRsid;
    }

    public void setErrorViewRsid(int errorViewRsid) {
        this.errorViewRsid = errorViewRsid;
    }

    public void setLoadMoreErrorViewRsid(int loadMoreErrorViewRsid) {
        this.loadMoreErrorViewRsid = loadMoreErrorViewRsid;
    }

    public void setErrorRetryBtnId(int errorRetryBtnId) {
        this.errorRetryBtnId = errorRetryBtnId;
    }

    public void setLoadMoreErrorRetryBtnId(int loadMoreErrorRetryBtnId) {
        this.loadMoreErrorRetryBtnId = loadMoreErrorRetryBtnId;
    }

    public void setNoDataBtnId(int noDataBtnId) {
        this.noDataBtnId = noDataBtnId;
    }
}
