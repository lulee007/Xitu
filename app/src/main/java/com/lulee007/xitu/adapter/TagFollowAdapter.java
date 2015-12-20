package com.lulee007.xitu.adapter;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lulee007.xitu.R;
import com.lulee007.xitu.base.XTBaseAdapter;
import com.lulee007.xitu.models.Tag;
import com.marshalchen.ultimaterecyclerview.UltimateViewAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * User: lulee007@live.com
 * Date: 2015-12-09
 * Time: 17:27
 */
public class TagFollowAdapter extends XTBaseAdapter<Tag> {



    @Override
    public TagFollowViewHolder getViewHolder(View view) {
        TagFollowViewHolder tagFollowViewHolder = new TagFollowViewHolder(view, false);
        return tagFollowViewHolder;
    }

    @Override
    public TagFollowViewHolder onCreateViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tag_follow_item, null);
        TagFollowViewHolder tagFollowViewHolder = new TagFollowViewHolder(view, true);
        return tagFollowViewHolder;
    }


    @Override
    public long generateHeaderId(int position) {
        Tag tag=getItem(position);
        if(tag!=null){
            return tag.isHot()?0:1;
        }
        return -1;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        if (isItemViewHolder(position)) {
            final TagFollowViewHolder holder1=(TagFollowViewHolder)holder;
            final Tag tag = getItem(position);
            holder1.tagTitle.setText(tag.getTitle());
            holder1.tagSubscribersCount.setText(String.format("%d 关注", tag.getSubscribersCount()));
            holder1.tagEntriesCount.setText(String.format("%d 文章", tag.getEntriesCount()));
            if(tag.isSubscribed()){
                holder1.follow.setText("√已关注");
            }else{
                holder1.follow.setText("关注");
            }
            holder1.follow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(itemListener!=null){
                        if(holder1.follow.getText().toString().equalsIgnoreCase("关注")) {
                            holder1.follow.setText("已关注");
                            itemListener.onFollowClick(tag);
                        }else{
                            holder1.follow.setText("关注");
                            itemListener.onFollowClick(tag);
                        }
                    }
                }
            });
            if(tag.getIcon()!=null) {
                Glide.with(holder1.tagIcon.getContext())
                        .load(tag.getIcon().getUrl())
                        .crossFade()
                        .into(holder1.tagIcon);
            }
        }
    }

    @Override
    public ViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.tag_follow_header_item,null);
        return new TagFollowHeaderViewHolder(view);
    }

    @Override
    public void onBindHeaderViewHolder(ViewHolder holder, int position) {
        TagFollowHeaderViewHolder tagFollowHeaderViewHolder=(TagFollowHeaderViewHolder)holder;
        if (isItemViewHolder(position)) {
            String category=getItem(position).isHot()?"推荐标签":"其他标签";
            tagFollowHeaderViewHolder.tagCategory.setText(category);
        }
    }



    public interface ItemListener{
        void onFollowClick(Tag tag);
    }
    private ItemListener itemListener;

    public  void setItemListener(ItemListener listener){
        this.itemListener=listener;
    }



    public Tag getItem(int position) {
        if (customHeaderView != null)
            position--;
        if (position < items.size())
            return items.get(position);
        else return null;
    }

    public class TagFollowViewHolder extends RecyclerView.ViewHolder {

        public TextView tagTitle;
        public TextView tagSubscribersCount;

        public TextView tagEntriesCount;
        public ImageView tagIcon;
        public Button follow;

        public TagFollowViewHolder(View itemView, boolean isItem) {


            super(itemView);
            if (isItem) {
                tagTitle = (TextView) itemView.findViewById(R.id.tagTitle);
                tagIcon = (ImageView) itemView.findViewById(R.id.tagIcon);
                tagEntriesCount= (TextView) itemView.findViewById(R.id.tag_entries_count);
                tagSubscribersCount= (TextView) itemView.findViewById(R.id.tag_subscribers_count);
                follow= (Button) itemView.findViewById(R.id.follow_btn);
            }
        }
    }

    public class TagFollowHeaderViewHolder extends RecyclerView.ViewHolder {

        public TextView tagCategory;

        public TagFollowHeaderViewHolder(View itemView) {
            super(itemView);
            tagCategory= (TextView) itemView.findViewById(R.id.tag_follow_category);
        }
    }


}
