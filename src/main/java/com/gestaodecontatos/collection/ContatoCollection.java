/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gestaodecontatos.collection;

import com.gestaodecontatos.model.Contato;
import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author cleit
 */
public class ContatoCollection {
    private ArrayList<Contato> contatos;

    public ContatoCollection() {
        this.contatos = new ArrayList<>();
    }

    public void addContato(Contato contato) {
        if (contatos.contains(contato)) {
            throw new RuntimeException("Contato já cadastrado!");
        } else if (contato != null) {
            this.contatos.add(contato);
        } else {
            throw new RuntimeException("Forneça uma instância válida de um contato!");
        }
    }
    
    public void atualizarContato(Contato antigo, Contato novo) {
        if (contatos.contains(novo)) {
            throw new RuntimeException("Contato já cadastrado!");       
        } else if (novo != null) {
            for(Contato c: this.contatos) {
                if(c.equals(antigo)) {
                    c.setNome(novo.getNome());
                    c.setTelefone(novo.getTelefone());
                }
            }
        } else {
            throw new RuntimeException("Forneça uma instância válida de um contato!");
        }
    }

    public ArrayList<Contato> getContatos() {
        return this.contatos;
    }

    public void setContatos(ArrayList<Contato> contatos) {
        this.contatos = contatos;
    }
    
    public void ordenar() {
        Collections.sort(this.contatos);
    }  
}
