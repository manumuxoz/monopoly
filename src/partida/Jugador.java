package partida;

import java.util.ArrayList;
import static monopoly.Valor.*;
import monopoly.*;

public class Jugador {

    //Atributos:
    private String nombre; //Nombre del jugador
    private Avatar avatar; //Avatar que tiene en la partida.
    private float fortuna; //Dinero que posee.
    private float gastos; //Gastos realizados a lo largo del juego.
    private boolean enCarcel; //Será true si el jugador está en la carcel
    private int tiradasCarcel; //Cuando está en la carcel, contará las tiradas sin éxito que ha hecho allí para intentar salir (se usa para limitar el numero de intentos).
    private int vueltas; //Cuenta las vueltas dadas al tablero.
    private ArrayList<Casilla> propiedades;//Propiedades que posee el jugador.

    //Constructor vacío. Se usará para crear la banca.
    public Jugador() {
        this.nombre = "banca";
        this.propiedades = new ArrayList<>();
    }

    /*Constructor principal. Requiere parámetros:
    * Nombre del jugador, tipo del avatar que tendrá, casilla en la que empezará y ArrayList de
    * avatares creados (usado para dos propósitos: evitar que dos jugadores tengan el mismo nombre y
    * que dos avatares tengan mismo ID). Desde este constructor también se crea el avatar.
     */
    public Jugador(String nombre, String tipoAvatar, Casilla inicio, ArrayList<Avatar> avCreados) {
        if(!existeJugador(avCreados, nombre, tipoAvatar)) { //Comprobamos si ya existe el nombre
            this.nombre = nombre;
            avatar = new Avatar(tipoAvatar, this, inicio, avCreados);
            fortuna = FORTUNA_INICIAL;
            gastos = 0;
            enCarcel = false;
            tiradasCarcel = 0;
            vueltas = 0;
            propiedades = new ArrayList<>();
        }
    }

    //Getters:
    public String getNombre() {
        return nombre;
    }
    public Avatar getAvatar() {
        return avatar;
    }
    public float getFortuna(){
        return fortuna;
    }
    public float getGastos(){
        return gastos;
    }
    public boolean getEnCarcel(){
        return enCarcel;
    }
    public int getTiradasCarcel(){
        return tiradasCarcel;
    }
    public int getVueltas(){
        return vueltas;
    }
    public ArrayList<Casilla> getPropiedades() {
        return propiedades;
    }

    //Setters:
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public void setAvatar(Avatar avatar) {
        this.avatar = avatar;
    }
    public void setFortuna(float fortuna) {
        this.fortuna = fortuna;
    }
    public void setGastos(float gastos) {
        this.gastos = gastos;
    }
    public void setenCarcel(boolean carcel) {
        this.enCarcel = carcel;
    }
    public void setTiradasCarcel(int tiradasCarcel) {
        this.tiradasCarcel = tiradasCarcel;
    }
    public void setVueltas(int vueltas) {
        this.vueltas = vueltas;
    }
    public void setPropiedades(ArrayList<Casilla> propiedades) {
        this.propiedades = propiedades;
    }

    //Otros métodos:
    //Método para añadir una propiedad al jugador. Como parámetro, la casilla a añadir.
    public void anhadirPropiedad(Casilla casilla) {
        if (!this.propiedades.contains(casilla))
            this.propiedades.add(casilla);
    }

    //Método para eliminar una propiedad del arraylist de propiedades de jugador.
    public void eliminarPropiedad(Casilla casilla) {
        if(propiedades.contains(casilla))
            propiedades.remove(casilla);
    }

    //Método para añadir fortuna a un jugador
    //Como parámetro se pide el valor a añadir. Si hay que restar fortuna, se pasaría un valor negativo.
    public void sumarFortuna(float valor) {
        this.fortuna += valor;
    }

    //Método para sumar gastos a un jugador.
    //Parámetro: valor a añadir a los gastos del jugador (será el precio de un solar, impuestos pagados...).
    public void sumarGastos(float valor) {
        this.gastos += valor;
    }

    /*Método para establecer al jugador en la cárcel. 
    * Se requiere disponer de las casillas del tablero para ello (por eso se pasan como parámetro).*/
    public void encarcelar(ArrayList<ArrayList<Casilla>> pos) {
        Casilla carcel = null;
        for (ArrayList<Casilla> lado : pos){
            for (Casilla casilla : lado){
                if (casilla.getNombre().equals("Carcel")){
                    carcel = casilla;
                    break;
                }
            }
            if (carcel != null) {
                Casilla casillaActual = avatar.getLugar();
                if(casillaActual!= null) {
                    casillaActual.eliminarAvatar(avatar);
                }
                avatar.setLugar(carcel);
                carcel.anhadirAvatar(avatar);
                enCarcel = true;
                tiradasCarcel = 0;
                System.out.println(nombre + " ha sido enviado a la carcel");
                break;
            }
        }
    }

    /*Método que comprueba si ya existe el nombre de un jugador. Parámetros:
     * - Un arraylist de los avatares ya creados.
     */
    private boolean existeJugador(ArrayList<Avatar> avCreados, String nombre, String  tipoAvatar) {
        for (Avatar av : avCreados) //Recorremos el arraylist de los avatares ya creados
            if (av.getJugador().getNombre().equals(nombre) || av.getJugador().getAvatar().getTipo().equals(tipoAvatar))
                return true;

        return false; //Comprobamos si se encuentra el nombre del jugador
    }
}
