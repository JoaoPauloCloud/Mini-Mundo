/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifnmg.minimundo.DomainModel;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * @author Joao Paulo
 */
public class Usuario  {
    
    private int id;    
    private String nome;
    private String cpf;
    private String telefone;
    private String usuario;
    private String senha;    
    private Pattern regex_cpf = Pattern.compile("\\d{3}\\.?\\d{3}\\.?\\d{3}\\-?\\d{2}");
    
    
    
    
public Usuario() {
        this.id = 0;
        this.nome = "";
        this.cpf = "00000000000";
        this.telefone = "";
        this.usuario = "";
        this.senha = "";         


    }

public Usuario(int id, String nome, String cpf, String telefone, String usuario, String senha) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.telefone = telefone;
        this.usuario = usuario;
        this.senha = senha;
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
        if(nome.length() < 3)
            throw new ErroValidacaoException("O atributo nome deve ter no mínimo 3 caracteres!");
        this.nome = nome;
    }

    public String getCpf() {
        return  cpf.substring(0, 3)+"."+
                cpf.substring(3, 6)+"."+
                cpf.substring(6, 9)+"-"+
                cpf.substring(9, 11);
    }

    public void setCpf(String cpf) throws ErroValidacaoException {
        Matcher m = regex_cpf.matcher(cpf);
        if(m.matches())
            this.cpf = cpf.replace(".", "").replace("-", "");
        else
            throw new ErroValidacaoException("CPF Inválido!");
    }

   
    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefones) {
        this.telefone = telefone;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
    
    

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + this.id;
        hash = 53 * hash + Objects.hashCode(this.nome);
        hash = 53 * hash + Objects.hashCode(this.cpf);
        hash = 53 * hash + Objects.hashCode(this.telefone);
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
        final Usuario other = (Usuario) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.cpf, other.cpf)) {
            return false;
        }
        return true;
    }

    
    
    @Override
    public String toString() {
        return "Usuario{" + "id=" + id + ", nome=" + nome + '}';
    }
    
    
    
    
    
}
