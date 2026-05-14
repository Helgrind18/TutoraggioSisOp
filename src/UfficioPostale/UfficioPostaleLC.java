package UfficioPostale;

import java.util.LinkedList;
import java.util.List;
import java.util.TreeMap;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class UfficioPostaleLC extends UfficioPostale {
    private Lock l = new ReentrantLock();
    private Condition possoRitirare = l.newCondition(); // se e solo se ci sono ancora ticket per l'operazione e SOPRATTUTTO sono il primo in coda
    private Condition possoServire = l.newCondition(); // Posso servire se c'è almeno un cliente nella coda d'attesa
    private Condition devoAttendere = l.newCondition(); // il cliente deve attendere finchè non è il suo turno, ovvero finchè non è il primo della coda
    private List<Thread>[] code = new LinkedList[numFile];
    private boolean[] servire = new boolean[numFile];
    private List<Thread> codaAttesa = new LinkedList<>();

    public UfficioPostaleLC() {
        for (int i = 0; i < code.length; i++) {
            code[i] = new LinkedList<>();
            servire[i] = false;
        }
    }

    @Override
    public boolean ritiraTicket(String operazione) throws InterruptedException {
        l.lock();
        try {
            int index = super.operazioni.indexOf(operazione);
            if(super.ticketRimanenti[index] == 0){
                System.out.println("Non può prendere il biglietto");
                return false;
            }
            codaAttesa.addLast(Thread.currentThread());
            while (!ritiro()){
                possoRitirare.await();
            }
            //Altrimenti l'utente può ritirare
            ticketRimanenti[index]--;
            code[index].addLast(Thread.currentThread()); // aggiungiamo il thread corrente in coda alla fila iesima
            codaAttesa.removeFirst();
            devoAttendere.signalAll();

        } finally {
            l.unlock();
        }
        return true;
    }

    private boolean ritiro() {
        return codaAttesa.isEmpty() || codaAttesa.getFirst().equals(Thread.currentThread());
    }

    @Override
    public void attendiSportello(String operazione) throws InterruptedException {
        l.lock();
        try {
            int index = super.operazioni.indexOf(operazione);
            while(!mioTurno(index)){
                devoAttendere.await();
            }
            servire[index] = true;
            possoServire.signalAll();
        }finally {
            l.unlock();
        }
    }

    private boolean mioTurno(int index) {
        return code[index].isEmpty() || code[index].getFirst().equals(Thread.currentThread());
    }

    @Override
    public void prossimoCliente(String operazione) throws InterruptedException {
        l.lock();
        try {
            int index = super.operazioni.indexOf(operazione);
            while (nonPossoServire(index)){
                possoServire.await();
            }
            possoRitirare.signalAll();
        }finally {
            l.unlock();
        }
    }

    private boolean nonPossoServire(int index) {
        return code[index].isEmpty();
    }

    @Override
    public void eseguiOperazione(String operazione) throws InterruptedException {
        l.lock();
        try {
            int index = super.operazioni.indexOf(operazione);
            while (!devoServire(index)){
                possoServire.await();
            }
            code[index].removeFirst();
            devoAttendere.signalAll();
        }finally {
            l.unlock();
        }
    }

    private boolean devoServire(int index) {
        return servire[index];
    }

    public static void main(String[] args) throws InterruptedException {
        UfficioPostaleLC uf = new UfficioPostaleLC();
        uf.test(200);
    }
}
