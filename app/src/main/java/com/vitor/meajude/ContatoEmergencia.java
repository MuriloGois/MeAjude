package com.vitor.meajude;

public class ContatoEmergencia { //ESSE OBJETO REPRESENTA UM CONTATO DE EMERGÊNCIA.

    // ELA CONTÉM O NOME E NUM. TELEFONE DA PESSOA.


    private String nome;
    private String telefone;


    public void setNome(String nome){
        this.nome = nome;
    }

    public void setTelefone(String telefone){
        this.telefone = telefone;
    }


    public String getNome(){
        return nome;
    }

    public String getTelefone(){
        return telefone;
    }

    @Override
    public String toString() {
        return("MEU NOME :" + getNome() + "\n" + " MEU TELEFONE :" + getTelefone() + "\n");
    }
}
