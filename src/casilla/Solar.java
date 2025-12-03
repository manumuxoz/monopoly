package casilla;

import edificio.Edificio;
import partida.Jugador;

import java.util.ArrayList;

import static monopoly.Valor.*;
import static monopoly.Valor.BLUE;
import static monopoly.Valor.GREEN;
import static monopoly.Valor.RED;
import static monopoly.Valor.WHITE;
import static monopoly.Valor.YELLOW;

public final class Solar extends Propiedad {
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
    private float hipoteca; //Valor otorgado por hipotecar una casilla
    private float impuestoInicial;
    private Grupo grupo;

    public Solar() {}

    public Solar(String nombre, int posicion, float valor, Jugador duenho, float alquiler, float hipoteca, float alquilerCasa, float alquilerHotel, float alquilerPiscina, float alquilerPistaDeporte) {
        super(nombre, "Solar", posicion, valor, duenho, alquiler);

        this.hipoteca = hipoteca;
        this.alquilerCasa = alquilerCasa;
        this.alquilerHotel = alquilerHotel;
        this.alquilerPiscina = alquilerPiscina;
        this.alquilerPistaDeporte = alquilerPistaDeporte;
        impuestoInicial = alquiler;
        edificios = new ArrayList<>();
    }

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
    public float getAlquilerPiscina() {return alquilerPiscina;}
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
    public float getHipoteca() {
        return hipoteca;
    }


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


    //Método para cambiar el valor del alquiler de las casillas de tipo 'Solar'
    private void incrementarAlquiler() {
        if (numCasas > 0) setImpuesto(alquilerCasa * numCasas);

        if (hotel) setImpuesto(alquilerHotel);

        if (piscina) setImpuesto(alquilerHotel + alquilerPiscina);

        if (pistaDeporte) setImpuesto(alquilerHotel + alquilerPiscina + alquilerPistaDeporte);
    }

    //Método para cambiar el valor del alquiler de las casillas de tipo 'Solar'
    private void decrementarAlquiler() {
        if (!pistaDeporte) setImpuesto(alquilerHotel + alquilerPiscina);

        if (!piscina) setImpuesto(alquilerHotel);

        if (!hotel) setImpuesto(alquilerCasa * numCasas);

        if (numCasas == 0) setImpuesto(impuestoInicial);
    }



