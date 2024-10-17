import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
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

    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    /**
     * Sets up the start menu screen.
     */
    public Menu() {
        
        // frame init
        frame.setLayout(new GridBagLayout());
        //sets window size relative to user's screen size
        frame.setSize((int) Math.round(screenSize.getWidth() / 1.6), 
            (int) Math.round(screenSize.getHeight() / 1.6));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.getContentPane().setBackground(Mazesweeper.DARK_GREEN);


        // start button init
        JButton startButton = new JButton("START");
        startButton.setBackground(Mazesweeper.LIGHT_GREEN);
        startButton.addActionListener(startListener);
        constraints.insets = new Insets(150, 10, 10, 10);
        frame.add(startButton, constraints);

        // rules button init
        JButton rulesButton = new JButton("HOW TO PLAY");
        rulesButton.setBackground(Mazesweeper.LIGHT_GREEN);
        rulesButton.addActionListener(rulesListener);
        constraints.insets.top = 10;
        constraints.gridy = 1;
        frame.add(rulesButton, constraints);

        // quit button init
        JButton quitButton = new JButton("QUIT");
        quitButton.setBackground(Mazesweeper.LIGHT_GREEN);
        quitButton.addActionListener(quitListener);
        constraints.gridy = 2;
        frame.add(quitButton, constraints);



        frame.setVisible(true);
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {

            //Mazesweeper game = new Mazesweeper();
            Menu startMenu = new Menu();

        });
    }

    public static void hideFrame() {
        frame.setVisible(false);
    }
}

// start game when start is pressed
class StartButtonEventHandler implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        Menu.hideFrame();
        Mazesweeper game = new Mazesweeper();
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
