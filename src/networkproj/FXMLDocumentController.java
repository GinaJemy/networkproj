package networkproj;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import jpcap.packet.IPPacket;

public class FXMLDocumentController implements Initializable {
    
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

        Networkproj mypcap = new Networkproj();
        String protocoll[] = {"HOPOPT", "ICMP", "IGMP", "GGP", "IPV4", "ST", "TCP", "CBT", "EGP", "IGP", "BBN", "NV2", "PUP", "ARGUS", "EMCON", "XNET", "CHAOS", "UDP", "mux"};
     public void loginButtonPress(ActionEvent event)
 {
     
        mypcap.ListInterfaces();
        mypcap.ChooseInterface();
        mypcap.CapturePackets();
 
 }
     public void stopCapture(ActionEvent event)
     {
         Networkproj.CaptureState=false;
     }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
         source.setCellValueFactory(e->new ReadOnlyStringWrapper(e.getValue().src_ip.toString()));
         destination.setCellValueFactory(e->new ReadOnlyStringWrapper(e.getValue().dst_ip.toString()));
         info.setCellValueFactory(e->new ReadOnlyStringWrapper(e.getValue().toString()));
         

         length.setCellValueFactory(e->new ReadOnlyStringWrapper((Short.toString(e.getValue().length))));

    protocol.setCellValueFactory(e->new ReadOnlyStringWrapper(protocoll[e.getValue().protocol]));
                table.setItems(Networkproj.packets);
                //this is made for only colum once u add objects in the observable list the table will update itself automatically
                // ladies and gentlemen we are highly depressed people i don't why i am wiritng this right now bas it seems funny anyway 
                //good bye
    }    
}
