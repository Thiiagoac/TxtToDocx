/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package txttodocx;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

/**
 *
 * @author Thiago
 */
public class TxtToDocX {

    public static ArrayList<Sala> salas2 = new ArrayList<>();
    public static ArrayList<Dados> listas = new ArrayList<>();
    public static File[] nomeArquivos;

    public static final String NomeDoUsuario = System.getProperty("user.name");
    
    //DEVE-SE FICAR ATENTO AO CAMINHO DA AREA DE TRABALHO CASO MUDE DE COMPUTADOR PARA COMPUTADOR
    public static final String CaminhoAreaDeTrabalho = "C:\\Users\\desir\\OneDrive\\Área de Trabalho\\";

    public static void main(String[] args) throws IOException {
        //Runtime.getRuntime().exec("cmd.exe /C start WINWORD.exe "+CaminhoAreaDeTrabalho+"teste1.docx");
        TxtToDocX z = new TxtToDocX();
        Sala x = new Sala();
        z.catchNameFiles();

        for (int i = 0; i < nomeArquivos.length; i++) {
            z.LoadTxt(nomeArquivos[i]);
        }

        for (int i = 0; i < nomeArquivos.length; i++) {
            System.out.println(nomeArquivos[i]);
        }

        for (int j = 0; j < salas2.size(); j++) {
            System.out.println("Turma: " + salas2.get(j).getNome());
            for (int i = 0; i < Sala.Dadosalas.size(); i++) {
                if (Sala.Dadosalas.get(i).getSerie().equals(salas2.get(j).getNome())) {
                    System.out.println("matricula:" + Sala.Dadosalas.get(i).getMatricula() + " Nome:" + Sala.Dadosalas.get(i).getNome()
                            + " Senha:" + Sala.Dadosalas.get(i).getSenha() + "\n");
                }
            }
        }
        z.WriteDocx();
    }

    public void catchNameFiles() {
        File Arquivo = null;

        try {
            Arquivo = new File(CaminhoAreaDeTrabalho);
            nomeArquivos = Arquivo.listFiles(new FileFilter() {
                public boolean accept(File pathname) {
                    return pathname.getName().toLowerCase().endsWith(".txt");
                }
            });

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void LoadTxt(File Arquivo) {
        //Dados novoDado = null;
        //File Arquivo = new File(CaminhoAreaDeTrabalho + "Teste.txt");
        if (Arquivo.exists()) {

            try {

                FileReader FR = new FileReader(Arquivo);
                BufferedReader BW = new BufferedReader(FR);

                String dados;
                String[] paraArray = new String[3];
                
                int matricula;
                int senha;
                String serie = Arquivo.getName();
                int pos = serie.lastIndexOf(".");
                if (pos > 0) {
                    serie = serie.substring(0, pos);
                }

                Sala novaSala = new Sala(serie); //CRIANDO SALA COM NOME DE TURMA
                salas2.add(novaSala); // ADICIONANDO SALA NA LISTA DE SALAS
                while ((dados = BW.readLine()) != null) {
                    paraArray = dados.split(";");

                    matricula = Integer.parseInt(paraArray[0]);
                    senha = Integer.parseInt(paraArray[2]);

                    Dados novoDado = new Dados(matricula, paraArray[1], senha);

                    novoDado.setSerie(serie); //ADICIONANDO TURMA AO DADO
                    novaSala.getDadosalas().add(novoDado); //ADICIONANDO DADOS A LISTA DE DADOS QUE ESTA EM SALA

                }

                BW.close();
                FR.close();

            } catch (Exception e) {
                System.out.println("ESSA MERDA N FOI");
                System.out.println(e.getMessage());
                e.printStackTrace();
            }

        } else {
            System.out.println("Nenhum arquivo de dados encontrado");
        }

    }

    public void WriteDocx() throws IOException {
        FileOutputStream fileOutPut = null;
        XWPFDocument document = new XWPFDocument();
        Sala z;
        try {
            fileOutPut = new FileOutputStream(new File(CaminhoAreaDeTrabalho + "testando.docx"));

            XWPFParagraph paragrafo1 = document.createParagraph();
            XWPFRun runPaRun1 = paragrafo1.createRun();

            runPaRun1.setText("");
            runPaRun1.addBreak();
            
            for (int j = 0; j < salas2.size(); j++) {
                runPaRun1.setText(salas2.get(j).getNome());
                runPaRun1.addBreak();
                for (int i = 0; i < Sala.Dadosalas.size(); i++) {
                    if (Sala.Dadosalas.get(i).getSerie().equals(salas2.get(j).getNome())) {
                        runPaRun1.setText(Sala.Dadosalas.get(i).getMatricula() + ";" + Sala.Dadosalas.get(i).getNome() + ";" + Sala.Dadosalas.get(i).getSenha());
                        runPaRun1.addBreak();
                        runPaRun1.addBreak();
                    }

                }
            }

            document.write(fileOutPut);

        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
    }
}
