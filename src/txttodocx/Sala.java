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
    private List<Dados> salas=  new ArrayList<>();
    private String nome;

    public Sala(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public List<Dados> getSalas() {
        return salas;
    }

    public void setSalas(List<Dados> salas) {
        this.salas = salas;
    }
    
    
         
}
