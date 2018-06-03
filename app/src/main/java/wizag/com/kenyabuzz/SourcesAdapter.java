package wizag.com.kenyabuzz;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import wizag.com.kenyabuzz.SourceModels.*;

/**
 * Created by User on 03/06/2018.
 */

public class SourcesAdapter extends RecyclerView.Adapter<SourcesAdapter.SourcesViewHolder>{

    private Context context;
    private List<ResultSource> sourcesPosts = null;

    public SourcesAdapter(Context context) {
        this.context = context;
    }

    public void setNewsPosts(List<ResultSource> sourcesPosts) {
        this.sourcesPosts = sourcesPosts;
        notifyDataSetChanged();
    }

    @Override
    public SourcesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.sources_row, parent, false);
        return new SourcesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SourcesViewHolder holder, int position) {
        final ResultSource article = sourcesPosts.get(position);
        holder.source.setText(article.getName());
        switch (article.getName()){
            case "Ars Technica":
                holder.source_image.setImageResource(R.drawable.ars);
                break;

            case "Crypto Coins News":
                holder.source_image.setImageResource(R.drawable.cryptocoinsnews);
                break;

            case "Engadget":
                holder.source_image.setImageResource(R.drawable.engadget);
                break;

            case "Gruenderszene":
                holder.source_image.setImageResource(R.drawable.gruend);
                break;

            case "Hacker News":
                holder.source_image.setImageResource(R.drawable.hacker);
                break;

            case "Recode":
                holder.source_image.setImageResource(R.drawable.redcode);
                break;

            case "T3n":
                holder.source_image.setImageResource(R.drawable.t3n);
                break;

            case "TechCrunch":
                holder.source_image.setImageResource(R.drawable.tech_crunch);
                break;

            case "TechCrunch (CN)":
                holder.source_image.setImageResource(R.drawable.tech_crunch);
                break;

            case "TechRadar":
                holder.source_image.setImageResource(R.drawable.techradar);
                break;

            case "The Next Web":
                holder.source_image.setImageResource(R.drawable.thenextweb);
                break;

            case "The Verge":
                holder.source_image.setImageResource(R.drawable.verge);
                break;

            case "Wired":
                holder.source_image.setImageResource(R.drawable.wired);
                break;

            case "Wired.de":
                holder.source_image.setImageResource(R.drawable.wired);
                break;

                default:
                    holder.source_image.setImageResource(R.drawable.newspaper_icon);
                    break;
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent all_articles = new Intent(context,AllArticlesActivity.class);
                all_articles.putExtra("text",article.getName());
                context.startActivity(all_articles);
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

    class SourcesViewHolder extends RecyclerView.ViewHolder {

        private TextView source;
        private ImageView source_image;

        public SourcesViewHolder(View itemView) {
            super(itemView);
            source = itemView.findViewById(R.id.source);
            source_image = itemView.findViewById(R.id.source_image);
        }
    }
}
