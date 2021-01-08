package Models;

public class CategoriesPagerModel {

    String imageUrl;
    String title;
    String description;
    Double latitude;

    private CategoriesPagerModel() {}

    public CategoriesPagerModel(String imageUrl, String title, String description, Double latitude) {
        this.imageUrl = imageUrl;
        this.title = title;
        this.description = description;
        this.latitude = latitude;
    }

    public String getImageUrl() { return imageUrl; }

    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getLatitude() { return latitude; }

    public void setLatitude(Double latitude) { this.latitude = latitude; }

}
