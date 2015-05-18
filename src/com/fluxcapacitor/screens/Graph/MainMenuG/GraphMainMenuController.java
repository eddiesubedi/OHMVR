package com.fluxcapacitor.screens.Graph.MainMenuG;

import com.fluxcapacitor.core.util.Constants;
import com.fluxcapacitor.core.util.Inject.InformationGraph;
import com.fluxcapacitor.screens.MenuBar.AbstractMenuController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import org.controlsfx.control.CheckComboBox;
import org.datafx.controller.FXMLController;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Eddie on 5/17/2015.
 */
@FXMLController("MainMenuFXML.fxml")
public class GraphMainMenuController extends AbstractMenuController {
    @FXML
    private VBox YearVBox, monthVBox, dayVBox;

    @FXML
    private ComboBox fromCBYear, toCBYear, graphCBYear, chooseYearCBMonth,
            fromCBMonth, toCBMonth, graphCBMonth, chooseYearCBDay, chooseMonthCBDay,
            fromCBDay, toCBDay, graphCBDay;


    private Button buttonYear;

    private Button buttonMonth;

    private Button buttonDay;

    @Inject
    InformationGraph datas;
    private CheckComboBox parksYear;
    private CheckComboBox parksMonth;
    private CheckComboBox parksDay;

    @PostConstruct
    public void init() {
        getViewBtn().getStylesheets().add("/com/fluxcapacitor/screens/MenuBar/MainMenuCSS.css");
        getCreateBtn().getStylesheets().add("/com/fluxcapacitor/screens/MenuBar/MainMenuCSS.css");
        getInputBtn().getStylesheets().add("/com/fluxcapacitor/screens/MenuBar/MainMenuCSS.css");
        getParkBtn().getStylesheets().add("/com/fluxcapacitor/screens/MenuBar/MainMenuCSS.css");
        getUserBtn().getStylesheets().add("/com/fluxcapacitor/screens/MenuBar/MainMenuCSS.css");

        ArrayList<String> temps = new ArrayList<String>(Arrays.asList(Constants.parkName));
        temps.remove(0);

        buttonYear = new Button("Create Graph");
        buttonYear.setPrefWidth(178);
        buttonYear.setPrefHeight(40);

        Region regionYear = new Region();
        regionYear.setPrefHeight(33);

        parksYear = new CheckComboBox();
        parksYear.getItems().addAll(temps);
        YearVBox.getChildren().addAll(parksYear, regionYear, buttonYear);

        ///
        buttonMonth = new Button("Create Graph");
        buttonMonth.setPrefWidth(178);
        buttonMonth.setPrefHeight(40);

        Region regionMonth = new Region();
        regionYear.setPrefHeight(33);

        parksMonth = new CheckComboBox();
        parksMonth.getItems().addAll(temps);
        monthVBox.getChildren().addAll(parksMonth, regionMonth, buttonMonth);

        //
        buttonDay = new Button("Create Graph");
        buttonDay.setPrefWidth(178);
        buttonDay.setPrefHeight(40);

        Region regionDay = new Region();
        regionDay.setPrefHeight(33);

        parksDay = new CheckComboBox();
        parksDay.getItems().addAll(temps);
        dayVBox.getChildren().addAll(parksDay, regionDay, buttonDay);

        setUpBoxes();
        setUpHandlers();
    }

    private void setUpBoxes() {
        ArrayList<String> years = new ArrayList<>();
        for (int i = 1980; i <= 2015; i++) {
            years.add(i + "");
        }
        ArrayList<String> days = new ArrayList<>();
        for (int i = 1; i <= 31; i++) {
            days.add(i + "");
        }

        fromCBYear.getItems().addAll(years);
        toCBYear.getItems().addAll(years);
        graphCBYear.getItems().addAll(Constants.graphTypes);

        chooseYearCBMonth.getItems().addAll(years);
        fromCBMonth.getItems().addAll(Constants.months);
        toCBMonth.getItems().addAll(Constants.months);
        graphCBMonth.getItems().addAll(Constants.graphTypes);

        chooseYearCBDay.getItems().addAll(years);
        chooseMonthCBDay.getItems().addAll(Constants.months);
        fromCBDay.getItems().addAll(days);
        toCBDay.getItems().addAll(days);
        graphCBDay.getItems().addAll(Constants.graphTypes);
    }

    private void setUpHandlers() {
        buttonYear.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            try {
                String[] temp = new String[4];
                temp[0] = fromCBYear.getSelectionModel().getSelectedItem() + "";
                temp[1] = toCBYear.getSelectionModel().getSelectedItem() + "";
                temp[2] = graphCBYear.getSelectionModel().getSelectedItem() + "";
                temp[3] = parksYear.getCheckModel().getCheckedIndices().toString();

                if (!(Arrays.asList(temp).contains("") || Arrays.asList(temp).contains("[]"))) {
                    datas.setYearDatas(temp);
                }
            } catch (Exception e) {

            }
        });

        buttonMonth.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            try {
                String[] temp = new String[5];
                temp[0] = chooseYearCBMonth.getSelectionModel().getSelectedItem() + "";
                temp[1] = fromCBMonth.getSelectionModel().getSelectedItem() + "";
                temp[2] = toCBMonth.getSelectionModel().getSelectedItem() + "";
                temp[3] = graphCBMonth.getSelectionModel().getSelectedItem() + "";
                temp[4] = parksMonth.getCheckModel().getCheckedIndices().toString();

                if (!(Arrays.asList(temp).contains("") || Arrays.asList(temp).contains("null"))) {
                    datas.setMonthDatas(temp);
                }
            } catch (Exception e) {

            }
        });

        buttonDay.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            try {
                String[] temp = new String[6];
                temp[0] = chooseYearCBDay.getSelectionModel().getSelectedItem() + "";
                temp[1] = chooseMonthCBDay.getSelectionModel().getSelectedItem() + "";
                temp[2] = fromCBDay.getSelectionModel().getSelectedItem() + "";
                temp[3] = toCBDay.getSelectionModel().getSelectedItem() + "";
                temp[4] = graphCBDay.getSelectionModel().getSelectedItem() + "";
                temp[5] = parksDay.getCheckModel().getCheckedIndices().toString();

                if (!(Arrays.asList(temp).contains("") || Arrays.asList(temp).contains("null"))) {
                    datas.setDayDatas(temp);
                }
            } catch (Exception e) {

            }
        });
    }
}
