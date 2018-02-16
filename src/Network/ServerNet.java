package Network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerNet implements Runnable {

    private ServerThread clients[] = new ServerThread[50];
    private ServerSocket server = null;
    private Thread thread = null;
    private int clientCount = 0;

    public void run() {
        while(thread != null)
            try {
                addThread(server.accept());
            } catch (IOException ioe) {
                System.out.print("Err: "+ ioe.getMessage());
            }
    }

    public ServerNet(int port) {

        try {
            server = new ServerSocket(port);
            start();

        } catch(Throwable T) {
            System.out.println("Err: " + T.getMessage());
        }
    }

    public synchronized void handle(int ID, String input) {
        if (input.equals(".bye")) {
            clients[findClient(ID)].send(".bye");
            remove(ID);
        }
        else
            for (int i = 0; i < clientCount; i++)
                clients[i].send(ID + ": " + input);
    }

    public void start() {
        if (thread == null)
        {
            thread = new Thread(this);
            thread.start();
        }
    }

    public void addThread(Socket client) {
        if (clientCount < clients.length) {

            clients[clientCount] = new ServerThread(this, client);
            try {
                clients[clientCount].open();
                clients[clientCount].start();
                clientCount++;
            } catch (IOException ioe) {
                System.out.print(ioe.getMessage());
            }
        } else
            System.out.print("Too many connections");
    }

    private int findClient(int ID) {
        for (int i = 0; i < clientCount; i++)
            if (clients[i].getID() == ID)
                return i;

        return -1;
    }

    public synchronized void remove(int ID) {
        int pos = findClient(ID);
        if (pos >= 0)
        {
            ServerThread toTerminate = clients[pos];
            System.out.println("Removing client thread " + ID + " at " + pos);
            if (pos < clientCount - 1)
            {
                for (int i = pos + 1; i < clientCount; i++)
                {
                    clients[i - 1] = clients[i];
                }
            }
            clientCount--;
            try
            {
                toTerminate.close();
            }
            catch (IOException ioe)
            {
                System.out.println("Error closing thread: " + ioe);
            }
            toTerminate.stop();
        }
    }
}
