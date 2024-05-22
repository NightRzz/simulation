package simulation.Zwierzeta;
import simulation.base.Organizm;
import simulation.base.Zwierze;

import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Zolw extends Zwierze {
    public Zolw() {
        setSila(2);
        setInicjatywa(1);
        setID('Z');
        setWiek(0);
        setImie("Zolw");
    }
    public Zolw(int x,int y, int sila, int wiek) {
        setX(x);
        setY(y);
        setSila(sila);
        setWiek(wiek);
        setInicjatywa(1);
        setID('Z');
        setImie("Zolw");
    }

    @Override
    public void akcja(Organizm[][] plansza, List<Organizm> gra, int szerokosc, int wysokosc, AtomicInteger keycode) {
        Random rand = new Random();
        int ruch = rand.nextInt(3);
        if(ruch==0) {
            int kierunek = rand.nextInt(4);
            switch (kierunek) {
                case 0:
                    if (getY() > 0) {
                        setY(getY() - 1);
                        break;
                    }
                    break;
                case 1:
                    if (getY() < szerokosc - 1) {
                        setY(getY() + 1);
                        break;
                    }
                    break;
                case 2:
                    if (getX() < wysokosc - 1) {
                        setX(getX() + 1);
                        break;
                    }
                    break;
                case 3:
                    if (getX() > 0) {
                        setX(getX() - 1);
                        break;
                    }
                    break;
            }


            //System.out.println(getImie() + " poruszył się na " + getX() + " " + getY());
        }

    }

    @Override
    public Organizm kolizja(Organizm off, Organizm def, Organizm[][] plansza, int szerokosc, int wysokosc) {
        if (def.getID() == off.getID()) {
            rozmnoz = true;
            return def;
        } else if (def.getID() == getID()) { // Zwierzeta.Zolw broni
            if (off.getSila() < 5) { // odparcie ataku
                System.out.println(def.getImie() + " odpiera atak " + off.getImie());
                zolwodparlatak = true;
                return def;
            } else {
                if (def.getSila() > off.getSila()) {
                    System.out.println(def.getImie() + " wygyrwa z " + off.getImie());
                    return def;
                } else {
                    System.out.println(def.getImie() + " przegrywa z " + off.getImie());
                    return off;
                }
            }
        } else if (off.getID() == getID()) { // Zwierzeta.Zolw atakuje
            if (off.getSila() > def.getSila()) {
                if (off == def.kolizja(off, def, plansza, szerokosc, wysokosc)) {
                    System.out.println(off.getImie() + " wygyrwa z " + def.getImie());
                    return off;
                }
            } else if (off.getSila() == def.getSila()) {
                System.out.println(off.getImie() + " wygrywa z " + def.getImie());
                return off;
            } else {
                System.out.println(off.getImie() + " przegrywa z " + def.getImie());
                return def;
            }
        }
        return null;
    }


}