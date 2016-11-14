
package com.SistemaControlRentaIVAFx.Controllers;

import com.SistemaControlRentaIVAFx.GUI.Main;
import com.SistemaControlRentaIVAFx.GUI.Main;

import java.awt.Color;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

/**
 * autor freddy ayala
 */
public class LoginController extends AnchorPane implements Initializable {

    @FXML
    TextField userId;
    @FXML
    PasswordField password;
    @FXML
    Button login;
    @FXML
    Label errorMessage;

    private Main application;
    
    
    public void setApp(Main application){
        this.application = application;
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        errorMessage.setText("");
        userId.setPromptText("");
        password.setPromptText("");
        
    }
    
    
    public void processLogin(ActionEvent event) {
        if (application == null){
            // We are running in isolated FXML, possibly in Scene Builder.
            // NO-OP.
          
        } else {
            if (!application.userLogging(userId.getText(), password.getText())){
                errorMessage.setText("Usuario o Contraseña Incorrectos");
            }else{
                  errorMessage.setText("Bienvenido " + userId.getText());
            }
        }
    }
    public void processExit (ActionEvent event){
        System.exit(1);
    }
}
