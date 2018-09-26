package com.kiluet.jguitar.dao.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Index;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAttribute;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(schema = "jguitar", name = "song", indexes = { @Index(columnList = "title", unique = true) })
@NamedQueries({ @NamedQuery(name = "Song.findAll", query = "SELECT a FROM Song a order by a.title"),
        @NamedQuery(name = "Song.findByTitle", query = "SELECT a FROM Song a where a.title = :title") })
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = true, exclude = { "tracks" })
@ToString(callSuper = true, includeFieldNames = true, exclude = { "tracks" })
public class Song extends PersistantEntity<Long> {

    private static final long serialVersionUID = -374280900814171392L;

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

    @OneToMany(mappedBy = "song", cascade = { CascadeType.ALL }, fetch = FetchType.EAGER)
    @OrderBy("number")
    private List<Track> tracks;

}
