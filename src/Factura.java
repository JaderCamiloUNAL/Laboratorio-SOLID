public abstract class Factura {
    protected final String referencia;
    protected final String emisor;
    protected final double valor;
    protected boolean pagada;

    public Factura(String referencia, String emisor, double valor) {
        this.referencia = referencia;
        this.emisor = emisor;
        this.valor = valor;
        this.pagada = false;
    }
    public String getReferencia() { return referencia; }
    public String getEmisor() { return emisor; }
    public double getValor() { return valor; }
    public boolean estaPagada() { return pagada; }

    public abstract void pagar();
} 