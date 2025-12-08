package tratos;

import casillas.Propiedad;
import partida.Jugador;

import java.util.ArrayList;

public class Trato {
    String id;
    Jugador jugador1;
    Jugador jugador2;
    Propiedad propiedad1;
    Propiedad propiedad2;
    float cantidad1;
    float cantidad2;

    public Trato() {
    }

    public Trato (Jugador solicitante, Jugador receptor, Propiedad propiedad1, Propiedad propiedad2, float cantidad1, float cantidad2, ArrayList<Trato> tratosCreados){
        generarID(tratosCreados);
        this.jugador1 = solicitante;
        this.jugador2 = receptor;
        this.propiedad1 = propiedad1;
        this.propiedad2 = propiedad2;
        this.cantidad1 = cantidad1;
        this.cantidad2 = cantidad2;
    }



    //Método para generar un ID a cada nuevo edificio
    private void generarID( ArrayList<Trato> tratosCreados) {
        id = String.format("trato" + tratosCreados.size()); //Creamos ID con el formato tipo-número
        tratosCreados.add(this); //Añadimos al arraylist global
    }
}



