package br.edu.fatecpg.api.gemini.view;

import br.edu.fatecpg.api.gemini.service.ConsomeAPI;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        Scanner scan = new Scanner(System.in);
        boolean running = true;

        try {
            while (running) {
                System.out.println("Entre com sua opção: ");
                System.out.println("1 - Fazer Pergunta");
                System.out.println("2 - Sair");

                int op = scan.nextInt();
                scan.nextLine();

                switch (op) {
                    case 1:
                        System.out.println("Digite sua pergunta: ");
                        String pergunta = scan.nextLine();
                        String resposta = ConsomeAPI.fazerPegunta(pergunta);
                        System.out.println(resposta);
                        break;
                    case 2:
                        System.out.println("Saindo da aplicação...");
                        running = false;
                        break;
                    default:
                        System.out.println("Opção inválida.");
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao acessar. API não disponível.");
        }
    }
}