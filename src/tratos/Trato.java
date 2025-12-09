package tratos;

import casillas.Propiedad;
import excepciones.Excepcion;
import excepciones.ExcepcionArgumento;
import excepciones.ExcepcionReglas;
import partida.Jugador;
import static monopoly.Juego.consola;

import java.util.ArrayList;

public class Trato {
    String id;
    Jugador solicitante;
    Jugador receptor;
    Propiedad propiedadSolicitante;
    Propiedad propiedadReceptor;
    float cantidadSolicitante;
    float cantidadReceptor;


    public Trato() {
    }

    public Trato (Jugador solicitante, Jugador receptor, Propiedad propiedad1, Propiedad propiedad2, int cantidad1, int cantidad2, ArrayList<Trato> tratosCreados){
        generarID(tratosCreados);
        this.solicitante = solicitante;
        this.receptor = receptor;
        this.propiedadSolicitante = propiedad1;
        this.propiedadReceptor = propiedad2;
        this.cantidadSolicitante = cantidad1;
        this.cantidadReceptor = cantidad2;
    }



    //Método para generar un ID a cada nuevo edificio
    private void generarID( ArrayList<Trato> tratosCreados) {
        id = String.format("trato" + tratosCreados.size()); //Creamos ID con el formato tipo-número
    }
    public String getId(){return id;}
    public Jugador getSolicitante(){return solicitante;}
    public Jugador getReceptor(){return receptor;}


