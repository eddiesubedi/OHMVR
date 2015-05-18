package com.fluxcapacitor.core.util.Inject;

import org.datafx.controller.flow.injection.FlowScoped;

import java.util.ArrayList;

/**
 * Created by Eddie on 5/17/2015.
 */
@FlowScoped
public class InformationGraph {
    //from year, to year, graph type, parks
    private static String[] yearDatas= new String[4];
    private static String[] monthDatas= new String[5];
    private static String[] dayDatas= new String[6];

    public static void setYearDatas(String[] yearDatas) {
        InformationGraph.yearDatas = yearDatas;
    }

    public static void setMonthDatas(String[] monthDatas) {
        InformationGraph.monthDatas = monthDatas;
    }

    public static void setDayDatas(String[] dayDatas) {
        InformationGraph.dayDatas = dayDatas;
    }

    public static String[] getYearDatas() {
        return yearDatas;
    }

    public static String[] getMonthDatas() {
        return monthDatas;
    }

    public static String[] getDayDatas() {
        return dayDatas;
    }
}
