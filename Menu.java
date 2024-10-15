import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;


/**
 * Menu class handles the menu screen that is shown when the program is run.
 */
public class Menu {


    private JFrame frame = new JFrame("Mazesweeper Startup");
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    public Menu() {
        
        frame.setLayout(null);
        //sets window size relative to user's screen size
        frame.setSize((int) Math.round(screenSize.getWidth() / 1.6), 
            (int) Math.round(screenSize.getHeight() / 1.6));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(true);
        frame.getContentPane().setBackground(Mazesweeper.DARK_GREEN);

        JButton startButton = new JButton("START");
        startButton.setBackground(Mazesweeper.LIGHT_GREEN);
        startButton.setSize(200, 50);
        startButton.setLocation(0, 0);
        frame.add(startButton);



        frame.setVisible(true);
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {

            //Mazesweeper game = new Mazesweeper();
            Menu startMenu = new Menu();


        });
    }
}

