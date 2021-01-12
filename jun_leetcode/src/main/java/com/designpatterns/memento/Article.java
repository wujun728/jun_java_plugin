package com.designpatterns.memento;

/**
 * @author BaoZhou
 * @date 2019/1/4
 */
public class Article {
    String name;
    String image;
    String url;

    public Article(String name, String image, String url) {
        this.name = name;
        this.image = image;
        this.url = url;
    }

    public ArticleMemento saveMemento() {
        ArticleMemento memento = new ArticleMemento(this);
        return memento;
    }

    public void undoFromMemento(ArticleMemento memento){
        this.name = memento.name;
        this.image = memento.image;
        this.url = memento.url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "Article{" +
                "name='" + name + '\'' +
                ", image='" + image + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
