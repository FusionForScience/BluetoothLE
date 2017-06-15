package edu.mst.bluetoothle;


import android.content.Context;
import android.util.Log;

import java.io.FileInputStream;
import java.io.FileOutputStream;


/**
 * Class used to manage a data file.
 */
class DataFile
{
    private Main mainActivity;                  // A copy of the main activity to use
    private String filename;                    // The name of our data file

    private final String BREAK_CHAR = "$";      // A symbol that denotes when one piece of info
                                                // stops and another one starts

    /**
     * Creates a dataFile.
     *
     * @param ACTIVITY The main activity
     * @param FILE_NAME The name of the file
     */
    DataFile(final Main ACTIVITY , final String FILE_NAME)
    {
        mainActivity = ACTIVITY;
        filename = FILE_NAME;
    }


    /**
     * Function that reads from a file and converts the data into something meaningful. It will
     * get the ID of the phone, its nickname, its charge, and how many devices are attached.
     */
    void updateFromFile()
    {
        String baseString = "";         // The original string obtained from the file

        FileInputStream inputStream;    // Stream used to read from the file

        byte[] bytes;                   // Array of bytes used to get stuff from the stream


        // Open the file and get all of the info
        try
        {
            inputStream = mainActivity.openFileInput(filename);
            bytes = new byte[(int)inputStream.getChannel().size()];
            inputStream.read(bytes);
            baseString = new String(bytes);
            inputStream.close();
            Log.i("Data File Input" , baseString);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        updateDevices(baseString);
    }


    /**
     * A testing function that writes data to a file that I edit here. This is so I can test
     * layouts and such.
     */
    void updateToFile()
    {
        final int numPhones = 5;        // Constant set by me to add and subtract phones as I please

        FileOutputStream outputStream;  // Stream we use to output to the file

        String output = "";             // String that we're outputting to the file. This function
                                        // modifies the string.


        // For loop that just creates incremental "IDs" because I'm too lazy to go randomly generate
        // them. It also assigns the nickname.
        // The format is as such: ID[BREAK]Nickname\n
        // These are the only values we need to store because we want to able to recognize previous
        // devices and user-set nicknames cannot be accessed through Bluetooth.

        for(int i = 1; i <= numPhones; i ++)
        {
            // For testing assume the charge is always 100

            output += String.valueOf(i) + BREAK_CHAR + "Device" + String.valueOf(i) + "\n";
        }


        // Opens the file and writes the output string to it.
        try
        {
            outputStream = mainActivity.openFileOutput(filename , Context.MODE_PRIVATE);
            outputStream.write(output.getBytes());
            outputStream.close();
            Log.i("Data File Output" , output);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }


    /**
     * Takes the String extracted from the data file and creates the appropriate devices.
     */
    private void updateDevices(String dataString)
    {
        String deviceString;            // The original data string contains data from all devices,
                                        // so this is a string that holds data for one

        for(int i = 0; dataString.length() > 0; i ++)
        {
            // Grabs the first line of data, then deletes it from the first block
            deviceString = dataString.substring(0 , dataString.indexOf("\n"));
            dataString = dataString.substring(dataString.indexOf("\n") + 1);


            // Adds a new device by taking the string until the break. This should be the ID.
            mainActivity.devices.add(new Device(deviceString.substring(0 ,
                                     deviceString.indexOf(BREAK_CHAR))));


            // Sets the nickname of the device by taking the other half of the device string.
            mainActivity.devices.get(i).setNickname(deviceString.substring(deviceString.
                                                    indexOf(BREAK_CHAR) + 1));

            // Debugging
            Log.i("Device ID" , mainActivity.devices.get(i).id());
            Log.i("Device Nickname" , mainActivity.devices.get(i).nickname() + "\n");
        }
    }
}
