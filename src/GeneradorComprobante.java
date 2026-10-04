public interface GeneradorComprobante {
    void generar(String origen, String destino, double monto, double comision);
}