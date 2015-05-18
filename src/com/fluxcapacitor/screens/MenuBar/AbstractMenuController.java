package com.fluxcapacitor.screens.MenuBar;

import com.fluxcapacitor.screens.Graph.MainMenuG.GraphMainMenuController;
import com.fluxcapacitor.screens.Input.MainMenu.InputMainMenuController;
import com.fluxcapacitor.screens.View_Database.MainMenu.MainMenuController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import org.datafx.controller.flow.action.LinkAction;

public class AbstractMenuController {
    @FXML
    @LinkAction(MainMenuController.class)
    private Button viewBtn;
    @FXML
    @LinkAction(InputMainMenuController.class)
    private Button inputBtn;
    @FXML
    @LinkAction(GraphMainMenuController.class)
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
