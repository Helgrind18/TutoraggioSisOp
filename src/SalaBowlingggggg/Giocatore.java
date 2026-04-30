package SalaBowlingggggg;

import java.util.concurrent.TimeUnit;

public class Giocatore extends Thread{
    private SalaBowling s;
    public Giocatore(SalaBowling s){
        this.s = s;
    }
    private int tiri = 10;

    @Override
    public void run(){
        try{
            String info = s.fornisciInformazione();
            while(tiri > 0){
                s.gioca(info);
                TimeUnit.SECONDS.sleep(1);
                tiri --;
            }
        }catch(InterruptedException e){}

    }

}
