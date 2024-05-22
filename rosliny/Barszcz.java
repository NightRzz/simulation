package simulation.rosliny;

import simulation.base.Organizm;
import simulation.base.Roslina;
import simulation.base.Zwierze;

import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Barszcz extends Roslina {
    public Barszcz() {
        setSila(10);
        setInicjatywa(0);
        setID('B');
        setWiek(0);
        setImie("Barszcz Sosnowskiego");
    }
    public Barszcz(int x,int y, int sila, int wiek) {
        setX(x);
        setY(y);
        setSila(sila);
        setInicjatywa(0);
        setWiek(wiek);
        setID('B');
        setImie("Barszcz Sosnowskiego");
    }
    @Override
    public void akcja(Organizm[][] plansza, List<Organizm> gra, int szerokosc, int wysokosc, AtomicInteger keycode) {
        eksterminacja(plansza,gra);
        Random rand = new Random();
        int rozsiew = rand.nextInt(50);
        rozsiane = rozsiew == 0;
    }
    @Override
    public Organizm kolizja(Organizm off, Organizm def, Organizm[][] plansza, int szerokosc, int wysokosc) {
        return def;
    }
    public void eksterminacja(Organizm[][] plansza, List<Organizm> gra) {
        //System.out.println("rosliny.Barszcz zabija zwierzat z sasiednich pol... ");
        for (int i = 0; i < gra.size(); i++) {
            Organizm org = gra.get(i);
            if ((org instanceof Zwierze || org!=null && org.getID()=='C')&&
                    ((org.getX() == getX() - 1 && org.getY() == getY()) ||
                            (org.getX() == getX() + 1 && org.getY() == getY()) ||
                            (org.getY() == getY() - 1 && org.getX() == getX()) ||
                            (org.getY() == getY() + 1 && org.getX() == getX()))) {
                System.out.println(org.getImie() + " umiera przez rosliny.Barszcz Sosnowskiego");
                plansza[org.getX()][org.getY()] = null;
                gra.set(i, null);
            }
        }
    }
}