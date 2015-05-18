package com.fluxcapacitor.screens.Graph.MainMenuG;

import com.fluxcapacitor.core.util.Inject.InformationConstants;
import com.fluxcapacitor.core.util.Inject.InformationGraph;
import com.fluxcapacitor.screens.Graph.AreaGraph.AreaGraphController;
import com.fluxcapacitor.screens.Graph.BarChart.BarGraphController;
import com.fluxcapacitor.screens.Graph.LineGraph.LineGraphController;
import com.fluxcapacitor.screens.Graph.PieChart.PieChartGraphController;
import com.fluxcapacitor.screens.Graph.ScatterGraph.ScatterGraphController;
import com.fluxcapacitor.screens.MenuBar.AbstractMenuController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import org.controlsfx.control.CheckComboBox;
import org.datafx.controller.FXMLController;
import org.datafx.controller.flow.context.ActionHandler;
import org.datafx.controller.flow.context.FlowActionHandler;

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
    private InformationGraph datas;

    @Inject
    private InformationConstants constants;

    @ActionHandler
    private FlowActionHandler actionHandler;

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

        ArrayList<String> temps = new ArrayList<String>(Arrays.asList(constants.getParkName()));
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
        graphCBYear.getItems().addAll(constants.graphTypes);

        chooseYearCBMonth.getItems().addAll(years);
        fromCBMonth.getItems().addAll(constants.months);
        toCBMonth.getItems().addAll(constants.months);
        graphCBMonth.getItems().addAll(constants.graphTypes);

        chooseYearCBDay.getItems().addAll(years);
        chooseMonthCBDay.getItems().addAll(constants.months);
        fromCBDay.getItems().addAll(days);
        toCBDay.getItems().addAll(days);
        graphCBDay.getItems().addAll(constants.graphTypes);
    }

    private void setUpHandlers() {
        buttonYear.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            try {
                String[] temp = new String[4];
                temp[0] = fromCBYear.getSelectionModel().getSelectedItem() + "";
                temp[1] = toCBYear.getSelectionModel().getSelectedItem() + "";
                temp[2] = graphCBYear.getSelectionModel().getSelectedItem() + "";
                temp[3] = parksYear.getCheckModel().getCheckedIndices().toString();

                if (!(Arrays.asList(temp).contains("") || Arrays.asList(temp).contains("[]") || Arrays.asList(temp).contains("null"))) {
                    datas.setYearDatas(temp);
                    switch (temp[2]){
                        case "Pie Chart":
                            actionHandler.navigate(PieChartGraphController.class);
                        break;
                        case "Line Graph":
                            actionHandler.navigate(LineGraphController.class);
                        break;
                        case "Area Graph":
                            actionHandler.navigate(AreaGraphController.class);
                        break;
                        case "Scatter Graph":
                            actionHandler.navigate(ScatterGraphController.class);
                        break;
                        case "Bar Chart":
                            actionHandler.navigate(BarGraphController.class);
                        break;
                    }
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

                if (!(Arrays.asList(temp).contains("") || Arrays.asList(temp).contains("null") || Arrays.asList(temp).contains("[]"))) {
                    datas.setMonthDatas(temp);
                    switch (temp[3]){
                        case "Pie Chart":
                            actionHandler.navigate(PieChartGraphController.class);
                            break;
                        case "Line Graph":
                            actionHandler.navigate(LineGraphController.class);
                            break;
                        case "Area Graph":
                            actionHandler.navigate(AreaGraphController.class);
                            break;
                        case "Scatter Graph":
                            actionHandler.navigate(ScatterGraphController.class);
                            break;
                        case "Bar Chart":
                            actionHandler.navigate(BarGraphController.class);
                            break;
                    }
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

                if (!(Arrays.asList(temp).contains("") || Arrays.asList(temp).contains("null") || Arrays.asList(temp).contains("[]"))) {
                    System.out.println(Arrays.toString(temp));
                    datas.setDayDatas(temp);
                    switch (temp[4]){
                        case "Pie Chart":
                            actionHandler.navigate(PieChartGraphController.class);
                            break;
                        case "Line Graph":
                            actionHandler.navigate(LineGraphController.class);
                            break;
                        case "Area Graph":
                            actionHandler.navigate(AreaGraphController.class);
                            break;
                        case "Scatter Graph":
                            actionHandler.navigate(ScatterGraphController.class);
                            break;
                        case "Bar Chart":
                            actionHandler.navigate(BarGraphController.class);
                            break;
                    }
                }
            } catch (Exception e) {

            }
        });
    }
}
