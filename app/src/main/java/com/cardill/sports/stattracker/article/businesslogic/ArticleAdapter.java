package com.cardill.sports.stattracker.article.businesslogic;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cardill.sports.stattracker.R;
import com.cardill.sports.stattracker.article.data.CardillContent;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import static com.cardill.sports.stattracker.article.ArticleDetailFragment.ARTICLE;

public class ArticleAdapter extends RecyclerView.Adapter<ContentViewHolder> {

    private final List<CardillContent> mValues;

    private final Context mContext;
    private final GradientDrawable gradientDrawable;

    private final FragmentManager mFragmentManager;

    public ArticleAdapter(List<CardillContent> items, Context context) {
        mValues = items;
        mContext = context;
        gradientDrawable = new GradientDrawable();
        gradientDrawable.setShape(GradientDrawable.RECTANGLE);
        gradientDrawable.setColor(Color.BLUE);
        FragmentActivity a = (FragmentActivity) context;
        mFragmentManager = a.getSupportFragmentManager();

    }

    @Override
    public ContentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.article_list_content, parent, false);
        return new ContentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ContentViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        Picasso.with(mContext)
                .load("http:" + holder.mItem.Image)
                .fit().centerCrop()
                .into(holder.mImageView);

        holder.mArticleAuthorView.setText(holder.mItem.Creator.name);
        holder.mArticleTitleView.setText(holder.mItem.Name);
        holder.mView.setOnClickListener(v -> {
            Bundle params = new Bundle();
            params.putParcelable(ARTICLE, holder.mItem);
            Navigation.findNavController(holder.mView).navigate(R.id.articleDetailFragment, params);
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }


}