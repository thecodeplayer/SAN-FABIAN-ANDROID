package Models;

public class DictionaryModel {

    private String id;
    private String word;
    private String classification;
    private String filipino_word;
    private String ilocano_word;
    private String pangasinan_word;
    private String english_example;
    private String pangasinan_example;
    private String filipino_example;
    private String ilocano_example;

    public DictionaryModel(String id, String word, String classification, String filipino_word, String pangasinan_word, String ilocano_word, String english_example, String pangasinan_example, String filipino_example, String ilocano_example) {
        this.id = id;
        this.word = word;
        this.classification = classification;
        this.filipino_word = filipino_word;
        this.pangasinan_word = pangasinan_word;
        this.ilocano_word = ilocano_word;
        this.english_example = english_example;
        this.pangasinan_example = pangasinan_example;
        this.filipino_example = filipino_example;
        this.ilocano_example = ilocano_example;
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

    public String getFilipino_word() {
        return filipino_word;
    }

    public void setFilipino_word(String filipino_word) {

        this.filipino_word = filipino_word;
    }

    public String getPangasinan_word() {
        return pangasinan_word;
    }

    public void setPangasinan_word(String pangasinan_word) {
        this.pangasinan_word = pangasinan_word;
    }

    public String getIlocano_word() {
        return ilocano_word;
    }

    public void setIlocano_word(String ilocano_word) {
        this.ilocano_word = ilocano_word;
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

    public String getFilipino_example() {
        return filipino_example;
    }

    public void setFilipino_example(String tagalog_example) {
        this.filipino_example = tagalog_example;
    }

    public String getIlocano_example() {
        return ilocano_example;
    }

    public void setIlocano_example(String ilocano_example) {
        this.ilocano_example = ilocano_example;
    }

}
