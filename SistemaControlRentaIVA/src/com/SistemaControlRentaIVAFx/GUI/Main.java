
package com.SistemaControlRentaIVAFx.GUI;



import com.SistemaControlRentaIVAFx.Controllers.PresupuestoFormController;
import com.SistemaControlRentaIVAFx.Controllers.MainFormController;
import com.SistemaControlRentaIVAFx.Controllers.NuevoProyectoController;
import com.SistemaControlRentaIVAFx.Controllers.ProfileController;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import com.SistemaControlRentaIVAFx.Entities.User;
import com.SistemaControlRentaIVAFx.BusinessProcess.Authenticator;
import com.SistemaControlRentaIVAFx.Controllers.LoginController;

/**
 * Main Application. This class handles navigation and user session.
 */
public class Main extends Application {

    private Stage stage;
    private User loggedUser;
    private final double MINIMUM_WINDOW_WIDTH = 390.0;
    private final double MINIMUM_WINDOW_HEIGHT = 500.0;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Application.launch(Main.class, (java.lang.String[])null);
     
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            stage = primaryStage;
            stage.setTitle("Sistema de control de impuestos de IVA y Renta para HidroSoil Sa de CV");
            stage.setMinWidth(MINIMUM_WINDOW_WIDTH);
            stage.setMinHeight(MINIMUM_WINDOW_HEIGHT);
            gotoLogin();
            primaryStage.show();
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public User getLoggedUser() {
        return loggedUser;
    }
        
    public boolean userLogging(String userId, String password){
        if (Authenticator.validate(userId, password)) {
            loggedUser = User.of(userId);
            gotoMainForm();
            return true;
        } else {
            return false;
        }
    }
    
  public  void userLogout(){
        loggedUser = null;
        gotoLogin();

    }
   public void backToMain(){
       gotoMainForm();
   }
   
    public void nuevoProyecto (){
        gotoNuevoProyectoForm();
    }
    public void goToPresupuesto ( ){
        gotoPresupuestoForm();
    }
 
   
   
    
    private void gotoProfile() {
        try {
            ProfileController profile = (ProfileController) replaceSceneContent("Profile.fxml");
            profile.setApp(this);
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void gotoMainForm(){
        try {
            MainFormController mainform = ( MainFormController) replaceSceneContent("MainForm.fxml");
            mainform.setApp(this);
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
     private void gotoPresupuestoForm() {
        try {
            PresupuestoFormController presupuestoform = ( PresupuestoFormController) replaceSceneContent("PresupuestoForm.fxml");
             presupuestoform.setApp(this);
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private void gotoNuevoProyectoForm() {
        try {
            NuevoProyectoController nuevoproyectoform = ( NuevoProyectoController) replaceSceneContent("NuevoProyecto.fxml");
            nuevoproyectoform.setApp(this);
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    

    private void gotoLogin() {   
        try {
            LoginController login = (LoginController) replaceSceneContentLogin("Login.fxml");
            login.setApp(this);
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private Initializable replaceSceneContent(String fxml) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        InputStream in = Main.class.getResourceAsStream(fxml);
        loader.setBuilderFactory(new JavaFXBuilderFactory());
        loader.setLocation(Main.class.getResource(fxml));
        AnchorPane page;
        try {
            page = (AnchorPane) loader.load(in);
        } finally {
            in.close();
        } 
        Scene scene = new Scene(page, 1024, 600);
        stage.setScene(scene);
        stage.sizeToScene();
        return (Initializable) loader.getController();
    }
    
    private Initializable replaceSceneContentLogin(String fxml) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        InputStream in = Main.class.getResourceAsStream(fxml);
        loader.setBuilderFactory(new JavaFXBuilderFactory());
        loader.setLocation(Main.class.getResource(fxml));
        AnchorPane page;
        try {
            page = (AnchorPane) loader.load(in);
        } finally {
            in.close();
        } 
        Scene scene = new Scene(page, 500, 500);
        stage.setScene(scene);
        stage.sizeToScene();
        return (Initializable) loader.getController();
    }
}
