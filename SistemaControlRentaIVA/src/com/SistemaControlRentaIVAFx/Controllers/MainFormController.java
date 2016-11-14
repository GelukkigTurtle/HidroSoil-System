
package com.SistemaControlRentaIVAFx.Controllers;


import com.SistemaControlRentaIVAFx.BusinessProcess.Calculadora;
import com.SistemaControlRentaIVAFx.BusinessProcess.PerisitenceManager;
import com.SistemaControlRentaIVAFx.DAO.ProyectoJpaController;
import com.SistemaControlRentaIVAFx.Entities.Cliente;
import com.SistemaControlRentaIVAFx.Entities.Costos;
import com.SistemaControlRentaIVAFx.Entities.Iva;

import com.SistemaControlRentaIVAFx.Entities.Proyecto;
import com.SistemaControlRentaIVAFx.Entities.User;

import com.SistemaControlRentaIVAFx.GUI.Main;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 * FXML Controller class
 *
 * @author freddy ayala
 */
public class MainFormController extends AnchorPane implements Initializable, Runnable {
   
    @FXML //  fx:id="graficoLinear"
    private LineChart <Number,Number> graficoLinear ;// Value injected by FXMLLoader
    @FXML //  fx:id="renAño"
    private ChoiceBox<?> renAño; // Value injected by FXMLLoader
    @FXML //  fx:id="ivaMes"
    private ChoiceBox<?> ivaMes; // Value injected by FXMLLoader
     @FXML //  fx:id="tablaCostos"
    private TableView<Costos> tablaCostos; // Value injected by FXMLLoader

    @FXML //  fx:id="tablaIVA"
    private TableView<?> tablaIVA; // Value injected by FXMLLoader
    

    @FXML //  fx:id="tablaRenta"
    private TableView<?> tablaRenta; // Value injected by FXMLLoader

    @FXML //  fx:id="tabCostos"
    private Tab tabCostos; // Value injected by FXMLLoader

    @FXML //  fx:id="tabImpuestos"
    private Tab tabImpuestos; // Value injected by FXMLLoader

    @FXML //  fx:id="renBtnReporte"
    private Button renBtnReporte; // Value injected by FXMLLoader
    @FXML //  fx:id="proBtnCorreo"
    private Button proBtnCorreo; // Value injected by FXMLLoader

    @FXML //  fx:id="proBtnReporte"
    private Button proBtnReporte; // Value injected by FXMLLoader
    
    @FXML //  fx:id="ivaBtnReporte"
    private Button ivaBtnReporte; // Value injected by FXMLLoader
    @FXML //  fx:id="cosBtnReporte"
    private Button cosBtnReporte; // Value injected by FXMLLoader

    @FXML //  fx:id="cosDescripcion"
    private TextArea cosDescripcion; // Value injected by FXMLLoader

    @FXML //  fx:id="cosFecha"
    private TextField cosFecha; // Value injected by FXMLLoader

    @FXML //  fx:id="cosMesIVA"
    private ChoiceBox<Iva> cosMesIVA; // Value injected by FXMLLoader

    @FXML //  fx:id="cosNombre"
    private TextField cosNombre; // Value injected by FXMLLoader

    @FXML //  fx:id="cosPagoCuenta"
    private TextField cosPagoCuenta; // Value injected by FXMLLoader

    @FXML //  fx:id="cosPagoIVA"
    private TextField cosPagoIVA; // Value injected by FXMLLoader

    @FXML //  fx:id="cosValorTotal"
    private TextField cosValorTotal; // Value injected by FXMLLoader

    @FXML //  fx:id="crudMensaje"
    private Label crudMensaje; // Value injected by FXMLLoader
    
    @FXML //  fx:id="btnPresupuesto"
    private Button btnPresupuesto; // Value injected by FXMLLoader

    @FXML //  fx:id="cliBtnReporte"
    private Button cliBtnReporte; // Value injected by FXMLLoader
    @FXML //  fx:id="cliCelular"
    private TextField cliCelular; // Value injected by FXMLLoader

    @FXML //  fx:id="cliContacto"
    private TextField cliContacto; // Value injected by FXMLLoader

    @FXML //  fx:id="cliEmpresa"
    private TextField cliEmpresa; // Value injected by FXMLLoader

    @FXML //  fx:id="cliTelefono"
    private TextField cliTelefono; // Value injected by FXMLLoader
    @FXML //  fx:id="tabAyuda"
    private Tab tabAyuda; // Value injected by FXMLLoader

    @FXML //  fx:id="tabClientes"
    private Tab tabClientes; // Value injected by FXMLLoader

    @FXML //  fx:id="tabGrafico"
    private Tab tabGrafico; // Value injected by FXMLLoader

    @FXML //  fx:id="tabProyectos"
    private Tab tabProyectos; // Value injected by FXMLLoader


    @FXML //  fx:id="btnAgregar"
    private Button btnAgregar; // Value injected by FXMLLoader

    @FXML //  fx:id="btnEditar"
    private Button btnEditar; // Value injected by FXMLLoader

    @FXML //  fx:id="btnEliminar"
    private Button btnEliminar; // Value injected by FXMLLoader
    @FXML //  fx:id="hora"
    private Label hora; // Value injected by FXMLLoader

    @FXML //  fx:id="logout"
    private Hyperlink logout; // Value injected by FXMLLoader

    @FXML //  fx:id="proCliente"
    private ChoiceBox<Cliente> proCliente; // Value injected by FXMLLoader

