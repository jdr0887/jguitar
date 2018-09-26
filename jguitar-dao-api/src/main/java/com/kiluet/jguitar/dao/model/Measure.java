package com.kiluet.jguitar.dao.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
@Table(schema = "jguitar", name = "measure")
@NamedQueries({
        @NamedQuery(name = "Measure.findByTrackId", query = "SELECT a FROM Measure a where a.track.id = :trackId order by a.number") })
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = true, exclude = { "track", "beats" })
@ToString(callSuper = true, includeFieldNames = true, exclude = { "track", "beats" })
public class Measure extends PersistantEntity<Long> {

    private static final long serialVersionUID = -8979175208508024995L;

    @Column(name = "number")
    private Integer number;

    @Column(name = "start")
    private Long start;

    @Column(name = "meter_type")
    @Enumerated(EnumType.STRING)
    private MeterType meterType;

    @Column(name = "tempo")
    private Integer tempo;

    @Column(name = "open_repeat")
    private Boolean openRepeat = Boolean.FALSE;

    @Column(name = "alternate_repeat")
    private Integer alternativeRepeat;

    @Column(name = "close_repeat")
    private Integer closeRepeat;

    @Column(name = "triplet_feel")
    private Integer tripletFeel;

    @OneToMany(mappedBy = "measure", cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
    @OrderBy("number")
    private List<Beat> beats;

    @ManyToOne
    @JoinColumn(name = "track_fid")
    private Track track;

    public Measure(Track track, MeterType meterType, Integer tempo, Integer number) {
        super();
        this.track = track;
        this.meterType = meterType;
        this.tempo = tempo;
        this.number = number;
    }

    public void incrementNumber() {
        number++;
    }

    public void decrementNumber() {
        number--;
    }

}
