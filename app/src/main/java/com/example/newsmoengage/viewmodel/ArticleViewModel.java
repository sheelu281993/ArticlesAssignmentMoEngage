package com.example.newsmoengage.viewmodel;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.newsmoengage.webservice.HttpGetRequest;
import com.example.newsmoengage.callbacks.OnResponseCallback;
import com.example.newsmoengage.models.Articles;
import com.google.gson.Gson;

import static com.example.newsmoengage.viewmodel.MyPrefs.PREFS_ARTICLE;

public class ArticleViewModel extends AndroidViewModel implements OnResponseCallback {

    private Context context = getApplication().getApplicationContext();

    private static final String API_URL = "https://candidate-test-data-moengage.s3.amazonaws.com/Android/news-api-feed/staticResponse.json";

    private MutableLiveData<Articles> articles = new MutableLiveData<>();

    public ArticleViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<Articles> getArticles(){
        if(articles.getValue() == null) {
            //check in cache offline storage
            String result = MyPrefs.getPreference(context, PREFS_ARTICLE);
            if(result != null && !result.isEmpty()){
                Gson gson = new Gson();
                Articles response = gson.fromJson(result, Articles.class);
                articles.setValue(response);
            }
            else {
                HttpGetRequest api = new HttpGetRequest();
                api.setResponseCallback(this);
                api.execute(API_URL);
            }
        }
        return articles;
    }

    @Override
    public void onSuccess(String result) {
          MyPrefs.setPreference(context, PREFS_ARTICLE, result);
          Gson gson = new Gson();
          Articles response = gson.fromJson(result, Articles.class);
          articles.setValue(response);
    }

    @Override
    public void onFailure(String errorMessage) {
        articles.setValue(null);
    }
}
