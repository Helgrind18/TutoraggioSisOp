package Cassaaaa;

public class Cassiere extends Thread {
    private Cassa c;

    public Cassiere(Cassa c) {
        this.c = c;
    }

    @Override
    public void run() {
        try {
            while (true) {
                c.scansiona();
                c.prossimoCliente();
            }
        } catch (InterruptedException e) {

        }

    }
}
