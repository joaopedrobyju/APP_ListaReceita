package com.joao.mendes;

public class Receita {

    private String id;

    public String getId() {return id;}
    public void setId(String id) {this.id = id;}

    private String nome;
    private String ingredientes;

    private String modoPreparo;

    public Receita() {}

    public Receita(String nome, String ingredientes, String modoPreparo) {
        this.nome = nome;
        this.ingredientes = ingredientes;
        this.modoPreparo = modoPreparo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(String ingredientes) {
        this.ingredientes = ingredientes;
    }

    public String getModoPreparo() {
        return modoPreparo;
    }

    public void setModoPreparo(String modoPreparo) {
        this.modoPreparo = modoPreparo;
    }

    @Override
    public String toString() {
        return this.nome;
    }
}
