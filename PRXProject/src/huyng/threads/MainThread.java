package huyng.threads;

import huyng.crawler.PhuCanhCrawler;

import java.io.IOException;

public class MainThread implements Runnable {
    public static void main(String[] args) {
        Thread main = new Thread(new MainThread());
        main.run();
    }

    @Override
    public void run() {
        Thread phucAnhThread = new Thread(new PhuCanhCrawler());
        phucAnhThread.start();
        System.out.println("Main Thread : " + Thread.currentThread().getName() + " is DONE!!!");
    }
}
