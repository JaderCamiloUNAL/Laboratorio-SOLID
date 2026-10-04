import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


class RepositorioSpy implements Repositorio {
    int llamadas = 0;
    @Override
    public void guardarTransaccion(String origen, String destino, double monto, double comision) {
        llamadas++;
    }
}

class NotificadorSpy implements Notificador {
    int llamadas = 0;
    @Override
    public void enviar(String destinatario, String mensaje) {
        llamadas++;
    }
}

class GeneradorComprobanteDummy implements GeneradorComprobante {
    @Override
    public void generar(String origen, String destino, double monto, double comision) {
        // No hace nada para las pruebas
    }
}

class AuditorDummy implements Auditor {
    @Override
    public void registrar(String tipo, String origen, String destino, double monto) {
        // No hace nada para las pruebas
    }
}


public class TransaccionServiceTest {

    private TransaccionService servicio;
    private RepositorioSpy repoSpy;
    private NotificadorSpy notificadorSpy;
    private CuentaAhorros origen;
    private CuentaAhorros destino;

    @BeforeEach
    public void setUp() {
        repoSpy = new RepositorioSpy();
        notificadorSpy = new NotificadorSpy();
        GeneradorComprobanteDummy comprobanteDummy = new GeneradorComprobanteDummy();
        AuditorDummy auditorDummy = new AuditorDummy();

        RegistroComisiones registro = new RegistroComisiones();
        registro.registrar("MISMO_BANCO", new ComisionMismoBanco());
        registro.registrar("OTRO_BANCO", new ComisionOtroBanco());

        servicio = new TransaccionService(
                repoSpy,
                notificadorSpy,
                comprobanteDummy,
                auditorDummy,
                registro
        );

        origen = new CuentaAhorros("001", "Ana", 100_000);
        destino = new CuentaAhorros("002", "Luis", 50_000);
    }

    @Test
    public void transferenciaMismoBancoNoCobraComisionYMueveMonto() {
        servicio.transferir(origen, destino, 20_000, "MISMO_BANCO");

        assertEquals(80_000, origen.getSaldo());
        assertEquals(70_000, destino.getSaldo());
    }

    @Test
    public void transferenciaOtroBancoCobraComision() {
        servicio.transferir(origen, destino, 20_000, "OTRO_BANCO");

        // Se descuenta 20,000 + 7,500 de comisión
        assertEquals(72_500, origen.getSaldo());
        assertEquals(70_000, destino.getSaldo());
    }

    @Test
    public void saldoInsuficienteRechazaYNoGuardaNada() {
        assertThrows(IllegalStateException.class, () -> {
            servicio.transferir(origen, destino, 200_000, "MISMO_BANCO");
        });

        assertEquals(0, repoSpy.llamadas);
        assertEquals(0, notificadorSpy.llamadas);
    }

    @Test
    public void transferenciaExitosaSeGuardaYNotificaUnaSolaVez() {
        servicio.transferir(origen, destino, 20_000, "MISMO_BANCO");

        assertEquals(1, repoSpy.llamadas);
        assertEquals(1, notificadorSpy.llamadas);
    }

    @Test
    public void tipoDesconocidoRechazaYNoCambiaSaldo() {
        assertThrows(IllegalArgumentException.class, () -> {
            servicio.transferir(origen, destino, 20_000, "INVENTADO");
        });

        assertEquals(100_000, origen.getSaldo());
        assertEquals(50_000, destino.getSaldo());
    }
}