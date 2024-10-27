import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent; 
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;

/**
 * Class describing the setting screen before a game of Mazesweeper.
 * 
 * @author Gunnar Johansson
 * @ID 2146444
 * @author Adam Bekesi
 * @ID 2147548
 */
public class SettingsScreen {

    private JFrame frame = new JFrame("Mazesweeper | Game settings"); 
    private StartButtonEventHandler2 startListener = new StartButtonEventHandler2();

    private JTextField seedInputField = new JTextField(19);
    private JLabel invalidSeedLabel = new JLabel();
    private JSlider difficultySlider = new JSlider(1, 5);

    /**
     * Builds and shows the settings window before a game of Mazesweeper.
     */
    public SettingsScreen() {

        // frame init

        //sets window size relative to user's screen size
        int frameWidth = Menu.frameWidth / 2;
        int frameHeight = Menu.frameHeight / 5;
        frame.setSize(frameWidth, frameHeight);
        int frameX = (Menu.SCREEN_SIZE.width - frameWidth) / 2;
        int frameY = (Menu.SCREEN_SIZE.height - frameHeight) / 2;
        frame.setLocation(frameX, frameY);
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        // Setup of background image
        try {
            final Image MazesweeperBackground = 
                ImageIO.read(new File("MazesweeperTiles.png"));
            BackgroundPanel backgroundPanel = new BackgroundPanel(MazesweeperBackground, frame);
            frame.setContentPane(backgroundPanel);
        } catch (IOException e) {
            System.err.println("ERROR: MISSING SETTINGS WINDOW BACKGROUND IMAGE!");
            e.printStackTrace();
        }
        
        JPanel seedPanel = new JPanel();
        seedPanel.setLocation(0, 0);
        seedPanel.setBackground(Mazesweeper.LIGHT_BEIGE);
        JLabel seedLabel = new JLabel("Seed: ");
        seedPanel.add(seedLabel);
        frame.add(seedPanel);

        seedInputField.setToolTipText("Leave blank to use a random seed");
        seedPanel.add(seedInputField);

        seedPanel.add(invalidSeedLabel);

        // start button init
        JButton startButton = new JButton("START");
        startButton.setBackground(Mazesweeper.LIGHT_BEIGE);
        startButton.addActionListener(startListener);
        startButton.setSize(Menu.buttonDimension);
        startButton.setFont(new Font("Dialog", Font.BOLD, Menu.buttonFontSize));
        // mouselistener for changing colour when hovering over it
        startButton.addMouseListener(new ButtonMouseListener(startButton));
        frame.add(startButton);

        difficultySlider.setPaintTrack(true);
        difficultySlider.setPaintTicks(true);
        difficultySlider.setPaintLabels(true);
        difficultySlider.setMajorTickSpacing(1);
        frame.add(difficultySlider);

        JLabel settingLabel = new JLabel("Set the seed and difficulty of your next game");
        settingLabel.setFont(new Font("Sans_Serif", Font.BOLD, Menu.buttonFontSize));

        frame.add(settingLabel);
        
        frame.setVisible(true);
    }

    class StartButtonEventHandler2 implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
    
            String seedInput = seedInputField.getText();
            boolean seedIsValid = true;
            long seed;
            int i = 0;

            // if no seed is given, pick a random one
            if (seedInput.length() == 0) {
                seed = Mazesweeper.randomGenerator.nextLong();
                seedInput = "" + seed;
            // seed character limit (longs have 19 digits at most)
            } else if (seedInput.length() > 20
                || (seedInput.length() > 19 && seedInput.charAt(0) != '-')) {

                seedIsValid = false;
            } 

            // if the seed is "-", the seed is invalid.
            if (seedInput.charAt(0) == '-') {
                if (seedInput.length() == 1) {
                    seedIsValid = false;
                } else { // if the seed is negative.
                    i = 1;
                }
            }

            while (i < seedInput.length()) {
                if (!Character.isDigit(seedInput.charAt(i))) {
                    seedIsValid = false;
                }
                i++;
            }

            if (seedIsValid) {

                try {
                    seed = Long.parseLong(seedInput);
                    frame.setVisible(false);
                    Mazesweeper game = new Mazesweeper(seed, difficultySlider.getValue());
                } catch (NumberFormatException err) { // only way this can happen is overflow
                    invalidSeedLabel.setText("Seed should be a number between "
                                           + "-9223372036854775808 and 9223372036854775807");
                }
            } else {
                invalidSeedLabel.setText("Seed should be a number between " 
                                       + "-9223372036854775808 and 9223372036854775807");
            }
        }
    }
}

