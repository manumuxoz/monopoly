package casillas;

import partida.Jugador;
import static monopoly.Juego.consola;

public final class Servicios extends Propiedad {
    public Servicios() {}

    public Servicios(String nombre, int posicion, Jugador duenho) {
        super(nombre, "Servicios", posicion, 500000, duenho, 50000);
    }

    @Override
    public boolean evaluarCasilla(Jugador actual, Jugador banca, int tirada) {
        Jugador duenho = getDuenho();

        sumarFrecuenciaVisita();
        if (!perteneceAJugador(banca) && !perteneceAJugador(actual)) {
            float alquiler = alquiler() * tirada;
            if (!actual.enBancarrota(alquiler, duenho) && actual.getFortuna() >= alquiler) {
                actual.sumarGastos(alquiler);
                actual.sumarFortuna(-alquiler);
                actual.sumarPagoAlquileres(alquiler);
                sumarAlquilerAcumulado(alquiler);
                duenho.sumarFortuna(alquiler);
                duenho.sumarCobroAlquileres(alquiler);
                consola.imprimir("Se han pagado " + alquiler + "€ de servicio a " + duenho.getNombre() + ".");
                return true;

            } else if (!actual.getEnBancarrota() && actual.getFortuna() < alquiler)
                actual.setDeudaAPagar(alquiler);
        }
        return false;
    }

    @Override
    public String infoCasilla() {
        return "{" +
                "\n\ttipo: " + getTipo() +
                ",\n\tvalor: " + getValor() +
                ",\n\talquiler: " + getImpuesto() +
                "\n}";
    }

    @Override
    public String casEnVenta(){
        return "{" +
                "\n\tnombre: " + getNombre() +
                ",\n\ttipo: " + getTipo() +
                ",\n\tvalor: " + getValor() +
                "\n}";
    }

    @Override
    public float alquiler(){
        float alquiler = 4 * getImpuesto();

        if(getDuenho().contarCasillasServicio() == 2)
            alquiler = 10 * getImpuesto();

        return alquiler;
    }

    @Override
    public float valor(){
        return getValor();
    }
}
