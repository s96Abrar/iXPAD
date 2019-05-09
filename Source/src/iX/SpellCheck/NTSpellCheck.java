package iX.SpellCheck;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.util.*;
import javax.swing.text.*;
import javax.swing.text.Highlighter.*;

public class NTSpellCheck implements ActionListener, MouseListener, Runnable {

    public static final int WAVE_UNDERLINE = 372110;
    public static final int PLAIN_HIGHLIGHT = 372111;
    private final String spChars = "\r\n\t\f\"\\'`~!@#$%^&*()_+-={}|[]:;<>?,./ “”‘’\b\u0085\u2028\u2029";
    JTextComponent tc;
    File dictionaryFile;
    boolean scFlag = true;
//    Hashtable dictionary = new Hashtable();
//    Hashtable dictionaryWords = new Hashtable();
    JPopupMenu rightClickMenu = new JPopupMenu();
    JMenu file = new JMenu("File");
    JMenuItem rcIgnore = new JMenuItem("Ignore");
    JMenuItem rcAdd = new JMenuItem("Add to Dictionary");
    int whichWord = 0;
    boolean wordCountFlag = true;
    javax.swing.Timer scTimer = new javax.swing.Timer(25, this);
    HighlightPainter myPainter;

    private Organizer orgn = new Organizer();
    private Analyzer analy = new Analyzer();
    public NTSpellCheck(JTextComponent tc,/* File dictionaryFile,*/ boolean checkSpelling, boolean manageRightClicks, int delay, Color highlightColor, int type) throws IOException {

        this.tc = tc;
//        this.dictionaryFile = dictionaryFile;
        scFlag = checkSpelling;
//        InputStream is = new FileInputStream(this.dictionaryFile);
//        BufferedReader br = new BufferedReader(new InputStreamReader(is));
//        String s = "";
//        StringBuffer buff = new StringBuffer();
//        while ((s = br.readLine()) != null) {
//            dictionaryWords.put(s.toLowerCase(), "ok");
//            String pro = getPronunciation(s);
//            if (dictionary.get(pro) != null) {
//                String ss = (String) dictionary.get(pro);
//                dictionary.remove(pro);
//                dictionary.put(pro, ss + " " + s);
//            } else {
//                dictionary.put(pro, s);
//            }
//        }
        
        orgn.loadList();
        scTimer.setRepeats(false);
        if (manageRightClicks) {
            tc.addMouseListener(this);
        }
        tc.addKeyListener(new KeyAdapter() {

            @Override
            public void keyPressed(KeyEvent e) {
                if (spChars.indexOf(e.getKeyChar()) != -1 && scFlag) {
                    scTimer.restart();
                }
            }
        });
        scTimer.setDelay(delay);
        if (scFlag) {
            new Thread(this).start();
            scTimer.restart();
        }
        myPainter = new WaveHighlighter(Color.RED);

    }

