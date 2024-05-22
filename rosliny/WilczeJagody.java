package simulation.rosliny;

import simulation.base.Organizm;
import simulation.base.Roslina;

import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class WilczeJagody extends Roslina {
    public WilczeJagody() {
        setSila(99);
        setInicjatywa(0);
        setID('J');
        setWiek(0);
        setImie("Wilcze Jagody");
    }
    public WilczeJagody(int x,int y, int sila, int wiek) {
        setX(x);
        setY(y);
        setSila(sila);
        setWiek(wiek);
        setInicjatywa(0);
        setID('J');
        setImie("Wilcze Jagody");
    }
    @Override
    public void akcja(Organizm[][] plansza, List<Organizm> gra, int szerokosc, int wysokosc, AtomicInteger keycode) {
        Random rand = new Random();
        int rozsiew = rand.nextInt(30);
        rozsiane = rozsiew == 0;
    }
    @Override
    public Organizm kolizja(Organizm off, Organizm def, Organizm[][] plansza, int szerokosc, int wysokosc) {
        System.out.printf("%s zjada %s i ginie\n", off.getImie(), def.getImie());
        return def;
    }

}