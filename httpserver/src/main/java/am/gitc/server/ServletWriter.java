package am.gitc.server;

import am.gitc.servlet.HttpServlet;

import java.io.IOException;
import java.io.Writer;

public class ServletWriter extends Writer {

  private HttpResponse response;
  private Writer writer;
  private boolean header = false;

  public ServletWriter(Writer writer, HttpResponse response) {
    this.response = response;
    this.writer = writer;
  }

  public void write(String content) throws IOException {
    if (!header) {
      this.writer.write(getHeaders(response.getStatus()));
      this.header = true;
    }
    this.writer.write(content);
  }

  void writeHeaders(String headers) throws IOException {
    this.header = true;
    this.writer.write(headers);
  }

  @Override
  public void write(char[] chars, int i, int i1) throws IOException {
    this.writer.write(chars, i, i1);
  }

  @Override
  public void flush() throws IOException {
    this.writer.flush();
  }

  @Override
  public void close() throws IOException {
    this.writer.close();
  }

  private String getHeaders(int status) {
    return "HTTP/1.1 " + status + "\r\n"
        + "Server: my am.gitc.server\r\n"
        + "Content-Type: text/html\r\n"
        + "Connection: close\r\n\r\n";
  }
}
