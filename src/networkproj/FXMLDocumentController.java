package networkproj;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;

import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import javax.xml.bind.DatatypeConverter;
import jpcap.*;
import jpcap.packet.*;

public class FXMLDocumentController implements Initializable {

    public ObservableList<MenuItem> m = FXCollections.observableArrayList();
    public ObservableList<IPPacket> temp=FXCollections.observableArrayList();
    public static ObservableList<IPPacket> packets = FXCollections.observableArrayList();
 public static ArrayList <Packet> pac = new ArrayList<Packet>();
     
       @FXML public Button load;

    @FXML
    public MenuButton device;
    @FXML
    public TableColumn<IPPacket, String> source;
    @FXML
    public TableView<IPPacket> table;
    @FXML
    public TableColumn<IPPacket, String> destination;
    @FXML
    public TableColumn<IPPacket, String> length;
    @FXML
    public TableColumn<IPPacket, String> info;
    @FXML
    public TableColumn<IPPacket, String> protocol;
    @FXML
    public TableColumn<IPPacket, Number> no;
    @FXML
    public TableColumn<IPPacket, String> time;
    @FXML
    public Button start;
    @FXML
    public Button save;
    @FXML
    public TextArea hex;
    @FXML
    public TextArea detail;
    @FXML public Button filter;
    @FXML public TextField filterarea;
    @FXML public Button rfilter;
    @FXML public Button addfilter;
    @FXML public Button stop;
    @FXML public AnchorPane root;
   public static AnchorPane rootp;
    EventHandler<ActionEvent> action = changeTabPlacement();
    SniffingThread s = new SniffingThread();
    public static String protocoll[] = {"HOPOPT", "ICMP", "IGMP", "GGP", "IPv4", "ST", "TCP", "CBT", "EGP", "IGP", "BBN-RCC-MON", "NVP-II", "PUP", "ARGUS (deprecated)", "EMCON", "XNET", "CHAOS", "UDP", "MUX", "DCN-MEAS", "HMP", "PRM", "XNS-IDP", "TRUNK-1", "TRUNK-2", "LEAF-1", "LEAF-2", "RDP", "IRTP", "ISO-TP4", "NETBLT", "MFE-NSP", "MERIT-INP", "DCCP", "3PC", "IDPR", "XTP", "DDP", "IDPR-CMTP", "TP++", "IL", "IPv6", "SDRP", "IPv6-Route", "IPv6-Frag", "IDRP", "RSVP", "GRE", "DSR", "BNA", "ESP", "AH", "I-NLSP", "SWIPE (deprecated)", "NARP", "MOBILE", "TLSP", "SKIP", "IPv6-ICMP", "IPv6-NoNxt", "IPv6-Opts", "CFTP", "SAT-EXPAK", "KRYPTOLAN", "RVD", "IPPC", "SAT-MON", "VISA", "IPCV", "CPNX", "CPHB", "WSN", "PVP", "BR-SAT-MON", "SUN-ND", "WB-MON", "WB-EXPAK", "ISO-IP", "VMTP", "SECURE-VMTP", "VINES", "TTP", "IPTM", "NSFNET-IGP", "DGP", "TCF", "EIGRP", "OSPFIGP", "Sprite-RPC", "LARP", "MTP", "AX.25", "IPIP", "MICP (deprecated)", "SCC-SP", "ETHERIP", "ENCAP", "GMTP", "IFMP", "PNNI", "PIM", "ARIS", "SCPS", "QNX", "A/N", "IPComp", "SNP", "Compaq-Peer", "IPX-in-IP", "VRRP", "PGM", "0-hopprotocol", "L2TP", "DDX", "IATP", "STP", "SRP", "UTI", "SMP", "SM (deprecated)", "PTP", "ISIS over IPv4", "FIRE", "CRTP", "CRUDP", "SSCOPMCE", "IPLT", "SPS", "PIPE", "SCTP", "FC", "RSVP-E2E-IGNORE", "Mobility Header", "UDPLite", "MPLS-in-IP", "manet", "HIP", "Shim6", "WESP", "ROHC"};

    public void loginButtonPress(ActionEvent event) {

              
if(s.getindex()==-1)
{
    return;
}
else{
    start.setDisable(true);
     s.CapturePackets();
     save.setDisable(true);
     load.setDisable(true);
                 addfilter.setDisable(true);
                 stop.setDisable(false);
pac.clear();
temp.clear();
}

    }

