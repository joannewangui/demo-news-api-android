package wizag.com.kenyabuzz;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import wizag.com.kenyabuzz.SourceModels.Result;
import wizag.com.kenyabuzz.SourceModels.ResultSource;

public class MainActivity extends AppCompatActivity {
    CoordinatorLayout coordinatorLayout;
    SourcesAdapter adapter;
    private List<ResultSource> sourceArticles = new ArrayList<>();
    private String BASE_URL = "http://newsapi.org/v2/";
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        coordinatorLayout = findViewById(R.id.coordinatorLayout);
        progressBar = findViewById(R.id.progressBar);

        //Setting up LayoutManager and RecyclerView
        //LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);


        adapter = new SourcesAdapter(this);
        recyclerView.setAdapter(adapter);
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

        SourcesAPI api = retrofit.create(SourcesAPI.class);
        Call<Result> sourcesCall = api.getSources();
        sourcesCall.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                progressBar.setVisibility(ProgressBar.GONE);
                if (response.isSuccessful()){
                    if (response.body()!=null){

                        Result result = response.body();
                        sourceArticles = result.getSources();
                        adapter.setNewsPosts(sourceArticles);


                       }

                       else if (response.code()==500){
                        Snackbar snackbar = Snackbar
                                .make(coordinatorLayout, " "+response.message(), Snackbar.LENGTH_INDEFINITE)
                                .setAction("RETRY", new View.OnClickListener() {

                                    @Override
                                    public void onClick(View v) {
                                        // page = page - 1;
                                        getSources();
                                    }
                                });

                        snackbar.show();
                    }

                    else {
                              Snackbar snackbar = Snackbar
                                .make(coordinatorLayout, "Something went wrong", Snackbar.LENGTH_INDEFINITE)
                                .setAction("RETRY", new View.OnClickListener() {

                                    @Override
                                    public void onClick(View v) {
                                        // page = page - 1;
                                        getSources();
                                    }
                                });

                        snackbar.show();
                    }

                    }

            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                progressBar.setVisibility(ProgressBar.GONE);
                t.printStackTrace();
                Snackbar snackbar = Snackbar
                        .make(coordinatorLayout, " "+fetchErrorMessage(t), Snackbar.LENGTH_INDEFINITE)
                        .setAction("RETRY", new View.OnClickListener() {

                            @Override
                            public void onClick(View v) {
                               // page = page - 1;
                                getSources();
                            }
                        });

                snackbar.show();
            }
        });
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




    interface SourcesAPI{
       @GET("sources?category=technology&apiKey=3ee7b990a72b4101a2cecde4c6e5868b")
        Call<Result>getSources();
    }


}
