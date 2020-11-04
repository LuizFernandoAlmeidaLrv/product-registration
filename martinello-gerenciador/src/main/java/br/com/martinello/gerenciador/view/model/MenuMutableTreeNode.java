package br.com.martinello.gerenciador.view.model;

import br.com.martinello.bd.matriz.model.domain.Menu;
import java.util.Collections;
import java.util.Enumeration;
import javax.swing.tree.DefaultMutableTreeNode;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Sidnei
 */
public class MenuMutableTreeNode extends DefaultMutableTreeNode {

    private Menu menu;

    public MenuMutableTreeNode(Menu menu) {
        super(menu.getRotulo());
        this.menu = menu;
    }

    public MenuMutableTreeNode(String menu) {
        super(menu);
        this.menu = null;
    }

    public MenuMutableTreeNode(boolean allowsChildren, Menu menu) {
        super(menu.getRotulo(), allowsChildren);
        this.menu = menu;
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
        this.setUserObject(menu.getRotulo());
    }

    public void sort() {
        Collections.sort(super.children, (Object o1, Object o2) -> {
            if (!(o1 instanceof MenuMutableTreeNode)) {
                return 0;
            }

            MenuMutableTreeNode node1 = (MenuMutableTreeNode) o1;
            MenuMutableTreeNode node2 = (MenuMutableTreeNode) o2;

            return node1.getMenu().getOrdem().compareTo(node2.getMenu().getOrdem());
        });

    }

    public DefaultMutableTreeNode searchNode(Menu menu) {
        MenuMutableTreeNode node;
        Enumeration<?> e = breadthFirstEnumeration();
        while (e.hasMoreElements()) {
            node = (MenuMutableTreeNode) e.nextElement();
            if (menu.equals(node.getMenu())) {
                return node;
            }
        }
        return null;
    }
    
    public void limpar(){
        removeAllChildren();
    }

}
