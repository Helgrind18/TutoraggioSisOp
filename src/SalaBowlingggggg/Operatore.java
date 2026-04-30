package SalaBowlingggggg;

public class Operatore extends Thread{

    private SalaBowling s;
    public Operatore(SalaBowling s){
        this.s = s;
    }

    @Override
    public void run(){
        try{
            while(true){
                s.preparaPartita();
                s.deposita();
            }
        }catch(InterruptedException e){

        }
    }

}
