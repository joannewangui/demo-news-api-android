package wizag.com.kenyabuzz.AllArticlesModels;

/**
 * Created by User on 03/06/2018.
 */

public class Articles {
    private String author;
    private String title;
    private String description;
    private String url;
    private String publishedAt;
    private String urlToImage;
    private Source source;

    /**
     * Information about news article
     *
     * @param author      Author of the news
     * @param title       Title or headline of the news
     * @param description Description of the news
     * @param url         URL news published at
     * @param publishedAt Time of publication: format 2018-05-11T11:59:00Z in UTC(+000)
     */
    public Articles(String author, String title, String description, String url, String publishedAt, Source source, String urlToImage) {
        this.author = author;
        this.title = title;
        this.description = description;
        this.url = url;
        this.publishedAt = publishedAt;
        this.source = source;
        this.urlToImage = urlToImage;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    @Override
    public String toString() {
        return "NewsArticle{" +
                "author='" + author + '\'' +
                ", title='" + title + '\'' +
                '}';
    }

    public Source getSource() {
        return source;
    }

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getUrl() {
        return url;
    }

    public String getPublishedAt() {
        return publishedAt;
    }
}