    @FXML //  fx:id="proDepartamento"
    private ChoiceBox<String> proDepartamento; // Value injected by FXMLLoader

    @FXML //  fx:id="proDescripcion"
    private TextArea proDescripcion; // Value injected by FXMLLoader

    @FXML //  fx:id="proDuracion"
    private TextField proDuracion; // Value injected by FXMLLoader

    @FXML //  fx:id="proEstado"
    private ChoiceBox<String> proEstado; // Value injected by FXMLLoader

    @FXML //  fx:id="proFechaFinalizacion"
    private TextField proFechaFinalizacion; // Value injected by FXMLLoader

    @FXML //  fx:id="proFechaInicio"
    private TextField proFechaInicio; // Value injected by FXMLLoader

    @FXML //  fx:id="proMunicipio"
    private ChoiceBox<String> proMunicipio; // Value injected by FXMLLoader

    @FXML //  fx:id="proNombre"
    private TextField proNombre; // Value injected by FXMLLoader

    @FXML //  fx:id="proRubro"
    private TextField proRubro; // Value injected by FXMLLoader

    @FXML //  fx:id="tablaProyectos"
    private TableView<Proyecto> tablaProyectos; // Value injected by FXMLLoader
    @FXML //  fx:id="tablaClientes"
    private TableView<Cliente> tablaClientes; // Value injected by FXMLLoader

    private EntityManagerFactory emf = null;
    private EntityManager em = null;
    ProyectoJpaController proyectoJpa;
    SimpleDateFormat formatoDelTexto = new SimpleDateFormat("aaaa/mm/dd");
    private Main application;
    String fechaActual, horas, minutos, segundos, ampm;
    Calendar calendario;    
    Thread h1 ;
    Cliente clienteCurrent = new Cliente();
    Proyecto proyectoCurrent = new Proyecto();
    Calculadora calculadora = new Calculadora();
    ObservableList<String> departamentosElSalvador = FXCollections.observableArrayList(
            "Ahuachapán","Santa Ana","Sonsonate","La Libertad","Chalatenango","San Salvador","La Paz",
            "Cuscatlán","Cabañas","San Vicente","Usulután","San Miguel","Morazán","La Unión");
    ObservableList<String> municipiosAhuachapan = FXCollections.observableArrayList(
            "Ahuachapán","Jujutla","Atiquizaya","Concepción de Ataco","El Refugio","Guaymango",
            "Apaneca","San Francisco Menéndez","San Lorenzo","San Pedro Puxtla","Tacuba","Turín");
    ObservableList<String> municipiosSantaAna = FXCollections.observableArrayList(
            "Candelaria de la Frontera","Chalchuapa","Coatepeque","El Congo","El Porvenir","Masahuat","Metapán",
            "San Antonio Pajonal","San Sebastián Salitrillo","Santa Ana","Santa Rosa Guachipilín","Santiago de la Frontera","Texistepeque");
    ObservableList<String> municipiosSonsonate = FXCollections.observableArrayList(
            "Acajutla","Armenia","Caluco","Cuisnahuat","Izalco","Juayúa","Nahuizalco","Nahulingo",
            "Salcoatitán","San Antonio del Monte","San Julián","Santa Catarina Masahuat","Santa Isabel Ishuatán","Santo Domingo de Guzmán","Sonsonate","Sonzacate");
    ObservableList<String> municipiosLaLibertad = FXCollections.observableArrayList(
            "Antiguo Cuscatlán","Chiltiupán","Ciudad Arce","Colón","Comasagua","Huizúcar","Jayaque","Jicalapa","La Libertad","Santa Tecla","Nuevo Cuscatlán",
            "San Juan Opico","Quezaltepeque","Sacacoyo","San José Villanueva","San Matías","San Pablo Tacachico","Talnique","Tamanique","Teotepeque","Tepecoyo","Zaragoza");
    ObservableList<String> municipiosChalatenango = FXCollections.observableArrayList(
            "Agua Caliente","Arcatao","Azacualpa","Cancasque","Chalatenango","Citalá","Comalapa","Concepción Quezaltepeque","Dulce Nombre de María","El Carrizal","El Paraíso",
            "La Laguna","La Palma","La Reina","Las Vueltas","Nueva Concepción","Nueva Trinidad","Nombre de Jesús","Ojos de Agua","Potonico","San Antonio de la Cruz","San Antonio Los Ranchos",
            "San Fernando","San Francisco Lempa","San Francisco Morazán","San Ignacio","San Isidro Labrador","Las Flores","San Luis del Carmen","San Miguel de Mercedes","San Rafael","Santa Rita","Tejutla");
    ObservableList<String> municipiosCuscatlan = FXCollections.observableArrayList(
            "Cojutepeque","Candelaria","El Carmen","El Rosario","Monte San Juan","Oratorio de Concepción","San Bartolomé Perulapía","San Cristóbal",
            "San José Guayabal","San Pedro Perulapán","San Rafael Cedros","San Ramón","Santa Cruz Analquito","Santa Cruz Michapa","Suchitoto","Tenancingo");
    ObservableList<String> municipiosSanSalvador =  FXCollections.observableArrayList(
            "Aguilares","Apopa","Ayutuxtepeque","Cuscatancingo","Delgado","El Paisnal","Guazapa","Ilopango",
            "Mejicanos","Nejapa","Panchimalco","Rosario de Mora","San Marcos","San Martín","San Salvador","Santiago Texacuangos","Santo Tomás","Soyapango","Tonacatepeque");
    ObservableList<String> municipiosLaPaz = FXCollections.observableArrayList(
            "Zacatecoluca","Cuyultitán","El Rosario","Jerusalén","Mercedes La Ceiba","Olocuilta","Paraíso de Osorio","San Antonio Masahuat","San Emigdio","San Francisco Chinameca",
            "San Pedro Masahuat","San Juan Nonualco","San Juan Talpa","San Juan Tepezontes","San Luis La Herradura","San Luis Talpa","San Miguel Tepezontes","San Pedro Nonualco","San Rafael Obrajuelo","Santa María Ostuma","Santiago Nonualco","Tapalhuaca");
    ObservableList<String> municipiosCabanas = FXCollections.observableArrayList(
            "Cinquera","Dolores","Guacotecti","Ilobasco","Jutiapa","San Isidro","Sensuntepeque","Tejutepeque","Victoria");
    ObservableList<String> municipiosSanVicente = FXCollections.observableArrayList(
            "Apastepeque","Guadalupe","San Cayetano Istepeque","San Esteban Catarina","San Ildefonso","San Lorenzo","San Sebastián","San Vicente","Santa Clara","Santo Domingo","Tecoluca","Tepetitán","Verapaz");
    ObservableList<String> municipiosUsulutan = FXCollections.observableArrayList(
            "Alegría","Berlín","California","Concepción Batres","El Triunfo","Ereguayquín","Estanzuelas","Jiquilisco","Jucuapa","Jucuarán","Mercedes Umaña","Nueva Granada",
            "Ozatlán","Puerto El Triunfo","San Agustín","San Buenaventura","San Dionisio","San Francisco Javier","Santa Elena","Santa María","Santiago de María","Tecapán","Usulután");
     ObservableList<String> municipiosSanMiguel = FXCollections.observableArrayList(
            "Carolina","Chapeltique","Chinameca","Chirilagua","Ciudad Barrios","Comacarán","El Tránsito","Lolotique","Moncagua","Nueva Guadalupe","Nuevo Edén de San Juan","Quelepa",
            "San Antonio","San Gerardo","San Jorge","San Luis de la Reina","San Miguel","San Rafael Oriente","Sesori","Uluazapa");
     ObservableList<String> municipiosMorazan = FXCollections.observableArrayList(
            "Arambala","Cacaopera","Chilanga","Corinto","Delicias de Concepción	","El Divisadero","El Rosario","Gualococti","Guatajiagua","Joateca","Jocoaitique",
            "Jocoro","Lolotiquillo","Meanguera","Osicala","Perquín","San Carlos","San Fernando","San Francisco Gotera","San Isidro","San Simón","Sensembra",
            "Sociedad","Torola","Yamabal","Yoloaiquín");
     ObservableList<String> municipiosLaUnion = FXCollections.observableArrayList(
            "La Unión","San Alejo","Yucuaiquín","Conchagua","Intipucá","San José","El Carmen","Yayantique","Bolívar","Meanguera del Golfo","Santa Rosa de Lima","Pasaquina",
            "Anamoros","Nueva Esparta","El Sauce","Concepción de Oriente","Polorós","Lislique");
    
