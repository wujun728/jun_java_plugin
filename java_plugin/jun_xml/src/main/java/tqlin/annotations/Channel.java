package tqlin.annotations;

import org.apache.commons.digester3.annotations.rules.BeanPropertySetter;
import org.apache.commons.digester3.annotations.rules.ObjectCreate;
import org.apache.commons.digester3.annotations.rules.SetNext;

import java.util.ArrayList;
import java.util.List;

@ObjectCreate(pattern = "rss/channel")
public class Channel {

    private final List<Item> items = new ArrayList<Item>();

    private Image image;

    @BeanPropertySetter(pattern = "rss/channel/title")
    private String title;

    @BeanPropertySetter(pattern = "rss/channel/link")
    private String link;

    @BeanPropertySetter(pattern = "rss/channel/description")
    private String description;

    @BeanPropertySetter(pattern = "rss/channel/language")
    private String language;


    @SetNext
    public void setImage(Image image) {
        this.image = image;
    }

    @SetNext
    public void addItem(Item item) {
        this.items.add(item);
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
     * @return the language
     */
    public String getLanguage() {
        return language;
    }

    /**
     * @param language the language to set
     */
    public void setLanguage(String language) {
        this.language = language;
    }

    /**
     * @return the items
     */
    public List<Item> getItems() {
        return items;
    }

    /**
     * @return the image
     */
    public Image getImage() {
        return image;
    }

}