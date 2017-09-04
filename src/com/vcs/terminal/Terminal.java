package com.vcs.terminal;

import com.vcs.util.Command;
import com.vcs.util.Env;

import javax.swing.*;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;


/**
 * created by 吴震煌 on 2017/3/6 11:20
 */
public class Terminal  extends JFrame implements ActionListener{

    private  JTextArea textArea;

    private JScrollPane scrollPane;

    private int pos ;

    private String right = Env.APP_NAME+" -"+Env.VERSION+ " "+ new Date() + " " +Env.RIGHT +" \n";

    private String input;

    private Command command;

    private String response;

    StringBuilder tmp = new StringBuilder(right).append(Env.prefix);

    public Terminal(){

        textArea  = new JTextArea(right+Env.prefix);
        textArea.setLineWrap(true);
        textArea.setBackground(Color.black);
        textArea.setWrapStyleWord(true);
        textArea.setFont(new Font("宋体", Font.PLAIN, 26));
        textArea.setForeground(Color.gray.brighter());
        textArea.requestFocusInWindow();
        textArea.setCaretColor(Color.white.brighter());
        Container content = this.getContentPane();
        content.setLayout(new BorderLayout());
        command = new Command();

        textArea.addCaretListener(new CaretListener() {
            @Override
            public void caretUpdate(CaretEvent e) {
                pos = textArea.getCaretPosition();
            }
        });


        textArea.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (pos < tmp.length() -1) {
                    e.consume();
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {

                if( pos < tmp.length()  && e.getKeyChar() == KeyEvent.VK_BACK_SPACE )
                    e.consume();
                if( e.getKeyChar() == KeyEvent.VK_ENTER) {
                    input = textArea.getText().substring(tmp.length() -1);
                    //command.set(input);
                    try {
                        response = command.execute(input);
                    } catch (InvocationTargetException e1) {
                        e1.printStackTrace();
                    } catch (NoSuchMethodException e1) {
                        e1.printStackTrace();
                    } catch (IllegalAccessException e1) {
                        e1.printStackTrace();
                    } catch (NoSuchFieldException e1) {
                        e1.printStackTrace();
                    }
                    textArea.append(response);
                    textArea.append(Env.prefix);
                    tmp = new StringBuilder(textArea.getText());
                    textArea.setCaretPosition(tmp.length() - 2 );

                }

            }

            @Override
            public void keyReleased(KeyEvent e) {}
        });

        scrollPane = new JScrollPane(textArea);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        content.add(scrollPane,BorderLayout.CENTER);
        this.setSize( new Dimension(1200,750));
        this.setTitle(Env.APP_NAME + Env.VERSION);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println(e.getSource());
    }

}
