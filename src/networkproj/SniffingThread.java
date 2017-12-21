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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author User
 */
public class SniffingThread {
private static final int DRIVER_CLASS_ROOT = WinRegistry.HKEY_LOCAL_MACHINE;
    private static final String DRIVER_CLASS_PATH = "SYSTEM\\CurrentControlSet\\Control\\Class";
    private static final String NETCFG_INSTANCE_KEY = "NetCfgInstanceId";
    private static final int IFACE_ROOT = WinRegistry.HKEY_LOCAL_MACHINE;
    private static final String IFACE_PATH = "SYSTEM\\CurrentControlSet\\services\\Tcpip\\Parameters\\Interfaces";

        private   String guid;


NetworkInterface [] NETWORK_INTERFACES;
    public static JpcapCaptor CAP;

    int index=-1;
    String rg;
    public static Thread captureThread;
    public SniffingThread() {

    }

    public ObservableList<MenuItem> ListInterfaces() {
        ObservableList<MenuItem> m = FXCollections.observableArrayList();
        NETWORK_INTERFACES = JpcapCaptor.getDeviceList();

        for (int i = 0; i < NETWORK_INTERFACES.length; i++) {
            System.out.println(NETWORK_INTERFACES[i].name + " " + NETWORK_INTERFACES[i].description + " " + NETWORK_INTERFACES[i].datalink_name + " " + NETWORK_INTERFACES[i].datalink_description);
            System.out.println("MAC address ");
            for (byte X : NETWORK_INTERFACES[i].mac_address) {
                System.out.print(Integer.toHexString(X & 0xff) + ":");
            }
            System.out.println();
            m.add(new MenuItem(getName(NETWORK_INTERFACES[i].name,NETWORK_INTERFACES[i].description)));
try{
            NetworkInterfaceAddress[] INT = NETWORK_INTERFACES[i].addresses;
            System.out.println("IP:" + INT[i].address);
            System.out.println("Subnet:" + INT[i].subnet);
            System.out.println("broadcast:" + INT[i].broadcast);
}
catch(Exception ArrayIndexOutOfBoundsException)
{
    System.out.println("no ip");
}

        }
        return m;

    }

    public void ChooseInterface(int i) {

        index = i;
        //todo save index of interface and make sure it is a valid number
    }

    int getindex() {
        return index;
    }

    void CapturePackets() {
        //MyPacketReceiver.capturedPackets.clear();
        System.out.println("this is i" + index);
        packets.clear();
        captureThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    CAP = JpcapCaptor.openDevice(NETWORK_INTERFACES[index], 65535, false, 20);
                    while (true) {

                        CAP.processPacket(1, new MyPacketReceiver());

                    }

                } catch (Exception e) {
                    System.out.println("thread stopped due to exception" + e.getMessage());
                }

                CAP.close();
            }
        }
        );
        captureThread.start();

    }

    public void stop() {
        captureThread.stop();
    }



    public JpcapCaptor getcap()
    {
        return CAP;
    }
    public String getName(String jpcapDeviceName , String jpcapDisplayName)
    {
           
 Matcher matcher = Pattern.compile("\\{(\\S*)\\}").matcher(jpcapDeviceName);
        guid = matcher.find() ? matcher.group(1) : null;
        if (guid == null)
            throw new IllegalArgumentException("Could not parse GUID from jpcap device name '" + jpcapDeviceName + "'");
         String theDriverName = "";
        try
        {
            
           
            String theDriverVendor = "";

            for (String driverClassSubkey : WinRegistry.readStringSubKeys(DRIVER_CLASS_ROOT, DRIVER_CLASS_PATH)) {
                for (String driverSubkey : WinRegistry.readStringSubKeys(DRIVER_CLASS_ROOT, DRIVER_CLASS_PATH + "\\" + driverClassSubkey)) {
                    String path = DRIVER_CLASS_PATH + "\\" + driverClassSubkey + "\\" + driverSubkey;
                    String netCfgInstanceId = WinRegistry.readString(DRIVER_CLASS_ROOT, path, NETCFG_INSTANCE_KEY);
                    if (netCfgInstanceId != null && netCfgInstanceId.equalsIgnoreCase("{" + guid + "}")) {
                        theDriverName = trimOrDefault(WinRegistry.readString(DRIVER_CLASS_ROOT, path, "DriverDesc"), "");
                        theDriverVendor = trimOrDefault(WinRegistry.readString(DRIVER_CLASS_ROOT, path, "ProviderName"), "");
                        // other interesting keys: DriverVersion, DriverDate
                        break;
                    }
                }
                if (!theDriverName.isEmpty())
                    break;
            }

            theDriverName = trimOrDefault(theDriverName, jpcapDisplayName);
        }
         catch (Exception x) {
            throw new UnsupportedOperationException("Information could not be read from the Windows registry.", x);
        }
        return theDriverName;
    }
      private static String trimOrDefault (String str, String def) {
        str = (str == null) ? "" : str.trim();
        return str.isEmpty() ? def : str;
    }
}
