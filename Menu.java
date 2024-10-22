import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import java.awt.Graphics;
import java.io.File;
import java.awt.BorderLayout;
import java.io.*;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
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

        // Setup of background image
        try {
            final Image MazesweeperBackground = javax.imageio.ImageIO.read(new File("Mazesweeper.png"));
            BackgroundPanel backgroundPanel = new BackgroundPanel(MazesweeperBackground, frame);
            frame.setContentPane(backgroundPanel);
        // The line where it reads the file really wants IOException to be handled
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // frame init
        frame.setLayout(new GridBagLayout());
        //sets window size relative to user's screen size
        frame.setSize(frameWidth, frameHeight);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        //label.setSize(frame.getSize());
        //frame.setContentPane(label);
        //frame.getContentPane().setBackground(Mazesweeper.DARK_GREEN);

        // GridBagConstraint config.
        constraints.insets = new Insets(topInset, leftInset, bottomInset, rightInset);
        constraints.fill = GridBagConstraints.BOTH;

        // start button init
        JButton startButton = new JButton("START");
        startButton.setBackground(Mazesweeper.LIGHT_BEIGE);
        startButton.addActionListener(startListener);
        startButton.setPreferredSize(buttonDimension);
        startButton.setFont(new Font("Dialog", Font.BOLD, buttonFontSize));
        // mouselistener for changing colour when hovering over it
        startButton.addMouseListener(new ButtonMouseListener(startButton));
        frame.add(startButton, constraints);

        // rules button init
        JButton rulesButton = new JButton("HOW TO PLAY");
        rulesButton.setBackground(Mazesweeper.LIGHT_BEIGE);
        rulesButton.addActionListener(rulesListener);
        rulesButton.setPreferredSize(buttonDimension);
        rulesButton.setFont(new Font("Dialog", Font.BOLD, buttonFontSize));
        constraints.insets.top = bottomInset; // sets top inset to be same as other insets
        constraints.gridy = 1; // gridy of start button is 0, rules button is 1 lower
        // mouselistener for changing colour when hovering over it
        rulesButton.addMouseListener(new ButtonMouseListener(rulesButton));
        frame.add(rulesButton, constraints);

        // quit button init
        JButton quitButton = new JButton("QUIT");
        quitButton.setBackground(Mazesweeper.LIGHT_BEIGE);
        quitButton.addActionListener(quitListener);
        quitButton.setPreferredSize(buttonDimension);
        quitButton.setFont(new Font("Dialog", Font.BOLD, buttonFontSize));
        constraints.gridy = 2; // gridy of rules button is 1, quit button is 1 lower
        // mouselistener for changing colour when hovering over it
        quitButton.addMouseListener(new ButtonMouseListener(quitButton));
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

class BackgroundPanel extends JPanel {

    private Image backgroundImage;
    private JFrame frame;

    /**
     * Constructor, to access host frame properties and the image being used.
     * @param backgroundImage The image to draw
     * @param frame The frame this will be the background for, used to set dimensions
     */
    BackgroundPanel(Image backgroundImage, JFrame frame) {
        this.backgroundImage = backgroundImage;
        this.frame = frame;
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.drawImage(backgroundImage, 0, 0, frame.getWidth(), frame.getHeight(), null);
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

class ButtonMouseListener implements MouseListener {
    
    private JButton button;

    ButtonMouseListener(JButton button) {
        this.button = button;
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        button.setBackground(Mazesweeper.DARK_BEIGE);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        button.setBackground(Mazesweeper.LIGHT_BEIGE);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }
}
