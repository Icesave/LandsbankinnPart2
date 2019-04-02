package is.landsbankinn.eta.models;


import java.util.List;

public class SearchParam {
    private String name;
    private List<String> genres;
    private String price;



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getTags() {
        return genres;
    }

    public void setTags(List<String> tags) {
        this.genres = tags;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