    //Método para eliminar las casas de una casilla
    private void eliminarCasas(ArrayList<Edificio> edificiosCreados) {
        numCasas = 0;
        edificiosCreados.removeIf(edificio -> edificio.getCasilla().equals(this) && edificio.getTipo().equals("casa"));
        edificios.removeIf(edificio -> edificio.getCasilla().equals(this) && edificio.getTipo().equals("casa"));
    }
    //Método para edificar una casa
    public void edificarCasa(Jugador solicitante, ArrayList<Edificio> edificiosCreados) {
        if (!getDuenho().equals(solicitante)) { //Comprobamos que sea el dueño
            System.out.println("Esta casilla pertenece a " + getDuenho().getNombre() + ".");
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

        System.out.println("Se ha edificado una casa en " + getNombre() + ". La fortuna de " + solicitante.getNombre() +
                " se reduce en " + valorCasa + "$.");
    }

    //Método para edificar un hotel
    public void edificarHotel(Jugador solicitante, ArrayList<Edificio> edificiosCreados) {
        if (!getDuenho().equals(solicitante)) { //Comprobamos que sea el dueño
            System.out.println("Esta casilla pertenece a " + getDuenho().getNombre() + ".");
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

        System.out.println("Se ha edificado un hotel en " + getNombre() + ". La fortuna de " + solicitante.getNombre() +
                " se reduce en " + valorHotel + "$.");
    }

    //Método para edificar una piscina
    public void edificarPiscina(Jugador solicitante, ArrayList<Edificio> edificiosCreados) {
        if (!getDuenho().equals(solicitante)) { //Comprobamos que sea el dueño
            System.out.println("Esta casilla pertenece a " + getDuenho().getNombre() + ".");
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

        System.out.println("Se ha edificado una piscina en " + getNombre() + ". La fortuna de " + solicitante.getNombre() +
                " se reduce en " + valorPiscina + "$.");
    }

    //Método para edificar una pista de deporte
    public void edificarPista(Jugador solicitante, ArrayList<Edificio> edificiosCreados) {
        if (!getDuenho().equals(solicitante)) { //Comprobamos que sea el dueño
            System.out.println("Esta casilla pertenece a " + getDuenho().getNombre() + ".");
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

        System.out.println("Se ha edificado una pista de deporte en " + getNombre() + ". La fortuna de " + solicitante.getNombre() +
                " se reduce en " + valorPistaDeporte + "$.");
    }

    //Método que devuelve información sobre los edificios construidos en una casilla
    public String infoEdificios() {
        return "{\n\tpropiedad: " + getNombre() +
                ",\n\t" + imprimirEdificios() +
                ",\n\talquiler: " + getImpuesto() +
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
                case "casa": case "casas":
                    if (hotel) {
                        sb.append("No se pueden vender casas en ").append(getNombre()).append(". Antes hay que vender el hotel");
                        break;
                    }
                    if (cantidad > numCasas) {
                        sb.append("Solamente se pueden vender " + numCasas + " casas");
                        cantidad = numCasas;
                    } else
                        sb.append(getDuenho().getNombre()).append(" ha vendido ").append(cantidad).append(" casas en ").append(getNombre());

                    venta = venderCasas(cantidad, edificiosCreados);

                    break;

                case "hotel":
                    if (!hotel) {
                        sb.append(getNombre()).append(" no tiene ningún hotel construido");
                        break;
                    }
                    if (piscina) {
                        sb.append("No se puede vender el hotel en ").append(getNombre()).append(". Antes hay que vender la piscina");
                        break;
                    }
                    if (cantidad > 1)
                        sb.append("Solamente se puede vender 1 hotel");
                    else
                        sb.append(getDuenho().getNombre()).append(" ha vendido 1 hotel en ").append(getNombre());

                    venta = venderHotel(edificiosCreados);
                    break;

                case "piscina":
                    if (!piscina) {
                        sb.append(getNombre()).append(" no tiene ninguna piscina construida");
                        break;
                    }
                    if (pistaDeporte) {
                        sb.append("No se puede vender la piscina en ").append(getNombre()).append(". Antes hay que vender la pista de deporte");
                        break;
                    }
                    if (cantidad > 1)
                        sb.append("Solamente se puede vender 1 piscina");
                    else
                        sb.append(getDuenho().getNombre()).append(" ha vendido 1 piscina en ").append(getNombre());

                    venta = venderPiscina(edificiosCreados);
                    break;

                case "pista":
                    if (!pistaDeporte) {
                        sb.append(getNombre()).append(" no tiene ninguna pista de deporte construida");
                        break;
                    }
                    if (cantidad > 1)
                        sb.append("Solamente se puede vender 1 pita de deporte");
                    else
                        sb.append(getDuenho().getNombre()).append(" ha vendido 1 pista de deporte en ").append(getNombre());

                    venta = venderPista(edificiosCreados);
                    break;

                case "default": break;
            }
            System.out.println(sb + ", recibiendo " + venta + "$. En la propiedad quedan " + imprimirEdificiosRestantes() + ".");
        } else
            System.out.println("No hay edificios para vender en " + getNombre() + ".");
    }

    //Método para vender casas
    private float venderCasas(int cantidad, ArrayList<Edificio> edificiosCreados) {
        int numCasasInicial = numCasas;
        float venta = cantidad * valorCasa;
        getDuenho().sumarFortuna(venta);
        eliminarCasas(edificiosCreados);
        getDuenho().sumarPatrimonio(-(cantidad * valorCasa));
        for (int i = 0; i < (numCasasInicial - cantidad); i++) {
            numCasas++;
            edificios.add(new Edificio(getDuenho(), this, "casa", edificiosCreados));
        }
        decrementarAlquiler();
        return venta;
    }

    //Método para vender hoteles
    private float venderHotel(ArrayList<Edificio> edificiosCreados) {
        float venta = valorHotel;
        getDuenho().sumarFortuna(venta);
        getDuenho().sumarPatrimonio(-valorHotel);
        edificios.removeIf(edificio -> edificio.getTipo().equals("hotel"));
        edificiosCreados.removeIf(edificio -> edificio.getCasilla().equals(this) && edificio.getTipo().equals("hotel"));
        hotel = false;
        edificios.add(new Edificio(getDuenho(), this, "casa", edificiosCreados)); //Volvemos a edificar las 4 casas
        edificios.add(new Edificio(getDuenho(), this, "casa", edificiosCreados));
        edificios.add(new Edificio(getDuenho(), this, "casa", edificiosCreados));
        edificios.add(new Edificio(getDuenho(), this, "casa", edificiosCreados));
        numCasas = 4;
        decrementarAlquiler();
        return venta;
    }

    //Método para vender piscinas
    private float venderPiscina(ArrayList<Edificio> edificiosCreados) {
        float venta = valorPiscina;
        getDuenho().sumarFortuna(venta);
        getDuenho().sumarPatrimonio(-valorPiscina);
        edificios.removeIf(edificio -> edificio.getTipo().equals("piscina"));
        edificiosCreados.removeIf(edificio -> edificio.getCasilla().equals(this) && edificio.getTipo().equals("piscina"));
        piscina = false;
        decrementarAlquiler();
        return venta;
    }

    //Método para vender pistas
    private float venderPista(ArrayList<Edificio> edificiosCreados) {
        float venta = valorPistaDeporte;
        getDuenho().sumarFortuna(venta);
        getDuenho().sumarPatrimonio(-valorPistaDeporte);
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

    //Método que devuelve el nombre del color de un grupo pasado por argumento
    public String color() {
        return switch (grupo.getColorGrupo()) {
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

    @Override
    public boolean evaluarCasilla(Jugador actual, Jugador banca, int tirada) {
        Jugador duenho = getDuenho();
        float impuesto = getImpuesto();

        sumarVecesEnCasilla(1);
        if (!duenho.equals(banca) && !duenho.equals(actual) && !hipotecado) {
            float alquiler = impuesto;

            if (this.getGrupo().esDuenhoGrupo(duenho) && edificios.isEmpty()) //Si el duenho tiene el grupo y no tiene casas, paga el doble del alquiler inicila
                alquiler = 2 * impuesto;

            if (!actual.enBancarrota(alquiler, duenho) && actual.getFortuna() >= alquiler) {
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
        }
        return false;
    }

    @Override
    public String infoCasilla() {
        return "{" +
                "\n\ttipo: " + getTipo() +
                ",\n\tgrupo: " + color() +
                ",\n\tpropietario: " + getDuenho().getNombre() +
                ",\n\tvalor: " + getValor() +
                ",\n\talquiler: " + getImpuesto() +
                ",\n\tvalor hotel: " + valorHotel +
                ",\n\tvalor casa: " + valorCasa +
                ",\n\tvalor piscina: " + valorPiscina +
                ",\n\tvalor pista de deporte: " + valorPistaDeporte +
                ",\n\talquiler casa: " + alquilerCasa +
                ",\n\talquiler hotel: " + alquilerHotel +
                ",\n\talquiler piscina: " + alquilerPiscina +
                ",\n\talquiler pista de deporte: " + alquilerPistaDeporte +
                "\n}";
    }

    @Override
    public String casEnVenta(){
        return "\n{" +
                "\n\tnombre: " + getNombre() +
                "\n\ttipo: " + getTipo() +
                ",\n\tgrupo: " + color() +
                ",\n\tvalor: " + getValor() +
                "\n}";
    }

    //edificar
}