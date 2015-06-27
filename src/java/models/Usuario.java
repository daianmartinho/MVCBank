/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.util.List;

/**
 *
 * @author Daian
 */
public class Usuario {

    int id;
    String nome;
    String CPF;
    String telefone;
    String endereço;
    List<Conta> contas;
    //String senha; tirei a senha pra não ficar passando ela na sessão

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEndereço() {
        return endereço;
    }

    public void setEndereço(String endereço) {
        this.endereço = endereço;
    }

    
    public String getCPF() {
        return CPF;
    }

    public void setCPF(String CPF) {
        this.CPF = CPF;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public List<Conta> getContas() {
        return contas;
    }

    public void setContas(List<Conta> conta) {
        this.contas = conta;
    }

    public Conta getConta(int id) {
        for (Conta c : contas) {
            if(c.getTipo().id==id){
                return c;
            }
        }
        return null;
    }

}
