package simulation.Zwierzeta;
import simulation.base.Organizm;
import simulation.base.Zwierze;

import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Antylopa extends Zwierze {
    public Antylopa() {
        setSila(4);
        setInicjatywa(4);
        setID('A');
        setWiek(0);
        setImie("Antylopa");
    }
    public Antylopa(int x,int y, int sila, int wiek) {
        setX(x);
        setY(y);
        setSila(sila);
        setInicjatywa(4);
        setWiek(wiek);
        setID('A');
        setImie("Antylopa");
    }
    @Override
    public void akcja(Organizm[][] plansza, List<Organizm> gra, int szerokosc, int wysokosc, AtomicInteger keycode) {
        Random rand = new Random();
        int kierunek = rand.nextInt(4);
        switch (kierunek) {
            case 0:
                if (getY() > 1) {
                    setY(getY() - 2);
                    break;
                }
                break;
            case 1:
                if (getY() < szerokosc - 2) {
                    setY(getY() + 2);
                    break;
                }
                break;
            case 2:
                if (getX() < wysokosc - 2) {
                    setX(getX() + 2);
                    break;
                }
                break;
            case 3:
                if (getX() > 1) {
                    setX(getX() - 2);
                    break;
                }
                break;
        }
    }
    private Organizm UciekaDef(Organizm[][] plansza, Organizm off, Organizm def) {
        plansza[getX()][getY()] = def;
        System.out.println(getImie() + " ucieka przed " + off.getImie() + " na pole " + getX() + " " + getY());
        return off;
    }
    private Organizm UciekaOff(Organizm[][] plansza, Organizm off, Organizm def) {
        plansza[getX()][getY()] = off;
        System.out.println(getImie() + " ucieka przed " + def.getImie() + " na pole " + getX() + " " + getY());
        return def;
    }
    
    @Override
    public Organizm kolizja(Organizm off, Organizm def, Organizm[][] plansza, int szerokosc, int wysokosc) {
        Random rand = new Random();
        int ucieczka = rand.nextInt(2);
        if (def.getID() == off.getID()) {
            rozmnoz = true;
            return off;
        } else if (ucieczka == 1 && def instanceof Zwierze) {
            int kierunek = rand.nextInt(4);
            switch (kierunek) {
                case 0:
                    if (getY() > 0 && plansza[getX()][getY() - 1] == null) {
                        setY(getY() - 1);
                        if (def.getID() == getID()) {
                            return UciekaDef(plansza, off, def);
                        } else {
                           return UciekaOff(plansza, off, def);
                        }
                    }
                    break;
                case 1:
                    if (getY() < szerokosc - 1 && plansza[getX()][getY() + 1] == null) {
                        setY(getY() + 1);
                        if (def.getID() == getID()) {
                            return UciekaDef(plansza, off, def);
                        } else {
                            return UciekaOff(plansza, off, def);
                        }
                    }
                    break;
                case 2:
                    if (getX() < wysokosc - 1 && plansza[getX() + 1][getY()] == null) {
                        setX(getX() + 1);
                        if (def.getID() == getID()) {
                            return UciekaDef(plansza, off, def);
                        } else {
                            return UciekaOff(plansza, off, def);
                        }
                    }
                    break;
                case 3:
                    if (getX() > 0 && plansza[getX() - 1][getY()] == null) {
                        setX(getX() - 1);
                        if (def.getID() == getID()) {
                            return UciekaDef(plansza, off, def);
                        } else {
                            return UciekaOff(plansza, off, def);
                        }
                    }
                    break;
                }

        } else if (def.getID() == getID()) { //broni
            if (def.getSila() > off.getSila()) {
                System.out.println(def.getImie() + " wygrywa z " + off.getImie());
                return def;
            } else {
                System.out.println(def.getImie() + " przegrywa z " + off.getImie());
                return off;
            }
        } else if (off.getID() == getID()) { //atakuje
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
        return null;
    }


}