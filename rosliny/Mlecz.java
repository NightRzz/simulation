package simulation.rosliny;

import simulation.base.Organizm;
import simulation.base.Roslina;

import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Mlecz extends Roslina {
    public Mlecz() {
        setSila(0);
        setInicjatywa(0);
        setID('M');
        setWiek(0);
        setImie("Mlecz");
    }
    public Mlecz(int x,int y, int sila, int wiek) {
        setX(x);
        setY(y);
        setSila(sila);
        setWiek(wiek);
        setInicjatywa(0);
        setID('M');
        setImie("Mlecz");
    }

    @Override
    public void akcja(Organizm[][] plansza, List<Organizm> gra, int szerokosc, int wysokosc, AtomicInteger keycode) {
        Random rand = new Random();
        int rozsiew;
        rozsiane = false;
        for(int i = 0;i<3;i++){
            rozsiew = rand.nextInt(8);
            if (rozsiew == 0) {
                rozsiane = true;
                break;
            }
        }
    }

    @Override
    public Organizm kolizja(Organizm off, Organizm def, Organizm[][] plansza, int szerokosc, int wysokosc) {
        return off;
    }

}