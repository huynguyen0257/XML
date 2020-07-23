package huyng.crawler;

import java.util.logging.Level;
import java.util.logging.Logger;

public class MainThread implements Runnable {
    private static String realPath;
    public MainThread(String realPath) {
        this.realPath = realPath;
    }

    @Override
    public void run() {
        Thread phucAnhThread = new Thread(new PACrawler(realPath));
        Thread kimLongThread = new Thread(new KLCrawler(realPath));
        Thread cpuThread = new Thread(new CPUCrawler(realPath));
        Thread statisticMarkThread = new Thread(new StatisticMark());
        cpuThread.start();
        while (cpuThread.isAlive()){
            try {
                Thread.currentThread().sleep(5*1000);
            } catch (InterruptedException e) {
//                e.printStackTrace();
                Logger.getLogger(PACrawler.class.getName()).log(Level.SEVERE, "InterruptedException e : " + e.getMessage() + "| Line:" + e.getStackTrace()[0].getLineNumber());

            }
        }
        phucAnhThread.start();
        kimLongThread.start();
        while (phucAnhThread.isAlive() || kimLongThread.isAlive()){
            try {
                Thread.currentThread().sleep(5*1000);
            } catch (InterruptedException e) {
//                e.printStackTrace();
                Logger.getLogger(PACrawler.class.getName()).log(Level.SEVERE, "InterruptedException e : " + e.getMessage() + "| Line:" + e.getStackTrace()[0].getLineNumber());
            }
        }
        statisticMarkThread.start();
    }
}
