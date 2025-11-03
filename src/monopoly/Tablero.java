package monopoly;

import partida.*;
import java.util.ArrayList;
import java.util.HashMap;
import static monopoly.Valor.*;


public class Tablero {
    //Atributos.
    private ArrayList<ArrayList<Casilla>> posiciones; //Posiciones del tablero: se define como un arraylist de arraylists de casillas (uno por cada lado del tablero).
    private HashMap<String, Grupo> grupos; //Grupos del tablero, almacenados como un HashMap con clave String (será el color del grupo).
    private Jugador banca; //Un jugador que será la banca.

    //Constructor: únicamente le pasamos el jugador banca (que se creará desde el menú).
    public Tablero(Jugador banca) {
        posiciones = new ArrayList<>();
        grupos = new HashMap<>();
        this.banca = banca;
        generarCasillas();
    }

    //Getters:
    public ArrayList<ArrayList<Casilla>> getPosiciones() {
        return posiciones;
    }

    //Método para crear todas las casillas del tablero. Formado a su vez por cuatro métodos (1/lado).
    private void generarCasillas() {
        insertarLadoSur();
        insertarLadoOeste();
        insertarLadoNorte();
        insertarLadoEste();
    }

    //Método para insertar las casillas del lado norte.
    private void insertarLadoNorte() {
        ArrayList<Casilla> casillasNorte = new ArrayList<>(); //Creamos un arraylist de casillas

        //Vamos creando casillas con los diferentes constructores para cada tipo y añadimos al arraylist
        casillasNorte.add(0, new Casilla("Parking", "Especiales", 20, banca)); //Añadimos al arraylist
        Casilla solar12 = new Casilla("Solar12", "Solar", 21, 2200000, banca, 180000, 1100000);
        casillasNorte.add(1, solar12);
        casillasNorte.add(2, new Casilla("Suerte", "Suerte", 22, banca));
        Casilla solar13 = new Casilla("Solar13", "Solar", 23, 2200000, banca, 180000, 1100000);
        casillasNorte.add(3, solar13);
        Casilla solar14 = new Casilla("Solar14", "Solar", 24, 2400000, banca, 200000, 1200000);
        casillasNorte.add(4, solar14);
        casillasNorte.add(5, new Casilla("Trans3", "Transporte", 25, 500000, banca, 250000, 0));
        Casilla solar15 = new Casilla("Solar15", "Solar", 26, 2600000, banca, 220000, 1300000);
        casillasNorte.add(6, solar15);
        Casilla solar16 = new Casilla("Solar16", "Solar", 27, 2600000, banca, 220000, 1300000);
        casillasNorte.add(7, solar16);
        casillasNorte.add(8, new Casilla("Serv2", "Servicios", 28, 500000, banca, 50000, 0));
        Casilla solar17 = new Casilla("Solar17", "Solar", 29, 2800000, banca, 240000, 14000000);
        casillasNorte.add(9, solar17);

        posiciones.add(2, casillasNorte); //Añadimos al arraylist de arraylist de casillas del tablero

        grupos.put("Rojo", new Grupo(solar12, solar13, solar14, RED)); //Creamos grupos
        grupos.put("Amarillo", new Grupo(solar15, solar16, solar17, YELLOW));
    }

    //Método para insertar las casillas del lado sur.
    private void insertarLadoSur() { //Repetimos proceso de insertarLadoNorte()
        ArrayList<Casilla> casillasSur = new ArrayList<>();

        casillasSur.add(0, new Casilla("Salida", "Especiales", 0, banca));
        Casilla solar1 = new Casilla("Solar1", "Solar", 1, 600000, banca, 20000, 300000);
        casillasSur.add(1, solar1);
        casillasSur.add(2, new Casilla("Caja", "Caja", 2, banca));
        Casilla solar2 = new Casilla("Solar2", "Solar", 3, 600000, banca, 40000, 300000);
        casillasSur.add(3, solar2);
        casillasSur.add(4, new Casilla("Imp1", 4, 2000000, banca));
        casillasSur.add(5, new Casilla("Trans1", "Transporte", 5, 500000, banca, 250000, 0));
        Casilla solar3 = new Casilla("Solar3", "Solar", 6, 1000000, banca, 60000, 500000);
        casillasSur.add(6, solar3);
        casillasSur.add(7, new Casilla("Suerte", "Suerte", 7, banca));
        Casilla solar4 = new Casilla("Solar4", "Solar", 8, 1000000, banca, 60000, 500000);
        casillasSur.add(8, solar4);
        Casilla solar5 = new Casilla("Solar5", "Solar", 9, 1200000, banca, 80000, 600000);
        casillasSur.add(9, solar5);

        posiciones.addFirst(casillasSur);

        grupos.put("Negro", new Grupo(solar1, solar2, BLACK));
        grupos.put("Azul", new Grupo(solar3, solar4, solar5, BLUE));
    }

