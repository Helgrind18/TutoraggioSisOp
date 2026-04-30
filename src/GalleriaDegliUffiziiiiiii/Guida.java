package GalleriaDegliUffiziiiiiii;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Guida extends Thread {
    private GalleriaDegliUffizi g;
    private final int lingua;

    public Guida(GalleriaDegliUffizi galleriaDegliUffizi, int i) {
        g = galleriaDegliUffizi;
        lingua = i;
    }

    public int getLingua() {
        return lingua;
    }

    private Random random = new Random();
    private final int minAttesa = 2;
    private final int maxAttesa = 3;

    @Override
    public void run() {
        try {
            while (true) {
                g.attendiVisitatori(lingua);
                System.out.println("La guida " + lingua + " inizia il tour");
                TimeUnit.SECONDS.sleep(random.nextInt(maxAttesa - minAttesa + 1) + minAttesa);
                System.out.println("La guida " + lingua + " ha terminato il tour");
                g.terminaVisita(lingua);
                TimeUnit.SECONDS.sleep(1);
            }
        } catch (InterruptedException e) {
        }
    }
}
