import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import java.util.List;

@XmlRootElement(name = "Hotel")
public class Hotel {

	private String name, description;
	private int id, stars_Rating;

	@XmlElementWrapper(name = "Photos")
	@XmlElement(name = "Photo")
	public List<Photo> photos;

	public String getName() {
		return this.name;
	}

	@XmlElement(name = "Name")
	public void setName(String name) {
		this.name = name;
	}

    public String getDescription() {
		return this.description;
	}

	@XmlElement(name = "Description")
	public void setDescription(String description) {
		this.description = description;
	}

	public int getStars_Rating() {
		return this.stars_Rating;
	}

	@XmlElement(name = "Stars_Rating")
	public void setStars_Rating(int stars_Rating) {
		this.stars_Rating = stars_Rating;
	}

	public int getId() {
		return this.id;
	}

	@XmlAttribute(name = "id")
	public void setId(int id) {
		this.id = id;
	}

    public String getFeaturedPhotoUri() {
        for (int j = 0; j < this.photos.size(); j++) {
            Photo p = (Photo) this.photos.get(j);
            if (p.getFeatured() != null) {
                return p.uri;
            }
        }

        return "";
    }
}
