package com.example.newsmoengage.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.newsmoengage.R;
import com.example.newsmoengage.callbacks.OnAdapterResponded;
import com.example.newsmoengage.models.Article;

import java.util.List;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.MyViewHolder> implements View.OnClickListener {

    private List<Article>  articles;

    private OnAdapterResponded onAdapterResponded;

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.tvHeadline){
             onAdapterResponded.onAdapterResponse(v.getTag().toString());
        }
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
         TextView tvHeadline;
         TextView tvContent;

         MyViewHolder(View v) {
            super(v);
            tvHeadline = v.findViewById(R.id.tvHeadline);
            tvContent = v.findViewById(R.id.tvContent);
        }
    }

    public ArticleAdapter(List<Article> articles, OnAdapterResponded onAdapterResponded) {
        this.articles = articles;
        this.onAdapterResponded = onAdapterResponded;
    }

    @Override
    public ArticleAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v =  LayoutInflater.from(parent.getContext()).inflate(R.layout.item_article, parent, false);
        v.findViewById(R.id.tvHeadline).setOnClickListener(this);
        return new MyViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Article article = articles.get(position);
        holder.tvHeadline.setTag(article.getUrl());
        if(article.getTitle() != null) holder.tvHeadline.setText(article.getTitle());
        if(article.getContent() != null) holder.tvContent.setText(article.getContent());
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }
}