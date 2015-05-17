package com.fluxcapacitor.screens.viewDatabase.DetailedView;

import com.fluxcapacitor.core.util.Information;
import org.datafx.controller.FXMLController;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

/**
 * Created by Eddie on 5/17/2015.
 */
@FXMLController("DetailedViewFXML.fxml")
public class DetailedViewController {
    @Inject
    private Information data;

    @PostConstruct
    public void init(){
        System.out.println(data.getSelectedPark()+" "+data.getSelectedYear()+" "+data.getSelectedMonth()+" "+data.getSelectedDay());
    }
}
