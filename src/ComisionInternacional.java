public class ComisionInternacional implements CalculadoraComision {
    @Override
    public double calcular(double monto) {
        return monto * 0.03 + 25_000;
    }
}