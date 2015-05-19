package com.fluxcapacitor.screens.View_Database.DetailedView;

import com.fluxcapacitor.core.util.Inject.InformationConstants;
import com.fluxcapacitor.core.util.Inject.InformationView;
import com.fluxcapacitor.screens.MenuBar.AbstractMenuController;
import com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import org.datafx.controller.FXMLController;
import org.datafx.controller.flow.action.BackAction;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Eddie on 5/17/2015.
 */
@FXMLController("DetailedViewFXML.fxml")
public class DetailedViewController extends AbstractMenuController {
    @Inject
    private InformationView data;

    @Inject
    private InformationConstants constants;

    @FXML
    @BackAction
    private Button backBTN;

    @FXML
    private Text headerText;

    @FXML
    private TextField _0,_1,_2,_3,_4,_5,_6,_7,_8,_9,_10,_11,_12,_13,_14,_15,_16,_17,_18,_19,_20,_21,_22,_23,_24,_25,_26,_27,_28,_29,_30,_31,_32,_33,_34,_35,_36,_37,_38,_39,_40,_41,_42;
    private ArrayList<TextField> textFields;

    @PostConstruct
    public void init() {
        getViewBtn().getStylesheets().add("/com/fluxcapacitor/screens/MenuBar/MainMenuCSS.css");
        getCreateBtn().getStylesheets().add("/com/fluxcapacitor/screens/MenuBar/MainMenuCSS.css");
        getInputBtn().getStylesheets().add("/com/fluxcapacitor/screens/MenuBar/MainMenuCSS.css");
        getParkBtn().getStylesheets().add("/com/fluxcapacitor/screens/MenuBar/MainMenuCSS.css");
        getUserBtn().getStylesheets().add("/com/fluxcapacitor/screens/MenuBar/MainMenuCSS.css");

        setUp();

//        System.out.println(data.getSelectedPark() + " " + data.getSelectedYear() + " " + data.getSelectedMonth() + " " + data.getSelectedDay());
//        System.out.println(data.getSelectedPark());
        String parkName;
        if (Integer.parseInt(data.getSelectedPark()) == 0) {
            parkName = constants.firstPark;
        } else {
            parkName = constants.getParkName()[Integer.parseInt(data.getSelectedPark()) + 1];
        }
        String header = "449 of " + parkName + " on " + data.getSelectedMonth() + " " + data.getSelectedDay() + ", " + data.getSelectedYear();
        headerText.setText(header);


//
        int monthNumber = 0;
        try {
            Date date = new SimpleDateFormat("MMM").parse(data.getSelectedMonth());//put your month name here
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            monthNumber = cal.get(Calendar.MONTH);
        } catch (Exception e) {

        }
        String name = data.getTableNames().get(Integer.parseInt(data.getSelectedPark())) + (monthNumber + 1) + "_" + constants.monthsVar[monthNumber] + "_" + data.getSelectedYear();


        String query = "Select * from " + name;
        try {
            Statement statement = data.getConnections().get(Integer.parseInt(data.getSelectedPark())).createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            for (int i = 0; i < Integer.parseInt(data.getSelectedDay()); i++) {
                resultSet.next();
            }
            System.out.println(name);
            for (int i = 2; i < constants.dataNames.length; i++) {
                String temp = resultSet.getString(i);
                try{
                    if(Double.parseDouble(temp)==-1){
                        temp=0+"";
                    }
                }catch (Exception e){

                }
                textFields.get(Math.abs(2-i)).setText(temp);
//                System.out.println(resultSet.getString(i));
                switch (i) {
                    case 2:

                    break;
                }
            }
            String temp = resultSet.getString(43);
            if(Double.parseDouble(temp)==-1){
                temp=0+"";
            }
            textFields.get(textFields.size()-1).setText(temp);
        } catch (MySQLSyntaxErrorException e) {
            System.out.println("File doesnt Exit");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void setUp() {
        textFields = new ArrayList<>();
        textFields.add(_0);
        textFields.add(_1);
        textFields.add(_2);
        textFields.add(_3);
        textFields.add(_4);
        textFields.add(_5);
        textFields.add(_6);
        textFields.add(_7);
        textFields.add(_8);
        textFields.add(_9);
        textFields.add(_10);
        textFields.add(_11);
        textFields.add(_12);
        textFields.add(_13);
        textFields.add(_14);
        textFields.add(_15);
        textFields.add(_16);
        textFields.add(_17);
        textFields.add(_18);
        textFields.add(_19);
        textFields.add(_20);
        textFields.add(_21);
        textFields.add(_22);
        textFields.add(_23);
        textFields.add(_24);
        textFields.add(_25);
        textFields.add(_26);
        textFields.add(_27);
        textFields.add(_28);
        textFields.add(_29);
        textFields.add(_30);
        textFields.add(_31);
        textFields.add(_32);
        textFields.add(_33);
        textFields.add(_34);
        textFields.add(_35);
        textFields.add(_36);
        textFields.add(_37);
        textFields.add(_38);
        textFields.add(_39);
        textFields.add(_40);
        textFields.add(_41);
        textFields.add(_42);
    }
}
