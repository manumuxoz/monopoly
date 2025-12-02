package monopoly;

import partida.Jugador;

import java.util.ArrayList;

public class Casa extends Edificio{
    public Casa(){
    }

    public Casa(Jugador duenho, Solar solar, ArrayList<Edificio> edificiosCreados) {
        super(duenho, solar, "casa", edificiosCreados);
    }

    @Override
    String imprimirCoste() {
        return String.valueOf(getSolar().getAlquilerCasa());
    }
}
