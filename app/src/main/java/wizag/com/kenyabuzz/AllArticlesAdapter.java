package wizag.com.kenyabuzz;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.List;

import wizag.com.kenyabuzz.AllArticlesModels.Articles;

/**
 * Created by User on 03/06/2018.
 */

public class AllArticlesAdapter extends RecyclerView.Adapter<AllArticlesAdapter.NewsViewHolder>{

    private Context context;
    private List<Articles> sourcesPosts = null;
    //private List<SourceSource> sourcesPosts = null;

    public AllArticlesAdapter(Context context) {
        this.context = context;
    }

    public void setNewsPosts(List<Articles> sourcesPosts) {
        this.sourcesPosts = sourcesPosts;
        notifyDataSetChanged();
    }

    @Override
    public NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.news_row, parent, false);
        return new NewsViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final NewsViewHolder holder, int position) {
        final Articles article = sourcesPosts.get(position);
        //final SourceSource article = sourcesPosts.get(position);
        holder.news_title.setText(article.getDescription());
        holder.news_time.setText(DateTimeUtils.getElapsedTime(article.getPublishedAt()));
        Glide.with(context)
                .load(article.getUrlToImage())
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        //TODO: handle failure
                        holder.progressBar.setVisibility(View.VISIBLE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        //image ready, hide progress now
                         holder.progressBar.setVisibility(View.GONE);
                        return false; //return false if you want Glide to handle everything else.
                    }
                })
                .diskCacheStrategy(DiskCacheStrategy.ALL) //cache both original and resized image
                .centerCrop()
                .crossFade()
                .into(holder.news_image);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent full_article = new Intent(context,FullArticleActivity.class);
                full_article.putExtra("full_image",article.getUrlToImage());
                 full_article.putExtra("full_title",article.getTitle());
                 full_article.putExtra("full_time",article.getPublishedAt());
                 full_article.putExtra("full_author",article.getAuthor());
                full_article.putExtra("full_read",article.getDescription());
                full_article.putExtra("full_story",article.getUrl());
                context.startActivity(full_article);

            }
        });


    }

    @Override
    public int getItemCount() {
        if (sourcesPosts != null && !sourcesPosts.isEmpty()) {
            return sourcesPosts.size();
        }
        return 0;
    }

    class NewsViewHolder extends RecyclerView.ViewHolder {

        private TextView news_title;
        private ImageView news_image;
        private TextView news_time;
        private ProgressBar progressBar;

        public NewsViewHolder(View itemView) {
            super(itemView);
            news_title = itemView.findViewById(R.id.news_title);
            news_image = itemView.findViewById(R.id.news_image);
            news_time = itemView.findViewById(R.id.news_time);
            progressBar = itemView.findViewById(R.id.progressBar);

        }
    }
}
