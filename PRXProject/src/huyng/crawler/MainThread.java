package huyng.crawler;

public class MainThread implements Runnable {
    private static String realPath;
    public MainThread(String realPath) {
        this.realPath = realPath;
    }

//        public static void main(String[] args) {
//        Thread main = new Thread(new MainThread());
//        main.run();
//    }

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
                e.printStackTrace();
            }
        }
        phucAnhThread.start();
        kimLongThread.start();
        while (phucAnhThread.isAlive() || kimLongThread.isAlive()){
            try {
                Thread.currentThread().sleep(5*1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        statisticMarkThread.start();
    }
}
