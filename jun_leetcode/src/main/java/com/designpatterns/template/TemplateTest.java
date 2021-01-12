package com.designpatterns.template;

import org.junit.jupiter.api.Test;

/**
 * @author BaoZhou
 * @date 2019/1/2
 */
public class TemplateTest {
    @Test
    void test() {
        Game game = new ComputerGame();
        game.play();
    }

}
