public class TransaccionService {

    private final Repositorio repositorio;
    private final Notificador notificador;
    private final GeneradorComprobante generadorComprobante;
    private final Auditor auditor;
    private final RegistroComisiones registroComisiones;

    public TransaccionService(
            Repositorio repositorio,
            Notificador notificador,
            GeneradorComprobante generadorComprobante,
            Auditor auditor,
            RegistroComisiones registroComisiones) {

        this.repositorio = repositorio;
        this.notificador = notificador;
        this.generadorComprobante = generadorComprobante;
        this.auditor = auditor;
        this.registroComisiones = registroComisiones;
    }

    public void transferir(
            Cuenta origen,
            Cuenta destino,
            double monto,
            String tipo) {

        if (monto <= 0) {
            throw new IllegalArgumentException("Monto inválido");
        }

        if (monto > 5_000_000) {
            throw new IllegalArgumentException("Supera el tope diario");
        }

        CalculadoraComision calculadora =
                registroComisiones.obtener(tipo);

        double comision = calculadora.calcular(monto);

        origen.retirar(monto + comision);
        destino.depositar(monto);

        repositorio.guardarTransaccion(
                origen.getNumero(),
                destino.getNumero(),
                monto,
                comision
        );

        generadorComprobante.generar(
                origen.getNumero(),
                destino.getNumero(),
                monto,
                comision
        );

        notificador.enviar(
                origen.getTitular(),
                "Transferiste $" + monto
                        + " a la cuenta "
                        + destino.getNumero()
        );

        auditor.registrar(
                tipo,
                origen.getNumero(),
                destino.getNumero(),
                monto
        );
    }
}