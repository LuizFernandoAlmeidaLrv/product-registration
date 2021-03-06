/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.matriz.bd.util;

/**
 *
 * @author luiz.almeida
 */
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.PathMatcher;

/**
 * Created by IntelliJ IDEA. User: bbejeck Date: 2/2/12 Time: 11:46 PM
 */
public class FilterBuilder {

    private FilterBuilder() {
    }

    public static DirectoryStream.Filter<Path> buildGlobFilter(String pattern) {
        final PathMatcher pathMatcher = getPathMatcher("glob:" + pattern);
        return new DirectoryStream.Filter<Path>() {
            @Override
            public boolean accept(Path entry) throws IOException {
                return pathMatcher.matches(entry);
            }
        };
    }

    public static DirectoryStream.Filter<Path> buildRegexFilter(String pattern) {
        final PathMatcher pathMatcher = getPathMatcher("regex:" + pattern);
        return new DirectoryStream.Filter<Path>() {
            @Override
            public boolean accept(Path entry) throws IOException {
                return pathMatcher.matches(entry);
            }
        };
    }

    private static PathMatcher getPathMatcher(String pattern) {
        return FileSystems.getDefault().getPathMatcher(pattern);
    }
}
