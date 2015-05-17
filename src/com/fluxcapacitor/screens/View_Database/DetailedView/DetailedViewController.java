package com.fluxcapacitor.screens.View_Database.DetailedView;

import com.fluxcapacitor.core.util.Information;
import com.fluxcapacitor.screens.MenuBar.AbstractMenuController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import org.datafx.controller.FXMLController;
import org.datafx.controller.flow.action.BackAction;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

/**
 * Created by Eddie on 5/17/2015.
 */
@FXMLController("DetailedViewFXML.fxml")
public class DetailedViewController extends AbstractMenuController {
    @Inject
    private Information data;

    @FXML
    @BackAction
    private Button backBTN;

    @FXML
    private Text headerText;
    @PostConstruct
    public void init(){
        getViewBtn().getStylesheets().add("/com/fluxcapacitor/screens/MenuBar/MainMenuCSS.css");
        getCreateBtn().getStylesheets().add("/com/fluxcapacitor/screens/MenuBar/MainMenuCSS.css");
        getInputBtn().getStylesheets().add("/com/fluxcapacitor/screens/MenuBar/MainMenuCSS.css");
        getParkBtn().getStylesheets().add("/com/fluxcapacitor/screens/MenuBar/MainMenuCSS.css");
        getUserBtn().getStylesheets().add("/com/fluxcapacitor/screens/MenuBar/MainMenuCSS.css");

        System.out.println(data.getSelectedPark()+" "+data.getSelectedYear()+" "+data.getSelectedMonth()+" "+data.getSelectedDay());
        String header = "449 of "+data.getSelectedPark()+" on "+data.getSelectedMonth()+" "+data.getSelectedDay()+", "+data.getSelectedYear();
        headerText.setText(header);
    }
}
