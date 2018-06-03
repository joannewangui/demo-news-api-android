package wizag.com.kenyabuzz;

import android.content.Context;
import android.net.ConnectivityManager;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;
import wizag.com.kenyabuzz.AllArticlesModels.Articles;

public class AllArticlesActivity extends AppCompatActivity {
    SwipeRefreshLayout swipeRefreshLayout;
    CoordinatorLayout coordinatorLayout;
    AllArticlesAdapter adapter;
    ProgressBar progressBar;
    public List<Articles> sourceArticles = new ArrayList<>();
    private String BASE_URL = "http://newsapi.org/v2/";
    TextView author;
    public int page = 1;
    String sources;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_articles);

        sources = getIntent().getStringExtra("text");
        author = findViewById(R.id.author);
        author.setText(sources);

        progressBar = findViewById(R.id.progressBar);
        coordinatorLayout = findViewById(R.id.coordinatorLayout);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);


        adapter = new AllArticlesAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener(){
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (isLastItemDisplaying(recyclerView)){
                    getSources();

                }


            }
        });
        if (isNetworkConnected()){
            getSources();
        }

        else {
            Snackbar snackbar = Snackbar
                    .make(coordinatorLayout, "Not connected to Internet", Snackbar.LENGTH_INDEFINITE)
                    .setAction("RETRY", new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            getSources();
                        }
                    });

            snackbar.show();
        }

    }

    private void getSources() {
        progressBar.setVisibility(ProgressBar.VISIBLE);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        NewsAPI api = retrofit.create(NewsAPI.class);
        //Call<Box> sourcesCall = api.getSources(String.valueOf(page));
        //Call<Box> sourcesCall = api.getSources(String.valueOf(page),sources);
        Call<Box> sourcesCall = api.getSources(sources);

        sourcesCall.enqueue(new Callback<Box>() {
            @Override
            public void onResponse(Call<Box> call, Response<Box> response) {
               progressBar.setVisibility(ProgressBar.GONE);
                if (response.isSuccessful()){
                    if (response.body()!=null){

                        Box box = response.body();
                        sourceArticles = box.getArticles();
                        adapter.setNewsPosts(sourceArticles);
                        Log.d("page",String.valueOf(page));


                    }

                }

                else if (response.code()==500) {
                    getSources();
                }

                else {
                    Snackbar snackbar = Snackbar
                            .make(coordinatorLayout, " "+response.message(), Snackbar.LENGTH_INDEFINITE)
                            .setAction("RETRY", new View.OnClickListener() {

                                @Override
                                public void onClick(View v) {
                                    page = page - 1;
                                    getSources();
                                }
                            });

                    snackbar.show();
                }

              }

            @Override
            public void onFailure(Call<Box> call, Throwable t) {
                progressBar.setVisibility(ProgressBar.GONE);
                t.printStackTrace();
                Snackbar snackbar = Snackbar
                        .make(coordinatorLayout, " "+fetchErrorMessage(t), Snackbar.LENGTH_INDEFINITE)
                        .setAction("RETRY", new View.OnClickListener() {

                            @Override
                            public void onClick(View v) {
                                page = page - 1;
                                getSources();
                            }
                        });

                snackbar.show();

            }
        });

        page += 1;
    }

    private boolean isLastItemDisplaying(RecyclerView recyclerView) {
        if (recyclerView.getAdapter().getItemCount() != 0) {
            int lastVisibleItemPosition = ((LinearLayoutManager) recyclerView.getLayoutManager())
                    .findLastCompletelyVisibleItemPosition();

            if (lastVisibleItemPosition != RecyclerView.NO_POSITION && lastVisibleItemPosition ==
                    recyclerView.getAdapter().getItemCount()-1)
                return true;
        }
        return false;
    }

    private String fetchErrorMessage(Throwable throwable){
        String errorMsg = getResources().getString(R.string.error_msg_unknown);

        if (!isNetworkConnected()){
            errorMsg = getResources().getString(R.string.error_msg_no_internet);
        }
        else if (throwable instanceof TimeoutException) {
            errorMsg = getResources().getString(R.string.error_msg_timeout);
        }

        return errorMsg;
    }

    private boolean isNetworkConnected(){
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }


    interface NewsAPI{

        @GET("top-headlines?apiKey=3ee7b990a72b4101a2cecde4c6e5868b")
        Call<Box>getSources(
                @Query("sources")String sources
        );
    }
}
