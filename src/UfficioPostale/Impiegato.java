package UfficioPostale;

import java.util.concurrent.TimeUnit;

public class Impiegato extends Thread{
    private UfficioPostale uf;
    private String op;
    private int i;
    private final int attesaA = 3;
    private final int attesaB = 5;
    private final int attesaC = 7;

    public Impiegato(UfficioPostale uf, String c, int i){
        this.uf = uf;
        this.op = c;
        this.i = i;
    }

    @Override
    public void run(){
        try {
            while (true){
                uf.prossimoCliente(op);
                uf.eseguiOperazione(op);
                attendi();
            }
        }catch (InterruptedException e){}
    }

    public void attendi() throws InterruptedException{
        if(op.equals("A")){
            TimeUnit.MICROSECONDS.sleep(attesaA);
            return;
        }
        if(op.equals("B")){
            TimeUnit.MICROSECONDS.sleep(attesaB);
            return;
        }
        TimeUnit.MICROSECONDS.sleep(attesaC);
        return;
    }

}
