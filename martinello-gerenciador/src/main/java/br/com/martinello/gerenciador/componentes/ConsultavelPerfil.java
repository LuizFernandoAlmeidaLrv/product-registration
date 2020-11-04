/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.gerenciador.componentes;

import br.com.martinello.bd.matriz.model.dao.ConexaoOracle;
import br.com.martinello.bd.matriz.model.domain.Perfil;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

/**
 *
 * @author Sidnei
 */
public class ConsultavelPerfil implements Consultavel<Perfil, Long> {

    protected Perfil perfilNew, perfilOld;
    protected String valorOld = "", valorNew = "";
    protected Class tabela;
    protected String campo;

    public ConsultavelPerfil(Class tabela, String campo) {
        this.tabela = tabela;
        this.campo = campo;
    }

    @Override
    public Perfil getValor() {
        return perfilNew;
    }

    @Override
    public void setValor(Perfil perfil) {
        perfilOld = perfilNew;
        valorOld = valorNew;

        if (perfil != null) {
            perfilNew = perfil;
            valorNew = "" + perfilNew.getIdPerfil();
        } else {
            perfilNew = null;
            valorNew = "";
        }
        /*
            setString("" + perfilNew.getIdPerfil());

            if (jlDescricao != null) {
                jlDescricao.setText(perfilNew.getDescricao());
            }
        } else {
            if (jlDescricao != null) {
                jlDescricao.setText("");
            }
        }*/
    }

    @Override
    public String getDescricao() {
        return perfilNew.getDescricao();
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
        return perfilNew != null;
    }

    @Override
    public boolean eVazio() {
        return perfilNew == null;
    }

    @Override
    public Long getChave() {
        return perfilNew.getIdPerfil();
    }

    @Override
    public void setChave(Long chave) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Perfil> pesquisar(String filtro) {

        EntityManager em = ConexaoOracle.getEntityManager();
        List<Perfil> perfils;

        TypedQuery<Perfil> tqPerfils;

        if (filtro.matches("[0-9]+")) {
            tqPerfils = em.createQuery(""
                    + " from Perfil "
                    + "where idPerfil = :filtroId "
                    + "   or perfil like :filtro "
                    + "   or descricao like :filtro", Perfil.class)
                    .setParameter("filtroId", Long.parseLong(filtro))
                    .setParameter("filtro", "%" + filtro + "%");
            perfils = tqPerfils.getResultList();
        } else {
            tqPerfils = em.createQuery(""
                    + " from Perfil "
                    + "where perfil like :filtro "
                    + "   or descricao like :filtro", Perfil.class)
                    .setParameter("filtro", "%" + filtro + "%");
            perfils = tqPerfils.getResultList();
        }

        return perfils;
    }
}
