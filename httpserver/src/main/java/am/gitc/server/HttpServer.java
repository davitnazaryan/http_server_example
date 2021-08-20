package am.gitc.server;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HttpServer {

    private ExecutorService connectionPool;

    private HttpServer() {
        long start = System.currentTimeMillis();
        System.out.println(new Date() + " Initialiazing Http Server");
        try {
            this.connectionPool = Executors.newFixedThreadPool(10);
            System.out.println(new Date() + " Creating http connection pool with fixed size 10");
            System.out.println(new Date() + " Starting http am.gitc.server.");
            int port = getPort();
            this.start(start, port);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static HttpServer createServer() {
        return HttpServerInstanceCreator.httpServer;
    }

    private void start(long start, int port) throws IOException, ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        System.out.println(new Date() + " creating components");
        ServerSocket serverSocket = new ServerSocket(port);
        ServletContext.initContext();
        System.out.println(new Date() + " Http Server started on port " + port + " in " + (System.currentTimeMillis() - start) / 1000.0 + "s");
        while (true) {
            Socket socket = serverSocket.accept();
            am.gitc.server.HttpConnection httpConnection = new am.gitc.server.HttpConnection(socket);
            this.connectionPool.execute(httpConnection);
        }
    }

    private static class HttpServerInstanceCreator {
        static HttpServer httpServer = new HttpServer();
    }

    private static int getPort() throws IOException {
        InputStream in = HttpServer.class.getClassLoader().getResourceAsStream("application.properties");
        if (in == null) {
            throw new RuntimeException("Please create application.properties file into root project.");
        }
        Properties properties = new Properties();
        properties.load(in);
        String serverPort = properties.getProperty("server.port");
        if (serverPort == null) {
            return 8080;
        }
        return Integer.parseInt(serverPort);
    }
}
