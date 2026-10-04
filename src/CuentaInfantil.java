public class CuentaInfantil extends CuentaAhorros {
    private double retiradoHoy = 0;
    private static final double LIMITE_DIARIO = 200_000;

    public CuentaInfantil(String numero, String titular, double saldoInicial) {
        super(numero, titular, saldoInicial);
    }

    @Override
    public void retirar(double monto) {
        if (retiradoHoy + monto > LIMITE_DIARIO) {
            throw new IllegalStateException("Supera el limite diario de retiro para cuenta infantil");
        }
        super.retirar(monto);
        retiradoHoy += monto;
    }
}