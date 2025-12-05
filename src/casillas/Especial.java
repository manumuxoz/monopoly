package casillas;

import partida.Avatar;
import partida.Jugador;

public final class Especial extends Casilla {
    public Especial() {
    }

    public Especial(String nombre, int posicion, Jugador duenho) {
        super(nombre, "Especiales", posicion, duenho);
    }

    //Método que devuelve la descripción de las casillas 'Especiales'
    public String imprimirEspeciales() { //Ajustar porque PArking es hija de ACCION
        StringBuilder sb = new StringBuilder().append("[");
        String separador = "";
        for (Avatar av : getAvatares()) {
            if (av.getJugador().getEnCarcel()) {
                sb.append(separador).append(av.getJugador().getNombre()).append(", ").append(av.getJugador().getTiradasCarcel());
                separador = "], [";
            }
        }
        return "{\n\tsalir: 500000,\n\tjugadores: " +  sb + "]\n}";
    }


    @Override
    public String infoCasilla() {
        return imprimirEspeciales();
    }

    @Override
    public boolean evaluarCasilla(Jugador jugador, Jugador banca, int tirada){
        return true;
    }
}
