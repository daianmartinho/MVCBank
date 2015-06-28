/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

/**
 *
 * @author Daian
 */
public class Operacao {

    private int id;
    private Conta conta;
    private TipoDeOperacao tipo;
    private double valor;
    private Timestamp data;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public TipoDeOperacao getTipo() {
        return tipo;
    }

    public void setTipo(TipoDeOperacao tipo) {
        this.tipo = tipo;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public String getData() {
        return new SimpleDateFormat("dd/MM/yyyy hh:mm:ss").format(data);
    }

    public Timestamp getTimestamp() {
        return data;
    }

    public void setData(Timestamp data) {
        this.data = data;
    }

    public Conta getConta() {
        return conta;
    }

    public void setConta(Conta conta) {
        this.conta = conta;
    }

    public String getDescricao() {
        return this.getTipo().getDescricao()+" "+this.getConta().getAgencia().getNum_agencia()+"/"+this.getConta().getNum_conta()+"-"+this.getTipo().getId();
    }

}
