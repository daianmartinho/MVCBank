/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Daian
 */
public class Conta {

    //verifique que a conta tem uma agencia, e a agencia tem varias contas
    //fiz assim pra facilitar na hora da busca e criação dos objetos
    
    private Agencia agencia;
    private String num_conta;
    private TipoDeConta tipo;
    private double saldo;
    private List<Operacao> operacoes= new ArrayList();

    public Agencia getAgencia() {
        return agencia;
    }

    public void setAgencia(Agencia agencia) {
        this.agencia = agencia;       
        //sempre que uma agencia é setada pra conta,
        //essa conta é colocada na lista de contas da agencia        
        this.agencia.getContas().add(this);
    }

    public String getNum_conta() {
        return num_conta;
    }

    public void setNum_conta(String num_conta) {
        this.num_conta = num_conta;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public TipoDeConta getTipo() {
        return tipo;
    }

    public void setTipo(TipoDeConta tipo) {
        this.tipo = tipo;
    }

    public List<Operacao> getOperacoes() {
        return operacoes;
    }

    public void setOperacoes(List<Operacao> operacoes) {
        this.operacoes = operacoes;
    }
    public void addOperacao(Operacao o){
        this.operacoes.add(o);
    }

}
