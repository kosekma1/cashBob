/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fel.restauracefel.smeny.smeny_gui;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 *
 * @author Martin
 */
public class EmployeeAction extends AbstractAction  {
     private int width = 0;
     private int height = 0;
     public final int SIZE = 300;
    
     //TODO - musi ziskat odkaz na frame aby tam mohl predat id zamestancu
     public EmployeeAction(int width, int height){
         super("Přihlásit zaměstnance");
         this.width = width;
         this.height = height;
     }
    
     public void actionPerformed(ActionEvent e) {       
       JFrame jf = new JFrame();
       jf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
       FlowLayout fl = new FlowLayout();
       jf.setLayout(fl);
       JLabel jl = new JLabel("Seznam zaměstnanců");       
       jf.add(jl);
       jf.setSize(SIZE, SIZE);
       jf.setLocation(width/2-SIZE/2, height/2-SIZE/2);       
       jf.setVisible(true);
    }
    
}
