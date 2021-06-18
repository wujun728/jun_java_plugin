package tqlin.annotations;

import org.apache.commons.digester3.annotations.rules.BeanPropertySetter;
import org.apache.commons.digester3.annotations.rules.ObjectCreate;

@ObjectCreate(pattern = "rss/channel/item")
public class Item {

    @BeanPropertySetter(pattern = "rss/channel/item/description")
    private String description;

    @BeanPropertySetter(pattern = "rss/channel/item/link")
    private String link;

    @BeanPropertySetter(pattern = "rss/channel/item/title")
    private String title;

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
}