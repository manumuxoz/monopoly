package excepciones;

import partida.Jugador;

public class ExcepcionBancarrota extends ExcepcionDinero {
    Jugador receptor;

    public Jugador getReceptor() {
        return receptor;
    }

    public ExcepcionBancarrota(String message) {
        super(message);
    }

    public ExcepcionBancarrota(String message, Jugador receptor) {
        super(message);
        this.receptor = receptor;
    }
}
