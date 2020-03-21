package com.example.newsmoengage.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import com.example.newsmoengage.adapter.ArticleAdapter;
import com.example.newsmoengage.viewmodel.ArticleViewModel;
import com.example.newsmoengage.callbacks.OnAdapterResponded;
import com.example.newsmoengage.R;
import com.example.newsmoengage.models.Articles;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements OnAdapterResponded {

    private RecyclerView articleRv;

    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        articleRv = findViewById(R.id.rvArticles);
        progressBar = findViewById(R.id.progressBar);

        ArticleViewModel viewModel = ViewModelProviders.of(this).get(ArticleViewModel.class);

        progressBar.setVisibility(View.VISIBLE);
        viewModel.getArticles().observe(this, new Observer<Articles>() {
            @Override
            public void onChanged(Articles articles) {
                progressBar.setVisibility(View.GONE);
                if(articles != null && articles.getArticles() != null && !articles.getArticles().isEmpty()) updateUI(articles);
                else Toast.makeText(MainActivity.this, "Articles not available", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void  updateUI(Articles articles){
        ArticleAdapter adapter = new ArticleAdapter(articles.getArticles(), this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        articleRv.setLayoutManager(layoutManager);
        articleRv.setAdapter(adapter);
    }
    @Override
    public void onAdapterResponse(String data) {
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(data));
        startActivity(i);
    }
}
