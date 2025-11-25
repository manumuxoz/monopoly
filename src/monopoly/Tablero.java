package monopoly;

import partida.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
    public HashMap<String, Grupo> getGrupos() {
        return grupos;
    }

    //Método para crear todas las casillas del tablero. Formado a su vez por cuatro métodos (1/lado).
    private void generarCasillas() {
        insertarLadoSur();
        insertarLadoOeste();
        insertarLadoNorte();
        insertarLadoEste();

        generarPreciosEdificaciones(grupos); //Asignamos también el precio de las edificaciones
    }

    //Método para insertar las casillas del lado norte.
    private void insertarLadoNorte() {
        ArrayList<Casilla> casillasNorte = new ArrayList<>(); //Creamos un arraylist de casillas

        //Vamos creando casillas con los diferentes constructores para cada tipo y añadimos al arraylist
        casillasNorte.add(0, new Parking(20, banca));
        Solar solar12 = new Solar("Solar12", 21, 2200000, banca, 180000, 1100000, 2200000, 10500000, 2100000, 2100000);
        casillasNorte.add(1, solar12);
        casillasNorte.add(2, new Suerte(22, banca));
        Solar solar13 = new Solar("Solar13", 23, 2200000, banca, 180000, 1100000, 2200000, 10500000, 2100000, 2100000);
        casillasNorte.add(3, solar13);
        Solar solar14 = new Solar("Solar14", 24, 2400000, banca, 200000, 1200000, 2325000, 11000000, 2200000, 2200000);
        casillasNorte.add(4, solar14);
        casillasNorte.add(5, new Transporte("Trans3", 25, banca));
        Solar solar15 = new Solar("Solar15", 26, 2600000, banca, 220000, 1300000, 2450000, 11500000, 2300000, 2300000);
        casillasNorte.add(6, solar15);
        Solar solar16 = new Solar("Solar16", 27, 2600000, banca, 220000, 1300000, 2450000, 11500000, 2300000, 2300000);
        casillasNorte.add(7, solar16);
        casillasNorte.add(8, new Servicios("Serv2", 28, banca));
        Solar solar17 = new Solar("Solar17", 29, 2800000, banca, 240000, 1400000, 2600000, 12000000, 2400000, 2400000);
        casillasNorte.add(9, solar17);

        posiciones.add(2, casillasNorte); //Añadimos al arraylist de arraylist de casillas del tablero

        grupos.put("Rojo", new Grupo(solar12, solar13, solar14, RED)); //Creamos grupos
        grupos.put("Amarillo", new Grupo(solar15, solar16, solar17, YELLOW));
    }

    //Método para insertar las casillas del lado sur.
    private void insertarLadoSur() { //Repetimos proceso de insertarLadoNorte()
        ArrayList<Casilla> casillasSur = new ArrayList<>();

        casillasSur.add(0, new Casilla("Salida", "Especiales", 0, banca));
        Solar solar1 = new Solar("Solar1", 1, 600000, banca, 20000, 300000, 400000, 2500000, 500000, 500000);
        casillasSur.add(1, solar1);
        casillasSur.add(2, new CajaComunidad(2, banca));
        Solar solar2 = new Solar("Solar2", 3, 600000, banca, 40000, 300000, 800000, 4500000, 900000, 900000);
        casillasSur.add(3, solar2);
        casillasSur.add(4, new Impuesto("Imp1", 4, 2000000, banca));
        casillasSur.add(5, new Transporte("Trans1", 5, banca));
        Solar solar3 = new Solar("Solar3", 6, 1000000, banca, 60000, 500000, 1000000, 5500000, 1100000, 1100000);
        casillasSur.add(6, solar3);
        casillasSur.add(7, new Suerte(7, banca));
        Solar solar4 = new Solar("Solar4", 8, 1000000, banca, 60000, 500000, 1000000, 5500000, 1100000, 1100000);
        casillasSur.add(8, solar4);
        Solar solar5 = new Solar("Solar5", 9, 1200000, banca, 80000, 600000, 1250000, 6000000, 1200000, 1200000);
        casillasSur.add(9, solar5);

        posiciones.addFirst(casillasSur);

        grupos.put("Negro", new Grupo(solar1, solar2, BLACK));
        grupos.put("Cian", new Grupo(solar3, solar4, solar5, CYAN));
    }

    //Método que inserta casillas del lado oeste.
    private void insertarLadoOeste() { //Mismo proceso que para insertarLadoNorte()
        ArrayList<Casilla> casillasOeste = new ArrayList<>();

        casillasOeste.add(0, new Especial("Carcel", 10, banca));
        Solar solar6 = new Solar("Solar6", 11, 1400000, banca, 100000, 700000, 1500000, 7500000, 1500000, 1500000);
        casillasOeste.add(1, solar6);
        casillasOeste.add(2, new Servicios("Serv1", 12, banca));
        Solar solar7 = new Solar("Solar7", 13, 1400000, banca, 100000, 700000, 1500000, 7500000, 1500000, 1500000);
        casillasOeste.add(3, solar7);
        Solar solar8 = new Solar("Solar8", 14, 1600000, banca, 120000, 800000, 1750000, 9000000, 1800000, 1800000);
        casillasOeste.add(4, solar8);
        casillasOeste.add(5, new Transporte("Trans2", 15, banca));
        Solar solar9 = new Solar("Solar9", 16, 1800000, banca, 140000, 900000, 1850000, 9500000, 1900000, 1900000);
        casillasOeste.add(6, solar9);
        casillasOeste.add(7, new CajaComunidad(17, banca));
        Solar solar10 = new Solar("Solar10", 18, 1800000, banca, 140000, 900000, 1850000, 9500000, 1900000, 1900000);
        casillasOeste.add(8, solar10);
        Solar solar11 = new Solar("Solar11", 19, 2200000, banca, 160000, 1100000, 2000000, 10000000, 2000000, 2000000);
        casillasOeste.add(9, solar11);

        posiciones.add(1, casillasOeste);

        grupos.put("Morado", new Grupo(solar6, solar7, solar8, PURPLE));
        grupos.put("Blanco", new Grupo(solar9, solar10, solar11, WHITE));
    }

    //Método que inserta las casillas del lado este.
    private void insertarLadoEste() { //Mismo proceso que para insertarLadoNorte()
        ArrayList<Casilla> casillasOeste = new ArrayList<>();

        casillasOeste.add(0, new Especial("IrCarcel", 30, banca));
        Solar solar18 = new Solar("Solar18", 31, 3000000, banca, 260000, 1500000, 2750000, 12750000, 2550000, 2550000);
        casillasOeste.add(1, solar18);
        Solar solar19 = new Solar("Solar19", 32, 3000000, banca, 260000, 1500000, 2750000, 12750000, 2550000, 2550000);
        casillasOeste.add(2, solar19);
        casillasOeste.add(3, new CajaComunidad(33, banca));
        Solar solar20 = new Solar("Solar20", 34, 3200000, banca, 280000, 1600000, 3000000, 14000000, 2800000, 2800000);
        casillasOeste.add(4, solar20);
        casillasOeste.add(5, new Transporte("Trans4", 35, banca));
        casillasOeste.add(6, new Suerte(36, banca));
        Solar solar21 = new Solar("Solar21", 37, 3500000, banca, 350000, 1750000, 3250000, 17000000, 3400000, 3400000);
        casillasOeste.add(7, solar21);
        casillasOeste.add(8, new Impuesto("Imp2", 38, 2000000, banca));
        Solar solar22 = new Solar("Solar22", 39, 4000000, banca, 500000, 2000000, 4250000, 20000000, 4000000, 4000000);
        casillasOeste.add(9, solar22);

        posiciones.add(3, casillasOeste);

        grupos.put("Verde", new Grupo(solar18, solar19, solar20, GREEN));
        grupos.put("Azul", new Grupo(solar21, solar22, BLUE));
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
        tableroStr.append("|");
        for (int i = 0; i < 10; i++) { //Bucle para imprimir las casillas del lado norte (Parking -> Solar17)
            Casilla casilla = ladoNorte.get(i);
            tableroStr.append(formatearCasilla(casilla)).append("|");
        }

        //Antes del retorno de carro, imprimimos la primera casilla del lado este (Carcel)
        tableroStr.append(formatearCasilla(ladoEste.getFirst())).append("|\n");

        //ESTE / OESTE / ESPACIO VACÍO:
        for (int i = 0; i < 9; i++) { //Blucle para imprimir las casillas de ambos laterales más el vacío del interior
            Casilla casillaOeste = ladoOeste.get(9 - i); //Solar11 -> Solar6 (de arriba a abajo)
            tableroStr.append("|").append(formatearCasilla(casillaOeste)).append("|");

            String vacio = ""; //Rellenamos el interior en blanco
            tableroStr.repeat(String.format("%21s", vacio), 8); //Bucle para 8 iteraciones
            tableroStr.append(String.format("%20s", vacio)); //Ultima casilla vacía con un caracter menos para cuadrar

            Casilla casillaEste = ladoEste.get(i + 1); //Solar18 -> Solar22 (de arriba a abajo)
            tableroStr.append("|").append(formatearCasilla(casillaEste)).append("|\n");
        }

        //SUR:
        tableroStr.append("|").append(formatearCasilla(ladoOeste.getFirst())).append("|");
        for (int i = 9; i >= 0; i--) {
            tableroStr.append(formatearCasilla(ladoSur.get(i)));
            if (i > 0) tableroStr.append("|");
        }
        tableroStr.append("|\n").append(RESET);

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

    //Métodos nuevos:

    // Método para formatear cada casilla del tablero para su impresión.
    private String formatearCasilla(Casilla casilla) {
        String color = RESET;
        if (casilla.getTipo().equals("Solar")) color = casilla.getGrupo().getColorGrupo();

        return color + String.format("%-8s" + RESET + "%12s", casilla.getNombre(), formatearAvatares(casilla));
    }

    //Método para formatear avatares de una casilla pasada por argumento.
    private String formatearAvatares(Casilla casilla) {
        if (casilla.getAvatares().isEmpty()) return " ";

        StringBuilder avataresStr = new StringBuilder();

        for (Avatar avatar : casilla.getAvatares())
            avataresStr.append(" &").append(avatar.getId());

        return avataresStr.toString();
    }

    //Método para adignar los precios de las edificaciones en base a su grupo
    private void generarPreciosEdificaciones(HashMap<String, Grupo> grupos) {
        //Como el precio de las ideficaciones depende del grupo al que pertenezcan las casillas de tipo 'Solar'
        //iteramos sobre el hashmap que almacena los grupos
        for (Map.Entry<String, Grupo> grupo : grupos.entrySet()) {
            switch (grupo.getKey()) {
                case "Negro": case "Cian":
                    for (Solar casilla : grupo.getValue().getMiembros()) {
                        casilla.setValorCasa(500000);
                        casilla.setValorHotel(500000);
                        casilla.setValorPiscina(100000);
                        casilla.setValorPistaDeporte(200000);
                    }
                    break;
                case "Morado": case "Blanco":
                    for (Solar casilla : grupo.getValue().getMiembros()) {
                        casilla.setValorCasa(1000000);
                        casilla.setValorHotel(1000000);
                        casilla.setValorPiscina(200000);
                        casilla.setValorPistaDeporte(400000);
                    }
                    break;
                case "Rojo": case "Amarillo":
                    for (Solar casilla : grupo.getValue().getMiembros()) {
                        casilla.setValorCasa(1500000);
                        casilla.setValorHotel(1500000);
                        casilla.setValorPiscina(300000);
                        casilla.setValorPistaDeporte(600000);
                    }
                    break;
                case "Verde": case "Azul":
                    for (Solar casilla : grupo.getValue().getMiembros()) {
                        casilla.setValorCasa(2000000);
                        casilla.setValorHotel(2000000);
                        casilla.setValorPiscina(400000);
                        casilla.setValorPistaDeporte(800000);
                    }
                    break;
                default: break;
            }
        }
    }
}
