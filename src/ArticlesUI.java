import com.sun.javafx.css.Stylesheet;
import javafx.scene.image.*;
import sun.awt.image.ImageWatched;
import sun.swing.ImageIconUIResource;


import java.awt.*;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import static java.awt.Color.*;

public class ArticlesUI extends JFrame {

    private Font font;
    private static final String[] ARTICLES = new String[] {"der","die","das"};
    private static final String ARTICLES_FILE = "./src/Sonad/sonad.txt";

    private static JFrame mainFrame;

    private Map<String,String> wordArticlesPairs = new HashMap<String, String>();
    private Iterator<String> articlesIterator;
    private String currentWord;
    private String selectedArticle;
    private String filePath;
    private int correctAnswers = 0;
    private int cookie = 1;

    private JLabel wordLabel, ImageLabel;
    private File imageFile;
    private Image image;
    private ImageIcon icon;
    private BorderLayout Layout;
    private Dimension labelSize;

    public ArticlesUI() throws IOException {
        setTitle("DerDieDasGame");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        loadArticlesFromFile();
        articlesIterator = wordArticlesPairs.keySet().iterator();
        currentWord = articlesIterator.next();
        Dimension labelSize = new Dimension();
        BorderLayout Layout = new BorderLayout();
        Layout.setHgap(100);
        Layout.setVgap(100);

        labelSize.height = 200;
        JPanel panel = new JPanel(Layout);
        //panel.setSize(3500, 3900);
        panel.setBorder(new EmptyBorder(200, 200, 200, 200)); // padding
        panel.setBackground(WHITE);

        wordLabel = new JLabel(currentWord);
        panel.add(wordLabel, BorderLayout.NORTH);
        panel.add(createRadioPanel(), BorderLayout.CENTER);
        panel.add(createAnswerButton(), BorderLayout.SOUTH);
        setContentPane(panel);
        pack();
        // center window
        setLocationRelativeTo(null);
        //Text font ist Verdana, size 24
        Font font1 = new Font("Arial", Font.PLAIN, 24);
        wordLabel.setFont(font1);
        //wordLabel.setAlignmentY(100);
        File imageFile = new File("./src/Logo_ddd.png");
        Image image = ImageIO.read(imageFile);
        ImageIcon icon = new ImageIcon(image);
        JLabel imageLabel = new JLabel();
        imageLabel.setIcon(icon);
    }

    private void loadArticlesFromFile() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(ARTICLES_FILE));
        for (int i = 0; i < 10; i++) {
            //System.out.println(br.readLine());
            wordArticlesPairs.put(reader.readLine(), reader.readLine());
        }
        reader.close();
    }

    private JPanel createRadioPanel() {
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(300, 50));
        ButtonGroup group = new ButtonGroup();

        for (String article : ARTICLES) {
            JRadioButton radioButton = new JRadioButton(article);
            radioButton.setActionCommand(article);
            radioButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    selectedArticle = e.getActionCommand();
                }
            });
            group.add(radioButton);
            panel.add(radioButton);
        }
        return panel;
    }


    //private static final String Image = "./src/Logo_ddd.png";
    //ImageIconUIResource icon = new ImageIconUIResource(100[];
    private JButton createAnswerButton() {
        JButton button = new JButton("Answer");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedArticle == null) { // user did not select anything
                    return; // do nothing
                }
                if (selectedArticle.equals(wordArticlesPairs.get(currentWord))) {
                    JOptionPane.showMessageDialog(mainFrame, "Correct! " + selectedArticle + " " + currentWord, " ", JOptionPane.PLAIN_MESSAGE);
                    correctAnswers += cookie;
                    if (articlesIterator.hasNext()) {
                        currentWord = articlesIterator.next();
                        wordLabel.setText(currentWord);
                        cookie = 1;
                    } else {
                        float tulemus = ((float)correctAnswers/wordArticlesPairs.size()) * 100;
                        JOptionPane.showMessageDialog(mainFrame, "Result: "+ tulemus + " %");
                    }
                } else {
                    JOptionPane.showMessageDialog(mainFrame, "Wrong! Try again", " ", JOptionPane.PLAIN_MESSAGE);
                    cookie = 0;
                }
            }
        });

        return button;
    }


}
