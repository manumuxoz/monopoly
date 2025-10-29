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

        grupos.put("Rojo", new Grupo(solar12, solar13, solar14, "Rojo")); //Creamos grupos
        grupos.put("Amarillo", new Grupo(solar15, solar16, solar17, "Amarillo"));
    }

    //Método para insertar las casillas del lado sur.
    private void insertarLadoSur() { //Repetimos proceso de insertarLadoNorte()
        ArrayList<Casilla> casillasSur = new ArrayList<>();

        casillasSur.add(0, new Casilla("Salida", "Especiales", 0, banca));
        casillasSur.add(1, new Casilla("Solar1", "Solar", 1, 600000, banca, 20000, 300000));
        casillasSur.add(2, new Casilla("Caja", "Caja", 2, banca));
        casillasSur.add(3, new Casilla("Solar2", "Solar", 3, 600000, banca, 40000, 300000));
        casillasSur.add(4, new Casilla("Imp1", 4, 2000000, banca));
        casillasSur.add(5, new Casilla("Trans1", "Transporte", 5, 500000, banca, 250000, 0));
        casillasSur.add(6, new Casilla("Solar3", "Solar", 6, 1000000, banca, 60000, 500000));
        casillasSur.add(7, new Casilla("Suerte", "Suerte", 7, banca));
        casillasSur.add(8, new Casilla("Solar4", "Solar", 8, 1000000, banca, 60000, 500000));
        casillasSur.add(9, new Casilla("Solar5", "Solar", 9, 1200000, banca, 80000, 600000));

        posiciones.add(0,casillasSur);
    }

    //Método que inserta casillas del lado oeste.
    private void insertarLadoOeste() { //Mismo proceso que para insertarLadoNorte()
        ArrayList<Casilla> casillasOeste = new ArrayList<>();

        casillasOeste.add(0, new Casilla("Carcel", "Especiales", 10, banca));
        casillasOeste.add(1, new Casilla("Solar6", "Solar", 11, 1400000, banca, 100000, 700000));
        casillasOeste.add(2, new Casilla("Serv1", "Servicios", 12, 500000, banca, 50000, 0));
        casillasOeste.add(3, new Casilla("Solar7", "Solar", 13, 1400000, banca, 100000, 700000));
        casillasOeste.add(4, new Casilla("Solar8", "Solar", 14, 1600000, banca, 120000, 800000));
        casillasOeste.add(5, new Casilla("Trans2", "Transporte", 15, 500000, banca, 250000, 0));
        casillasOeste.add(6, new Casilla("Solar9", "Solar", 16, 1800000, banca, 140000, 900000));
        casillasOeste.add(7, new Casilla("Caja", "Caja", 17, banca));
        casillasOeste.add(8, new Casilla("Solar10", "Solar", 18, 1800000, banca, 140000, 900000));
        casillasOeste.add(9, new Casilla("Solar11", "Solar", 19, 2200000, banca, 160000, 1100000));

        posiciones.add(1, casillasOeste);
    }

    //Método que inserta las casillas del lado este.
    private void insertarLadoEste() { //Mismo proceso que para insertarLadoNorte()
        ArrayList<Casilla> casillasOeste = new ArrayList<>();

        casillasOeste.add(0, new Casilla("IrCarcel", "Especiales", 30, banca));
        casillasOeste.add(1, new Casilla("Solar18", "Solar", 31, 3000000, banca, 260000, 1500000));
        casillasOeste.add(2, new Casilla("Solar19", "Solar", 32, 3000000, banca, 260000, 1500000));
        casillasOeste.add(3, new Casilla("Caja", "Caja", 33, banca));
        casillasOeste.add(4, new Casilla("Solar20", "Solar", 34, 3200000, banca, 280000, 1600000));
        casillasOeste.add(5, new Casilla("Trans4", "Transporte", 35, 500000, banca, 250000, 0));
        casillasOeste.add(6, new Casilla("Suerte", "Suerte", 36, banca));
        casillasOeste.add(7, new Casilla("Solar21", "Solar", 37, 3500000, banca, 350000, 1750000));
        casillasOeste.add(8, new Casilla("Imp2", 38, 2000000, banca));
        casillasOeste.add(9, new Casilla("Solar22", "Solar", 39, 4000000, banca, 500000, 2000000));

        posiciones.add(3, casillasOeste);
    }

    //Para imprimir el tablero, modificamos el método toString().
    @Override
    public String toString() {
        StringBuilder tableroStr = new StringBuilder();

        // Crear HashMap con todas las casillas por posición
        HashMap<Integer, Casilla> todasCasillas = new HashMap<>();
        for (ArrayList<Casilla> lado : posiciones) {
            for (Casilla casilla : lado) {
                int posicion = (int) casilla.getPosicion();
                todasCasillas.put(posicion, casilla);
            }
        }

        // LÍNEA 1: POSICIONES 20-30 (Norte: Parking -> IrCarcel)
        tableroStr.append("| ");
        for (int pos = 20; pos <= 30; pos++) {
            Casilla casilla = todasCasillas.get(pos);
            tableroStr.append(formatearCasilla(casilla));
            if (pos < 30) tableroStr.append("|");
        }
        tableroStr.append(" |\n");

        // LÍNEAS 2-10: LADOS OESTE Y ESTE - ESTE INVERTIDO
        for (int i = 0; i < 9; i++) {
            int posOeste = 19 - i; // Solar11 -> Solar6 (de arriba a abajo)
            int posEste = 31 + i;  // Solar18 -> Solar22 (de arriba a abajo) - NORMAL

            // Casilla OESTE
            Casilla casillaOeste = todasCasillas.get(posOeste);
            String oesteFormateado = formatearCasilla(casillaOeste);
            tableroStr.append("| ").append(oesteFormateado).append(" |");

            // ESPACIO CENTRAL VACÍO (45 espacios)
            for (int j = 0; j < 106; j++) tableroStr.append(" ");

            // Casilla ESTE - orden normal: Solar18, Solar19, Caja, Solar20, Trans4, Suerte, Solar21, Imp2, Solar22
            Casilla casillaEste = todasCasillas.get(posEste);
            String esteFormateado = formatearCasilla(casillaEste);
            tableroStr.append("| ").append(esteFormateado).append(" |\n");
        }

        // LÍNEA 11: POSICIONES 0-10 (Sur: Carcel -> Salida) - INVERTIDA
        tableroStr.append("| ");
        for (int pos = 10; pos >= 0; pos--) {
            Casilla casilla = todasCasillas.get(pos);
            tableroStr.append(formatearCasilla(casilla));
            if (pos > 0) tableroStr.append("|");
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

    private inicializarGrupos(ArrayList<ArrayList<Casilla>> posiciones) {
        //Creamos los grupos de este lado e insertamos en el hashmap







        Grupo grupoBlack = new Grupo(solar1, solar2, "Black");
        grupos.put("Black", grupoBlack);
        Grupo grupoBlue = new Grupo(solar3, solar4, solar5, "Blue");
        grupos.put("Blue", grupoBlue);
        Grupo grupoPurple = new Grupo(solar6, solar7, solar8, "Purple");
        grupos.put("Purple", grupoPurple);
        Grupo grupoWhite = new Grupo(solar9, solar10, solar11, "White");
        grupos.put("White", grupoWhite);
        Grupo grupoGreen = new Grupo(solar18, solar19, solar20, "Green");
        grupos.put("Green", grupoGreen);
        Grupo grupoCyan = new Grupo(solar21, solar22, "Cyan");
        grupos.put("Cyan", grupoCyan);
    }

    // Método para formatear cada casilla del tablero para su impresión.
    private String formatearCasilla(Casilla casilla) {
        String nombre = casilla.getNombre();
        String avatares = obtenerAvatares(casilla);
        String color = obtenerColor(casilla);
        String nombreFormateado = formatearNombre(nombre);
        return color + nombreFormateado + avatares + RESET;
    }

    // Método para formatear avatares de una casilla pasada por argumento.
    private String obtenerAvatares(Casilla casilla) {
        if (casilla.getAvatares().isEmpty()) return "  ";
        StringBuilder avataresStr = new StringBuilder();
        for (Avatar avatar : casilla.getAvatares()) {
            avataresStr.append("&").append(avatar.getId());
        }
        if (avataresStr.length() == 2) return avataresStr.toString();
        else if (avataresStr.length() == 1) return avataresStr.toString() + " ";
        else return avataresStr.substring(0, 2);
    }

    // Método para formatear el nombre de cada casilla.
    private String formatearNombre(String nombre) {
        switch (nombre) {
            case "Parking": return "Parking  ";
            case "IrCarcel": return "IrCarcel ";
            case "Carcel": return "Carcel   ";
            case "Salida": return "Salida   ";
            case "Suerte": return "Suerte   ";
            case "Caja": return "Caja     ";
            case "Solar1": return "Solar1   ";
            case "Solar2": return "Solar2   ";
            case "Solar3": return "Solar3   ";
            case "Solar4": return "Solar4   ";
            case "Solar5": return "Solar5   ";
            case "Solar6": return "Solar6   ";
            case "Solar7": return "Solar7   ";
            case "Solar8": return "Solar8   ";
            case "Solar9": return "Solar9   ";
            case "Solar10": return "Solar10  ";
            case "Solar11": return "Solar11  ";
            case "Solar12": return "Solar12  ";
            case "Solar13": return "Solar13  ";
            case "Solar14": return "Solar14  ";
            case "Solar15": return "Solar15  ";
            case "Solar16": return "Solar16  ";
            case "Solar17": return "Solar17  ";
            case "Solar18": return "Solar18  ";
            case "Solar19": return "Solar19  ";
            case "Solar20": return "Solar20  ";
            case "Solar21": return "Solar21  ";
            case "Solar22": return "Solar22  ";
            case "Trans1": return "Trans1   ";
            case "Trans2": return "Trans2   ";
            case "Trans3": return "Trans3   ";
            case "Trans4": return "Trans4   ";
            case "Serv1": return "Serv1    ";
            case "Serv2": return "Serv2    ";
            case "Imp1": return "Imp1     ";
            case "Imp2": return "Imp2     ";
            default: return String.format("%-8s", nombre);
        }
    }

    // Método para obtener el color con el que queremos imprimir cada casilla del tablero.
    private String obtenerColor(Casilla casilla) {
        if (casilla.getGrupo() != null && casilla.getTipo().equals("Solar")) {
            String colorGrupo = casilla.getGrupo().getColorGrupo();
            switch (colorGrupo.toLowerCase()) {
                case "black": return BLACK;
                case "blue": return BLUE;
                case "purple": return PURPLE;
                case "white": return WHITE;
                case "red": return RED;
                case "yellow": return YELLOW;
                case "green": return GREEN;
                case "cyan": return CYAN;
            }
        }

        return RESET;
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
