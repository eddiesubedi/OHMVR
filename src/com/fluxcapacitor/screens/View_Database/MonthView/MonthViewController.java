package com.fluxcapacitor.screens.View_Database.MonthView;

import com.fluxcapacitor.core.util.Inject.InformationConstants;
import com.fluxcapacitor.core.util.Inject.InformationView;
import com.fluxcapacitor.screens.MenuBar.AbstractMenuController;
import com.fluxcapacitor.screens.View_Database.DailyView.DailyController;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
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
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Random;

/**
 * Created by Eddie on 5/16/2015.
 */
@FXMLController("MonthViewFXML.fxml")
public class MonthViewController extends AbstractMenuController {
    @FXML
    @BackAction
    private Button back;

    @FXML
    private Text headerText;

    @FXML
    private StackPane sPane;

    @FXML
    private NumberAxis CX, CPX,HX,HHX,HVX,OX,OWX,PX,OWXS, AX;

    @FXML
    private LineChart CarnegieChart, ClayChart, HeberChart, HollisterChart, HungryValleyChart, OceanoChart, OcotilloChart,OcotilloChartS, PrairieChart, AllChart;

    @Inject
    private InformationView data;

    @Inject
    private InformationConstants constants;

    @ActionHandler
    private FlowActionHandler actionHandler;
    private String[][] staffArray;

