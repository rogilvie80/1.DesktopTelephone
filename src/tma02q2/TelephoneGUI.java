package tma02q2;

import java.awt.*;
import java.util.HashMap;
import javax.swing.*;

/**
 *
 * @author M257 Course Team
 *
 * The auxiliary class to create the graphical user interface (GUI) of the Telephone.
 *
 * Besides the constructor, it also provides some auxiliary methods to be
 * used by the TelephoneWithBehaviour subclass
 *
 * No change is needed.
 */
public class TelephoneGUI extends JFrame
{
    // declare private constants and variables
    // declare the size of the window

    private static final int FRAME_WIDTH = 500;
    private static final int FRAME_HEIGHT = 360;
    // declare the title of the window
    private static final String TITLE = "M257 Team's Phone";
    // declare protected constants and variables
    // declare the labels to be shown on the buttons
    protected static final String DIGITS = "M123DA456LB789SC*0#R";
    // declare the prompt labels to be shown above the buttons
    protected static final String PROMPTS[] =
    {
        "Current Memory", " ", " ", " ", "Dial ",
        "Memory A", " ", " ", " ", "Load ",
        "Memory B", " ", " ", " ", "Store ",
        "Memory C", " ", " ", " ", "Reset "
    };
    // declare the memory KEYS
    protected static final String CURRENT_MEMORY = "M";
    protected static final String KEYS[] =
    {
        CURRENT_MEMORY // the default key
        , "A", "B", "C"
    };
    // declare private constants and variables
    protected String currentKey; // initially the key is the default
    // the lookup table to associate a number with a memory key
    protected HashMap<String, String> memory;
    // declare the GUI elements
    // declare menubar, menu and menu items
    private JMenuBar mb;
    private JMenu fileMenu;
    // the menu items are corresponding to the commands.
    protected JMenuItem menuItems[];
    protected JMenuItem exit;
    // declare panels
    private JPanel background;
    private JPanel topPanel;
    private JScrollPane pane;
    private JLabel statusLabel;
    private JTextPane messageText;
    private JPanel bottomPanel;
    private JLabel dialLabels[];
    private JPanel dialPanels[];
    protected JButton dialButtons[];

    /**
     *  Constructor: set up frame window
     */
    public TelephoneGUI()
    {
        super();
        currentKey = CURRENT_MEMORY;
        memory = new HashMap<String, String>();
        menuItems = new JMenuItem[DIGITS.length()];
        dialLabels = new JLabel[PROMPTS.length];
        dialPanels = new JPanel[DIGITS.length()];
        dialButtons = new JButton[PROMPTS.length];
        setTitle(TITLE);
        setSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
        // create menu bar, menu and menu items and add
        mb = new JMenuBar();
        fileMenu = new JMenu("File");
        // add the menu items corresponding to the commands
        for (int i = 0; i < DIGITS.length(); i++)
        {
            if (!PROMPTS[i].equals(" ")) {
                menuItems[i] = new JMenuItem(DIGITS.charAt(i) + " -- " + PROMPTS[i]);
                fileMenu.add(menuItems[i]);
            }
        }
        // create the exit command
        exit = new JMenuItem("Exit");
        fileMenu.add(exit);
        mb.add(fileMenu);
        setJMenuBar(mb);

        // create panels and set layouts
        background = new JPanel();
        background.setLayout(new BorderLayout());
        this.add(background);
        topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());
        background.add("North", topPanel);
        statusLabel = new JLabel();
        updateStatus(); // show the current value of the memory KEYS
        messageText = new JTextPane();
        messageText.setEditable(false);
        messageText.setAutoscrolls(true);
        pane = new JScrollPane(messageText);
        pane.setPreferredSize(new Dimension(FRAME_WIDTH - 10,
                FRAME_HEIGHT / 3));
        topPanel.add(BorderLayout.NORTH, statusLabel);
        topPanel.add(BorderLayout.CENTER, pane);
        bottomPanel = new JPanel();
        background.add("South", bottomPanel);
        bottomPanel.setLayout(new GridLayout(4, 5));
        int k = 0;
        final Font FONT = new Font("Helvetica", Font.ITALIC, 12);
        for (JLabel label : dialLabels)
        {
            //assign new object to current label, button, panel
            label = new JLabel(PROMPTS[k]);
            dialButtons[k] = new JButton(DIGITS.substring(k, k + 1));
            dialPanels[k] = new JPanel();

            label.setFont(FONT);
            dialPanels[k].setLayout(new BoxLayout(dialPanels[k], BoxLayout.Y_AXIS));
            dialPanels[k].add("North", label);
            dialPanels[k].add("South", dialButtons[k]);
            bottomPanel.add(dialPanels[k]);

            k++;
        }
        dialButtons[0].setForeground(Color.red);
        setResizable(false);
   }

    /**
     * Append the prompt to the messageText pane
     * @param msg
     */
    public void message(String msg)
    {
        String text = messageText.getText() + msg;
        messageText.setText(text);
        updateStatus();
    }

    /**
     * update the display of the memory status by showing the
     * current numbers stored in the KEYS inside square brackets
     */
    public void updateStatus()
    {
        String numbers = "";
        for (int i = 0; i < KEYS.length; i++)
        {
            //if there is a number stored in memory
            if (memory.get(KEYS[i]) != null)
            {
                numbers += KEYS[i] + " [ " + memory.get(KEYS[i]) + " ] ";
            }
            else //no number stored in memory
            {
                numbers += KEYS[i] + " [ " + " ] ";
            }
        }
        statusLabel.setText(" " + numbers);
    }

    /**
     * Simulating the dialling out function
     * @param currentNumber -- the current number to be dialled
     */
    protected void dialling(String currentNumber)
    {
        if (currentNumber.equals(""))
        {
            message("Please dial the number first\n");
        }
        else
        {
            Thread dialling = new Thread(new DisplayNumbers(currentNumber, this));
            dialling.start();
        }
    }
}
