public class TransaccionService {
    private final OracleRepositorio repositorio = new OracleRepositorio();
    private final SmsGateway sms = new SmsGateway();
    private final GeneradorComprobante generadorComprobante = new ComprobanteConsola();
    private final Auditor auditor = new AuditorConsola();
    private final RegistroComisiones registroComisiones;

    public TransaccionService(RegistroComisiones registroComisiones) {
        this.registroComisiones = registroComisiones;
    }

    public void transferir(Cuenta origen, Cuenta destino, double monto, String tipo) {
        if (monto <= 0) throw new IllegalArgumentException("Monto inválido");
        if (monto > 5_000_000) throw new IllegalArgumentException("Supera el tope diario");

        CalculadoraComision calculadora = registroComisiones.obtener(tipo);
        double comision = calculadora.calcular(monto);

        origen.retirar(monto + comision);
        destino.depositar(monto);

        repositorio.guardarTransaccion(origen.getNumero(), destino.getNumero(), monto, comision);
        generadorComprobante.generar(origen.getNumero(), destino.getNumero(), monto, comision);
        sms.enviar(origen.getTitular(), "Transferiste $" + monto + " a la cuenta " + destino.getNumero());
        auditor.registrar(tipo, origen.getNumero(), destino.getNumero(), monto);
    }
}