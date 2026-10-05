public class CuentaAhorros extends Cuenta implements PagadorServiciosPublicos {
    public CuentaAhorros(String numero, String titular, double saldoInicial) {
        super(numero, titular, saldoInicial);
    }

    @Override
    public void pagar(double monto) {
        retirar(monto);
    }
}