      ObservableList<String> estados = FXCollections.observableArrayList("ACTIVO","PAUSADO","FINALIZADO","CANCELADO");
     /**
     * Initializes the controller class.
     * 
     */
      
    String reportPath = "C:\\Users\\Freddy Sebastian\\Documents\\Workspaces\\TurtleSoftware\\Project Manager HS\\SistemaControlRentaIVA\\";
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         hora.setText(getHoraCompleta());
         cosPagoCuenta.setDisable(true);
         cosPagoIVA.setDisable(true);
         cargarDatosCliente();
         cargarDatosProyecto(); 
         cargarDatosCostos();
       
         
         ObservableList <String> deptoList = FXCollections.observableArrayList(departamentosElSalvador);
         proDepartamento.setItems(deptoList);
         ObservableList <String> estadoList = FXCollections.observableArrayList(estados);
         proEstado.setItems(estadoList);
         
         btnAgregar.setOnAction(new EventHandler<ActionEvent>(){
             
            @Override
            public void handle(ActionEvent arg0) {
              
                 
                if(tabProyectos.isSelected()){
                em.getTransaction().begin();
                Proyecto proyecto = new Proyecto();
                proyecto.setProNombre(proNombre.getText());
                proyecto.setProRubro(proRubro.getText());
                proyecto.setProDescripcion(proDescripcion.getText());
                proyecto.setProDuracion(Integer.parseInt(proDuracion.getText()));
                proyecto.setProEstado(proEstado.getValue());
                proyecto.setProDepartamento(proDepartamento.getValue());
                proyecto.setProMunicipio(proMunicipio.getValue());
                proyecto.setClienteCliId(proCliente.getValue());
                proyecto.setProFechaInicio(proFechaInicio.getText());
                proyecto.setProFechaFin(proFechaFinalizacion.getText());
                em.persist(proyecto);
                em.getTransaction().commit();
                cargarDatosProyecto();
                }
                if (tabClientes.isSelected()){
                em.getTransaction().begin();
                Cliente cliente = new Cliente();
                cliente.setCliEmpresa(cliEmpresa.getText());
                cliente.setCliContacto(cliContacto.getText());
                cliente.setCliTelefono(cliTelefono.getText());
                cliente.setCliCelular(cliCelular.getText());
                em.persist(cliente);
                em.getTransaction().commit();
                cargarDatosCliente();
                } 
                if (tabCostos.isSelected()){
                em.getTransaction().begin();
                Costos costo= new Costos();
              
                costo.setCosNombre(cosNombre.getText());
                costo.setCosDescripcion(cosDescripcion.getText());
                costo.setCosFecha(cosFecha.getText());
                
                Double costoValor = Double.parseDouble(cosValorTotal.getText());
                costo.setCosValor(costoValor);
                
                Double pagoAbonoCuenta = calculadora.calcularAbonoPagoCuenta(costoValor);
                costo.setCosPagoCuenta(pagoAbonoCuenta);
                cosPagoCuenta.setText(pagoAbonoCuenta.toString());
                
                Double pagoIva = calculadora.calcularPagoIvaCosto(costoValor);
                costo.setCosPagoIva(pagoIva);
                cosPagoIVA.setText(pagoIva.toString());
                costo.setIvaIvaId(cosMesIVA.getValue());
                em.persist(costo);
                em.getTransaction().commit();
                cargarDatosCostos();
                }  
            }
         });
         
