package simulation.base;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class Roslina extends Organizm {
    // Abstract methods
        @Override
    public abstract void akcja(Organizm[][] plansza, List<Organizm> gra, int szerokosc, int wysokosc, AtomicInteger keycode);

    public abstract Organizm kolizja(Organizm off, Organizm def, Organizm[][] plansza, int szerokosc, int wysokosc);

}