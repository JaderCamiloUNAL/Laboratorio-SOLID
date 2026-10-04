public class AntifraudeConsola implements SistemaAntifraude {
    @Override
    public void analizar(String origen, String destino, double monto) {
        System.out.println("[ANTIFRAUDE] " + origen + " -> " + destino + " $" + monto);
    }
}