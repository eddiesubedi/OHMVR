package com.fluxcapacitor.core;

import com.fluxcapacitor.screens.login.LoginController;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import javafx.stage.Stage;
import org.datafx.controller.flow.Flow;
import org.datafx.controller.flow.FlowException;

/**
 * @author Eddie
 */
public class MainStart extends Application {

    @Override
    public void start(Stage primaryStage) throws FlowException {
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();

        primaryStage.setX(bounds.getMinX());
        primaryStage.setY(bounds.getMinY());
        primaryStage.setWidth(bounds.getWidth());
        primaryStage.setHeight(bounds.getHeight());
        new Flow(LoginController.class).startInStage(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }

}

