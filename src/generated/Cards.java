//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.03.31 at 10:12:48 AM IST 
//


package generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}surprises"/>
 *         &lt;element ref="{}warrants"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "surprises",
    "warrants"
})
@XmlRootElement(name = "cards")
public class Cards {

    @XmlElement(required = true)
    protected Surprises surprises;
    @XmlElement(required = true)
    protected Warrants warrants;

    /**
     * Gets the value of the surprises property.
     * 
     * @return
     *     possible object is
     *     {@link Surprises }
     *     
     */
    public Surprises getSurprises() {
        return surprises;
    }

    /**
     * Sets the value of the surprises property.
     * 
     * @param value
     *     allowed object is
     *     {@link Surprises }
     *     
     */
    public void setSurprises(Surprises value) {
        this.surprises = value;
    }

    /**
     * Gets the value of the warrants property.
     * 
     * @return
     *     possible object is
     *     {@link Warrants }
     *     
     */
    public Warrants getWarrants() {
        return warrants;
    }

    /**
     * Sets the value of the warrants property.
     * 
     * @param value
     *     allowed object is
     *     {@link Warrants }
     *     
     */
    public void setWarrants(Warrants value) {
        this.warrants = value;
    }

}
