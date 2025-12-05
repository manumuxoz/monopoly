package edificios;

import monopoly.Grupo;
import casillas.Solar;
import partida.*;
import java.util.ArrayList;
import java.util.Objects;


public abstract class Edificio { //Nueva clase edificio
    private Jugador duenho; //Jugador al que pertenece
    private Solar solar; //Casilla en la que está el edificio
    private Grupo grupo; //Grupo en el que está el edificio
    private String ID; //ID que identifica de manera única al edificio
    private String tipo; //String para el tipo de edificio (casa, hotel, piscina o pista)

    //Constructor vacío
    public Edificio() {
    }

    //Constructor con argumentos (jugador al que pertenece, casilla en la que está, tipo de edificio y un arraylist
    //con los edificios ya creados
    public Edificio(Jugador duenho, Solar solar, String tipo, ArrayList<Edificio> edificiosCreados) {
        this.duenho = duenho;
        this.solar = solar;
        this.tipo = tipo;
        grupo = solar.getGrupo();
        generarID(tipo,  edificiosCreados); //Creamos ID
    }

    //Método para generar un ID a cada nuevo edificio
    private void generarID(String tipo, ArrayList<Edificio> edificiosCreados) {
        int count = 1;
        for (Edificio edificio : edificiosCreados) {
            if (tipo.equals(edificio.tipo)) //Comparamos tipo para sumar al contador
                count++;
        }
        ID = String.format(tipo + "-" + count); //Creamos ID con el formato tipo-número

        edificiosCreados.add(this); //Añadimos al arraylist global
    }

    //Getters:
    public Jugador getDuenho() {
        return duenho;
    }
    public Solar getSolar() {
        return solar;
    }
    public String getID() {
        return ID;
    }
    public String getTipo() {
        return tipo;
    }

    /*Método para mostrar información sobre un edificio.
     * Devuelve una cadena con información específica de cada tipo de edificio.*/
    public String infoEdificio() {
        return "{\n\tid: " + ID +
                ",\n\tpropietario: " + duenho.getNombre() +
                ",\n\tsolar: " + solar.getNombre() +
                ",\n\tgrupo: " + solar.color() +
                ",\n\tcoste: " + imprimirCoste() +
                "\n}";
    }

    //Método para imprimir el valor del alquiler de cada edificio dependiendo de su tipo
    abstract String imprimirCoste();

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Edificio edificio = (Edificio) o;
        return Objects.equals(ID, edificio.ID);
    }
}
