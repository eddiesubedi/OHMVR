package com.fluxcapacitor.screens.viewDatabase.MonthView;

import com.fluxcapacitor.core.util.Constants;
import com.fluxcapacitor.core.util.Information;
import com.fluxcapacitor.screens.viewDatabase.DailyView.DailyController;
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

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.Arrays;
import java.util.Random;

/**
 * Created by Eddie on 5/16/2015.
 */
@FXMLController("MonthViewFXML.fxml")
public class MonthViewController {
    @FXML
    @BackAction
    private Button back;

    @FXML
    private Text headerText;

    @FXML
    private StackPane sPane;

    @Inject
    private Information data;

    @ActionHandler
    private FlowActionHandler actionHandler;

    @PostConstruct
    public void init(){
        String header = "Monthly report of "+data.getViewDatRange().get(3)+" of "+data.getSelectedYear();
        headerText.setText(header);

        int row = 8;
        int start = 1;
        int end = 12;
        int column = end - start + 2;
        Random random = new Random();
        String[] parkName = { "Carnegie", "Clay pit", "Heber Dunes",
                "Hollister Hills", "Hungry Hills", "Oceano Dunes",
                "Ocotillo Wells", "Prairie City" };
        String staffArray[][] = new String[parkName.length][column];
        staffArray[0][0]="Park Name";
        for(int i =1;i<row;i++){
            staffArray[i][0]=parkName[i];
        }
        for(int i =start;i<=end;i++){
            staffArray[0][i-start+1] = Constants.months[i];
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
                if (event.getButton().equals(MouseButton.PRIMARY)) {
                    if (event.getClickCount() == 2) {
                        try {
                            Text temp = (Text) event.getTarget();
                            if (!temp.getText().equals("sort")) {
                                data.setSelectedMonth(temp.getText());
                                actionHandler.navigate(DailyController.class);
                            }
                        } catch (Exception e) {

                        }
                        try {
                            Label temp = (Label) event.getTarget();
                            if (!temp.getText().equals("sort")) {
                                data.setSelectedMonth(temp.getText());
                                actionHandler.navigate(DailyController.class);
                            }
                        } catch (Exception e) {

                        }
                    }
                }

            }
        });
    }
}
