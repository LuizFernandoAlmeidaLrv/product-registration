/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.matriz.util.integracao;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.Objects.isNull;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 *
 * @author rafael.rosar
 */
public class GZIPCompression {

    public static byte[] compress(final String stringToCompress) {
        if (isNull(stringToCompress) || stringToCompress.length() == 0) {
            return null;
        }

        try (final ByteArrayOutputStream baos = new ByteArrayOutputStream();
                final GZIPOutputStream gzipOutput = new GZIPOutputStream(baos)) {
            gzipOutput.write(stringToCompress.getBytes(UTF_8));
            gzipOutput.finish();
            return baos.toByteArray();
        } catch (IOException e) {
            throw new UncheckedIOException("Error while compression!", e);
        }
    }

    public static String ungzip(byte[] bytes) throws Exception {
        InputStreamReader isr = new InputStreamReader(new GZIPInputStream(new ByteArrayInputStream(bytes)), StandardCharsets.UTF_8);
        StringWriter sw = new StringWriter();
        char[] chars = new char[1024];
        for (int len; (len = isr.read(chars)) > 0;) {
            sw.write(chars, 0, len);
        }
        return sw.toString();
    }

    public static byte[] gzip(String s) throws Exception {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        GZIPOutputStream gzip = new GZIPOutputStream(bos);
        OutputStreamWriter osw = new OutputStreamWriter(gzip, StandardCharsets.UTF_8);
        osw.write(s);
        osw.close();
        return bos.toByteArray();
    }
}
