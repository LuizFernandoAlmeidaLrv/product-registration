/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.gerenciador.componentes;

import br.com.martinello.bd.matriz.model.dao.ConexaoOracle;
import br.com.martinello.bd.matriz.integracao.model.domain.Loja;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

/**
 *
 * @author Sidnei
 */
public class ConsultavelLoja implements Consultavel<Loja, String> {

    protected Loja lojaNova, lojaOld;
    protected String valorOld = "", valorNew = "";
    protected Class loja;
    protected String campo;

    public ConsultavelLoja(Class loja, String campo) {
        this.loja = loja;
        this.campo = campo;
    }

    @Override
    public Loja getValor() {
        return lojaNova;
    }

    @Override
    public void setValor(Loja loja) {
        lojaOld = lojaNova;
        valorOld = valorNew;

        if (loja != null) {
            lojaNova = loja;
            valorNew = "" + lojaNova.getCodigo();
        } else {
            lojaNova = null;
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
        return lojaNova.getNome();
    }

    @Override
    public Class getTabela() {
        return loja;
    }

    @Override
    public String getCampo() {
        return campo;
    }

    @Override
    public boolean eValido() {
        return lojaNova != null;
    }

    @Override
    public boolean eVazio() {
        return lojaNova == null;
    }

    @Override
    public String getChave() {
        return lojaNova.getCodigo();
    }

    @Override
    public void setChave(String chave) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Loja> pesquisar(String filtro) {

        EntityManager em = ConexaoOracle.getEntityManager();
        List<Loja> colunas;

        TypedQuery<Loja> tqColunas;

        tqColunas = em.createQuery(""
                + " from Loja "
                + "where codigo = :filtro "
                + "   or nome like :filtro "
                + "   or razaoSocial like :filtro", Loja.class)
                .setParameter("filtro", "%" + filtro + "%");
        colunas = tqColunas.getResultList();

        return colunas;
    }
}