         btnEliminar.setOnAction(new EventHandler<ActionEvent>(){

            @Override
            public void handle(ActionEvent arg0) {
                
                if(tabProyectos.isSelected()){
                em.getTransaction().begin();
                Proyecto proyecto = getSelectedProyecto();
                int proyectoEliminado = em.createQuery("DELETE FROM Proyecto p WHERE p.proId = :proId").setParameter("proId",proyecto.getProId()).executeUpdate();
                em.remove(proyecto);
                em.getTransaction().commit();
                cargarDatosProyecto();
                }
                if (tabClientes.isSelected()){
                em.getTransaction().begin();
                Cliente cliente = getSelectedCliente();
                int clienteEliminado = em.createQuery("DELETE FROM Cliente c WHERE c.cliId = :cliId").setParameter("cliId",cliente.getCliId()).executeUpdate();
                em.remove(cliente);
                em.getTransaction().commit();
                cargarDatosCliente();
                }
                if (tabCostos.isSelected()){
                em.getTransaction().begin();
                Costos costo = getSelectedCosto();
                int costoEliminado = em.createQuery("DELETE FROM Costos c WHERE c.cosId = :cosId").setParameter("cosId",costo.getCosId()).executeUpdate();
                em.remove(costo);
                em.getTransaction().commit();
                cargarDatosCostos();
                }
               
            }
         });
         
         btnEditar.setOnAction(new EventHandler<ActionEvent>(){

            @Override
            public void handle(ActionEvent arg0) {
                
                if(tabProyectos.isSelected()){
                    em.getTransaction().begin();
                    Proyecto proyecto = getSelectedProyecto();
                    proyecto.setProNombre(proNombre.getText());
                    proyecto.setProRubro(proRubro.getText());
                    proyecto.setProDescripcion(proDescripcion.getText());
                    proyecto.setProDuracion(Integer.parseInt(proDuracion.getText()));
                    proyecto.setProEstado(proEstado.getValue());
                    proyecto.setProDepartamento(proDepartamento.getValue());
                    proyecto.setProMunicipio(proMunicipio.getValue());
                    proyecto.setClienteCliId(proCliente.getValue());
                    proyecto.setProFechaInicio(proFechaInicio.getText());
                    proyecto.setProFechaFin(proFechaFinalizacion.getText());
                    em.getTransaction().commit();
                    cargarDatosProyecto();
                
                }
                if (tabClientes.isSelected()){
               
                 em.getTransaction().begin();
                Cliente cliente = getSelectedCliente();
                cliente.setCliContacto(cliContacto.getText());
                cliente.setCliEmpresa(cliEmpresa.getText());
                cliente.setCliCelular(cliCelular.getText());
                cliente.setCliTelefono(cliTelefono.getText());
                em.getTransaction().commit();
                cargarDatosCliente();
                }
                 if (tabCostos.isSelected()){
                em.getTransaction().begin();
                Costos costo= getSelectedCosto();
                costo.setCosNombre(cosNombre.getText());
                costo.setCosDescripcion(cosDescripcion.getText());
                costo.setCosFecha(cosFecha.getText());
                
                Double costoValor = Double.parseDouble(cosValorTotal.getText());
                costo.setCosValor(costoValor);
                
                Double pagoAbonoCuenta = calculadora.calcularAbonoPagoCuenta(costoValor);
                costo.setCosPagoCuenta(pagoAbonoCuenta);
                cosPagoCuenta.setText(pagoAbonoCuenta.toString());
                
                Double pagoIva = calculadora.calcularPagoIvaCosto(costoValor);
                costo.setCosPagoIva(pagoIva);
                cosPagoIVA.setText(pagoIva.toString());
                costo.setIvaIvaId(cosMesIVA.getValue());
               
                em.getTransaction().commit();
                cargarDatosCostos();
                } 
               if(tabGrafico.isSelected()){
             actualizarGrafico();
         }
                
            }
         });
        
