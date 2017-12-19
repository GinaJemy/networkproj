package networkproj;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import jpcap.*;
import jpcap.packet.*;

public class FXMLDocumentController implements Initializable {
                     public   ObservableList<MenuItem> m=FXCollections.observableArrayList();

    public static ObservableList <IPPacket> packets=FXCollections.observableArrayList();

        @FXML public MenuButton device;
       @FXML public TableColumn<IPPacket,String> source;
       @FXML public TableView <IPPacket> table;
       @FXML public TableColumn <IPPacket,String> destination;
       @FXML public TableColumn <IPPacket,String> length;
       @FXML public TableColumn <IPPacket,String> info;
       @FXML public TableColumn <IPPacket,String> protocol;
       @FXML public TableColumn <IPPacket,Integer> no;
       @FXML public TableColumn <IPPacket,String> time;
       @FXML public Button start;
EventHandler<ActionEvent> action = changeTabPlacement();
SniffingThread s =new SniffingThread();
String protocoll[] = {"HOPOPT","ICMP","IGMP","GGP","IPv4","ST","TCP","CBT","EGP","IGP","BBN-RCC-MON","NVP-II","PUP","ARGUS (deprecated)","EMCON","XNET","CHAOS","UDP","MUX","DCN-MEAS","HMP","PRM","XNS-IDP","TRUNK-1","TRUNK-2","LEAF-1","LEAF-2","RDP","IRTP","ISO-TP4","NETBLT","MFE-NSP","MERIT-INP","DCCP","3PC","IDPR","XTP","DDP","IDPR-CMTP","TP++","IL","IPv6","SDRP","IPv6-Route","IPv6-Frag","IDRP","RSVP","GRE","DSR","BNA","ESP","AH","I-NLSP","SWIPE (deprecated)","NARP","MOBILE","TLSP","SKIP","IPv6-ICMP","IPv6-NoNxt","IPv6-Opts","CFTP","SAT-EXPAK","KRYPTOLAN","RVD","IPPC","SAT-MON","VISA","IPCV","CPNX","CPHB","WSN","PVP","BR-SAT-MON","SUN-ND","WB-MON","WB-EXPAK","ISO-IP","VMTP","SECURE-VMTP","VINES","TTP","IPTM","NSFNET-IGP","DGP","TCF","EIGRP","OSPFIGP","Sprite-RPC","LARP","MTP","AX.25","IPIP","MICP (deprecated)","SCC-SP","ETHERIP","ENCAP","GMTP","IFMP","PNNI","PIM","ARIS","SCPS","QNX","A/N","IPComp","SNP","Compaq-Peer","IPX-in-IP","VRRP","PGM","0-hopprotocol","L2TP","DDX","IATP","STP","SRP","UTI","SMP","SM (deprecated)","PTP","ISIS over IPv4","FIRE","CRTP","CRUDP","SSCOPMCE","IPLT","SPS","PIPE","SCTP","FC","RSVP-E2E-IGNORE","Mobility Header","UDPLite","MPLS-in-IP","manet","HIP","Shim6","WESP","ROHC"};
     public void loginButtonPress(ActionEvent event)
 {
       
if(s.getindex()==-1)
{
    return;
}
else{
    start.setDisable(true);
     s.CapturePackets();
}
 
 }
     public void stopCapture(ActionEvent event)
     {
         if(s.getindex()==-1)
         {
             return;
         }
         s.stop();
         start.setDisable(false);
         
         
     }
    public void setaction()
     {
           for(int i=0;i<m.size();i++)
           {
               m.get(i).setOnAction(action);
               System.out.println("action on "+ m.get(i).getText());
           }
     }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
         source.setCellValueFactory(e->new ReadOnlyStringWrapper(e.getValue().src_ip.toString()));
         destination.setCellValueFactory(e->new ReadOnlyStringWrapper(e.getValue().dst_ip.toString()));
         info.setCellValueFactory(e->new ReadOnlyStringWrapper(e.getValue().toString()));


         length.setCellValueFactory(e->new ReadOnlyStringWrapper((Short.toString(e.getValue().length))));
         time.setCellValueFactory(e->new ReadOnlyStringWrapper((Long.toString(e.getValue().sec))));

    protocol.setCellValueFactory(e->new ReadOnlyStringWrapper(protocoll[e.getValue().protocol]));
     m.addAll(s.ListInterfaces());
                 setaction();

        device.getItems().addAll(m);
                table.setItems(packets);
               
    }

     private EventHandler<ActionEvent> changeTabPlacement() {
         return new EventHandler<ActionEvent>() {
             @Override
             public void handle(ActionEvent event) {
                 MenuItem mItem = (MenuItem) event.getSource();
                 setindex(mItem);
                 device.setText(mItem.getText());
               
             }
         };
    }
     public void setindex(MenuItem me)
     {
         int i;
          for(i=0;i<m.size();i++)
           {
               if(m.get(i).equals(me))
                       break;
           }
          s.ChooseInterface(i);     
     }
}
