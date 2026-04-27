package Cassaaaa;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class CassaSem extends Cassa{
    private Semaphore mutex = new Semaphore(0,true);
    private Semaphore possoDepositare = new Semaphore(1,true);
    private Semaphore possoUscire = new Semaphore(0);
    private Semaphore possoScansionare = new Semaphore(0);

    private int prodottiNelCarrello = 0;


    public CassaSem(int numClienti) {
        super(numClienti);
    }

    @Override
    public void svuotaCarrello(int n) throws InterruptedException {
        possoDepositare.acquire();
        System.out.println("Il cliente "+Thread.currentThread()+ " ha svuotato il carrello ");
        prodottiNelCarrello = n;
        possoScansionare.release();
    }

    @Override
    public void scansiona() throws InterruptedException {
        possoScansionare.acquire();
        System.out.println(" Il cassiere dovrà attendere "+ (5*prodottiNelCarrello));
        TimeUnit.MILLISECONDS.sleep(5*prodottiNelCarrello);
        mutex.release();
    }

    @Override
    public void paga(int n) throws InterruptedException {
        mutex.acquire();
        incasso += 5*prodottiNelCarrello;
        System.out.println("Il cliente "+Thread.currentThread()+" esce ");
        possoUscire.release();
    }

    @Override
    public void prossimoCliente() throws InterruptedException {
        possoUscire.acquire();
        possoDepositare.release();
    }

    public static void main(String[] args) throws InterruptedException {
        Cassa c = new CassaSem(10);
        c.test();
    }
}
