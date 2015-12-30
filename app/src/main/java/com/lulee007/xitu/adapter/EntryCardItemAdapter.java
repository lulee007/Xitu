package com.lulee007.xitu.adapter;

import android.animation.Animator;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jakewharton.rxbinding.view.RxView;
import com.lulee007.xitu.R;
import com.lulee007.xitu.base.XTBaseAdapter;
import com.lulee007.xitu.models.Entry;
import com.marshalchen.ultimaterecyclerview.animators.internal.ViewHelper;

import java.util.concurrent.TimeUnit;

import rx.functions.Action1;

/**
 * User: lulee007@live.com
 * Date: 2015-12-15
 * Time: 15:30
 */
public class EntryCardItemAdapter extends XTBaseAdapter<Entry> {

    private EntryCardItemListener entryCardItemListener;

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
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (isItemViewHolder(position)) {
            final Entry entry = getItem(position);
            if (entry != null) {
                final EntryCardViewHolder entryCardViewHolder = (EntryCardViewHolder) holder;
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
                        if (entryCardItemListener != null) {
                            entryCardItemListener.onTagClick(entry);
                        }
                    }
                });
                entryCardViewHolder.collectionCount.setText(entry.getCollectionCount() > 0 ? String.valueOf(entry.getCollectionCount()) : "0");
                entryCardViewHolder.recommended.setVisibility(entry.isHot() ? View.VISIBLE : View.GONE);
                if (entry.getCollection() == null) {
                    Glide.with(entryCardViewHolder.itemView.getContext())
                            .load(R.mipmap.ic_action_favorite_outline)
                            .into(entryCardViewHolder.collected);
                } else {
                    Glide.with(entryCardViewHolder.itemView.getContext())
                            .load(R.mipmap.ic_action_favorite)
                            .into(entryCardViewHolder.collected);
                }
                RxView.clicks(entryCardViewHolder.collected)
                        .throttleFirst(500, TimeUnit.MILLISECONDS)
                        .subscribe(new Action1<Void>() {
                            @Override
                            public void call(Void aVoid) {
                                if (entryCardItemListener != null) {
                                    entryCardItemListener.onToCollectClick(entry, position);
                                }
                            }
                        });
                RxView.clicks(entryCardViewHolder.authorAvatar)
                        .throttleFirst(500, TimeUnit.MILLISECONDS)
                        .subscribe(new Action1<Void>() {
                            @Override
                            public void call(Void aVoid) {
                                if (entryCardItemListener != null) {
                                    entryCardItemListener.onAuthorIconClick(entry, entryCardViewHolder.authorAvatar);
                                }
                            }
                        });
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

    public interface EntryCardItemListener {
        void onAuthorIconClick(Entry entry, ImageView icon);

        void onTagClick(Entry entry);

        void onToCollectClick(Entry entry, int position);
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
