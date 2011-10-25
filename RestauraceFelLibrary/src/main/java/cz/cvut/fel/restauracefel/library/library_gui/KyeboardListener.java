/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fel.restauracefel.library.library_gui;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Trida keyListeneru pro psani z klavesnice.
 *
 * @author Tomas Hnizdil
 */
class KyeboardListener implements KeyListener {

    private Keyboard panel;

    /**
     * Konstruktor tridy KyeBListener
     *
     * @param keyboard odkaz na KeyboardDialog volajici tuto tridu
     */
    public KyeboardListener(Keyboard panel) {
        this.panel = panel;
    }

    public void keyTyped(KeyEvent e) {
        String puvodni = panel.actualTF.getText();
        int znak = (int) e.getKeyChar();
        if ((znak >= 65 && znak <= 122) || (znak >= 48 && znak <= 57)) {
            String text = "";
            text += e.getKeyChar();
            panel.addText(text.toUpperCase());
            //panel.actualTF.setText((puvodni + e.getKeyChar()).toUpperCase());
        } else if (znak == 32) { // space
            if (!panel.jButtonSPACE.hasFocus()) {
                panel.jButtonSPACE.requestFocus();
                panel.addText(" ");
            }
        } else if (znak == 127) { // delete
            if (puvodni.length() == 0) {
                return;
            }

            panel.actualTF.setText(puvodni.substring(0, puvodni.length() - 1));
        }// else if ( znak == 10 && panel.shutable ) { // enter
        // panel.dispose( );
        //}
    }

    public void keyPressed(KeyEvent e) {
    }

    public void keyReleased(KeyEvent e) {
    }
}
