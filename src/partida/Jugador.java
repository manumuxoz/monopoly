package partida;

import java.util.ArrayList;
import java.util.Objects;
import casilla.*;
import static monopoly.Valor.*;

import casilla.Casilla;

public class Jugador {

    //Atributos:
    private String nombre; //Nombre del jugador
    private Avatar avatar; //Avatar que tiene en la partida.
    private float fortuna; //Dinero que posee.
    private float gastos; //Gastos realizados a lo largo del juego.
    private boolean enCarcel; //Será true si el jugador está en la carcel
    private int tiradasCarcel; //Cuando está en la carcel, contará las tiradas sin éxito que ha hecho allí para intentar salir (se usa para limitar el numero de intentos).
    private int vueltas; //Cuenta las vueltas dadas al tablero.
    private ArrayList<Propiedad> propiedades;//Propiedades que posee el jugador.
    private ArrayList<Solar> hipotecas;
    private float tasasImpuestos;
    private float pagoAlquileres;
    private float cobroAlquileres;
    private float premios;
    private int vecesCarcel;
    private float patrimonio;
    private boolean enBancarrota;
    private float deudaAPagar;

    //Constructor vacío. Se usará para crear la banca.
    public Jugador() {
        this.nombre = "Banca";
        fortuna = FORTUNA_BANCA;
        this.propiedades = new ArrayList<>();
    }

