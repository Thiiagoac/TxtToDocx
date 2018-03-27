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
    //public static ArrayList<Dados> listas = new ArrayList<>();
    public static File[] nomeArquivos;

    public static final String NomeDoUsuario = System.getProperty("user.name");

    //DEVE-SE FICAR ATENTO AO CAMINHO DA AREA DE TRABALHO CASO MUDE DE COMPUTADOR PARA COMPUTADOR
    public static final String CaminhoAreaDeTrabalho = "C:/Users/"+NomeDoUsuario+"/Desktop/";

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
            for (int i = 0; i < salas2.get(j).getDadosalas().size(); i++) {
                if (salas2.get(j).getDadosalas().get(i).getSerie().equals(salas2.get(j).getNome())) {

                    System.out.println("Matricula:" + salas2.get(j).getDadosalas().get(i).getMatricula()
                            + " Nome:" + salas2.get(j).getDadosalas().get(i).getNome()
                            + " Senha:" + salas2.get(j).getDadosalas().get(i).getSenha() + "\n");
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
            System.out.println("Erro ao pegar o nome da turma");
            // ex.printStackTrace();
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
                String matricula;
                
                String serie = Arquivo.getName();
                int pos = serie.lastIndexOf(".");
                if (pos > 0) {
                    serie = serie.substring(0, pos);
                }

                Sala novaSala = new Sala(serie); //CRIANDO SALA COM NOME DE TURMA
                salas2.add(novaSala); // ADICIONANDO SALA NA LISTA DE SALAS
                while ((dados = BW.readLine()) != null) {
                    paraArray = dados.split(";");

                    matricula = /*Integer.parseInt*/ (paraArray[0]);
                    //senha = /*Integer.parseInt*/(paraArray[2]);
                    Dados novoDado = new Dados(matricula, paraArray[1], paraArray[2]);

                    novoDado.setSerie(serie); //ADICIONANDO TURMA AO DADO
                    novaSala.getDadosalas().add(novoDado); //ADICIONANDO DADOS A LISTA DE DADOS QUE ESTA EM SALA

                }
                

                BW.close();
                FR.close();

            } catch (Exception e) {
                System.out.println("Erro ao carregar arquivo acima");
                System.out.println(e.getMessage());

            }

        } else {
            System.out.println("Nenhum arquivo de dados encontrado");
        }

    }

    public void WriteDocx() throws IOException {
        FileOutputStream fileOutPut = null;
        XWPFDocument document = new XWPFDocument();
        Sala z = null;
        try {
            fileOutPut = new FileOutputStream(new File(CaminhoAreaDeTrabalho + "Senhas.docx"));

            XWPFParagraph paragrafo1 = document.createParagraph();
            XWPFRun runPaRun1 = paragrafo1.createRun();

            for (int j = 0; j < salas2.size(); j++) {
                runPaRun1.setBold(true);
                runPaRun1.addBreak();
                runPaRun1.setText(salas2.get(j).getNome());
                runPaRun1.addBreak();

                runPaRun1.addBreak();

                for (int i = 0; i < salas2.get(j).getDadosalas().size(); i++) {
                    if (salas2.get(j).getDadosalas().get(i).getSerie().equals(salas2.get(j).getNome())) {
                        runPaRun1.setBold(false);
                        runPaRun1.setText(salas2.get(j).getDadosalas().get(i).getMatricula()
                                + ";" + salas2.get(j).getDadosalas().get(i).getNome()
                                + ";" + salas2.get(j).getDadosalas().get(i).getSenha());
                        runPaRun1.addBreak();
                    }

                }
            }

            document.write(fileOutPut);

        } catch (FileNotFoundException ex) {
            System.out.println("Erro ao escrever");
            //  ex.printStackTrace();

        }
    }
}
