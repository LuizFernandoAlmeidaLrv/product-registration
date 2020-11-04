/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.matriz.bd.transients;

/**
 *
 * @author rafael.rosar
 */
public class EstoqueTray {

    private Long estoqueFisico;
    private Long estoqueReservado;
    private Long centroDistribuicaoId;

    public Long getEstoqueFisico() {
        return estoqueFisico;
    }

    public void setEstoqueFisico(Long estoqueFisico) {
        this.estoqueFisico = estoqueFisico;
    }

    public Long getEstoqueReservado() {
        return estoqueReservado;
    }

    public void setEstoqueReservado(Long estoqueReservado) {
        this.estoqueReservado = estoqueReservado;
    }

    public Long getCentroDistribuicaoId() {
        return centroDistribuicaoId;
    }

    public void setCentroDistribuicaoId(Long centroDistribuicaoId) {
        this.centroDistribuicaoId = centroDistribuicaoId;
    }

}
