package game;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class Main {

    public static void main(String[] args) {

        JFrame window = new JFrame("Frame");
        window.setTitle("Go - Baduk - Weiqi");

        Board panel = new Board(); // this is the panel the game will be

        JPanel panel1 = new JPanel();
        JPanel panel2 = new JPanel();
        JPanel panel3 = new JPanel();
        JPanel panel4 = new JPanel();

        panel.setLayout(new BorderLayout()); // making sure the panel is in the center

        // setting these panels to specific sizes 
        panel1.setPreferredSize(new Dimension(100, 85));

        panel2.setPreferredSize(new Dimension(350, 100));
        panel3.setPreferredSize(new Dimension(350, 100));
        panel4.setPreferredSize(new Dimension(100, 85));
        panel.setPreferredSize(new Dimension(600, 600));

        window.add(panel1, BorderLayout.NORTH);
        window.add(panel2, BorderLayout.WEST);
        window.add(panel3, BorderLayout.EAST);
        window.add(panel4, BorderLayout.SOUTH);

        window.add(panel, BorderLayout.CENTER);

        // add labels for capturing
        JLabel lab1 = new JLabel("white stones captured: 0");
        panel3.setLayout(null);
        panel3.add(lab1);
        Dimension dim1 = lab1.getPreferredSize();
        lab1.setBounds(30, 50, 300, dim1.height);

        JLabel lab2 = new JLabel("black stones captured: 0");
        panel3.add(lab2);
        Dimension dim2 = lab2.getPreferredSize();
        lab2.setBounds(30, 100, 300, dim2.height);
        
        
        // with this object we can detect mouse clicks
        ClickListener game = new ClickListener(panel, lab1, lab2);

        panel.addMouseListener(game); // specifically detecting them in the center panel

        // add coordinates
        // vertical coordinates
        panel2.setLayout(null);
        int y = 0;
        for (int i = 1; i < 20; i++) { // making labels with numbers 1-19
            if (i == 1) { // the text 1 starts in a specific place
                JLabel l = new JLabel("" + i);
                Dimension size = l.getPreferredSize();
                l.setFont(new Font("Arial", Font.PLAIN, 13));

                y = 26;
                panel2.add(l);
                l.setBounds(320, y, size.width, size.height);
                continue;
            }
            y += 30; // change the y value so the text moves down
            JLabel l = new JLabel("" + i);
            Dimension size = l.getPreferredSize();
            l.setFont(new Font("Arial", Font.PLAIN, 13));

            panel2.add(l);
            l.setBounds(320, y, size.width, size.height);
        }

        // horizontal coordinates
        panel1.setLayout(null);
        JLabel letterCoords = new JLabel("A     B     C      D      E      F     G     H      I      J     K     L     M     N     O     P     Q     R     S");
        letterCoords.setFont(new Font("Arial", Font.PLAIN, 13));
        panel1.add(letterCoords);
        Dimension d = letterCoords.getPreferredSize();
        letterCoords.setBounds(375, 55, d.width, d.height);

        // add reset button
        JButton reset = new JButton("Reset Game");
        panel4.add(reset);
        // Add event handler for reset button
        reset.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                game.resetGame(); // can be improved, game logic in main class
            }
        });

        // add undo button
        JButton undo = new JButton("Undo Move");
        panel4.add(undo);
        // Add event handler for undo button
        undo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (panel.getVisibleStones().isEmpty() == false) { // if there are no stones, undo move does nothing
                    if (game.undoMove() == false) { // undoMove() returns a boolean, but also performs a function when returning true
                        JOptionPane.showMessageDialog(panel,
                                "Cannot undo after capture"
                        );
                    }
                }
            }
        });

        window.setSize(1300, 800);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setVisible(true);
        window.setLayout(null);
        window.setResizable(false);

    }
}
