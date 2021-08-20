package am.gitc.servlet;

import am.gitc.annotation.WebServlet;
import am.gitc.server.HttpRequest;
import am.gitc.server.HttpResponse;

import java.io.IOException;

@WebServlet({"/test", "/test2", "test5"})
public class TestServlet extends HttpServlet {


  @Override
  public void doPost(HttpRequest request, HttpResponse response) throws IOException {
    response.getWriter().write("<h1>Hello Corona Virus</h1>");
  }

}
