/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package networkproj;

import java.util.Scanner;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import jpcap.packet.*;
import jpcap.*;
/**
 *
 * @author Gina Salib
 */
public class Networkproj extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
      /*  Scanner sc = new Scanner(System.in);
        Networkproj mypcap = new Networkproj();
        mypcap.ListInterfaces();
        mypcap.ChooseInterface();
        mypcap.CapturePackets();
        if (sc.nextInt()==1)
            CaptureState=false;
        */
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    
}