package com.kiluet.jguitar.scales.heptatonic;

import java.util.LinkedList;
import java.util.List;

import com.kiluet.jguitar.AbstractPersistRunnable;
import com.kiluet.jguitar.dao.model.KeyType;

public abstract class AbstractHeptatonicPersistRunnable extends AbstractPersistRunnable {

    protected List<KeyType> keyTypeList;

    public AbstractHeptatonicPersistRunnable() {
        super();
        keyTypeList = new LinkedList<KeyType>();
        keyTypeList.add(KeyType.E);
        keyTypeList.add(KeyType.F);
        keyTypeList.add(KeyType.G);
        keyTypeList.add(KeyType.A);
        keyTypeList.add(KeyType.B);
        keyTypeList.add(KeyType.C);
        keyTypeList.add(KeyType.D);
    }

}
