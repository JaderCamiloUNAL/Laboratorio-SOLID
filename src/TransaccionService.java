public class TransaccionService {

    private final Repositorio repositorio;
    private final Notificador notificador;
    private final GeneradorComprobante generadorComprobante;
    private final Auditor auditor;
    private final RegistroComisiones registroComisiones;
    private final SistemaAntifraude antifraude;

    public TransaccionService(
            Repositorio repositorio,
            Notificador notificador,
            GeneradorComprobante generadorComprobante,
            Auditor auditor,
            SistemaAntifraude antifraude,
            RegistroComisiones registroComisiones) {

        this.repositorio = repositorio;
        this.notificador = notificador;
        this.generadorComprobante = generadorComprobante;
        this.auditor = auditor;
        this.antifraude = antifraude;
        this.registroComisiones = registroComisiones;
    }

    private void validarMonto(double monto) {
        if (monto <= 0) {
            throw new IllegalArgumentException("Monto inválido");
        }

        if (monto > 5_000_000) {
            throw new IllegalArgumentException("Supera el tope diario");
        }
    }

    public void transferir(
            Cuenta origen,
            Cuenta destino,
            double monto,
            String tipo) {

        validarMonto(monto);

        CalculadoraComision calculadora =
                registroComisiones.obtener(tipo);

        double comision = calculadora.calcular(monto);

        origen.retirar(monto + comision);
        destino.depositar(monto);

        antifraude.analizar(
                origen.getNumero(),
                destino.getNumero(),
                monto
        );

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

    public void pagoServicios(
            PagadorServiciosPublicos origen,
            Factura factura,
            double monto,
            String tipo) {

        validarMonto(monto);

        if (factura.estaPagada()) {
            throw new IllegalStateException("La factura ya ha sido pagada");
        }

        CalculadoraComision calculadora =
                registroComisiones.obtener(tipo);

        double comision = calculadora.calcular(monto);

        origen.pagar(monto + comision);
        factura.pagar();

        antifraude.analizar(
                origen.getNumero(),
                factura.getReferencia(),
                monto
        );

        repositorio.guardarTransaccion(
                origen.getNumero(),
                factura.getReferencia(),
                monto,
                comision
        );

        generadorComprobante.generar(
                origen.getNumero(),
                factura.getReferencia(),
                monto,
                comision
        );

        notificador.enviar(
                origen.getTitular(),
                "Pagaste $" + monto
                        + " de la factura "
                        + factura.getReferencia()
        );

        auditor.registrar(
                tipo,
                origen.getNumero(),
                factura.getReferencia(),
                monto
        );
    }
}