package com.kiluet.jguitar.dao.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(schema = "jguitar", name = "note")
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true, includeFieldNames = true)
public class Note extends PersistantEntity<Long> {

    private static final long serialVersionUID = 3627594670573036614L;

    @Column(name = "string", length = 1)
    private Integer string;

    @Column(name = "value")
    private Integer value;

    @Column(name = "velocity")
    private Integer velocity;

    @ManyToOne
    @JoinColumn(name = "beat_fid")
    private Beat beat;

    public Note(Integer string) {
        super();
        this.string = string;
    }

    public Note(Beat beat, Integer string, Integer value, Integer velocity) {
        super();
        this.beat = beat;
        this.string = string;
        this.value = value;
        this.velocity = velocity;
    }

}