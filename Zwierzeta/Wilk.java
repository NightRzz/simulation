package simulation.Zwierzeta;
import simulation.base.Organizm;
import simulation.base.Zwierze;

import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Wilk extends Zwierze {
    public Wilk() {
        setSila(9);
        setInicjatywa(5);
        setID('W');
        setWiek(0);
        setImie("Wilk");
    }
    public Wilk(int x,int y, int sila, int wiek) {
        setX(x);
        setY(y);
        setSila(sila);
        setInicjatywa(5);
        setWiek(wiek);
        setID('W');
        setImie("Wilk");
    }

    @Override
    public void akcja(Organizm[][] plansza, List<Organizm> gra, int szerokosc, int wysokosc, AtomicInteger keycode) {
        Random rand = new Random();
        int kierunek = rand.nextInt(4);
        switch (kierunek) {
            case 0:
                if (getY() > 0) {
                    setY(getY() - 1);
                }
                break;
            case 1:
                if (getY() < szerokosc - 1 ) {
                    setY(getY() + 1);
                }
                break;
            case 2:
                if (getX() < wysokosc - 1 ) {
                    setX(getX() + 1);
                }
                break;
            case 3:
                if (getX() > 0 ) {
                    setX(getX() - 1);
                }
                break;
        }
        //System.out.println(getImie() + " poruszyła się na " + getX() + " " + getY());
    }

    @Override
    public Organizm kolizja(Organizm off, Organizm def, Organizm[][] plansza, int szerokosc, int wysokosc) {
        if (def.getID() == off.getID()) {
            rozmnoz = true;

            return def;
        } else if (def.getID() == getID()) { // Zwierzeta.Wilk defends
            if (def.getSila() > off.getSila()) {
                System.out.println(def.getImie() + " wins against " + off.getImie());
                return def;
            } else {
                System.out.println(def.getImie() + " loses against " + off.getImie());
                return off;
            }
        } else if (off.getID() == getID()) { // Zwierzeta.Wilk attacks
            if (off.getSila() > def.getSila()) {
                if (off == def.kolizja(off, def, plansza, szerokosc, wysokosc)) {
                    System.out.println(off.getImie() + " wins against " + def.getImie());
                    return off;
                }
            } else if (off.getSila() == def.getSila()) {
                System.out.println(off.getImie() + " wins against " + def.getImie());
                return off;
            } else {
                System.out.println(off.getImie() + " loses against " + def.getImie());
                return def;
            }
        }
        return null;
    }

}