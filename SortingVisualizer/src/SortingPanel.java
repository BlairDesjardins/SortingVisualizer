import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class SortingPanel extends JPanel implements ActionListener {

    private int listLength;
    private int delay;

    private int canvasWidth;
    private int canvasHeight;
    private static int boxWidth;
    private static int boxHeight;

    private int[] list;
    private int[] sortedList;
    private ArrayList<int[]> moveList;
    private Timer timer;

    private int index_i;

    public SortingPanel(int canvasWidth, int canvasHeight, String listSize, String sortType) {
        this.canvasWidth = canvasWidth;
        this.canvasHeight = canvasHeight;
        setPreferredSize(new Dimension(canvasWidth, canvasHeight));

        switch (listSize) {
            case "Small":
                listLength = 25;
                delay = 50;
                break;
            case "Medium":
                listLength = 100;
                delay = 20;
                break;
            case "Large":
                listLength = 250;
                delay = 10;
                break;
            case "Very Large":
                listLength = 500;
                delay = 5;
                break;
            default:
                break;
        }

        boxWidth = canvasWidth/listLength;
        sortedList = createList(listLength);
        list = sortedList.clone();
        moveList = new ArrayList<>();

        if (sortType.equals("Insertion Sort"))
            insertionSort(sortedList);
        else if (sortType.equals("Quicksort"))
            quickSort(sortedList, 0, listLength-1);
        else if (sortType.equals("Bubble Sort"))
            bubbleSort(sortedList);
        else if (sortType.equals("Merge Sort"))
            mergeSort(sortedList, 0, listLength-1);

        index_i = 0;
        timer = new Timer(delay, this);
        timer.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        setBackground(Color.BLACK);

        for (int i = 0; i < list.length; i++) {
            boxHeight = canvasHeight*(list[i]+1)/list.length;
            g.setColor(new Color((list[i]/(float)list.length), (float) 1.0, (float) 1.0));
            g.fillRect(boxWidth*i, canvasHeight-boxHeight, boxWidth, boxHeight);
            g.setColor(Color.BLACK);
            g.drawRect(boxWidth*i, canvasHeight-boxHeight, boxWidth, boxHeight);
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (index_i < moveList.size()) {
            swap(list, moveList.get(index_i)[0], moveList.get(index_i)[1]);
            index_i++;
        }
        repaint();
    }

    public static void swap(int[] list, int i, int j) {
        int temp = list[i];
        list[i] = list[j];
        list[j] = temp;
    }

    public void insertionSort(int[] list) {
        for (int i = 0; i < list.length; i++) {
            int j = i;
            while (j > 0 && list[j-1] > list[j]) {
                swap(list, j-1, j);
                moveList.add(new int[]{j-1, j});
                j--;
            }
        }
    }

    public void quickSort(int[] list, int low, int high) {
        if (low < high) {
            int pi = partition(list, low, high);
            quickSort(list, low, pi-1);
            quickSort(list, pi+1, high);
        }
    }

    public int partition(int[] list, int low, int high) {
        int pivot = list[high];
        int i = low;
        for (int j = low; j <= high-1; j++) {
            if (list[j] <= pivot) {
                if (i != j) {
                    swap(list, i, j);
                    moveList.add(new int[]{i, j});
                }
                i++;
            }
        }
        swap(list, i, high);
        moveList.add(new int[]{i, high});
        return i;
    }

    public void bubbleSort(int[] list) {
        int n = list.length;
        while (n > 0) {
            int new_n = 0;
            for (int i = 1; i < n; i++) {
                if (list[i-1] > list[i]) {
                    swap(list, i-1, i);
                    moveList.add(new int[]{i-1, i});
                    new_n = i;
                }
            }
            n = new_n;
        }
    }

    public void mergeSort(int[] list, int l, int r) {
        if (l < r) {
            int m = (l+r)/2;
            mergeSort(list, l, m);
            mergeSort(list, m+1, r);
            merge(list, l, m, r);
        }
    }

    public void merge(int[] list, int l, int m, int r) {
        int nl = m - l + 1;
        int nr = r - m;

        int[] leftList = new int[nl];
        int[] rightList = new int[nr];
        for (int i = 0; i < nl; i++)
            leftList[i] = list[l+i];
        for (int i = 0; i < nr; i++)
            rightList[i] = list[m+1+i];

        int i, j, k;
        i = j = 0;
        k = l;
        while (i < nl && j < nr) {
            if (leftList[i] <= rightList[j]) {
                list[k] = leftList[i];
                moveList.add(new int[]{k, l+i});
                i++;
            }
            else {
                list[k] = rightList[j];
                moveList.add(new int[]{k, m+1+j});
                j++;
            }
            k++;
        }

        while(i < nl) {
            list[k] = leftList[i];
            moveList.add(new int[]{k, l+i});
            i++; k++;
        }
        while(j < nr) {
            list[k] = rightList[j];
            moveList.add(new int[]{k, m+1+j});
            j++; k++;
        }
    }

    public static int[] createList(int length) {
        int[] list = new int[length];
        Random rand = new Random();
        for (int i = 0; i < length; i++) {
            list[i] = rand.nextInt(length);
        }
        return list;
    }
}
