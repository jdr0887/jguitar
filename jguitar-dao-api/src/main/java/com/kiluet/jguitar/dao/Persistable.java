package com.kiluet.jguitar.dao;

import java.io.Serializable;

public interface Persistable<ID extends Serializable> extends Serializable {

    public ID getId();

}
