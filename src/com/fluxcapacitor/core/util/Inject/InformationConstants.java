package com.fluxcapacitor.core.util.Inject;

import com.fluxcapacitor.core.util.ConnectDB;
import org.datafx.controller.flow.injection.FlowScoped;
import org.datafx.controller.injection.ApplicationScoped;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Created by Eddie on 5/18/2015.
 */
@ApplicationScoped
public class InformationConstants {
    public final String months[] = {
            "" , "January" , "February" , "March" , "April", "May",
            "June", "July", "August", "September", "October",
            "November", "December"};

    public final String monthsVar[]={"jan","feb","march","april","may","june","july","august","september","october","november","december"};

    public final String graphTypes[]={"Pie Chart", "Line Graph", "Area Graph", "Scatter Graph", "Bar Chart"};

    public static final String dataNames[] = {"","SE: Event", "SE: People","Paid: Vehicle","Paid: Non-Vehicle","Paid: Annual Day Use","Paid: Daily Use","Paid: Senior","Paid: Vet.","Paid: Disabled","Paid: OHV","Paid: Annual Sale","Paid: Golden Brear/Dis. Vet.","Paid: Golden Poppy","Paid: Camping","Paid: Senior Camping","Paid: Disabled Camping","Paid: People-to-Vehicle","Paid: Other","Free: Day Use","Free: Golden Bear","Free: Camping","Free: Dis/Vet","Free: Vehicles","Free: people","Free: Ratio","Free: Hudner","Free: Foundation","Free: Other","Turnaway: Camp","Turnaway: Day Use","Turnaway: Boats","Other: People","Other: Camping","Other: MC","Other: ATV","Other: 4x4","Other: ROV","Other: AQMA","Other: Dune","Other: Legal Vehicle","Other: Karting","Other: HangTown","Other: Other"};
    private static  String[] parkName;

    public static String firstPark;

    public void setUp(){
        System.out.println("asddas");
        ArrayList<String> parkNames = new ArrayList<>();
        parkNames.add("");

        ArrayList<String>dataTypes = new ArrayList<>();

        ConnectDB db = new ConnectDB();
        db.connectToDB("parks");

        ResultSet rs = null;
        try {
            Statement statement = db.getConnection().createStatement();
            rs = statement.executeQuery("Select * from parks");
            while(rs.next()){
                parkNames.add(rs.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String[] temp = parkNames.toArray(new String[parkNames.size()]);
        firstPark = parkNames.get(1);
        setParkName(temp);


    }

    public static String[] getParkName() {
        return parkName;
    }

    public static void setParkName(String[] parkName) {
        InformationConstants.parkName = parkName;
    }

}
