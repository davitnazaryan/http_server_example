package am.gitc.server;

import am.gitc.annotation.*;
import am.gitc.servlet.HttpServlet;

import java.io.File;
import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

class ServletContext {

  private static List<HttpServlet> servlets = new ArrayList<>();

  private static Map<String, HttpServlet> urlsServletsMap = new ConcurrentHashMap<>();

  static void initContext() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
    init();
    processControllers();
  }

  private static void processControllers() {
    for (HttpServlet servlet : servlets) {
      WebServlet mapping = servlet.getClass().getAnnotation(WebServlet.class);
      String[] paths = mapping.value();
      for (String path : paths) {
        if (urlsServletsMap.containsKey(path)) {
          throw new RuntimeException("Path already bind " + path);
        }
        System.out.println(new Date() + " " + path + " bindid to " + servlet.getClass().getName());
        urlsServletsMap.put(path, servlet);
      }
    }
  }


  private static void init() throws ClassNotFoundException,
      NoSuchMethodException, IllegalAccessException,
      InvocationTargetException,
      InstantiationException {
    File file = new File(ServletContext.class
        .getClassLoader().getResource("./").getFile());
    loadClasses(file, file);
  }


  private static void loadClasses(File file, File root) throws
      ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
    if (file.isFile() && file.getName().endsWith(".class")) {
      Class<?> clazz = Class.forName(file.getAbsolutePath()
          .replace(root.getAbsolutePath() + File.separator, "")
          .replace(File.separator, ".")
          .replace(".class", ""));
      if (clazz.isAnnotationPresent(WebServlet.class)) {
        Constructor<?> constructor = clazz.getDeclaredConstructor();
        constructor.setAccessible(true);
        HttpServlet obj = (HttpServlet) constructor.newInstance();
        servlets.add(obj);
      }
    } else {
      File[] classes = file.listFiles();
      if (classes != null) {
        for (File clazzFile : classes) {
          loadClasses(clazzFile, root);
        }
      }
    }
  }

  public static HttpServlet getForUrl(String uri) {
    return urlsServletsMap.get(uri);
  }
}
