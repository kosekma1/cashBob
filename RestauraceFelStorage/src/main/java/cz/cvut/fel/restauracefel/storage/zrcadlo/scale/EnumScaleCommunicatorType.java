package cz.cvut.fel.restauracefel.storage.zrcadlo.scale;

/**
 *
 * @author Vojta
 */
public enum EnumScaleCommunicatorType {
    COM("COM");

    private final String type;

    private EnumScaleCommunicatorType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }


}
