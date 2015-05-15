package com.kiluet.jguitar.dao.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.openjpa.persistence.jdbc.Index;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Scale", propOrder = {})
@XmlRootElement(name = "scale")
@Entity
@Table(schema = "jguitar", name = "scale")
@NamedQueries({ @NamedQuery(name = "Scale.findAll", query = "SELECT a FROM Scale a order by a.name"),
        @NamedQuery(name = "Scale.findByName", query = "SELECT a FROM Scale a where a.name = :name") })
public class Scale extends PersistantEntity {

    private static final long serialVersionUID = -3070311499735125534L;

    @XmlAttribute
    @Index
    @Column(name = "name")
    private String name;

    @Column(name = "key_type")
    @Enumerated(EnumType.STRING)
    private KeyType key;

    @Column(name = "scale_type")
    @Enumerated(EnumType.STRING)
    private ScaleType type;

    @Column(name = "comments")
    private String comments;

    @ManyToOne
    @JoinColumn(name = "track_fid")
    private Track track;

    public Scale() {
        super();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public KeyType getKey() {
        return key;
    }

    public void setKey(KeyType key) {
        this.key = key;
    }

    public ScaleType getType() {
        return type;
    }

    public void setType(ScaleType type) {
        this.type = type;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Track getTrack() {
        return track;
    }

    public void setTrack(Track track) {
        this.track = track;
    }

    @Override
    public String toString() {
        return String.format("Scale [id=%s, name=%s, key=%s, type=%s, comments=%s]", id, name, key, type, comments);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((comments == null) ? 0 : comments.hashCode());
        result = prime * result + ((key == null) ? 0 : key.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((type == null) ? 0 : type.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        Scale other = (Scale) obj;
        if (comments == null) {
            if (other.comments != null)
                return false;
        } else if (!comments.equals(other.comments))
            return false;
        if (key != other.key)
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (type != other.type)
            return false;
        return true;
    }

}
