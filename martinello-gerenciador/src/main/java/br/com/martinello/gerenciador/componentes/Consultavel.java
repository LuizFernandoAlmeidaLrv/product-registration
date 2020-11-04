/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.gerenciador.componentes;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Sidnei
 */
public interface Consultavel<E extends Serializable, I> {

    public abstract I getChave();

    public abstract void setChave(I i);

    public abstract E getValor();

    public abstract void setValor(E e);

    public abstract String getDescricao();

    public abstract Class getTabela();

    public abstract String getCampo();

    public abstract boolean eValido();

    public abstract boolean eVazio();

    public abstract List<E> pesquisar(String filtro);

}
