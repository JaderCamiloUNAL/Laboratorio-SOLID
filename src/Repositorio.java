public interface Repositorio {
    void guardarTransaccion(
            String origen,
            String destino,
            double monto,
            double comision
    );
}