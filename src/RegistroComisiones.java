import java.util.HashMap;
import java.util.Map;

public class RegistroComisiones {
    private final Map<String, CalculadoraComision> calculadoras = new HashMap<>();

    public void registrar(String tipo, CalculadoraComision calculadora) {
        calculadoras.put(tipo, calculadora);
    }

    public CalculadoraComision obtener(String tipo) {
        CalculadoraComision calc = calculadoras.get(tipo);
        if (calc == null) {
            throw new IllegalArgumentException("Tipo de transferencia desconocido");
        }
        return calc;
    }
}