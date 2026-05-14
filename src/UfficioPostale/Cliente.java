package UfficioPostale;

public class Cliente extends Thread{
    private UfficioPostale uf;
    private String op;
    public Cliente(UfficioPostale uf, String operazione){
        this.uf = uf;
        this.op = operazione;
    }

    @Override
    public void run(){
        try{
            if(!uf.ritiraTicket(op)){
                System.out.println("Il cliente non può entrare perchè non ci sono più ticket");
                return;
            }
            //Altrimenti può prendere il biglietto
            uf.attendiSportello(op); // attende il proprio turno allo sportello per essere servito da un impiegato
            System.out.println("Il cliente ha lasciato l'ufficio");
        }catch (InterruptedException e){}
    }
}
