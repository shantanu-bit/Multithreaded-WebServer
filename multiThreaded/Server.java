import java.io.BufferedReader;
import java.io.IOError;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class Server {

    public void run() throws IOException {

        int port = 8080;
        ServerSocket serverSocket = new ServerSocket(port);
        serverSocket.setSoTimeout(10000);
        while (true) {

            try {
                System.out.println("Server is Listening...");
                Socket acceptSocket = serverSocket.accept();
                System.out.println("Client connected!");

                new Thread(() -> {

                    try {
                        PrintWriter toClient = new PrintWriter(acceptSocket.getOutputStream(), true);
                        BufferedReader fromClient = new BufferedReader(
                                new InputStreamReader(acceptSocket.getInputStream()));

                        toClient.println("This message is from the server");
                        String line;
                        line = fromClient.readLine();
                        System.out.println("Message from client: " + line);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            acceptSocket.close();
                        } catch (Exception e) {
                            System.out.println("Unnable to close the socket. ");
                            e.printStackTrace();
                        }
                    }

                }).start();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

    public static void main(String[] args) {
        Server server = new Server();
        try {
            server.run();
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}
