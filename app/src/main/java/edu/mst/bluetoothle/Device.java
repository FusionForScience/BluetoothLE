package edu.mst.bluetoothle;

/**
 * Class used to manage a particular device connected to the charging mat.
 */
class Device
{
    // Default values for some of the values below
    private final String _DEFAULT_NAME = "Unknown Device";
    private final String _DEFAULT_NICKNAME = "My Device";
    private final int _DEFAULT_CHARGE = 50;
    private final boolean _IS_CONNECTED = false;

    private String _id;             // Device-specific ID (Bluetooth address)
    private String _name;           // Name of the device
    private String _nickname;       // User-created nickname of the device
    private int _charge;            // Charge percentage of the device
    private boolean _isConnected;   // Whether the device is connected or not


    /**
     * Creates a device with the specified ID
     *
     * @param ID ID of the device (Bluetooth address)
     */
    Device(final String ID)
    {
        _id = ID;
    }


    /**
     * Returns the nickname of the device
     *
     * @return The nickname of the device
     */
    String nickname()
    {
        return _nickname;
    }


    /**
     * Sets the nickname of the device.
     *
     * @param NAME The nickname to set to the device.
     */
    void setNickname(final String NAME)
    {
        _nickname = NAME;
    }


    /**
     * Returns the device ID
     *
     * @return
     */
    String id()
    {
        return _id;
    }
}
