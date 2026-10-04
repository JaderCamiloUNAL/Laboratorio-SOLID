public class NotificadorMultiple implements Notificador {
    private final Notificador sms;
    private final Notificador push;

    public NotificadorMultiple(Notificador sms, Notificador push) {
        this.sms = sms;
        this.push = push;
    }

    @Override
    public void enviar(String destinatario, String mensaje) {
        sms.enviar(destinatario, mensaje);
        push.enviar(destinatario, mensaje);
    }
}