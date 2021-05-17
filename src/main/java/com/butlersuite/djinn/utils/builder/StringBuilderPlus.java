package com.butlersuite.djinn.utils.builder;

import org.springframework.stereotype.Component;

@Component
public class StringBuilderPlus {

   private StringBuilder sb;

   public StringBuilderPlus(){
      sb = new StringBuilder();
   }

   public void append(String str)
   {
      sb.append(str != null ? str : "");
   }

   public void appendLine(String str)
   {
      sb.append(str != null ? str : "").append(System.getProperty("line.separator"));
   }

   public String toString()
   {
      return sb.toString();
   }
}