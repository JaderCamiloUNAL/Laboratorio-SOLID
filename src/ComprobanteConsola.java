public class ComprobanteConsola implements GeneradorComprobante {
    @Override
    public void generar(String origen, String destino, double monto, double comision) {
        System.out.println("===== BANCO ANDINO - COMPROBANTE =====");
        System.out.println("Origen: " + origen);
        System.out.println("Destino: " + destino);
        System.out.println("Monto: $" + monto);
        System.out.println("Comision: $" + comision);
        System.out.println("======================================");
    }
}