    //Método que inserta casillas del lado oeste.
    private void insertarLadoOeste() { //Mismo proceso que para insertarLadoNorte()
        ArrayList<Casilla> casillasOeste = new ArrayList<>();

        casillasOeste.add(0, new Casilla("Carcel", "Especiales", 10, banca));
        Casilla solar6 = new Casilla("Solar6", "Solar", 11, 1400000, banca, 100000, 700000);
        casillasOeste.add(1, solar6);
        casillasOeste.add(2, new Casilla("Serv1", "Servicios", 12, 500000, banca, 50000, 0));
        Casilla solar7 = new Casilla("Solar7", "Solar", 13, 1400000, banca, 100000, 700000);
        casillasOeste.add(3, solar7);
        Casilla solar8 = new Casilla("Solar8", "Solar", 14, 1600000, banca, 120000, 800000);
        casillasOeste.add(4, solar8);
        casillasOeste.add(5, new Casilla("Trans2", "Transporte", 15, 500000, banca, 250000, 0));
        Casilla solar9 = new Casilla("Solar9", "Solar", 16, 1800000, banca, 140000, 900000);
        casillasOeste.add(6, solar9);
        casillasOeste.add(7, new Casilla("Caja", "Caja", 17, banca));
        Casilla solar10 = new Casilla("Solar10", "Solar", 18, 1800000, banca, 140000, 900000);
        casillasOeste.add(8, solar10);
        Casilla solar11 = new Casilla("Solar11", "Solar", 19, 2200000, banca, 160000, 1100000);
        casillasOeste.add(9, solar11);

        posiciones.add(1, casillasOeste);

        grupos.put("Morado", new Grupo(solar6, solar7, solar8, PURPLE));
        grupos.put("Blanco", new Grupo(solar9, solar10, solar11, WHITE));
    }

    //Método que inserta las casillas del lado este.
    private void insertarLadoEste() { //Mismo proceso que para insertarLadoNorte()
        ArrayList<Casilla> casillasOeste = new ArrayList<>();

        casillasOeste.add(0, new Casilla("IrCarcel", "Especiales", 30, banca));
        Casilla solar18 = new Casilla("Solar18", "Solar", 31, 3000000, banca, 260000, 1500000);
        casillasOeste.add(1, solar18);
        Casilla solar19 = new Casilla("Solar19", "Solar", 32, 3000000, banca, 260000, 1500000);
        casillasOeste.add(2, solar19);
        casillasOeste.add(3, new Casilla("Caja", "Caja", 33, banca));
        Casilla solar20 = new Casilla("Solar20", "Solar", 34, 3200000, banca, 280000, 1600000);
        casillasOeste.add(4, solar20);
        casillasOeste.add(5, new Casilla("Trans4", "Transporte", 35, 500000, banca, 250000, 0));
        casillasOeste.add(6, new Casilla("Suerte", "Suerte", 36, banca));
        Casilla solar21 = new Casilla("Solar21", "Solar", 37, 3500000, banca, 350000, 1750000);
        casillasOeste.add(7, solar21);
        casillasOeste.add(8, new Casilla("Imp2", 38, 2000000, banca));
        Casilla solar22 = new Casilla("Solar22", "Solar", 39, 4000000, banca, 500000, 2000000);
        casillasOeste.add(9, solar22);

        posiciones.add(3, casillasOeste);

        grupos.put("Verde", new Grupo(solar18, solar19, solar20, GREEN));
        grupos.put("Cian", new Grupo(solar21, solar22, CYAN));
    }

    //Para imprimir el tablero, modificamos el método toString().
    @Override
    public String toString() {
        StringBuilder tableroStr = new StringBuilder();

        //Obtenemos los lados que contienen las casillas
        ArrayList<Casilla> ladoSur = posiciones.get(0);
        ArrayList<Casilla> ladoOeste = posiciones.get(1);
        ArrayList<Casilla> ladoNorte = posiciones.get(2);
        ArrayList<Casilla> ladoEste = posiciones.get(3);

        //NORTE:
        for (int i = 0; i < 10; i++) {
            Casilla casilla = ladoNorte.get(i);

        }

        //ESTE / OESTE / ESPACIO VACÍO:
        for (int i = 0; i < 9; i++) {

            Casilla casillaOeste = ladoOeste.get(i); //Solar11 -> Solar6 (de arriba a abajo)
            tableroStr.append("| ").append(formatearCasilla(casillaOeste)).append(" |");

            tableroStr.repeat(" ", 106);

            Casilla casillaEste = ladoEste.get(i); //Solar18 -> Solar22 (de arriba a abajo)
            tableroStr.append("| ").append(formatearCasilla(casillaEste)).append(" |\n");
        }

        //SUR:
        tableroStr.append("| ");
        for (int i = 9; i >= 0; i--) {
            tableroStr.append(formatearCasilla(ladoSur.get(i)));
            if (i > 0) tableroStr.append("|");
        }
        tableroStr.append(" |\n");

        return tableroStr.toString();
    }

    //Método usado para buscar la casilla con el nombre pasado como argumento:
    public Casilla encontrar_casilla(String nombre) {
        for (ArrayList<Casilla> lado : posiciones) { //Recorremos los lados del tablero
            for (Casilla casilla : lado) { //Recorremos las casillas de casa lado
                if (casilla.getNombre().equals(nombre)) //Comparamos strings
                    return casilla;
            }
        }
        return null;
    }

    // Nuevos métodos:

    // Método para formatear cada casilla del tablero para su impresión.
    private String formatearCasilla(Casilla casilla) {
        String nombre = casilla.getNombre();
        String avatares = formatearAvatares(casilla);
        String color = RESET;
        if (casilla.getTipo().equals("Solar"))
            color = casilla.getGrupo().getColorGrupo();

        return color + String.format("%-15s", casilla.getNombre()) + avatares + RESET;
    }

    //Método para formatear avatares de una casilla pasada por argumento.
    private String formatearAvatares(Casilla casilla) {
        if (casilla.getAvatares().isEmpty()) return "  ";

        StringBuilder avataresStr = new StringBuilder();

        for (Avatar avatar : casilla.getAvatares()) {
            avataresStr.append("&").append(avatar.getId());
        }

        return avataresStr.toString();
    }

    //Método usado para buscar la cosilla en la posicion pasada por argumento:
    public Casilla encontrarCasillaPorPosicion(float posicion) {
        for (ArrayList<Casilla> lado : posiciones) {
            for (Casilla casilla : lado) {
                if (casilla.getPosicion() == posicion) {
                    return casilla;
                }
            }
        }
        return null;
    }
}
