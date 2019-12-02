
package br.edu.ifnmg.minimundo.DomainModel;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Cliente {
    
    private int id;    
    private String nome;
    private String cpf;
    private String email;
    private List<String> telefones;
    
    //endereço
    private String bairro;
    private Estado estado;
    private String rua;
    private String cidade;
    private int numero;
    private String complemento;
    private String cep;
    private int statu;
     
    private Pattern regex_cpf = Pattern.compile("\\d{3}\\.?\\d{3}\\.?\\d{3}\\-?\\d{2}");
    
    
    
    
public Cliente() {
        this.id = 0;
        this.nome = "";
        this.cpf = "00000000000";       
        this.telefones = new ArrayList<>();
        this.email = "";
        this.statu = 0;
        //Endereço
        this.bairro = "";
        this.cidade = "";
        this.complemento = "";
        this.estado = Estado.AC;
        this.numero = 0;
        this.rua = "";
        this.cep = "";
        
    }


public Cliente(int id, String nome, String cpf, List<String> telefones,String email,int statu,String bairro,String estado,String rua,String cidade,int numero,String complemento,String cep) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;        
        this.telefones = telefones;
        this.email = email;
        this.statu = statu;
        //Endereço
        this.bairro = bairro;
        this.cidade = cidade;
        this.complemento = complemento;
        this.estado = Estado.MG;
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

   
    //buscar,alterar o nome do bairro
    public void setBairro(String bairro) {
        this.bairro = bairro;
    }
         
    public String getBairro() {
        return bairro;
    }
    
    //buscar,alterar o nome da cidade
    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }
    
    //buscar,alterar o nome complemento

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }
    //buscar,alterar o nome do estado
    public Estado getEstado() {
        return estado;
    }

   
    public void setEstado(Estado estado) {
        this.estado = estado;
    }
    
    //buscar,alterar o nome da rua
    public String getRua() {
        return rua;
    }

    public void setRua(String rua) throws ErroValidacaoException {
        if(nome.length() < 2)
            throw new ErroValidacaoException(" * Campo Obrigatorio");
        this.rua = rua;
    }
    //buscar,alterar o numero
    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
      
    
    
    //buscar ou alterar nome do cliente
     public String getNome() {
        return nome;
    }

    public void setNome(String nome) throws ErroValidacaoException {
        if(nome.length() < 3)
            throw new ErroValidacaoException("O atributo nome deve ter no mínimo 3 caracteres!");
        this.nome = nome;
    }
//Buscando, alterando e verificando se cpf é valido
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


    public List<String> getTelefones() {
        return telefones;
    }

    public void setTelefones(List<String> telefones) {
        this.telefones = telefones;
    }
    
    public void addTelefone(String telefone){
        if(! this.telefones.contains(telefone))
            this.telefones.add(telefone);
    }
    
    public void removeTelefone(String telefone){
        if(this.telefones.contains(telefone))
            this.telefones.remove(telefone);
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public int getStatu() {
        return statu;
    }

    public void setStatu(int statu) {
        this.statu = statu;
    }
    
    

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + this.id;
        hash = 53 * hash + Objects.hashCode(this.nome);
        hash = 53 * hash + Objects.hashCode(this.cpf);
        hash = 53 * hash + Objects.hashCode(this.telefones);
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
        final Cliente other = (Cliente) obj;
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
        return "Cliente{" + "id=" + id + ", nome=" + nome + '}';
    }











}

