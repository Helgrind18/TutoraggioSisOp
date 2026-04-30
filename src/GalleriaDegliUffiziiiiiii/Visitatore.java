package GalleriaDegliUffiziiiiiii;

public class Visitatore extends Thread{
    private GalleriaDegliUffizi g;
    private final int lingua;
    public Visitatore(GalleriaDegliUffizi galleriaDegliUffizi, int i) {
        g = galleriaDegliUffizi;
        lingua = i;
    }

    public int getLingua() {
        return lingua;
    }

    @Override
    public void run() {
        try {
            g.iniziaVisita(lingua);
            g.esci(lingua);
        }catch (InterruptedException e){}
    }
}
