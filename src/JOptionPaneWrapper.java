import javax.swing.*;

/*
    Wrapper class for JOptionPane
    Makes it easier to stub showErrorMessageDialog() during testing.
 */
public class JOptionPaneWrapper {
    public static void showErrorMessageDialog(String title, String message) {
        JOptionPane.showMessageDialog(null, message, title, JOptionPane.ERROR_MESSAGE);
    }
}
