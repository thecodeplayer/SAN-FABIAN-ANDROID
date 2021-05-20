package Models;

public class RecyclerViewDataModel {

    String imageUrl;
    String title;
    String subtitle;
    String description;
    double latitude;
    double longitude;

    private RecyclerViewDataModel() {}

    public RecyclerViewDataModel(String imageUrl, String title, String subtitle, double latitude, double longitude, String description) {
        this.imageUrl = imageUrl;
        this.title = title;
        this.subtitle = subtitle;
        this.latitude = latitude;
        this.longitude = longitude;
        this.description = description;
    }

    public String getImageUrl() { return imageUrl; }

    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

}
