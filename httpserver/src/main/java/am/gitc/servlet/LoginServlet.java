package am.gitc.servlet;

import am.gitc.annotation.WebServlet;
import am.gitc.server.HttpRequest;
import am.gitc.server.HttpResponse;

import java.io.IOException;

@WebServlet({"/login"})
public class LoginServlet extends HttpServlet {

  @Override
  public void doGet(HttpRequest request, HttpResponse response) throws IOException {
    response.getWriter().write("<h1>Login Servlet</h1>");
  }
}
