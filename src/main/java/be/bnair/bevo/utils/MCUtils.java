package be.bnair.bevo.utils;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class MCUtils {

    public static void test(String ip, int port)
    {
        try {
            Socket sock = new Socket(ip, port);

            DataOutputStream out = new DataOutputStream(sock.getOutputStream());
            DataInputStream in = new DataInputStream(sock.getInputStream());

            out.write(0xFE);

            int b;
            StringBuffer str = new StringBuffer();
            while ((b = in.read()) != -1) {
                if (b != 0 && b > 16 && b != 255 && b != 23 && b != 24) {
                    str.append((char) b);
                }
            }

            String[] data = str.toString().split("§");
            String motd = data[0];
            int onlinePlayers = Integer.parseInt(data[1]);
            int maxPlayers = Integer.parseInt(data[2]);

        } catch (IOException e) {}
    }
    public static boolean isOnline(String ip, int port)
    {
        try {
            Socket sock = new Socket(ip, port);

            DataOutputStream out = new DataOutputStream(sock.getOutputStream());
            DataInputStream in = new DataInputStream(sock.getInputStream());

            out.write(0xFE);

            int b;
            StringBuffer str = new StringBuffer();
            while ((b = in.read()) != -1) {
                if (b != 0 && b > 16 && b != 255 && b != 23 && b != 24) {
                    str.append((char) b);
                }
            }

            String[] data = str.toString().split("§");
            sock.close();
            return true;
        } catch (IOException e) {
            return false;
        }
    }
    public static Integer getConnected(String ip, int port)
    {
        try {
            Socket sock = new Socket(ip, port);

            DataOutputStream out = new DataOutputStream(sock.getOutputStream());
            DataInputStream in = new DataInputStream(sock.getInputStream());

            out.write(0xFE);

            int b;
            StringBuffer str = new StringBuffer();
            while ((b = in.read()) != -1) {
                if (b != 0 && b > 16 && b != 255 && b != 23 && b != 24) {
                    str.append((char) b);
                }
            }

            String[] data = str.toString().split("§");
            int onlinePlayers = Integer.parseInt(data[1]);
            sock.close();
            return onlinePlayers;
        } catch (IOException e) {
            return 0;
        }
    }

    public static Integer getMaxPlayer(String ip, int port)
    {
        try {
            Socket sock = new Socket(ip, port);

            DataOutputStream out = new DataOutputStream(sock.getOutputStream());
            DataInputStream in = new DataInputStream(sock.getInputStream());

            out.write(0xFE);

            int b;
            StringBuffer str = new StringBuffer();
            while ((b = in.read()) != -1) {
                if (b != 0 && b > 16 && b != 255 && b != 23 && b != 24) {
                    str.append((char) b);
                }
            }

            String[] data = str.toString().split("§");
            int maxPlayers = Integer.parseInt(data[2]);
            sock.close();
            return maxPlayers;
        } catch (IOException e) {
            return 0;
        }
    }
}
