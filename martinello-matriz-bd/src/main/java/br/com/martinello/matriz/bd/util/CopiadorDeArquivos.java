/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.matriz.bd.util;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

/**
 *
 * @author luiz.almeida
 */
public class CopiadorDeArquivos extends SimpleFileVisitor {

    private Path origem;
    private Path destino;

    public CopiadorDeArquivos(Path origem, Path destino) {
        this.origem = origem;
        this.destino = destino;
    }

    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs)
            throws IOException {
        copiaPath(dir);
        return FileVisitResult.CONTINUE;
    }

    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)
            throws IOException {
        copiaPath(file);
        return FileVisitResult.CONTINUE;
    }

    private void copiaPath(Path entrada) throws IOException {
        // encontra o caminho equivalente na árvore de cópia
        Path novoDiretorio = destino.resolve(origem.relativize(entrada));
        Files.copy(entrada, novoDiretorio);
    }

}
