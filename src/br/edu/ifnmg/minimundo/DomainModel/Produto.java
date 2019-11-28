/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifnmg.minimundo.DomainModel;

/**
 *
 * @author Joao Paulo
 */
public class Produto {
    private int id;
    private String descricao;
    private int statu;
    //Unidade de Compra
    private int uc;
    //Unidade de Venda
    private int uv;
    //Preço de Compra
    private float pc;
    //Preço de Venda
    private float pv;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getStatu() {
        return statu;
    }

    public void setStatu(int statu) {
        this.statu = statu;
    }

    public int getUc() {
        return uc;
    }

    public void setUc(int uc) {
        this.uc = uc;
    }

    public int getUv() {
        return uv;
    }

    public void setUv(int uv) {
        this.uv = uv;
    }

    public float getPc() {
        return pc;
    }

    public void setPc(float pc) {
        this.pc = pc;
    }

    public float getPv() {
        return pv;
    }

    public void setPv(float pv) {
        this.pv = pv;
    }
    
    
    
    
    @Override
    public String toString() {
        return "Produto{" + "id=" + id + ", descricao=" + descricao + '}';
    }
    
    
    
    
    
    
}
