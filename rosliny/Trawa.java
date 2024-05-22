package simulation.rosliny;

import simulation.base.Organizm;
import simulation.base.Roslina;

import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Trawa extends Roslina {
    public Trawa() {
        setSila(0);
        setInicjatywa(0);
        setID('T');
        setWiek(0);
        setImie("Trawa");
    }
    public Trawa(int x,int y, int sila, int wiek) {
        setX(x);
        setY(y);
        setSila(sila);
        setWiek(wiek);
        setInicjatywa(0);
        setID('T');
        setImie("Trawa");
    }
    @Override
    public void akcja(Organizm[][] plansza, List<Organizm> gra, int szerokosc, int wysokosc, AtomicInteger keycode) {
        Random rand = new Random();
        int rozsiew = rand.nextInt(8);
        rozsiane = rozsiew == 0;
    }
    @Override
    public Organizm kolizja(Organizm off, Organizm def, Organizm[][] plansza, int szerokosc, int wysokosc) {
        return off;
    }
}