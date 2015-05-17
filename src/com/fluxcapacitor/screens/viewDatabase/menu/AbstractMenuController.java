package com.fluxcapacitor.screens.viewDatabase.menu;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class AbstractMenuController {
    @FXML
    private Button view;

    @FXML
    private Button newDat;

    public Button getViewButton(){
        return view;
    }
    public Button getNewButton(){
        return view;
    }
}
