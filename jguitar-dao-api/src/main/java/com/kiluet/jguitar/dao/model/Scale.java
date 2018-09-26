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
import javax.persistence.UniqueConstraint;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(schema = "jguitar", name = "scale", uniqueConstraints = {
        @UniqueConstraint(columnNames = { "id", "name", "key_type", "scale_type" }) })
@NamedQueries({ @NamedQuery(name = "Scale.findAll", query = "SELECT a FROM Scale a order by a.name"),
        @NamedQuery(name = "Scale.findByName", query = "SELECT a FROM Scale a where a.name = :name") })
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true, includeFieldNames = true)
public class Scale extends PersistantEntity<Long> {

    private static final long serialVersionUID = -3070311499735125534L;

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

}
