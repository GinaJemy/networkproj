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
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import jpcap.packet.*;
import jpcap.*;

/**
 *
 * @author Gina Salib
 */
public class Networkproj extends Application {
public static boolean isSplashLoaded=false;

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("Scene.fxml"));

        Scene scene = new Scene(root);
        stage.getIcons().add(new Image(Networkproj.class.getResourceAsStream("nade.jpg")));
        stage.setScene(scene);
        stage.setTitle("Nader the dragon");
        stage.show();
        stage.setResizable(false);
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                try {
                    SniffingThread.captureThread.stop();
                    stop();
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        });
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