    public void aceptarTrato(String id, ArrayList<Trato> tratosCreados) throws Excepcion {
        if (!this.id.equals(id))
            throw new ExcepcionArgumento("Este trato no existe");

        if(propiedadSolicitante !=null && propiedadReceptor !=null && cantidadReceptor==0 && cantidadSolicitante == 0){
            //PROPIEDAD POR PROPIEDAD
            if(!receptor.getPropiedades().contains(propiedadReceptor)){
                throw new ExcepcionReglas(receptor.getNombre() + " no tiene la casilla " + propiedadReceptor.getNombre());
            }
            if(!solicitante.getPropiedades().contains(propiedadSolicitante)){
                throw new ExcepcionReglas(solicitante.getNombre() + " no tiene la casilla " + propiedadSolicitante.getNombre());
            }
            solicitante.eliminarPropiedad(propiedadSolicitante);
            receptor.anhadirPropiedad(propiedadSolicitante);
            receptor.eliminarPropiedad(propiedadReceptor);
            solicitante.anhadirPropiedad(propiedadReceptor);
            consola.imprimir("Se ha aceptado el siguiente trato con " + solicitante.getNombre() + ": le doy " + propiedadReceptor.getNombre() + " y " + solicitante.getNombre() + " me da " + propiedadSolicitante.getNombre() + ".");
            tratosCreados.remove(this);

        }
        if(propiedadSolicitante != null && propiedadReceptor==null && cantidadReceptor!=0 && cantidadSolicitante == 0){
            //PROPIEDAD POR DINERO
            if(!solicitante.getPropiedades().contains(propiedadSolicitante)){
                throw new ExcepcionReglas(solicitante.getNombre() + " no tiene la casilla " + propiedadSolicitante.getNombre());
            }
            if(cantidadReceptor >= receptor.getFortuna()){
                throw new ExcepcionReglas("El jugador " + receptor.getNombre() + " no tiene suficiente dinero");
            }
            solicitante.eliminarPropiedad(propiedadSolicitante);
            receptor.anhadirPropiedad(propiedadSolicitante);
            solicitante.sumarFortuna(cantidadReceptor);
            receptor.sumarFortuna(-cantidadReceptor);
            receptor.sumarGastos(cantidadReceptor);
            tratosCreados.remove(this);
            consola.imprimir("Se ha aceptado el siguiente trato con " + solicitante.getNombre() + ": le doy " + cantidadReceptor + " y " + solicitante.getNombre() + " me da " + propiedadSolicitante.getNombre() + "$.");

        }
        if(propiedadSolicitante == null && propiedadReceptor !=null && cantidadReceptor == 0 && cantidadSolicitante!=0){
            //DINERO POR PROPIEDAD
            if(!receptor.getPropiedades().contains(propiedadReceptor)){
                throw new ExcepcionReglas(receptor.getNombre() + " no tiene la casilla " + propiedadReceptor.getNombre());
            }
            if(cantidadSolicitante >= solicitante.getFortuna()){
                throw new ExcepcionReglas("El jugador " + solicitante.getNombre() + " no tiene suficiente dinero");
            }
            receptor.eliminarPropiedad(propiedadReceptor);
            solicitante.anhadirPropiedad(propiedadReceptor);
            solicitante.sumarFortuna(-cantidadSolicitante);
            solicitante.sumarGastos(cantidadSolicitante);
            receptor.sumarFortuna(cantidadSolicitante);
            tratosCreados.remove(this);
            consola.imprimir("Se ha aceptado el siguiente trato con " + solicitante.getNombre() + ": le doy " + propiedadReceptor.getNombre() + " y " + solicitante.getNombre() + " me da " + cantidadSolicitante + "$.");

        }
        if((propiedadSolicitante!=null && propiedadReceptor !=null && cantidadReceptor!=0 && cantidadSolicitante==0)){
            //PROPIEDAD POR DINERO Y PROPIEDAD O PROPIEDAD POR PROPIEDAD Y DINERO
            if(!solicitante.getPropiedades().contains(propiedadSolicitante)){
                throw new ExcepcionReglas(solicitante.getNombre() + " no tiene la casilla " + propiedadSolicitante.getNombre());
            }
            if(!receptor.getPropiedades().contains(propiedadReceptor)){
                throw new ExcepcionReglas(receptor.getNombre() + " no tiene la casilla " + propiedadReceptor.getNombre());
            }
            if(cantidadReceptor >= receptor.getFortuna()){
                throw new ExcepcionReglas("El jugador " + receptor.getNombre() + "no tiene suficiente dinero");
            }
            solicitante.eliminarPropiedad(propiedadSolicitante);
            receptor.anhadirPropiedad(propiedadSolicitante);
            receptor.eliminarPropiedad(propiedadReceptor);
            solicitante.anhadirPropiedad(propiedadReceptor);
            solicitante.sumarFortuna(cantidadReceptor);
            receptor.sumarFortuna(-cantidadReceptor);
            receptor.sumarGastos(cantidadReceptor);
            tratosCreados.remove(this);
            consola.imprimir("Se ha aceptado el siguiente trato con " + solicitante.getNombre() + ": le doy " + propiedadReceptor.getNombre() + " y " + cantidadReceptor + "$ y "+ solicitante.getNombre() + " me da " + propiedadSolicitante.getNombre());

        }
    }
    public String infoTrato() {
        if (propiedadSolicitante != null && propiedadReceptor != null && cantidadReceptor == 0 && cantidadSolicitante == 0) {
            //PROPIEDAD POR PROPIEDAD
            return "{" +
                    "\nid: " + id +
                    ",\n\tjugadorPropone: " + solicitante.getNombre() + "," +
                    ",\n\ttrato: cambiar (" + propiedadSolicitante.getNombre() + ", " + propiedadReceptor.getNombre() + ")" +
                    "\n},";
        } else if (propiedadSolicitante != null && propiedadReceptor == null && cantidadReceptor != 0 && cantidadSolicitante == 0) {
            //PROPIEDAD POR DINERO
            return "{" +
                    "\nid: " + id +
                    ",\n\tjugadorPropone: " + solicitante.getNombre() + "," +
                    ",\n\ttrato: cambiar (" + propiedadSolicitante.getNombre() + ", " + cantidadReceptor + ")" +
                    "\n},";
        } else if(propiedadSolicitante == null && propiedadReceptor !=null && cantidadReceptor == 0 && cantidadSolicitante!=0){
            //DINERO POR PROPIEDAD
            return "{" +
                    "\nid: " + id +
                    ",\n\tjugadorPropone: " + solicitante.getNombre() + "," +
                    ",\n\ttrato: cambiar (" + cantidadSolicitante + ", " + propiedadReceptor.getNombre() + ")" +
                    "\n},";
        }else if(propiedadSolicitante!=null && propiedadReceptor !=null && cantidadReceptor!=0 && cantidadSolicitante==0){
            //PROPIEDAD POR DINERO Y PROPIEDAD O PROPIEDAD POR PROPIEDAD Y DINERO
            return "{" +
                    "\nid: " + id +
                    ",\n\tjugadorPropone: " + solicitante.getNombre() + "," +
                    ",\n\ttrato: cambiar (" + propiedadSolicitante.getNombre() + ", " + propiedadReceptor.getNombre() + ", " + cantidadReceptor + ")" +
                    "\n},";
        }
        return"";
    }
}



