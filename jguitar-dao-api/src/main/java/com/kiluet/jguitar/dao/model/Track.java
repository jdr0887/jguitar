package com.kiluet.jguitar.dao.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(schema = "jguitar", name = "track")
@NamedQueries({ @NamedQuery(name = "Track.findBySongId", query = "SELECT a FROM Track a where a.song.id = :songId order by a.number") })
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = true, exclude = { "instrument", "measures", "song" })
@ToString(callSuper = true, includeFieldNames = true, exclude = { "instrument", "measures", "song" })
public class Track extends PersistantEntity<Long> {

    private static final long serialVersionUID = -705819738289324321L;

    @Column(name = "number")
    private Integer number;

    @Column(name = "offset")
    private Integer offset;

    @Column(name = "channel_id")
    private Integer channelId;

    @Column(name = "solo")
    private Boolean solo;

    private Boolean mute;

    @ManyToOne
    @JoinColumn(name = "instrument_fid")
    private Instrument instrument;

    @Column(name = "color")
    private String color;

    @OneToMany(mappedBy = "track", cascade = { CascadeType.ALL }, fetch = FetchType.EAGER)
    @OrderBy("number")
    private List<Measure> measures;

    @ManyToOne
    @JoinColumn(name = "song_fid")
    private Song song;

    public Track(Song song, Instrument instrument, Integer number) {
        super();
        this.song = song;
        this.instrument = instrument;
        this.number = number;
    }

    public void incrementNumber() {
        number++;
    }

    public void decrementNumber() {
        number--;
    }

}
