package com.fluxcapacitor.core;

import com.fluxcapacitor.screens.viewDatabase.login.LoginController;
import javafx.application.Application;
import javafx.stage.Stage;
import org.datafx.controller.flow.Flow;
import org.datafx.controller.flow.FlowException;

/**
 * @author Eddie
 */
public class MainStart extends Application {

    @Override
    public void start(Stage primaryStage) throws FlowException {
        new Flow(LoginController.class).startInStage(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }

}

