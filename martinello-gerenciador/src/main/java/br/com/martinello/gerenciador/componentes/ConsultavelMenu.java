/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.gerenciador.componentes;

import br.com.martinello.bd.matriz.model.dao.ConexaoOracle;
import br.com.martinello.bd.matriz.model.domain.Menu;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

/**
 *
 * @author Sidnei
 */
public class ConsultavelMenu implements Consultavel<Menu, Long> {

    protected Menu menuNew, menuOld;
    protected String valorOld = "", valorNew = "";
    protected Class tabela;
    protected String campo;

    public ConsultavelMenu(Class tabela, String campo) {
        this.tabela = tabela;
        this.campo = campo;
    }

    @Override
    public Menu getValor() {
        return menuNew;
    }

    @Override
    public void setValor(Menu menu) {
        menuOld = menuNew;
        valorOld = valorNew;

        if (menu != null) {
            menuNew = menu;
            valorNew = "" + menuNew.getIdMenu();
        } else {
            menuNew = null;
            valorNew = "";
        }
        /*
            setString("" + menuNew.getIdMenu());

            if (jlDescricao != null) {
                jlDescricao.setText(menuNew.getDescricao());
            }
        } else {
            if (jlDescricao != null) {
                jlDescricao.setText("");
            }
        }*/
    }

    @Override
    public String getDescricao() {
        return menuNew.getDescricao();
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
        return menuNew != null;
    }

    @Override
    public boolean eVazio() {
        return menuNew == null;
    }

    @Override
    public Long getChave() {
        return menuNew.getIdMenu();
    }

    @Override
    public void setChave(Long chave) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Menu> pesquisar(String filtro) {

        EntityManager em = ConexaoOracle.getEntityManager();
        List<Menu> menus;

        TypedQuery<Menu> tqMenus;

        if (filtro.matches("[0-9]+")) {
            tqMenus = em.createQuery(""
                    + " from Menu "
                    + "where idMenu = :filtroId "
                    + "   or menu like :filtro "
                    + "   or descricao like :filtro", Menu.class)
                    .setParameter("filtroId", Long.parseLong(filtro))
                    .setParameter("filtro", "%" + filtro + "%");
            menus = tqMenus.getResultList();
        } else {
            tqMenus = em.createQuery(""
                    + " from Menu "
                    + "where menu like :filtro "
                    + "   or descricao like :filtro", Menu.class)
                    .setParameter("filtro", "%" + filtro + "%");
            menus = tqMenus.getResultList();
        }

        return menus;
    }
}
