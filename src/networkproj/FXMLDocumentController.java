package networkproj;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;

import java.util.ResourceBundle;
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
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
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
        save.setDisable(true);
        hex.setEditable(false);
        detail.setEditable(false);
        addfilter.setDisable(true);
        stop.setDisable(true);
        source.setCellValueFactory(e -> new ReadOnlyStringWrapper(e.getValue().src_ip.toString()));
        destination.setCellValueFactory(e -> new ReadOnlyStringWrapper(e.getValue().dst_ip.toString()));
        info.setCellValueFactory(e -> new ReadOnlyStringWrapper(e.getValue().toString()));
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
                    information = information.concat("IP Packet : \n");
                    if (pkt.dont_frag) {
                        information = information.concat("dft bi is set. packet will not be fragmented \n");

                    } 
                    else {
                        information = information.concat("dft bi is not set. packet will  be fragmented \n");
                    }
                    information = information.concat(" \n destination ip is :" + pkt.dst_ip);
                    information = information.concat("\n this is source ip :" + pkt.src_ip);
                    information = information.concat("\n this is hop limit :" + pkt.hop_limit);
                    information = information.concat(" \n this is identification field  :" + pkt.ident);
                    information = information.concat(" \npacket length :" + pkt.length);
                    information = information.concat("\n packet priority  :" + (int) pkt.priority);
                    information = information.concat("\n type of service field" + pkt.rsv_tos);
                    if (pkt.r_flag) {
                        information = information.concat("\n releiable transmission");
                    } else {
                        information = information.concat("\n not reliable");
                    }
                    information = information.concat("\n protocol version is : " + (int) pkt.version);
                    information = information.concat("\n flow label field" + pkt.flow_label);
                    information = information.concat("\n  \n Ethernet packet");

                    EthernetPacket ept = (EthernetPacket) pkt.datalink;
                    information = information.concat("\n this is destination mac address :" + ept.getDestinationAddress());
                    information = information.concat("\n this is source mac address" + ept.getSourceAddress());
                    if (protocoll[pkt.protocol].equalsIgnoreCase("TCP")) {

                        information += " \n\n this is TCP packet";
                        TCPPacket tp = (TCPPacket) pkt;
                        information += "this is destination port of tcp :" + tp.dst_port;
                        if (tp.ack) {
                            information += "\n" + "this is an acknowledgement";
                        } else {
                            information += "this is not an acknowledgment packet";
                        }

                        if (tp.rst) {
                            information += "reset connection ";
                        }
                        information += " \n protocol version is :" + tp.version;
                        information += "\n this is destination ip " + tp.dst_ip;
                        information += "this is source ip" + tp.src_ip;
                        if (tp.fin) {
                            information += "sender does not have more data to transfer";
                        }
                        if (tp.syn) {
                            information += "\n request for connection";
                        }
                    }
                    if (protocoll[pkt.protocol].equalsIgnoreCase("UDP")) {
                        UDPPacket pac = (UDPPacket) pkt;
                        information += "\n\n this is udp packet";
                        information += "\nthis is source port : " + pac.src_port;
                        information += "\nthis is destination port : " + pac.dst_port;
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
         JpcapCaptor captor=JpcapCaptor.openFile("test.pcap");
         while(true)
         {
             Packet packet=captor.getPacket();
             if(packet==null)break;
             if(packet instanceof IPPacket)
                 packets.add((IPPacket)packet);
         }
     }
}
