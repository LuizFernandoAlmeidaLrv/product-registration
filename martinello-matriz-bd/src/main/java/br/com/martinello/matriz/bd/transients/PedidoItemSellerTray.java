/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.matriz.bd.transients;

/**
 *
 * @author rafael
 */
public class PedidoItemSellerTray {
    private Long sellerId;
    private String sellerNome;
    private Long sellerPedidoId;

    public Long getSellerId() {
        return sellerId;
    }

    public void setSellerId(Long sellerId) {
        this.sellerId = sellerId;
    }

    public String getSellerNome() {
        return sellerNome;
    }

    public void setSellerNome(String sellerNome) {
        this.sellerNome = sellerNome;
    }

    public Long getSellerPedidoId() {
        return sellerPedidoId;
    }

    public void setSellerPedidoId(Long sellerPedidoId) {
        this.sellerPedidoId = sellerPedidoId;
    }
    
    
    
}
