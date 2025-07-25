package br.com.files;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileManipulator {

    public void manipulate() {

        String inputPath = "input.txt";

        String outputPath = "output.txt";

        List<String> linhasFiltradas = new ArrayList<>();

        try(BufferedReader reader = new BufferedReader(new FileReader(inputPath));

            BufferedWriter writer = new BufferedWriter(new FileWriter(outputPath)) ) {

            String linha;

            while ((linha = reader.readLine()) != null) {

                if (!linha.trim().isEmpty()) {

                    linhasFiltradas.add(linha);

                }

            }
            for (int i = 0; i < linhasFiltradas.size(); i++) {

                writer.write(linhasFiltradas.get(i));

                if (i < linhasFiltradas.size() - 1) {

                    writer.newLine();

                }

            }

        } catch (IOException e) {

            System.err.println("Erro ao processar o arquivo: " + e.getMessage());

        }

    }
}

