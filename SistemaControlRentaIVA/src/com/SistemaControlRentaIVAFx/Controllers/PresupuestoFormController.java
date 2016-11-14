/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.SistemaControlRentaIVAFx.Controllers;

import com.SistemaControlRentaIVAFx.BusinessProcess.Calculadora;
import com.SistemaControlRentaIVAFx.BusinessProcess.PerisitenceManager;
import com.SistemaControlRentaIVAFx.Entities.Cliente;
import com.SistemaControlRentaIVAFx.Entities.Iva;
import com.SistemaControlRentaIVAFx.Entities.Presupuesto;
import com.SistemaControlRentaIVAFx.Entities.Proyecto;
import com.SistemaControlRentaIVAFx.Entities.User;
import com.SistemaControlRentaIVAFx.GUI.Main;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
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
public class PresupuestoFormController implements Initializable {
    @FXML //  fx:id="valorGananciaNeta"
    private TextField valorGananciaNeta; // Value injected by FXMLLoader
    
    @FXML //  fx:id="btnReporte"
    private Button btnReporte; // Value injected by FXMLLoader
    @FXML //  fx:id="valorUtilidadNeta"
    private TextField valorUtilidadNeta; // Value injected by FXMLLoader
    @FXML //  fx:id="valorPagoCuenta"
    private TextField valorPagoCuenta; // Value injected by FXMLLoader

    @FXML //  fx:id="valorPagoIva"
    private TextField valorPagoIva; // Value injected by FXMLLoader

    @FXML //  fx:id="preProyecto"
    private ChoiceBox<Proyecto> preProyecto; // Value injected by FXMLLoader

    @FXML //  fx:id="btnCancelar"
    private Button btnCancelar; // Value injected by FXMLLoader

    @FXML //  fx:id="btnGuardar"
    private Button btnGuardar; // Value injected by FXMLLoader
   @FXML //  fx:id="btnModificar"
    private Button btnModificar; // Value injected by FXMLLoader
   
    @FXML //  fx:id="btnLimpiar"
    private Button btnLimpiar; // Value injected by FXMLLoader

    @FXML //  fx:id="numCuotas"
    private TextField numCuotas; // Value injected by FXMLLoader

    @FXML //  fx:id="opContado"
    private RadioButton opContado; // Value injected by FXMLLoader

    @FXML //  fx:id="opCredito"
    private RadioButton opCredito; // Value injected by FXMLLoader

    @FXML //  fx:id="valorCombustible"
    private TextField valorCombustible; // Value injected by FXMLLoader

    @FXML //  fx:id="valorCuota"
    private TextField valorCuota; // Value injected by FXMLLoader

    @FXML //  fx:id="valorEquipo"
    private TextField valorEquipo; // Value injected by FXMLLoader

    @FXML //  fx:id="valorManoObra"
    private TextField valorManoObra; // Value injected by FXMLLoader

    @FXML //  fx:id="valorMateriales"
    private TextField valorMateriales; // Value injected by FXMLLoader

    @FXML //  fx:id="valorObra"
    private TextField valorObra; // Value injected by FXMLLoader

    @FXML //  fx:id="valorPrima"
    private TextField valorPrima; // Value injected by FXMLLoader

    @FXML //  fx:id="valorTotalCostos"
    private TextField valorTotalCostos; // Value injected by FXMLLoader
    

    @FXML //  fx:id="valorViaticos"
    private TextField valorViaticos; // Value injected by FXMLLoader
    @FXML //  fx:id="preIva"
    private ChoiceBox<Iva> preIva; // Value injected by FXMLLoader
    ToggleGroup group = new ToggleGroup();
    MainFormController mainForm = new MainFormController();
    private Main application;
    private EntityManager em = null;
    private EntityManagerFactory emf = null;
    Calculadora calculadora = new Calculadora();
    
    public void setApp(Main application){
        this.application = application;
  
    }

    // Handler for Button[fx:id="btnGuardar"] onAction
    public void guardar(ActionEvent event) {
        // handle the event here
    }

    // Handler for Button[fx:id="btnLimpiar"] onAction
  

    // Handler for Button[fx:id="btnCancelar"] onAction
    public void salir(ActionEvent event) {
          application.backToMain();
    }

