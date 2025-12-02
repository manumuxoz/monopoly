package cartas;

import monopoly.Casilla;
import partida.Jugador;

import java.util.ArrayList;

public abstract class Carta {
    private String tipo;

    public Carta() {}

    public Carta(String tipo) {
        this.tipo = tipo;
    }

    protected abstract void accion(Jugador actual, Casilla casilla, ArrayList<ArrayList<Casilla>> posiciones, ArrayList<Jugador> jugadores, int numero);
}
