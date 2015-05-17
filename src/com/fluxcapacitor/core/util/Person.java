/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fluxcapacitor.core.util;

import java.util.ArrayList;
import javafx.beans.property.SimpleStringProperty;

public class Person {
    private final SimpleStringProperty firstNames;
    private final SimpleStringProperty lastName;
    private final SimpleStringProperty email;
    
 
    public Person(String fName, String lName, String email) {
        this.firstNames = new SimpleStringProperty(fName);
        this.lastName = new SimpleStringProperty(lName);
        this.email = new SimpleStringProperty(email);
    }
 
    public String getFirstName() {
        return firstNames.get();
    }
    public void setFirstName(String fName) {
        firstNames.set(fName);
    }
        
    public String getLastName() {
        return lastName.get();
    }
    public void setLastName(String fName) {
        lastName.set(fName);
    }
    
    public String getEmail() {
        return email.get();
    }
    public void setEmail(String fName) {
        email.set(fName);
    }
        
}