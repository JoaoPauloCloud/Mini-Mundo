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
    private String nome;
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

    private Pattern regex_cnpj
            = Pattern.compile("\\d{2}\\.?\\d{3}\\.?\\d{3}\\.?\\d{4}\\-?\\d{2}");
    
    private Pattern regex_email = Pattern.compile("^[a-za-z0-9_+&*-]+(?:\\."+ 
                            "[a-za-z0-9_+&*-]+)*@" + 
                            "(?:[a-zA-Z0-9-]+\\.)+[a-z" + 
                            "A-Z]{2,7}$");
    

    private Pattern regex_cep = Pattern.compile("\\d{5}\\-?\\d{3}");

    public Fornecedor() {
        this.id = 0;
        this.nome = "";
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
        this.cep = "00000000";

    }

    public Fornecedor(int id, String nome, String cnpj, String rs, String endereço, String email, String bairro, String estado, String rua, String cidade, int numero, String complemento, String cep) {
        this.id = id;
        this.nome = nome;
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

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) throws ErroValidacaoException {
        if (nome.length() < 3) {
            throw new ErroValidacaoException("O campo nome deve ter no mínimo 3 caracteres!");
        }
        this.nome = nome;
    }

    public String getcnpj() {
        return (cnpj.substring(0, 2) + "."
                + cnpj.substring(2, 5) + "."
                + cnpj.substring(5, 8) + "."
                + cnpj.substring(8, 12) + "-"
                + cnpj.substring(12, 14));
    }

    public void setcnpj(String cnpj) throws ErroValidacaoException {
        Matcher m = regex_cnpj.matcher(cnpj);
        if (m.matches()) {
            this.cnpj = cnpj.replace(".", "").replace("-", "");
        } else {
            throw new ErroValidacaoException("CNPJ Inválido!");
        }
    }

    public String getRs() {
        return rs;
    }

    public void setRs(String rs) throws ErroValidacaoException {
        if (rs.length() < 3) {
            throw new ErroValidacaoException("* O campo Razão Social deve ter no mínimo 3 caracteres!");
        }
        this.rs = rs;
    }

    public void setBairro(String bairro) throws ErroValidacaoException {
        if (bairro.length() < 3) {
            throw new ErroValidacaoException(" * Campo Bairro Obrigatorio");
        }
        this.bairro = bairro;
    }

    public String getBairro() {
        return bairro;
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

    public void setRua(String rua) throws ErroValidacaoException {
        if (rua.length() < 2) {
            throw new ErroValidacaoException(" * Campo Obrigatorio");
        }
        this.rua = rua;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) throws ErroValidacaoException {
        if (cidade.length() < 3) {
            throw new ErroValidacaoException(" * Campo Cidade Obrigatorio");
        }
        this.cidade = cidade;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) throws ErroValidacaoException {
        if ((numero < 1) && (numero > 999999)) {
            throw new ErroValidacaoException(" * Campo Numero é Obrigatorio");
        }
        this.numero = numero;

    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getCep() {
        return cep.substring(0, 5) + "-"
                + cep.substring(5, 8);

    }

    public void setCep(String cep) throws ErroValidacaoException {
        Matcher m = regex_cep.matcher(cep);
        if (m.matches()) {
            this.cep = cep.replace("-", "");
        } else {
            throw new ErroValidacaoException("Cep Inválido!");
        }
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) throws ErroValidacaoException {
        Matcher m = regex_email.matcher(email);
        if (m.matches()) {
            this.email = email;
        } else {
            throw new ErroValidacaoException("Email Inválido!");
        }
    }
    
    public String toString(){
        return this.nome;
    }

}
