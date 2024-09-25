package br.edu.fatecpg.api.gemini.view;

import br.edu.fatecpg.api.gemini.service.ConsomeAPI;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        Scanner scan = new Scanner(System.in);

        boolean running = true;

        ArrayList<String> questões = new ArrayList<>();

        while (running) {
            int cont = 0;
            int max = 2;

            System.out.println("Entre com sua opção: ");
            System.out.println("1 - Fazer Perguntas(Máximo de 3 perguntas por sessão.)");
            System.out.println("2 - Sair");

            int op = scan.nextInt();
            scan.nextLine();

            switch (op) {
                case 1:
                    for (int i = 0; i <= max; i++) {
                        System.out.println("Digite sua " + (i+1) + " pergunta: ");
                        String pergunta = scan.nextLine();
                        String resposta = ConsomeAPI.fazerPegunta(pergunta);
                        cont++;
                        questões.add("Pergunta " + cont + " : " + pergunta + "\n" + "Resposta: " + resposta);

                        StringBuilder sb = new StringBuilder();

                        for (String questão : questões) {
                            sb.append(questão).append("\n\n");
                        }

                        ConsomeAPI.salvarResumoArquivo(sb.toString(), "resumo_respostas.txt");
                        System.out.println("Resposta gravada com sucesso em resumo_respostas.txt");
                    }
                    break;
                case 2:
                    System.out.println("Saindo da aplicação...");
                    running = false;
                    break;
                default:
                    System.out.println("Opção inválida.");
            }


        }
    }
}