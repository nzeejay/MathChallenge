package Network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientNet implements Runnable {

    private Socket socket = null;
    private Thread thread = null;
    private DataInputStream streamIn = null;
    private DataOutputStream streamOut = null;
    private ClientThread client = null;

    public String sendText = "";

    public void run() {
        while (thread != null)
            try {
                if(sendText != "") {
                    streamOut.writeUTF(sendText);
                    streamOut.flush();
                    sendText = "";
                }
            } catch (Throwable T) {
                System.out.println("Sending error: " + T.getMessage());
                stop();
            }
    }

    public ClientNet(String IP, int port) throws Throwable {
            socket = new Socket(IP, port);
            start();
    }

    public void handle(String msg) {
        if (msg.equals(".bye"))
        {
            System.out.println("Good bye. Press RETURN to exit ...");
            stop();
        } else
        {
            System.out.println(msg);
        }
    }

    public void start() throws IOException {

        streamIn = new DataInputStream(socket.getInputStream());
        streamOut = new DataOutputStream(socket.getOutputStream());
        if (thread == null)
        {
            client = new ClientThread(this, socket);
            thread = new Thread(this);
            thread.start();
        }
    }

    public void stop()
    {
        if (thread != null) {
            thread.stop();
            thread = null;
        }
        try {
            if (streamIn != null)
                streamIn.close();

            if (streamOut != null)
                streamOut.close();

            if (socket != null)
                socket.close();

        } catch (IOException ioe) {
            System.out.println("Error closing ...");
        }
        client.close();
        client.stop();
    }

}
