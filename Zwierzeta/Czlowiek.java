package simulation.Zwierzeta;
import simulation.base.Organizm;
import simulation.base.Zwierze;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Czlowiek extends Zwierze {
    public Czlowiek() {
        setSila(5);
        setInicjatywa(4);
        setID('C');
        setWiek(0);
        setImie("Czlowiek");
        setCooldown(6);
        setLicznik(0);
        setWlacz(false);
    }
    public Czlowiek(int x,int y, int sila, int wiek, int cooldown, int licznik, boolean wlacz) {
        setX(x);
        setY(y);
        setSila(sila);
        setCooldown(cooldown);
        setLicznik(licznik);
        setWlacz(wlacz);
        setInicjatywa(4);
        setWiek(wiek);
        setID('C');
        setImie("Czlowiek");
    }

    @Override
    public void akcja(Organizm[][] plansza, List<Organizm> gra, int szerokosc, int wysokosc, AtomicInteger keycode) {
        int key = 0;
        if(keycode != null) {
            key = keycode.get();
            keycode.set(0);
        }
        if (licznik == 0 && !wlacz)
            ++cooldown;
        if (wlacz && cooldown > 5) {
            if (licznik < 6)
                setSila(getSila() - 1);
            else {
                wlacz = false;
                licznik = 0;
                cooldown = 0;
                System.out.println("Umiejetnosc czlowieka przestala dzialac");
            }
        } else {
            ++cooldown;
        }

            switch (key) {
                case 38:
                    if (getX() > 0)
                        setX(getX() - 1);
                    break;
                case 40:
                    if (getX() < wysokosc - 1)
                        setX(getX() + 1);
                    break;
                case 37:
                    if (getY() > 0)
                        setY(getY() - 1);
                    break;
                case 39:
                    if (getY() < szerokosc - 1)
                        setY(getY() + 1);
                    break;
                case 49:
                    if (cooldown < 10)
                        System.out.println("Umiejetnosc czlowieka nie gotowa");
                    if (!wlacz && cooldown > 10) {
                        System.out.println("Umiejetnosc czlowieka wlaczona");
                        setSila(getSila() + 5);
                        wlacz = true;
                    }
                    break;
            }

        System.out.println("Ruszasz sie na pole " + getX() + " " + getY() + " z sila " + getSila());
    }

    @Override
    public Organizm kolizja(Organizm off, Organizm def, Organizm[][] plansza, int szerokosc, int wysokosc) {
        if (def.getID() == getID()) { //broni
            if (def.getSila() > off.getSila()) {
                System.out.println(def.getImie() + " wygyrwa z " + off.getImie());
                return def;
            } else {
                System.out.println(def.getImie() + " przegrywa z " + off.getImie());
                return off;
            }
        } else if (off.getID() == getID()) { //atakuje
            if (off.getSila() > def.getSila()) {
                if (off == def.kolizja(off, def, plansza, szerokosc, wysokosc)) {
                    System.out.println(off.getImie() + " wygyrwa z " + def.getImie());
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