package com.fluxcapacitor.core.util.Inject;

import org.datafx.controller.flow.injection.FlowScoped;

import java.lang.reflect.Array;
import java.sql.Connection;
import java.util.ArrayList;

/**
 * Created by Eddie on 5/16/2015.
 */
@FlowScoped
public class InformationView {
    private ArrayList<String> viewDatRange = new ArrayList<>();
    private String selectedYear, selectedMonth, selectedPark, selectedDay;
    ArrayList<String> queries = new ArrayList<>();
    ArrayList<String> tableNames = new ArrayList<>();
    ArrayList<Connection> connections = new ArrayList<>();

    public ArrayList<Connection> getConnections() {
        return connections;
    }

    public void setConnections(ArrayList<Connection> connections) {
        this.connections = connections;
    }

    public ArrayList<String> getQueries() {
        return queries;
    }

    public void setQueries(ArrayList<String> queries) {
        this.queries = queries;
    }

    public ArrayList<String> getTableNames() {
        return tableNames;
    }

    public void setTableNames(ArrayList<String> tableNames) {
        this.tableNames = tableNames;
    }

    public void setSelectedPark(String selectedPark) {
        this.selectedPark = selectedPark;
    }

    public void setSelectedDay(String selectedDay) {
        this.selectedDay = selectedDay;
    }

    public String getSelectedPark() {
        return selectedPark;
    }

    public String getSelectedDay() {
        return selectedDay;
    }

    public String getSelectedMonth() {
        return selectedMonth;
    }

    public void setSelectedMonth(String selectedMonth) {
        this.selectedMonth = selectedMonth;
    }

    public void setSelectedYear(String selectedYear) {
        this.selectedYear = selectedYear;
    }

    public String getSelectedYear() {
        return selectedYear;
    }

    public ArrayList<String> getViewDatRange() {
        return viewDatRange;
    }

    public void setViewDatRange(ArrayList<String> viewDatRange) {
        this.viewDatRange = viewDatRange;
    }
    public void clearviewDatRange(){
        this.viewDatRange.clear();
    }
}
