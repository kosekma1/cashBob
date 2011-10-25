package cz.cvut.fel.restauracefel.restauracefel.gui_tests;

import abbot.finder.Matcher;
import abbot.finder.matchers.ClassMatcher;
import abbot.tester.JListTester;
import cz.cvut.fel.restauracefel.restauracefel.gui.LogingForm;
import cz.cvut.fel.restauracefel.restauracefel.gui.MainFrame;
import cz.cvut.fel.restauracefel.restauracefel.gui.StatusBar;
import java.awt.Component;
import java.awt.Frame;
import javax.swing.JButton;
import junit.extensions.abbot.ComponentTestFixture;
import junit.extensions.abbot.TestHelper;

/**
 *
 * @author Tomáš
 */
public class MainFrameTest extends ComponentTestFixture {

    public void testLabelChangedOnSelectionChange() throws Throwable {
        /*//String[] contents = { "one", "two", "three" };
        final StatusBar form = new StatusBar();
        final MainFrame mainFrame = new MainFrame();
        //Frame frame = showFrame(form);
        Frame frame = mainFrame;
        // The interface abbot.finder.Matcher allows you to define whatever
        // matching specification you'd like.  We know there's only one
        // JList in the hierarchy we're searching, so we can look up by
        // class with an instance of ClassMatcher.
        Component logingForm = getFinder().find(new ClassMatcher(LogingForm.class));
        //Component jPanel = getFinder().find(new ClassMatcher(JPanel.AccessibleJComponent.class));
        Component jButtonLog = getFinder().find(new ClassMatcher(JButton.class));
        JListTester tester = new JListTester();

        
        // We could also use an instance of ClassMatcher, but this shows
        // how you can put more conditions into the Matcher.
        JButton button = (JButton)getFinder().find(new Matcher() {
            public boolean matches(Component c) {
                return c.getClass().equals(JButton.class)
                    && c.hasFocus();
            }
        });

        tester.actionClick(button);
        // Select by row index or by value
        //tester.actionSelectRow(list, new JListLocation(1));
        // tester.actionSelect(list, new JListLocation("two"));
        //assertEquals("Wrong label after selection",
        //             "Selected: two", label.getText());

        //tester.actionSelectRow(list, new JListLocation(2));
        //assertEquals("Wrong label after selection", "Selected: three", label.getText());

        //form.setMessage("Test status baru");
        //assertEquals("Wrong label after selection", "Test status baru", form.getText());*/
    }

    public MainFrameTest(String name) { super(name); }

    /*public static void main(String[] args) {
        TestHelper.runTests(args, MainFrameTest.class);
    }*/

}
