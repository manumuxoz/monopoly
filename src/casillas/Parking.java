package casillas;
import partida.*;

public final class Parking extends Accion {
    public Parking() {}

    public Parking(int posicion, Jugador duenho) {
        super("Parking", posicion, duenho);
    }

    @Override
    public boolean evaluarCasilla(Jugador actual, Jugador banca, int tirada) {
        float impuesto = getImpuesto();
        if (impuesto > 0) {
            actual.sumarFortuna(impuesto);
            actual.sumarPremios(impuesto);
            System.out.println("El jugador " + actual.getNombre() + " recibe por caer en el parking " + (int)impuesto + "€ del bote acumulado de impuestos.");
            setImpuesto(0);
        }
        return true;
    }
}
