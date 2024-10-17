import java.awt.Dimension;
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
    StartButtonEventHandler startListener = new StartButtonEventHandler();
    RulesButtonEventHandler rulesListener = new RulesButtonEventHandler();
    QuitButtonEventHandler quitListener = new QuitButtonEventHandler();
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    /**
     * Sets up the start menu screen.
     */
    public Menu() {
        
        frame.setLayout(null);
        //sets window size relative to user's screen size
        frame.setSize((int) Math.round(screenSize.getWidth() / 1.6), 
            (int) Math.round(screenSize.getHeight() / 1.6));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.getContentPane().setBackground(Mazesweeper.DARK_GREEN);

        JButton startButton = new JButton("START");
        startButton.setBackground(Mazesweeper.LIGHT_GREEN);
        startButton.setSize(200, 50);
        startButton.setLocation(0, 0);
        startButton.addActionListener(startListener);
        frame.add(startButton);

        JButton rulesButton = new JButton("HOW TO PLAY");
        rulesButton.setBackground(Mazesweeper.LIGHT_GREEN);
        rulesButton.setSize(200, 50);
        rulesButton.setLocation(200, 50);
        rulesButton.addActionListener(rulesListener);
        frame.add(rulesButton);

        JButton quitButton = new JButton("QUIT");
        quitButton.setBackground(Mazesweeper.LIGHT_GREEN);
        quitButton.setSize(200, 50);
        quitButton.setLocation(400, 100);
        quitButton.addActionListener(quitListener);
        frame.add(quitButton);



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

class RulesButtonEventHandler implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        //TODO add rules screen
        System.out.println("The button works!");
    }
}

class QuitButtonEventHandler implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        System.exit(0);
    }
}