    Vector getAllSubsets(String superSet) {
        Vector returnVal = new Vector();
        int min = (int) Math.rint(2.0 * (double) superSet.length() / 3.0);
        for (int i = min; i <= superSet.length(); i++) {
            int j = 0;
            for (j = 0; j + i < superSet.length(); j++) {
                String str = superSet.substring(j, j + i);
                if (!returnVal.contains(str)) {
                    returnVal.addElement(str);
                }
            }
            String temp = "";
            for (int z = superSet.length() - i; z < superSet.length(); z++) {
                temp += superSet.charAt(z);
            }
            if (!returnVal.contains(temp)) {
                returnVal.add(temp);
            }
        }
        for (int i = 0; i < superSet.length() - 1; i++) {
            String temp = superSet.substring(0, i) + superSet.substring(i + 1, superSet.length());
            if (!returnVal.contains(temp)) {
                returnVal.add(temp);
            }
        }
        return returnVal;
    }

//    synchronized Vector listSimilarWords(String misspelt) {
//
//        Vector temp = new Vector();
//        temp.clear();
//        temp.trimToSize();
//        temp.ensureCapacity(1);
//
//        String misspelt1 = misspelt;
//        misspelt = misspelt.toLowerCase();
//
////        String returnString = getPronunciation(misspelt);
//
//        for (int i = 0; i < misspelt.length(); i++) {
//            String one = misspelt.substring(0, i + 1);
//            String two = misspelt.substring(i + 1, misspelt.length());
//            if (dictionaryWords.get(one) != null && dictionaryWords.get(two) != null) {
//                String finstr = "";
//                if (one.equals(one.toLowerCase())) {
//                    for (int pos = 0; pos < one.length(); pos++) {
//                        if (Character.isUpperCase(misspelt.charAt(pos))) {
//                            finstr += Character.toUpperCase(one.charAt(pos));
//                        } else {
//                            finstr += one.charAt(pos);
//                        }
//                    }
//                } else {
//                    finstr += one;
//                }
//                finstr += " ";
//                if (two.equals(two.toLowerCase())) {
//                    for (int pos = 0; pos < two.length(); pos++) {
//                        if (Character.isUpperCase(misspelt.charAt(pos))) {
//                            finstr += Character.toUpperCase(two.charAt(pos));
//                        } else {
//                            finstr += two.charAt(pos);
//                        }
//                    }
//                } else {
//                    finstr += two;
//                }
//                if (!temp.contains(finstr)) {
//                    temp.add(finstr);
//                }
//            }
//        }
//
////        if (dictionary.get(returnString) != null) {
////            String s = (String) dictionary.get(returnString);
////            StringTokenizer st = new StringTokenizer(s, " ");
////            while (st.hasMoreTokens()) {
////                String str = st.nextToken();
////                String finstr = "";
////                int i = 0;
////                finstr += Character.isUpperCase(misspelt1.charAt(i)) ? Character.toUpperCase(str.charAt(i)) : Character.toLowerCase(str.charAt(i));
////                finstr += str.substring(1, str.length());
////                if (!temp.contains(finstr)) {
////                    temp.add(finstr);
////                }
////            }
////        }
//
//        Vector subsets = getAllSubsets(misspelt);
//        Iterator it = subsets.iterator();
//        while (it.hasNext()) {
//            String str = (String) it.next();
//            System.out.println("String " + str);
////            if (dictionary.get(getPronunciation(str)) != null) {
////                String s = (String) dictionary.get(getPronunciation(str));
////                StringTokenizer st = new StringTokenizer(s, " ");
////                while (st.hasMoreTokens()) {
////                    String string = st.nextToken();
////                    String finstr = "";
////                    int i = 0;
////                    finstr += Character.isUpperCase(misspelt1.charAt(i)) ? Character.toUpperCase(string.charAt(i)) : Character.toLowerCase(string.charAt(i));
////                    finstr += string.substring(1, string.length());
////                    if (finstr.length() >= 2 * misspelt.length() / 3 && !temp.contains(finstr)) {
////                        temp.add(finstr);
////                    }
////                }
////            }
//        }
//
//// -----------------------------------------------------OLD ALGORITHM----------------------------------------------------
////        String s = (String) dictionaryWords.get("0");
////        for (int z = 0; (s = (String) dictionaryWords.get(String.valueOf(z))) != null; z++) {
////            int n = 0;
////            if (misspelt.length() > s.length()) {
////                n = s.length() * 2;
////            } else {
////                n = misspelt.length() * 2;
////            }
////            int x = n / 2;
////            for (int i = 0; i < x; i++) {
////                if (s.charAt(i) == misspelt.charAt(i)) {
////                    n--;
////                }
////                if (s.charAt((s.length() - 1) - i) == misspelt.charAt((misspelt.length() - 1) - i)) {
////                    n--;
////                }
////            }
////            int r = (int) Math.ceil((x + 2) / 2);
////            if (n <= r && n > -1 && s.length() - misspelt.length() > -2 && s.length() - misspelt.length() < 2) {
////                dlm.addElement(s);
////                temp.add(s);
////            } else if (s.indexOf(misspelt) != -1 && s.length() - misspelt.length() > -2 && s.length() - misspelt.length() < 2) {
////                dlm.addElement(s);
////                temp.add(s);
////            } else if (misspelt.indexOf(s) != -1 && s.length() - misspelt.length() > -2 && s.length() - misspelt.length() < 2) {
////                dlm.addElement(s);
////                temp.add(s);
////            } else if (misspelt.length() % 3 == 0) {
////                int third = misspelt.length() / 3;
////                int third2 = third * 2;
////                if ((s.startsWith(misspelt.substring(0, third2)) || s.endsWith(misspelt.substring(third, misspelt.length()))) && s.length() - misspelt.length() > -2 && s.length() - misspelt.length() < 2) {
////                    dlm.addElement(s);
////                    temp.add(s);
////                }
////            }
////        }
////
////
////
////        if (dlm.size() < 3) {
////            for (int z = 0; (s = (String) dictionaryWords.get(String.valueOf(z))) != null; z++) {
////                if (s.length() - misspelt.length() > -3 && s.length() - misspelt.length() < 3) {
////                    for (int i = 1; i < (misspelt.length() - 1); i++) {
////                        if (s.startsWith(misspelt.substring(0, i)) && s.endsWith(misspelt.substring(i + 1, misspelt.length()))) {
////                            dlm.addElement(s);
////                            temp.add(s);
////                            break;
////                        }
////                    }
////                }
////            }
////        }
//// ---------------------------------------------------END OLD ALGORITHM--------------------------------------------------
//
//
//
//        if (temp.isEmpty()) {
//            temp.add("No matches were found");
//        }
//
//        temp.trimToSize();
//        return temp;
//
//    }

//    synchronized void doSCHighlights() {
//        try {
//            Vector x = new Vector();
//            Vector y = new Vector();
//            if (tc.getText().indexOf(" ") == -1 && tc.getText().indexOf("\r") == -1 && tc.getText().indexOf("\n") == -1) {
//                return;
//            }
//            int one = tc.getCaretPosition() - 5000;
//            if (one < 0) {
//                one = 0;
//            }
//            int two = tc.getCaretPosition() + 5000;
//            if (two > tc.getText().length()) {
//                two = tc.getText().length();
//            }
//            String string = tc.getText(one, two);
//            int z = 0;
//            StringTokenizer tokens = new StringTokenizer(string, spChars, true);
//            while (tokens.hasMoreTokens()) {
//                String s = tokens.nextToken();
//                if (checkSpelling(s) && spChars.indexOf(s) == -1) {
//                    x.add(new Integer(z));
//                    y.add(new Integer(z + s.length()));
//                }
//                z += s.length();
//            }
//            int r = x.size();
//            tc.getHighlighter().removeAllHighlights();
//            for (int i = 0; i < r; i++) {
//                tc.getHighlighter().addHighlight(Integer.parseInt(x.get(i).toString()), Integer.parseInt(y.get(i).toString()), myPainter);
//            }
//        } catch (Exception e) {
//            return;
//        }
//    }

//    boolean checkSpelling(String misspelt) {
//
//        misspelt = misspelt.trim();
//
//        if (misspelt.equals(misspelt.toUpperCase())) {
//            return false;
//        }
//        if (misspelt.length() == 1) {
//            return false;
//        }
//        if (dictionaryWords.get(misspelt.toLowerCase()) == null) {
//
//            return true;
//
//        } else {
//            return false;
//        }
//    }

