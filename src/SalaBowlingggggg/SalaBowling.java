package SalaBowlingggggg;

public abstract class SalaBowling {
    protected abstract String fornisciInformazione() throws InterruptedException;
    protected abstract void preparaPartita() throws InterruptedException;
    protected abstract void gioca(String info) throws InterruptedException;
    protected abstract void deposita() throws InterruptedException;

    protected final int numGiocatori = 4;
    protected void test(int numGiocatori){
        Operatore o = new Operatore(this);
        o.setDaemon(true);
        o.start();
        for(int i = 0; i < numGiocatori; i++){
            Giocatore g = new Giocatore(this);
            g.start();
        }

    }

}
