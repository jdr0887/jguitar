package com.kiluet.jguitar.model;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Song", propOrder = {})
@XmlRootElement(name = "song")
public class Song implements Serializable {

    private static final long serialVersionUID = -374280900814171392L;

    @XmlAttribute
    private String name;

    private String artist;

    private String album;

    private String author;

    @XmlAttribute
    private Date created;

    private String copyright;

    private String writer;

    private String transcriber;

    private String comments;

    @XmlElementWrapper(name = "tracks")
    @XmlElement(name = "track")
    private List<Track> tracks;

    private List<Channel> channels;

    public Song() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
            tracks = new LinkedList<Track>();
        }
        return tracks;
    }

    public void setTracks(List<Track> tracks) {
        this.tracks = tracks;
    }

    public List<Channel> getChannels() {
        if (channels == null) {
            channels = new LinkedList<Channel>();
        }
        return channels;
    }

    public void setChannels(List<Channel> channels) {
        this.channels = channels;
    }

    @Override
    public String toString() {
        return String
                .format("Song [name=%s, artist=%s, album=%s, author=%s, created=%s, copyright=%s, writer=%s, transcriber=%s, comments=%s]",
                        name, artist, album, author, created, copyright, writer, transcriber, comments);
    }

}
