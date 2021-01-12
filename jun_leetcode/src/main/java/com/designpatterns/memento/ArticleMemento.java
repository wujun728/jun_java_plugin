package com.designpatterns.memento;

/**
 * @author BaoZhou
 * @date 2019/1/4
 */
public class ArticleMemento {
    String name;
    String image;
    String url;

    public ArticleMemento(Article article) {
        this.name = article.getName();
        this.image = article.getImage();
        this.url = article.getUrl();
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public String getUrl() {
        return url;
    }
}
