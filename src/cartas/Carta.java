package cartas;

import casillas.Casilla;
import partida.Jugador;

import java.util.ArrayList;

public abstract class Carta {
    private Jugador duenho;
    private String tipo; //Tipo de carta
    private int num; //Número de carta

    public Carta() {}

    public Carta(String tipo, int num, Jugador duenho) {
        this.tipo = tipo;
        this.num = num;
        this.duenho = duenho;
    }

    public int getNum() {
        return num;
    }

    public abstract boolean accion(Jugador actual, Jugador banca, ArrayList<ArrayList<Casilla>> posiciones, ArrayList<Jugador> jugadores);
}
