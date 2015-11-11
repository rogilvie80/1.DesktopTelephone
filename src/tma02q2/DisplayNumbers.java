package tma02q2;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;

/**
 * @author M257 course team
 *
 * The auxiliary class to simulate the dialling out function
 *
 * It is implemented as a runnable so that the DIGITS are printed out one
 * by one every second. Upon finishing, the "OK!" will be printed.
 *
 * No change is needed.
 */
class DisplayNumbers implements Runnable
{

    // the numbers to be dialed as a message
    private String msg;
    // the GUI interface where the message will be shown
    private TelephoneGUI gui;

    // constructor to pass the parameters
    public DisplayNumbers(String numbers, TelephoneGUI theGui)
    {
        gui = theGui;
        msg = numbers;
    }

    @Override
    public void run()
    {
        int n = msg.length();
        // disable the dialing buttons
        for (JButton b: gui.dialButtons) {
            b.setEnabled(false);
        }
        // display the dialling number
        gui.message("Dialling ");
        // handle one number at a time
        for (int i = 0; i < n; i++)
        {
            try
            {
                Thread.sleep(1000); // 1000 ms = 1 second
                gui.message("" + msg.charAt(i));
            } 
            catch (InterruptedException ex)
            {
                // In case the dialling was interrupted
                Logger.getLogger(TelephoneGUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        // confirm the end of dialling is OK
        gui.message("... OK!\n");
        // restore the dialling buttons
        for (JButton b: gui.dialButtons)
         b.setEnabled(true);
    }
}
