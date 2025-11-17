package monopoly;

import partida.*;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;

public class Acciones {
    private String tipo; //Suerte o Caja

    //Constructor vacío
    public Acciones(){}

    public Acciones(String tipo){
        this.tipo = tipo;
    }

    //Acción Suerte 1/Caja 6:
    public void avanzaSolar(Jugador jugadorActual, Casilla Solar) {
        if (tipo.equals("Suerte"))
            System.out.println("Decides hacer un viaje de placer. Avanza hasta Solar19. Si pasas por la casilla de Salida, cobra 2.000.000€");
        else
            System.out.println("Ve a Solar20 para disfrutar del San Fermín. Si pasas por la casilla de Salida, cobra 2.000.000€.");


        Avatar avatarActual = jugadorActual.getAvatar();

        if (avatarActual.getLugar().getPosicion() > Solar.getPosicion()) {
            System.out.println("\nEl jugador " + jugadorActual.getNombre() + " pasa por la casilla de salida y recibe 2000000$.\n");
            jugadorActual.setVueltas(jugadorActual.getVueltas() + 1);
            jugadorActual.sumarFortuna(2000000);
        }
        // Mostrar movimiento básico
        System.out.println("El avatar " + avatarActual.getId() + " avanza desde " + avatarActual.getLugar().getNombre() + " hasta " + Solar.getNombre() + ".");
        // Mover avatar
        avatarActual.getLugar().eliminarAvatar(avatarActual);
        avatarActual.setLugar(Solar);
        Solar.anhadirAvatar(avatarActual);
    }

    //Acción Suerte/Caja 2:
    public void veCarcel(Jugador jugadorActual, ArrayList<ArrayList<Casilla>> posiciones) {

        if (tipo.equals("Suerte"))
            System.out.println("Los acreedores te persiguen por impago. Ve a la Cárcel. Ve directamente sin pasar por la casilla de Salida y sin cobrar los 2.000.000€.");
        else
            System.out.println("Te investigan por fraude de identidad. Ve a la Cárcel. Ve directamente sin pasar por la casilla de Salida y sin cobrar los 2.000.000€.");

        jugadorActual.encarcelar(posiciones);
    }

    //Acción Suerte 3:
    public void boteLoteria(Jugador jugadorActual) {
        System.out.println("¡Has ganado el bote de la lotería! Recibe 1.000.000€.");

        jugadorActual.sumarFortuna(1000000);
        jugadorActual.sumarPremios(1000000);
    }

    //Accion Suerte 4:
    public void elegidoPresidente(Jugador jugadorActual, ArrayList<Jugador> jugadores) {
        for (Jugador jugador : jugadores)
            if (!jugador.equals(jugadorActual)) {
                jugadorActual.sumarGastos(250000);
                jugadorActual.sumarGastos(-250000);
                jugador.sumarFortuna(250000);
                System.out.println(jugador.getNombre() + " ha recibido 250.000€ de " + jugadorActual.getNombre());
                jugador.sumarPremios(250000);
            }
    }

    //Acción 5 Suerte:
    public void retrocedeTres(Jugador jugadorActual, ArrayList<ArrayList<Casilla>> posiciones) {
        System.out.println("¡Hora punta de tráfico! Retrocede tres casillas.");

        Avatar avatarActual = jugadorActual.getAvatar();

        avatarActual.moverAvatar(posiciones, -3);
    }

    //Acción 6 Suerte:
    public void multa(Jugador jugadorActual) {
        jugadorActual.sumarGastos(150000);
        jugadorActual.sumarFortuna(-150000);
    }

    //Acción 7 Suerte:
    public void avanzaTransporte(Jugador jugadorActual, ArrayList<ArrayList<Casilla>> posiciones) {
        System.out.println("Avanza hasta la casilla de transporte más cercana. Si no tiene dueño, puedes comprarla. Si tiene dueño, paga al dueño el doble de la operación indicada.");

        Avatar avatarActual = jugadorActual.getAvatar();

        int posicionInicial = (int) avatarActual.getLugar().getPosicion();
        Casilla transporteCercana = posiciones.get(posicionInicial/10).get(posicionInicial%10);
        for (int i = posicionInicial + 1; transporteCercana.getTipo().equals("Transporte"); i = (i + 1)%40) {
            transporteCercana = posiciones.get(i / 10).get(i % 10);
            if (transporteCercana.getTipo().equals("Transporte"))
                avatarActual.setLugar(transporteCercana);
        }
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
    public void retrocedeSolar1(Jugador jugadorActual, Casilla Solar1) {
        System.out.println("Retrocede hasta Solar1 para comprar antigüedades exóticas.");

        Avatar avatarActual = jugadorActual.getAvatar();

        // Mover avatar
        avatarActual.getLugar().eliminarAvatar(avatarActual);
        avatarActual.setLugar(Solar1);
        Solar1.anhadirAvatar(avatarActual);
    }

}
