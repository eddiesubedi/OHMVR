package com.fluxcapacitor.screens.View_Database.MainMenu;

import com.fluxcapacitor.core.util.Constants;
import com.fluxcapacitor.core.util.Inject.InformationView;
import com.fluxcapacitor.screens.View_Database.DetailedView.DetailedViewController;
import com.fluxcapacitor.screens.View_Database.YearView.YearViewController;
import com.fluxcapacitor.screens.MenuBar.AbstractMenuController;
import eu.schudt.javafx.controls.calendar.DatePicker;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.TextFlow;
import org.datafx.controller.FXMLController;
import org.datafx.controller.flow.FlowException;
import org.datafx.controller.flow.action.ActionMethod;
import org.datafx.controller.flow.action.ActionTrigger;
import org.datafx.controller.flow.context.ActionHandler;
import org.datafx.controller.flow.context.FlowActionHandler;
import org.datafx.controller.util.VetoException;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.net.URL;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

@FXMLController("ViewFXML.fxml")
public class MainMenuController extends AbstractMenuController{
    @FXML
    private TextFlow textFlow;

    @Inject
    private InformationView data;

    @FXML
    private AnchorPane leftPane, rightPane;

    @FXML
    @ActionTrigger("ViewDataRange")
    private Button viewData;
    
    @FXML
    @ActionTrigger("ViewDataSpec")
    private Button ViewSpecBtn;
    
    @FXML
    private HBox dateBox;

    @FXML
    private ComboBox fromCB,toCB,dataTypeCB,dataTypeSpecCB;

    @FXML
    private RadioButton calRadio, fisRadio;

    @ActionHandler
    private FlowActionHandler actionHandler;
    private DatePicker datePicker;

    @PostConstruct
    public void init() {
        getViewBtn().getStylesheets().add("/com/fluxcapacitor/screens/MenuBar/MainMenuCSS.css");
        getCreateBtn().getStylesheets().add("/com/fluxcapacitor/screens/MenuBar/MainMenuCSS.css");
        getInputBtn().getStylesheets().add("/com/fluxcapacitor/screens/MenuBar/MainMenuCSS.css");
        getParkBtn().getStylesheets().add("/com/fluxcapacitor/screens/MenuBar/MainMenuCSS.css");
        getUserBtn().getStylesheets().add("/com/fluxcapacitor/screens/MenuBar/MainMenuCSS.css");

        DropShadow dropShadowTextFlow = new DropShadow();
        dropShadowTextFlow.setOffsetY(2);
        dropShadowTextFlow.setColor(Color.web("#e9e9e9"));
        dropShadowTextFlow.setSpread(.5);

        textFlow.setEffect(dropShadowTextFlow);

        DropShadow dropShadowLeft = new DropShadow();
        dropShadowLeft.setColor(Color.web("#e9e9e9"));
        dropShadowLeft.setSpread(.5);

        leftPane.setEffect(dropShadowLeft);
        rightPane.setEffect(dropShadowLeft);

        datePicker = new DatePicker(Locale.ENGLISH);

        datePicker.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
        datePicker.getCalendarView().todayButtonTextProperty().set("Today");
        datePicker.getCalendarView().setShowWeeks(false);

        URL url = this.getClass().getResource("DatePicker.css");
        String css = url.toExternalForm();
        datePicker.getStylesheets().add(css);
        dateBox.getChildren().add(datePicker);

        for(int i = 1980;i<=2015;i++){
            fromCB.getItems().add(i);
            toCB.getItems().add(i);
        }

        fromCB.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                toCB.getItems().clear();
                for (int i = (int) fromCB.getItems().get((int) newValue); i <= 2015; i++) {
                    toCB.getItems().add(i);
                }
            }
        });

        dataTypeCB.getItems().addAll(Constants.dataNames);
        dataTypeSpecCB.getItems().addAll(Constants.dataNames);
    }
    @ActionMethod("ViewDataRange")
    public void viewActionRange() throws VetoException, FlowException {
        data.clearviewDatRange();
        ArrayList<String> datas = new ArrayList<>();
        datas.add(fromCB.getSelectionModel().getSelectedItem()+"");
        datas.add(toCB.getSelectionModel().getSelectedItem() + "");
        if(calRadio.isSelected()){
            datas.add("Calander");
        }else if(fisRadio.isSelected()){
            datas.add("Fiscal");
        }else{
            datas.add("null");
        }
        if(dataTypeCB.getSelectionModel().getSelectedIndex()==-1){
            datas.add("null");
        }else {
            datas.add(Constants.dataNames[dataTypeCB.getSelectionModel().getSelectedIndex()] + "");
        }
        if(!datas.contains("null")){
            data.setViewDatRange(datas);
            System.out.println(data.getViewDatRange());
            actionHandler.navigate(YearViewController.class);
        }
    }
    
    @ActionMethod("ViewDataSpec")
    public void viewActionSpec(){
        if(!datePicker.invalidProperty().getValue()){
            try{
                Calendar cal = Calendar.getInstance();
                cal.setTime(datePicker.getSelectedDate());
                data.setSelectedYear(cal.get(Calendar.YEAR) + "");
                data.setSelectedMonth(new DateFormatSymbols().getMonths()[cal.get(Calendar.MONTH)] + "");

                data.setSelectedDay(cal.get(Calendar.DATE) + "");
                data.setSelectedPark(Constants.dataNames[dataTypeSpecCB.getSelectionModel().getSelectedIndex()]);
                actionHandler.navigate(DetailedViewController.class);
            }catch (Exception e){

            }

        }

    }
}