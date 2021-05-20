package tqlin.annotations;

import org.apache.commons.digester3.annotations.rules.BeanPropertySetter;
import org.apache.commons.digester3.annotations.rules.ObjectCreate;

@ObjectCreate(pattern = "rss/channel/image")
public class Image {

    @BeanPropertySetter(pattern = "rss/channel/image/description")
    private String description;

    @BeanPropertySetter(pattern = "rss/channel/image/width")
    private int width;

    @BeanPropertySetter(pattern = "rss/channel/image/height")
    private int height;

    @BeanPropertySetter(pattern = "rss/channel/image/link")
    private String link;

    @BeanPropertySetter(pattern = "rss/channel/image/title")
    private String title;

    @BeanPropertySetter(pattern = "rss/channel/image/url")
    private String url;

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the width
     */
    public int getWidth() {
        return width;
    }

    /**
     * @param width the width to set
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * @return the height
     */
    public int getHeight() {
        return height;
    }

    /**
     * @param height the height to set
     */
    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * @return the link
     */
    public String getLink() {
        return link;
    }

    /**
     * @param link the link to set
     */
    public void setLink(String link) {
        this.link = link;
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url the url to set
     */
    public void setUrl(String url) {
        this.url = url;
    }

    // getters and setters

}