    /*Constructor principal. Requiere parámetros:
    * Nombre del jugador, tipo del avatar que tendrá, casilla en la que empezará y ArrayList de
    * avatares creados (usado para dos propósitos: evitar que dos jugadores tengan el mismo nombre y
    * que dos avatares tengan mismo ID). Desde este constructor también se crea el avatar.
     */
    public Jugador(String nombre, String tipoAvatar, Casilla inicio, ArrayList<Avatar> avCreados) {
        if(!existeJugador(avCreados, nombre)) { //Comprobamos si ya existe el nombre
            this.nombre = nombre;
            avatar = new Avatar(tipoAvatar, this, inicio, avCreados);
            fortuna = FORTUNA_INICIAL;
            enCarcel = false;
            propiedades = new ArrayList<>();
            hipotecas = new ArrayList<>();
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
    public ArrayList<Propiedad> getPropiedades() {
        return propiedades;
    }
    public ArrayList<Solar> getHipotecas() {
        return hipotecas;
    }
    public float getPagoAlquileres() {return pagoAlquileres;}
    public float getCobroAlquileres() {return cobroAlquileres;}
    public float getTasasImpuestos(){return tasasImpuestos;}
    public float getPremios() {return premios;}
    public int getVueltas() {return vueltas;}
    public int getVecesCarcel(){return vecesCarcel;}
    public float getPatrimonio(){return patrimonio;}
    public boolean getEnBancarrota(){return enBancarrota;}
    public float getDeudaAPagar() {return deudaAPagar;}

    //Setters:
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public void setFortuna(float fortuna) {
        this.fortuna = fortuna;
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
    public void setDeudaAPagar(float deudaAPagar) {
        this.deudaAPagar = deudaAPagar;
    }

    //Otros métodos:

    //Método para añadir una propiedad al jugador. Como parámetro, la casilla a añadir.
    public void anhadirPropiedad(Propiedad propiedad) {
        if (!this.propiedades.contains(propiedad))
            this.propiedades.add(propiedad);
    }

    //Método para eliminar una propiedad del arraylist de propiedades de jugador.
    public void eliminarPropiedad(Propiedad propiedad) {
        propiedades.remove(propiedad);
    }

    //Método para añadir fortuna a un jugador
    //Como parámetro se pide el valor a añadir. Si hay que restar fortuna, se pasaría un valor negativo.
    public void sumarFortuna(float valor) {
        fortuna += valor;
    }

    //Método para sumar gastos a un jugador.
    //Parámetro: valor a añadir a los gastos del jugador (será el precio de un solar, impuestos pagados...).
    public void sumarGastos(float valor) {
        gastos += valor;
    }

    public void sumarVueltas(int valor) {vueltas += valor;}

    public void sumarTasasImpuestos(float valor) {tasasImpuestos += valor;}

    public void sumarPagoAlquileres(float valor) {pagoAlquileres += valor;}

    public void sumarCobroAlquileres(float valor) {cobroAlquileres += valor;}

    public void sumarPremios(float valor) {premios += valor;}

    public void sumarVecesCarcel(int valor) {vecesCarcel += valor;}

    public void sumarPatrimonio(float valor) {patrimonio += valor;}

    /*Método para establecer al jugador en la cárcel. 
    * Se requiere disponer de las casillas del tablero para ello (por eso se pasan como parámetro).*/
    public void encarcelar(ArrayList<ArrayList<Casilla>> pos) {
        Casilla carcel = pos.get(1).getFirst(); //La carcel el la primera casilla del lado este

        Casilla casillaActual = avatar.getLugar();
        casillaActual.eliminarAvatar(avatar);
        avatar.setLugar(carcel);
        carcel.anhadirAvatar(avatar);
        enCarcel = true;
        tiradasCarcel = 0;
        System.out.println(nombre + " ha sido enviado a la carcel.");
        sumarVecesCarcel(1);
    }

    //Método para saber si ya existe un jugador
    private boolean existeJugador(ArrayList<Avatar> avCreados, String nombre) {
        for (Avatar av : avCreados) //Recorremos el arraylist de los avatares ya creados
            if (av.getJugador().getNombre().equals(nombre))
                return true;

        return false; //Comprobamos si se encuentra el nombre del jugador
    }

    //Método para saber si un jugador está en bancarrota
    public boolean enBancarrota(float cobro, Jugador destinatario) {
        if (dineroVentas() + fortuna < cobro) {
            System.out.println(nombre + " está en bancarrota.");
            enBancarrota = true;

            realizarBancarrota(destinatario); //Realizamos la bancarrota

            return true;
        }
        return false;
    }

    //Método para calcular el dinero de posibles ventas:
    private float dineroVentas() {
        float ventaEdificios = 0;
        float hipotecaPropiedades = 0;

        for (Propiedad propiedad : propiedades) {
            if (propiedad.getTipo().equals("Solar")) {
                hipotecaPropiedades += ((Solar) propiedad).getHipoteca();
                ventaEdificios += (((Solar) propiedad).getNumCasas() * ((Solar) propiedad).getValorCasa());

                if (((Solar) propiedad).getHotel()) ventaEdificios += ((Solar) propiedad).getValorHotel();
                if (((Solar) propiedad).getPiscina()) ventaEdificios += ((Solar) propiedad).getValorPiscina();
                if (((Solar) propiedad).getPistaDeporte()) ventaEdificios += ((Solar) propiedad).getValorPistaDeporte();
            }
        }

        return ventaEdificios + hipotecaPropiedades;
    }

    //Método para realizar la bancarrota (ceder propiedades):
    private void
    realizarBancarrota(Jugador destinatario) {
        if (!destinatario.getNombre().equals("Banca")) {//Si no es banca cedemos todos al destinatario
            destinatario.sumarFortuna(fortuna);
            fortuna = 0;
            for (Casilla casilla : propiedades)
                casilla.setDuenho(destinatario); //Cambiamos dueño
        } else { //Si el destinatario es banca
            destinatario.sumarFortuna(fortuna);
            fortuna = 0;
            for (Casilla casilla : propiedades) {
                casilla.setDuenho(destinatario);
                if (casilla.getTipo().equals("Solar")) //Si es solar eliminamos los edificios
                    ((Solar) casilla).getEdificios().clear();

            }
        }
    }

    public int contarCasillasTransporte(){
        int i=0;
        for (Casilla casilla : propiedades){
            if (casilla.getTipo().equals("Transporte")){
                i++;
            }
        }
        return i;
    }

    public int contarCasillasServicio(){
        int i=0;
        for (Casilla casilla : propiedades){
            if (casilla.getTipo().equals("Servicio")){
                i++;
            }
        }
        return i;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Jugador jugador = (Jugador) o;
        return Objects.equals(nombre, jugador.nombre);
    }
}
