public class CreditoVivienda implements GenerableExtracto, CalculableIntereses, PagableCuota {
    private double saldoPendiente;

    public CreditoVivienda(double valorPrestamo) {
        this.saldoPendiente = valorPrestamo;
    }

    @Override
    public double calcularIntereses() {
        return saldoPendiente * 0.011;
    }

    @Override
    public void pagarCuota(double monto) {
        saldoPendiente -= monto;
    }

    @Override
    public String generarExtracto() {
        return "Credito vivienda - pendiente: $" + saldoPendiente;
    }
}