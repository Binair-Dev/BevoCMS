package be.bnair.bevo.utils;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Utilitaire pour la gestion de connexions à un serveur Minecraft et la récupération d'informations.
 * Cette classe permet de tester la connexion à un serveur Minecraft, de vérifier s'il est en ligne,
 * de récupérer le nombre de joueurs connectés et le nombre maximum de joueurs autorisés.
 *
 * @author Brian Van Bellinghen
 */
public class MCUtils {

    /**
     * Teste la connexion à un serveur Minecraft.
     *
     * @param ip   L'adresse IP du serveur Minecraft.
     * @param port Le port du serveur Minecraft.
     */
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

    /**
     * Vérifie si un serveur Minecraft est en ligne.
     *
     * @param ip   L'adresse IP du serveur Minecraft.
     * @param port Le port du serveur Minecraft.
     * @return True si le serveur est en ligne, False sinon.
     */
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

    /**
     * Récupère le nombre de joueurs connectés à un serveur Minecraft.
     *
     * @param ip   L'adresse IP du serveur Minecraft.
     * @param port Le port du serveur Minecraft.
     * @return Le nombre de joueurs connectés ou 0 en cas d'erreur.
     */
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

    /**
     * Récupère le nombre maximum de joueurs autorisés sur un serveur Minecraft.
     *
     * @param ip   L'adresse IP du serveur Minecraft.
     * @param port Le port du serveur Minecraft.
     * @return Le nombre maximum de joueurs autorisés ou 0 en cas d'erreur.
     */
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
