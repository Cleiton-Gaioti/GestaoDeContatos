/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gestaodecontatos.presenter;

import com.gestaodecontatos.collection.ContatoCollection;
import com.gestaodecontatos.model.Contato;
import com.gestaodecontatos.view.ManterContatosView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JOptionPane;

/**
 *
 * @author cleit
 */
public class ManterContatosPresenter {
    
    private final ManterContatosView view;
    private final ContatoCollection contatos;
    private final ListarContatosPresenter parent;
    private final String titulo;
    
    public ManterContatosPresenter(String titulo, ContatoCollection contatos) {
        this.contatos = contatos;
        this.parent = null;
        this.view = new ManterContatosView();
        this.titulo = titulo;
        
        this.view.getBtnSalvar().addActionListener((ActionEvent e) -> {
            salvar();
        });
        
        this.view.getBtnFechar().addActionListener((ActionEvent e) -> {
            view.dispose();
        });
        
        this.view.setVisible(true);
        this.view.setLocationRelativeTo(this.view.getParent());
        this.view.setTitle(titulo);
    }
    
    public ManterContatosPresenter(String titulo, ContatoCollection contatos, Contato contato, ListarContatosPresenter parent) {
        this.contatos = contatos;
        this.parent = parent;
        this.view = new ManterContatosView();
        this.titulo = titulo;
        
        setModoVisualizacao(contato);
        
        this.view.setVisible(true);
        this.view.setLocationRelativeTo(this.view.getParent());
        this.view.setTitle(titulo);
    }
    
    private void salvar() {
        String nome = this.view.getTxtNome().getText();
        String telefone = this.view.getTxtTelefone().getText();
        
        try{
            this.contatos.addContato(new Contato(nome, telefone));
            this.contatos.ordenar();

            JOptionPane.showMessageDialog(view, "Contato salvo com sucesso!");
        } catch(RuntimeException erro) {
            JOptionPane.showMessageDialog(view, "Preencha os campos corretamente!\n" + erro.getMessage());
        }
    }
    
    private void salvar(Contato antigo) {
        String nome = this.view.getTxtNome().getText();
        String telefone = this.view.getTxtTelefone().getText();
        
        try {
            this.contatos.atualizarContato(antigo, new Contato(nome, telefone));
            this.contatos.ordenar();

            JOptionPane.showMessageDialog(view, "Contato atualizado com sucesso!");

            this.view.dispose();
            this.parent.carregarTabela();
        } catch(RuntimeException erro) {
            JOptionPane.showMessageDialog(view, "Preencha os campos corretamente!\n" + erro.getMessage());
        }                
    }
    
    private void setModoEdicao(Contato contato) {
        this.view.getBtnSalvar().setText("Salvar");
        this.view.getBtnFechar().setText("Cancelar");
        
        this.view.setTitle("Editar Contato");
        this.view.getTxtNome().setEditable(true);
        this.view.getTxtTelefone().setEditable(true);
        
        limparAcoes(view.getBtnSalvar());  
        this.view.getBtnSalvar().addActionListener((ActionEvent e) -> {
            salvar(contato);
        });
        
        limparAcoes(view.getBtnFechar());  
        this.view.getBtnFechar().addActionListener((ActionEvent e) -> {
            new ManterContatosPresenter(titulo, contatos, contato, parent);
            view.dispose();
        });
    }                                         
   
    
    private void setModoVisualizacao(Contato contato) {
        this.view.getTxtNome().setText(contato.getNome());
        this.view.getTxtTelefone().setText(contato.getTelefone());
        
        this.view.getBtnSalvar().setText("Editar");
        this.view.getBtnFechar().setText("Fechar");
        this.view.setTitle("Editar Contato");
        this.view.getTxtNome().setEditable(false);
        this.view.getTxtTelefone().setEditable(false);
        
        limparAcoes(view.getBtnSalvar());   
        this.view.getBtnSalvar().addActionListener((ActionEvent e) -> {
            setModoEdicao(contato);
        });

        limparAcoes(view.getBtnFechar());
        this.view.getBtnFechar().addActionListener((ActionEvent e) -> {
            view.dispose();
        });
    }
    
    private void limparAcoes(JButton button) {
        for(ActionListener al: button.getActionListeners()) {
            this.view.getBtnFechar().removeActionListener(al);
        }
        
        button.repaint();
        button.revalidate();
    }
}
