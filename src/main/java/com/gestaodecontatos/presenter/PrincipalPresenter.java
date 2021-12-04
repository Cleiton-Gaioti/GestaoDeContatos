/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gestaodecontatos.presenter;

import com.gestaodecontatos.collection.ContatoCollection;
import com.gestaodecontatos.factory.Empacotamento;
import com.gestaodecontatos.view.PrincipalView;
import java.awt.event.ActionEvent;

/**
 *
 * @author cleit
 */
public class PrincipalPresenter {  
    public static void main(String[] args) throws Exception {
        ContatoCollection contatos = new ContatoCollection();
        PrincipalView view = new PrincipalView();
        
        contatos.setContatos(Empacotamento.lerArquivo("contatos.dat"));
        
        view.getjMenuItemNovo().addActionListener((ActionEvent e) -> {
            addContato(contatos);
        });
        
        view.getjMenuItemListar().addActionListener((ActionEvent e) -> {
            listar(contatos);
        });
        
        view.getjMenuItemFechar().addActionListener((ActionEvent e) -> {
            fechar(view, contatos);
        });
        
        view.setVisible(true);
        view.setLocationRelativeTo(view.getParent());
    }
    
    private static void addContato(ContatoCollection contatos) {
        new ManterContatosPresenter("Inclusão de Pesoa", contatos);
    }

    private static void listar(ContatoCollection contatos) {
        new ListarContatosPresenter(contatos);
    }

    private static void fechar(PrincipalView view, ContatoCollection contatos) {
        Empacotamento.gravarArquivo(contatos.getContatos(), "contatos.dat");
        view.dispose();
    }
}
