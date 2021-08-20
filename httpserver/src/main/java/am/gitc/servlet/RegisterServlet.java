package am.gitc.servlet;

import am.gitc.annotation.WebServlet;
import am.gitc.server.HttpRequest;
import am.gitc.server.HttpResponse;

import java.io.IOException;

@WebServlet({"/register"})
public class RegisterServlet extends HttpServlet {

  @Override
  public void doGet(HttpRequest request, HttpResponse response) throws IOException {
    throw new IOException("");
//    response.getWriter().write("<h1>Register Servlet</h1>");
  }
}
