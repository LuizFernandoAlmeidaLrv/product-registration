/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.matriz.util.integracao;

import com.towel.el.annotation.Resolvable;
import java.time.LocalDateTime;

/**
 *
 * @author rafael.rosar
 */
public class Log {

    @Resolvable(colName = "Id. Log")
    private Long idLog;
    @Resolvable(colName = "Data")
    private LocalDateTime data;
    @Resolvable(colName = "Mensagem")
    private String msg;

    public Log() {
    }

    public Log(Long idLog, LocalDateTime data, String msg) {
        this.idLog = idLog;
        this.data = data;
        this.msg = msg;
    }

    public Long getIdLog() {
        return idLog;
    }

    public void setIdLog(Long idLog) {
        this.idLog = idLog;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}
