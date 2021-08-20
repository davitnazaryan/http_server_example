package am.gitc.server;

import java.io.Writer;

public class HttpResponse {

  private Writer writer;
  private int status = 200;

  public Writer getWriter() {
    return writer;
  }

  void setWriter(Writer writer) {
    this.writer = writer;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  public int getStatus() {
    return status;
  }
}
