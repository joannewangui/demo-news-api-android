package wizag.com.kenyabuzz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

public class FullArticleActivity extends AppCompatActivity {

    ImageView full_image;
    TextView full_title, full_time, full_author, full_read;
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_article);

        String image = getIntent().getStringExtra("full_image");
        String title = getIntent().getStringExtra("full_title");
        String time = getIntent().getStringExtra("full_time");
        String author = getIntent().getStringExtra("full_author");
        String read = getIntent().getStringExtra("full_read");
        String url = getIntent().getStringExtra("full_story");

        full_image = findViewById(R.id.full_image);
        Glide.with(this)
                .load(image)
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        //TODO: handle failure
                        //holder.mProgress.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        //image ready, hide progress now
                        // holder.mProgress.setVisibility(View.GONE);
                        return false; //return false if you want Glide to handle everything else.
                    }
                })
                .diskCacheStrategy(DiskCacheStrategy.ALL) //cache both original and resized image
                .centerCrop()
                .crossFade()
                .into(full_image);

        full_title = findViewById(R.id.full_title);
        full_title.setText(title);
        full_time = findViewById(R.id.full_time);
        full_time.setText(DateTimeUtils.getElapsedTime(time));
        full_author = findViewById(R.id.full_author);
        full_author.setText(author);
        full_read = findViewById(R.id.full_read);
        full_read.setText(read);

        webView = findViewById(R.id.webView);
        //  webView.getSettings().setJavaScriptEnabled(true);

        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setDisplayZoomControls(true);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(url);

    }

    @Override
    protected void onStop() {
        super.onStop();
        getCacheDir().deleteOnExit();
        webView.clearCache(true);
    }
}
