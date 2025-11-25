package monopoly;

import partida.*;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;

import static monopoly.Valor.*;


public class Casilla {

    //Atributos:
    private String nombre; //Nombre de la casilla
    private String tipo; //Tipo de casilla (Solar, Especial, Transporte, Servicios, Comunidad, Suerte y Impuesto).
    private float valor; //Valor de esa casilla (en la mayoría será valor de compra, en la casilla parking se usará como el bote).
    private int posicion; //Posición que ocupa la casilla en el tablero (entero entre 1 y 40).
    private Jugador duenho; //Dueño de la casilla (por defecto sería la banca).
    private Grupo grupo; //Grupo al que pertenece la casilla (si es solar).
    private float impuesto; //Cantidad a pagar por caer en la casilla: el alquiler en solares/servicios/transportes o impuestos.
    private ArrayList<Avatar> avatares; //Avatares que están situados en la casilla.
    private int vecesEnCasilla;

    //Constructores:
    public Casilla() {}//Parámetros vacíos

    /*Constructur utilizado para inicializar las casillas de tipo PROPIEDAD.
     * Parámetros: nombre, tipo, posicion, valor, duenho, alquiler
     */
    public Casilla(String nombre, String tipo, int posicion, float valor, Jugador duenho, float alquiler) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.posicion = posicion;
        this.valor = valor;
        this.duenho = duenho;
        this.impuesto = alquiler;
        avatares = new ArrayList<>();
    }

    /*Constructor utilizado para inicializar las casillas de tipo IMPUESTOS.
     * Parámetros: nombre, posición en el tablero, impuesto establecido y dueño.
     */
    public Casilla(String nombre, int posicion, float impuesto, Jugador duenho) {
        this.nombre = nombre;
        this.tipo = "Impuesto";
        this.posicion = posicion;
        this.duenho = duenho;
        this.impuesto = impuesto;
        avatares = new ArrayList<>();
    }

    /*Constructor utilizado para crear ESPECIAL y ACCION:
     * Parámetros: nombre, tipo de la casilla (será uno de los que queda), posición en el tablero y dueño.
     */
    public Casilla(String nombre, String tipo, int posicion, Jugador duenho) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.posicion = posicion;
        this.duenho = duenho;
        avatares = new ArrayList<>();
    }

    //Getters:
    public String getNombre() {
        return nombre;
    }
    public String getTipo() {
        return tipo;
    }
    public float getValor() {
        return valor;
    }
    public float getPosicion() {
        return posicion;
    }
    public Jugador getDuenho() {
        return duenho;
    }
    public Grupo getGrupo() {
        return grupo;
    }
    public float getImpuesto() {
        return impuesto;
    }
    public ArrayList<Avatar> getAvatares() {
        return avatares;
    }
    public int getVecesEnCasilla(){return vecesEnCasilla;}

    //Setters:
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public void setDuenho(Jugador duenho) {
        this.duenho = duenho;
    }
    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
    }
    public void setImpuesto(float impuesto) {this.impuesto = impuesto;}
    public void setAvatares(ArrayList<Avatar> avatares) {
        this.avatares = avatares;
    }
    public void setTipo(String tipo) {this.tipo = tipo;}
    public void setPosicion(int posicion){this.posicion = posicion;}
    public void setValor(float valor) {this.valor = valor;}

    //Método utilizado para añadir un avatar al array de avatares en casilla.
    public void anhadirAvatar(Avatar av) {
        if (!avatares.contains(av))
            this.avatares.add(av);
    }

    //Método utilizado para eliminar un avatar del array de avatares en casilla.
    public void eliminarAvatar(Avatar av) {
        avatares.remove(av);
    }

    /*Método para evaluar qué hacer en una casilla concreta. Parámetros:
     * - Jugador cuyo avatar está en esa casilla.
     * - La banca (para ciertas comprobaciones).
     * - El valor de la tirada: para determinar impuesto a pagar en casillas de servicios.
     * Valor devuelto: true en caso de ser solvente (es decir, de cumplir las deudas), y false
     * en caso de no cumplirlas.*/
    public boolean evaluarCasilla(Jugador actual, Jugador banca, int tirada) {
        switch (tipo) {
            case "Solar":
                sumarVecesEnCasilla(1);
                if (!duenho.equals(banca) && !duenho.equals(actual) && !hipotecado) {
                    float alquiler = impuesto;

                    if (this.getGrupo().esDuenhoGrupo(duenho) && edificios.isEmpty()) //Si el duenho tiene el grupo y no tiene casas, paga el doble del alquiler inicila
                        alquiler = 2 * impuesto;

                    if (!actual.enBancarrota(alquiler, duenho) && actual.getFortuna() >= alquiler){
                        actual.sumarGastos(alquiler);
                        actual.sumarFortuna(-alquiler);
                        actual.sumarPagoAlquileres(alquiler);
                        sumarAlquilerAcumulado(alquiler);
                        duenho.sumarFortuna(alquiler);
                        duenho.sumarCobroAlquileres(alquiler);
                        System.out.println("Se han pagado " + alquiler + "€ de alquiler a " + duenho.getNombre() + ".");
                        return true;
                    } else if (!actual.getEnBancarrota() && actual.getFortuna() < alquiler)
                        actual.setDeudaAPagar(alquiler);

                    return false;
                }
                break;
            case "Servicios":
                sumarVecesEnCasilla(1);
                if (!duenho.equals(banca) && !duenho.equals(actual) && !hipotecado) {
                    int numCasillasServicio = duenho.contarCasillasServicio();
                    int valorImpuesto = tirada * 4 * 50000;
                    if(numCasillasServicio == 2) valorImpuesto = tirada * 10 * 50000;
                    if (!actual.enBancarrota(impuesto, duenho) && actual.getFortuna() >= impuesto) {
                        actual.sumarGastos(valorImpuesto);
                        actual.sumarFortuna(-valorImpuesto);
                        actual.sumarPagoAlquileres(valorImpuesto);
                        sumarAlquilerAcumulado(valorImpuesto);
                        Jugador propietario = duenho;
                        propietario.sumarFortuna(valorImpuesto);
                        propietario.sumarCobroAlquileres(valorImpuesto);
                        System.out.println("Se han pagado " + valorImpuesto + "€ de servicio a " + propietario.getNombre() + ".");
                        return true;
                    } else if (!actual.getEnBancarrota() && actual.getFortuna() < impuesto)
                        actual.setDeudaAPagar(impuesto);

                    return false;
                }
                break;
            case "Transporte":
                sumarVecesEnCasilla(1);
                if (!duenho.equals(banca) && !duenho.equals(actual) && !hipotecado) {
                    int numCasillasTransporte = actual.contarCasillasTransporte();
                    if (!actual.enBancarrota(impuesto, duenho) && actual.getFortuna() >= impuesto * numCasillasTransporte) {
                        actual.sumarGastos(impuesto * numCasillasTransporte);
                        actual.sumarFortuna(-impuesto * numCasillasTransporte);
                        actual.sumarPagoAlquileres(impuesto * numCasillasTransporte);
                        sumarAlquilerAcumulado(impuesto * numCasillasTransporte);
                        Jugador propietario = duenho;
                        propietario.sumarFortuna(impuesto*numCasillasTransporte);
                        propietario.sumarCobroAlquileres(impuesto*numCasillasTransporte);
                        System.out.println("El jugador" + actual.getNombre() + " paga " +  (int)impuesto*numCasillasTransporte + "€ de transporte a " + propietario.getNombre() + ".");
                        return true;
                    } else if (!actual.getEnBancarrota() && actual.getFortuna() < impuesto * numCasillasTransporte) actual.setDeudaAPagar(impuesto);

                    return false;
                }
                break;
            case "Impuesto":
                sumarVecesEnCasilla(1);
                if (!actual.enBancarrota(impuesto, duenho) && actual.getFortuna() >= impuesto) {
                    actual.sumarGastos(impuesto);
                    actual.sumarFortuna(-impuesto);
                    actual.sumarTasasImpuestos(impuesto);

                    // Añadir al bote del Parking
                    System.out.println("El jugador " + actual.getNombre() + " paga " + (int)impuesto + "€ que se depositan en el Parking.");
                    return true;
                } else if (!actual.getEnBancarrota() && actual.getFortuna() < impuesto)
                    actual.setDeudaAPagar(impuesto);

                return false;
            case "Especiales":
                sumarVecesEnCasilla(1);
                if (this.nombre.equals("Parking")) {
                    if (impuesto > 0) {
                        actual.sumarFortuna(impuesto);
                        actual.sumarPremios(impuesto);
                        System.out.println("El jugador " + actual.getNombre() + " recibe por caer en el parking " + (int)impuesto + "€ del bote acumulado de impuestos.");
                        this.impuesto = 0;
                    }
                    return true;
                } else if (this.nombre.equals("IrCarcel")) {
                    // Retornamos false para indicar que se debe manejar el encarcelamiento externamente
                    return false;
                }
                // Para IrCarcel, no hacemos nada aquí porque se maneja en lanzarDados()
        }
        return true;
    }

    /*Método para añadir valor a una casilla. Utilidad:
     * - Sumar valor a la casilla de parking.
     * - Sumar valor a las casillas de solar al no comprarlas tras cuatro vueltas de todos los jugadores.
     * Este método toma como argumento la cantidad a añadir del valor de la casilla.*/
    public void sumarValor(float suma) {
        if (nombre.equals("Parking"))
            impuesto += suma;
        if (tipo.equals("Solar"))
            valor += suma;
    }

    /*Método para mostrar información sobre una casilla.
     * Devuelve una cadena con información específica de cada tipo de casilla.*/
    public String infoCasilla() {
        switch (tipo) {
            case "Solar":
                return "{" +
                        "\n\ttipo: " + tipo +
                        ",\n\tgrupo: " + color(grupo.getColorGrupo()) +
                        ",\n\tpropietario: " + duenho.getNombre() +
                        ",\n\tvalor: " + valor +
                        ",\n\talquiler: " + impuesto +
                        ",\n\tvalor hotel: " + valorHotel +
                        ",\n\tvalor casa: " + valorCasa +
                        ",\n\tvalor piscina: " + valorPiscina +
                        ",\n\tvalor pista de deporte: " + valorPistaDeporte +
                        ",\n\talquiler casa: " + alquilerCasa +
                        ",\n\talquiler hotel: " + alquilerHotel +
                        ",\n\talquiler piscina: " + alquilerPiscina +
                        ",\n\talquiler pista de deporte: " + alquilerPistaDeporte +
                        "\n}";
            case "Transporte":
            case "Servicios":
                return "{" +
                        "\n\ttipo: " + tipo +
                        ",\n\tvalor: " + valor +
                        ",\n\talquiler: " + impuesto +
                        "\n}";
            case "Impuesto":
                return "{" +
                        "\n\ttipo: " + tipo +
                        ",\n\tapagar: " + impuesto +
                        "\n}";
            case "Especiales": return imprimirEspeciales(nombre);
            default: return "";
        }
    }

    //Método que devuelve la descripción de las casillas 'Especiales'
    public String imprimirEspeciales(String nombre) {
        StringBuilder sb = new StringBuilder().append("[");
        String separador = "";
        if  (nombre.equals("Parking")) {
            for (Avatar avatar : avatares) {
                sb.append(separador).append(avatar.getJugador().getNombre());
                separador = ", ";
            }
            return "{\n\tbote: " + impuesto + "\n\tjugadores: " +  sb + "]\n}";
        } else if (nombre.equals("Carcel")) {
            for (Avatar av : avatares) {
                if (av.getJugador().getEnCarcel()) {
                    sb.append(separador).append(av.getJugador().getNombre()).append(", ").append(av.getJugador().getTiradasCarcel());
                    separador = "], [";
                }
            }
            return "{\n\tsalir: 500000,\n\tjugadores: " +  sb + "]\n}";
        }
        return "";
    }

    public void sumarVecesEnCasilla(int valor){vecesEnCasilla+=valor;}

    @Override //Sobreescritura del método equals
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Casilla casilla = (Casilla) o;
        return Objects.equals(nombre, casilla.nombre);
    }
}