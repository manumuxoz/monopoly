package casillas;

import partida.Jugador;
import static monopoly.Juego.consola;

public final class Transporte extends Propiedad {

    public Transporte() {}

    public Transporte(String nombre, int posicion, Jugador duenho) {
        super(nombre, "Transporte", posicion, 500000, duenho, 250000);
    }


    @Override
    public boolean evaluarCasilla(Jugador actual, Jugador banca, int tirada) {
        Jugador duenho = getDuenho();
        float alquiler = alquiler();

        sumarFrecuenciaVisita();
        if (!perteneceAJugador(banca) && !perteneceAJugador(actual)) {
            if (!actual.enBancarrota(alquiler, duenho) && actual.getFortuna() >= alquiler) {
                actual.sumarGastos(alquiler);
                actual.sumarFortuna(-alquiler);
                actual.sumarPagoAlquileres(alquiler);
                sumarAlquilerAcumulado(alquiler);

                duenho.sumarFortuna(alquiler);
                duenho.sumarCobroAlquileres(alquiler);
                consola.imprimir("El jugador" + actual.getNombre() + " paga " +  (int)alquiler + "€ de transporte a " + duenho.getNombre() + ".");
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
                "\n\ttipo: " + getTipo() +
                ",\n\tvalor: " + getValor() +
                "\n}";
    }
    @Override
    public float alquiler(){
        return getDuenho().contarCasillasTransporte() * getImpuesto();
    }

    @Override
    public float valor(){
        return getValor();
    }
}