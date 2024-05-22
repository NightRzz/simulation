package simulation.base;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class Organizm {
    protected int x;
    protected int y;
    public char id;
    protected String imie;
    protected int sila;
    protected int inicjatywa;
    public boolean rozsiane;
    public boolean rozmnoz;

    public int cooldown;
    public int licznik;
    public boolean wlacz = false;
    public boolean zolwodparlatak;
    protected int wiek;
    public void setCooldown(int cooldown) {
        this.cooldown = cooldown;
    }

    public void setLicznik(int licznik) {
        this.licznik = licznik;
    }

    public void setWlacz(boolean wlacz) {
        this.wlacz = wlacz;
    }
    public void setX(int x) {
        this.x = x;
    }

    public int getX() {
        return x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getY() {
        return y;
    }

    public char getID() {
        return id;
    }

    public String getImie() {
        return imie;
    }

    public void setImie(String imie) {
        this.imie = imie;
    }

    public int getSila() {
        return sila;
    }

    public void setSila(int sila) {
        this.sila = sila;
    }

    public int getInicjatywa() {
        return inicjatywa;
    }

    public void setInicjatywa(int inicjatywa) {
        this.inicjatywa = inicjatywa;
    }

    public int getWiek() {
        return wiek;
    }

    public void setID(char id) {
        this.id = id;
    }

    public void setWiek(int wiek) {
        this.wiek = wiek;
    }

    public abstract void akcja(Organizm[][] plansza, List<Organizm> gra, int szerokosc, int wysokosc, AtomicInteger keycode);

    public abstract Organizm kolizja(Organizm off, Organizm def, Organizm[][] plansza, int szerokosc, int wysokosc);


}