package com.designpatterns.memento;

import org.junit.jupiter.api.Test;

/**
 * 备忘录模式
 * @author BaoZhou
 * @date 2019/1/4
 */
public class MementoTest {

    @Test
    void test() {
        Article article = new Article("A", "A", "A");
        ArticalMementoManager manager = new ArticalMementoManager();
        manager.addMemento(article.saveMemento());
        System.out.println(article);
        article = new Article("B", "B", "B");
        System.out.println(article);
        ArticleMemento memento = manager.getMemento();
        article.undoFromMemento(memento);
        System.out.println(article);
    }
}
