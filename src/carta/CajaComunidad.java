package carta;

import casilla.Casilla;
import casilla.Solar;
import partida.Avatar;
import partida.Jugador;

import java.util.ArrayList;

public class CajaComunidad extends Carta{
    public CajaComunidad() {
        super("caja");
    }

    //Caja 6:
    public void avanzaSolar(Jugador jugadorActual, Solar solar) {

        System.out.println("Ve a Solar20 para disfrutar del San Fermín. Si pasas por la casilla de Salida, cobra 2.000.000€.");


        Avatar avatarActual = jugadorActual.getAvatar();

        if (avatarActual.getLugar().getPosicion() >  solar.getPosicion()) {
            System.out.println("\nEl jugador " + jugadorActual.getNombre() + " pasa por la casilla de salida y recibe 2000000$.\n");
            jugadorActual.setVueltas(jugadorActual.getVueltas() + 1);
            jugadorActual.sumarFortuna(2000000);
        }
        // Mostrar movimiento básico
        System.out.println("El avatar " + avatarActual.getId() + " avanza desde " + avatarActual.getLugar().getNombre() + " hasta " + solar.getNombre() + ".");
        // Mover avatar
        avatarActual.getLugar().eliminarAvatar(avatarActual);
        avatarActual.setLugar(solar);
        solar.anhadirAvatar(avatarActual);
    }

    //Caja 2:
    public void veCarcel(Jugador jugadorActual, ArrayList<ArrayList<Casilla>> posiciones) {

        System.out.println("Te investigan por fraude de identidad. Ve a la Cárcel. Ve directamente sin pasar por la casilla de Salida y sin cobrar los 2.000.000€.");

        jugadorActual.encarcelar(posiciones);
    }

    //Acción 1 Caja:
    public void balneario(Jugador jugadorActual) {
        jugadorActual.sumarGastos(500000);
        jugadorActual.sumarFortuna(-500000);
    }

    //Acción 3 Caja:
    public void colocateSalida(Jugador jugadorActual, Casilla Salida) {
        System.out.println("Colócate en la casilla de Salida. Cobra 2.000.000€.");

        Avatar avatarActual = jugadorActual.getAvatar();

        // Mover avatar
        avatarActual.getLugar().eliminarAvatar(avatarActual);
        avatarActual.setLugar(Salida);
        jugadorActual.sumarVueltas(1);
        Salida.anhadirAvatar(avatarActual);
        jugadorActual.sumarFortuna(2000000);
    }

    //Acción 4 Caja:
    public void devolucionHacienda(Jugador jugadorActual) {
        System.out.println("Devolución de Hacienda. Cobra 500.000€.");

        jugadorActual.sumarFortuna(500000);
        jugadorActual.sumarPremios(500000);
    }

    //Acción 5 Caja:
    public void retrocedeSolar1(Jugador jugadorActual, Solar Solar1) {
        System.out.println("Retrocede hasta Solar1 para comprar antigüedades exóticas.");

        Avatar avatarActual = jugadorActual.getAvatar();

        // Mover avatar
        avatarActual.getLugar().eliminarAvatar(avatarActual);
        avatarActual.setLugar(Solar1);
        Solar1.anhadirAvatar(avatarActual);
    }


    @Override
    public void accion(Jugador actual, Casilla casilla, ArrayList<ArrayList<Casilla>> posiciones, ArrayList<Jugador> jugadores, int numero) {
        switch (numero) {
            case 0: balneario(actual);
            case 1: veCarcel(actual, posiciones);
            case 2: colocateSalida(actual, casilla);
            case 3: devolucionHacienda(actual);
            case 4: retrocedeSolar1(actual, (Solar) casilla);
            case 5: avanzaSolar(actual, (Solar) casilla);
        }
    }
}
