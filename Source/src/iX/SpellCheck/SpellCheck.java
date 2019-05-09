package iX.SpellCheck;

import java.io.IOException;

public class SpellCheck {
    /* deprecated */
    public static void main(String[] args) {
        Organizer org = new Organizer();
        Analyzer analy = new Analyzer();
        try {
			org.loadList();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        analy.setInput("bu");
//        try {
//			analy.start(org);
//		} catch (IOException e) {
////			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
        analy.setOrganizer(org);
        System.out.println(analy.getWordList("pygeun"));
        
        System.out.println(org.getGroupBySyllables("pygeun", org.getGroupBasedOnTerminalLetter("pygeun")));
    }
}
