public interface Auditor {
    void registrar(String tipo, String origen, String destino, double monto);
}