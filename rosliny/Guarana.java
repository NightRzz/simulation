package simulation.rosliny;

import simulation.base.Organizm;
import simulation.base.Roslina;

import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Guarana extends Roslina {
    public Guarana() {
        setSila(0);
        setInicjatywa(0);
        setID('G');
        setWiek(0);
        setImie("Guarana");
    }
    public Guarana(int x,int y, int sila, int wiek) {
        setX(x);
        setY(y);
        setSila(sila);
        setWiek(wiek);
        setInicjatywa(0);
        setID('G');
        setImie("Guarana");
    }
    @Override
    public void akcja(Organizm[][] plansza, List<Organizm> gra, int szerokosc, int wysokosc, AtomicInteger keycode) {
        Random rand = new Random();
        int rozsiew = rand.nextInt(25);
        rozsiane = rozsiew == 0;
    }
    @Override
    public Organizm kolizja(Organizm off, Organizm def, Organizm[][] plansza, int szerokosc, int wysokosc) {
        off.setSila(off.getSila() + 3);
        System.out.println(def.getImie() + " zostaje zjedzona przez " + off.getImie() + " i zwieksza jego sile o 3 pkt.\n Obecna sila " + off.getImie() + " wynosi " + off.getSila());
        return off;
    }

}