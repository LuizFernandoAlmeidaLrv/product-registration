/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.imatriz.integrador.ecommerce;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author rafael
 */
public class CategoriaRelacionarTray {

    private Long categoriaPrincipalId;
    private List<Long> listaCategoriaId = new LinkedList<>();

    public CategoriaRelacionarTray() {
    }

    public Long getCategoriaPrincipalId() {
        return categoriaPrincipalId;
    }

    public void setCategoriaPrincipalId(Long categoriaPrincipalId) {
        this.categoriaPrincipalId = categoriaPrincipalId;
    }

    public List<Long> getListaCategoriaId() {
        return listaCategoriaId;
    }

    public void setListaCategoriaId(List<Long> listaCategoriaId) {
        this.listaCategoriaId = listaCategoriaId;
    }

}
