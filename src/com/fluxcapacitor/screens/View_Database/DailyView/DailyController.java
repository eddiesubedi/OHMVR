package com.fluxcapacitor.screens.View_Database.DailyView;

import com.fluxcapacitor.core.util.Constants;
import com.fluxcapacitor.core.util.Inject.InformationView;
import com.fluxcapacitor.screens.MenuBar.AbstractMenuController;
import com.fluxcapacitor.screens.View_Database.DetailedView.DetailedViewController;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.util.Callback;
import org.datafx.controller.FXMLController;
import org.datafx.controller.flow.FlowException;
import org.datafx.controller.flow.action.BackAction;
import org.datafx.controller.flow.context.ActionHandler;
import org.datafx.controller.flow.context.FlowActionHandler;
import org.datafx.controller.util.VetoException;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Eddie on 5/16/2015.
 */
@FXMLController("DailyViewFXML.fxml")
public class DailyController  extends AbstractMenuController {
    @FXML
    @BackAction
    private Button back;

    @FXML
    private Text headerText;

    @FXML
    private StackPane sPane;

    @Inject
    private InformationView data;

    @ActionHandler
    private FlowActionHandler actionHandler;

    private ArrayList<TableColumn> columns = new ArrayList<>();

    @PostConstruct
    public void init() {
        getViewBtn().getStylesheets().add("/com/fluxcapacitor/screens/MenuBar/MainMenuCSS.css");
        getCreateBtn().getStylesheets().add("/com/fluxcapacitor/screens/MenuBar/MainMenuCSS.css");
        getInputBtn().getStylesheets().add("/com/fluxcapacitor/screens/MenuBar/MainMenuCSS.css");
        getParkBtn().getStylesheets().add("/com/fluxcapacitor/screens/MenuBar/MainMenuCSS.css");
        getUserBtn().getStylesheets().add("/com/fluxcapacitor/screens/MenuBar/MainMenuCSS.css");

        String header = "Daily view of " + data.getViewDatRange().get(3) + " on " + data.getSelectedMonth();
        headerText.setText(header);

        int monthNumber = 0;
        try {
            Date date = new SimpleDateFormat("MMM").parse(data.getSelectedMonth());//put your month name here
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            monthNumber = cal.get(Calendar.MONTH);
        } catch (Exception e) {

        }
        Calendar mycal = new GregorianCalendar(Integer.parseInt(data.getSelectedYear()), monthNumber, 1);
        int daysInMonth = mycal.getActualMaximum(Calendar.DAY_OF_MONTH); // 28

        int row = 8;
        int start = 1;
        int end = daysInMonth;
        int column = end - start + 2;
        Random random = new Random();
        String staffArray[][] = new String[Constants.parkName.length][column];
        staffArray[0][0] = "Park Name";
        for (int i = 1; i < row; i++) {
            staffArray[i][0] = Constants.parkName[i];
        }
        //top label
        for (int i = start; i <= end; i++) {
            staffArray[0][i - start + 1] = "" + i;
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

            columns.add(new TableColumn("sort"));


            tc.getColumns().add(columns.get(columns.size() - 1));

            final int colNo = i;
            columns.get(columns.size() - 1).setCellValueFactory(new Callback<TableColumn.CellDataFeatures<String[], String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(TableColumn.CellDataFeatures<String[], String> p) {
                    return new SimpleStringProperty((p.getValue()[colNo]));
                }
            });


            columns.get(columns.size() - 1).setPrefWidth(90);
            table.getColumns().add(tc);
        }
        table.getSelectionModel().setCellSelectionEnabled(true);
        table.setItems(dataObserve);
        sPane.getChildren().add(table);

        table.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getClickCount() == 2) {
                    TablePosition tc = table.getFocusModel().getFocusedCell();
                    if(tc.getColumn()>0){
                        data.setSelectedDay(tc.getColumn()+"");
                        data.setSelectedPark(table.getSelectionModel().getSelectedItem()[0]);
                        try {
                            actionHandler.navigate(DetailedViewController.class);
                        } catch (VetoException e) {
                            e.printStackTrace();
                        } catch (FlowException e) {
                            e.printStackTrace();
                        }
//                    System.out.println(table.getSelectionModel().getSelectedItem()[0]+" day:"+tc.getColumn());
                    }
                }
            }
        });
    }
}
