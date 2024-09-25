package br.edu.fatecpg.api.gemini.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ConsomeAPI {
    private static final String API_URL = "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash-latest:generateContent?key=AIzaSyA0pdZdvIXa9pYicdzcwJVQxRo7xwkxJwo";
    private static final Pattern RESP_PATTERN = Pattern.compile("\"text\"\\s*:\\s*\"([^\"]+)\"");

    public static String fazerPegunta(String pergunta) throws IOException, InterruptedException {
        String jsonRequest = gerarJsonRequest(pergunta);
        String respostaJson = enviarRequisicao(jsonRequest);
        return extrairResposta(respostaJson);
    }

    private static String gerarJsonRequest(String pergunta) {
        String promptFormatado = "O Resultado gerado não deve possuir formatação ou caracteres especiais e precisa ser resumido em no máximo 1 linha, capture os pontos mais importantes. Pergunta: " + pergunta;
        return "{\"contents\":[{\"parts\":[{\"text\":\"" + promptFormatado + "\"}]}]}";
    }

    private static String enviarRequisicao(String jsonRequest) throws IOException, InterruptedException {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(API_URL))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonRequest))
                    .build();
            return client.send(request, HttpResponse.BodyHandlers.ofString()).body();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static String extrairResposta(String respostaJson) {
        StringBuilder resposta = new StringBuilder();
        for (String linha : respostaJson.lines().toList()) {
            Matcher matcher = RESP_PATTERN.matcher(linha);
            if (matcher.find()) {
                resposta.append(matcher.group()).append(" ");
            }
        }
        return resposta.toString();
    }

    public static void salvarResumoArquivo(String resumo, String caminho) throws IOException {
        Files.write(Paths.get(caminho), resumo.getBytes());
    }
}