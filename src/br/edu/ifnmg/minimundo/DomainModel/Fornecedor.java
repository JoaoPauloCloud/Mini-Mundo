/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifnmg.minimundo.DomainModel;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Joao Paulo
 */
public class Fornecedor {
    
    private int id;
    private String cnpj;
    //rs = Razão Social
    private String rs;
    private String email;
    //Endereço
    private String bairro;
    private Estado estado;
    private String rua;
    private String cidade;
    private int numero;
    private String complemento;
    private String cep;
    
    private Pattern regex_cnpj = 
            Pattern.compile("\\d{2}\\.?\\d{3}\\.?\\d{3}\\.?\\d{4}\\-?\\d{2}");

    public Fornecedor() {
        this.cnpj = "00000000000000";
        this.rs = "";
        this.email = "";
        //Endereço
        this.bairro = "";
        this.cidade = "";
        this.complemento = "";
        this.estado = Estado.AC;
        this.numero = 0;
        this.rua = "";
        this.cep = "";
        
        
        
    }
    
    public Fornecedor(String cnpj, String rs, String endereço, String email,String bairro,String estado,String rua,String cidade,int numero,String complemento,String cep) {
        this.cnpj = cnpj;
        this.rs = rs;
        this.email = email;
        //Endereço
        this.bairro = bairro;
        this.cidade = cidade;
        this.complemento = complemento;
        this.estado = Estado.AC;
        this.numero = numero;
        this.rua = rua;
        this.cep = cep;
        
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    
    
    public String getcnpj() {
        return (cnpj.substring(0, 2) + "." + 
                cnpj.substring(2, 5) + "." +
                cnpj.substring(5, 8) + "." + 
                cnpj.substring(8, 12) + "-" +
                cnpj.substring(12, 14));
    }
    
    public void setcnpj(String cnpj) throws ErroValidacaoException {
        Matcher m = regex_cnpj.matcher(cnpj);
        if(m.matches())
            this.cnpj = cnpj.replace(".", "").replace("-", "");
        else
            throw new ErroValidacaoException("CNPJ Inválido!");
    }

   /* public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }
*/
    public String getRs() {
        return rs;
    }

    public void setRs(String rs) {
        this.rs = rs;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Pattern getRegex_cnpj() {
        return regex_cnpj;
    }

    public void setRegex_cnpj(Pattern regex_cnpj) {
        this.regex_cnpj = regex_cnpj;
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}
