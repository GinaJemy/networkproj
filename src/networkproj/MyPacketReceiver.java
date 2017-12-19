/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package networkproj;
import java.util.ArrayList;
import javafx.application.Platform;
import jpcap.packet.*;
import jpcap.*;
import javax.xml.bind.DatatypeConverter;
/**
 *
 * @author Gina Salib
 */
public class MyPacketReceiver implements PacketReceiver{
String protocoll[] = {"HOPOPT","ICMP","IGMP","GGP","IPv4","ST","TCP","CBT","EGP","IGP","BBN-RCC-MON","NVP-II","PUP","ARGUS (deprecated)","EMCON","XNET","CHAOS","UDP","MUX","DCN-MEAS","HMP","PRM","XNS-IDP","TRUNK-1","TRUNK-2","LEAF-1","LEAF-2","RDP","IRTP","ISO-TP4","NETBLT","MFE-NSP","MERIT-INP","DCCP","3PC","IDPR","XTP","DDP","IDPR-CMTP","TP++","IL","IPv6","SDRP","IPv6-Route","IPv6-Frag","IDRP","RSVP","GRE","DSR","BNA","ESP","AH","I-NLSP","SWIPE (deprecated)","NARP","MOBILE","TLSP","SKIP","IPv6-ICMP","IPv6-NoNxt","IPv6-Opts","CFTP","SAT-EXPAK","KRYPTOLAN","RVD","IPPC","SAT-MON","VISA","IPCV","CPNX","CPHB","WSN","PVP","BR-SAT-MON","SUN-ND","WB-MON","WB-EXPAK","ISO-IP","VMTP","SECURE-VMTP","VINES","TTP","IPTM","NSFNET-IGP","DGP","TCF","EIGRP","OSPFIGP","Sprite-RPC","LARP","MTP","AX.25","IPIP","MICP (deprecated)","SCC-SP","ETHERIP","ENCAP","GMTP","IFMP","PNNI","PIM","ARIS","SCPS","QNX","A/N","IPComp","SNP","Compaq-Peer","IPX-in-IP","VRRP","PGM","0-hopprotocol","L2TP","DDX","IATP","STP","SRP","UTI","SMP","SM (deprecated)","PTP","ISIS over IPv4","FIRE","CRTP","CRUDP","SSCOPMCE","IPLT","SPS","PIPE","SCTP","FC","RSVP-E2E-IGNORE","Mobility Header","UDPLite","MPLS-in-IP","manet","HIP","Shim6","WESP","ROHC"};
    @Override
    
    public void receivePacket(Packet packet) {
        
         
        
       
         System.out.println(packet.toString());
         System.out.println("header" + DatatypeConverter.printHexBinary(packet.header));
         System.out.println("data" + DatatypeConverter.printHexBinary(packet.data));
                 
        if (packet.datalink instanceof EthernetPacket)
        {
            EthernetPacket e = (EthernetPacket)packet.datalink;
            System.out.println(e.toString());
            if(packet instanceof IPPacket)
            {
                IPPacket ippkt=(IPPacket)packet;
                 Platform.runLater(() ->  
        FXMLDocumentController.packets.add(ippkt));
                int p=ippkt.protocol;
                
                String proto=protocoll[p];
                if(proto.equalsIgnoreCase("TCP"))
                {
                    TCPPacket tcppkt = (TCPPacket)packet;
                    System.out.println("TCP : "+tcppkt);
                    //http is tcp with dest port 80
                }

            }
else if(packet instanceof ARPPacket)
            {
                ARPPacket arppkt = (ARPPacket)packet;
                DatalinkPacket dpkt= (DatalinkPacket)arppkt.datalink;
                System.out.println(dpkt.toString());
            }
            
        }
    }
    
}
