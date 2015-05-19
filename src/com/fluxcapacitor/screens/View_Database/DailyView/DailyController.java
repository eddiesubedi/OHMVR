package com.fluxcapacitor.screens.View_Database.DailyView;

import com.fluxcapacitor.core.util.Inject.InformationConstants;
import com.fluxcapacitor.core.util.Inject.InformationView;
import com.fluxcapacitor.screens.MenuBar.AbstractMenuController;
import com.fluxcapacitor.screens.View_Database.DetailedView.DetailedViewController;
import com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Eddie on 5/16/2015.
 */
@FXMLController("DailyViewFXML.fxml")
public class DailyController extends AbstractMenuController {
    @FXML
    @BackAction
    private Button back;

    @FXML
    private Text headerText;

    @FXML
    private StackPane sPane;

    @Inject
    private InformationView data;

    @Inject
    private InformationConstants constants;

    @FXML
    private NumberAxis CX, CPX, HX, HHX, HVX, OX, OWX, PX, OWXS, AX;

    @FXML
    private LineChart CarnegieChart, ClayChart, HeberChart, HollisterChart, HungryValleyChart, OceanoChart, OcotilloChart, OcotilloChartS, PrairieChart, AllChart;

    @ActionHandler
    private FlowActionHandler actionHandler;

    private ArrayList<TableColumn> columns = new ArrayList<>();
    private int monthNumber;
    private String[][] staffArray;
    private int daysInMonth;

