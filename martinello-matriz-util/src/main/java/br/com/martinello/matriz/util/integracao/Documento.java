/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.matriz.util.integracao;

public class Documento {

    private Long cStat;
    private String chaveAcesso;
    private Long codigoUF;
    private String conteudo;
    private Long dataRecebimento;
    private Long idLote;
    private String numeroProtocolo;
    private String status;
    private Long timestamp;
    private String xMotivo;

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

    public Long getCodigoUF() {
        return codigoUF;
    }

    public void setCodigoUF(Long codigoUF) {
        this.codigoUF = codigoUF;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    public Long getDataRecebimento() {
        return dataRecebimento;
    }

    public void setDataRecebimento(Long dataRecebimento) {
        this.dataRecebimento = dataRecebimento;
    }

    public Long getIdLote() {
        return idLote;
    }

    public void setIdLote(Long idLote) {
        this.idLote = idLote;
    }

    public String getNumeroProtocolo() {
        return numeroProtocolo;
    }

    public void setNumeroProtocolo(String numeroProtocolo) {
        this.numeroProtocolo = numeroProtocolo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getxMotivo() {
        return xMotivo;
    }

    public void setxMotivo(String xMotivo) {
        this.xMotivo = xMotivo;
    }

    @Override
    public String toString() {
        return "Documento{" + "cStat=" + cStat + ", chaveAcesso=" + chaveAcesso + ", codigoUF=" + codigoUF + ", dataRecebimento=" + dataRecebimento + ", idLote=" + idLote + ", numeroProtocolo=" + numeroProtocolo + ", status=" + status + ", timestamp=" + timestamp + ", xMotivo=" + xMotivo + '}';
    }
    
}
