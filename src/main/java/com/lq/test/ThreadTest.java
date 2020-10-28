package com.lq.test;

public class ThreadTest extends Thread implements Runnable {
    //    @Override
    public void run() {
        System.out.println("开始执行线程");
    }

    public static void main(String[] args) {
        Thread thread = new Thread(new ThreadTest());
        thread.start();
    }
}
