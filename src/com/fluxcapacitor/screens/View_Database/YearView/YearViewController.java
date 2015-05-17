/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fluxcapacitor.screens.View_Database.YearView;

import com.fluxcapacitor.core.util.Constants;
import com.fluxcapacitor.core.util.Information;
import com.fluxcapacitor.screens.MenuBar.AbstractMenuController;
import com.fluxcapacitor.screens.View_Database.MonthView.MonthViewController;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.util.Callback;
import org.datafx.controller.FXMLController;
import org.datafx.controller.flow.action.BackAction;
import org.datafx.controller.flow.context.ActionHandler;
import org.datafx.controller.flow.context.FlowActionHandler;
import sun.applet.Main;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.Arrays;
import java.util.Random;

@FXMLController("YearViewFXML.fxml")
public class YearViewController extends AbstractMenuController{

    @FXML
    private StackPane sPane;

    @FXML
    @BackAction
    private Button back;

    @FXML
    private Text headerText;

    @Inject
    private Information data;

    @ActionHandler
    private FlowActionHandler actionHandler;

    @PostConstruct
    public void init() {
        getViewBtn().getStylesheets().add("/com/fluxcapacitor/screens/MenuBar/MainMenuCSS.css");
        getCreateBtn().getStylesheets().add("/com/fluxcapacitor/screens/MenuBar/MainMenuCSS.css");
        getInputBtn().getStylesheets().add("/com/fluxcapacitor/screens/MenuBar/MainMenuCSS.css");
        getParkBtn().getStylesheets().add("/com/fluxcapacitor/screens/MenuBar/MainMenuCSS.css");
        getUserBtn().getStylesheets().add("/com/fluxcapacitor/screens/MenuBar/MainMenuCSS.css");

        String header = "Yearly report of "+data.getViewDatRange().get(3)+" from "+data.getViewDatRange().get(0)+"-"+data.getViewDatRange().get(1);
        headerText.setText(header);
        int row = Constants.parkName.length;
        int start = Integer.parseInt(data.getViewDatRange().get(0));
        int end = Integer.parseInt(data.getViewDatRange().get(1));
        int column = end - start + 2;
        Random random = new Random();
        String staffArray[][] = new String[Constants.parkName.length][column];
        staffArray[0][0]="Park Name";
        for(int i =1;i<row;i++){
            staffArray[i][0]=Constants.parkName[i];
        }
        for(int i =start;i<=end;i++){
            staffArray[0][i-start+1] = i+"";
        }
        for (int i = 1; i < column; i++) {
            for (int j = 1; j < row; j++) {
                staffArray[j][i] = random.nextInt(500) + 1 + "";
            }
        }
        ObservableList<String[]> dataObserve = FXCollections.observableArrayList();
        dataObserve.addAll(Arrays.asList(staffArray));
        dataObserve.remove(0);//remove titles from data
        TableView<String[]> table = new TableView<>();
        for (int i = 0; i < staffArray[0].length; i++) {
            TableColumn tc = new TableColumn(staffArray[0][i]);

            TableColumn sortColumn = new TableColumn("sort");

            tc.getColumns().add(sortColumn);

            final int colNo = i;
            sortColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<String[], String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(TableColumn.CellDataFeatures<String[], String> p) {
                    return new SimpleStringProperty((p.getValue()[colNo]));
                }
            });

            sortColumn.setPrefWidth(90);
            table.getColumns().add(tc);
        }
        table.setItems(dataObserve);
        sPane.getChildren().add(table);


        table.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(event.getButton().equals(MouseButton.PRIMARY)){
                    if(event.getClickCount() == 2){
                        try {
                            Text temp = (Text) event.getTarget();
                            if(!temp.getText().equals("sort")){
                                data.setSelectedYear(temp.getText());
                                actionHandler.navigate(MonthViewController.class);
                            }
                        } catch (Exception e) {

                        }
                        try {
                            Label temp = (Label) event.getTarget();
                            if(!temp.getText().equals("sort")){
                                data.setSelectedYear(temp.getText());
                                actionHandler.navigate(MonthViewController.class);
                            }
                        } catch (Exception e) {

                        }
                    }
                }

            }
        });


    }
}