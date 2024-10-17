import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;


/**
 * Menu class handles the menu screen that is shown when the program is run.
 */
public class Menu {

    private static JFrame frame = new JFrame("Mazesweeper Startup");

    // button ActionListeners
    StartButtonEventHandler startListener = new StartButtonEventHandler();
    RulesButtonEventHandler rulesListener = new RulesButtonEventHandler();
    QuitButtonEventHandler quitListener = new QuitButtonEventHandler();

    // button layout constraints. Used to configure the layout manager.
    GridBagConstraints constraints = new GridBagConstraints();

    // Variables for scaling with screen size
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    // frame dimensions depending on screen size
    float screenSizeMultiplier = 0.625f; // frame size compared to the screen size. Default: 0.625f
    int frameWidth = (int) Math.round(screenSize.getWidth() * screenSizeMultiplier);
    int frameHeight = (int) Math.round(screenSize.getHeight() * screenSizeMultiplier);
    // button dimensions depending on screen size
    int buttonWidth = (int) Math.round(screenSize.getWidth() * screenSizeMultiplier * 0.15);
    int buttonHeight = (int) Math.round(screenSize.getHeight() * screenSizeMultiplier * 0.06);
    Dimension buttonDimension = new Dimension(buttonWidth, buttonHeight);
    int buttonFontSize = (int) Math.round(buttonWidth / 12);
    // amount of pixels between buttons depending on screen size
    int topInset = (int) Math.round(240 * screenSizeMultiplier);
    int leftInset = (int) Math.round(16 * screenSizeMultiplier);
    int bottomInset = (int) Math.round(16 * screenSizeMultiplier);
    int rightInset = (int) Math.round(16 * screenSizeMultiplier);

    /**
     * Sets up the start menu screen.
     */
    public Menu() {
        // frame init
        frame.setLayout(new GridBagLayout());
        //sets window size relative to user's screen size
        frame.setSize(frameWidth, frameHeight);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.getContentPane().setBackground(Mazesweeper.DARK_GREEN);

        // GridBagConstraint config.
        constraints.insets = new Insets(topInset, leftInset, bottomInset, rightInset);
        constraints.fill = GridBagConstraints.BOTH;

        // start button init
        JButton startButton = new JButton("START");
        startButton.setBackground(Mazesweeper.LIGHT_GREEN);
        startButton.addActionListener(startListener);
        startButton.setPreferredSize(buttonDimension);
        startButton.setFont(new Font("Dialog", Font.BOLD, buttonFontSize));
        frame.add(startButton, constraints);

        // rules button init
        JButton rulesButton = new JButton("HOW TO PLAY");
        rulesButton.setBackground(Mazesweeper.LIGHT_GREEN);
        rulesButton.addActionListener(rulesListener);
        rulesButton.setPreferredSize(buttonDimension);
        rulesButton.setFont(new Font("Dialog", Font.BOLD, buttonFontSize));
        constraints.insets.top = bottomInset; // sets top inset to be same as other insets
        constraints.gridy = 1; // gridy of start button is 0, rules button is 1 lower
        frame.add(rulesButton, constraints);

        // quit button init
        JButton quitButton = new JButton("QUIT");
        quitButton.setBackground(Mazesweeper.LIGHT_GREEN);
        quitButton.addActionListener(quitListener);
        quitButton.setPreferredSize(buttonDimension);
        quitButton.setFont(new Font("Dialog", Font.BOLD, buttonFontSize));
        constraints.gridy = 2; // gridy of rules button is 1, quit button is 1 lower
        frame.add(quitButton, constraints);
        frame.setVisible(true);
    }


    public static void hideFrame() {
        frame.setVisible(false);
    }
    
    public static void showFrame() {
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {

            Menu startMenu = new Menu();

        });
    }
}

// start game when start is pressed
class StartButtonEventHandler implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        SwingUtilities.invokeLater(() -> {

            Menu.hideFrame();

            Mazesweeper game = new Mazesweeper();
            
        });
    }
}

// show rules screen when "how to play" is pressed
class RulesButtonEventHandler implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        //TODO add rules screen
        System.out.println("The button works!");
    }
}

// exit program when quit is pressed
class QuitButtonEventHandler implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        System.exit(0);
    }
}
