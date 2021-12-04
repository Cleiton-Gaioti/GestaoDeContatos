package com.gestaodecontatos.factory;

import com.gestaodecontatos.model.Contato;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author cleit
 */
public class Empacotamento {
    public static void gravarArquivo(ArrayList<Contato> contatos, String nomeArq) {
        File arq = new File(nomeArq);
        
        try {
            arq.delete();
            arq.createNewFile();
            
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(arq));
            out.writeObject(contatos);
            out.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Empacotamento.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Empacotamento.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static ArrayList<Contato> lerArquivo(String nomeArq) {
        ArrayList<Contato> contatos = new ArrayList<>();
        
        try {
            File arq = new File(nomeArq);
            
            if(arq.exists()) {
                ObjectInputStream inp = new ObjectInputStream(new FileInputStream(arq));
                contatos = (ArrayList<Contato>) inp.readObject();
                inp.close();
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Empacotamento.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(Empacotamento.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return contatos;
    }
}
