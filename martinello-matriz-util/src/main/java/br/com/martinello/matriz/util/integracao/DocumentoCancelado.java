/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.matriz.util.integracao;

public class DocumentoCancelado {

    private Long cStat;
    private String chaveAcesso;
    private Long codigoEvento;
    private String conteudo;
    private String dataEvento;
    private String descricaoEvento;
    private String retornoSefaz;
    private Long sequencial;
    private String status;

    public Long getcStat() {
        return cStat;
    }

    public void setcStat(Long cStat) {
        this.cStat = cStat;
    }

    public String getChaveAcesso() {
        return chaveAcesso;
    }

    public void setChaveAcesso(String chaveAcesso) {
        this.chaveAcesso = chaveAcesso;
    }

    public Long getCodigoEvento() {
        return codigoEvento;
    }

    public void setCodigoEvento(Long codigoEvento) {
        this.codigoEvento = codigoEvento;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    public String getDataEvento() {
        return dataEvento;
    }

    public void setDataEvento(String dataEvento) {
        this.dataEvento = dataEvento;
    }

    public String getDescricaoEvento() {
        return descricaoEvento;
    }

    public void setDescricaoEvento(String descricaoEvento) {
        this.descricaoEvento = descricaoEvento;
    }

    public String getRetornoSefaz() {
        return retornoSefaz;
    }

    public void setRetornoSefaz(String retornoSefaz) {
        this.retornoSefaz = retornoSefaz;
    }

    public Long getSequencial() {
        return sequencial;
    }

    public void setSequencial(Long sequencial) {
        this.sequencial = sequencial;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "DocumentoCancelado{" + "cStat=" + cStat + ", chaveAcesso=" + chaveAcesso + ", codigoEvento=" + codigoEvento + ", conteudo=" + conteudo + ", dataEvento=" + dataEvento + ", descricaoEvento=" + descricaoEvento + ", retornoSefaz=" + retornoSefaz + ", sequencial=" + sequencial + ", status=" + status + '}';
    }

    
}
