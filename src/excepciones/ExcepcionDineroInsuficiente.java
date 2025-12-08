package excepciones;

public class ExcepcionDineroInsuficiente extends ExcepcionDinero {
    float cobro;

    public float getCobro() {
        return cobro;
    }

    public ExcepcionDineroInsuficiente(String message, float cobro) {
        super(message);
        this.cobro = cobro;
    }
}
