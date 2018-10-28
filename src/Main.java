import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

/**
 * Created by nazanin on 10/14/2018.
 */
public class Main extends JFrame implements ActionListener, KeyListener, FocusListener {

    public static String fromLang = "fa";
    public static String toLang = "en";
    static JFrame f;
    static JButton translateButton;
    static JTextArea code, lineNumber;
    static JTextArea error;
    static JScrollPane scrollPane;
    static boolean okId = true,cmSuspect=false,okStr=true;
    static Token token;
    private int line = 0;
    static int count=0;
    private static String[] lines;

    Main() {

    }

    public static void main(String[] args) {
        Main main = new Main();

        f = new JFrame("translator");
        f.getContentPane().setBackground(Color.black);
        translateButton = new JButton("ترجمه");
        translateButton.setFont(translateButton.getFont().deriveFont(16f));
        translateButton.addActionListener(main);
        translateButton.setBounds(880, 570, 70, 30);
        translateButton.setBackground(new Color(99, 31, 89));
        translateButton.setForeground(Color.BLACK);

        lineNumber = new JTextArea();
        lineNumber.setBounds(850, 30, 30, 500);
        lineNumber.setBackground(new Color(99, 31, 89));
        lineNumber.setForeground(new Color(255, 0, 191));
        lineNumber.setMargin(new Insets(30, 10, 10, 10));
        //    lineNumber.setText(String.valueOf(1));
        lineNumber.setEnabled(false);
        lineNumber.setFont(lineNumber.getFont().deriveFont(16f));

        code = new JTextArea();
        code.addKeyListener(main);
        code.setBackground(new Color(37, 37, 37));
        //    code.setBounds(50, 30, 800, 500);
        code.setFont(code.getFont().deriveFont(16f));
        code.setMargin(new Insets(30, 30, 10, 30));
        code.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        code.setForeground(new Color(255, 0, 191));
        code.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void removeUpdate(DocumentEvent e) {
                StringBuilder numbers=new StringBuilder();
                for (int i = 0; i <code.getLineCount() ; i++) {
                    numbers.append(String.valueOf(i+1));
                    numbers.append("\n");

                }
                lineNumber.setText(numbers.toString());
            }

            @Override
            public void insertUpdate(DocumentEvent e) {

                StringBuilder numbers=new StringBuilder();
                for (int i = 0; i <code.getLineCount() ; i++) {
                    numbers.append(String.valueOf(i+1));
                    numbers.append("\n");

                }
                lineNumber.setText(numbers.toString());
            }

            @Override
            public void changedUpdate(DocumentEvent arg0) {

            }
        });

        error = new JTextArea(10, 60);
        error.setBounds(50, 540, 800, 80);
        error.setFont(code.getFont().deriveFont(16f));
        error.setMargin(new Insets(10, 10, 10, 10));
        error.setForeground(new Color(255, 25, 25));
        error.setBackground(new Color(26, 26, 26));

        scrollPane=new JScrollPane(code);
        scrollPane.setBounds(50,30,800,500);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.getVerticalScrollBar().setPreferredSize (new Dimension(0,0));
        //  scrollPane.setSize(300,500);
        // f.add(code);
        //   lines= code.getText().split("\\n");
        f.add(scrollPane);
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
            lines = code.getText().split("\\n");
            ArrayList<String> outputs = new ArrayList<>();
            for (int i = 0; i < lines.length; i++) {

                Tokenizer tokenizer = new Tokenizer();
                token = new Token();
                token.setRow(i + 1);
                outputs.add(tokenizer.tokenize(lines[i]));
                o += outputs.get(i) + "\n";
            }
            if (okId && okStr && (count%2==0)) {
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
                    error.setText("invalid identifier");
                }
                else if (!okStr){
                    error.setText("invalid string");
                }
                else if (count%2!=0){
                    error.setText("missing curly bracket");
                    count=0;
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

//      //  System.out.println(lines.length);
//        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
//            line++;
//   //     lineNumber.setText("");
//      //  for (int i = 0; i <lines.length ; i++) {
//            lineNumber.append("\n");
//            lineNumber.append(String.valueOf(line));
//    //    }
//        StringBuilder numbers=new StringBuilder();
//        for (int i = 0; i <code.getLineCount() ; i++) {
//            numbers.append(String.valueOf(i+1));
//            numbers.append("\n");
//
//        }
//        lineNumber.setText(numbers.toString());

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void focusGained(FocusEvent e) {
//        System.out.println("focus");
//        StringBuilder numbers=new StringBuilder();
//        for (int i = 0; i <code.getLineCount() ; i++) {
//            numbers.append(String.valueOf(i+1));
//            numbers.append("\n");
//
//        }
//        lineNumber.setText(numbers.toString());
    }

    @Override
    public void focusLost(FocusEvent e) {

    }
}
