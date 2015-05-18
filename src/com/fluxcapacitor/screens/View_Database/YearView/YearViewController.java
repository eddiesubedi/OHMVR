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
public class YearViewController extends AbstractMenuController{

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

    @ActionHandler
    private FlowActionHandler actionHandler;

    @PostConstruct
    public void init() {

//        System.out.println(constants.dataNames[Integer.parseInt(data.getViewDatRange().get(3))]);
        getViewBtn().getStylesheets().add("/com/fluxcapacitor/screens/MenuBar/MainMenuCSS.css");
        getCreateBtn().getStylesheets().add("/com/fluxcapacitor/screens/MenuBar/MainMenuCSS.css");
        getInputBtn().getStylesheets().add("/com/fluxcapacitor/screens/MenuBar/MainMenuCSS.css");
        getParkBtn().getStylesheets().add("/com/fluxcapacitor/screens/MenuBar/MainMenuCSS.css");
        getUserBtn().getStylesheets().add("/com/fluxcapacitor/screens/MenuBar/MainMenuCSS.css");

        setUp();

        String header = "Yearly report of "+data.getViewDatRange().get(3)+" from "+data.getViewDatRange().get(0)+"-"+data.getViewDatRange().get(1);
        headerText.setText(header);
        int row = constants.getParkName().length;
        int start = Integer.parseInt(data.getViewDatRange().get(0));
        int end = Integer.parseInt(data.getViewDatRange().get(1));
        int column = end - start + 2;
        Random random = new Random();
        String staffArray[][] = new String[constants.getParkName().length][column];
        staffArray[0][0]="Park Name";
        for(int i =1;i<row;i++){
            staffArray[i][0]= constants.getParkName()[i];
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

    private void setUp() {
        ArrayList<String>queries = new ArrayList<>();
        ArrayList<String>tableNames = new ArrayList<>();

        ConnectDB connectDB = new ConnectDB();
        connectDB.connectToDB("parks");
        Connection connectionParks = connectDB.getConnection();

        ResultSet rs = null;
        try {
            Statement statement = connectionParks.createStatement();
            rs = statement.executeQuery("Select * from parks");
            while(rs.next()){
                queries.add(rs.getString(2));
                tableNames.add(rs.getString(3));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ////////////////////////////////////////////////////////////


        ArrayList<Connection>connections = new ArrayList<>();
        for(int i =0;i<queries.size();i++){
            connectDB.connectToDB(queries.get(i));
            connections.add(connectDB.getConnection());
        }
        for(int q =0;q<queries.size();q++){
            for(int i =0;i<constants.monthsVar.length;i++){
                for(int j= Integer.parseInt(data.getViewDatRange().get(0));j<=Integer.parseInt(data.getViewDatRange().get(1));j++){
//                    if(j<2015 || (j==2015 && i<=2) ){
                        try{
                            String name = tableNames.get(q)+(i+1)+"_"+constants.monthsVar[i]+"_"+j;
                            System.out.println(name);
                            ResultSet resultSet = null;
                            try {
                                Statement statement = connections.get(q).createStatement();
                                String query = "Select * from "+name;
                                resultSet = statement.executeQuery(query);
                                for(int k = 1;k<=32;k++){
                                    resultSet.next();
                                }
                                int index = Integer.parseInt(data.getViewDatRange().get(3));
                                System.out.println(resultSet.getString(index+1));
                            } catch (SQLException e) {// the year 449 is not in the databse
//                                e.printStackTrace();
                            }
                            System.out.println("===============================");
                        }catch (Exception e){
//                            e.printStackTrace();
                        }
//                    }
                }
            }
        }
    }
}