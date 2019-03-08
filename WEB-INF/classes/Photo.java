import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

@XmlRootElement(name = "Photo")
public class Photo {

    protected String featured;

    @XmlValue
    public String uri;

    @XmlAttribute(name = "featured")
    public String getFeatured() {
        return this.featured;
    }

    public void setFeatured(String featured) {
        this.featured = featured;
    }
}
