/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package txttodocx;

/**
 *
 * @author Thiago
 */
public class Dados {

    private String serie;
    private int matricula;
    private String nome;
    private int senha;

    public Dados( int matricula, String nome, int senha) {
        this.matricula = matricula;
        this.nome = nome;
        this.senha = senha;
        
    }

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public int getMatricula() {
        return matricula;
    }

    public void setMatricula(int matricula) {
        this.matricula = matricula;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getSenha() {
        return senha;
    }

    public void setSenha(int senha) {
        this.senha = senha;
    }

}
