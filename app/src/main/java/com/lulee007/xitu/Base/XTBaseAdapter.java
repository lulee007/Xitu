package com.lulee007.xitu.base;

import com.marshalchen.ultimaterecyclerview.UltimateViewAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 1、ItemView自定义view点击事件需要实现ItemListener接口
 * 2、提供数据初始化接口init以及添加更多数据接口addMore
 * 3、在bindViewHolder里需要调用isItemViewHolder，来检测当前位置的item是不是数据item
 * 4、获取item T 时可以直接调用getItem方法
 * User: lulee007@live.com
 * Date: 2015-12-09
 * Time: 17:27
 */
public abstract class XTBaseAdapter<T> extends UltimateViewAdapter {

    protected List<T> items = new ArrayList<>();
    protected int mLastPosition;
    protected boolean isFirstOnly;

    public void init(List<T> itemList) {

        items.clear();
        items.addAll(itemList);
        notifyDataSetChanged();
    }

    public void insert(List<T> newItems){
        if(newItems!=null){
            this.items.addAll(0,newItems);
            notifyItemRangeInserted(0, newItems.size());
        }
    }

    protected boolean isItemViewHolder(int position){
        return position < getItemCount()
                && (customHeaderView != null ?position <= items.size():position<items.size())
                && (customHeaderView != null ? position > 0 : true);
    }


    @Override
    public int getAdapterItemCount() {
        return items.size();
    }

    @Override
    public long generateHeaderId(int position) {
//        T tag = getItem(position);
//        if (tag != null) {
//            return tag.isHot() ? 0 : 1;
//        }
        return -1;
    }


    public interface ItemListener {
        void onItemClick(Object item);
    }

    protected ItemListener itemListener;

    public void setItemListener(ItemListener listener) {
        this.itemListener = listener;
    }

    public void addMore(List<T> moreItems) {
        int old=items.size();
        items.addAll(moreItems);
        notifyItemRangeInserted(old,moreItems.size());
    }

    protected T getItem(int position) {
        if (customHeaderView != null)
            position--;
        if (position < items.size())
            return items.get(position);
        else return null;
    }


}
