package excepciones;

import partida.Jugador;

public class ExcepcionBancarrota extends ExcepcionDinero {
    public ExcepcionBancarrota(String message) {
        super(message);
    }
}
