package cz.cvut.fel.restauracefel.pokladna.pokladna_gui;

import java.awt.Graphics;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 * Trida pro graficke zobrazeni uctenky.
 *
 * @author komarem
 */

class BackgroundPanel extends JPanel
{
  ImageIcon image = new ImageIcon("images/bill-bg.png");

  @Override
  protected void paintComponent(Graphics g)
  {
    super.paintComponent(g);
    if (image != null)
      g.drawImage(image.getImage(), 0,0,300,460,this);
  }
}
