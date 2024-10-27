import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent; 
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;


/**
 * Menu class handles the menu screen that is shown when the program is run.
 * 
 * @author Gunnar Johansson
 * @ID 2146444
 * @author Adam Bekesi
 * @ID 2147548
 */
public class Menu {

    private static final JFrame FRAME = new JFrame("Mazesweeper | Startup"); 

    // button ActionListeners
    StartButtonEventHandler startListener = new StartButtonEventHandler();
    // RulesButtonEventHandler rulesListener = new RulesButtonEventHandler();
    QuitButtonEventHandler quitListener = new QuitButtonEventHandler();

    // button layout constraints. Used to configure the layout manager.
    GridBagConstraints constraints = new GridBagConstraints();

    // Variables for scaling with screen size
    public static final Dimension SCREEN_SIZE = Toolkit.getDefaultToolkit().getScreenSize();
    // frame size compared to the screen size. Default: 0.625f
    public static final float SCREEN_SIZE_MULTIPLIER = 0.625f; 
    // frame dimensions depending on screen size
    public static int frameWidth =
        (int) Math.round(SCREEN_SIZE.getWidth() * SCREEN_SIZE_MULTIPLIER);
    public static int frameHeight =
        (int) Math.round(SCREEN_SIZE.getHeight() * SCREEN_SIZE_MULTIPLIER);
    // button dimensions depending on screen size
    static final int BUTTON_WIDTH =
        (int) Math.round(SCREEN_SIZE.getWidth() * SCREEN_SIZE_MULTIPLIER * 0.15);
    static final int BUTTON_HEIGHT =
        (int) Math.round(SCREEN_SIZE.getHeight() * SCREEN_SIZE_MULTIPLIER * 0.06);
    public static Dimension buttonDimension = new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT);
    public static int buttonFontSize = (int) Math.round(BUTTON_WIDTH / 12);
    // amount of pixels between buttons depending on screen size
    public static int topInset = (int) Math.round(240 * SCREEN_SIZE_MULTIPLIER);
    public static int leftInset = (int) Math.round(16 * SCREEN_SIZE_MULTIPLIER);
    public static int bottomInset = (int) Math.round(16 * SCREEN_SIZE_MULTIPLIER);
    public static int rightInset = (int) Math.round(16 * SCREEN_SIZE_MULTIPLIER);

    /**
     * Sets up the start menu screen.
     */
    public Menu() {

        // Setup of background image
        try {
            final Image MazesweeperBackground = ImageIO.read(new File("Mazesweeper.png"));
            BackgroundPanel backgroundPanel = new BackgroundPanel(MazesweeperBackground, FRAME);
            FRAME.setContentPane(backgroundPanel);
        } catch (IOException e) {
            System.err.println("ERROR: MISSING MAIN MENU BACKGROUND IMAGE!");
            e.printStackTrace();
        }

        // frame init
        FRAME.setLayout(new GridBagLayout());
        //sets window size relative to user's screen size
        FRAME.setSize(frameWidth, frameHeight);
        FRAME.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        FRAME.setResizable(false);
        int frameX = (SCREEN_SIZE.width - frameWidth) / 2;
        int frameY = (SCREEN_SIZE.height - frameHeight) / 2;
        FRAME.setLocation(frameX, frameY);

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
        FRAME.add(startButton, constraints);

        constraints.insets.top = bottomInset; // sets top inset to be same as other insets


        // quit button init
        JButton quitButton = new JButton("QUIT");
        quitButton.setBackground(Mazesweeper.LIGHT_BEIGE);
        quitButton.addActionListener(quitListener);
        quitButton.setPreferredSize(buttonDimension);
        quitButton.setFont(new Font("Dialog", Font.BOLD, buttonFontSize));
        constraints.gridy = 2; // gridy of rules button is 1, quit button is 1 lower
        // mouselistener for changing colour when hovering over it
        quitButton.addMouseListener(new ButtonMouseListener(quitButton));
        FRAME.add(quitButton, constraints);

        FRAME.setVisible(true);
    }


    public static void hideFrame() {
        FRAME.setVisible(false);
    }
    
    public static void showFrame() {
        FRAME.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {

            Menu startMenu = new Menu();

        });
    }
}

class BackgroundPanel extends JPanel {

    private final Image backgroundImage;
    private final JFrame frame;

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

        Menu.hideFrame();

        SettingsScreen settings = new SettingsScreen();

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
    
    private final JButton button;

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
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}
}
