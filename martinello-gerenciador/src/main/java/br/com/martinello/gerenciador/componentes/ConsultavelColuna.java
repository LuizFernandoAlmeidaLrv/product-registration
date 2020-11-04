/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.gerenciador.componentes;

import br.com.martinello.bd.matriz.model.dao.ConexaoOracle;
import br.com.martinello.bd.matriz.model.domain.Coluna;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

/**
 *
 * @author Sidnei
 */
public class ConsultavelColuna implements Consultavel<Coluna, Long> {

    protected Coluna colunaNew, colunaOld;
    protected String valorOld = "", valorNew = "";
    protected Class tabela;
    protected String campo;

    public ConsultavelColuna(Class tabela, String campo) {
        this.tabela = tabela;
        this.campo = campo;
    }

    @Override
    public Coluna getValor() {
        return colunaNew;
    }

    @Override
    public void setValor(Coluna coluna) {
        colunaOld = colunaNew;
        valorOld = valorNew;

        if (coluna != null) {
            colunaNew = coluna;
            valorNew = "" + colunaNew.getColuna();
        } else {
            colunaNew = null;
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
        return colunaNew.getDescricao();
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
        return colunaNew != null;
    }

    @Override
    public boolean eVazio() {
        return colunaNew == null;
    }

    @Override
    public Long getChave() {
        return colunaNew.getIdColuna();
    }

    @Override
    public void setChave(Long chave) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Coluna> pesquisar(String filtro) {

        EntityManager em = ConexaoOracle.getEntityManager();
        List<Coluna> colunas;

        TypedQuery<Coluna> tqColunas;

        if (filtro.matches("[0-9]+")) {
            tqColunas = em.createQuery(""
                    + " from Coluna "
                    + "where idColuna = :filtroId "
                    + "   or coluna like :filtro "
                    + "   or descricao like :filtro", Coluna.class)
                    .setParameter("filtroId", Long.parseLong(filtro))
                    .setParameter("filtro", "%" + filtro + "%");
            colunas = tqColunas.getResultList();
        } else {
            tqColunas = em.createQuery(""
                    + " from Coluna "
                    + "where coluna like :filtro "
                    + "   or descricao like :filtro", Coluna.class)
                    .setParameter("filtro", "%" + filtro + "%");
            colunas = tqColunas.getResultList();
        }

        return colunas;
    }
}
