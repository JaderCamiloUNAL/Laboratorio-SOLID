import java.time.LocalDate;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Cuenta ana = new CuentaAhorros("001-1", "Ana", 2_000_000);
        Cuenta luis = new CuentaAhorros("001-2", "Luis", 500_000);
        Cuenta cdtAna = new CDT("CDT-9", "Ana", 10_000_000, LocalDate.now().plusMonths(6));

        RegistroComisiones registro = new RegistroComisiones();
        registro.registrar("MISMO_BANCO", new ComisionMismoBanco());
        registro.registrar("OTRO_BANCO", new ComisionOtroBanco());
        registro.registrar("INTERNACIONAL", new ComisionInternacional());

        Repositorio repositorio = new OracleRepositorio();
        Notificador notificador = new SmsGateway();
        GeneradorComprobante generadorComprobante = new ComprobanteConsola();
        Auditor auditor = new AuditorConsola();

        TransaccionService servicio = new TransaccionService(
                repositorio,
                notificador,
                generadorComprobante,
                auditor,
                registro
        );
        servicio.transferir(ana, luis, 150_000, "OTRO_BANCO");

        new CobroCuotaManejo().cobrarMensual(List.of(
                (CuentaAhorros) ana,
                (CuentaAhorros) luis
        ));

        List<GenerableExtracto> productos = List.of(
                new TarjetaCredito(3_000_000),
                new CreditoVivienda(120_000_000)
        );
        for (GenerableExtracto p : productos) {
            System.out.println(p.generarExtracto());
        }
    }
}