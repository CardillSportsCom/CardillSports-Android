package com.cardill.sports.stattracker.consumer.article.businesslogic;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cardill.sports.stattracker.consumer.R;
import com.cardill.sports.stattracker.consumer.article.data.CardillContent;

public class ContentViewHolder extends RecyclerView.ViewHolder {
    public final View mView;
    public final ImageView mImageView;
    public final TextView mArticleTitleView;
    public final TextView mArticleAuthorView;

    public CardillContent mItem;

    public ContentViewHolder(View view) {
        super(view);
        mView = view;
        mImageView = (ImageView) view.findViewById(R.id.image);
        mArticleAuthorView = (TextView) view.findViewById(R.id.article_author);
        mArticleTitleView = (TextView) view.findViewById(R.id.article_title);
    }

    @Override
    public String toString() {
        return super.toString() + " '";
    }
}