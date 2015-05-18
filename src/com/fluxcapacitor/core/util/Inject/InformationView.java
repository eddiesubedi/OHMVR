package com.fluxcapacitor.core.util.Inject;

import org.datafx.controller.flow.injection.FlowScoped;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Eddie on 5/16/2015.
 */
@FlowScoped
public class InformationView {
    private ArrayList<String> viewDatRange = new ArrayList<>();
    private String selectedYear, selectedMonth, selectedPark, selectedDay;

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
