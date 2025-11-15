package monopoly;

import partida.*;

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
            jugadorActual.setVueltas(jugadorActual.getVueltas() + 1);
            jugadorActual.sumarFortuna(2000000);
            avatarActual.setLugar(Solar);
        } else
            avatarActual.setLugar(Solar);
    }

    //Acción Suerte/Caja 2:
    public void veCarcel(Jugador jugadorActual, Casilla Carcel) {

        if (tipo.equals("Suerte"))
            System.out.println("Los acreedores te persiguen por impago. Ve a la Cárcel. Ve directamente sin pasar por la casilla de Salida y sin cobrar los 2.000.000€.");
        else
            System.out.println("Te investigan por fraude de identidad. Ve a la Cárcel. Ve directamente sin pasar por la casilla de Salida y sin cobrar los 2.000.000€.");

        Avatar avatarActual = jugadorActual.getAvatar();

        avatarActual.setLugar(Carcel);
        jugadorActual.setenCarcel(true);
        jugadorActual.setTiradasCarcel(0);
        jugadorActual.sumarVecesCarcel(1);
    }

    //Acción Suerte 3:
    public void boteLoteria(Jugador jugadorActual) {
        System.out.println("¡Has ganado el bote de la lotería! Recibe 1.000.000€.");

        jugadorActual.sumarFortuna(1000000);
    }

    //Accion Suerte 4:
    public void elegidoPresidente(Jugador jugadorActual, ArrayList<Jugador> jugadores) {
        System.out.println("Has sido elegido presidente de la junta directiva. Paga a cada jugador 250.000€.");


        while (jugadorActual.getFortuna() < 250000 * jugadores.size()) {
            System.out.println(jugadorActual.getNombre() + " no dispone de suficiente dinero para realizar el pago. " +
                    "Debe vender edificios o hipotecar propiedades.");

            Casilla casillaVenta =  null;
            do {
                System.out.print("Seleccione una de sus propiedades para continuar con la venta: ");
                Scanner sc = new Scanner(System.in);
                String nombre = sc.nextLine();

                for (Casilla casilla : jugadorActual.getPropiedades()) {
                    if (casilla.getNombre().equals(nombre)) {
                        casillaVenta = casilla;
                        break;
                    }
                }
            } while(casillaVenta ==  null);

            //Sin acabar

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
        System.out.println("Te multan por usar el móvil mientras conduces. Paga 150.000€.");
    }

    //Acción 7 Suerte:
    public void avanzaTransporte(Jugador jugadorActual, ArrayList<ArrayList<Casilla>> posiciones) {
        System.out.println("Avanza hasta la casilla de transporte más cercana. Si no tiene dueño, puedes comprarla. Si tiene dueño, paga al dueño el doble de la operación indicada.");

        Avatar avatarActual = jugadorActual.getAvatar();
    }

    //Acción 1 Caja:
    public void balneario(Jugador jugadorActual) {
        System.out.println("Paga 500.000€ por un fin de semana en un balneario de 5 estrellas.");
    }

    //Acción 3 Caja:
    public void colocateSalida(Jugador jugadorActual, Casilla Salida) {
        System.out.println("Colócate en la casilla de Salida. Cobra 2.000.000€.");

        jugadorActual.getAvatar().setLugar(Salida);
    }

    //Acción 4 Caja:
    public void devolucionHacienda(Jugador jugadorActual) {
        System.out.println("Devolución de Hacienda. Cobra 500.000€.");

        jugadorActual.sumarFortuna(500000);
    }

    //Acción 5 Caja:
    public void retrocedeSolar1(Jugador jugadorActual, Casilla Solar1) {
        System.out.println("Retrocede hasta Solar1 para comprar antigüedades exóticas.");

        jugadorActual.getAvatar().setLugar(Solar1);
    }

}
