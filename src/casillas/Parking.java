package casillas;

import partida.*;
import static monopoly.Juego.consola;

public final class Parking extends Accion {
    private float bote;

    public Parking() {}

    public Parking(int posicion, Jugador duenho) {
        super("Parking", posicion, duenho);
    }

    private String jugadoresEnCasilla(){
        StringBuilder sb = new StringBuilder().append("[");
        String separador = "\0";

        for (Avatar av : getAvatares()){
            sb.append(separador).append(av.getJugador().getNombre());
            separador = ", ";
        }

        return sb.append("]").toString();
    }

    public void sumarBote(float bote) {
        this.bote += bote;
    }

    @Override
    public boolean evaluarCasilla(Jugador actual, Jugador banca, int tirada) {
        sumarFrecuenciaVisita();

        actual.sumarFortuna(bote);
        actual.sumarPremios(bote);

        if (bote > 0)
            consola.imprimir("El jugador " + actual.getNombre() + " recibe por caer en el Parking " + (int)bote + "$ del bote acumulado de impuestos.");

        bote = 0;

        return true;
    }
    @Override
    public String infoCasilla(){
        return "{\n\tbote: " + getImpuesto() +
                "\n\tjugadores: " + jugadoresEnCasilla();
    }

}