        proDepartamento.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>(){

            @Override
            public void changed(ObservableValue<? extends String> ov, String t, String t1) {
      
	  if (t1.equals("Ahuachapán")){
                ObservableList <String> muniList = FXCollections.observableArrayList(municipiosAhuachapan);
                proMunicipio.setItems(muniList);
           }
         else if(t1.equals("Santa Ana")){
                ObservableList <String> muniList = FXCollections.observableArrayList(municipiosSantaAna);
                proMunicipio.setItems(muniList);
           }
         else if(t1.equals("Sonsonate")){
                ObservableList <String> muniList = FXCollections.observableArrayList(municipiosSonsonate);
                proMunicipio.setItems(muniList);
           }
         else if(t1.equals("Usulután")){
                ObservableList <String> muniList = FXCollections.observableArrayList(municipiosUsulutan);
                proMunicipio.setItems(muniList);
           }
          else if(t1.equals("San Miguel")){
                ObservableList <String> muniList = FXCollections.observableArrayList(municipiosSanMiguel);
                proMunicipio.setItems(muniList);
           }
           else if(t1.equals("Morazán")){
                ObservableList <String> muniList = FXCollections.observableArrayList(municipiosMorazan);
                proMunicipio.setItems(muniList);
           }
           else if(t1.equals("La Unión")){
                ObservableList <String> muniList = FXCollections.observableArrayList(municipiosLaUnion);
                proMunicipio.setItems(muniList);
           }
           else if(t1.equals("La Libertad")){
                ObservableList <String> muniList = FXCollections.observableArrayList(municipiosLaLibertad);
                proMunicipio.setItems(muniList);
           }
           else if(t1.equals("Chalatenango")){
                ObservableList <String> muniList = FXCollections.observableArrayList(municipiosChalatenango);
                proMunicipio.setItems(muniList);
           }
           else if(t1.equals("Cuscatlán")){
                ObservableList <String> muniList = FXCollections.observableArrayList(municipiosCuscatlan);
                proMunicipio.setItems(muniList);
           }
           else if(t1.equals("San Salvador")){
                ObservableList <String> muniList = FXCollections.observableArrayList(municipiosSanSalvador);
                proMunicipio.setItems(muniList);
           }
           else if(t1.equals("La Paz")){
                ObservableList <String> muniList = FXCollections.observableArrayList(municipiosLaPaz);
                proMunicipio.setItems(muniList);
           }
           else if(t1.equals("Cabañas")){
                ObservableList <String> muniList = FXCollections.observableArrayList(municipiosCabanas);
                proMunicipio.setItems(muniList);
           }
           else if(t1.equals("San Vicente")){
                ObservableList <String> muniList = FXCollections.observableArrayList(municipiosSanVicente);
                proMunicipio.setItems(muniList);
           }
         else{
              proMunicipio.setItems(null);
            }
            }
           
       });
        
        proBtnReporte.setOnAction(new EventHandler<ActionEvent>(){

            @Override
            public void handle(ActionEvent t) {
                try {
                    reporteProyectos();
                } catch (JRException ex) {
                    Logger.getLogger(MainFormController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(MainFormController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
        });
        cliBtnReporte.setOnAction(new EventHandler<ActionEvent>(){

            @Override
            public void handle(ActionEvent t) {
                try {
                    reporteClientes();
                } catch (JRException ex) {
                    Logger.getLogger(MainFormController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(MainFormController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
        });
               
    }
    
      public void calcularPresupuesto(ActionEvent event) {
         application.goToPresupuesto();
    }
     

    //Metodo para seleccionar registros

    public Cliente getSelectedCliente (){
        if(tablaClientes != null){
           List<Cliente> selectedPersons = tablaClientes.getSelectionModel().getSelectedItems(); 
            if (selectedPersons.size() == 1) {
                final Cliente selectedPerson = selectedPersons.get(0);
                return selectedPerson;
            }
        }
        return null;
    }
    public Proyecto getSelectedProyecto(){
        if(tablaProyectos != null){
            List<Proyecto> selectedProyectos = tablaProyectos.getSelectionModel().getSelectedItems();
            if (selectedProyectos.size() == 1){
                final Proyecto selectedProyecto = selectedProyectos.get(0);
                return selectedProyecto;
            }
        }
        return null;
    }
    public Costos getSelectedCosto (){
        if(tablaCostos != null){
           List<Costos> selectedCostos = tablaCostos.getSelectionModel().getSelectedItems(); 
            if (selectedCostos.size() == 1) {
                final Costos selectedcosto = selectedCostos.get(0);
                return selectedcosto;
            }
        }
        return null;
    }
    
    private void  actualizarDatosCliente (){
         final Cliente selectedCliente = getSelectedCliente();
         if (selectedCliente != null){
             if(cliContacto != null){
                 cliContacto.setText(selectedCliente.getCliContacto());
             }
             if (cliEmpresa != null){
                 cliEmpresa.setText(selectedCliente.getCliEmpresa());
             }
             if (cliCelular != null){
                 cliCelular.setText(selectedCliente.getCliCelular());
             }
             if(cliTelefono != null){
             cliTelefono.setText(selectedCliente.getCliTelefono());
            } 
         }
   }
     private void  actualizarDatosCostos (){
         final Costos selectedCosto = getSelectedCosto();
         if (selectedCosto != null){
             if(cosNombre != null){
                 cosNombre.setText(selectedCosto.getCosNombre());
             }
             if(cosDescripcion != null){
                 cosDescripcion.setText(selectedCosto.getCosDescripcion());
             }
             if(cosFecha != null){
                 cosFecha.setText(selectedCosto.getCosFecha());
             }
             if(cosValorTotal != null){
                 cosValorTotal.setText(selectedCosto.getCosValor().toString());
             }
             if(cosPagoIVA != null){
                 cosPagoIVA.setText(selectedCosto.getCosPagoIva().toString());
             }
             if(cosPagoCuenta != null){
                 cosPagoCuenta.setText(selectedCosto.getCosPagoCuenta().toString());
             }
              if(cosMesIVA != null){
                 cosMesIVA.setValue(selectedCosto.getIvaIvaId());
             }
         }
   }
    private void actualizarDatosProyecto (){
        final Proyecto selectedProyecto = getSelectedProyecto();
       if (selectedProyecto != null){
         if(proNombre != null){ 
            proNombre.setText(selectedProyecto.getProNombre());
         }
       
         if(proRubro != null){ 
            proRubro.setText(selectedProyecto.getProRubro());
         }
         if(proDescripcion != null){ 
            proDescripcion.setText(selectedProyecto.getProDescripcion());
         }
         if(proFechaInicio != null){ 
            proFechaInicio.setText(selectedProyecto.getProFechaInicio());
         }
         if(proFechaFinalizacion != null){ 
            
          proFechaFinalizacion.setText(selectedProyecto.getProFechaFin());
         }
          if(proDuracion != null){ 
            proDuracion.setText(selectedProyecto.getProDuracion().toString());
         }
         if(proDuracion != null){ 
            proDuracion.setText(selectedProyecto.getProDuracion().toString());
         }
         if(proCliente != null){ 
            proCliente.setValue(selectedProyecto.getClienteCliId());
         }
         if(proDepartamento != null){ 
            proDepartamento.setValue(selectedProyecto.getProDepartamento());
         }
          if(proMunicipio != null){ 
            proMunicipio.setValue(selectedProyecto.getProMunicipio());
         }
         if(proEstado != null){ 
            proEstado.setValue(selectedProyecto.getProEstado());
         
         }

        }  
    }
    
    
     private final ListChangeListener<Cliente> tableSelectionChangedCliente =
            new ListChangeListener<Cliente>() {

                @Override
                public void onChanged(ListChangeListener.Change<? extends Cliente> c) {
                   actualizarDatosCliente();
                }
            };
     private final ListChangeListener<Proyecto> tableSelectionChangedProyecto =
            new ListChangeListener<Proyecto>() {

                @Override
                public void onChanged(ListChangeListener.Change<? extends Proyecto> p) {
                   actualizarDatosProyecto();
                }
            };
      private final ListChangeListener<Costos> tableSelectionChangedCostos =
            new ListChangeListener<Costos>() {

                @Override
                public void onChanged(ListChangeListener.Change<? extends Costos> c) {
                   actualizarDatosCostos();
                }
            };
    
    private void cargarDatosProyecto(){
        emf = Persistence.createEntityManagerFactory("SistemaControlRentaIVAPU");
        em = emf.createEntityManager();
        
         try {
             em.getTransaction().begin();
            //Select all the record from student table
            Query query = em.createQuery("SELECT p FROM Proyecto p");
            List lst = query.getResultList();
            Iterator it = lst.iterator();
            configurarTablaProyectos();
            ObservableList <Proyecto> proyectoLista = FXCollections.observableArrayList();
            while (it.hasNext()) {
                Proyecto proyecto= (Proyecto) it.next(); 
                //System.out.print("Name:" + proyecto.getProNombre());
                proyectoLista.add(proyecto);
            } 
              tablaProyectos.setItems(proyectoLista);  
              em.getTransaction().commit();
              
          
           
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    private void cargarDatosCliente(){
        emf = Persistence.createEntityManagerFactory("SistemaControlRentaIVAPU");
        em = emf.createEntityManager();
        
         try {
             em.getTransaction().begin();
            //Select all the record from student table
            Query query = em.createQuery("SELECT c FROM Cliente c");
            List lst = query.getResultList();
            Iterator it = lst.iterator();
            configurarTablaClientes();
            ObservableList <Cliente> clienteLista = FXCollections.observableArrayList();
            while (it.hasNext()) {
                Cliente cliente = (Cliente) it.next(); 
                //System.out.print("Name:" + proyecto.getProNombre());
                clienteLista.add(cliente);
            } 
              tablaClientes.setItems(clienteLista);
            
                      
              em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
     private void cargarDatosCostos(){
        emf = Persistence.createEntityManagerFactory("SistemaControlRentaIVAPU");
        em = emf.createEntityManager();
        
         try {
             em.getTransaction().begin();
            //Select all the record from student table
            Query query = em.createQuery("SELECT c FROM Costos c");
            List lst = query.getResultList();
            Iterator it = lst.iterator();
            configurarTablaCostos();
            ObservableList <Costos> costoLista = FXCollections.observableArrayList();
            while (it.hasNext()) {
                Costos costo = (Costos) it.next(); 
                //System.out.print("Name:" + proyecto.getProNombre());
                costoLista.add(costo);
            } 
              tablaCostos.setItems(costoLista);
            
                      
              em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    private String getHoraCompleta (){
       String horaCompleta;
       String dia, mes , ano , horas, minutos, segundos;
       Calendar calendario = Calendar.getInstance();
       dia = Integer.toString(calendario.get(Calendar.DAY_OF_MONTH));
       mes = Integer.toString(calendario.get(Calendar.MONTH) + 1);
       ano = Integer.toString(calendario.get(Calendar.YEAR));
       horas = Integer.toString(calendario.get(Calendar.HOUR_OF_DAY));
       minutos = Integer.toString(calendario.get(Calendar.MINUTE));
 

       horaCompleta = dia +"/"+ mes + "/"+ano + "  Hora: "+ horas + ":" + minutos;
       return horaCompleta;
    
    }

    public void setApp(Main application){
        this.application = application;
        User loggedUser = application.getLoggedUser();
  
    }
    
    public void agregarProyecto (ActionEvent event){
        application.nuevoProyecto();
        
    }
     public void processLogout(ActionEvent event) {
        if (application == null){
            // We are running in isolated FXML, possibly in Scene Builder.
            // NO-OP.
            return;
            
        }
     
        application.userLogout();
    }
     
      public void Principal() {
        h1 = new Thread(this);
        h1.start();
        setVisible(true);
    }
    @Override
    public void run(){
        Principal();
        Thread ct = Thread.currentThread();
        while(ct == h1) {   
            calcula();
            hora.setText(horas + ":" + minutos + ":" + segundos + " "+ampm);
            try {
                Thread.sleep(1000);
            }
            catch(InterruptedException e) {}
        }
    }
    
    public void calcula () {        
        Calendar calendario = new GregorianCalendar();
        Date fechaHoraActual = new Date();

        calendario.setTime(fechaHoraActual);
        ampm = calendario.get(Calendar.AM_PM)==Calendar.AM?"AM":"PM";
        if(ampm.equals("PM")){
            int h = calendario.get(Calendar.HOUR_OF_DAY)-12;
            horas = h>9?""+h:"0"+h;
        }else{
            horas = calendario.get(Calendar.HOUR_OF_DAY)>9?""+calendario.get(Calendar.HOUR_OF_DAY):"0"+calendario.get(Calendar.HOUR_OF_DAY);            
        }
        minutos = calendario.get(Calendar.MINUTE)>9?""+calendario.get(Calendar.MINUTE):"0"+calendario.get(Calendar.MINUTE);
        segundos = calendario.get(Calendar.SECOND)>9?""+calendario.get(Calendar.SECOND):"0"+calendario.get(Calendar.SECOND); 
    }
      private void configurarTablaProyectos() {
        
         tablaProyectos.setTableMenuButtonVisible(true);
        
        TableColumn pro_nombre = new TableColumn<Proyecto, String>("Nombre");
        pro_nombre.setCellValueFactory(new PropertyValueFactory("proNombre"));
        pro_nombre.setPrefWidth(200);
        
        TableColumn pro_estado = new TableColumn<Proyecto, String>("Estado");
        pro_estado.setCellValueFactory(new PropertyValueFactory("proEstado"));
        pro_estado.setPrefWidth(100);
        
        TableColumn pro_fecha_inicio = new TableColumn<Proyecto, Date>("Fecha de Inicio");
        pro_fecha_inicio.setCellValueFactory(new PropertyValueFactory("proFechaInicio"));
        pro_fecha_inicio.setPrefWidth(150);
        
      
        TableColumn pro_cliente = new TableColumn<Proyecto, String>("Cliente");
        pro_cliente.setCellValueFactory(new PropertyValueFactory("clienteCliId"));
        pro_cliente.setPrefWidth(100);
        
        TableColumn pro_rubro = new TableColumn<Proyecto, String>("Rubro");
        pro_rubro.setCellValueFactory(new PropertyValueFactory("proRubro"));
        pro_rubro.setPrefWidth(100);
        
        
        TableColumn pro_departamento = new TableColumn<Proyecto, String>("Departamento");
        pro_departamento.setCellValueFactory(new PropertyValueFactory("proDepartamento"));
        pro_departamento.setPrefWidth(100);
        
        TableColumn pro_municipio = new TableColumn<Proyecto, String>("Municipio");
        pro_municipio .setCellValueFactory(new PropertyValueFactory("proMunicipio"));
        pro_municipio .setPrefWidth(100);
   
        TableColumn pro_descripcion = new TableColumn<Proyecto, String>("Descripcion");
        pro_descripcion.setCellValueFactory(new PropertyValueFactory("proDescripcion"));
        pro_descripcion.setPrefWidth(300);
        
        TableColumn pro_duracion = new TableColumn<Proyecto, Integer>("Duracion");
        pro_duracion.setCellValueFactory(new PropertyValueFactory("proDuracion"));
        pro_duracion.setPrefWidth(100);
          
        
        TableColumn pro_fecha_fin = new TableColumn<Proyecto, Date>("Fecha de Finalizacion");
        pro_fecha_fin.setCellValueFactory(new PropertyValueFactory("proFechaFin"));
        pro_fecha_fin.setPrefWidth(150);

        if(tablaProyectos.getColumns().isEmpty()){
            tablaProyectos.getColumns().addAll(pro_nombre,pro_estado,pro_cliente,pro_fecha_inicio,pro_rubro,pro_departamento,pro_municipio,pro_descripcion,pro_duracion,pro_fecha_fin);
        }else{
            tablaProyectos.getColumns().clear();
            tablaProyectos.getColumns().addAll(pro_nombre,pro_estado,pro_cliente,pro_fecha_inicio,pro_rubro,pro_departamento,pro_municipio,pro_descripcion,pro_duracion,pro_fecha_fin);
        }
        final ObservableList<Proyecto> tableSelection = tablaProyectos.getSelectionModel().getSelectedItems(); 
        tableSelection.addListener(tableSelectionChangedProyecto);
    }
      
      private void configurarTablaClientes(){
        tablaClientes.setTableMenuButtonVisible(true);
        
          
        TableColumn cli_empresa = new TableColumn<Cliente, String>("Empresa");
        cli_empresa.setCellValueFactory(new PropertyValueFactory("cliEmpresa"));
        cli_empresa.setPrefWidth(250);
        
        TableColumn cli_contacto = new TableColumn<Cliente, String>("Persona Contacto");
        cli_contacto.setCellValueFactory(new PropertyValueFactory("cliContacto"));
        cli_contacto.setPrefWidth(300);
       
        TableColumn cli_telefono = new TableColumn<Cliente, String>("Telefono");
        cli_telefono.setCellValueFactory(new PropertyValueFactory("cliTelefono"));
        cli_telefono.setPrefWidth(100);
        
        TableColumn cli_celular = new TableColumn<Cliente, String>("Celular");
        cli_celular .setCellValueFactory(new PropertyValueFactory("cliCelular"));
        cli_celular .setPrefWidth(100);
        
        if(tablaClientes.getColumns().isEmpty()){
           tablaClientes.getColumns().addAll(cli_empresa,cli_contacto,cli_telefono,cli_celular);
        }else{
           tablaClientes.getColumns().clear();
           tablaClientes.getColumns().addAll(cli_empresa,cli_contacto,cli_telefono,cli_celular);
        }
        
        
        
         List <Cliente> clienteEntityList = em.createQuery("SELECT c FROM Cliente c").getResultList();
         ObservableList <Cliente> cliList = FXCollections.observableArrayList(clienteEntityList);
         proCliente.setItems(cliList);
        
         
         final ObservableList<Cliente> tableSelection = tablaClientes.getSelectionModel().getSelectedItems();
         tableSelection.addListener(tableSelectionChangedCliente);
      }
      
        private void configurarTablaCostos(){
        tablaCostos.setTableMenuButtonVisible(true);
        TableColumn cos_nombre = new TableColumn<Costos, String>("Nombre");
        cos_nombre.setCellValueFactory(new PropertyValueFactory("cosNombre"));
        cos_nombre.setPrefWidth(200);
        
        TableColumn cos_descripcion = new TableColumn<Costos, String>("Descripcion");
        cos_descripcion.setCellValueFactory(new PropertyValueFactory("cosDescripcion"));
        cos_descripcion.setPrefWidth(300);
       
        TableColumn cos_valor = new TableColumn<Costos, String>("Valor");
        cos_valor.setCellValueFactory(new PropertyValueFactory("cosValor"));
        cos_valor.setPrefWidth(150);
        
        TableColumn cos_fecha = new TableColumn<Costos, String>("Fecha");
        cos_fecha .setCellValueFactory(new PropertyValueFactory("cosFecha"));
        cos_fecha .setPrefWidth(100);
        
        if(tablaCostos.getColumns().isEmpty()){
           tablaCostos.getColumns().addAll(cos_nombre,cos_valor,cos_fecha,cos_descripcion);
        }else{
           tablaCostos.getColumns().clear();
           tablaCostos.getColumns().addAll(cos_nombre,cos_valor,cos_fecha,cos_descripcion);
        }
        
        
        
         List <Iva> ivaEntityList = em.createQuery("SELECT i FROM Iva i").getResultList();
         ObservableList <Iva> ivaList = FXCollections.observableArrayList(ivaEntityList);
         cosMesIVA.setItems(ivaList);
        
         final ObservableList<Costos> tableSelection = tablaCostos.getSelectionModel().getSelectedItems();
         tableSelection.addListener(tableSelectionChangedCostos);
      }
     public void actualizarGrafico(){

    }
     //SECCION DE REPORTES
     
     public void reporteClientes() throws JRException, IOException {  
            Stage stage = null;
            EntityManager em = PerisitenceManager.getEntityManager();
            FileChooser fileChooser = new FileChooser();
            Query query= em.createQuery("select c from Cliente c");
            List<Cliente> listOfFamipad =(List<Cliente>)query.getResultList();
            //String reportPath =  "C:/Users/Baby Mature/Dropbox/Universidad Albert Einstein/Año 3/Ingenieria en Sistemas II/Sistema/SistemaControlRentaIVA/src/";
            JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(listOfFamipad);        
            String reportName =  reportPath+ "src\\com\\SistemaControlRentaIVAFx\\Reports\\reporteClientes.jasper";
            HashMap params = new HashMap();
            JasperPrint jasperPrint = JasperFillManager.fillReport(reportName, params, beanCollectionDataSource);
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("PDF files (*.pdf)", "*.pdf");
            fileChooser.getExtensionFilters().add(extFilter);
            File file = fileChooser.showSaveDialog(stage);
            file.getName().concat(".pdf");
            OutputStream output = new FileOutputStream(file); 
            JasperExportManager.exportReportToPdfStream(jasperPrint, output);
            output.flush();
            output.close();           
    }  
     
      public void reporteProyectos() throws JRException, IOException {  
            Stage stage = null;
            EntityManager em = PerisitenceManager.getEntityManager();
            FileChooser fileChooser = new FileChooser();
            Query query= em.createQuery("SELECT p FROM Proyecto p");
            List<Proyecto> listOfFamipad =(List<Proyecto>)query.getResultList();
            //String reportPath =  "C:/Users/Baby Mature/Dropbox/Universidad Albert Einstein/Año 3/Ingenieria en Sistemas II/Sistema/SistemaControlRentaIVA/src/";
            JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(listOfFamipad);        
            String reportName =  reportPath+ "src\\com\\SistemaControlRentaIVAFx\\Reports\\reporteProyectos.jasper";
            HashMap params = new HashMap();
            JasperPrint jasperPrint = JasperFillManager.fillReport(reportName, params, beanCollectionDataSource);
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("PDF files (*.pdf)", "*.pdf");
            fileChooser.getExtensionFilters().add(extFilter);
            File file = fileChooser.showSaveDialog(stage);
            file.getName().concat(".pdf");
            OutputStream output = new FileOutputStream(file); 
            JasperExportManager.exportReportToPdfStream(jasperPrint, output);
            output.flush();
            output.close();
    }  
      

}
