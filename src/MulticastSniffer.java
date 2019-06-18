import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;

public class MulticastSniffer {

  public static void main(String[] args) {


    InetAddress group = null;
    int port = 0;

    try {
      // Determines the IP address of a host, given the host's name.
      group = InetAddress.getByName("239.255.255.250");
      port = 1900;

    } catch (UnknownHostException e) {
      System.err.println("Usage: Java Multicast address port");
      System.exit(1);
    }

    MulticastSocket ms = null;
    try {
      ms = new MulticastSocket(port);

      // Joins a multicast group. Its behavior may be affected by
      // setInterface or setNetworkInterface.
      ms.joinGroup(group);

      byte[] buffer = new byte[8192];

      while (true) {

        DatagramPacket dp = new DatagramPacket(buffer, buffer.length);
        ms.receive(dp);

        String s = new String(dp.getData(), "8859_1");
        System.out.println(s);

      } // END WHILE LOOP

    } catch (IOException ex) {

    } finally {
      if (ms != null) {
        try {
          ms.leaveGroup(group);
          ms.close();

        } catch (IOException ignored) {}
      }
    }
  }
}
