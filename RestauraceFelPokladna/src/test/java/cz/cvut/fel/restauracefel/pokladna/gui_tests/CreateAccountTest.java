package cz.cvut.fel.restauracefel.pokladna.gui_tests;

import abbot.finder.Matcher;
import abbot.finder.matchers.ClassMatcher;
import abbot.tester.ButtonTester;
import java.awt.Component;
import java.awt.Frame;
import javax.swing.JButton;
import javax.swing.JTextField;
import junit.extensions.abbot.ComponentTestFixture;
import junit.extensions.abbot.TestHelper;
import cz.cvut.fel.restauracefel.pokladna.pokladna_gui.CreateAccountForm;
import cz.cvut.fel.restauracefel.pokladna.pokladna_gui.KeyboardDialog;
import cz.cvut.fel.restauracefel.pokladna.pokladna_gui.StatusBar;

/**
 *
 * @author Tomáš
 */
public class CreateAccountTest extends ComponentTestFixture {

    ButtonTester tester = new ButtonTester();

    public void pressButton(final String s) throws Throwable {
        JButton button = (JButton)getFinder().find(new Matcher() {
            @Override
            public boolean matches(Component c) {
                return c.getClass().equals(JButton.class)
                    && ((JButton)c).getText().equals(s);
            }
        });
        tester.actionClick(button);
    }

    public void testLabelChangedOnSelectionChange() throws Throwable {
        
        /*final CreateAccountForm form = new CreateAccountForm(null, new StatusBar());
        Frame frame = showFrame(form);

        JButton button = (JButton)getFinder().find(new Matcher() {
            @Override
            public boolean matches(Component c) {
                return c.getClass().equals(JButton.class)
                    && c.hasFocus();
            }
        });

        tester.actionClick(button);

        KeyboardDialog kd = (KeyboardDialog) getFinder().find(new ClassMatcher(KeyboardDialog.class));

        String nazev = "HROZNE DLOUHY NAZEV NOVEHO UCTU";
        for (int i=0; i<nazev.length(); i++) pressButton(nazev.substring(i, i+1));

        JTextField textField = (JTextField)getFinder().find(new Matcher() {
            @Override
            public boolean matches(Component c) {
                return c.getClass().equals(JTextField.class)
                    && ((JTextField)c).isEditable();
            }
        });
        
        assertEquals("Chybny vystup z klavesnice na obrazovce", nazev, textField.getText());*/
    }

    public CreateAccountTest(String name) { super(name); }

    public static void main(String[] args) {
        TestHelper.runTests(args, CreateAccountTest.class);
    }

}
