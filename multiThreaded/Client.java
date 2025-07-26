import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
    public Runnable getRunnable() {
        return new Runnable() {
            @Override
            public void run() {
                try {
                    int port = 8080;
                    InetAddress address = InetAddress.getByName("localhost");
                    Socket socket = new Socket(address, port);
                    PrintWriter toServer = new PrintWriter(socket.getOutputStream(), true);
                    BufferedReader fromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    toServer.println("Hello to the server");
                    String line = fromServer.readLine();
                    System.out.println("Response from the socket: " + line);
                    socket.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        };

    }

    public static void main(String[] args) {

        try {
            Client client = new Client();
            for (int i = 0; i < 100; i++) {
                new Thread(client.getRunnable()).start();
                Thread.sleep(10);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
