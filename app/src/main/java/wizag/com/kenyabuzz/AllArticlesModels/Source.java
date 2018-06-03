package wizag.com.kenyabuzz.AllArticlesModels;

/**
 * Created by User on 03/06/2018.
 */

public class Source {
    private String id;
    private String name;

    /**
     * NewsSource of the news
     *
     * @param id   id of the news source, example cnn
     * @param name display name of news source Example CNN
     */
    public Source(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }
}
