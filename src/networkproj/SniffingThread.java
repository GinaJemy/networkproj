/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package networkproj;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.MenuItem;
import jpcap.JpcapCaptor;
import jpcap.NetworkInterface;
import jpcap.NetworkInterfaceAddress;
import static networkproj.FXMLDocumentController.packets;

/**
 *
 * @author User
 */
public class SniffingThread {
NetworkInterface [] NETWORK_INTERFACES;
    JpcapCaptor CAP;
    int index=-1;
    Thread captureThread;
    public SniffingThread() {
        
    }
ObservableList<MenuItem> ListInterfaces()
    {
        ObservableList<MenuItem> m=FXCollections.observableArrayList();
        NETWORK_INTERFACES = JpcapCaptor.getDeviceList();
       for(int i=0;i<NETWORK_INTERFACES.length;i++)
       { 
           System.out.println(NETWORK_INTERFACES[i].name+" "+NETWORK_INTERFACES[i].description+" "+NETWORK_INTERFACES[i].datalink_name+" "+NETWORK_INTERFACES[i].datalink_description);
           //byte[]R= NETWORK_INTERFACES[i].mac_address;
           System.out.println("MAC address ");
           for(byte X: NETWORK_INTERFACES[i].mac_address)
           System.out.print(Integer.toHexString(X&0xff)+":");
           System.out.println();
           m.add(new MenuItem(NETWORK_INTERFACES[i].description));
           
           NetworkInterfaceAddress [] INT =NETWORK_INTERFACES[i].addresses;
           System.out.println("IP:" +INT[i].address);
           System.out.println("Subnet:" +INT[i].subnet);
           System.out.println("broadcast:" +INT[i].broadcast);
           
       }
       return m;
      
}
    public void ChooseInterface(int  i)
       {
          
           index=i;
            //todo save index of interface and make sure it is a valid number
       }
    int getindex()
    {
        return index;
    }
     void CapturePackets()
     {
         //MyPacketReceiver.capturedPackets.clear();
         System.out.println("this is i"+index);
         packets.clear();
          captureThread = new Thread(new Runnable() {
             @Override
             public void run() {
                 try{
                     CAP = JpcapCaptor.openDevice(NETWORK_INTERFACES[index], 65535, false, 20);
                     while(true){
                         
                         CAP.processPacket(1, new MyPacketReceiver());
                         
                     }
                     
                     
                 
                 }
                  catch (Exception e){
                      System.out.println("thread stopped due to exception"+e.getMessage());
                  }
                    
                 CAP.close();
             }
         }
         );
          captureThread.start();
          
          
     }   
     public void stop()
     {
         captureThread.stop();
     }
    
}
