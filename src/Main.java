import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

/**
 * Created by nazanin on 10/14/2018.
 */
public class Main extends JFrame implements ActionListener, KeyListener {

    public static String fromLang = "en";
    public static String toLang = "fa";
    static JFrame f;
    static JButton translateButton;
    static JTextArea code, lineNumber;
    static JTextArea error;
    static boolean okId = true,cmSuspect=false,okStr=true;
    static Token token;
    private int line = 1;

    Main() {
    }

    public static void main(String[] args) {
        Main te = new Main();
        f = new JFrame("translator");
        f.getContentPane().setBackground(Color.black);
        translateButton = new JButton("ترجمه");
        translateButton.setFont(translateButton.getFont().deriveFont(16f));
        translateButton.addActionListener(te);
        translateButton.setBounds(880, 570, 70, 30);
        translateButton.setBackground(new Color(99, 31, 89));
        translateButton.setForeground(Color.BLACK);

        lineNumber = new JTextArea();
        lineNumber.setBounds(850, 30, 30, 500);
        lineNumber.setBackground(new Color(99, 31, 89));
        lineNumber.setForeground(new Color(255, 0, 191));
        lineNumber.setMargin(new Insets(30, 10, 10, 10));
        lineNumber.setText(String.valueOf(1));
        lineNumber.setEnabled(false);
        lineNumber.setFont(lineNumber.getFont().deriveFont(16f));

        code = new JTextArea(30, 60);
        code.addKeyListener(te);
        code.setBackground(new Color(37, 37, 37));
        code.setBounds(50, 30, 800, 500);
        code.setFont(code.getFont().deriveFont(16f));
        code.setMargin(new Insets(30, 30, 10, 30));
        code.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        code.setForeground(new Color(255, 0, 191));

        error = new JTextArea(10, 60);
        error.setBounds(50, 540, 800, 80);
        error.setFont(code.getFont().deriveFont(16f));
        error.setMargin(new Insets(10, 10, 10, 10));
        error.setForeground(new Color(255, 25, 25));
        error.setBackground(new Color(26, 26, 26));

        f.add(code);
        f.add(error);
        f.add(lineNumber);
        f.add(translateButton);

        f.setSize(1000, 700);
        f.setLayout(null);
        f.setVisible(true);

        new DictionaryManager();
    }


    public void actionPerformed(ActionEvent e) {
        error.setText("");
        String o = "";
        okId = true;
        okStr=true;
        try {
            String[] lines = code.getText().split("\\n");
            ArrayList<String> outputs = new ArrayList<>();
            for (int i = 0; i < lines.length; i++) {

                Tokenizer tokenizer = new Tokenizer();
                token = new Token();
                token.setRow(i + 1);
                outputs.add(tokenizer.tokenize(lines[i]));
                o += outputs.get(i) + "\n";

            }
            if (okId && okStr) {
                //   System.out.println("ok ok");
                if (toLang.equals("fa")) {
                    code.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
                }
                if (toLang.equals("en")) {
                    code.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
                }

                code.setText(o);
                String temp;
                temp = fromLang;
                fromLang = toLang;
                toLang = temp;
            }
            else {
                if (!okId) {
                    error.setText("invalid identifier in line: " + token.getRow() + " column: " + token.getCol());
                }
                else if (!okStr){
                    error.setText("invalid string in line: " + token.getRow() + " column: " + token.getCol());
                }
            }


        } catch (Exception e1) {
            e1.printStackTrace();
        }

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            line++;
            lineNumber.append("\n");
            lineNumber.append(String.valueOf(line));
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