    @PostConstruct
    public void init() {
        getViewBtn().getStylesheets().add("/com/fluxcapacitor/screens/MenuBar/MainMenuCSS.css");
        getCreateBtn().getStylesheets().add("/com/fluxcapacitor/screens/MenuBar/MainMenuCSS.css");
        getInputBtn().getStylesheets().add("/com/fluxcapacitor/screens/MenuBar/MainMenuCSS.css");
        getParkBtn().getStylesheets().add("/com/fluxcapacitor/screens/MenuBar/MainMenuCSS.css");
        getUserBtn().getStylesheets().add("/com/fluxcapacitor/screens/MenuBar/MainMenuCSS.css");

        String header = "Daily view of " + constants.dataNames[Integer.parseInt(data.getViewDatRange().get(3))] + " on " + data.getSelectedMonth() + ", " + data.getSelectedYear();
        headerText.setText(header);

        monthNumber = 0;
        try {
            Date date = new SimpleDateFormat("MMM").parse(data.getSelectedMonth());//put your month name here
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            monthNumber = cal.get(Calendar.MONTH);
        } catch (Exception e) {

        }
        Calendar mycal = new GregorianCalendar(Integer.parseInt(data.getSelectedYear()), monthNumber, 1);
        daysInMonth = mycal.getActualMaximum(Calendar.DAY_OF_MONTH); // 28

        int row = constants.getParkName().length;

        int start = 1;
        int end = daysInMonth;
        int column = end - start + 2;
        staffArray = new String[constants.getParkName().length][column];
        staffArray[0][0] = "Park Name";
        for (int i = 1; i < row; i++) {
            staffArray[i][0] = constants.getParkName()[i];
        }
        //top label
        setUp();
        for (int i = start; i <= end; i++) {
            staffArray[0][i - start + 1] = "" + i;
        }
//        for (int i = 1; i < column; i++) {
//            for (int j = 1; j < row; j++) {
//                staffArray[j][i] = random.nextInt(500) + 1 + "";
//            }
//        }
        setUpGraph();
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
                    if (tc.getColumn() > 0) {
                        data.setSelectedDay(tc.getColumn() + "");
                        int day = 0;
                        int park = tc.getRow();
//                        table.getItems().get(park)[0]

                        data.setSelectedPark((Arrays.asList(constants.getParkName()).indexOf(table.getItems().get(park)[0]) - 1) + "");
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

    private void setUpGraph() {


        XYChart.Series<Number, Number> series = new XYChart.Series<Number, Number>();
        int range = daysInMonth;
        int start = -1;
        for (int i = 1; i < staffArray[8].length; i++) {
            series.getData().add(new XYChart.Data<Number, Number>(start + i, (int) Double.parseDouble(staffArray[8][i])));
        }

        CarnegieChart.setLayoutX(.500);
        CX.setAutoRanging(false);
        CX.setLowerBound(2008);
        CX.setUpperBound(2018);
        CarnegieChart.getData().add(series);

        ////////////////////

        XYChart.Series<Number, Number> series2 = new XYChart.Series<Number, Number>();

        for (int i = 1; i < staffArray[1].length; i++) {
            series2.getData().add(new XYChart.Data<Number, Number>(start + i, (int) Double.parseDouble(staffArray[1][i])));
        }
        CPX.setAutoRanging(false);
        CPX.setLowerBound(2008);
        CPX.setUpperBound(2018);
        ClayChart.getData().add(series2);

        /////////////////////////

        XYChart.Series<Number, Number> series3 = new XYChart.Series<Number, Number>();

        for (int i = 1; i < staffArray[7].length; i++) {
            series3.getData().add(new XYChart.Data<Number, Number>(start + i, (int) Double.parseDouble(staffArray[7][i])));
        }
        HX.setAutoRanging(false);
        HX.setLowerBound(2008);
        HX.setUpperBound(2018);
        HeberChart.getData().add(series3);

        ////////////////////////////

        XYChart.Series<Number, Number> series4 = new XYChart.Series<Number, Number>();

        for (int i = 1; i < staffArray[9].length; i++) {
            series4.getData().add(new XYChart.Data<Number, Number>(start + i, (int) Double.parseDouble(staffArray[9][i])));
        }
        HHX.setAutoRanging(false);
        HHX.setLowerBound(2008);
        HHX.setUpperBound(2018);
        HollisterChart.getData().add(series4);

        //////////////////////////

        XYChart.Series<Number, Number> series5 = new XYChart.Series<Number, Number>();

        for (int i = 1; i < staffArray[2].length; i++) {
            series5.getData().add(new XYChart.Data<Number, Number>(start + i, (int) Double.parseDouble(staffArray[2][i])));
        }
        HVX.setAutoRanging(false);
        HVX.setLowerBound(2008);
        HVX.setUpperBound(2018);
        HungryValleyChart.getData().add(series5);

        ////////////////////////////

        XYChart.Series<Number, Number> series6 = new XYChart.Series<Number, Number>();

        for (int i = 1; i < staffArray[3].length; i++) {
            series6.getData().add(new XYChart.Data<Number, Number>(start + i, (int) Double.parseDouble(staffArray[3][i])));
        }
        OX.setAutoRanging(false);
        OX.setLowerBound(2008);
        OX.setUpperBound(2018);
        OceanoChart.getData().add(series6);

        ////////////////////////////

        XYChart.Series<Number, Number> series7 = new XYChart.Series<Number, Number>();

        for (int i = 1; i < staffArray[4].length; i++) {
            series7.getData().add(new XYChart.Data<Number, Number>(start + i, (int) Double.parseDouble(staffArray[4][i])));
        }
        OWX.setAutoRanging(false);
        OWX.setLowerBound(2008);
        OWX.setUpperBound(2018);
        OcotilloChart.getData().add(series7);
        ////////////////////////////

        XYChart.Series<Number, Number> series8 = new XYChart.Series<Number, Number>();

        for (int i = 1; i < staffArray[5].length; i++) {
            series8.getData().add(new XYChart.Data<Number, Number>(start + i, (int) Double.parseDouble(staffArray[5][i])));
        }
        OWXS.setAutoRanging(false);
        OWXS.setLowerBound(2008);
        OWXS.setUpperBound(2018);
        OcotilloChartS.getData().add(series8);

        ////////////////////////////

        XYChart.Series<Number, Number> series9 = new XYChart.Series<Number, Number>();

        for (int i = 1; i < staffArray[6].length; i++) {
            series9.getData().add(new XYChart.Data<Number, Number>(start + i, (int) Double.parseDouble(staffArray[6][i])));
        }
        PX.setAutoRanging(false);
        PX.setLowerBound(2008);
        PX.setUpperBound(2018);
        PrairieChart.getData().add(series9);
        ////////

    }

    private void setUp() {
        //loop parks
        for (int q = 0; q < data.getQueries().size(); q++) {
            String name = data.getTableNames().get(q) + (monthNumber + 1) + "_" + constants.monthsVar[monthNumber] + "_" + data.getSelectedYear();
            double temp = 0;
            try {
                Statement statement = data.getConnections().get(q).createStatement();
                String query = "Select * from " + name;
                ResultSet resultSet = statement.executeQuery(query);
//                System.out.println(name);
                int index = Integer.parseInt(data.getViewDatRange().get(3));

                for (int i = 0; i < 32; i++) {
                    resultSet.next();
                    temp = Double.parseDouble(resultSet.getString(index + 1) + "");
                    if (temp == -1) {
                        temp = 0;
                    }
                    try {
                        staffArray[q + 1][i + 1] = temp + "";
                    } catch (Exception e) {

                    }
//                    System.out.print(" "+temp+" ");
                }
            } catch (MySQLSyntaxErrorException e) {
//                System.out.println("File not foumd");
                int row = constants.getParkName().length;
                int start = 1;
                int end = daysInMonth;
                int column = end - start + 2;
                for (int i = 1; i < column; i++) {
                    for (int j = 1; j < row; j++) {
                        staffArray[j][i] = 0.0 + "";
                    }
                }
                break;
            } catch (SQLException e) {
//                e.printStackTrace();
            }
        }
    }
}