    @PostConstruct
    public void init() {
        getViewBtn().getStylesheets().add("/com/fluxcapacitor/screens/MenuBar/MainMenuCSS.css");
        getCreateBtn().getStylesheets().add("/com/fluxcapacitor/screens/MenuBar/MainMenuCSS.css");
        getInputBtn().getStylesheets().add("/com/fluxcapacitor/screens/MenuBar/MainMenuCSS.css");
        getParkBtn().getStylesheets().add("/com/fluxcapacitor/screens/MenuBar/MainMenuCSS.css");
        getUserBtn().getStylesheets().add("/com/fluxcapacitor/screens/MenuBar/MainMenuCSS.css");

        String header = "Monthly report of " + constants.dataNames[Integer.parseInt(data.getViewDatRange().get(3))] + " of " + data.getSelectedYear();
        headerText.setText(header);

        int row = constants.getParkName().length;
        int start = 1;
        int end = 12;
        int column = end - start + 2;
        Random random = new Random();

        staffArray = new String[constants.getParkName().length][column];
        staffArray[0][0] = "Park Name";
//        System.out.println(staffArray.length);
        for (int i = 1; i < row; i++) {
            staffArray[i][0] = constants.getParkName()[i];
        }


        for (int i = start; i <= end; i++) {
            staffArray[0][i - start + 1] = constants.months[i] + "";
        }
        setUp();
        setUpGraph();
//        for (int i = 1; i < column; i++) {
//            for (int j = 1; j < row; j++) {
//                staffArray[j][i] = random.nextInt(500) + 1 + "";
//            }
//        }
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

    private void setUpGraph() {
        XYChart.Series<Number, Number> series = new XYChart.Series<Number, Number>();
        int range = 12;
        int start = 0;
        for(int i =1;i<staffArray[8].length;i++){
            series.getData().add(new XYChart.Data<Number, Number>(start+i, (int) Double.parseDouble(staffArray[8][i])));
        }

        CarnegieChart.setLayoutX(.500);
        CX.setAutoRanging(true);
        CX.setLowerBound(2008);
        CX.setUpperBound(2018);
        CarnegieChart.getData().add(series);

        ////////////////////

        XYChart.Series<Number, Number> series2 = new XYChart.Series<Number, Number>();

        for(int i =1;i<staffArray[1].length;i++){
            series2.getData().add(new XYChart.Data<Number, Number>(start+i, (int) Double.parseDouble(staffArray[1][i])));
        }
        CPX.setAutoRanging(true);
        CPX.setLowerBound(2008);
        CPX.setUpperBound(2018);
        ClayChart.getData().add(series2);

        /////////////////////////

        XYChart.Series<Number, Number> series3 = new XYChart.Series<Number, Number>();

        for(int i =1;i<staffArray[7].length;i++){
            series3.getData().add(new XYChart.Data<Number, Number>(start+i, (int) Double.parseDouble(staffArray[7][i])));
        }
        HX.setAutoRanging(true);
        HX.setLowerBound(2008);
        HX.setUpperBound(2018);
        HeberChart.getData().add(series3);

        ////////////////////////////

        XYChart.Series<Number, Number> series4 = new XYChart.Series<Number, Number>();

        for(int i =1;i<staffArray[9].length;i++){
            series4.getData().add(new XYChart.Data<Number, Number>(start+i, (int) Double.parseDouble(staffArray[9][i])));
        }
        HHX.setAutoRanging(true);
        HHX.setLowerBound(2008);
        HHX.setUpperBound(2018);
        HollisterChart.getData().add(series4);

        //////////////////////////

        XYChart.Series<Number, Number> series5 = new XYChart.Series<Number, Number>();

        for(int i =1;i<staffArray[2].length;i++){
            series5.getData().add(new XYChart.Data<Number, Number>(start+i, (int) Double.parseDouble(staffArray[2][i])));
        }
        HVX.setAutoRanging(true);
        HVX.setLowerBound(2008);
        HVX.setUpperBound(2018);
        HungryValleyChart.getData().add(series5);

        ////////////////////////////

        XYChart.Series<Number, Number> series6 = new XYChart.Series<Number, Number>();

        for(int i =1;i<staffArray[3].length;i++){
            series6.getData().add(new XYChart.Data<Number, Number>(start+i, (int) Double.parseDouble(staffArray[3][i])));
        }
        OX.setAutoRanging(true);
        OX.setLowerBound(2008);
        OX.setUpperBound(2018);
        OceanoChart.getData().add(series6);

        ////////////////////////////

        XYChart.Series<Number, Number> series7 = new XYChart.Series<Number, Number>();

        for(int i =1;i<staffArray[4].length;i++){
            series7.getData().add(new XYChart.Data<Number, Number>(start+i, (int) Double.parseDouble(staffArray[4][i])));
        }
        OWX.setAutoRanging(true);
        OWX.setLowerBound(2008);
        OWX.setUpperBound(2018);
        OcotilloChart.getData().add(series7);
        ////////////////////////////

        XYChart.Series<Number, Number> series8 = new XYChart.Series<Number, Number>();

        for(int i =1;i<staffArray[5].length;i++){
            series8.getData().add(new XYChart.Data<Number, Number>(start+i, (int) Double.parseDouble(staffArray[5][i])));
        }
        OWXS.setAutoRanging(true);
        OWXS.setLowerBound(2008);
        OWXS.setUpperBound(2018);
        OcotilloChartS.getData().add(series8);

        ////////////////////////////

        XYChart.Series<Number, Number> series9 = new XYChart.Series<Number, Number>();

        for(int i =1;i<staffArray[6].length;i++){
            series9.getData().add(new XYChart.Data<Number, Number>(start+i, (int) Double.parseDouble(staffArray[6][i])));
        }
        PX.setAutoRanging(true);
        PX.setLowerBound(2008);
        PX.setUpperBound(2018);
        PrairieChart.getData().add(series9);
        ////////

    }



    private void setUp() {

        //loop parks
        for (int q = 0; q < data.getQueries().size(); q++) {
            //loop month
            for (int i = 0; i < constants.monthsVar.length; i++) {
                double temp = 0;
                try {
                    Statement statement = data.getConnections().get(q).createStatement();
                    String name = data.getTableNames().get(q)+(i+1)+"_"+constants.monthsVar[i]+"_"+data.getSelectedYear();
//                    System.out.println(name);
                    String query = "Select * from "+name;
                    ResultSet resultSet = statement.executeQuery(query);
                    for(int k = 1;k<=32;k++){
                        resultSet.next();
                    }
                    int index = Integer.parseInt(data.getViewDatRange().get(3));
                    temp = Double.parseDouble(resultSet.getString(index + 1) + "");
                    if(temp == -1){
                        temp = 0;
                    }
                } catch (Exception e) {
                    temp = 0;
//                    e.printStackTrace();
                }
                staffArray[q+1][i+1] = temp+"";
            }
//            System.out.println("========================================");
        }

    }
}
