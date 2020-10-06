package com.hyena.todolist.view;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 *
 * @author k1715939
 */
public class Console extends JPanel{
    private JTextArea textarea;
    
    public Console()
    {
        this.textarea = new JTextArea();
        this.setLayout(new BorderLayout());
        JScrollPane scroll = new JScrollPane(textarea);
        this.add(scroll,BorderLayout.CENTER);
        textarea.setLineWrap(true); // otherwise everything is on one line
        textarea.setEditable(false); // otherwise people can type over the text
    }
    
    public void print(String text)
    {
        String currentText = this.getTextarea().getText();
        currentText += text;
        this.getTextarea().setText(currentText);
        this.getTextarea().setCaretPosition(this.getTextarea().getDocument().getLength());
    }
    
    public void println(String text)
    {
        this.print(text+System.getProperty("line.separator"));
    }
    
    public void clear()
    {
        this.getTextarea().setText("");
    }

    public JTextArea getTextarea() {
        return textarea;
    }   
}
