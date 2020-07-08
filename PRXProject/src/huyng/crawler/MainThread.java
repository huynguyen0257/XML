package huyng.crawler;

public class MainThread implements Runnable {
    public static void main(String[] args) {
        Thread main = new Thread(new MainThread());
        main.run();
    }

    @Override
    public void run() {
        Thread phucAnhThread = new Thread(new PACrawler());
        Thread kimLongThread = new Thread(new KLCrawler());
        Thread cpuThread = new Thread(new CPUCrawler());
//        cpuThread.start();
//        while (cpuThread.isAlive()){
//            try {
//                cpuThread.sleep(5*1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
        phucAnhThread.start();
        kimLongThread.start();
    }
}
