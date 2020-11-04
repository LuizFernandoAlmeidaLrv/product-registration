/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.gerenciador.view.model;

import br.com.martinello.bd.matriz.model.domain.Menu;
import java.util.List;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;

/**
 *
 * @author Sidnei
 */
public class MenuTreeModel extends DefaultTreeModel {

    protected DefaultMutableTreeNode root;

    public MenuTreeModel(DefaultMutableTreeNode root) {
        super(root);
        this.root = root;
    }

    public MenuTreeModel(MutableTreeNode root, boolean asksAllowsChildren) {
        super(root, asksAllowsChildren);
    }

    public void addAll(List<DefaultMutableTreeNode> lMutableTreeNode) {
        
        for (DefaultMutableTreeNode mutableTreeNode : lMutableTreeNode) {
            root.add(mutableTreeNode);
        }
    }

    public void addAllMenus(List<Menu> lMenus, DefaultMutableTreeNode node) {

        for (Menu menu : lMenus) {
            MenuMutableTreeNode dmtnMenu = new MenuMutableTreeNode(menu);

            node.add(dmtnMenu);

            //todosMenus.add(menu);
            if (menu.getMenusFilhos() != null && menu.getMenusFilhos().size() > 0) {
                addAllMenus(menu.getMenusFilhos(), dmtnMenu);
            }
        }
    }

}
