package iX.SpellCheck;

import java.util.Comparator;

public class SimilarityComparator implements Comparator<Word> {

    @Override
    public int compare(Word w1, Word w2) {
        if (w1.getPercent() > w2.getPercent()) {
            return 1;
        } else {
            if (w1.getPercent() < w2.getPercent()) {
                return -1;
            }
        }
        return 0;
    }
}
