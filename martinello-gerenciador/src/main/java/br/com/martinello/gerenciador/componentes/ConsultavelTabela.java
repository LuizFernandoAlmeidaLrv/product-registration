/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.gerenciador.componentes;

import br.com.martinello.bd.matriz.model.dao.ConexaoOracle;
import br.com.martinello.bd.matriz.model.domain.Tabela;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

/**
 *
 * @author Sidnei
 */
public class ConsultavelTabela implements Consultavel<Tabela, Long> {

    protected Tabela tabelaNova, tabelaOld;
    protected String valorOld = "", valorNew = "";
    protected Class tabela;
    protected String campo;

    public ConsultavelTabela(Class tabela, String campo) {
        this.tabela = tabela;
        this.campo = campo;
    }

    @Override
    public Tabela getValor() {
        return tabelaNova;
    }

    @Override
    public void setValor(Tabela tabela) {
        tabelaOld = tabelaNova;
        valorOld = valorNew;

        if (tabela != null) {
            tabelaNova = tabela;
            valorNew = "" + tabelaNova.getTabela();
        } else {
            tabelaNova = null;
            valorNew = "";
        }
        /*
            setString("" + listaNew.getIdLista());

            if (jlDescricao != null) {
                jlDescricao.setText(listaNew.getDescricao());
            }
        } else {
            if (jlDescricao != null) {
                jlDescricao.setText("");
            }
        }*/
    }

    @Override
    public String getDescricao() {
        return tabelaNova.getDescricao();
    }

    @Override
    public Class getTabela() {
        return tabela;
    }

    @Override
    public String getCampo() {
        return campo;
    }

    @Override
    public boolean eValido() {
        return tabelaNova != null;
    }

    @Override
    public boolean eVazio() {
        return tabelaNova == null;
    }

    @Override
    public Long getChave() {
        return tabelaNova.getIdTabela();
    }

    @Override
    public void setChave(Long chave) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Tabela> pesquisar(String filtro) {

        EntityManager em = ConexaoOracle.getEntityManager();
        List<Tabela> colunas;

        TypedQuery<Tabela> tqColunas;

        if (filtro.matches("[0-9]+")) {
            tqColunas = em.createQuery(""
                    + " from Tabela "
                    + "where idTabela = :filtroId "
                    + "   or tabela like :filtro "
                    + "   or descricao like :filtro", Tabela.class)
                    .setParameter("filtroId", Long.parseLong(filtro))
                    .setParameter("filtro", "%" + filtro + "%");
            colunas = tqColunas.getResultList();
        } else {
            tqColunas = em.createQuery(""
                    + " from Tabela "
                    + "where tabela like :filtro "
                    + "   or descricao like :filtro", Tabela.class)
                    .setParameter("filtro", "%" + filtro + "%");
            colunas = tqColunas.getResultList();
        }

        return colunas;
    }
}
