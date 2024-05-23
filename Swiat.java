package simulation;

import simulation.Zwierzeta.*;
import simulation.base.Organizm;
import simulation.base.Roslina;
import simulation.base.Zwierze;
import simulation.rosliny.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.util.*;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Swiat extends JFrame {
    private final JPanel grid;
    public static Swiat okno;
    private static JTextArea logArea;
    private int szerokosc;
    private Map<Character, Color> colorMap;
    private int wysokosc;
    public static Organizm[][] plansza;

    final AtomicInteger[] keyCode = {null};
    public static List<Organizm> Gra = new ArrayList<>();

    public Swiat(int Szerokosc, int Wysokosc) {
        szerokosc = Szerokosc;
        wysokosc = Wysokosc;
        plansza = new Organizm[wysokosc][szerokosc];
        for (int i = 0; i < wysokosc; i++) {
            for (int j = 0; j < szerokosc; j++) {
                plansza[i][j] = null;
            }
        }
        setTitle("Yuriy Dyedyk");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        colorMap = new HashMap<>();
        colorMap.put('A', new Color(168, 131, 25, 127)); // Czerwony dla Antylopa
        colorMap.put('O', new Color(237, 227, 199, 127)); // Zielony dla Owca
        colorMap.put('W', new Color(117, 115, 110, 127)); // Niebieski dla Wilk
        colorMap.put('L', new Color(217, 138, 2, 127)); // Żółty dla Lisa
        colorMap.put('Z', new Color(189, 165, 115, 127)); // Szary dla Żółwia
        colorMap.put('T', new Color(7, 242, 11, 127)); // Zielony dla Trawy
        colorMap.put('M', new Color(225, 232, 26, 127)); // Biały dla Mlecz
        colorMap.put('J', new Color(11, 8, 61, 127)); // Czarny dla WilczeJagody
        colorMap.put('G', new Color(219, 101, 86, 127)); // Pomarańczowy dla Guarana
        colorMap.put('B', new Color(176, 222, 162, 127)); // Fioletowy dla Barszcz
        colorMap.put('C', new Color(242, 229, 203, 127));
        grid = new JPanel();
        grid.setLayout(new GridLayout(wysokosc, szerokosc));

        initializeGrid();

        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.add(grid, BorderLayout.CENTER);


        JPanel log = new JPanel();
        log.setPreferredSize(new Dimension(250, getHeight()));
        JButton wykonajTureButton = new JButton("Wykonaj Ture");
        JButton zapiszButton = new JButton("Zapisz");
        JButton wczytajButton = new JButton("Wczytaj");

        logArea = new JTextArea();
        logArea.setEditable(false);
        log.setLayout(new BoxLayout(log, BoxLayout.Y_AXIS));
        log.add(wykonajTureButton);
        log.add(Box.createRigidArea(new Dimension(0, 25)));
        log.add(zapiszButton);
        log.add(Box.createRigidArea(new Dimension(0, 25)));
        log.add(wczytajButton);
        log.add(Box.createRigidArea(new Dimension(0, 25)));
        log.add(new JScrollPane(logArea));
        wykonajTureButton.addActionListener(e -> {
            wykonajTure(keyCode[0]);
            okno.updateGrid();
            requestFocusInWindow();
        });
        zapiszButton.addActionListener(e -> {
            saveWorldToFile();
            requestFocusInWindow();
        });
        wczytajButton.addActionListener(e -> {
            Wczytaj("save.txt");
            updateGrid();
            requestFocusInWindow();
        });
        contentPanel.add(log, BorderLayout.EAST);

        getContentPane().add(contentPanel);
        PrintStream printStream = new PrintStream(new OutputStream() {
            @Override
            public void write(int b) {
                // redirects data to the text area
                logArea.append(String.valueOf((char)b));
                // scrolls the text area to the end of data
                logArea.setCaretPosition(logArea.getDocument().getLength());
            }
        });

        System.setOut(printStream);
        pack();

        setLocationRelativeTo(null);
    }

    public Map<Character, Color> getColorMap() {
        return colorMap;
    }

    private void initializeGrid() {
        grid.removeAll();

        for (int i = 0; i < wysokosc; i++) {
            for (int j = 0; j < szerokosc; j++) {
                JPanel square = new JPanel(new BorderLayout());

                square.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                boolean organismFound = false;
                for (Organizm organizm : Swiat.Gra) {
                    if (organizm != null && organizm.getX() == i && organizm.getY() == j && organizm.getSila() > -1 && organizm.getInicjatywa() > -1) {
                        JLabel label = new JLabel(String.valueOf(organizm.getID()));
                        label.setOpaque(true);
                        label.setBackground(colorMap.get(organizm.getID()));
                        if(szerokosc+wysokosc>30){
                            label.setFont(label.getFont().deriveFont((szerokosc+wysokosc)*0.35f));
                        }else label.setFont(label.getFont().deriveFont((szerokosc+wysokosc)*1.5f));
                        label.setHorizontalAlignment(SwingConstants.CENTER);

                        square.add(label, BorderLayout.CENTER);

                        organismFound = true;
                        break;
                    }
                }
                if (!organismFound) {
                    JLabel label = new JLabel(String.valueOf(' '));
                    square.add(label, BorderLayout.CENTER);
                    int gridi = i;
                    int gridj = j;
                    square.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            super.mouseClicked(e);
                            String[] options = {"Antylopa", "Owca", "Wilk", "Lis", "Zolw", "Trawa", "Mlecz", "JagodyWilcze", "Guarana", "Barszcz", "Czlowiek"};
                            String input = (String) JOptionPane.showInputDialog(null, "Choose organism",
                                    "Organism Choice", JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                            if (input != null) {
                                char id = input.charAt(0);
                                Organizm newOrganizm = dodajOrganizm(gridi, gridj, id);
                                plansza[gridi][gridj] = newOrganizm;
                                Gra.add(newOrganizm);
                                updateGrid();
                            }
                        }
                    });
                }

                grid.add(square);
            }
        }

        grid.revalidate();
        grid.repaint();
    }
    public void updateGrid() {
        initializeGrid();
    }
    public static Organizm dodajOrganizm(int x, int y, char id) {
        Organizm newOrganizm = switch (id) {
            case 'A' -> new Antylopa();
            case 'O' -> new Owca();
            case 'W' -> new Wilk();
            case 'L' -> new Lis();
            case 'Z' -> new Zolw();
            case 'T' -> new Trawa();
            case 'M' -> new Mlecz();
            case 'J' -> new WilczeJagody();
            case 'G' -> new Guarana();
            case 'B' -> new Barszcz();
            case 'C' -> new Czlowiek();
            default -> null;
        };

        if (newOrganizm != null) {
            newOrganizm.setX(x);
            newOrganizm.setY(y);
        }

        return newOrganizm;
    }

    public void Wczytaj(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            line = reader.readLine();
            String[] parts = line.split(",");
            wysokosc = Integer.parseInt(parts[0]);
            szerokosc = Integer.parseInt(parts[1]);
            int licznik = Integer.parseInt(parts[2]);
            int cooldown = Integer.parseInt(parts[3]);
            boolean wlacz = Boolean.parseBoolean(parts[4]);
            Gra.clear();
            plansza = new Organizm[wysokosc][szerokosc];
            for (int i = 0; i < wysokosc; i++) {
                for (int j = 0; j < szerokosc; j++) {
                    plansza[i][j] = null;
                }
            }
            while ((line = reader.readLine()) != null) {
                parts = line.split(",");
                int x = Integer.parseInt(parts[0]);
                int y = Integer.parseInt(parts[1]);
                int sila = Integer.parseInt(parts[2]);
                char sign = parts[3].charAt(0);
                int wiek = Integer.parseInt(parts[4]);
                Organizm org;
                switch (sign) {
                    case 'A':
                        org = new Antylopa(x, y, sila, wiek);
                        plansza[x][y] = org;
                        Gra.add(org);
                        break;
                    case 'B':
                        org = new Barszcz(x, y, sila, wiek);
                        plansza[x][y] = org;
                        Gra.add(org);
                        break;
                    case 'C':
                        org = new Czlowiek(x, y, sila, wiek, cooldown, licznik, wlacz);
                        plansza[x][y] = org;
                        Gra.add(org);

                        break;
                    case 'G':
                        org = new Guarana(x, y, sila, wiek);
                        plansza[x][y] = org;
                        Gra.add(org);
                        break;
                    case 'L':
                        org = new Lis(x, y, sila, wiek);
                        plansza[x][y] = org;
                        Gra.add(org);
                        break;
                    case 'M':
                        org = new Mlecz(x, y, sila, wiek);
                        plansza[x][y] = org;
                        Gra.add(org);
                        break;
                    case 'O':
                        org = new Owca(x, y, sila, wiek);
                        plansza[x][y] = org;
                        Gra.add(org);
                        break;
                    case 'T':
                        org = new Trawa(x, y, sila, wiek);
                        plansza[x][y] = org;
                        Gra.add(org);
                        break;
                    case 'J':
                        org = new WilczeJagody(x, y, sila, wiek);
                        plansza[x][y] = org;
                        Gra.add(org);
                        break;
                    case 'W':
                        org = new Wilk(x, y, sila, wiek);
                        plansza[x][y] = org;
                        Gra.add(org);
                        break;
                    case 'Z':
                        org = new Zolw(x, y, sila, wiek);
                        plansza[x][y] = org;
                        Gra.add(org);
                        break;
                    default:
                        break;
                }
            }

            JOptionPane.showMessageDialog(null, "Zapis gry zostal wczytany!");
        } catch (IOException | NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void generuj() {
        Random rand = new Random();
        int i = 0;
        while(i < 15) {
            int randy = rand.nextInt(szerokosc);
            int randx = rand.nextInt(wysokosc);
            if (plansza[randx][randy] == null) {
                if (i < 2)
                    plansza[randx][randy] = dodajOrganizm(randx, randy, 'W');
                else if (i < 3)
                    plansza[randx][randy] = dodajOrganizm(randx, randy, 'C');
                else if (i < 4)
                    plansza[randx][randy] = dodajOrganizm(randx, randy, 'A');
                else if (i < 5)
                    plansza[randx][randy] = dodajOrganizm(randx, randy, 'L');
                else if (i < 7)
                    plansza[randx][randy] = dodajOrganizm(randx, randy, 'Z');
                else if (i < 8)
                    plansza[randx][randy] = dodajOrganizm(randx, randy, 'T');
                else if (i < 10)
                    plansza[randx][randy] = dodajOrganizm(randx, randy, 'M');
                else if (i < 11)
                    plansza[randx][randy] = dodajOrganizm(randx, randy, 'J');
                else if (i < 12)
                    plansza[randx][randy] = dodajOrganizm(randx, randy, 'B');
                else if (i < 13)
                    plansza[randx][randy] = dodajOrganizm(randx, randy, 'O');
                else plansza[randx][randy] = dodajOrganizm(randx, randy, 'G');
                i++;
            }
        }
    }

    public void start() {
        generuj();

        SwingUtilities.invokeLater(() -> {
            okno = new Swiat(szerokosc,wysokosc);
            okno.setVisible(true);
            okno.setSize(1200,800);
            okno.addKeyListener(new KeyListener() {
                @Override
                public void keyTyped(KeyEvent e) {}
                @Override
                public void keyPressed(KeyEvent e) {

                    keyCode[0] = new AtomicInteger(e.getKeyCode());

                    logArea.removeAll();
                    wykonajTure(keyCode[0]);
                    okno.updateGrid();


                }
                @Override
                public void keyReleased(KeyEvent e) {}
            });
            okno.requestFocusInWindow();
        });

        zakonczTure();
        //System.out.println();
        wykonajTure(keyCode[0]);

        logArea.removeAll();


    }
public void rozsianie(int kierunek, Organizm org){
    switch (kierunek) {
        case 0:
            if (org.getY() > 0 && plansza[org.getX()][org.getY() - 1] == null) {
                Organizm newPlant = dodajOrganizm(org.getX(), org.getY() - 1, org.getID());
                plansza[org.getX()][org.getY() - 1] = newPlant;
                Gra.add(newPlant);
                System.out.println(org.getImie() + " rozsial sie na pole " + org.getX() + " " + org.getY());
                break;
            }
            break;
        case 1:
            if (org.getY() < szerokosc - 1 && plansza[org.getX()][org.getY() + 1] == null) {

                Organizm newPlant = dodajOrganizm(org.getX(), org.getY() + 1, org.getID());
                plansza[org.getX()][org.getY() + 1] = newPlant;
                Gra.add(newPlant);
                System.out.println(org.getImie() + " rozsial sie na pole " + org.getX() + " " + org.getY());
                break;
            }
            break;
        case 2:
            if (org.getX() < wysokosc - 1 && plansza[org.getX() + 1][org.getY()] == null) {
                Organizm newPlant = dodajOrganizm(org.getX() + 1, org.getY(), org.getID());
                plansza[org.getX() + 1][org.getY()] = newPlant;
                Gra.add(newPlant);
                System.out.println(org.getImie() + " rozsial sie na pole " + org.getX() + " " + org.getY());
                break;
            }
            break;
        case 3:
            if (org.getX() > 0 && plansza[org.getX() - 1][org.getY()] == null) {
                Organizm newPlant = dodajOrganizm(org.getX() - 1, org.getY(), org.getID());
                plansza[org.getX() - 1][org.getY()] = newPlant;
                Gra.add(newPlant);
                System.out.println(org.getImie() + " rozsial sie na pole " + org.getX() + " " + org.getY());
                break;
            }
            break;
    }

}
public void rozmnoz(Organizm org, Random rand, int i, int x1, int y1){
    org.rozmnoz = false;
    org.zolwodparlatak = false;
    Organizm obronca = plansza[org.getX()][org.getY()];
    char atakujacy = org.getID();
    plansza[org.getX()][org.getY()] = org.kolizja(org, plansza[org.getX()][org.getY()], plansza, szerokosc, wysokosc);
    if (org.rozmnoz) {
        int kierunek = rand.nextInt(4);
        switch (kierunek) {
            case 0:
                if (org.getY() > 0 && plansza[org.getX()][org.getY() - 1] == null) {
                    Organizm newPlant = dodajOrganizm(org.getX(), org.getY() - 1, org.getID());
                    plansza[org.getX()][org.getY() - 1] = newPlant;
                    Gra.add(newPlant);
                    System.out.println(org.getImie() + " rozmnozyl sie na pole " + org.getX() + " " + org.getY());
                    break;
                }
                break;
            case 1:
                if (org.getY() < szerokosc - 1 && plansza[org.getX()][org.getY() + 1] == null) {

                    Organizm newPlant = dodajOrganizm(org.getX(), org.getY() + 1, org.getID());
                    plansza[org.getX()][org.getY() + 1] = newPlant;
                    Gra.add(newPlant);
                    System.out.println(org.getImie() + " rozmnozyl sie na pole " + org.getX() + " " + org.getY());
                    break;
                }
                break;
            case 2:
                if (org.getX() < wysokosc - 1 && plansza[org.getX() + 1][org.getY()] == null) {
                    Organizm newPlant = dodajOrganizm(org.getX() + 1, org.getY(), org.getID());
                    plansza[org.getX() + 1][org.getY()] = newPlant;
                    Gra.add(newPlant);
                    System.out.println(org.getImie() + " rozmnozyl sie na pole " + org.getX() + " " + org.getY());
                    break;
                }
                break;
            case 3:
                if (org.getX() > 0 && plansza[org.getX() - 1][org.getY()] == null) {
                    Organizm newPlant = dodajOrganizm(org.getX() - 1, org.getY(), org.getID());
                    plansza[org.getX() - 1][org.getY()] = newPlant;
                    Gra.add(newPlant);
                    System.out.println(org.getImie() + " rozmnozyl sie na pole " + org.getX() + " " + org.getY());
                    break;
                }
                break;
        }

    }
    else if (plansza[org.getX()][org.getY()]!=null && atakujacy == plansza[org.getX()][org.getY()].getID()) {
        for (int j = 0; j < Gra.size(); j++) {
            if (Gra.get(j) != null && obronca == Gra.get(j)) {
                Gra.set(j, null);
            }
        }
        plansza[x1][y1] = null;
    } else {
        if (obronca.getID() != 'Z' && !obronca.zolwodparlatak) {
            Gra.set(i, null);
            plansza[x1][y1] = null;
        } else {
            org.setX(x1);
            org.setY(y1);
        }
    }
}

    public void wykonajTure(AtomicInteger keycode) {
        Random rand = new Random();
        logArea.setText("");
        for (int i = Gra.size() - 1; i >= 0; i--) {
            Organizm org = Gra.get(i);
            if (org != null) {
                int x1 = org.getX();
                int y1 = org.getY();
                org.rozsiane = false;
                org.akcja(plansza, Gra, szerokosc, wysokosc, keycode);
                if (org.getID() == 'C' && org.wlacz) {
                    org.licznik++;
                }
                if (org instanceof Roslina && org.rozsiane) {
                    int kierunek = rand.nextInt(4);
                    rozsianie(kierunek, org);
                } else if (plansza[org.getX()][org.getY()] == plansza[x1][y1]) {
                    plansza[org.getX()][org.getY()] = org;
                } else if (plansza[org.getX()][org.getY()] == null) {
                    plansza[org.getX()][org.getY()] = org;
                    plansza[x1][y1] = null;
                } else if ((org instanceof Zwierze) && plansza[org.getX()][org.getY()] != null) {
                    rozmnoz(org, rand, i, x1, y1);
                }
            }
        }
    }

    public void sortowanie() {
        Gra.sort((o1, o2) -> {
            if (o1.getInicjatywa() == o2.getInicjatywa()) {
                return Integer.compare(o2.getWiek(), o1.getWiek());
            } else {
                return Integer.compare(o2.getInicjatywa(), o1.getInicjatywa());
            }
        });
    }

    private void saveWorldToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("save.txt", false))) {
            int licznik = 0,cooldown = 0;
            boolean wlacz = false;
            for(Organizm org : Gra){
                if(org!=null && org.id == 'C'){
                    licznik = org.licznik;
                    cooldown = org.cooldown;
                    wlacz = org.wlacz;
                    break;
                }
            }
            writer.write(wysokosc + "," + szerokosc+","+licznik+","+ cooldown +","+wlacz );
            writer.newLine();

            for (Organizm org : Gra ) {
                if(org == null) {
                    continue;
                }
                writer.write(org.getX() + "," + org.getY() + "," +
                        org.getSila() + "," + org.getID()+","+org.getWiek());
                writer.newLine();
            }
            JOptionPane.showMessageDialog(null, "Zapisano stan gry do pliku!");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void zakonczTure() {
        if (!Gra.isEmpty()) {
            Gra.set(0, null);
        }
        Gra.clear();
        for (int i = 0; i < wysokosc; i++) {
            for (int j = 0; j < szerokosc; j++) {
                if (plansza[i][j] != null) {
                    Gra.add(plansza[i][j]);
                }
            }
        }
        if (!Gra.isEmpty()) {
            sortowanie();
        }
        for (Organizm org : Gra) {
            if (org != null) {
                org.setWiek(org.getWiek() + 1);
            }
        }
    }
    public static void main(String[] args) {
        JTextField firstField = new JTextField();
        JTextField secondField = new JTextField();
        Object[] message = {
                "Podaj wysokosc mapy:", firstField,
                "Podaj szerokosc mapy:", secondField
        };
        Swiat PoliTech = null;
        int option = JOptionPane.showConfirmDialog(null, message, "Wymiary gry", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                int wysokosc = Integer.parseInt(firstField.getText());
                int szerokosc = Integer.parseInt(secondField.getText());
                PoliTech = new Swiat(wysokosc, szerokosc);
            } catch (NumberFormatException e) {
                System.out.println("Blad wprowadzenia danych, upewnij sie ze wszystkie pola zostaly wypelnione liczba");
            }
        }else{
            return;
        }
        if(PoliTech!=null)PoliTech.start();
    }
}