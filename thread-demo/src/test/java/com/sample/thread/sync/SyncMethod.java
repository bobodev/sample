package com.sample.thread.sync;

public class SyncMethod {

   public int i;

   public synchronized void syncTask(){
           i++;
   }
}