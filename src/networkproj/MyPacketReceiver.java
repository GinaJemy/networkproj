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
                FXMLDocumentController.pac.add(packet);
                
                String proto=FXMLDocumentController.protocoll[p];
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
