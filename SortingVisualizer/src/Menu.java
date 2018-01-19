import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Menu extends JFrame {

    private static final int MENU_WIDTH = 300;
    private static final int MENU_HEIGHT = 100;

    private static final int CANVAS_WIDTH  = 1000;
    private static final int CANVAS_HEIGHT = 600;

    private String currentSize;
    private String currentSort;

    public Menu(String title) {
        super(title);
        setPreferredSize(new Dimension(MENU_WIDTH, MENU_HEIGHT));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        JPanel panel = new JPanel();

        String[] listSize = {"Small", "Medium", "Large", "Very Large"};
        JComboBox sizeCombo = new JComboBox(listSize);
        sizeCombo.setSelectedIndex(2);
        sizeCombo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox cb = (JComboBox)e.getSource();
                currentSize = (String)cb.getSelectedItem();
            }
        });

        String[] sortType = {"Insertion Sort", "Quicksort", "Bubble Sort", "Merge Sort"};
        JComboBox sortCombo = new JComboBox(sortType);
        sortCombo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox cb = (JComboBox)e.getSource();
                currentSort = (String)cb.getSelectedItem();
            }
        });

        currentSize = (String)sizeCombo.getSelectedItem();
        currentSort = (String)sortCombo.getSelectedItem();

        JButton button = new JButton("Run");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame sortingFrame = new JFrame("Sorting");
                sortingFrame.setResizable(false);
                SortingPanel sortingPanel = new SortingPanel(CANVAS_WIDTH, CANVAS_HEIGHT, currentSize, currentSort);
                sortingFrame.getContentPane().add(sortingPanel, BorderLayout.CENTER);
                sortingFrame.setVisible(true);
                sortingFrame.pack();
                sortingFrame.setLocationRelativeTo(null);
            }
        });

        panel.add(sizeCombo);
        panel.add(sortCombo);
        panel.add(button);
        getContentPane().add(panel);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
