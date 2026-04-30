package GalleriaDegliUffiziiiiiii;

import javax.swing.plaf.SliderUI;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class GalleriaDegliUffiziSem extends GalleriaDegliUffizi{
    protected final int maxVisitatori = 200;
    protected final int massimiPerGuida = 30;
    private int [] personePerGuida = new int[guide];
    private Semaphore possoEntrare = new Semaphore(maxVisitatori);
    private Semaphore [] possoIniziare = new Semaphore[guide];
    private Semaphore [] possoUscire = new Semaphore[guide];
    private Semaphore [] attendo = new Semaphore[guide];
    private Semaphore mutex = new Semaphore(1);

    public GalleriaDegliUffiziSem() {
        for (int i = 0; i < guide; i++) {
            possoIniziare[i] = new Semaphore(0);
            possoUscire[i] = new Semaphore(0);
            attendo[i] = new Semaphore(1);
        }
    }

    @Override
    protected void iniziaVisita(int lingua) throws InterruptedException {
        possoEntrare.acquire();
        possoIniziare[lingua].acquire();
        mutex.acquire();
        personePerGuida[lingua]++;
        mutex.release();
    }

    @Override
    protected void esci(int lingua) throws InterruptedException {
        possoUscire[lingua].acquire();
        mutex.acquire();
        personePerGuida[lingua]--;
        possoEntrare.release();
        if(personePerGuida[lingua] == 0){
            System.out.println("Sono usciti tutti per la guida "+lingua+" permetto alla guida di iniziare ");
            attendo[lingua].release();
        }
        mutex.release();
    }

    @Override
    protected void attendiVisitatori(int lingua) throws InterruptedException {
        attendo[lingua].acquire();
        System.out.println("La guida "+lingua+" è arrivata, attende i visitatori");
        possoIniziare[lingua].release(massimiPerGuida);
        TimeUnit.SECONDS.sleep(3);
        mutex.acquire();
        if(personePerGuida[lingua] != massimiPerGuida){
            possoIniziare[lingua].acquire(massimiPerGuida-personePerGuida[lingua]);
        }
        mutex.release();
        System.out.println("La guida "+lingua+" parte");

    }

    @Override
    protected void terminaVisita(int lingua) {
        System.out.println("La guida "+lingua+" ha terminato, permette ai turisti di uscire");
        possoUscire[lingua].release(personePerGuida[lingua]);
    }

    public static void main(String[] args) {
        GalleriaDegliUffizi g = new GalleriaDegliUffiziSem();
        g.test(5,300);
    }
}
