/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minimundo.DomainModel;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Joao Paulo
 */
public class Fornecedor {
    
    private String cnpj;
    private String rs;
    private List<String> endereço;
    private String email;
    private Pattern regex_cnpj = 
            Pattern.compile("\\d{2}\\.?\\d{3}\\.?\\d{3}\\.?\\d{4}\\-?\\d{2}");

    public Fornecedor() {
        this.cnpj = "00000000000000";
        this.rs = "";
        this.endereço = new ArrayList<>();
        this.email = "";
        
        
    }
    
    public Fornecedor(String cnpj, String rs, String endereço, String email) {
        this.cnpj = cnpj;
        this.rs = rs;
        this.endereço = new ArrayList<>();
        this.email = email;
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
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}
