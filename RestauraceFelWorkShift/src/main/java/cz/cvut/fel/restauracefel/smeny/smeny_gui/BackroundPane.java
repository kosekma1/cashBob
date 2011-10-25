package cz.cvut.fel.restauracefel.smeny.smeny_gui;

import java.awt.Graphics;
import javax.swing.ImageIcon;
import javax.swing.JScrollPane;

/**
 * Trida pro graficke zobrazeni pozadi
 *
 * @author komarem
 */
public class BackroundPane extends JScrollPane {

    ImageIcon image = new ImageIcon("images/back.png");

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (image != null) {
            g.drawImage(image.getImage(), 0, 0, g.getClipBounds().width, g.getClipBounds().height, this);
        }
    }
}
