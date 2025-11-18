
package com.mycompany.ifood;

import java.util.ArrayList;

public class Cliente {
    private String email;
    private String senha;
    
    public Cliente(){
    }
    public Cliente(String email, String senha){
        email = this.email;
        senha = this.senha;
    }
    Boolean e = false;
    //sequencia de caracteres
    String caractere = "@gmail.com";
    public void setEmail(String email) {
        
        //verifica se ha essa sequencia de caracteres no final da palavra
        if(this.email.endsWith(caractere)){
            this.email = email;
            // teste de email errado ao cadastrar
            e = true;
        }else{
            System.out.println("REESCRIVA SEU E MAIL com @gmail.com NO FINAL");
        }
    }
    boolean s = false;
    public void setSenha(String senha) {
        if(senha.length() >= 8){
            senha = this.senha;
            // teste de senha errada ao cadastrar
            s = true;
        }else{
            System.err.println("Erro: A senha deve ter pelo menos 8 caracteres.");
        }
    }
    private ArrayList<Cliente> listaCliente = new ArrayList();
    public void adicionarCliente(String email, String senha){
        Cliente novoCliente = new Cliente(email, senha);
        listaCliente.add(novoCliente);
    }
    
    
}
