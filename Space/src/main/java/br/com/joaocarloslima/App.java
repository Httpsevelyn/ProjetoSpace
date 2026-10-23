package br.com.joaocarloslima;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;

public class App {
    private static Controller controller = new Controller();

    public static void main(String[] args) throws IOException {
        HttpServer servidor = HttpServer.create(new InetSocketAddress("localhost", 8080), 0);
        servidor.createContext("/api/jogo", App::api);
        servidor.createContext("/", App::arquivo);
        servidor.start();
        System.out.println("Abra http://localhost:8080 no navegador.");
        System.out.println("Para encerrar, pressione Ctrl+C.");
    }

    private static synchronized void api(HttpExchange req) throws IOException {
        if (!req.getRequestMethod().equals("POST")) {
            responder(req, 405, "text/plain", "Use POST".getBytes(StandardCharsets.UTF_8));
            return;
        }
        String comando = new String(req.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
        String estado = controller.atualizar(comando);
        responder(req, 200, "application/json", estado.getBytes(StandardCharsets.UTF_8));
    }

    private static void arquivo(HttpExchange req) throws IOException {
        String caminho = req.getRequestURI().getPath();
        if (caminho.equals("/")) caminho = "/index.html";
        if (!(caminho.equals("/index.html") || caminho.startsWith("/images/"))
                || caminho.contains("..")) {
            responder(req, 404, "text/plain", new byte[0]);
            return;
        }
        try (InputStream arquivo = App.class.getResourceAsStream("/web" + caminho)) {
            if (arquivo == null) {
                responder(req, 404, "text/plain", new byte[0]);
                return;
            }
            String tipo = caminho.endsWith(".png") ? "image/png" : "text/html";
            responder(req, 200, tipo, arquivo.readAllBytes());
        }
    }

    private static void responder(HttpExchange req, int status, String tipo, byte[] dados) throws IOException {
        req.getResponseHeaders().set("Content-Type", tipo + (tipo.startsWith("image/") ? "" : "; charset=UTF-8"));
        req.getResponseHeaders().set("Cache-Control", "no-store");
        req.sendResponseHeaders(status, dados.length);
        try (java.io.OutputStream saida = req.getResponseBody()) { saida.write(dados); }
    }
}
