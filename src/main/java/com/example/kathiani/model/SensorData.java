import java.util.List;

public class SensorData {
    private int id;
    private String uri;
    private String created_at;
    private String updated_at;
    private double lat;
    private double lon;
    private String status;
    private String collect_interval;
    private String description;
    private String uuid;
    private String city;
    private String neighborhood;
    private String state;
    private String postal_code;
    private String country;
    private List<String> capabilities;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getUri() {
        return uri;
    }
    public void setUri(String uri) {
        this.uri = uri;
    }
    public String getCreated_at() {
        return created_at;
    }
    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
    public String getUpdated_at() {
        return updated_at;
    }
    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }
    public double getLat() {
        return lat;
    }
    public void setLat(double lat) {
        this.lat = lat;
    }
    public double getLon() {
        return lon;
    }
    public void setLon(double lon) {
        this.lon = lon;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getCollect_interval() {
        return collect_interval;
    }
    public void setCollect_interval(String collect_interval) {
        this.collect_interval = collect_interval;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getUuid() {
        return uuid;
    }
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public String getNeighborhood() {
        return neighborhood;
    }
    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }
    public String getState() {
        return state;
    }
    public void setState(String state) {
        this.state = state;
    }
    public String getPostal_code() {
        return postal_code;
    }
    public void setPostal_code(String postal_code) {
        this.postal_code = postal_code;
    }
    public String getCountry() {
        return country;
    }
    public void setCountry(String country) {
        this.country = country;
    }
    public List<String> getCapabilities() {
        return capabilities;
    }
    public void setCapabilities(List<String> capabilities) {
        this.capabilities = capabilities;
    }
    
    public SensorData(int id, String uri, String created_at, String updated_at, double lat, double lon, String status,
            String collect_interval, String description, String uuid, String city, String neighborhood, String state,
            String postal_code, String country, List<String> capabilities) {
        this.id = id;
        this.uri = uri;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.lat = lat;
        this.lon = lon;
        this.status = status;
        this.collect_interval = collect_interval;
        this.description = description;
        this.uuid = uuid;
        this.city = city;
        this.neighborhood = neighborhood;
        this.state = state;
        this.postal_code = postal_code;
        this.country = country;
        this.capabilities = capabilities;
    }

    
}
