package wizag.com.kenyabuzz.AllArticlesModels;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 03/06/2018.
 */

public class Box {
    private String status;

    @Override
    public String toString() {
        return "Box{" +
                "status='" + status + '\'' +
                ", totalResults=" + totalResults +
                ", articles=" + articles +
                '}';
    }

    private int totalResults;
    private List<Articles> articles = new ArrayList<>();

    public String getStatus() {
        return status;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public List<Articles> getArticles() {
        return articles;
    }
}


