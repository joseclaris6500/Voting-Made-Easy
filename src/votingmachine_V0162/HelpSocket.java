/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package votingmachine_V0162;

import java.io.DataOutputStream;
import java.net.Socket;
import java.util.Scanner;


public class HelpSocket {

    private final int stationID;
    private final String ip;
    private final int port;

    public HelpSocket(int stationID, String ip, int port) {
        this.stationID = stationID;
        this.ip = ip;
        this.port = port;
    }

    public void sendRequest() {
        try (Socket socket = new Socket(this.ip, this.port);
                DataOutputStream dos = new DataOutputStream(socket.getOutputStream())) {
            
            dos.writeUTF(this.stationID + " requests help.");
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
