package com.example.kathiani.utils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Utils {

    public static void salvarEmArquivo(String formattedString, String nomeArquivo) {
        String path = "/home/MIdVAL/ManagedData/nomeArquivo.txt";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
            writer.write(formattedString);
            System.out.println("Texto salvo com sucesso no arquivo: " + nomeArquivo);
        } catch (IOException e) {
            System.err.println("Erro ao salvar o arquivo: " + e.getMessage());
        }
    }
    
}
