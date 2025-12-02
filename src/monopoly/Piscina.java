package monopoly;

import partida.Jugador;

import java.util.ArrayList;

public class Piscina extends Edificio{

    public Piscina(){
    }

    public Piscina(Jugador duenho, Solar solar, ArrayList<Edificio> edificiosCreados) {
        super(duenho, solar, "piscina", edificiosCreados);
    }
    @Override
    String imprimirCoste() {
        return String.valueOf(getSolar().getAlquilerPiscina());
    }
}
