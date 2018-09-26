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
@Table(schema = "jguitar", name = "instrument_string")
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true, includeFieldNames = true)
public class InstrumentString extends PersistantEntity<Long> {

    private static final long serialVersionUID = -3772337130346447202L;

    @Column(name = "string")
    private Integer string;

    @Column(name = "pitch")
    private Integer pitch;

    @ManyToOne
    @JoinColumn(name = "instrument_fid")
    private Instrument instrument;

    public InstrumentString(Instrument instrument, Integer string, Integer pitch) {
        super();
        this.instrument = instrument;
        this.string = string;
        this.pitch = pitch;
    }

}
