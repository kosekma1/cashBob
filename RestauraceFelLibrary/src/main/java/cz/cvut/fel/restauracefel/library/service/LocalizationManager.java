package cz.cvut.fel.restauracefel.library.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

/**
 * <p>
 * <code>LocalizationManager</code> is representing main localization class. It has
 * methods for localization of some components frow Swing framework and for showing
 * dialogs with localized messages. <strong>It uses default locale!</strong>
 * </p>
 * 
 * @author Štěpán Osmík, steve21@seznam.cz
 */
public class LocalizationManager {

    /**
     * Enum with types of messages which are used for showing messages.
     */
    public enum MessageType {

        /**
         * Error message
         */
        ERROR("ErrorMessageTitle", JOptionPane.ERROR_MESSAGE),
        /**
         * Information message
         */
        INFORMATION("InformationMessageTitle", JOptionPane.INFORMATION_MESSAGE),
        /**
         * Plain message (no icon)
         */
        PLAIN("PlainMessageTitle", JOptionPane.PLAIN_MESSAGE),
        /**
         * Warning message
         */
        WARNING("WarningMessageTitle", JOptionPane.WARNING_MESSAGE);
        private String messageTitleKey;
        private int optionPaneMessageType;

        /**
         * Constructs a new instance.
         *
         * @param messageTitleKey message title key in default resource bundle
         * @param optionPaneMessageType option pane constant for icon
         */
        private MessageType(String messageTitleKey, int optionPaneMessageType) {
            this.messageTitleKey = messageTitleKey;
            this.optionPaneMessageType = optionPaneMessageType;
        }

        /**
         * Method returns message title key in default resource bundle.
         *
         * @return message title key
         */
        public String getMessageTitleKey() {
            return messageTitleKey;
        }

        /**
         * Method returns option pane message type constant.
         *
         * @return option pane message type
         */
        public int getOptionPaneMessageType() {
            return optionPaneMessageType;
        }
    }
    /**
     * Accelerator constant for bundle
     */
    public static final String ACCELERATOR = "Accelerator";
    /**
     * Constant with default locale key
     */
    public static final String DEFAULT_LOCALE = "default-locale";
    /**
     * In locale delimiter (splits the locale into language, country and variant)
     */
    public static final String IN_LOCALE_DELIMITER = "_";
    /**
     * Locale delimiter
     */
    public static final String LOCALE_DELIMITER = ":";
    /**
     * Mnemonic constant for bundle
     */
    public static final String MNEMONIC = "Mnemonic";
    /**
     * Constant with properties file name
     */
    public static final String PROPERTIES_FILE_NAME = "config" + File.separator + "localization.ini";
    /**
     * Constant with supported locales key
     */
    public static final String SUPPORTED_LOCALES = "supported-locales";
    /**
     * Text constant for bundle
     */
    public static final String TEXT = "Text";
    /**
     * Title constant for bundle
     */
    public static final String TITLE = "Title";
    /**
     * Tool tip text for constant
     */
    public static final String TOOL_TIP_TEXT = "ToolTipText";
    /*
     * Singleton
     */
    private static LocalizationManager instance;
    /*
     * Properties for localization (default locale and supported locales)
     */
    private Properties properties;
    /*
     * Next default locale
     */
    private Locale defaultLocale;
    /*
     * Array of supported locales (locales must be defined in property file and
     * also in JVM)
     */
    private Locale[] supportedLocales;
    /*
     * Map with resource bundles
     */
    private Map<String, ResourceBundle> resourceBundles;

    /**
     * Creates an instance.
     */
    private LocalizationManager() {
        try {
            resourceBundles = new HashMap<String, ResourceBundle>();

            properties = new Properties();

            properties.load(new FileInputStream(PROPERTIES_FILE_NAME));

            Set<Locale> availableLocales = new HashSet<Locale>(Arrays.asList(Locale.getAvailableLocales()));
            Set<Locale> definedLocales = new HashSet<Locale>();

            for (String locale : properties.getProperty(SUPPORTED_LOCALES).split(LOCALE_DELIMITER)) {
                definedLocales.add(parseLocale(locale));
            }

            availableLocales.retainAll(definedLocales);
            supportedLocales = new Locale[availableLocales.size()];
            availableLocales.toArray(supportedLocales);

            defaultLocale = parseLocale(properties.getProperty(DEFAULT_LOCALE));

            Locale.setDefault(defaultLocale);
        } catch (Exception e) {
            Logger.getAnonymousLogger().log(Level.SEVERE, "Localization initialization error.", e);
            System.exit(0);
        }
    }

    /**
     * Returns next default locale.
     * 
     * @return next default locale
     */
    public Locale getDefaultLocale() {
        return defaultLocale;
    }

    /**
     * Returns instance of this class - singleton.
     *
     * @return singleton instance
     */
    public static LocalizationManager getInstance() {
        return instance == null ? instance = new LocalizationManager() : instance;
    }

    /**
     * Returns bundle with specified base name and actual locale.
     *
     * @param bundleBaseName bundle base name
     * @return specified bundle
     */
    public ResourceBundle getResourceBundle(String bundleBaseName) {
        ResourceBundle resourceBundle = resourceBundles.get(bundleBaseName);

        if (resourceBundle == null || !Locale.getDefault().equals(resourceBundle.getLocale())) {
            resourceBundle = ResourceBundle.getBundle(bundleBaseName, Locale.getDefault());
        }

        return resourceBundle;
    }

    /**
     * Method returns supported locales (locales specified in properties file intersected with
     * JVM available locales).
     *
     * @return supported locales
     */
    public Locale[] getSupportedLocales() {
        return supportedLocales;
    }

