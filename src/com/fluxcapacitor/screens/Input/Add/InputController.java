package com.fluxcapacitor.screens.Input.Add;

import com.fluxcapacitor.core.util.Inject.InformationInput;
import com.fluxcapacitor.screens.MenuBar.AbstractMenuController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.text.Text;
import org.datafx.controller.FXMLController;
import org.datafx.controller.flow.action.BackAction;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

/**
 * Created by Eddie on 5/17/2015.
 */
@FXMLController("InputFXML.fxml")
public class InputController extends AbstractMenuController{
    @Inject
    private InformationInput data;

    @FXML
    @BackAction
    private Button backBtn;

    @FXML
    private Text headerText;
    @PostConstruct
    public void init(){
        String header = "Input Data for "+data.getSelectedPark()+" on "+data.getSelectedMonth()+" "+data.getSelectedDay()+", "+data.getSelectedYear();
        headerText.setText(header);

        DatePicker datePicker = new DatePicker();

        getViewBtn().getStylesheets().add("/com/fluxcapacitor/screens/MenuBar/MainMenuCSS.css");
        getCreateBtn().getStylesheets().add("/com/fluxcapacitor/screens/MenuBar/MainMenuCSS.css");
        getInputBtn().getStylesheets().add("/com/fluxcapacitor/screens/MenuBar/MainMenuCSS.css");
        getParkBtn().getStylesheets().add("/com/fluxcapacitor/screens/MenuBar/MainMenuCSS.css");
        getUserBtn().getStylesheets().add("/com/fluxcapacitor/screens/MenuBar/MainMenuCSS.css");
    }
}
