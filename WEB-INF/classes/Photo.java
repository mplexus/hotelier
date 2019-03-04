import java.util.List;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlValue;

@XmlRootElement(name = "Photo")
@XmlAccessorType(XmlAccessType.FIELD)
public class Photo implements Serializable {

    protected boolean featured;

    @XmlValue
    protected String content;

    @XmlAttribute(name = "featured")
    public boolean getFeatured() {
        return this.featured;
    }

    public void setFeatured(String featured) {
        this.featured = true;
    }

    public void setContent(String content) {
            this.content = content;
        }
        public String getContent() {
            return content;
        }
}
