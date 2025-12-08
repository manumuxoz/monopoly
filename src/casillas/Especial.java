package casillas;

import partida.Avatar;
import partida.Jugador;

public final class Especial extends Casilla {
    public Especial() {
    }

    public Especial(String nombre, int posicion, Jugador duenho) {
        super(nombre, "Especiales", posicion, duenho);
    }

    @Override
    public String infoCasilla() {
        if (getNombre().equals("Carcel")){
            StringBuilder sb = new StringBuilder().append("[");
            String separador = "";
            for (Avatar av : getAvatares()) {
                if (av.getJugador().getEnCarcel()) {
                    sb.append(separador).append(av.getJugador().getNombre()).append(", ").append(av.getJugador().getTiradasCarcel());
                    separador = "], [";
                }
            }
            return "{\n\tsalir: 500000,\n\tjugadores: " + sb + "]\n}";
        }
        return "";
    }

    @Override
    public boolean evaluarCasilla(Jugador jugador, Jugador banca, int tirada) {
        sumarFrecuenciaVisita();
        return true;
    }
}
