package com.kiluet.jguitar.scales.pentatonic;

import java.util.LinkedList;
import java.util.List;

import com.kiluet.jguitar.AbstractPersistRunnable;
import com.kiluet.jguitar.dao.model.KeyType;

public abstract class AbstractPentatonicPersistRunnable extends AbstractPersistRunnable {

    protected static final List<KeyType> keyTypeList = new LinkedList<KeyType>();

    public AbstractPentatonicPersistRunnable() {
        super();
        keyTypeList.add(KeyType.G);
        keyTypeList.add(KeyType.A);
        keyTypeList.add(KeyType.B);
        keyTypeList.add(KeyType.C);
        keyTypeList.add(KeyType.D);
        keyTypeList.add(KeyType.E);
        keyTypeList.add(KeyType.F);
    }

}
