package tma02q2;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.*;

/**
 *
 * @author M257 Course Team
 *
 * This class extends the TelephoneGUI class for adding behaviour to the GUI.
 *
 */
public class TelephoneWithBehaviour extends TelephoneGUI
{

    // ----------CHANGES HERE -----------------------------------------
    // TODO: behaviour methods/classes needed here
    /**
     * TODO: complete this constructor to associate the GUI elements
     * with necessary behaviours
     */
    public TelephoneWithBehaviour()
    {
        KeyAdapter adapter;
        // initialise the memory of numbers
        for (int i = 0; i < KEYS.length; i++)
        {
            memory.put(KEYS[i], "");
        }
        memory.put(currentKey, "");

        // add button behaviours
        int k = 0;
        for (int j = 0; j < 4; j++)
        {
            for (int i = 0; i < 5; i++)
            {
                JButton b = (JButton) dialButtons[k];
                b.addActionListener(new ButtonWatcher(k));
                k++;
            }
        }
        adapter = new KeyWatcher();
        setFocusable(true);
        MenuWatcher mw = new MenuWatcher();

        // TODO: quit the application by closing the frame window
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // TODO: associate the menu items with appropriate behaviour
        for(int i = 0; i < DIGITS.length(); i++)
        {
            if (!PROMPTS[i].equals(" "))
            {
                menuItems[i].addActionListener(mw);
            }
        }        
        exit.addActionListener(mw);

        // TODO: add keyboard listeners
        addKeyListener(adapter);

        // TODO: make sure all the buttons have the same key event adapter
        for(JButton eachButton : dialButtons)
        {
            eachButton.addKeyListener(adapter);
        }
    }

    // TODO Process the single character command
    private void processingCommand(char c)
    {
        if (c == '?')
        {
            message("The command is undefined !");
            // no action should be taken - else things like
           // shift keys will cause a problem!
        }
        else
        {
            // TODO retrieve the current number
            String currentNumber = memory.get("M");

            if (c == 'D')// dialling
            {
                // TODO dial the current number
                dialling(currentNumber);

            }
            else if (c == 'M' || c == 'A' || c == 'B' || c == 'C')
            // either current number or a memory selected
            {
                int currentButtonIndex = DIGITS.indexOf(currentKey.charAt(0));
                int newButtonIndex = DIGITS.indexOf(c);
                if (!currentKey.equals("" + c))
                {
                    dialButtons[currentButtonIndex].setForeground(Color.BLACK);
                    dialButtons[newButtonIndex].setForeground(Color.RED);
                    currentKey = "" + c;
                    updateStatus();
                }
                else if (!currentKey.equals(""+CURRENT_MEMORY.charAt(0)))
                {
                    //same key selected so toggle and select CURRENT_NUMBER_KEY
                    dialButtons[newButtonIndex].setForeground(Color.BLACK);
                    dialButtons[0].setForeground(Color.RED);
                    currentKey = CURRENT_MEMORY;
                    updateStatus();
                }
            }
            else if (c == 'L')
            {
                // TODO load the selected memory into the current number for dialling
                memory.put(CURRENT_MEMORY, memory.get(currentKey));
                updateStatus();
            }
            else if (c == 'S')
            {
                // TODO store the current number to current memory key
                memory.put(currentKey, memory.get(CURRENT_MEMORY));
                updateStatus();
            }
            else if (c == 'R')
            {
                // TODO reset the current key to empty
                memory.put(currentKey, "");
                updateStatus();
            }
            else
            {
                // TODO: otherwise the DIGITS shall be simply appended to current number
                String phoneNumber = memory.get(CURRENT_MEMORY);
                phoneNumber = phoneNumber + Character.toString(c);
                memory.put(CURRENT_MEMORY, phoneNumber);
                updateStatus();
                message(Character.toString(c));
            }
        }
    }

    /**
     * Class to process the key pressed event, the event key is first
     * translated to the upper case, then processed if it is one of the
     * commands
     */
    private class KeyWatcher extends KeyAdapter
    {
        @Override
        public void keyPressed(KeyEvent k)
        {
           // TODO complete the method
            Character key = Character.toUpperCase(k.getKeyChar());
            for(int i = 0; i < DIGITS.length(); i++)
            {
                if(key == DIGITS.charAt(i))
                {
                    processingCommand(key);
                }
            }
        }
    }

    /**
     * Class to process the menu item events, the first letter of the
     * label will be used to obtain the command from the array of commands.
     * If the label is "Exit", then quit the program.
     */
    private class MenuWatcher implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
           // TODO complete the method
            for(int i = 0; i < menuItems.length; i++)
            {
                if(menuItems[i] == e.getSource())
                {
                    processingCommand(menuItems[i].getText().charAt(0));
                }
            }
            if(e.getSource() == exit)
            {
                System.exit(0);
            }
        }
    }

    /**
     * Class to process the button pressed event, the index of the
     * buttons will be used to obtain the command from the array of commands
     */
    private class ButtonWatcher implements ActionListener
    {
        private int command = 0;
        private ButtonWatcher(int k)
        {
            command = k;
        }
        @Override
        public void actionPerformed(ActionEvent e)
        {
            // TODO complete the method
            Object buttonPressed = e.getSource();
            if(buttonPressed.equals(dialButtons[command]))
            {
                processingCommand(DIGITS.charAt(command));
            }
        }
    }
}
