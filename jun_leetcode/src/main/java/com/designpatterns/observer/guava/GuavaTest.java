package com.designpatterns.observer.guava;

import com.google.common.eventbus.EventBus;
import org.junit.jupiter.api.Test;

/**
 * @author BaoZhou
 * @date 2019/1/2
 */
public class GuavaTest {
    @Test
    public void test() {
        EventBus eventBus = new EventBus();
        GuavaEvent event = new GuavaEvent();
        event.setName("数学");
        GuavaEvent1 event1 = new GuavaEvent1();
        event1.setName("语文");
        eventBus.register(event);
        eventBus.register(event1);
        Question question = new Question();
        question.setContent("这个汉堡怎么吃");
        question.setUserName("BaoZhou");
        eventBus.post(question);
    }
}
