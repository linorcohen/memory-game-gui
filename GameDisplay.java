package MemoryGameGUI;

import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.util.*;
import java.util.List;

public class GameDisplay extends JFrame {

    protected JButton[] listButtons = new JButton[20];
    protected JButton newGameButton;
    protected JLabel scoreLabel;
    protected JLabel timerLabel;
    public static JPanel massagePanel;
    protected Timer cardsTimer;
    protected Timer clockTimer;
    protected int count = 0;
    protected int score = 0;
    protected int numOfCards = 0;
    protected final List<ImageIcon> imageIconList = ReadIconsFolder.setListOfIconsFromFolder("src/MemoryGameGUI/icons");
    protected Map<JButton, ImageIcon> imageCardsSelected = new HashMap<>();


    public GameDisplay() {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setTitle("Memory Game");
        this.setSize(700, 438);
        this.setResizable(false);

        ImageIcon imgIcon = new ImageIcon("src/MemoryGameGUI/card.png");
        this.setIconImage(imgIcon.getImage());

        JPanel questionPanel = new JPanel();
        questionPanel.setBounds(0, 0, 500, 450);
        questionPanel.setLayout(null);

        JPanel scorePanel = new JPanel();
        scorePanel.setBounds(500, 0, 200, 460);
        scorePanel.setLayout(null);
        scorePanel.setBackground(new Color(0xEBF38A));

        massagePanel = new JPanel();
        massagePanel.setBounds(0, 0, 700, 438);
        massagePanel.setLayout(null);

        setScoreLabelDisplay(scorePanel);
        setTimerLabelDisplay(scorePanel);
        setNewGameButton(scorePanel);
        setButtonsOnPanel(questionPanel);
        setButtonDisplay();
        setGameAction();

        add(scorePanel);
        add(questionPanel);
        add(massagePanel);

        try {
            setCardsTimer();
        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }
        setGameClockTimer();

    }

    private void setButtonsOnPanel(JPanel panel) {
        for (int i = 0; i < 20; i++) {
            listButtons[i] = new JButton();
        }
        int x = 0;
        int y = 0;
        for (int i = 0; i < 20; i++) {
            listButtons[i].setBounds(x, y, 100, 100);
            x += 100;
            if (x == 500) {
                x = 0;
                y += 100;
            }
            panel.add(listButtons[i]);
        }
    }

    private void setButtonDisplay() {
        for (int i = 0; i < 20; i++) {
            listButtons[i].setBorder(BorderFactory.createBevelBorder(1, new Color(0xB0F5B6), Color.WHITE));
            listButtons[i].setBackground(new Color(0x6ED578));

        }
    }

    private void setGameAction() {
        Collections.shuffle(imageIconList);
        for (int i = 0; i < 20; i++) {
            int finalI = i;

            listButtons[i].addActionListener(e -> {
                numOfCards++;
                listButtons[finalI].setIcon(imageIconList.get(finalI));
                imageCardsSelected.put(listButtons[finalI], imageIconList.get(finalI));
                if (numOfCards == 2) {
                    List<ImageIcon> values = new ArrayList<>(imageCardsSelected.values());
                    if (imageCardsSelected.size() == 2 && values.get(0).equals(values.get(1))) {
                        setButtonsDisable();
                        updateScoreLabel();
                    } else {
                        cardsTimer.start();
                    }
                }
            });
        }
    }

    private void initialTheCards() {
        imageCardsSelected.clear();
        numOfCards = 0;
    }

    private void setCardsTimer() throws InterruptedException {
        cardsTimer = new Timer(600, ae -> {
            setButtonsIconsDisappear();
            cardsTimer.stop();
        });
    }

    private void updateScoreLabel() {
        score++;
        if (score == 10) {
            clockTimer.stop();
            setWinStatusMessage(massagePanel);
        }
        scoreLabel.setText("Your Score: " + score);
    }

    private void setButtonsIconsDisappear() {
        for (JButton key : imageCardsSelected.keySet()) {
            key.setIcon(null);
        }
        initialTheCards();
    }

    private void setButtonsDisable() {
        for (JButton key : imageCardsSelected.keySet()) {
            key.setEnabled(false);
        }
        initialTheCards();
    }


    private void setScoreLabelDisplay(JPanel panel) {
        scoreLabel = new JLabel();
        scoreLabel.setText("Your Score: " + score);
        panel.add(scoreLabel);

        scoreLabel.setFont(new Font("Helvetica", Font.PLAIN, 20));
        scoreLabel.setBounds(35, 60, 200, 20);
    }


    private void setWinStatusMessage(JPanel panel) {
        ImageIcon winIcon = new ImageIcon("src/MemoryGameGUI/win.png");
        Image imgIcon = winIcon.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT);
        Icon icon = new ImageIcon(imgIcon);

        JOptionPane.showMessageDialog(panel,
                "You won the game! \n Your time was: " + (count - 1),
                "Congratulations!",
                JOptionPane.INFORMATION_MESSAGE,
                icon);
    }

    private void setNewGameButton(JPanel scorePanel) {
        newGameButton = new JButton();
        newGameButton.setBounds(28, 200, 130, 40);
        scorePanel.add(newGameButton);

        newGameButton.setText("New Game");
        newGameButton.setFont(new Font("Helvetica", Font.PLAIN, 15));
        newGameButton.setBorder(BorderFactory.createEtchedBorder(Color.BLUE, Color.WHITE));
        newGameButton.setBackground(new Color(0xA3E5D8));

        startNewGameAction();
    }

    private void startNewGameAction() {
        newGameButton.addActionListener(e -> {
            clockTimer.stop();
            resetListButtons();
            Collections.shuffle(imageIconList);
            initialTheCards();
            score = 0;
            scoreLabel.setText("Your Score: " + score);
            count = 0;
            timerLabel.setText("Timer: " + count);
            setGameClockTimer();
        });
    }

    private void resetListButtons() {
        for (int i = 0; i < 20; i++) {
            listButtons[i].setEnabled(true);
            listButtons[i].setIcon(null);
        }
    }

    private void setTimerLabelDisplay(JPanel panel) {
        timerLabel = new JLabel();
        timerLabel.setText("Timer: " + count);
        panel.add(timerLabel);

        timerLabel.setFont(new Font("Helvetica", Font.PLAIN, 20));
        timerLabel.setBounds(35, 100, 200, 20);
    }

    private void setGameClockTimer() {
        clockTimer = new Timer(1000, ae -> {
            timerLabel.setText("Timer: " + count);
            count++;
        });
        clockTimer.start();
    }
}
