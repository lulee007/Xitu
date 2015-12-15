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
import com.lulee007.xitu.models.Tag;
import com.marshalchen.ultimaterecyclerview.UltimateViewAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * User: lulee007@live.com
 * Date: 2015-12-09
 * Time: 17:27
 */
public class TagFollowAdapter extends UltimateViewAdapter<TagFollowAdapter.TagFollowViewHolder> {

    private List<Tag> allTags = new ArrayList<>();

    public void init(List<Tag> hotTags) {

        allTags.clear();
        allTags.addAll(hotTags);
    }

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
    public int getAdapterItemCount() {
        return allTags.size();
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
    public void onBindViewHolder(final TagFollowViewHolder holder, int position) {
        if (position < getItemCount()
                && (customHeaderView != null ?position <= allTags.size():position<allTags.size())
                && (customHeaderView != null ? position > 0 : true)) {

            final Tag tag = allTags.get(customHeaderView != null ?position-1:position);
            holder.tagTitle.setText(tag.getTitle());
            holder.tagSubscribersCount.setText(String.format("%d 关注", tag.getSubscribersCount()));
            holder.tagEntriesCount.setText(String.format("%d 文章", tag.getEntriesCount()));
            holder.follow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(itemListener!=null){
                        if(holder.follow.getText().toString().equalsIgnoreCase("关注")) {
                            holder.follow.setText("已关注");
                            itemListener.onFollowClick(tag);
                        }else{
                            holder.follow.setText("关注");
                            itemListener.onFollowClick(tag);
                        }
                    }
                }
            });
            if(tag.getIcon()!=null) {
                Glide.with(holder.tagIcon.getContext())
                        .load(tag.getIcon().getUrl())
                        .crossFade()
                        .into(holder.tagIcon);
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
        if (position < getItemCount()
                && (customHeaderView != null ?position <= allTags.size():position<allTags.size())
                && (customHeaderView != null ? position > 0 : true)) {
            String category=allTags.get(customHeaderView!=null?position-1:position).isHot()?"推荐标签":"其他标签";
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

    public void addMore(List<Tag> tags) {
        allTags.addAll(tags);
    }
    public Tag getItem(int position) {
        if (customHeaderView != null)
            position--;
        if (position < allTags.size())
            return allTags.get(position);
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
