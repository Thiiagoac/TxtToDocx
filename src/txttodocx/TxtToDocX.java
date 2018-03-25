/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package txttodocx;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
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

    public static ArrayList<Dados> listas = new ArrayList<>();

    public static final String NomeDoUsuario = System.getProperty("user.name");

    public static final String CaminhoAreaDeTrabalho = "C:/Users/" + NomeDoUsuario + "/Desktop/";

    public static void main(String[] args) throws IOException {
        //Runtime.getRuntime().exec("cmd.exe /C start WINWORD.exe "+CaminhoAreaDeTrabalho+"teste1.docx");
        TxtToDocX z = new TxtToDocX();
        z.LoadTxt(new File(CaminhoAreaDeTrabalho + "Teste.txt"));
        z.LoadTxt(new File(CaminhoAreaDeTrabalho + "Teste1.txt"));
        z.LoadTxt(new File(CaminhoAreaDeTrabalho + "Teste2.txt"));
        z.LoadTxt(new File(CaminhoAreaDeTrabalho + "Teste3.txt"));
        z.LoadTxt(new File(CaminhoAreaDeTrabalho + "Teste4.txt"));
        for (int i = 0; i < listas.size(); i++) {
            System.out.println("matricula:" + listas.get(i).getMatricula() + " Nome:" + listas.get(i).getNome()
                    + " Senha:" + listas.get(i).getSenha() + "\n");
        }
        z.WriteDocx();

    }

    public void catchNameFiles() {
        File Arquivo = null;
        File[] nomeArquivos;
        
        try{
            Arquivo = new File(CaminhoAreaDeTrabalho);
            nomeArquivos = Arquivo.listFiles();
            
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

    public void LoadTxt(File Arquivo) {

        //File Arquivo = new File(CaminhoAreaDeTrabalho + "Teste.txt");
        if (Arquivo.exists()) {

            try {

                FileReader FR = new FileReader(Arquivo);
                BufferedReader BW = new BufferedReader(FR);

                String dados;
                String[] paraArray = new String[3];
                int matricula;
                int senha;
                String serie;
                
                while ((dados = BW.readLine()) != null) {
                    paraArray = dados.split(";");

                    matricula = Integer.parseInt(paraArray[0]);
                    senha = Integer.parseInt(paraArray[2]);

                    Dados novoDado = new Dados(matricula, paraArray[1], senha);
                    listas.add(novoDado);
                }
                BW.close();
                FR.close();

            } catch (Exception e) {
                System.out.println("Deu ruim");
                System.out.println(e.getMessage());
                e.printStackTrace();
            }

        } else {
            System.out.println("Nenhum arquivo de dados encontrado, por favor, "
                    + "use primeiro a função de SaveGame");
        }

    }

    public void WriteDocx() throws IOException {
        FileOutputStream fileOutPut = null;
        XWPFDocument document = new XWPFDocument();

        try {
            fileOutPut = new FileOutputStream(new File(CaminhoAreaDeTrabalho + "teste2.docx"));

            XWPFParagraph paragrafo1 = document.createParagraph();
            XWPFRun runPaRun1 = paragrafo1.createRun();

            runPaRun1.setText("");
            runPaRun1.addBreak();
            for (int i = 0; i < listas.size(); i++) {
                runPaRun1.setText(listas.get(i).getMatricula() + ";" + listas.get(i).getNome() + ";" + listas.get(i).getSenha());
                runPaRun1.addBreak();

            }

            document.write(fileOutPut);

        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
    }
}
