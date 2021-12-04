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
import javax.swing.JOptionPane;

/**
 *
 * @author cleit
 */
public class ManterContatosPresenter {
    
    private final ManterContatosView view;
    private final ContatoCollection contatos;
    private final ListarContatosPresenter parent;
    
    public ManterContatosPresenter(String titulo, ContatoCollection contatos) {
        this.contatos = contatos;
        this.parent = null;
        this.view = new ManterContatosView();
        
        this.view.getBtnSalvar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnSalvar();
            }
        });
        
        this.view.getBtnFechar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                view.dispose();
            }
        });
        
        this.view.setVisible(true);
        this.view.setLocationRelativeTo(this.view.getParent());
        this.view.setTitle(titulo);
    }
    
    public ManterContatosPresenter(String titulo, ContatoCollection contatos, Contato contato, ListarContatosPresenter parent) {
        this.contatos = contatos;
        this.parent = parent;
        this.view = new ManterContatosView();
        
        this.view.getTxtNome().setText(contato.getNome());
        this.view.getTxtTelefone().setText(contato.getTelefone());
        
        this.view.getBtnSalvar().setText("Editar");
        this.view.getTxtNome().setEditable(false);
        this.view.getTxtTelefone().setEditable(false);
        
        this.view.getBtnSalvar().addActionListener((ActionEvent e) -> {
            btnEditar(contato);
        });
        
        this.view.getBtnFechar().addActionListener((ActionEvent e) -> {
            view.dispose();
        });
        
        this.view.setVisible(true);
        this.view.setLocationRelativeTo(this.view.getParent());
        this.view.setTitle(titulo);
    }
    
    private void btnSalvar() {
        String nome = this.view.getTxtNome().getText();
        String telefone = this.view.getTxtTelefone().getText();
        
        if(!nome.isBlank() && !telefone.isBlank()){
            if(validarTelefone(telefone)) {
                try{
                    this.contatos.addContato(new Contato(nome, telefone));
                    this.contatos.ordenar();
                
                    JOptionPane.showMessageDialog(view, "Contato salvo com sucesso!");
                } catch(RuntimeException erro) {
                    JOptionPane.showMessageDialog(view, erro.getMessage());
                }
            } else {
                JOptionPane.showMessageDialog(view, "Telefone inválido!");
            }
        } else {
            JOptionPane.showMessageDialog(view, "Preencha todos os campos!");
        }
    }
    
    private void btnSalvar(Contato antigo) {
        String nome = this.view.getTxtNome().getText();
        String telefone = this.view.getTxtTelefone().getText();
        
        if(!nome.isBlank() && !telefone.isBlank()){
            if(validarTelefone(telefone)) {
                Contato novo = new Contato(nome, telefone);
                try {
                    this.contatos.atualizarContato(antigo, novo);
                    this.contatos.ordenar();
                    
                    JOptionPane.showMessageDialog(view, "Contato atualizado com sucesso!");
                    
                    this.view.dispose();
                    this.parent.carregarTabela();
                } catch(RuntimeException erro) {
                    JOptionPane.showMessageDialog(view, erro.getMessage());
                }                
            } else {
                JOptionPane.showMessageDialog(view, "Telefone inválido!");
            }
        } else {
            JOptionPane.showMessageDialog(view, "Preencha todos os campos!");
        }
    }
    
    private void btnEditar(Contato con) {
        this.view.getBtnSalvar().setText("Salvar");
        this.view.getBtnFechar().setText("Cancelar");
        this.view.setTitle("Editar Contato");
        this.view.getTxtNome().setEditable(true);
        this.view.getTxtTelefone().setEditable(true);
        
        this.view.getBtnSalvar().addActionListener((ActionEvent e) -> {
            btnSalvar(con);
        });
        
        this.view.getBtnFechar().addActionListener((ActionEvent e) -> {
            btnCancelar(con);
        });
    }                                         
    
    private void btnCancelar(Contato contato) {
        this.view.getTxtNome().setText(contato.getNome());
        this.view.getTxtTelefone().setText(contato.getTelefone());
        
        this.view.getBtnSalvar().setText("Editar");
        this.view.getBtnFechar().setText("Fechar");
        this.view.setTitle("Editar Contato");
        this.view.getTxtNome().setEditable(false);
        this.view.getTxtTelefone().setEditable(false);
        
        this.view.getBtnSalvar().addActionListener((ActionEvent e) -> {
            btnEditar(contato);
        });

        this.view.getBtnFechar().addActionListener((ActionEvent e) -> {
            new ManterContatosPresenter(view.getTitle(), contatos, contato, parent);
        });
    }
    
    private boolean validarTelefone(String tel) {
        String pattern = "^\\([1-9]{2}\\)(?:[2-8]|9[1-9])[0-9]{3}\\-[0-9]{4}$";
        
        return tel.matches(pattern);
    }
}
