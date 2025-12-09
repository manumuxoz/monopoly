package monopoly;

import excepciones.*;

public interface Comando {
    void acabarTurno();

    void comprar(String nombre) throws Excepcion;

    void crearJugador(String partes[]) throws Excepcion;

    void descCasilla(String nombre) throws Excepcion;

    void descJugador(String nombre) throws Excepcion;

    void deshipotecar(String nombre) throws Excepcion;

    void edificar(String tipo) throws Excepcion;

    void hipotecar(String nombreCasilla) throws Excepcion;

    void indicarTurnoJugador();

    void lanzarDados(int tirada1, int tirada2) throws Excepcion;

    void leerComandos() throws RuntimeException;

    void listarEdificios();

    void listarEdificiosGrupo(String colorGrupo) throws Excepcion;

    void listarJugadores();

    void listarEnVenta();

    void mostrarEstadisticas(String nombre) throws Excepcion;

    void mostrarEstadisticasGlobales() throws Excepcion;

    void salirCarcel() throws Excepcion;

    void vender(String tipoEdificio, String nombreCasilla, int cantidad) throws Excepcion;

    String verTablero();
}
