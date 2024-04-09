/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.dev.wscom.estoqueapp;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author wanderlei
 */
public class LeitorCSV {

    protected static ArrayList< HashMap<String, String>> produtos = new ArrayList< HashMap<String, String>>();

    public static ArrayList< HashMap<String, String>> leitorCSV(String caminho) throws FileNotFoundException {

        // Criar um scanner para o arquivo CSV
        Scanner scanner = new Scanner(new File(caminho));

        //limpar os dados no arraylist
        produtos.clear();

        // Ignorar cabeçalho
        scanner.nextLine();

        // Ler cada linha do arquivo
        while (scanner.hasNextLine()) {

            // Obter a próxima linha
            String linha = scanner.nextLine();

            // Dividir a linha em tokens
            String[] tokens = linha.split(";");

            // Ler os dados de cada token        
            var produto = new HashMap<String, String>();

            produto.put("descricao", tokens[0]);
            produto.put("preco_custo", tokens[1]);
            produto.put("qtde_atual", tokens[2]);
            produto.put("qtde_maxima", tokens[3]);
            produto.put("qtde_minima", tokens[4]);

            produtos.add(produto);

        }

        // Fechar o scanner
        scanner.close();

        return produtos;
    }

    public static boolean cadastrar(HashMap<String, String> produto) {
        return produtos.add(produto);
    }

    public static boolean excluir(HashMap<String, String> produto) {
        return produtos.remove(produto);
    }

    public static boolean atualizar(String descricao, HashMap<String, String> produto) {

        boolean achou = false;

        for (int i = 0; i < produtos.size(); i++) {

            var p = produtos.get(i);

            if (p.get("descricao").toUpperCase().equals(descricao.toUpperCase())) {

                p.replace("descricao", produto.get("descricao"));
                p.replace("preco_custo", produto.get("preco_custo"));
                p.replace("qtde_atual", produto.get("qtde_atual"));
                p.replace("qtde_maxima", produto.get("qtde_maxima"));
                p.replace("qtde_minima", produto.get("qtde_minima"));

                achou = true;
            }

            if (achou) {
                break;
            }
        }

        return achou;
    }

    public static void salvarNoArquivo(String caminho) throws FileNotFoundException {

        try {
            // Abre o arquivo para gravação
            FileOutputStream arquivo = new FileOutputStream(caminho);

            produtos.forEach(p -> {
                try {
                    // Escreve no arquivo
                    String texto = p.get("descricao") + ";" + p.get("preco_custo") + ";" + p.get("qtde_atual") + ";" + p.get("qtde_maxima") + ";" + p.get("qtde_minima") + "\n";
                    arquivo.write(texto.getBytes());
                } catch (IOException ex) {
                    Logger.getLogger(LeitorCSV.class.getName()).log(Level.SEVERE, null, ex);
                }
            });

            // Fecha o arquivo
            arquivo.close();

            System.out.println("Texto gravado com sucesso no arquivo.");
        } catch (IOException e) {
            System.out.println("Erro ao gravar no arquivo: " + e.getMessage());
        }

    }
}
