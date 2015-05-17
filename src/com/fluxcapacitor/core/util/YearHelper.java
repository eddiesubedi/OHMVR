
package com.fluxcapacitor.core.util;

import javafx.beans.property.SimpleStringProperty;


public class YearHelper {
    private SimpleStringProperty data;
    public YearHelper(String data) {
        this.data = new SimpleStringProperty(data);
    }
    public String data() {
        return data.get();
    }
    public void data(String data) {
        this.data.set(data);
    }
}
