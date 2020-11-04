/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.gerenciador.componentes;

import br.com.martinello.bd.matriz.model.dao.ConexaoOracle;
import br.com.martinello.bd.matriz.model.domain.Lista;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

/**
 *
 * @author Sidnei
 */
public class ConsultavelLista implements Consultavel<Lista, Long> {

    protected Lista listaNew, listaOld;
    protected String valorOld = "", valorNew = "";
    protected Class tabela;
    protected String campo;

    public ConsultavelLista(Class tabela, String campo) {
        this.tabela = tabela;
        this.campo = campo;
    }

    @Override
    public Lista getValor() {
        return listaNew;
    }

    @Override
    public void setValor(Lista lista) {
        listaOld = listaNew;
        valorOld = valorNew;

        if (lista != null) {
            listaNew = lista;
            valorNew = "" + listaNew.getIdLista();
        } else {
            listaNew = null;
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
        return listaNew.getDescricao();
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
        return listaNew != null;
    }

    @Override
    public boolean eVazio() {
        return listaNew == null;
    }

    @Override
    public Long getChave() {
        return listaNew.getIdLista();
    }

    @Override
    public void setChave(Long chave) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Lista> pesquisar(String filtro) {

        EntityManager em = ConexaoOracle.getEntityManager();
        List<Lista> listas;

        TypedQuery<Lista> tqListas;

        if (filtro.matches("[0-9]+")) {
            tqListas = em.createQuery(""
                    + " from Lista "
                    + "where idLista = :filtroId "
                    + "   or lista like :filtro "
                    + "   or descricao like :filtro", Lista.class)
                    .setParameter("filtroId", Long.parseLong(filtro))
                    .setParameter("filtro", "%" + filtro + "%");
            listas = tqListas.getResultList();
        } else {
            tqListas = em.createQuery(""
                    + " from Lista "
                    + "where lista like :filtro "
                    + "   or descricao like :filtro", Lista.class)
                    .setParameter("filtro", "%" + filtro + "%");
            listas = tqListas.getResultList();
        }

        return listas;
    }
}
