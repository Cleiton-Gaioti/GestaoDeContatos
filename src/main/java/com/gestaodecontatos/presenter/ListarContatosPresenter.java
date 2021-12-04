/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gestaodecontatos.presenter;

import com.gestaodecontatos.collection.ContatoCollection;
import com.gestaodecontatos.model.Contato;
import com.gestaodecontatos.view.ListarContatosView;
import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author cleit
 */
public class ListarContatosPresenter {
    private final ListarContatosView view;
    private final ContatoCollection contatos;
    private final DefaultTableModel tableModelContatos;
    
    public ListarContatosPresenter(ContatoCollection contatos) {
        this.contatos = contatos;
        this.view = new ListarContatosView();
        
        this.tableModelContatos = new DefaultTableModel(
            new Object[][]{},
            new String[]{"Nome", "Telefone"}
        ) {
            @Override
		public boolean isCellEditable(final int row, final int column) {
			return false;
		}
        };
        
        this.view.getCbOrdenar().addActionListener((ActionEvent e) -> {
            ordenar();
        });
        
        this.view.getBtnVisualizar().addActionListener((ActionEvent e) -> {
            visualizar();
            
            this.view.getBtnVisualizar().setEnabled(false);
            this.view.getBtnExcluir().setEnabled(false);
        });
        
        this.view.getBtnExcluir().addActionListener((ActionEvent e) -> {
            if(excluir()) {
                this.view.getBtnVisualizar().setEnabled(false);
                this.view.getBtnExcluir().setEnabled(false);
            }
        });
        
        this.view.getBtnFechar().addActionListener((ActionEvent e) -> {
            view.dispose();
        });
        
        this.view.getTblPessoas().getSelectionModel().addListSelectionListener((ListSelectionEvent e) -> {
            view.getBtnVisualizar().setEnabled(true);
            view.getBtnExcluir().setEnabled(true);
        });
        
        carregarTabela();
             
        this.view.getBtnVisualizar().setEnabled(false);
        this.view.getBtnExcluir().setEnabled(false);
        
        this.view.setLocationRelativeTo(this.view.getParent());
        this.view.setVisible(true);
    }
    
    
    public void carregarTabela(){
        this.tableModelContatos.setNumRows(0);
        
        this.view.getTblPessoas().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        
        this.contatos.getContatos().forEach(c -> {
            tableModelContatos.addRow(new Object[]{c.getNome(), c.getTelefone()});
        });
        
        this.view.getLbTotal().setText(String.valueOf(this.tableModelContatos.getRowCount()));
        this.view.getTblPessoas().setModel(this.tableModelContatos);  
    }
    
    
    private void ordenar() {                                          
        TableRowSorter sorter = new TableRowSorter(this.tableModelContatos);
        this.view.getTblPessoas().setRowSorter(sorter);
        
        if(this.view.getCbOrdenar().isSelected()) {
            sorter.toggleSortOrder(1);
        } else {
            sorter.toggleSortOrder(0);
        }
    } 
    
    
    private void visualizar() {                
        int linha = view.getTblPessoas().getSelectedRow();
        
        if(linha != -1) {
            String nome = view.getTblPessoas().getValueAt(linha, 0).toString();
            String telefone = view.getTblPessoas().getValueAt(linha, 1).toString();
            
            new ManterContatosPresenter("Visualizar Contato", contatos, new Contato(nome, telefone), this);
            
            carregarTabela();
        } else {
            JOptionPane.showMessageDialog(view, "Selecione uma linha!");
        }
    }   
    

    private boolean excluir() {                                           
        int linha = view.getTblPessoas().getSelectedRow();
        
        if(linha != -1) {
            String nome = view.getTblPessoas().getValueAt(linha, 0).toString();
            String telefone = view.getTblPessoas().getValueAt(linha, 1).toString();
            
            String[] options = {"Sim", "Não"};

            String mensagem = "Deseja realmente excluir o contato " + nome + "?";
            int a = JOptionPane.showOptionDialog(null, mensagem, "Exclusão de Contato", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
            
            if(a == JOptionPane.YES_OPTION) {
                contatos.remove(new Contato(nome, telefone));
                carregarTabela();
                
                return true;
            }
        } else {
            JOptionPane.showMessageDialog(view, "Selecione uma linha");
        }
        
        return false;
    }
    
}