    @Override
    public void actionPerformed(ActionEvent ae) {

        if (ae.getSource() == scTimer) {
//            doSCHighlights();
        }

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (tc.getSelectedText() == null) {
            Point pt = new Point(e.getX(), e.getY());
            Position.Bias[] biasRet = new Position.Bias[1];
            int pos = tc.getUI().viewToModel(tc, pt, biasRet);
            if (biasRet[0] == null) {
                biasRet[0] = Position.Bias.Forward;
            }
            if (pos >= 0) {
                tc.getCaret().setDot(pos);
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
//        if (e.isPopupTrigger()) {
//            rightClickMenu.removeAll();
//            int one = tc.getCaretPosition() - 1000;
//            if (one < 0) {
//                one = 0;
//            }
//            int two = tc.getCaretPosition() + 1000;
//            if (two > tc.getText().length()) {
//                two = tc.getText().length();
//            }
//            String string = tc.getText().substring(one, two);
//            StringTokenizer tok = new StringTokenizer(string, spChars);
//            String stemp = "";
//            int z = 0;
//            if (tok.hasMoreTokens()) {
//                do {
//                    stemp = tok.nextToken();
//                    z++;
//                } while (tok.hasMoreTokens() && z < whichWord);
//            }
//            if (!stemp.equals("")) {
//                final String str = stemp;
//                if (checkSpelling(str)) {
//                    JMenu similarWords = new JMenu("Similar Matches");
//                    Vector v = listSimilarWords(str);
//                    for (int i = 0; i < 15 && i < v.size(); i++) {
//                        JMenuItem temp = new JMenuItem((String) v.get(i));
//                        temp.addActionListener(new ActionListener() {
//
//                            public void actionPerformed(ActionEvent ae) {
//                                int startPos = tc.getText().lastIndexOf(str, tc.getCaretPosition());
//                                tc.select(startPos, startPos + str.length());
//                                tc.replaceSelection(ae.getActionCommand());
//                                tc.getCaret().setDot(startPos);
//                                if (scFlag) {
//                                    scTimer.restart();
//                                }
//                            }
//                        });
//                        if (temp.getText().equals("No matches were found")) {
//                            temp.setEnabled(false);
//                        }
//                        similarWords.add(temp);
//                    }
//                    similarWords.addSeparator();
//                    similarWords.add(rcIgnore);
//                    rcIgnore.setActionCommand(str);
//                    similarWords.add(rcAdd);
//                    rcAdd.setActionCommand(str);
//                    rightClickMenu.add(similarWords);
//                    rightClickMenu.addSeparator();
//                }
//            }
//            rightClickMenu.validate();
//            rightClickMenu.show(tc, e.getX(), e.getY());
//        }
    }

    @Override
    public void mouseExited(MouseEvent me) {
    }

    @Override
    public void mouseEntered(MouseEvent me) {
    }

    @Override
    public void mouseClicked(MouseEvent me) {
    }

    private void wordCount() {

        try {

            int carPos = tc.getCaretPosition();
            String splited = tc.getText().substring(0, carPos);
            StringTokenizer splitedWords = new StringTokenizer(splited, spChars);
            whichWord = splitedWords.countTokens();

        } catch (Exception e) {

            return;

        }

    }

    public void run() {
        while (scFlag) {
            int old = whichWord;
            wordCount();
            if (whichWord != old) scTimer.restart();
        }
    }

    void toggleSpellCheck() {
        synchronized (this) {
            if (scFlag) {
                scFlag = false;
                tc.getHighlighter().removeAllHighlights();
                scTimer.stop();
            } else {
                scFlag = true;
                new Thread(this).start();
//                doSCHighlights();
                scTimer.start();
            }
        }
    }

    void setHighlightDelay(int delay) {
        scTimer.setDelay(delay);
        scTimer.restart();
    }

    void setHighlightColor(Color newColor) {
        ((WaveHighlighter) myPainter).setColor(newColor);
        if (scFlag) {
//            doSCHighlights();
            scTimer.restart();
        }
    }

//    String getPronunciation(String word) {
//        word = word.toLowerCase();
//        String pronunciation = "";
//        for (int i = 0; i < word.length(); i++) {
//            String letter = String.valueOf(word.charAt(i));
//            if ("bdfghklmnprvwz".indexOf(letter) != -1) {
//                if (word.indexOf(letter + letter, i) == i) {
//                    pronunciation += i;
//                    i++;
//                } else {
//                    pronunciation += letter;
//                }
//            } else if (letter.equals("e")) {
//                if (word.length() > i + 2 && word.charAt(i + 1) == 'a' && word.charAt(i + 2) == 'u') {
//                    pronunciation += "u";
//                    i += 2;
//                } else if (word.length() > i + 1 && (word.charAt(i + 1) == 'a' || word.charAt(i + 1) == 'e' || word.charAt(i + 1) == 'i')) {
//                    pronunciation += "ee";
//                    i++;
//                } else if (i > 1 && "aeiou".indexOf(String.valueOf(word.charAt(i - 2))) == -1) {
//                    pronunciation += "e";
//                } else {
//                    continue;
//                }
//            } else if (letter.equals("a")) {
//                if ((word.length() > i + 2 && "aeiouy".indexOf(word.charAt(i + 2)) != -1)) {
//                    pronunciation += "A";
//                } else if (word.length() > i + 1 && (word.charAt(i + 1) == 'e' || word.charAt(i + 1) == 'y')) {
//                    pronunciation += "ay";
//                    i++;
//                } else if (word.length() > i + 2 && word.charAt(i + 1) == 'i' && "bcdfghjklmnpqrstvwxz".indexOf(word.charAt(i + 2)) != -1) {
//                    pronunciation += "ay";
//                    i++;
//                } else if (word.length() > i + 1 && (word.charAt(i + 1) == 'u' || word.charAt(i + 1) == 'a')) {
//                    pronunciation += "o";
//                    i++;
//                } else if (word.length() == 1) {
//                    pronunciation += "u";
//                } else {
//                    pronunciation += "a";
//                }
//            } else if (letter.equals("e")) {
//                if (word.length() > i + 2 && word.charAt(i + 1) == 'a' && word.charAt(i + 2) == 'u') {
//                    pronunciation += "u";
//                    i += 2;
//                } else if (word.length() > i + 1 && (word.charAt(i + 1) == 'a' || word.charAt(i + 1) == 'e' || word.charAt(i + 1) == 'i')) {
//                    pronunciation += "ee";
//                    i++;
//                } else if (i > 1 && "aeiou".indexOf(String.valueOf(word.charAt(i - 2))) == -1) {
//                    pronunciation += "e";
//                } else {
//                    continue;
//                }
//            } else if (letter.equals("i")) {
//                if (word.equals("i") || (word.length() > i + 2 && word.charAt(i + 2) == 'e')) {
//                    pronunciation += "I";
//                } else if (word.length() > i + 1 && word.charAt(i + 1) == 'i') {
//                    pronunciation += "i";
//                    i++;
//                } else {
//                    pronunciation += "i";
//                }
//            } else if (letter.equals("o")) {
//                if (word.length() > i + 1 && word.charAt(i + 1) == 'o') {
//                    pronunciation += "u";
//                    i++;
//                } else if (word.length() > i + 2 && word.charAt(i + 2) == 'e') {
//                    pronunciation += "O";
//                } else if (word.length() > i + 1 && word.charAt(i + 1) == 'u') {
//                    pronunciation += "u";
//                    i++;
//                } else if (word.length() > i + 1 && word.charAt(i + 1) == 'a') {
//                    pronunciation += "O";
//                    i++;
//                } else {
//                    pronunciation += "o";
//                }
//            } else if (letter.equals("u")) {
//                if (word.length() > i + 2 && word.charAt(i + 2) == 'e') {
//                    pronunciation += "U";
//                } else {
//                    pronunciation += "u";
//                }
//            } else if (letter.equals("c")) {
//                if (word.indexOf("cion", i) == i) {
//                    pronunciation += "shun";
//                    i += 3;
//                } else if (word.length() > i + 1 && "aeiouy".indexOf(word.charAt(i + 1)) != -1) {
//                    pronunciation += "s";
//                } else {
//                    pronunciation += "k";
//                }
//            } else if (letter.equals("j")) {
//                pronunciation += "g";
//            } else if (letter.equals("t")) {
//                if (word.indexOf("tion", i) == i) {
//                    pronunciation += "shun";
//                    i += 3;
//                } else {
//                    pronunciation += "t";
//                }
//            } else if (letter.equals("s")) {
//                if (word.indexOf("sion", i) == i) {
//                    pronunciation += "shun";
//                    i += 3;
//                } else {
//                    pronunciation += "s";
//                }
//            } else if (letter.equals("x")) {
//                if (i == 0) {
//                    pronunciation += "z";
//                } else {
//                    pronunciation += "x";
//                }
//            } else if (letter.equals("q")) {
//                if (word.length() > i + 1 && word.charAt(i + 1) == 'u') {
//                    pronunciation += "kw";
//                    i++;
//                } else {
//                    pronunciation += "k";
//                }
//            } else if (letter.equals("y")) {
//                if (i == word.length() - 1 || (i != 0 && i != word.length() - 1 && "bcdfghjklmnpqrstvwxyz".indexOf(word.charAt(i - 1)) != -1 && "bcdfghjklmnpqrstvwxyz".indexOf(word.charAt(i + 1)) != -1)) {
//                    pronunciation += "i";
//                } else {
//                    pronunciation += "y";
//                }
//            }
//        }
//        return pronunciation;
//    }

}