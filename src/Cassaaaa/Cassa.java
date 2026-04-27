package Cassaaaa;

public abstract class Cassa {
    protected int incasso = 0;
    private int numClienti;
    protected Cliente [] clienti;
    public Cassa(int numClienti){
        this.numClienti = numClienti;
        clienti = new Cliente[numClienti];
    }

    public abstract void svuotaCarrello(int n) throws InterruptedException;
    public abstract void scansiona() throws InterruptedException;
    public abstract void paga(int n) throws InterruptedException;
    public abstract void prossimoCliente() throws InterruptedException;

    public void test() throws InterruptedException {
        for (int i = 0; i < clienti.length; i++) {
            Cliente c = new Cliente(this,i);
            clienti[i] = c;
            c.start();
        }
        Cassiere cassiere = new Cassiere(this);
        cassiere.setDaemon(true);
        cassiere.start();
        for (int i = 0; i < clienti.length; i++) {
            clienti[i].join();
        }


        System.out.println("L'incasso totale è "+incasso);
    }
}
