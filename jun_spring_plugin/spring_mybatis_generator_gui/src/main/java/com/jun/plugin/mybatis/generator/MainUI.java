package com.jun.plugin.mybatis.generator;

import java.net.URL;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jun.plugin.mybatis.generator.controller.MainUIController;
import com.jun.plugin.mybatis.generator.model.GeneratorConfig;
import com.jun.plugin.mybatis.generator.util.ConfigHelper;
import com.jun.plugin.mybatis.generator.view.AlertUtil;

/**
 * 这是本软件的主入口,要运行本软件请直接运行本类就可以了,不用传入任何参数
 */
public class MainUI extends Application {

    private static final Logger _LOG = LoggerFactory.getLogger(MainUI.class);

    @Override
    public void start(Stage primaryStage) throws Exception {
        ConfigHelper.createEmptyFiles();
        URL url = Thread.currentThread().getContextClassLoader().getResource("fxml/MainUI.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(url);
        Parent root = fxmlLoader.load();
        primaryStage.setResizable(true);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

        MainUIController controller = fxmlLoader.getController();
        controller.setPrimaryStage(primaryStage);
    }


    public static void main(String[] args) {
        launch(args);
    }

}
