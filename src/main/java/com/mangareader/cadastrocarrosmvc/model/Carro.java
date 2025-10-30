package com.mangareader.cadastrocarrosmvc.model;

public class Carro {

    private String modelo;
    private int ano;
    private String cor;

    public Carro(String modelo, int ano, String cor) {
        if (modelo == null || modelo.trim().isEmpty()) {
            throw new IllegalArgumentException("Modelo não pode ser vazio.");
        }
        if (ano < 1900 || ano > 2025) {
            throw new IllegalArgumentException("Ano inválido.");
        }
        this.modelo = modelo;
        this.ano = ano;
        this.cor = cor;
    }

    // Getters PÚBLICOS (necessários para a Tabela)
    public String getModelo() { return modelo; }
    public int getAno() { return ano; }
    public String getCor() { return cor; }

    @Override
    public String toString() {
        return "Modelo: " + modelo + ", Ano: " + ano + ", Cor: " + cor;
    }
}