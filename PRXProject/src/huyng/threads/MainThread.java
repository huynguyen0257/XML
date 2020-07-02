package huyng.threads;

import huyng.crawler.KLCrawler;
import huyng.crawler.PACrawler;

public class MainThread implements Runnable {
    public static void main(String[] args) {
        Thread main = new Thread(new MainThread());
        main.run();
    }

    @Override
    public void run() {
        Thread phucAnhThread = new Thread(new PACrawler());
        phucAnhThread.start();
        Thread kimLongThread = new Thread(new KLCrawler());
        kimLongThread.start();
    }
}