    /**
     * Method localize abstract button.
     *
     * @param bundleBaseName bundle base name
     * @param componentName component name
     * @param component localized component
     */
    public void localizeAbstractButton(String bundleBaseName, String componentName, AbstractButton component) {
        ResourceBundle resourceBundle = getResourceBundle(bundleBaseName);

        localizeComponent(bundleBaseName, componentName, component);

        if (resourceBundle.containsKey(componentName + TEXT)) {
            component.setText(resourceBundle.getString(componentName + TEXT));
        }

        if (resourceBundle.containsKey(componentName + MNEMONIC)) {
            component.setMnemonic(parseMnemonic(resourceBundle.getString(componentName + MNEMONIC)));
        }
    }

    /**
     * Method localize button.
     *
     * @param bundleBaseName bundle base name
     * @param componentName component name
     * @param component localized component
     */
    public void localizeButton(String bundleBaseName, String componentName, JButton component) {
        localizeAbstractButton(bundleBaseName, componentName, component);
    }

    /**
     * Method localize component.
     * 
     * @param bundleBaseName bundle base name
     * @param componentName component name
     * @param component localized component
     */
    public void localizeComponent(String bundleBaseName, String componentName, JComponent component) {
        ResourceBundle resourceBundle = getResourceBundle(bundleBaseName);

        if (resourceBundle.containsKey(componentName + TOOL_TIP_TEXT)) {
            component.setToolTipText(resourceBundle.getString(componentName + TOOL_TIP_TEXT));
        }
    }

    /**
     * Method localize dialog.
     *
     * @param bundleBaseName bundle base name
     * @param componentName component name
     * @param component localized component
     */
    public void localizeDialog(String bundleBaseName, String componentName, JDialog component) {
        ResourceBundle resourceBundle = getResourceBundle(bundleBaseName);

        component.setTitle(resourceBundle.getString(componentName + TITLE));
    }

    /**
     * Method localize frame.
     *
     * @param bundleBaseName bundle base name
     * @param componentName component name
     * @param component localized component
     */
    public void localizeFrame(String bundleBaseName, String componentName, JFrame component) {
        ResourceBundle resourceBundle = getResourceBundle(bundleBaseName);

        component.setTitle(resourceBundle.getString(componentName + TITLE));
    }

    /**
     * Method localize label.
     *
     * @param bundleBaseName bundle base name
     * @param componentName component name
     * @param component localized component
     */
    public void localizeLabel(String bundleBaseName, String componentName, JLabel component) {
        ResourceBundle resourceBundle = getResourceBundle(bundleBaseName);

        localizeComponent(bundleBaseName, componentName, component);

        component.setText(resourceBundle.getString(componentName + TEXT));
    }

    /**
     * Method localize menu.
     *
     * @param bundleBaseName bundle base name
     * @param componentName component name
     * @param component localized component
     */
    public void localizeMenu(String bundleBaseName, String componentName, JMenu component) {
        localizeMenuItem(bundleBaseName, componentName, component);
    }

    /**
     * Method localize menu item.
     *
     * @param bundleBaseName bundle base name
     * @param componentName component name
     * @param component localized component
     */
    public void localizeMenuItem(String bundleBaseName, String componentName, JMenuItem component) {
        ResourceBundle resourceBundle = getResourceBundle(bundleBaseName);

        localizeAbstractButton(bundleBaseName, componentName, component);

        if (resourceBundle.containsKey(componentName + ACCELERATOR)) {
            component.setAccelerator(parseAccelerator(resourceBundle.getString(componentName + ACCELERATOR)));
        }
    }

    /**
     * Returns accelerator represented in the string.
     *
     * @param accelerator parsed string
     * @return accelerator
     */
    public KeyStroke parseAccelerator(String accelerator) {
        return KeyStroke.getKeyStroke(accelerator);
    }

    /**
     * Returns locale represented in the string.
     *
     * @param locale parsed locale
     * @return locale
     */
    private Locale parseLocale(String locale) {
        String[] strings = locale.split(IN_LOCALE_DELIMITER);
        switch (strings.length) {
            case 1:
                return new Locale(strings[0]);
            case 2:
                return new Locale(strings[0], strings[1]);
            case 3:
                return new Locale(strings[0], strings[1], strings[2]);
            default:
                return null;
        }
    }

    /**
     * Returns mnemonic represented in the string.
     *
     * @param mnemonic parsed string
     * @return mnemonic
     */
    public char parseMnemonic(String mnemonic) {
        return KeyStroke.getKeyStroke(mnemonic).getKeyChar();
    }

    /**
     * Method sets next default locale. All changes will be visible after
     * restart of application.
     *
     * @param defaultLocale next default locale
     */
    public void setDefaultLocale(Locale defaultLocale) throws IOException {
        this.defaultLocale = defaultLocale;

        properties.setProperty(DEFAULT_LOCALE, defaultLocale.toString());

        properties.store(new FileOutputStream(PROPERTIES_FILE_NAME), new Date().toString());
    }

    /**
     * Method show dialog with message.
     *
     * @param messageType type of message (error, information, plain or warning)
     * @param bundleBaseName bundle base name (bundle with message)
     * @param message message
     * @param arguments arguments which should be inserted in the localized message
     */
    public void showMessage(MessageType messageType, String bundleBaseName, String message, Object... arguments) {
        String title = getResourceBundle(bundleBaseName).getString(messageType.getMessageTitleKey());
        JOptionPane.showMessageDialog(null, MessageFormat.format(getResourceBundle(bundleBaseName).getString(message), arguments), title, messageType.getOptionPaneMessageType());
    }
}
