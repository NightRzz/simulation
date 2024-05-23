package simulation.Zwierzeta;

import simulation.base.Organizm;
import simulation.base.Zwierze;

import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Owca extends Zwierze {
    public Owca() {
        setSila(4);
        setInicjatywa(4);
        setID('O');
        setWiek(0);
        setImie("Owca");
    }
    public Owca(int x,int y, int sila, int wiek) {
        setX(x);
        setY(y);
        setSila(sila);
        setWiek(wiek);
        setInicjatywa(4);
        setID('O');
        setImie("Owca");
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
                if (getY() < szerokosc - 1) {
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
            def.rozmnoz = true;
            return def;
        } else if (def.getID() == getID()) { // Zwierzeta.Owca defends
            if (def.getSila() > off.getSila()) {
                System.out.println(def.getImie() + " wygrywa z " + off.getImie());
                return def;
            } else if (def.getSila() == off.getSila()) {
                System.out.println(off.getImie() + " wygrywa z " + def.getImie());
                return off;
            } else {
                System.out.println(def.getImie() + " przegrywa z " + off.getImie());
                return off;
            }
        } else if (off.getID() == getID()) { // Zwierzeta.Owca attacks
            if (off.getSila() > def.getSila()) {
                if (off == def.kolizja(off, def, plansza, szerokosc, wysokosc)) {
                    System.out.println(off.getImie() + " wygrywa z " + def.getImie());
                    return off;
                } else if (def.zolwodparlatak) {
                    return def;
                }
            } else if (off.getSila() == def.getSila()) {
                System.out.println(off.getImie() + " wygrywa z " + def.getImie());
                return off;
            } else {
                System.out.println(off.getImie() + " przegrywa z " + def.getImie());
                return def;
            }
        }
        return def;
    }


}