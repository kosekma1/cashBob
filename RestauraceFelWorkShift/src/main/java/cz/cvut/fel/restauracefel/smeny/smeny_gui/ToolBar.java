package cz.cvut.fel.restauracefel.smeny.smeny_gui;

import java.awt.BorderLayout;
//import java.awt.Dimension;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JToolBar;

/**
 * Trida reprezentujici toolbar pro ovladani aplikace
 *
 * @author Martin Kosek
 */
public class ToolBar extends JToolBar {

    public JButton addTypeWorkShift = new JButton(new ImageIcon("images/add-type-workshift.png"));
    public JButton addTemplate = new JButton(new ImageIcon("images/add-template.png"));
    public JButton listOfTemplates = new JButton(new ImageIcon("images/seznam-sablon.png"));
    public JButton planOfShifts = new JButton(new ImageIcon("images/plan-of-workshifts.png"));
    public JButton statistics = new JButton(new ImageIcon("images/statistics.png"));
    public JButton information = new JButton(new ImageIcon("images/information.png"));
    public JButton overviewShift = new JButton(new ImageIcon("images/edit-leader.png"));
    public JButton overviewEmpShift = new JButton(new ImageIcon("images/edit-employees.png"));
    public JButton close = new JButton(new ImageIcon("images/exit.png"));
    private JPanel panel = new JPanel();
    public final String ADD_TYPE_OF_WORKSHIFT = "Přidat typ směny";
    public final String ADD_TEMPLATE = "Přidat novou šablonu";
    public final String PLAN_OF_SHIFTS = "Plán směn";
    public final String STATISTICS = "Statistika směn";
    public final String OVERVIEW_WORKSHIFTS = "Přehled směn / Přihlášení / Odhlášení ze směny.";
    public final String INFORMATION_HELP = "Informace / Nápověda";

    /**
     * Vlozi ikony do horniho panelu.
     */
    public ToolBar() {
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        this.setLayout(new BorderLayout());

        close.setFocusPainted(false);
        this.add(close, BorderLayout.WEST);
        panel.add(close);

        addTypeWorkShift.setFocusPainted(false);
        addTypeWorkShift.setToolTipText(ADD_TYPE_OF_WORKSHIFT);

        addTemplate.setFocusPainted(false);
        addTemplate.setToolTipText(ADD_TEMPLATE);

        planOfShifts.setFocusPainted(false);
        planOfShifts.setToolTipText(PLAN_OF_SHIFTS);

        overviewShift.setFocusPainted(false);
        overviewShift.setToolTipText(OVERVIEW_WORKSHIFTS);

        //statistics.setFocusPainted(false);
        //statistics.setToolTipText(STATISTICS);

        panel.add(addTypeWorkShift);
        panel.add(addTemplate);
        panel.add(planOfShifts);
        panel.add(overviewShift);
        //panel.add(statistics);                        

        overviewEmpShift.setFocusPainted(false);
        overviewEmpShift.setToolTipText(OVERVIEW_WORKSHIFTS);

        panel.add(overviewEmpShift);
        //panel.add(information);

        //this.addSeparator(new Dimension(20, 20));
        this.add(panel, BorderLayout.WEST);
        System.out.println("Tool bar created.");
    }
}
