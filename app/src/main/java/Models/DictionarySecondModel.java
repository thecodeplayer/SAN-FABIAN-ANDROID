package Models;

public class DictionarySecondModel {

    private String id;
    private String classification;
    private String english_phrase;
    private String pangasinan_phrase;
    private String filipino_phrase;

    public DictionarySecondModel(String id, String classification, String english_phrase, String pangasinan_phrase, String filipino_phrase) {
        this.id = id;
        this.english_phrase = english_phrase;
        this.pangasinan_phrase = pangasinan_phrase;
        this.classification = classification;
        this.filipino_phrase = filipino_phrase;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClassification() {
        return classification;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }

    public String getEnglish_phrase() {
        return english_phrase;
    }

    public void setEnglish_phrase(String english_phrase) {
        this.english_phrase = english_phrase;
    }

    public String getPangasinan_phrase() {
        return pangasinan_phrase;
    }

    public void setPangasinan_phrase(String pangasinan_phrase) {
        this.pangasinan_phrase = pangasinan_phrase;
    }

    public String getFilipino_phrase() {
        return filipino_phrase;
    }

    public void setFilipino_phrase(String filipino_phrase) {
        this.filipino_phrase = filipino_phrase;
    }

}
