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
    NetworkInterface [] NETWORK_INTERFACES;
    JpcapCaptor CAP;
    int index;
    public static boolean CaptureState=false;
    public static ObservableList <IPPacket> packets=FXCollections.observableArrayList();
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
    void ListInterfaces()
    {
        NETWORK_INTERFACES = JpcapCaptor.getDeviceList();
       for(int i=0;i<NETWORK_INTERFACES.length;i++)
       {
           System.out.println(NETWORK_INTERFACES[i].name+" "+NETWORK_INTERFACES[i].description+" "+NETWORK_INTERFACES[i].datalink_name+" "+NETWORK_INTERFACES[i].datalink_description);
           //byte[]R= NETWORK_INTERFACES[i].mac_address;
           System.out.println("MAC address ");
           for(byte X: NETWORK_INTERFACES[i].mac_address)
           System.out.print(Integer.toHexString(X&0xff)+":");
           System.out.println();
           
           NetworkInterfaceAddress [] INT =NETWORK_INTERFACES[i].addresses;
           System.out.println("IP:" +INT[i].address);
           System.out.println("Subnet:" +INT[i].subnet);
           System.out.println("broadcast:" +INT[i].broadcast);
           
       }
      
}
     void ChooseInterface()
       {
           index=1;
            //todo save index of interface and make sure it is a valid number
       }
     void CapturePackets()
     {
         MyPacketReceiver.capturedPackets.clear();
         packets.clear();
         CaptureState = true;
          new Thread(new Runnable() {
             @Override
             public void run() {
                 try{
                     CAP = JpcapCaptor.openDevice(NETWORK_INTERFACES[index], 65535, false, 20);
                     while(CaptureState){
                         CAP.processPacket(1, new MyPacketReceiver());
                     }
                     CAP.close();
                 
                 }
                  catch (Exception e){
                  }
                    
                 
             }
         }
         ).start();

          
     }
    
}
