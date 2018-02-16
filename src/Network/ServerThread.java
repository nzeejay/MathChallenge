package Network;

import java.io.*;
import java.net.Socket;

public class ServerThread extends Thread {

    private ServerNet server = null;
    private Socket socket = null;
    private int ID = -1;
    private DataInputStream streamIn = null;
    private DataOutputStream streamOut = null;

    public ServerThread(ServerNet serv, Socket soc) {

        super();
        server = serv;
        socket = soc;
        ID = socket.getPort();
    }

    public void send(String msg)
    {
        try {
            streamOut.writeUTF(msg);
            streamOut.flush();
        }
        catch (IOException ioe) {
            System.out.println(ID + " ERROR sending: " + ioe.getMessage());
            server.remove(ID);
            stop();
        }
    }

    public void run() {
        while (true)
            try {
                server.handle(ID, streamIn.readUTF());
            }
            catch (IOException ioe) {
                System.out.println(ID + " ERROR reading: " + ioe.getMessage());
                server.remove(ID);
                stop();//!!!!
            }
    }

    public void open() throws IOException {
        streamIn = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
        streamOut = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
    }

    public void close() throws IOException {
        if (socket != null)
            socket.close();

        if (streamIn != null)
            streamIn.close();

        if (streamOut != null)
            streamOut.close();
    }

    public int getID() {
        return ID;
    }
}
