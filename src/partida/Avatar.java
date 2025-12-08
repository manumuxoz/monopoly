package partida;

import casillas.Casilla;

import java.util.ArrayList;
import java.util.Objects;

public class Avatar {

    //Atributos
    private String id; //Identificador: una letra generada aleatoriamente.
    private String tipo; //Sombrero, Esfinge, Pelota, Coche
    private Jugador jugador; //Un jugador al que pertenece ese avatar.
    private Casilla lugar; //Los avatares se sitúan en casillas del tablero.

    //Constructor vacío
    public Avatar() {}

    /*Constructor principal. Requiere éstos parámetros:
    * Tipo del avatar, jugador al que pertenece, lugar en el que estará ubicado, y un arraylist con los
    * avatares creados (usado para crear un ID distinto del de los demás avatares).
     */
    public Avatar(String tipo, Jugador jugador, Casilla lugar, ArrayList<Avatar> avCreados) {
        generarId(avCreados);
        this.tipo = tipo;
        this.jugador = jugador;
        this.lugar = lugar;
        lugar.getAvatares().add(this); //Introducimos el avatar en la casilla
        avCreados.add(this);
    }

    //Setters:
    public void setLugar(Casilla lugar){
        this.lugar= lugar;
    }

    //Getters:
    public String getId(){
        return id;
    }
    public Jugador getJugador() {
        return jugador;
    }
    public Casilla getLugar(){
        return lugar;
    }
    public String getTipo() {
        return tipo;
    }

    //A continuación, tenemos otros métodos útiles para el desarrollo del juego.
    /*Método que permite mover a un avatar a una casilla concreta. Parámetros:
    * - Un array con las casillas del tablero. Se trata de un arrayList de arrayList de casillas (uno por lado).
    * - Un entero que indica el numero de casillas a moverse (será el valor sacado en la tirada de los dados).
    * EN ESTA VERSIÓN SUPONEMOS QUE valorTirada siemrpe es positivo.
     */
    public void moverAvatar(ArrayList<ArrayList<Casilla>> casillas, int valorTirada) {
        lugar.eliminarAvatar(this);

        float posicionActual = lugar.getPosicion();
        float nuevaPosicion = (posicionActual + valorTirada) % 40;
        Casilla nuevaCasilla = null;

        // Encontrar nueva casilla
        for (ArrayList<Casilla> lado : casillas) {
            for (Casilla casilla : lado) {
                if (casilla.getPosicion() == nuevaPosicion) {
                    nuevaCasilla = casilla;
                    break;
                }
            }
        }

        lugar = nuevaCasilla;
        nuevaCasilla.anhadirAvatar(this);

        if (nuevaPosicion < posicionActual && !getJugador().getEnCarcel()){
            getJugador().sumarFortuna(2000000);
            getJugador().sumarVueltas(1);
        }
    }

    /*Método que permite generar un ID para un avatar. Sólo lo usamos en esta clase (por ello es privado).
    * El ID generado será una letra mayúscula. Parámetros:
    * - Un arraylist de los avatares ya creados, con el objetivo de evitar que se generen dos ID iguales.
     */
    private void generarId(ArrayList<Avatar> avCreados) {
        String nuevoId;
        ArrayList<String> idCreados = new ArrayList<>(); //Declaramos un array para registrar los ids ya creados

        for (Avatar av : avCreados)
            idCreados.add(av.id);

        do {
            nuevoId = String.valueOf((char) (int) (Math.random() * (91 - 65) + 65)); //Mayúscula aleatoria
        } while (idCreados.contains(nuevoId)); //Damos mayúsculas hasta que no se encuentre el id

        id = nuevoId;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Avatar avatar = (Avatar) o;
        return Objects.equals(id, avatar.id);
    }
}
