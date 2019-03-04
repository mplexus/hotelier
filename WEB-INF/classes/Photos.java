import java.util.List;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlElement;

@XmlRootElement(name = "Photos")
@XmlAccessorType(XmlAccessType.FIELD)
public class Photos implements Serializable{

    private final static long serialVersionUID = 1L;

    @XmlElement(name = "Photo")
    private List<Photo> photos;

    public List<Photo> getPhotos() {
        return this.photos;
    }

    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
    }
}
