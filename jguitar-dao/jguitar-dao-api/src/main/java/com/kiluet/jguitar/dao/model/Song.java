package com.kiluet.jguitar.dao.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.openjpa.persistence.jdbc.Index;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Song", propOrder = {})
@XmlRootElement(name = "song")
@Entity
@Table(schema = "jguitar", name = "song")
@NamedQueries({ @NamedQuery(name = "Song.findAll", query = "SELECT a FROM Song a order by a.title"),
        @NamedQuery(name = "Song.findByTitle", query = "SELECT a FROM Song a where a.title = :title") })
public class Song extends PersistantEntity {

    private static final long serialVersionUID = -374280900814171392L;

    @XmlAttribute
    @Index
    @Column(name = "title")
    private String title;

    @Column(name = "artist")
    private String artist;

    @Column(name = "album")
    private String album;

    @Column(name = "author")
    private String author;

    @XmlAttribute
    @Column(name = "created")
    private Date created;

    @Column(name = "copyright")
    private String copyright;

    @Column(name = "writer")
    private String writer;

    @Column(name = "transcriber")
    private String transcriber;

    @Column(name = "comments")
    private String comments;

    @XmlElementWrapper(name = "tracks")
    @XmlElement(name = "track")
    @OneToMany(mappedBy = "song", cascade = { CascadeType.ALL }, fetch = FetchType.EAGER)
    @OrderBy("number")
    private List<Track> tracks;

    public Song() {
        super();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getTranscriber() {
        return transcriber;
    }

    public void setTranscriber(String transcriber) {
        this.transcriber = transcriber;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public List<Track> getTracks() {
        if (tracks == null) {
            tracks = new ArrayList<Track>();
        }
        return tracks;
    }

    public void setTracks(List<Track> tracks) {
        this.tracks = tracks;
    }

    @Override
    public String toString() {
        return String.format(
                "Song [id=%s, title=%s, artist=%s, album=%s, author=%s, created=%s, copyright=%s, writer=%s, transcriber=%s, comments=%s]",
                id, title, artist, album, author, created, copyright, writer, transcriber, comments);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((album == null) ? 0 : album.hashCode());
        result = prime * result + ((artist == null) ? 0 : artist.hashCode());
        result = prime * result + ((author == null) ? 0 : author.hashCode());
        result = prime * result + ((comments == null) ? 0 : comments.hashCode());
        result = prime * result + ((copyright == null) ? 0 : copyright.hashCode());
        result = prime * result + ((created == null) ? 0 : created.hashCode());
        result = prime * result + ((title == null) ? 0 : title.hashCode());
        result = prime * result + ((transcriber == null) ? 0 : transcriber.hashCode());
        result = prime * result + ((writer == null) ? 0 : writer.hashCode());
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
        Song other = (Song) obj;
        if (album == null) {
            if (other.album != null)
                return false;
        } else if (!album.equals(other.album))
            return false;
        if (artist == null) {
            if (other.artist != null)
                return false;
        } else if (!artist.equals(other.artist))
            return false;
        if (author == null) {
            if (other.author != null)
                return false;
        } else if (!author.equals(other.author))
            return false;
        if (comments == null) {
            if (other.comments != null)
                return false;
        } else if (!comments.equals(other.comments))
            return false;
        if (copyright == null) {
            if (other.copyright != null)
                return false;
        } else if (!copyright.equals(other.copyright))
            return false;
        if (created == null) {
            if (other.created != null)
                return false;
        } else if (!created.equals(other.created))
            return false;
        if (title == null) {
            if (other.title != null)
                return false;
        } else if (!title.equals(other.title))
            return false;
        if (transcriber == null) {
            if (other.transcriber != null)
                return false;
        } else if (!transcriber.equals(other.transcriber))
            return false;
        if (writer == null) {
            if (other.writer != null)
                return false;
        } else if (!writer.equals(other.writer))
            return false;
        return true;
    }

}
