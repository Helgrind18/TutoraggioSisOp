import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    private static Semaphore mutex = new Semaphore(1);
    private static Semaphore stampaA = new Semaphore(2);
    private static Semaphore stampaB = new Semaphore(0);
    private static int cntA = 0;
    static class P1 extends Thread{
        @Override
        public void run() {
            try {
                stampaA.acquire();
                System.out.printf("A");
                mutex.acquire();
                cntA++;
                if (cntA == 2){
                    stampaB.release();
                    cntA = 0;
                }
                mutex.release();
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }
    static class P2 extends Thread{
        @Override
        public void run() {
            try {
                stampaB.acquire();
                System.out.printf("B");
                stampaA.release(2);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        while (true) {
            new P1().start();
            new P2().start();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}