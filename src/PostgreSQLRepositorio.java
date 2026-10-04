public class PostgreSQLRepositorio implements Repositorio {
    @Override
    public void guardarTransaccion(
            String origen,
            String destino,
            double monto,
            double comision) {

        System.out.println(
                "[POSTGRES] INSERT INTO transacciones VALUES ('"
                        + origen + "', '"
                        + destino + "', "
                        + monto + ", "
                        + comision + ")"
        );
    }
}