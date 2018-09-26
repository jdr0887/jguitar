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
@Table(schema = "jguitar", name = "beat")
@NamedQueries({
        @NamedQuery(name = "Beat.findByMeasureId", query = "SELECT a FROM Beat a where a.measure.id = :measureId order by a.number") })
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = true, exclude = { "notes", "measure" })
@ToString(callSuper = true, includeFieldNames = true, exclude = { "notes", "measure" })
public class Beat extends PersistantEntity<Long> {

    private static final long serialVersionUID = -8296658472802163357L;

    @Column(name = "number", length = 2)
    private Integer number;

    @Column(name = "duration_type")
    @Enumerated(EnumType.STRING)
    private DurationType duration;

    @OneToMany(mappedBy = "beat", cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
    @OrderBy("string")
    private List<Note> notes;

    @ManyToOne
    @JoinColumn(name = "measure_fid")
    private Measure measure;

    public Beat(Measure measure, DurationType duration, Integer number) {
        super();
        this.measure = measure;
        this.duration = duration;
        this.number = number;
    }

    public void incrementNumber() {
        number++;
    }

    public void decrementNumber() {
        number--;
    }

}