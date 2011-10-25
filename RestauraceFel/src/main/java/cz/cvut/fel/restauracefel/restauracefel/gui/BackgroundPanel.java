package cz.cvut.fel.restauracefel.restauracefel.gui;

import java.awt.Graphics;
import javax.swing.ImageIcon;
import javax.swing.JScrollPane;

/**
 * Trida pro graficke zobrazeni pozadi
 *
 * @author komarem
 */
public class BackgroundPanel extends JScrollPane {

    ImageIcon image = new ImageIcon("images/back.png");

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (image != null) {
            g.drawImage(image.getImage(), 0, 0, this.getViewport().getWidth(), this.getViewport().getHeight(), this);
        }
    }
}
