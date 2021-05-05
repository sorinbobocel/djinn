package com.butlersuite.djinn.utils.logging;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;

public class AppLogger {

   private static AppLogger logger = null;

   private final String logFile = "log.txt";

   private PrintWriter logWriter;

   public static enum LogLevel {

      INFO,
      WARNING,
      SEVERE;
   }

   private AppLogger() {
      try {
         FileWriter fileWriter = new FileWriter(logFile);
         logWriter = new PrintWriter(fileWriter, true);
      } catch (IOException e) {
         e.printStackTrace();
      }
   }

   public static synchronized AppLogger getInstance() {
      if (logger == null)
         logger = new AppLogger();
      return logger;
   }

   public void log(LogLevel logLevel, LocalDateTime localDateTime,  String message) {
      logWriter.println(logLevel + ", " +  LocalDateTime.now().toString() + ", details: " + message);
}


}
