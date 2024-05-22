package simulation.Zwierzeta;
import simulation.base.Organizm;
import simulation.base.Zwierze;

import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Lis extends Zwierze {
    public Lis() {
        setSila(3);
        setInicjatywa(7);
        setID('L');
        setWiek(0);
        setImie("Lis");
    }
    public Lis(int x,int y, int sila, int wiek) {
        setX(x);
        setY(y);
        setSila(sila);
        setWiek(wiek);
        setInicjatywa(7);
        setID('L');
        setImie("Lis");
    }

    @Override
    public void akcja(Organizm[][] plansza, List<Organizm> gra, int szerokosc, int wysokosc, AtomicInteger keycode) {
        Random rand = new Random();
        int kierunek = rand.nextInt(4);
        switch (kierunek) {
            case 0:
                if (getY() > 0) {
                    if (plansza[getX()][getY() - 1] instanceof Zwierze) {
                        if (plansza[getX()][getY() - 1].getSila() < this.getSila() || plansza[getX()][getY() - 1].getID() == 'L') {
                            setY(getY() - 1);
                            break;
                        }
                    } else {
                        setY(getY() - 1);
                        break;
                    }
                }
                break;
            case 1:
                if (getY() < szerokosc - 1) {
                    if (plansza[getX()][getY() + 1] instanceof Zwierze) {
                        if (plansza[getX()][getY() + 1].getSila() < this.getSila() || plansza[getX()][getY() + 1].getID() == 'L') {
                            setY(getY() + 1);
                            break;
                        }
                    } else {
                        setY(getY() + 1);
                        break;
                    }
                }
                break;
            case 2:
                if (getX() < wysokosc - 1) {
                    if (plansza[getX() + 1][getY()] instanceof Zwierze) {
                        if (plansza[getX() + 1][getY()].getSila() < this.getSila() || plansza[getX() + 1][getY()].getID() =='L') {
                            setX(getX() + 1);
                            break;
                        }
                    } else {
                        setX(getX() + 1);
                        break;
                    }
                }
                break;
            case 3:
                if (getX() > 0) {
                    if (plansza[getX() - 1][getY()] instanceof Zwierze) {
                        if (plansza[getX() - 1][getY()].getSila() < this.getSila() || plansza[getX() - 1][getY()].getID() == 'L'){
                            setX(getX() - 1);
                            break;
                        }
                    } else {
                        setX(getX() - 1);
                        break;
                    }
                }
                break;
        }
    }

    @Override
    public Organizm kolizja(Organizm off, Organizm def, Organizm[][] plansza, int szerokosc, int wysokosc) {
        if (def.getID() == off.getID()) {
            rozmnoz = true;
            return def;
        } else if (def.getID() == getID()) { // Zwierzeta.Owca defends
            if (def.getSila() > off.getSila()) {
                System.out.println(def.getImie() + " wins against " + off.getImie());
                return def;
            } else if (def.getSila() == off.getSila()) {
                System.out.println(off.getImie() + " wins against " + def.getImie());
                return off;
            } else {
                System.out.println(def.getImie() + " loses against " + off.getImie());
                return off;
            }
        } else if (off.getID() == getID()) { // Zwierzeta.Owca attacks
            if (off.getSila() > def.getSila()) {
                if (off == def.kolizja(off, def, plansza, szerokosc, wysokosc)) {
                    System.out.println(off.getImie() + " wins against " + def.getImie());
                    return off;
                } else if (def.zolwodparlatak) {
                    return def;
                }
            } else if (off.getSila() == def.getSila()) {
                System.out.println(off.getImie() + " wins against " + def.getImie());
                return off;
            } else {
                System.out.println(off.getImie() + " loses against " + def.getImie());
                return def;
            }
        }
        return def;
    }
}