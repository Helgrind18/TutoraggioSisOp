package Cassaaaa;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Cliente extends Thread{
    private Cassa c;
    private int id;
    private Random random = new Random();
    private int minAttesa = 1;
    private int maxAttesa = 20;
    public Cliente(Cassa c, int i){
        this.c = c;
        this.id = i;
    }
    int prodotti = random.nextInt((maxAttesa-minAttesa+1)+minAttesa);

    @Override
    public void run() {
        try {
            TimeUnit.MILLISECONDS.sleep(prodotti);
            c.svuotaCarrello(prodotti);
            c.paga(prodotti);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
