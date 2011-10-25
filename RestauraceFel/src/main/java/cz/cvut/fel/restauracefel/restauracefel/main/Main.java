package cz.cvut.fel.restauracefel.restauracefel.main;

import cz.cvut.fel.restauracefel.restauracefel.Controller.Controller;

/**
 * Hlavni trida spolecneho rozhranni pro system Restaurace.
 *
 * @author Tomas Hnizdil
 */
public class Main {

    public static final String DEFAULT_BUNDLE_BASE_NAME = "cz.cvut.fel.restauracefel.localization.restaurace_fel_bundle";
    public static final String MESSAGE_BUNDLE_BASE_NAME = "cz.cvut.fel.restauracefel.localization.restaurace_fel_message_bundle";

    public static void main(String[] args) {

        Controller controller = Controller.getInstance();
        controller.run();
    }
}
