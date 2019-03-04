import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlElementWrapper;
import java.util.List;

@XmlRootElement(name = "Hotel")
public class Hotel {

	private String name, description;
	private int id, stars_Rating;

	//@XmlElement(name = "Photos")
	//private Photos photos;

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

	//public void setPhotos(Photos photos) {
	//	this.photos = photos;
	//}

	//public Photos getPhotos() {
	//	return this.photos;
	//}

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

}