    public void stopCapture(ActionEvent event) {
        if(s.getindex()==-1)
         {
             return;
         }
         else{
         s.stop();
         start.setDisable(false);
         save.setDisable(false);
         load.setDisable(false);
                        addfilter.setDisable(false);
stop.setDisable(true);
         }

    }

    public void setaction() {
        for (int i = 0; i < m.size(); i++) {
            m.get(i).setOnAction(action);
            System.out.println("action on " + m.get(i).getText());
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        if (!Networkproj.isSplashLoaded) 
            loadSplashScreen();
        
        rootp=root;
        save.setDisable(true);
        hex.setEditable(false);
        detail.setEditable(false);
        addfilter.setDisable(true);
        stop.setDisable(true);
        source.setCellValueFactory(e -> new ReadOnlyStringWrapper(e.getValue().src_ip.toString()));
        destination.setCellValueFactory(e -> new ReadOnlyStringWrapper(e.getValue().dst_ip.toString()));
        info.setCellValueFactory(e -> new ReadOnlyStringWrapper(e.getValue().src_ip.toString()+" ->"+ e.getValue().dst_ip.toString()+"  Length :"+Integer.toString(e.getValue().length)+"  Hop Limit :"+e.getValue().hop_limit));
        length.setCellValueFactory(e -> new ReadOnlyStringWrapper((Short.toString(e.getValue().length))));
        time.setCellValueFactory(e -> new ReadOnlyStringWrapper(new Date(e.getValue().sec*1000).toString()));
        
        no.setCellValueFactory(column-> {
            return new ReadOnlyObjectWrapper<Number>(pac.indexOf(column.getValue()));
        });

        protocol.setCellValueFactory(e -> new ReadOnlyStringWrapper(protocoll[e.getValue().protocol]));
        m.addAll(s.ListInterfaces());
        setaction();

        device.getItems().addAll(m);
        table.setItems(packets);
        rfilter.setDisable(true);

        table.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<IPPacket>() {
            @Override
            public void changed(ObservableValue<? extends IPPacket> observable, IPPacket oldValue, IPPacket newValue) {
                IPPacket pkt = table.getSelectionModel().getSelectedItem();
                if (pkt != null) {

                    hex.setText(DatatypeConverter.printHexBinary(pkt.header) + "\n" + DatatypeConverter.printHexBinary(pkt.data));

                    String information = "";
                    information = information.concat("Internet Protocol Version "+pkt.version);
                    information = information.concat(" , Src  : " + pkt.src_ip);
                    information = information.concat(" , Dst  : " + pkt.dst_ip +"\n");
                    information = information.concat(" \nTotal Length : " + pkt.length);
                    information = information.concat(" \nIdentification : " + pkt.ident);
                    information = information.concat(" \nFlags");
                    information = information.concat("\nReserved Bit : "+pkt.r_flag);
                    information = information.concat("\nR Flag : "+pkt.r_flag);   
                    information = information.concat("\nDon't Fragment : "+pkt.dont_frag);
                    information = information.concat("\nMore Fragments : "+pkt.more_frag);
                    information = information.concat("\nFragment offset : " + pkt.offset);
                    information = information.concat("\nHop limit : " + pkt.hop_limit);                  
                    information = information.concat("\nPacket priority  : " + (int) pkt.priority);
                    information = information.concat("\nType of Service : " + pkt.rsv_tos);                                         
                    information = information.concat("\nProtocol : "+protocoll[pkt.protocol]+ " ("+pkt.protocol+")");
                    information = information.concat("\nProtocol Version : " + (int) pkt.version);
                    information = information.concat("\nSource  : " + pkt.src_ip);
                    information = information.concat("\nDestination  : " + pkt.dst_ip );
                    
                    EthernetPacket ept = (EthernetPacket) pkt.datalink;
                    information = information.concat("\n  \n Ethernet ");                    
                    information = information.concat("\nDestination MAC address : " + ept.getDestinationAddress());
                    information = information.concat("\nSource MAC address : " + ept.getSourceAddress());
                    information = information.concat("\nType : " );
                    if(ept.frametype==2048)
                    {
                        information+= "IPv4";
                    }
                    else
                    {
                        information+=ept.frametype;                        
                    }
                    if (protocoll[pkt.protocol].equalsIgnoreCase("TCP")) {
                        TCPPacket tp = (TCPPacket) pkt;
                        information += " \n\n Transmission Control Protocol, Src Port : "+tp.src_port+ " , Dst Port : "+tp.dst_port+" "+" ,Seq :"+tp.sequence+" , ACK : "+tp.ack;
                        information += "\nSource Port : " + tp.src_port;
                        information += "\nDestination Port : " + tp.dst_port;
                        information+="\nSequence Number :"+tp.sequence;
                        information+="\nAcknowledgement Number :"+tp.ack_num;
                        information+="\nFlags :";
                        information+="\nUrgent : "+tp.urg; 
                        information+="\nAcknowledgement : "+tp.ack;
                        information+="\nPush : "+tp.psh;                
                        information+="\nReset : "+tp.rst;     
                        information+="\nSyn : "+tp.syn;
                        information+="\nFin : "+tp.fin;
                        information+="\nWindow Size : "+tp.window;
                        if(tp.src_port==80||tp.dst_port==80)
                            information+="\nHyperText Transfer Protocol";
                    }
                    if (protocoll[pkt.protocol].equalsIgnoreCase("UDP")) {
                        UDPPacket pac = (UDPPacket) pkt;
                        information += "\n\n User Datagram Protocol";
                        information += "\nSource Port : " + pac.src_port;
                        information += "\nDestination Port : " + pac.dst_port;
                        information += "\nLength : "+pac.length;
                        
                    }
                    detail.setText(information);
                }
            }
        });
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

    public void setindex(MenuItem me) {
        int i;
        for (i = 0; i < m.size(); i++) {
            if (m.get(i).equals(me)) {
                break;
            }
        }
        s.ChooseInterface(i);
    }
    public void filter()
    {
        ArrayList<IPPacket> filteredPackets = new ArrayList<IPPacket>();
        String input = filterarea.getText();
        for(IPPacket p : packets){
            if(p.dst_ip.toString().toLowerCase().contains(input.toLowerCase()) 
                    || p.src_ip.toString().toLowerCase().contains(input.toLowerCase()) 
                        || protocoll[p.protocol].toLowerCase().contains(input.toLowerCase())
                            || Short.toString(p.length).toLowerCase().contains(input.toLowerCase())){
                filteredPackets.add(p);
                
                
            }
        }
        temp.addAll(packets);
                packets.clear();
                packets.addAll(filteredPackets);
                rfilter.setDisable(false);
                addfilter.setDisable(true);
     
    }
    public void removefilter()
    {
        packets.clear();
        packets.addAll(temp);
        addfilter.setDisable(false);

    }


     public void savepac() throws IOException
     {
                         JpcapWriter j = JpcapWriter.openDumpFile(s.getcap(),"test.pcap");
                         for(int i=0;i<pac.size();i++)
                         {
                             j.writePacket(pac.get(i));
                         }

     }
     public void loadpac() throws IOException
     {
         packets.clear();
         pac.clear();

         JpcapCaptor captor=JpcapCaptor.openFile("test.pcap");
         while(true)
         {
             Packet packet=captor.getPacket();
             if(packet==null||packet==Packet.EOF)break;
             if(packet instanceof IPPacket)
             {
                 packets.add((IPPacket)packet);
                 pac.add((IPPacket)packet);
             }
         }
     }
     
    private void loadSplashScreen() {
        try {
            Networkproj.isSplashLoaded = true;
            
            StackPane pane = FXMLLoader.load(getClass().getResource(("splash.fxml")));
            root.getChildren().setAll(pane);

            FadeTransition fadeIn = new FadeTransition(Duration.seconds(4), pane);
            fadeIn.setFromValue(0);
            fadeIn.setToValue(1);
            fadeIn.setCycleCount(1);

            FadeTransition fadeOut = new FadeTransition(Duration.seconds(4), pane);
            fadeOut.setFromValue(1);
            fadeOut.setToValue(0);
            fadeOut.setCycleCount(1);

            fadeIn.play();

            fadeIn.setOnFinished((e) -> {
                fadeOut.play();
            });

            fadeOut.setOnFinished((e) -> {
                try {
                    AnchorPane parentContent = FXMLLoader.load(getClass().getResource(("Scene.fxml")));
                    root.getChildren().setAll(parentContent);

                } catch (IOException ex) {
                    Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });

        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
