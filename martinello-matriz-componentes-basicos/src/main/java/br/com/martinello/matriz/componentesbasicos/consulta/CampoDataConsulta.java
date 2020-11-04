/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.matriz.componentesbasicos.consulta;

import br.com.martinello.matriz.componentesbasicos.Campo;
import br.com.martinello.matriz.componentesbasicos.Rotulo;
import br.com.martinello.matriz.componentesbasicos.paineis.Painel;
import br.com.martinello.matriz.util.filtro.Filtro;
import br.com.martinello.matriz.util.filtro.Periodo;
import java.util.Date;
import javax.swing.SwingUtilities;

/**
 *
 * @author Sidnei
 */
public class CampoDataConsulta extends Painel implements Campo {

    protected String dica;
    protected String descricaoRotulo;
    protected boolean obrigatorio;
    protected Boolean editavel = true;
    protected Rotulo rRotulo;

    /**
     * Creates new form CampoStringConsulta
     */
    public CampoDataConsulta() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        cdDataInicial = new br.com.martinello.matriz.componentesbasicos.CampoData();
        cdDataFinal = new br.com.martinello.matriz.componentesbasicos.CampoData();
        rSeparador = new br.com.martinello.matriz.componentesbasicos.Rotulo();

        setLayout(new java.awt.BorderLayout());
        add(cdDataInicial, java.awt.BorderLayout.LINE_START);
        add(cdDataFinal, java.awt.BorderLayout.LINE_END);

        rSeparador.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        rSeparador.setText("-");
        add(rSeparador, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private br.com.martinello.matriz.componentesbasicos.CampoData cdDataFinal;
    private br.com.martinello.matriz.componentesbasicos.CampoData cdDataInicial;
    private br.com.martinello.matriz.componentesbasicos.Rotulo rSeparador;
    // End of variables declaration//GEN-END:variables

    public Date getDataInicial() {
        return cdDataInicial.getDate();
    }

    public Date getDataFinal() {
        return cdDataFinal.getDate();
    }

    public void setDataInicial(Date data) {
        cdDataInicial.setDate(data);
    }

    public void setDataFinal(Date data) {
        cdDataFinal.setDate(data);
    }

    public Periodo getPeriodo() {
        return getDataInicial() == null && getDataFinal() == null ? null : new Periodo(getDataInicial(), getDataFinal());
    }

    public Filtro getFiltro(String campo) {
        if (getDataInicial() != null && getDataFinal() != null) {
            return new Filtro(campo, Filtro.DATE, Filtro.PERIODO, new Periodo(getDataInicial(), getDataFinal()));
        } else if (getDataInicial() != null) {
            return new Filtro(campo, Filtro.DATE, Filtro.MAIOROUIGUAL, getDataInicial());
        } else if (getDataFinal() != null) {
            return new Filtro(campo, Filtro.DATE, Filtro.MENOROUIGUAL, getDataFinal());
        } else {
            return new Filtro(campo, Filtro.DATE, Filtro.PERIODO, null);
        }
    }

    public Filtro getFiltro(String campo, String formato) {
        if (getDataInicial() != null && getDataFinal() != null) {
            return new Filtro(campo, formato, Filtro.PERIODO, new Periodo(getDataInicial(), getDataFinal()));
        } else if (getDataInicial() != null) {
            return new Filtro(campo, formato, Filtro.MAIOROUIGUAL, getDataInicial());
        } else if (getDataFinal() != null) {
            return new Filtro(campo, formato, Filtro.MENOROUIGUAL, getDataFinal());
        } else {
            return new Filtro(campo, formato, Filtro.PERIODO, null);
        }
    }

    @Override
    public boolean eVazio() {
        return cdDataInicial.getDate() == null && cdDataFinal.getDate() == null;
    }

    @Override
    public void limpar() {
        cdDataInicial.limpar();
        cdDataFinal.limpar();
    }

    @Override
    public boolean eValido() {
        return cdDataFinal.getDate().after(cdDataInicial.getDate());
    }

    @Override
    public String getDica() {
        return dica;
    }

    @Override
    public String getDescricaoRotulo() {
        return descricaoRotulo;
    }

    @Override
    public boolean isObrigatorio() {
        return obrigatorio;
    }

    @Override
    public Rotulo getComponenteRotulo() {
        return rRotulo;
    }

    @Override
    public void setObrigatorio(boolean obrigatorio) {
        this.obrigatorio = obrigatorio;

        atualizaDescricaoRotulo();
    }

    @Override
    public void setComponenteRotulo(Rotulo rRotulo) {
        this.rRotulo = rRotulo;

        atualizaDescricaoRotulo();

    }

    public void atualizaDescricaoRotulo() {
        if (rRotulo != null) {
            SwingUtilities.invokeLater(() -> {
                if (obrigatorio) {
                    rRotulo.setText("<html><body>" + descricaoRotulo + "<FONT COLOR='red'><b>*</b></FONT>:</body></html>");
                } else {
                    rRotulo.setText("<html><body>" + descricaoRotulo + ":</body></html>");
                }
            });
        }
    }

    @Override
    public void setDescricaoRotulo(String descricaoRotulo) {
        this.descricaoRotulo = descricaoRotulo;
    }
}