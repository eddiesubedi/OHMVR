package com.fluxcapacitor.core.util.Inject;

import org.datafx.controller.flow.injection.FlowScoped;

/**
 * Created by Eddie on 5/17/2015.
 */
@FlowScoped
public class InformationInput {
    private String selectedYear,selectedMonth,selectedDay, selectedPark;

    public void setSelectedYear(String selectedYear) {
        this.selectedYear = selectedYear;
    }

    public void setSelectedMonth(String selectedMonth) {
        this.selectedMonth = selectedMonth;
    }

    public void setSelectedDay(String selectedDay) {
        this.selectedDay = selectedDay;
    }

    public void setSelectedPark(String selectedPark) {
        this.selectedPark = selectedPark;
    }

    public String getSelectedYear() {
        return selectedYear;
    }

    public String getSelectedMonth() {
        return selectedMonth;
    }

    public String getSelectedDay() {
        return selectedDay;
    }

    public String getSelectedPark() {
        return selectedPark;
    }

    @Override
    public String toString() {
        return "InformationInput{" +
                "selectedYear='" + selectedYear + '\'' +
                ", selectedMonth='" + selectedMonth + '\'' +
                ", selectedDay='" + selectedDay + '\'' +
                ", selectedPark='" + selectedPark + '\'' +
                '}';
    }
}
