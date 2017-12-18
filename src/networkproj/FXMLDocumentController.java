package networkproj;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import jpcap.*;
import jpcap.packet.*;

public class FXMLDocumentController implements Initializable {
    NetworkInterface [] NETWORK_INTERFACES;
    JpcapCaptor CAP;
    int index;
    public static boolean CaptureState=false;
    Thread captureThread;
    public static ObservableList <IPPacket> packets=FXCollections.observableArrayList();
   public static  MenuItem wifi=new MenuItem("wifi");
      public  MenuItem eth=new MenuItem("ethernet");
        @FXML public MenuButton device;
       @FXML public TableColumn<IPPacket,String> source;
       @FXML public TableView <IPPacket> table;
       @FXML public TableColumn <IPPacket,String> destination;
       @FXML public TableColumn <IPPacket,String> length;
       @FXML public TableColumn <IPPacket,String> info;
       @FXML public TableColumn <IPPacket,String> protocol;

    // u have to make the type compatible with the one t7t  @FXML public TableColumn <IPPacket,Short> protocole;

        //Networkproj mypcap = new Networkproj();
        String protocoll[] = {"HOPOPT", "ICMP", "IGMP", "GGP", "IPV4", "ST", "TCP", "CBT", "EGP", "IGP", "BBN", "NV2", "PUP", "ARGUS", "EMCON", "XNET", "CHAOS", "UDP", "mux"};
     public void loginButtonPress(ActionEvent event)
 {
     
        ListInterfaces();
        ChooseInterface();
        CapturePackets();
 
 }
     public void stopCapture(ActionEvent event)
     {
        // CaptureState=false;
         captureThread.stop();
         //captureThread.interrupt();
     }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
         source.setCellValueFactory(e->new ReadOnlyStringWrapper(e.getValue().src_ip.toString()));
         destination.setCellValueFactory(e->new ReadOnlyStringWrapper(e.getValue().dst_ip.toString()));
         info.setCellValueFactory(e->new ReadOnlyStringWrapper(e.getValue().toString()));
         

         length.setCellValueFactory(e->new ReadOnlyStringWrapper((Short.toString(e.getValue().length))));

    protocol.setCellValueFactory(e->new ReadOnlyStringWrapper(protocoll[e.getValue().protocol]));
                table.setItems(packets);
                //this is made for only colum once u add objects in the observable list the table will update itself automatically
                // ladies and gentlemen we are highly depressed people i don't why i am wiritng this right now bas it seems funny anyway 
                //good bye
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
         //MyPacketReceiver.capturedPackets.clear();
         packets.clear();
         CaptureState = true;
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
}
