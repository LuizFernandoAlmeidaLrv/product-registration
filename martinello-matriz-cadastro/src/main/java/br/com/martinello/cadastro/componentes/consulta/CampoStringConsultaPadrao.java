/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.cadastro.componentes.consulta;

import br.com.martinello.matriz.componentesbasicos.Botao;
import br.com.martinello.matriz.componentesbasicos.CampoString;
import java.awt.BorderLayout;
import java.awt.Insets;

/**
 *
 * @author Sidnei
 */
public class CampoStringConsultaPadrao extends CampoString {
    
    protected Botao bConsultar = new Botao("...");
    
    public CampoStringConsultaPadrao() {
        
        setLayout(new BorderLayout());
        bConsultar.setMargin(new Insets(0, 0, 0, 0));
        bConsultar.setBorderPainted(false);
        
        add(bConsultar, BorderLayout.EAST);
        
        bConsultar.setFocusable(false);
    }
    
    public Botao getbConsultar() {
        return bConsultar;
    }
    
    public void setbConsultar(Botao bConsultar) {
        this.bConsultar = bConsultar;
    }
    
    @Override
    public void setEnabled(boolean habilitar) {
        super.setEnabled(habilitar);
        bConsultar.setEnabled(habilitar);
    }
    
}

