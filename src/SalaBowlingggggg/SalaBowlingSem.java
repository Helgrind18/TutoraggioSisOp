package SalaBowlingggggg;

import javax.crypto.SealedObject;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class SalaBowlingSem extends SalaBowling{
    private Semaphore [] possoGiocare = new Semaphore[numGiocatori];
    private Semaphore possoPreparare = new Semaphore(0);
    private Semaphore possoConsegnare = new Semaphore(numGiocatori);
    private Semaphore possoDepositare = new Semaphore(0);
    private Semaphore possoFornireInfo = new Semaphore(super.numGiocatori);

    private int prossimoGiocatore = 0;
    private List<String> giocatori = new ArrayList<>();
    private Semaphore mutex = new Semaphore(1);

    public SalaBowlingSem(){
        for(int i = 0; i < numGiocatori; i++){
            possoGiocare[i] = new Semaphore(0);
        }
    }

    @Override
    protected String fornisciInformazione() throws InterruptedException {
        possoFornireInfo.acquire();
        mutex.acquire();
        giocatori.addLast(Thread.currentThread().getName());
        mutex.release();
        if (giocatori.size() == numGiocatori){
            System.out.println("Ci sono tutti i giocatori");
            possoPreparare.release();
        }
        return Thread.currentThread().getName();
    }

    @Override
    protected void preparaPartita() throws InterruptedException {
        possoPreparare.acquire();
        System.out.println("L'operatore prepara");
        TimeUnit.SECONDS.sleep(1);
        possoGiocare[0].release();
    }

    private int [] tiri = new int[super.numGiocatori];

    @Override
    protected void gioca(String info) throws InterruptedException {
        mutex.acquire();
        int index = giocatori.indexOf(info);
        mutex.release();
        possoGiocare[index].acquire();
        System.out.println("Tocca al giocatore "+info);
        mutex.acquire();
        tiri[index] ++;
        if (tiri[index] == 10){
            System.out.println("Il giocatore si reca a consegnare le scarpe");
            possoDepositare.release();
        }
        prossimoGiocatore = (prossimoGiocatore+1) % giocatori.size();
        mutex.release();
        possoGiocare[prossimoGiocatore].release();
    }

    @Override
    protected void deposita() throws InterruptedException {
        possoDepositare.acquire(numGiocatori);
        System.out.println("Tutti i giocatori hanno consegnato");
    }

    public static void main(String[] args) {
        SalaBowling s = new SalaBowlingSem();
        s.test(4);
    }
}
