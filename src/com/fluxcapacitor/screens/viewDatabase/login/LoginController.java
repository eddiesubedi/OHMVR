/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fluxcapacitor.screens.viewDatabase.login;

import com.fluxcapacitor.core.util.ConnectDB;
import com.fluxcapacitor.core.util.Encrypt;
import com.fluxcapacitor.screens.viewDatabase.DatabaseView.ViewController;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import org.datafx.controller.FXMLController;
import org.datafx.controller.flow.action.ActionMethod;
import org.datafx.controller.flow.action.ActionTrigger;
import org.datafx.controller.flow.context.ActionHandler;
import org.datafx.controller.flow.context.FlowActionHandler;

import javax.annotation.PostConstruct;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Eddie
 */
@FXMLController("LoginFXML.fxml")
public class LoginController {
    @FXML
    private VBox vBoxPanel;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    @ActionTrigger("loginAction")
    private Button btnLogin;

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passField;

    @ActionHandler
    private FlowActionHandler actionHandler;
    
    private ArrayList<String> infos = new ArrayList<>();
    private String resetCss = ".setStyle(\"-fx-border-color: #004853;-fx-focus-color: #007E80 ;\");";
    private String failCSS = "-fx-border-color: #FA2A00;-fx-text-fill: #FA2A00;-fx-border-width:2px;";
    private boolean info;
    private boolean btnClicked = false;

    @PostConstruct
    public void init() {
        setupDB();
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();

        anchorPane.setTopAnchor(vBoxPanel, screenBounds.getHeight() / 8);
        anchorPane.setBottomAnchor(vBoxPanel, screenBounds.getHeight() / 7);
        anchorPane.setLeftAnchor(vBoxPanel, screenBounds.getWidth() / 4);
        anchorPane.setRightAnchor(vBoxPanel, screenBounds.getWidth() / 4);
        btnLogin.setPrefWidth(screenBounds.getWidth());

        usernameField.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (!info && btnClicked) {
                    usernameField.setStyle(resetCss);
                    passField.setStyle(resetCss);
                    usernameField.setText("");
                    passField.setText("");
                    info = true;
                    btnClicked = false;
                }
            }
        });

        passField.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (!info && btnClicked) {
                    usernameField.setStyle(resetCss);
                    passField.setStyle(resetCss);
                    usernameField.setText("");
                    passField.setText("");
                    info = true;
                    btnClicked = false;
                }
            }
        });
    }

    @ActionMethod("loginAction")
    public void btnAction() {
        info = infos.contains(Encrypt.encodeAccount(usernameField.getText().toLowerCase(), passField.getText()));
        if (!info) {
            passField.setStyle(failCSS);
            usernameField.setStyle(failCSS);
            usernameField.setText("Incorrect Username or Password");
            btnClicked = true;
        } else {
            btnLogin.setDisable(true);
            try {
                actionHandler.navigate(ViewController.class);
            } catch (Exception ex) {
                
            }
        }
    }

    private void setupDB() {
        ConnectDB db = new ConnectDB();
        db.connectToDB();
        try {
            Statement statement = db.getConnection().createStatement();
            ResultSet rs = statement.executeQuery("Select * from account");
            while(rs.next()){
                infos.add(rs.getString(2));
            }
        } catch (SQLException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
