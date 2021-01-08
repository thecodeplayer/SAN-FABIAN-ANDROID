package Models;

public class BarangayModel {

    int image;
    String name;
    String description;
    double latitude;
    double longitude;


    public BarangayModel(int image, String name, String description, double latitude, double longitude) {
        this.image = image;
        this.name = name;
        this.description = description;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