    @Override // This method is called by the FXMLLoader when initialization is complete
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        // initialize your logic here: all @FXML variables will have been injected
          valorTotalCostos.setDisable(true);
          opContado.setToggleGroup(group);
          opCredito.setToggleGroup(group);
          group.selectToggle(opContado);
         
          if(group.getSelectedToggle().equals(opContado)){
              valorPrima.setDisable(true);
              valorCuota.setDisable(true);
              numCuotas.setDisable(true);
              valorObra.setDisable(false);
          }
          if(group.getSelectedToggle().equals(opCredito)){
               valorPrima.setDisable(false);
              valorCuota.setDisable(false);
              numCuotas.setDisable(false);
              valorObra.setDisable(true);
          }
 
          cargarDatosProyecto();
          cargarDatosIva();
          
          final Proyecto selectedProyecto = mainForm.getSelectedProyecto();
          //Presupuesto presupuesto = new Presupuesto();
         // presupuesto.setProyectoProId(selectedProyecto);
         

          preProyecto.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Proyecto>(){

            @Override
            public void changed(ObservableValue<? extends Proyecto> ov, Proyecto t, Proyecto t1) {
                preProyecto.setValue(t1);
                cargarDatosPesupuesto();
                //preProyecto.setValue(t);
                
            }
       });
          
           preIva.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Iva>(){

            @Override
            public void changed(ObservableValue<? extends Iva> ov, Iva t, Iva t1) {
              preIva.setValue(t1);
            }

       });
          
           btnGuardar.setOnAction(new EventHandler<ActionEvent>(){
             
            @Override
            public void handle(ActionEvent arg0) {
               Double ventaCredito = null;
               Double prima = null;
               Integer numCuotasvar = null;
               Double valCuotas = null;
               Double compraTotal = null;
               
         
                Presupuesto presupuesto = new Presupuesto();
                em.getTransaction().begin();
                
                Double combustible = Double.parseDouble(valorCombustible.getText());
                presupuesto.setPreCombustible(combustible);
                
                Double materiales = Double.parseDouble(valorMateriales.getText());
                presupuesto.setPreMateriales(materiales);
                
                Double equipo = Double.parseDouble(valorEquipo.getText());
                presupuesto.setPreEquipo(equipo);
                
                Double manoObra = Double.parseDouble(valorManoObra.getText());
                presupuesto.setPreManoObra(manoObra);
                
                Double viaticos = Double.parseDouble(valorViaticos.getText());
                presupuesto.setPreViaticos(viaticos);
                
                if (group.getSelectedToggle().equals(opContado)){
                    ventaCredito = Double.parseDouble(valorObra.getText());
                    presupuesto.setPreTotalVenta(ventaCredito);
                    presupuesto.setPreOpContado(Boolean.TRUE);
                }
                if(group.getSelectedToggle().equals(opCredito)){
                    prima = Double.parseDouble(valorPrima.getText());
                    numCuotasvar = Integer.parseInt(numCuotas.getText());
                    valCuotas = Double.parseDouble(valorCuota.getText());
                    
                    presupuesto.setPrePrima(prima);
                    presupuesto.setPreNumCuotas(numCuotasvar);
                    presupuesto.setPreValCuota(valCuotas);
                    
                    ventaCredito = calculadora.calcularVentaPorCuota(valCuotas, prima, numCuotasvar);
                    presupuesto.setPreTotalVenta(ventaCredito);
                    presupuesto.setPreOpCredito(Boolean.TRUE);
                }
                
                compraTotal = calculadora.calcularCompraTotal(combustible, materiales, equipo, manoObra, viaticos);
                presupuesto.setPreTotalCompra(compraTotal);
                valorTotalCostos.setText(compraTotal.toString());
               
                Double ivaVenta = calculadora.calcularIvaVenta(ventaCredito);
                Double ivaCompra = calculadora.calcularIvaCompra(compraTotal);
                
                Double pagoIva = calculadora.calcularPagoIVA(ivaVenta, ivaCompra);
                presupuesto.setPrePagoIva(pagoIva);
                valorPagoIva.setText(pagoIva.toString());
               
                BigDecimal utilidadNeta = calculadora.calcularUtilidadNeta(ventaCredito,compraTotal, pagoIva);
                presupuesto.setPreUtilidadNeta(utilidadNeta.doubleValue());         
                valorUtilidadNeta.setText(utilidadNeta.toString());
                
                Double pagoCuenta = calculadora.calcularPagoCuenta(utilidadNeta.doubleValue());
                presupuesto.setPrePagoCuenta(pagoCuenta);
                valorPagoCuenta.setText(pagoCuenta.toString());
                
                Double pagoRenta = calculadora.calcularPagoRenta(utilidadNeta.doubleValue(), pagoCuenta);
                presupuesto.setPrePagoRenta(pagoRenta);
                 
                Double gananciaNeta = calculadora.calcularGananciaNeta(utilidadNeta.doubleValue(), pagoRenta);
                presupuesto.setPreGananciaNeta(gananciaNeta);
                valorGananciaNeta.setText(gananciaNeta.toString());
                
                presupuesto.setProyectoProId(preProyecto.getValue());
                presupuesto.setIvaIvaId(preIva.getValue());
                em.persist(presupuesto);
                em.getTransaction().commit();
                   System.out.println("Entro a a guardar");
              }

         });
       opContado.setOnAction(new EventHandler<ActionEvent>(){

            @Override
            public void handle(ActionEvent t) {
           if(group.getSelectedToggle().equals(opContado)){
              valorPrima.setDisable(true);
              valorCuota.setDisable(true);
              numCuotas.setDisable(true);
              valorObra.setDisable(false);
          }
          if(group.getSelectedToggle().equals(opCredito)){
               valorPrima.setDisable(false);
              valorCuota.setDisable(false);
              numCuotas.setDisable(false);
              valorObra.setDisable(true);
          }
            }
           
       }); 
         opCredito.setOnAction(new EventHandler<ActionEvent>(){

            @Override
            public void handle(ActionEvent t) {
           if(group.getSelectedToggle().equals(opContado)){
              valorPrima.setDisable(true);
              valorCuota.setDisable(true);
              numCuotas.setDisable(true);
              valorObra.setDisable(false);
          }
          if(group.getSelectedToggle().equals(opCredito)){
               valorPrima.setDisable(false);
              valorCuota.setDisable(false);
              numCuotas.setDisable(false);
              valorObra.setDisable(true);
          }
            }
           
       });  
         
         btnModificar.setOnAction(new EventHandler<ActionEvent>(){
             Double ventaCredito = null;
             Double prima = null;
             Integer numCuotasvar = null;
             Double valCuotas = null;
             Double compraTotal = null;

            @Override
            public void handle(ActionEvent t) {
                Presupuesto presupuesto = preProyecto.getValue().getPresupuestoList().get(0);
                if(em.getTransaction().isActive()){
                    em.getTransaction().commit();
                }
                
                 em.getTransaction().begin();
                
                Double combustible = Double.parseDouble(valorCombustible.getText());
                presupuesto.setPreCombustible(combustible);
                
                Double materiales = Double.parseDouble(valorMateriales.getText());
                presupuesto.setPreMateriales(materiales);
                
                Double equipo = Double.parseDouble(valorEquipo.getText());
                presupuesto.setPreEquipo(equipo);
                
                Double manoObra = Double.parseDouble(valorManoObra.getText());
                presupuesto.setPreManoObra(manoObra);
                
                Double viaticos = Double.parseDouble(valorViaticos.getText());
                presupuesto.setPreViaticos(viaticos);
                
                if (group.getSelectedToggle().equals(opContado)){
                    ventaCredito = Double.parseDouble(valorObra.getText());
                    presupuesto.setPreTotalVenta(ventaCredito);
                    presupuesto.setPreOpContado(Boolean.TRUE);
                }
                if(group.getSelectedToggle().equals(opCredito)){
                    prima = Double.parseDouble(valorPrima.getText());
                    numCuotasvar = Integer.parseInt(numCuotas.getText());
                    valCuotas = Double.parseDouble(valorCuota.getText());
                    
                    presupuesto.setPrePrima(prima);
                    presupuesto.setPreNumCuotas(numCuotasvar);
                    presupuesto.setPreValCuota(valCuotas);
                    
                    ventaCredito = calculadora.calcularVentaPorCuota(valCuotas, prima, numCuotasvar);
                    presupuesto.setPreTotalVenta(ventaCredito);
                    presupuesto.setPreOpCredito(Boolean.TRUE);
                }
                
                compraTotal = calculadora.calcularCompraTotal(combustible, materiales, equipo, manoObra, viaticos);
                presupuesto.setPreTotalCompra(compraTotal);
                valorTotalCostos.setText(compraTotal.toString());
               
                Double ivaVenta = calculadora.calcularIvaVenta(ventaCredito);
                Double ivaCompra = calculadora.calcularIvaCompra(compraTotal);
                
                Double pagoIva = calculadora.calcularPagoIVA(ivaVenta, ivaCompra);
                presupuesto.setPrePagoIva(pagoIva);
                valorPagoIva.setText(pagoIva.toString());
               
                BigDecimal utilidadNeta = calculadora.calcularUtilidadNeta(ventaCredito,compraTotal, pagoIva);
                presupuesto.setPreUtilidadNeta(utilidadNeta.doubleValue());         
                valorUtilidadNeta.setText(utilidadNeta.toString());
                
                Double pagoCuenta = calculadora.calcularPagoCuenta(utilidadNeta.doubleValue());
                presupuesto.setPrePagoCuenta(pagoCuenta);
                valorPagoCuenta.setText(pagoCuenta.toString());
                
                Double pagoRenta = calculadora.calcularPagoRenta(utilidadNeta.doubleValue(), pagoCuenta);
                presupuesto.setPrePagoRenta(pagoRenta);
                
                Double gananciaNeta = calculadora.calcularGananciaNeta(utilidadNeta.doubleValue(), pagoRenta);
                presupuesto.setPreGananciaNeta(gananciaNeta);
                valorGananciaNeta.setText(gananciaNeta.toString());
                
                presupuesto.setProyectoProId(preProyecto.getValue());
                presupuesto.setIvaIvaId(preIva.getValue());

                System.out.println("Entro a modificar");
                em.getTransaction().commit();
            }
             
         });
         

         
      } 
    
    public void limpiar (ActionEvent event){
        valorCombustible.setText("");
        valorCuota.setText("");
        valorEquipo.setText("");
        valorGananciaNeta.setText(null);
        valorManoObra.setText(null);
        valorMateriales.setText(null);
        valorObra.setText(null);
        valorPagoCuenta.setText(null);
        valorPagoIva.setText(null);
        valorPrima.setText(null);
        valorTotalCostos.setText(null);
        valorUtilidadNeta.setText(null);
        
        
        
    }
    
    
     private void cargarDatosProyecto(){
        emf = Persistence.createEntityManagerFactory("SistemaControlRentaIVAPU");
        em = emf.createEntityManager();
        
         try {
             em.getTransaction().begin();
            //Select all the record from student table
            Query query = em.createQuery("SELECT p FROM Proyecto p");
            List lst = query.getResultList();
            Iterator it = lst.iterator();
            List <Proyecto> proyectoEntityList = em.createQuery("SELECT p FROM Proyecto p").getResultList();
            ObservableList <Proyecto> proList = FXCollections.observableArrayList(proyectoEntityList);
            preProyecto.setItems(proList);
            
            ObservableList <Proyecto> proyectoLista = FXCollections.observableArrayList();
            while (it.hasNext()) {
                Proyecto proyecto= (Proyecto) it.next(); 
                //System.out.print("Name:" + proyecto.getProNombre());
                proyectoLista.add(proyecto);
            } 
              //tablaProyectos.setItems(proyectoLista);  
              em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
     
     private void cargarDatosIva(){
        emf = Persistence.createEntityManagerFactory("SistemaControlRentaIVAPU");
        em = emf.createEntityManager();
        
         try {
             em.getTransaction().begin();
            //Select all the record from student table
            Query query = em.createQuery("SELECT i FROM Iva i");
            List lst = query.getResultList();
            Iterator it = lst.iterator();
            List <Iva> ivaEntityList = em.createQuery("SELECT i FROM Iva i").getResultList();
            ObservableList <Iva> ivaList = FXCollections.observableArrayList(ivaEntityList);
            preIva.setItems(ivaList);
            
            ObservableList <Iva> ivaLista = FXCollections.observableArrayList();
            while (it.hasNext()) {
                Iva iva = (Iva) it.next(); 
                //System.out.print("Name:" + proyecto.getProNombre());
                ivaLista.add(iva);
            } 
              //tablaProyectos.setItems(proyectoLista);  
              em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
     
     private void cargarDatosPesupuesto(){
        emf = Persistence.createEntityManagerFactory("SistemaControlRentaIVAPU");
        em = emf.createEntityManager();
        
         try {
             em.getTransaction().begin();
            //Select all the record from student table
            Query query = em.createQuery("SELECT p FROM Presupuesto p");
            List lst = query.getResultList();
            Iterator it = lst.iterator();
            ObservableList <Presupuesto> proyectoLista = FXCollections.observableArrayList();
            while (it.hasNext()) {
                Presupuesto presupuesto = (Presupuesto) it.next(); 
                //Proyecto pro = preProyecto.getValue();
                
                if (presupuesto.getProyectoProId().equals(preProyecto.getValue())){
                    valorCombustible.setText(presupuesto.getPreCombustible().toString());
                    valorMateriales.setText(presupuesto.getPreMateriales().toString());
                    valorEquipo.setText(presupuesto.getPreEquipo().toString());
                    valorManoObra.setText(presupuesto.getPreManoObra().toString());
                    valorViaticos.setText(presupuesto.getPreViaticos().toString());
                    valorTotalCostos.setText(presupuesto.getPreTotalCompra().toString());
                    valorGananciaNeta.setText(presupuesto.getPreGananciaNeta().toString());
                    valorPagoCuenta.setText(presupuesto.getPrePagoCuenta().toString());
                    valorUtilidadNeta.setText(presupuesto.getPreUtilidadNeta().toString());
                    valorPagoIva.setText(presupuesto.getPrePagoIva().toString());
                    valorObra.setText(presupuesto.getPreTotalVenta().toString());
                    valorPrima.setText(presupuesto.getPrePrima().toString());
                    numCuotas.setText(presupuesto.getPreNumCuotas().toString());
                    valorCuota.setText(presupuesto.getPreValCuota().toString());
                    valorObra.setText(presupuesto.getPreTotalVenta().toString());
                

                }else{
                    
                    valorCombustible.setText("");
                    valorMateriales.setText("");
                    valorEquipo.setText("");
                    valorManoObra.setText("");
                    valorViaticos.setText("");
                    valorTotalCostos.setText("");
                    valorObra.setText("");
                    valorPrima.setText("");
                    numCuotas.setText("");
                    valorCuota.setText("");
                    valorPagoIva.setText("");
                    valorUtilidadNeta.setText("");
                    valorPagoCuenta.setText("");
                    valorGananciaNeta.setText("");
                    group.selectToggle(opContado);
                }
                  
                proyectoLista.add(presupuesto);
            } 
                
              em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
      
     //Reporte
        public void reportePresupuesto() throws JRException, IOException {  
            Stage stage = null;
            EntityManager em = PerisitenceManager.getEntityManager();
            FileChooser fileChooser = new FileChooser();
            Query query= em.createQuery("SELECT p, i FROM Presupuesto p, Proyecto i");
            //SELECT b.institucionidInstitucion FROM Alumno b, Institucion i WHERE i.nombre = :nombre
            List listOfFamipad =  query.getResultList();
            //String reportPath = "C:/Users/freddy ayala/Dropbox/Universidad Albert Einstein/Año 3/Ingenieria en Sistemas II/Sistema/SistemaControlRentaIVA/src/";
            String reportPath =  "C:/Users/Baby Mature/Dropbox/Universidad Albert Einstein/Año 3/Ingenieria en Sistemas II/Sistema/SistemaControlRentaIVA/src/";
            JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(listOfFamipad);        
            String reportName =  reportPath+ "com/SistemaControlRentaIVAFx/Reports/reportePresupuesto.jasper";
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
