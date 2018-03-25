/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package txttodocx;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Thiago
 */
public class Sala {
    public static List<Dados> Dadosalas=  new ArrayList<>();
    private String nome;

    public Sala() {
    }

    public Sala(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Dados> getDadosalas() {
        return Dadosalas;
    }

    public void setDadosalas(List<Dados> Dadosalas) {
        this.Dadosalas = Dadosalas;
    }
   
         
}
