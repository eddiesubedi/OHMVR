/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fluxcapacitor.screens.View_Database.YearView;

import com.fluxcapacitor.core.util.ConnectDB;
import com.fluxcapacitor.core.util.Inject.InformationConstants;
import com.fluxcapacitor.core.util.Inject.InformationView;
import com.fluxcapacitor.screens.MenuBar.AbstractMenuController;
import com.fluxcapacitor.screens.View_Database.MonthView.MonthViewController;
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
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

@FXMLController("YearViewFXML.fxml")
public class YearViewController extends AbstractMenuController {

    @FXML
    private StackPane sPane;

    @FXML
    @BackAction
    private Button back;

    @FXML
    private Text headerText;

    @Inject
    private InformationView data;

    @Inject
    private InformationConstants constants;

    @FXML
    private NumberAxis CX, CPX,HX,HHX,HVX,OX,OWX,PX,OWXS, AX;

    @FXML
    private LineChart CarnegieChart, ClayChart, HeberChart, HollisterChart, HungryValleyChart, OceanoChart, OcotilloChart,OcotilloChartS, PrairieChart, AllChart;

    @ActionHandler
    private FlowActionHandler actionHandler;
    private String[][] staffArray;

    @PostConstruct
    public void init() {
//        System.out.println(Arrays.toString(constants.getParkName()));

//        System.out.println(constants.dataNames[Integer.parseInt(data.getViewDatRange().get(3))]);

        getViewBtn().getStylesheets().add("/com/fluxcapacitor/screens/MenuBar/MainMenuCSS.css");
        getCreateBtn().getStylesheets().add("/com/fluxcapacitor/screens/MenuBar/MainMenuCSS.css");
        getInputBtn().getStylesheets().add("/com/fluxcapacitor/screens/MenuBar/MainMenuCSS.css");
        getParkBtn().getStylesheets().add("/com/fluxcapacitor/screens/MenuBar/MainMenuCSS.css");
        getUserBtn().getStylesheets().add("/com/fluxcapacitor/screens/MenuBar/MainMenuCSS.css");


        String header = "Yearly report of " + constants.dataNames[Integer.parseInt(data.getViewDatRange().get(3))] + " from " + data.getViewDatRange().get(0) + "-" + data.getViewDatRange().get(1);
        headerText.setText(header);
        int row = constants.getParkName().length;
        int start = Integer.parseInt(data.getViewDatRange().get(0));
        int end = Integer.parseInt(data.getViewDatRange().get(1));
        int column = end - start + 2;
        Random random = new Random();
        staffArray = new String[constants.getParkName().length][column];
        staffArray[0][0] = "Park Name";

        for (int i = 1; i < row; i++) {
            staffArray[i][0] = constants.getParkName()[i];
        }
        for (int i = start; i <= end; i++) {
            staffArray[0][i - start + 1] = i + "";
        }

        setUp();
        setUpGraph();
//        for (int i = 1; i < column; i++) {
//            for (int j = 1; j < row; j++) {
//                System.out.println("i: "+i+" j : "+j);
//                staffArray[j][i] = random.nextInt(500) + 1 + "";
//            }
//            System.out.println();
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
                                data.setSelectedYear(temp.getText());
                                actionHandler.navigate(MonthViewController.class);
                            }
                        } catch (Exception e) {

                        }
                        try {
                            Label temp = (Label) event.getTarget();
                            if (!temp.getText().equals("sort")) {
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

    private void setUpGraph() {
        XYChart.Series<Number, Number> series = new XYChart.Series<Number, Number>();
        int range = Math.abs(Integer.parseInt(data.getViewDatRange().get(0)) - Integer.parseInt(data.getViewDatRange().get(1)))-1;
        int start = (Integer.parseInt(data.getViewDatRange().get(0)))-1;
        for(int i =1;i<staffArray[8].length;i++){
            series.getData().add(new XYChart.Data<Number, Number>(start+i, (int) Double.parseDouble(staffArray[8][i])));
        }

        CarnegieChart.setLayoutX(.500);
        CX.setAutoRanging(false);
        CX.setLowerBound(2008);
        CX.setUpperBound(2018);
        CarnegieChart.getData().add(series);

        ////////////////////

        XYChart.Series<Number, Number> series2 = new XYChart.Series<Number, Number>();

        for(int i =1;i<staffArray[1].length;i++){
            series2.getData().add(new XYChart.Data<Number, Number>(start+i, (int) Double.parseDouble(staffArray[1][i])));
        }
        CPX.setAutoRanging(false);
        CPX.setLowerBound(2008);
        CPX.setUpperBound(2018);
        ClayChart.getData().add(series2);

        /////////////////////////

        XYChart.Series<Number, Number> series3 = new XYChart.Series<Number, Number>();

        for(int i =1;i<staffArray[7].length;i++){
            series3.getData().add(new XYChart.Data<Number, Number>(start+i, (int) Double.parseDouble(staffArray[7][i])));
        }
        HX.setAutoRanging(false);
        HX.setLowerBound(2008);
        HX.setUpperBound(2018);
        HeberChart.getData().add(series3);

        ////////////////////////////

        XYChart.Series<Number, Number> series4 = new XYChart.Series<Number, Number>();

        for(int i =1;i<staffArray[9].length;i++){
            series4.getData().add(new XYChart.Data<Number, Number>(start+i, (int) Double.parseDouble(staffArray[9][i])));
        }
        HHX.setAutoRanging(false);
        HHX.setLowerBound(2008);
        HHX.setUpperBound(2018);
        HollisterChart.getData().add(series4);

        //////////////////////////

        XYChart.Series<Number, Number> series5 = new XYChart.Series<Number, Number>();

        for(int i =1;i<staffArray[2].length;i++){
            series5.getData().add(new XYChart.Data<Number, Number>(start+i, (int) Double.parseDouble(staffArray[2][i])));
        }
        HVX.setAutoRanging(false);
        HVX.setLowerBound(2008);
        HVX.setUpperBound(2018);
        HungryValleyChart.getData().add(series5);

        ////////////////////////////

        XYChart.Series<Number, Number> series6 = new XYChart.Series<Number, Number>();

        for(int i =1;i<staffArray[3].length;i++){
            series6.getData().add(new XYChart.Data<Number, Number>(start+i, (int) Double.parseDouble(staffArray[3][i])));
        }
        OX.setAutoRanging(false);
        OX.setLowerBound(2008);
        OX.setUpperBound(2018);
        OceanoChart.getData().add(series6);

        ////////////////////////////

        XYChart.Series<Number, Number> series7 = new XYChart.Series<Number, Number>();

        for(int i =1;i<staffArray[4].length;i++){
            series7.getData().add(new XYChart.Data<Number, Number>(start+i, (int) Double.parseDouble(staffArray[4][i])));
        }
        OWX.setAutoRanging(false);
        OWX.setLowerBound(2008);
        OWX.setUpperBound(2018);
        OcotilloChart.getData().add(series7);
        ////////////////////////////

        XYChart.Series<Number, Number> series8 = new XYChart.Series<Number, Number>();

        for(int i =1;i<staffArray[5].length;i++){
            series8.getData().add(new XYChart.Data<Number, Number>(start+i, (int) Double.parseDouble(staffArray[5][i])));
        }
        OWXS.setAutoRanging(false);
        OWXS.setLowerBound(2008);
        OWXS.setUpperBound(2018);
        OcotilloChartS.getData().add(series8);

        ////////////////////////////

        XYChart.Series<Number, Number> series9 = new XYChart.Series<Number, Number>();

        for(int i =1;i<staffArray[6].length;i++){
            series9.getData().add(new XYChart.Data<Number, Number>(start+i, (int) Double.parseDouble(staffArray[6][i])));
        }
        PX.setAutoRanging(false);
        PX.setLowerBound(2008);
        PX.setUpperBound(2018);
        PrairieChart.getData().add(series9);
        ////////

    }

    private void setUp() {

        ArrayList<String> queries = new ArrayList<>();
        ArrayList<String> tableNames = new ArrayList<>();

        ConnectDB connectDB = new ConnectDB();
        connectDB.connectToDB("parks");
        Connection connectionParks = connectDB.getConnection();

        ResultSet rs = null;
        try {
            Statement statement = connectionParks.createStatement();
            rs = statement.executeQuery("Select * from parks");
            while (rs.next()) {
                queries.add(rs.getString(2));
                tableNames.add(rs.getString(3));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        data.setQueries(queries);
        data.setTableNames(tableNames);
        ////////////////////////////////////////////////////////////

//        ArrayList<ArrayList<Double>> totals = new ArrayList<>();

        ArrayList<Connection> connections = new ArrayList<>();
        for (int i = 0; i < queries.size(); i++) {
            connectDB.connectToDB(queries.get(i));
            connections.add(connectDB.getConnection());
        }

        data.setQueries(queries);
        data.setTableNames(tableNames);
        data.setConnections(connections);
        //loop parks
        for (int q = 0; q < queries.size(); q++) {
            //loop year

            for (int j = Integer.parseInt(data.getViewDatRange().get(0)); j <= Integer.parseInt(data.getViewDatRange().get(1)); j++) {
                double total = 0;
                //loop month
                for (int i = 0; i < constants.monthsVar.length; i++) {
                        double value = 0;
                        try{
                            String name = tableNames.get(q)+(i+1)+"_"+constants.monthsVar[i]+"_"+j;
//                            System.out.println(name);
                            ResultSet resultSet = null;

                            try {
                                Statement statement = connections.get(q).createStatement();
                                String query = "Select * from "+name;
                                resultSet = statement.executeQuery(query);
                                //loop table
                                for(int k = 1;k<=32;k++){
                                    resultSet.next();
                                }
                                int index = Integer.parseInt(data.getViewDatRange().get(3));
                                double temp = Double.parseDouble(resultSet.getString(index+1)+"");
                                if(temp != -1){
                                    value= temp;
                                }
//                                System.out.println(resultSet.getString(index+1));
                            } catch (SQLException e) {// the year 449 is not in the databse
//                                e.printStackTrace();
                            }
                              total = total + value;
//                            System.out.println(value);
//                            System.out.println("===============================");
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                }
                staffArray[(q+1)][(1+j-Integer.parseInt(data.getViewDatRange().get(0)))] = total+"";
//                  System.out.println("q: "+(q+1)+" j: "+(1+j-Integer.parseInt(data.getViewDatRange().get(0))));
//                System.out.println(queries.get(q)+" "+j+": "+total);
//                System.out.print(" "+total+" ");
            }
//                System.out.println();
        }
    }
}