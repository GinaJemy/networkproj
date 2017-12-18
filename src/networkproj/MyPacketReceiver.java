/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package networkproj;
import java.util.ArrayList;
import jpcap.packet.*;
import jpcap.*;
import javax.xml.bind.DatatypeConverter;
/**
 *
 * @author Gina Salib
 */
public class MyPacketReceiver implements PacketReceiver{
static ArrayList<Packet> capturedPackets = new ArrayList<Packet>();
String protocoll[] = {"HOPOPT", "ICMP", "IGMP", "GGP", "IPV4", "ST", "TCP", "CBT", "EGP", "IGP", "BBN", "NV2", "PUP", "ARGUS", "EMCON", "XNET", "CHAOS", "UDP", "mux"};
    @Override
    
    public void receivePacket(Packet packet) {
        IPPacket ippkt=(IPPacket)packet;
        capturedPackets.add(ippkt);
        
        
         System.out.println(packet.toString());
         System.out.println("header" + DatatypeConverter.printHexBinary(packet.header));
         System.out.println("data" + DatatypeConverter.printHexBinary(packet.data));
                 
        if (packet.datalink instanceof EthernetPacket)
        {
            EthernetPacket e = (EthernetPacket)packet.datalink;
            System.out.println(e.toString());
            if(packet instanceof IPPacket)
            {
                //not very useful
                int p=ippkt.protocol;
                String proto=protocoll[p];
                if(proto.equalsIgnoreCase("TCP"))
                {
                    TCPPacket tcppkt = (TCPPacket)packet;
                    System.out.println("TCP : "+tcppkt);
                    //http is tcp with dest port 80
                }
            }
            
        }
    }
    
}
