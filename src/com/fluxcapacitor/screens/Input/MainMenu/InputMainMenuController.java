package com.fluxcapacitor.screens.Input.MainMenu;

import com.fluxcapacitor.core.util.Constants;
import com.fluxcapacitor.core.util.Inject.InformationInput;
import com.fluxcapacitor.screens.Input.Add.InputController;
import com.fluxcapacitor.screens.MenuBar.AbstractMenuController;
import eu.schudt.javafx.controls.calendar.DatePicker;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.TextFlow;
import org.datafx.controller.FXMLController;
import org.datafx.controller.flow.action.ActionMethod;
import org.datafx.controller.flow.action.ActionTrigger;
import org.datafx.controller.flow.context.ActionHandler;
import org.datafx.controller.flow.context.FlowActionHandler;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.net.URL;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by Eddie on 5/17/2015.
 */
@FXMLController("InputFXML.fxml")
public class InputMainMenuController extends AbstractMenuController{
    @FXML
    private TextFlow textFlow;

    @FXML
    private HBox dateBox;
    @FXML
    private ComboBox dataTypeSpecCB;
    private DatePicker datePicker;

    @FXML
    @ActionTrigger("inputBtn")
    private Button input;

    @Inject
    InformationInput data;

    @ActionHandler
    private FlowActionHandler actionHandler;

    @PostConstruct
    public void init(){
        DropShadow dropShadowTextFlow = new DropShadow();
        dropShadowTextFlow.setOffsetY(2);
        dropShadowTextFlow.setColor(Color.web("#e9e9e9"));
        dropShadowTextFlow.setSpread(.5);

        textFlow.setEffect(dropShadowTextFlow);

        datePicker = new DatePicker(Locale.ENGLISH);

        datePicker.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
        datePicker.getCalendarView().todayButtonTextProperty().set("Today");
        datePicker.getCalendarView().setShowWeeks(false);

        URL url = this.getClass().getResource("/com/fluxcapacitor/screens/View_Database/MainMenu/DatePicker.css");
        String css = url.toExternalForm();
        datePicker.getStylesheets().add(css);
        dateBox.getChildren().add(datePicker);


        dataTypeSpecCB.getItems().addAll(Constants.dataNames);

        getViewBtn().getStylesheets().add("/com/fluxcapacitor/screens/MenuBar/MainMenuCSS.css");
        getCreateBtn().getStylesheets().add("/com/fluxcapacitor/screens/MenuBar/MainMenuCSS.css");
        getInputBtn().getStylesheets().add("/com/fluxcapacitor/screens/MenuBar/MainMenuCSS.css");
        getParkBtn().getStylesheets().add("/com/fluxcapacitor/screens/MenuBar/MainMenuCSS.css");
        getUserBtn().getStylesheets().add("/com/fluxcapacitor/screens/MenuBar/MainMenuCSS.css");
    }

    @ActionMethod("inputBtn")
    public void inputHandler(){
        try {
            if(!datePicker.invalidProperty().getValue()){
                try{
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(datePicker.getSelectedDate());
                    data.setSelectedYear(cal.get(Calendar.YEAR) + "");
                    data.setSelectedMonth(new DateFormatSymbols().getMonths()[cal.get(Calendar.MONTH)] + "");

                    data.setSelectedDay(cal.get(Calendar.DATE) + "");
                    data.setSelectedPark(Constants.dataNames[dataTypeSpecCB.getSelectionModel().getSelectedIndex()]);
                    actionHandler.navigate(InputController.class);
                }catch (Exception e){

                }

            }
        }catch (Exception e){

        }
    }
}
