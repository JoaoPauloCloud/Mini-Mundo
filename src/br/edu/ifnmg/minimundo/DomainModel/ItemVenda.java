/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifnmg.minimundo.DomainModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author Joao Paulo
 */
public class ItemVenda {
    private int id;
    private Cliente cliente_id;
    private Usuario usuario_id;
    private List<Produto> produtos;
    //private data date;
    private float valor;
    private int quantidade;
    private int unidade;
    //Valor total
    private float vt;  
    
    public ItemVenda (){
     this.produtos = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Cliente getCliente_id() {
        return cliente_id;
    }

    public void setCliente_id(Cliente cliente_id) {
        this.cliente_id = cliente_id;
    }

    public Usuario getUsuario_id() {
        return usuario_id;
    }

    public void setUsuario_id(Usuario usuario_id) {
        this.usuario_id = usuario_id;
    }

    
    public List<Produto> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<Produto> produtos) {
        this.produtos = produtos;
    }
    
    

   

    public float getValor() {
        return valor;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public int getUnidade() {
        return unidade;
    }

    public void setUnidade(int unidade) {
        this.unidade = unidade;
    }

    public float getVt() {
        return vt;
    }

    public void setVt(float vt) {
        this.vt = vt;
    }
    
    
    
    
    
}
