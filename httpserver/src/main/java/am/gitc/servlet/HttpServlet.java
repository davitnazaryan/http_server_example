package am.gitc.servlet;

import am.gitc.server.HttpRequest;
import am.gitc.server.HttpResponse;

import java.io.IOException;

public abstract class HttpServlet {

  public void service(HttpRequest request, HttpResponse response) throws IOException {
    String method = request.getMethod();
    if ("get".equals(method.toLowerCase())) {
      doGet(request, response);
    } else if ("post".equals(method.toLowerCase())) {
      doPost(request, response);
    }
  }

  public void doGet(HttpRequest request, HttpResponse response) throws IOException {
    response.setStatus(405);
    response.getWriter().write(request.getMethod() + " Method not implemented.");
  }

  public void doPost(HttpRequest request, HttpResponse response) throws IOException {
    response.setStatus(405);
    response.getWriter().write(request.getMethod() + " Method not implemented.");
  }
}
