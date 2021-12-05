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
        var filePath = "src\\main\\java\\com\\gestaodecontatos\\docs\\contatos.dat";
        
        contatos.setContatos(Empacotamento.lerArquivo(filePath));
        
        view.getjMenuItemNovo().addActionListener((ActionEvent e) -> {
            new ManterContatosPresenter("Inclusão de Pesoa", contatos);
        });
        
        view.getjMenuItemListar().addActionListener((ActionEvent e) -> {
            new ListarContatosPresenter(contatos);
        });
        
        view.getjMenuItemFechar().addActionListener((ActionEvent e) -> {
            Empacotamento.gravarArquivo(contatos.getContatos(), filePath);
            view.dispose();
        });
        
        view.setVisible(true);
        view.setLocationRelativeTo(view.getParent());
    }
}
