/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.telas.componentes;

import java.awt.AWTKeyStroke;
import java.awt.Component;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Sidnei
 */
public class ControlaEnter {

    public static void considerarEnterComoTab(Component comp) {
        Set<AWTKeyStroke> keystrokes = comp.getFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS);
        Set<AWTKeyStroke> newKeystrokes = new HashSet<AWTKeyStroke>(keystrokes);
        newKeystrokes.add(AWTKeyStroke.getAWTKeyStroke(KeyEvent.VK_ENTER, 0));
        comp.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, newKeystrokes);

//        HashSet backup = new HashSet(comp.getFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS));
//        HashSet conj = (HashSet) backup.clone();
//        conj.add(AWTKeyStroke.getAWTKeyStroke(KeyEvent.VK_DOWN, 0));
//        comp.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, conj);
//        
//        HashSet backupCima = new HashSet(comp.getFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS));
//        HashSet conjCima = (HashSet) backupCima.clone();
//        conjCima.add(AWTKeyStroke.getAWTKeyStroke(KeyEvent.VK_UP, 0));
//        comp.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, conjCima);
        Set<AWTKeyStroke> forwardTraversalKeys = new HashSet<AWTKeyStroke>(comp.getFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS));
        forwardTraversalKeys.add(AWTKeyStroke.getAWTKeyStroke(KeyEvent.VK_DOWN, KeyEvent.VK_UNDEFINED));

        Set<AWTKeyStroke> backwardTraversalKeys = new HashSet<AWTKeyStroke>(comp.getFocusTraversalKeys(KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS));
        backwardTraversalKeys.add(AWTKeyStroke.getAWTKeyStroke(KeyEvent.VK_UP, KeyEvent.VK_UNDEFINED));

        comp.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, forwardTraversalKeys);
        comp.setFocusTraversalKeys(KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, backwardTraversalKeys);
    }
}
