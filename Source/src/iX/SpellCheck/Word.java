package iX.SpellCheck;

public class Word {
    private String word;
    private Double percent;

    public Word(String w, Double p) {
        word = w;
        percent = p;
    }

    public String getWord(){
        return word;
    }
    public Double getPercent() {
        return percent;
    }

}
