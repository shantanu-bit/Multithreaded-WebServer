import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private final ExecutorService threadPool;

    public Server() {
        this.threadPool = Executors.newCachedThreadPool();
    }

    public void handleClient(Socket socket) {
        try (PrintWriter toClient = new PrintWriter(socket.getOutputStream(), true);) {

            // BufferedReader fromClient = new BufferedReader(new
            // InputStreamReader(socket.getInputStream()));

            toClient.println("Hello this message is from server: " + socket.getInetAddress());
            // Thread.sleep(100);

            // String line = fromClient.readLine();
            // System.out.println("Message from client: " + line);
        } catch (Exception e) {
            System.out.println("Client took too long to send message. ");
        }
    }

    public static void main(String[] args) {
        int port = 9090;
        int poolSize = 100;

        Server server = new Server();
        try {
            ServerSocket serverSocket = new ServerSocket(port, 2000);
            serverSocket.setSoTimeout(70000);
            System.out.println("Server is listening on port: " + port);
            while (true) {

                Socket socket = serverSocket.accept();

                server.threadPool.execute(() -> server.handleClient(socket));
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            server.threadPool.shutdown();
        }

    }
}
