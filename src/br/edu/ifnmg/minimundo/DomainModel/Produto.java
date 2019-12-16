/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifnmg.minimundo.DomainModel;

import java.util.Objects;

/**
 *
 * @author Joao Paulo
 */
public class Produto {
    private int id;
    private String descricao;
    private String statu;
    //Unidade de Compra
    private String uc;
    //Unidade de Venda
    private String uv;
    //Preço de Compra
    private float pc;
    //Preço de Venda
    private float pv;
    private Fornecedor fornecedor;
    private String nome;
    private int idfornecedor;

    public Produto() {
        this.id = 0;
        this.descricao = "";
        this.statu = "";
        this.uc = "";
        this.uv = "";
        this.pc = 0;
        this.pv = 0;
        this.fornecedor = fornecedor;
        this.nome = "";
        this.id = 0;
        
        
    }

    public Produto(int id, String descricao, String statu, String uc, String uv, float pc, float pv, Fornecedor fornecedor, String nome,int idfornecedor) {
        this.id = id;
        this.descricao = descricao;
        this.statu = statu;
        this.uc = uc;
        this.uv = uv;
        this.pc = pc;
        this.pv = pv;
        this.fornecedor = fornecedor;
        this.nome = nome;
        this.idfornecedor = idfornecedor;
    }
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }   

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao)throws ErroValidacaoException {
        if(descricao.length() < 3)throw new ErroValidacaoException ("O campo descriçao deve ter no mínimo 3 caracteres!");
        this.descricao = descricao;
        
     }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getStatu() {
        return statu;
    }

    public void setStatu(String statu) {
        this.statu = statu;
    }
      

  
    public String getUc() {
        return uc;
    }

    public void setUc(String uc) throws ErroValidacaoException {
        if(uc.length() < 3) throw new ErroValidacaoException("O campo Unidade de Compra deve ter no mínimo 3 caracteres!");
        this.uc = uc;
    }

    public String getUv() {
        return uv;
    }

    public void setUv(String uv)throws ErroValidacaoException {
        if(uv.length() < 3) throw new ErroValidacaoException("O campo Unidade de Venda deve ter no mínimo 3 caracteres!");
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

    public Fornecedor getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(Fornecedor fornecedor) {
        this.fornecedor = fornecedor;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 79 * hash + this.id;
        hash = 79 * hash + Objects.hashCode(this.descricao);
        hash = 79 * hash + Objects.hashCode(this.statu);
        hash = 79 * hash + Objects.hashCode(this.uc);
        hash = 79 * hash + Objects.hashCode(this.uv);
        hash = 79 * hash + Float.floatToIntBits(this.pc);
        hash = 79 * hash + Float.floatToIntBits(this.pv);
        hash = 79 * hash + Objects.hashCode(this.fornecedor);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Produto other = (Produto) obj;
        if (this.id != other.id) {
            return false;
        }
        if (this.statu != other.statu) {
            return false;
        }
        if (Float.floatToIntBits(this.pc) != Float.floatToIntBits(other.pc)) {
            return false;
        }
        if (Float.floatToIntBits(this.pv) != Float.floatToIntBits(other.pv)) {
            return false;
        }
        if (!Objects.equals(this.descricao, other.descricao)) {
            return false;
        }
        if (!Objects.equals(this.uc, other.uc)) {
            return false;
        }
        if (!Objects.equals(this.uv, other.uv)) {
            return false;
        }
        if (!Objects.equals(this.fornecedor, other.fornecedor)) {
            return false;
        }
        return true;
    }

    public int getIdfornecedor() {
        return idfornecedor;
    }

    public void setIdfornecedor(int idfornecedor) {
        this.idfornecedor = idfornecedor;
    }

    @Override
    public String toString() {
        return this.descricao;
    }

   
    
    
    
    
}
