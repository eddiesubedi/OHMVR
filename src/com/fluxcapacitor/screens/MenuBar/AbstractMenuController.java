package com.fluxcapacitor.screens.MenuBar;

import com.fluxcapacitor.screens.View_Database.MainMenu.MainMenuController;
import com.fluxcapacitor.screens.login.LoginController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import org.datafx.controller.flow.action.ActionMethod;
import org.datafx.controller.flow.action.ActionTrigger;
import org.datafx.controller.flow.action.LinkAction;

public class AbstractMenuController {
    @FXML
    @LinkAction(MainMenuController.class)
    private Button viewBtn;
    @FXML
    private Button inputBtn;
    @FXML
    private Button createBtn;
    @FXML
    private Button userBtn;
    @FXML
    private Button parkBtn;

    public Button getViewBtn() {
        return viewBtn;
    }

    public Button getInputBtn() {
        return inputBtn;
    }

    public Button getCreateBtn() {
        return createBtn;
    }

    public Button getUserBtn() {
        return userBtn;
    }

    public Button getParkBtn() {
        return parkBtn;
    }
}
