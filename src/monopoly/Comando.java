package monopoly;

public interface Comando {
    void acabarTurno();

    void comprar(String nombre);

    void descCasilla(String nombre);

    void deshipotecar(String nombreCasilla);

    void descJugador(String[] partes);

    void edificar(String tipoEdificio);

    void hipotecar(String nombreCasilla);

    void indicarTurnoJugador();

    void lanzarDados(int tirada1, int tirada2);

    void listarEdificios();

    void listarEdificiosGrupo(String colorGrupo);

    void listarJugadores();

    void listarVenta();

    void mostrarEstadisticas(String[] string);

    void mostrarEstadisticasGlobales();

    void salirCarcel();

    void vender(String tipoEdificio, String nombreCasilla, int cantidad);
}
