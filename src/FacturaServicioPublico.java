public class FacturaServicioPublico extends Factura {
    public FacturaServicioPublico(String referencia, String emisor, double valor) {
        super(referencia, emisor, valor);
    }

    public void pagar() {
        if (estaPagada()) { throw new IllegalStateException("La factura ya ha sido pagada"); }
        this.pagada = true;
    }
}