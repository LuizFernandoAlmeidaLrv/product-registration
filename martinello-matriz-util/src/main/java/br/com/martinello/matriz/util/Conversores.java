/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.matriz.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

/**
 *
 * @author Sidnei
 */
public class Conversores {

    public String converteSenhaASCII(String senhaConverter) {
        try {
            byte[] bSenhaConverter = senhaConverter.getBytes("CP850");
            byte[] bSenhaConvertida = new byte[bSenhaConverter.length];

            for (int i = 0; i < senhaConverter.length(); i++) {
                bSenhaConvertida[i] = (byte) (bSenhaConverter[i] + 129);
            }
            String senhaConvertida = new String(bSenhaConvertida, "CP850");
            return senhaConvertida;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public String lpad(String valueToPad, String filler, int size) {
        while (valueToPad.length() < size) {
            valueToPad = filler + valueToPad;
        }
        return valueToPad;
    }

    public String rpad(String valueToPad, String filler, int size) {
        while (valueToPad.length() < size) {
            valueToPad = valueToPad + filler;
        }
        return valueToPad;
    }

    public static java.sql.Date stringParaData(String dataString) {
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date data = null;

        if (dataString != null && !dataString.equals("null")) {

            try {
                data = (java.util.Date) formatter.parse(dataString);
            } catch (ParseException e) {
                e.printStackTrace();
                //return data;
            }
            return converteData(data);
        }else{
            return null;
        }
    }

    public static java.sql.Date converteData(java.util.Date data) {
        GregorianCalendar m_cal = new GregorianCalendar(TimeZone.getTimeZone("GMT"));
        m_cal.setTime(data);
        return new java.sql.Date(m_cal.getTime().getTime());
    }
}
