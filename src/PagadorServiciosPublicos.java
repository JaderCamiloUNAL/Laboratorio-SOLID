public interface PagadorServiciosPublicos {
    String getNumero();
    String getTitular();
    void pagar(double monto);
}