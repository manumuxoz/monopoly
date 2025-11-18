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
    private float hipoteca; //Valor otorgado por hipotecar una casilla
    private ArrayList<Avatar> avatares; //Avatares que están situados en la casilla.

    //Atributos nuevos (Edificaciones):
    private float valorCasa;
    private float valorHotel;
    private float valorPiscina;
    private float valorPistaDeporte;
    private float alquilerCasa;
    private float alquilerHotel;
    private float alquilerPiscina;
    private float alquilerPistaDeporte;
    private int numCasas = 0;
    private boolean hotel;
    private boolean piscina;
    private boolean pistaDeporte;
    private ArrayList<Edificio> edificios;
    private boolean hipotecado;
    private float alquilerAcumulado;
    private int vecesEnCasilla;
    private float impuestoInicial;

    //Constructores:
    public Casilla() {
    }//Parámetros vacíos

    /*Constructor para casillas tipo Solar, Servicios o Transporte:
     * Parámetros: nombre casilla, tipo (debe ser solar, serv. o transporte), posición en el tablero, valor y dueño.
     */
    public Casilla(String nombre, String tipo, int posicion, float valor, Jugador duenho, float alquiler, float hipoteca, float alquilerCasa, float alquilerHotel, float alquilerPiscina, float alquilerPistaDeporte) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.valor = valor;
        this.posicion = posicion;
        this.duenho = duenho;
        this.impuesto = alquiler;
        this.hipoteca = hipoteca;
        avatares = new ArrayList<>();
        this.alquilerCasa = alquilerCasa;
        this.alquilerHotel = alquilerHotel;
        this.alquilerPiscina = alquilerPiscina;
        this.alquilerPistaDeporte = alquilerPistaDeporte;
        impuestoInicial = alquiler;
        edificios = new ArrayList<>();
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

    /*Constructor utilizado para crear las otras casillas (Suerte, Caja de comunidad y Especiales):
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
    public float getHipoteca() {
        return hipoteca;
    }
    public ArrayList<Avatar> getAvatares() {
        return avatares;
    }

    //Getters atributos nuevos:
    public float getValorCasa() {
        return valorCasa;
    }
    public float getValorHotel() {
        return valorHotel;
    }
    public float getValorPiscina() {
        return valorPiscina;
    }
    public float getValorPistaDeporte() {
        return valorPistaDeporte;
    }
    public float getAlquilerCasa() {
        return alquilerCasa;
    }
    public float getAlquilerHotel() {
        return alquilerHotel;
    }
    public float getAlquilerPiscina() {
        return alquilerPiscina;
    }
    public float getAlquilerPistaDeporte() {
        return alquilerPistaDeporte;
    }
    public ArrayList<Edificio> getEdificios() {
        return edificios;
    }
    public int  getNumCasas() {
        return numCasas;
    }
    public boolean getHotel() {
        return hotel;
    }
    public boolean getPiscina() {
        return piscina;
    }
    public boolean getPistaDeporte() {
        return pistaDeporte;
    }
    public boolean getHipotecado() {
        return hipotecado;
    }
    public float getAlquilerAcumulado(){return alquilerAcumulado;}
    public int getVecesEnCasilla(){return vecesEnCasilla;}

    //Setters:
    public void setDuenho(Jugador duenho) {
        this.duenho = duenho;
    }
    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
    }
    public void setImpuesto(float impuesto) {
        this.impuesto = impuesto;
    }
    public void setAvatares(ArrayList<Avatar> avatares) {
        this.avatares = avatares;
    }

    //Setters atributos nuevos:
    public void setValorCasa(float valorCasa) {
        this.valorCasa = valorCasa;
    }
    public void setValorHotel(float valorHotel) {
        this.valorHotel = valorHotel;
    }
    public void setValorPiscina(float valorPiscina) {
        this.valorPiscina = valorPiscina;
    }
    public void setValorPistaDeporte(float valorPistaDeporte) {
        this.valorPistaDeporte = valorPistaDeporte;
    }
    public void setHipotecado(boolean hipotecado) {
        this.hipotecado = hipotecado;
    }
    public void sumarAlquilerAcumulado(float valor){alquilerAcumulado+=valor;}
    public void sumarVecesEnCasilla(int valor){vecesEnCasilla+=valor;}

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

    /*Método usado para comprar una casilla determinada. Parámetros:
     * - Jugador que solicita la compra de la casilla.
     * - Banca del monopoly (es el dueño de las casillas no compradas aún).*/
    public void comprarCasilla(Jugador solicitante, Jugador banca) {
        if (!duenho.equals(banca) && !duenho.equals(solicitante)) {
            System.out.println("Esta casilla ya tiene dueño: " + duenho.getNombre() + ".");
            return;
        }

        if (tipo.equals("Especiales") || tipo.equals("Suerte") || tipo.equals("Caja")) { //Comprobamos tipo
            System.out.println("Error: Esta casilla es de tipo '" + tipo + "'. Solo se pueden comprar casillas de tipo 'Solar', 'Transporte' o 'Servicios'.");
            return;
        }

        if (solicitante.getFortuna() >= valor) { //Comprobamos fortuna
            solicitante.sumarGastos(valor);
            solicitante.sumarFortuna(-valor);
            banca.eliminarPropiedad(this);
            banca.sumarPremios(valor); //Recauda el dinero de la propiedad (Banca)
            solicitante.anhadirPropiedad(this);
            this.setDuenho(solicitante);
            solicitante.sumarPatrimonio(valor);
            System.out.println("El jugador " + solicitante.getNombre() + " compra la casilla " + nombre +
                    " por " + valor + "$. Su fortuna actual es " + solicitante.getFortuna() + "$.");
        } else
            System.out.println(solicitante.getNombre() + " no dispone de suficiente fortuna para comprar " + nombre);

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

    /* Método para mostrar información de una casilla en venta.
     * Valor devuelto: texto con esa información.
     */
    public String casEnVenta() {
        // Solo mostrar si el dueño es la banca (está en venta)
        if (duenho.getNombre().equals("Banca")) {
            if (this.getTipo().equals("Solar"))
                return "\n{" +
                        "\n\tnombre: " + nombre +
                        "\n\ttipo: " + tipo +
                        ",\n\tgrupo: " + color(grupo.getColorGrupo()) +
                        ",\n\tvalor: " + valor +
                        "\n}";
            if (this.getTipo().equals("Transporte") || this.getTipo().equals("Servicios"))
                return "\n{" +
                        "\n\tnombre: " + nombre +
                        "\n\ttipo: " + tipo +
                        ",\n\tvalor: " + valor +
                        "\n}";
        }
        return "";
    }

    //Métodos nueos:

    //Método que devuelve el nombre del color de un grupo pasado por argumento
    public String color(String colorGrupo) {
        return switch (colorGrupo) {
            case BLACK -> "Negro";
            case CYAN -> "Cian";
            case PURPLE -> "Morado";
            case WHITE -> "Blanco";
            case RED -> "Rojo";
            case YELLOW -> "Amarillo";
            case GREEN -> "Verde";
            case BLUE -> "Azul";
            default -> "";
        };
    }

    //Método para cambiar el valor del alquiler de las casillas de tipo 'Solar'
    private void incrementarAlquiler() {
        if (numCasas > 0) impuesto = alquilerCasa * numCasas;

        if (hotel) impuesto = alquilerHotel;

        if (piscina) impuesto = alquilerHotel + alquilerPiscina;

        if (pistaDeporte) impuesto = alquilerHotel + alquilerPiscina + alquilerPistaDeporte;
    }

    //Método para cambiar el valor del alquiler de las casillas de tipo 'Solar'
    private void decrementarAlquiler() {
        if (!pistaDeporte) impuesto = alquilerHotel + alquilerPiscina;

        if (!piscina) impuesto = alquilerHotel;

        if (!hotel) impuesto = alquilerCasa * numCasas;

        if (numCasas == 0) impuesto = impuestoInicial;
    }

    //Método para eliminar las casas de una casilla
    private void eliminarCasas(ArrayList<Edificio> edificiosCreados) {
        numCasas = 0;
        edificiosCreados.removeIf(edificio -> edificio.getCasilla().equals(this) && edificio.getTipo().equals("casa"));
        edificios.removeIf(edificio -> edificio.getCasilla().equals(this) && edificio.getTipo().equals("casa"));
    }

    //Método para edificar una casa
    public void edificarCasa(Jugador solicitante, ArrayList<Edificio> edificiosCreados) {
        if (!duenho.equals(solicitante)) { //Comprobamos que sea el dueño
            System.out.println("Esta casilla pertenece a " + duenho.getNombre() + ".");
            return;
        }
        if (!grupo.esDuenhoGrupo(solicitante)){ //Para comprar tiene que ser dueño de todas las propiedades del grupo
            System.out.println(solicitante.getNombre() + " no posee todas las propiedades del grupo: " + color(grupo.getColorGrupo()) + ".");
            return;
        }
        if (numCasas == 4) { //Si hay 4 casas ya construídas no puede construir más
            System.out.println("No se puede edificar ningún edificio más en esta casilla.");
            return;
        }
        if (hotel) { //Si hay un hotel no puede construir casas
            System.out.println("No se puede edificar ninguna casa, ya que ya se dispone de un hotel.");
            return;
        }

        solicitante.sumarGastos(valorCasa); //Sumamos gastos y restamos fortuna
        solicitante.sumarFortuna(-valorCasa);
        edificios.add(new Edificio(solicitante, this, "casa", edificiosCreados)); //Creamos una casa
        numCasas++; //Sumamos una casa al contador
        solicitante.sumarPatrimonio(valorCasa);
        incrementarAlquiler(); //Incrementamos alquiler

        System.out.println("Se ha edificado una casa en " + nombre + ". La fortuna de " + solicitante.getNombre() +
                " se reduce en " + valorCasa + "$.");
    }

    //Método para edificar un hotel
    public void edificarHotel(Jugador solicitante, ArrayList<Edificio> edificiosCreados) {
        if (!duenho.equals(solicitante)) { //Comprobamos que sea el dueño
            System.out.println("Esta casilla pertenece a " + duenho.getNombre() + ".");
            return;
        }
        if (!grupo.esDuenhoGrupo(solicitante)){ //Para comprar tiene que ser dueño de todas las propiedades del grupo
            System.out.println(solicitante.getNombre() + " no posee todas las propiedades del grupo: " + color(grupo.getColorGrupo()) + ".");
            return;
        }
        if (numCasas < 4) { //Si hay 4 casas ya construídas no puede construir más
            System.out.println("No se puede edificar un hotel en esta casilla. Número de casas: " + numCasas);
            return;
        }
        if (hotel) { //Si hay un hotel no puede construir casas
            System.out.println("No se puede edificar un hotel, ya que ya se dispone de uno.");
            return;
        }

        solicitante.sumarGastos(valorHotel); //Sumamos gastos y restamos fortuna
        solicitante.sumarFortuna(-valorHotel);
        eliminarCasas(edificiosCreados); //Eliminamos las casas
        edificios.add(new Edificio(solicitante, this, "hotel", edificiosCreados)); //Creamos hotel
        hotel = true;
        solicitante.sumarPatrimonio(valorHotel);
        incrementarAlquiler(); //Incrementamos alquiler

        System.out.println("Se ha edificado un hotel en " + nombre + ". La fortuna de " + solicitante.getNombre() +
                " se reduce en " + valorHotel + "$.");
    }

    //Método para edificar una piscina
    public void edificarPiscina(Jugador solicitante, ArrayList<Edificio> edificiosCreados) {
        if (!duenho.equals(solicitante)) { //Comprobamos que sea el dueño
            System.out.println("Esta casilla pertenece a " + duenho.getNombre() + ".");
            return;
        }
        if (!grupo.esDuenhoGrupo(solicitante)){ //Para comprar tiene que ser dueño de todas las propiedades del grupo
            System.out.println(solicitante.getNombre() + " no posee todas las propiedades del grupo: " + color(grupo.getColorGrupo()) + ".");
            return;
        }
        if (!hotel) { //Si hay un hotel no puede construir casas
            System.out.println("No se puede edificar una piscina, ya que no se dispone de un hotel.");
            return;
        }
        if (piscina) {
            System.out.println("No se puede edificar una piscina, ya que ya se dispone de una.");
            return;
        }

        solicitante.sumarGastos(valorPiscina); //Sumamos gastos y restamos fortuna
        solicitante.sumarFortuna(-valorPiscina);
        edificios.add(new Edificio(solicitante, this, "piscina", edificiosCreados)); //Creamos una piscina
        piscina = true; //Cambiamos bandera
        solicitante.sumarPatrimonio(valorPiscina);
        incrementarAlquiler(); //Incrementamos alquiler

        System.out.println("Se ha edificado una piscina en " + nombre + ". La fortuna de " + solicitante.getNombre() +
                " se reduce en " + valorPiscina + "$.");
    }

    //Método para edificar una pista de deporte
    public void edificarPista(Jugador solicitante, ArrayList<Edificio> edificiosCreados) {
        if (!duenho.equals(solicitante)) { //Comprobamos que sea el dueño
            System.out.println("Esta casilla pertenece a " + duenho.getNombre() + ".");
            return;
        }
        if (!grupo.esDuenhoGrupo(solicitante)){ //Para comprar tiene que ser dueño de todas las propiedades del grupo
            System.out.println(solicitante.getNombre() + " no posee todas las propiedades del grupo: " + color(grupo.getColorGrupo()) + ".");
            return;
        }
        if (!piscina) { //Si hay un hotel no puede construir casas
            System.out.println("No se puede edificar una pista de deporte, ya que no se dispone de una piscina.");
            return;
        }
        if (pistaDeporte) {
            System.out.println("No se puede edificar una pista de deporte, ya que ya se dispone de una.");
            return;
        }

        solicitante.sumarGastos(valorPistaDeporte); //Sumamos gastos y restamos fortuna
        solicitante.sumarFortuna(-valorPistaDeporte);
        edificios.add(new Edificio(solicitante, this, "pista", edificiosCreados)); //Creamos una pista de deporte
        pistaDeporte = true; //Cambiamos bandera
        solicitante.sumarPatrimonio(valorPistaDeporte);
        incrementarAlquiler(); //Incrementamos alquiler

        System.out.println("Se ha edificado una pista de deporte en " + nombre + ". La fortuna de " + solicitante.getNombre() +
                " se reduce en " + valorPistaDeporte + "$.");
    }

    //Método que devuelve información sobre los edificios construidos en una casilla
    public String infoEdificios() {
        return "{\n\tpropiedad: " + nombre +
                ",\n\t" + imprimirEdificios() +
                ",\n\talquiler: " + impuesto +
                "\n}";
    }

    //Método que devuelve un String de los edificios que se encuentran en la casilla
    private String imprimirEdificios() {
        StringBuilder sbCasas = new StringBuilder().append("["); //Creamos stringbuilders independientes para luego unirlos
        StringBuilder sbHotel = new StringBuilder().append("[");
        StringBuilder sbPiscina = new StringBuilder().append("[");
        StringBuilder sbPista = new StringBuilder().append("[");
        String separador = "";

        for (Edificio edificio : edificios) {
            switch (edificio.getTipo()) {
                case "piscina": sbPiscina.append(edificio.getID()); break;
                case "pista": sbPista.append(edificio.getID()); break;
                case "hotel": sbHotel.append(edificio.getID()); break;
                case "casa": sbCasas.append(separador).append(edificio.getID()); separador = ", "; break; //Si hay varias casas las separamos por comas
            }
        }
        return "casas: " + sbCasas + "],\n\thotel: " + sbHotel + "],\n\tpiscina: " + sbPiscina + "],\n\tpista: " + sbPista + "]"; //Unimos
    }

    //Método para vender edificios
    public void venderEdificios(String tipoEdificio, int cantidad, ArrayList<Edificio> edificiosCreados) {
        StringBuilder sb = new StringBuilder();
        float venta = 0;

        if (!edificios.isEmpty()) {
            switch (tipoEdificio) {
                case "casa":
                    if (hotel) {
                        sb.append("No se pueden vender casas en ").append(nombre).append(". Antes hay que vender el hotel");
                        break;
                    }
                    if (cantidad > numCasas) {
                        sb.append("Solamente se pueden vender " + numCasas + " casas");
                        cantidad = numCasas;
                    } else
                        sb.append(duenho.getNombre()).append(" ha vendido ").append(cantidad).append(" casas en ").append(nombre);

                    venta = venderCasas(cantidad, edificiosCreados);

                    break;

                case "hotel":
                    if (!hotel) {
                        sb.append(nombre).append(" no tiene ningún hotel construido");
                        break;
                    }
                    if (piscina) {
                        sb.append("No se puede vender el hotel en ").append(nombre).append(". Antes hay que vender la piscina");
                        break;
                    }
                    if (cantidad > 1)
                        sb.append("Solamente se puede vender 1 hotel");
                    else
                        sb.append(duenho.getNombre()).append(" ha vendido 1 hotel en ").append(nombre);

                    venta = venderHotel(edificiosCreados);
                    break;

                case "piscina":
                    if (!piscina) {
                        sb.append(nombre).append(" no tiene ninguna piscina construida");
                        break;
                    }
                    if (pistaDeporte) {
                        sb.append("No se puede vender la piscina en ").append(nombre).append(". Antes hay que vender la pista de deporte");
                        break;
                    }
                    if (cantidad > 1)
                        sb.append("Solamente se puede vender 1 piscina");
                    else
                        sb.append(duenho.getNombre()).append(" ha vendido 1 piscina en ").append(nombre);

                    venta = venderPiscina(edificiosCreados);
                    break;

                case "pista":
                    if (!pistaDeporte) {
                        sb.append(nombre).append(" no tiene ninguna pista de deporte construida");
                        break;
                    }
                    if (cantidad > 1)
                        sb.append("Solamente se puede vender 1 pita de deporte");
                    else
                        sb.append(duenho.getNombre()).append(" ha vendido 1 pista de deporte en ").append(nombre);

                    venta = venderPista(edificiosCreados);
                    break;

                case "default": break;
            }
            System.out.println(sb + ", recibiendo " + venta + "$. En la propiedad quedan " + imprimirEdificiosRestantes() + ".");
        } else
            System.out.println("No hay edificios para vender en " + nombre + ".");
    }

    //Método para vender casas
    private float venderCasas(int cantidad, ArrayList<Edificio> edificiosCreados) {
        int numCasasInicial = numCasas;
        float venta = cantidad * valorCasa;
        duenho.sumarFortuna(venta);
        eliminarCasas(edificiosCreados);
        duenho.sumarPatrimonio(-(cantidad * valorCasa));
        for (int i = 0; i < (numCasasInicial - cantidad); i++) {
            numCasas++;
            edificios.add(new Edificio(duenho, this, "casa", edificiosCreados));
        }
        decrementarAlquiler();
        return venta;
    }

    //Método para vender hoteles
    private float venderHotel(ArrayList<Edificio> edificiosCreados) {
        float venta = valorHotel;
        duenho.sumarFortuna(venta);
        duenho.sumarPatrimonio(-valorHotel);
        edificios.removeIf(edificio -> edificio.getTipo().equals("hotel"));
        edificiosCreados.removeIf(edificio -> edificio.getCasilla().equals(this) && edificio.getTipo().equals("hotel"));
        hotel = false;
        edificios.add(new Edificio(duenho, this, "casa", edificiosCreados)); //Volvemos a edificar las 4 casas
        edificios.add(new Edificio(duenho, this, "casa", edificiosCreados));
        edificios.add(new Edificio(duenho, this, "casa", edificiosCreados));
        edificios.add(new Edificio(duenho, this, "casa", edificiosCreados));
        numCasas = 4;
        decrementarAlquiler();
        return venta;
    }

    //Método para vender piscinas
    private float venderPiscina(ArrayList<Edificio> edificiosCreados) {
        float venta = valorPiscina;
        duenho.sumarFortuna(venta);
        duenho.sumarPatrimonio(-valorPiscina);
        edificios.removeIf(edificio -> edificio.getTipo().equals("piscina"));
        edificiosCreados.removeIf(edificio -> edificio.getCasilla().equals(this) && edificio.getTipo().equals("piscina"));
        piscina = false;
        decrementarAlquiler();
        return venta;
    }

    //Método para vender pistas
    private float venderPista(ArrayList<Edificio> edificiosCreados) {
        float venta = valorPistaDeporte;
        duenho.sumarFortuna(venta);
        duenho.sumarPatrimonio(-valorPistaDeporte);
        edificios.removeIf(edificio -> edificio.getTipo().equals("pista"));
        edificiosCreados.removeIf(edificio -> edificio.getCasilla().equals(this) && edificio.getTipo().equals("pista"));
        piscina = false;
        decrementarAlquiler();
        return venta;
    }

    //Método para imprimir edificios restantes después de una venta
    private String imprimirEdificiosRestantes() {
        StringBuilder sb = new StringBuilder();
        if (numCasas > -1) sb.append(numCasas).append(" casas");
        if (hotel) sb.append(", 1 hotel");
        if (piscina) sb.append(", 1 piscina");
        if (pistaDeporte) sb.append(", 1 pista de deporte");
        return sb.toString();
    }


    @Override //Sobreescritura del método equals
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Casilla casilla = (Casilla) o;
        return Objects.equals(nombre, casilla.nombre);
    }
}