package iX;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Find extends JFrame implements ActionListener {

    int startIndex=0;
    int select_start=-1;
    JLabel lab1, lab2;
    JTextField textF, textR;
    JButton findBtn, findNext, replace, replaceAll, cancel;
    private JTextArea txt;

    public Find(JTextArea text) {
        this.txt = text;

        lab1 = new JLabel("Find:");
        lab2 = new JLabel("Replace:");
        textF = new JTextField(30);
        textR = new JTextField(30);
        findBtn = new JButton("Find");
        findNext = new JButton("Find Next");
        replace = new JButton("Replace");
        replaceAll = new JButton("Replace All");
        cancel = new JButton("Cancel");

        // Set Layout NULL
        setLayout(null);

        // Set the width and height of the label
        int labWidth = 80;
        int labHeight = 20;

        // Adding labels
        lab1.setBounds(10,10, labWidth, labHeight);
        add(lab1);
        textF.setBounds(10+labWidth, 10, 120, 20);
        add(textF);
        lab2.setBounds(10, 10+labHeight+10, labWidth, labHeight);
        add(lab2);
        textR.setBounds(10+labWidth, 10+labHeight+10, 120, 20);
        add(textR);

        // Adding buttons
        findBtn.setBounds(225, 6, 115, 20);
        add(findBtn);
        findBtn.addActionListener(this);

        findNext.setBounds(225, 28, 115, 20);
        add(findNext);
        findNext.addActionListener(this);

        replace.setBounds(225, 50, 115, 20);
        add(replace);
        replace.addActionListener(this);

        replaceAll.setBounds(225, 72, 115, 20);
        add(replaceAll);
        replaceAll.addActionListener(this);

        cancel.setBounds(225, 94, 115, 20);
        add(cancel);
        cancel.addActionListener(this);


        // Set the width and height of the window
        int width = 360;
        int height = 160;

        // Set size window
        setSize(width,height);

        // center the frame on the frame
        setLocationRelativeTo(txt);
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    public void find() {
        select_start = txt.getText().toLowerCase().indexOf(textF.getText().toLowerCase());
        if(select_start == -1)
        {
            startIndex = 0;
            JOptionPane.showMessageDialog(null, "Could not find \"" + textF.getText() + "\"!");
            return;
        }
        if(select_start == txt.getText().toLowerCase().lastIndexOf(textF.getText().toLowerCase()))
        {
            startIndex = 0;
        }
        int select_end = select_start + textF.getText().length();
        txt.select(select_start, select_end);
    }

    public void findNext() {
        String selection = txt.getSelectedText();
        try
        {
            selection.equals("");
        }
        catch(NullPointerException e)
        {
            selection = textF.getText();
            try
            {
                selection.equals("");
            }
            catch(NullPointerException e2)
            {
                selection = JOptionPane.showInputDialog("Find:");
                textF.setText(selection);
            }
        }
        try
        {
            int select_start = txt.getText().toLowerCase().indexOf(selection.toLowerCase(), startIndex);
            int select_end = select_start+selection.length();
            txt.select(select_start, select_end);
            startIndex = select_end+1;

            if(select_start == txt.getText().toLowerCase().lastIndexOf(selection.toLowerCase()))
            {
                startIndex = 0;
            }
        }
        catch(NullPointerException e)
        {}
    }

    public void replace() {
        try
        {
            find();
            if (select_start != -1)
            txt.replaceSelection(textR.getText());
        }
        catch(NullPointerException e)
        {
            System.out.print("Null Pointer Exception: "+e);
        }
    }

    public void replaceAll() {
        txt.setText(txt.getText().toLowerCase().replaceAll(textF.getText().toLowerCase(), textR.getText()));
    }

    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == findBtn)
        {
           find();
        }
        else if(e.getSource() == findNext)
        {
           findNext();
        }
        else if(e.getSource() == replace)
        {
            replace();
        }
        else if(e.getSource() == replaceAll)
        {
           replaceAll();
        }
        else if(e.getSource() == cancel)
        {
           this.setVisible(false);
        }
   }

}