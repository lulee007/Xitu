package com.lulee007.xitu.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lulee007.xitu.R;
import com.lulee007.xitu.base.XTBaseAdapter;
import com.lulee007.xitu.models.Entry;

/**
 * User: lulee007@live.com
 * Date: 2015-12-15
 * Time: 15:30
 */
public class EntryCardItemAdapter extends XTBaseAdapter<Entry> {

    private  EntryCardItemListener entryCardItemListener;

    @Override
    public RecyclerView.ViewHolder getViewHolder(View view) {
        return new EntryCardViewHolder(view, false);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.entry_card_item, parent, false);
        return new EntryCardViewHolder(view, true);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (isItemViewHolder(position)) {
            final Entry entry = getItem(position);
            if (entry != null) {
              final   EntryCardViewHolder entryCardViewHolder = (EntryCardViewHolder) holder;
                entryCardViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (itemListener != null) {
                            itemListener.onItemClick(entry);
                        }
                    }
                });
                entryCardViewHolder.title.setText(entry.getTitle());
                entryCardViewHolder.content.setText(entry.getContent());

                entryCardViewHolder.tag.setText(entry.getTagsTitleArray().get(0));
                entryCardViewHolder.tag.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(entryCardItemListener!=null){
                            entryCardItemListener.onTagClick(entry);
                        }
                    }
                });
                entryCardViewHolder.collectionCount.setText(entry.getCollectionCount() + "");
                entryCardViewHolder.recommended.setVisibility(entry.isHot() ? View.VISIBLE : View.GONE);

                if (entry.getScreenshot() != null)
                    Glide.with(entryCardViewHolder.itemView.getContext())
                            .load(entry.getScreenshot().getUrl())
                            .crossFade()
                            .into(entryCardViewHolder.screenshot);

                if (entry.getUser().getAvatar_large() != null)
                    Glide.with(entryCardViewHolder.itemView.getContext())
                            .load(entry.getUser().getAvatar_large())
                            .crossFade()
                            .into(entryCardViewHolder.authorAvatar);
                entryCardViewHolder.authorAvatar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(entryCardItemListener!=null){
                            entryCardItemListener.onAuthorIconClick(entry, entryCardViewHolder.authorAvatar);
                        }
                    }
                });

                entryCardViewHolder.collected.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(entryCardItemListener!=null){
                            entryCardItemListener.onToCollectClick(entry);
                        }
                    }
                });

            }
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
        return null;
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    public void setEntryCardItemListener(EntryCardItemListener entryCardItemListener) {
        this.entryCardItemListener = entryCardItemListener;
    }

    public interface EntryCardItemListener{
        void onAuthorIconClick(Entry entry,ImageView icon);
        void onTagClick(Entry entry);
        void onToCollectClick(Entry entry);
    }

    protected class EntryCardViewHolder extends RecyclerView.ViewHolder {

        public ImageView screenshot;
        public TextView title;
        public TextView content;
        public ImageView authorAvatar;
        public ImageView recommended;
        public TextView tag;
        public TextView collectionCount;
        public ImageView collected;

        public EntryCardViewHolder(View itemView, boolean isItem) {
            super(itemView);
            if (isItem) {
                screenshot = (ImageView) itemView.findViewById(R.id.entry_screenshot);
                title = (TextView) itemView.findViewById(R.id.entry_title);
                content = (TextView) itemView.findViewById(R.id.entry_content);
                authorAvatar = (ImageView) itemView.findViewById(R.id.entry_author_avatar);
                recommended = (ImageView) itemView.findViewById(R.id.entry_recommended);

                recommended.setVisibility(View.GONE);
                tag = (TextView) itemView.findViewById(R.id.entry_tag);
                collectionCount = (TextView) itemView.findViewById(R.id.entry_collection_count);
                collected = (ImageView) itemView.findViewById(R.id.entry_to_collect);
            }
        }


    }
}
