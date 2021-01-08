package Models;

public class DictionaryModel {

    private String id;
    private String word;
    private String classification;
    private String pilipino_word;
    private String pangasinan_word;
    private String english_example;
    private String pangasinan_example;

    public DictionaryModel(String id, String word, String classification, String pilipino_word, String pangasinan_word, String english_example, String pangasinan_example) {
        this.id = id;
        this.word = word;
        this.classification = classification;
        this.pilipino_word = pilipino_word;
        this.pangasinan_word = pangasinan_word;
        this.english_example = english_example;
        this.pangasinan_example = pangasinan_example;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getClassification() {
        return classification;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }

    public String getPilipino_word() {
        return pilipino_word;
    }

    public void setPilipino_word(String pilipino_word) {
        this.pilipino_word = pilipino_word;
    }

    public String getPangasinan_word() {
        return pangasinan_word;
    }

    public void setPangasinan_word(String pangasinan_word) {
        this.pangasinan_word = pangasinan_word;
    }

    public String getEnglish_example() {
        return english_example;
    }

    public void setEnglish_example(String english_example) {
        this.english_example = english_example;
    }

    public String getPangasinan_example() {
        return pangasinan_example;
    }

    public void setPangasinan_example(String pangasinan_example) {
        this.pangasinan_example = pangasinan_example;
    }

}
