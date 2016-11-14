/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.SistemaControlRentaIVAFx.Controllers;

import com.SistemaControlRentaIVAFx.Entities.User;
import com.SistemaControlRentaIVAFx.GUI.Main;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author freddy ayala
 */
public class NuevoProyectoController implements Initializable {

   @FXML //  fx:id="MunicipioList"
    private ChoiceBox<?> MunicipioList; // Value injected by FXMLLoader

    @FXML //  fx:id="btnGuardar"
    private Button btnGuardar; // Value injected by FXMLLoader

    @FXML //  fx:id="btnLimpiar"
    private Button btnLimpiar; // Value injected by FXMLLoader

    @FXML //  fx:id="btnPresupuesto"
    private Button btnPresupuesto; // Value injected by FXMLLoader

    @FXML //  fx:id="btnSalir"
    private Button btnSalir; // Value injected by FXMLLoader

    @FXML //  fx:id="celular"
    private TextField celular; // Value injected by FXMLLoader

    @FXML //  fx:id="contacto"
    private TextField contacto; // Value injected by FXMLLoader

    @FXML //  fx:id="departamentoList"
    private ChoiceBox<?> departamentoList; // Value injected by FXMLLoader

    @FXML //  fx:id="descripcionField"
    private TextArea descripcionField; // Value injected by FXMLLoader

    @FXML //  fx:id="duracionDias"
    private TextField duracionDias; // Value injected by FXMLLoader

    @FXML //  fx:id="empresa"
    private TextField empresa; // Value injected by FXMLLoader

    @FXML //  fx:id="fechaDeInicio"
    private TextField fechaDeInicio; // Value injected by FXMLLoader

    @FXML //  fx:id="nombre"
    private TextField nombre; // Value injected by FXMLLoader

    @FXML //  fx:id="rubro"
    private TextField rubro; // Value injected by FXMLLoader

    @FXML //  fx:id="telefono"
    private TextField telefono; // Value injected by FXMLLoader

    
   private Main application;
   
    public void setApp(Main application){
        this.application = application;
        
    }

    // Handler for Button[fx:id="btnPresupuesto"] onAction
    public void calcularPresupuesto(ActionEvent event) {
         application.goToPresupuesto();
    }

    // Handler for Button[fx:id="btnGuardar"] onAction
    public void guardar(ActionEvent event) {
        // handle the event here
    }

    // Handler for Button[fx:id="btnLimpiar"] onAction
    public void limpiar(ActionEvent event) {
        // handle the event here
    }

    // Handler for Button[fx:id="btnSalir"] onAction
    public void salir(ActionEvent event) {
        application.backToMain();
    }

    @Override // This method is called by the FXMLLoader when initialization is complete
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        assert MunicipioList != null : "fx:id=\"MunicipioList\" was not injected: check your FXML file 'NuevoProyecto.fxml'.";
        assert btnGuardar != null : "fx:id=\"btnGuardar\" was not injected: check your FXML file 'NuevoProyecto.fxml'.";
        assert btnLimpiar != null : "fx:id=\"btnLimpiar\" was not injected: check your FXML file 'NuevoProyecto.fxml'.";
        assert btnPresupuesto != null : "fx:id=\"btnPresupuesto\" was not injected: check your FXML file 'NuevoProyecto.fxml'.";
        assert btnSalir != null : "fx:id=\"btnSalir\" was not injected: check your FXML file 'NuevoProyecto.fxml'.";
        assert celular != null : "fx:id=\"celular\" was not injected: check your FXML file 'NuevoProyecto.fxml'.";
        assert contacto != null : "fx:id=\"contacto\" was not injected: check your FXML file 'NuevoProyecto.fxml'.";
        assert departamentoList != null : "fx:id=\"departamentoList\" was not injected: check your FXML file 'NuevoProyecto.fxml'.";
        assert descripcionField != null : "fx:id=\"descripcionField\" was not injected: check your FXML file 'NuevoProyecto.fxml'.";
        assert duracionDias != null : "fx:id=\"duracionDias\" was not injected: check your FXML file 'NuevoProyecto.fxml'.";
        assert empresa != null : "fx:id=\"empresa\" was not injected: check your FXML file 'NuevoProyecto.fxml'.";
        assert fechaDeInicio != null : "fx:id=\"fechaDeInicio\" was not injected: check your FXML file 'NuevoProyecto.fxml'.";
        assert nombre != null : "fx:id=\"nombre\" was not injected: check your FXML file 'NuevoProyecto.fxml'.";
        assert rubro != null : "fx:id=\"rubro\" was not injected: check your FXML file 'NuevoProyecto.fxml'.";
        assert telefono != null : "fx:id=\"telefono\" was not injected: check your FXML file 'NuevoProyecto.fxml'.";
      
        // initialize your logic here: all @FXML variables will have been injected

    } 
}
