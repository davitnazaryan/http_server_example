package am.gitc.server;

import am.gitc.servlet.HttpServlet;

import java.io.*;
import java.lang.reflect.Field;
import java.net.Socket;

public class HttpConnection implements Runnable {

  private InputStream in;
  private OutputStream out;

  public HttpConnection(Socket socket) throws IOException {
    this.in = socket.getInputStream();
    this.out = socket.getOutputStream();
  }

  @Override
  public void run() {
    try {
      HttpRequest request = readIn();
      HttpResponse response = new HttpResponse();
      ServletWriter servletWriter = new ServletWriter(new OutputStreamWriter(this.out), response);
      response.setWriter(servletWriter);
      try {
        if (request != null) {
          String endpoint = request.getEndpoint();
          HttpServlet servlet = ServletContext.getForUrl(endpoint);
          if (servlet != null) {
            servlet.service(request, response);
            response.getWriter().close();
          } else {
            response.setStatus(404);
            servletWriter.write("<html><body><h1 style=\"color:red\">ERROR 404 endpoint = "
                + endpoint + " has not been implemented " + " or not found</h1></body></html>");
            servletWriter.close();
          }
        }
      } catch (Exception e) {
        response.setStatus(500);
        servletWriter.write("<html><body><h1 style=\"color:red\">INTERNAL SERVER ERROR</h1></body></html>");
        servletWriter.close();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public HttpRequest readIn() throws IOException {
    BufferedReader reader = new BufferedReader(new InputStreamReader(this.in));
    String line;
    while ((line = reader.readLine()) != null) {
      System.out.println(line);
      if (!line.contains("favicon") && !line.trim().isEmpty()) {
        HttpRequest request = new HttpRequest();
        String[] endpointContent = line.split("\\s+")[1].substring(1).split("\\?");
        request.setEndpoint("/" + endpointContent[0]);
        request.setMethod(line.split("\\s+")[0]);
        return request;
      }
      break;
    }
    return null;
  }
}
