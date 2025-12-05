package casillas;
import partida.*;

public final class Parking extends Accion {
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
    @Override
    public String infoCasilla(){
        return "{\n\tbote: " + getImpuesto() +
                "\n\tjugadores: " + jugadoresEnCasilla();
    }

}
