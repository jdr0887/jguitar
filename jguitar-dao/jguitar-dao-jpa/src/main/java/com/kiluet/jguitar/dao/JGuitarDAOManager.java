package com.kiluet.jguitar.dao;

import java.io.File;
import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.kiluet.jguitar.dao.jpa.BeatDAOImpl;
import com.kiluet.jguitar.dao.jpa.InstrumentDAOImpl;
import com.kiluet.jguitar.dao.jpa.MeasureDAOImpl;
import com.kiluet.jguitar.dao.jpa.NoteDAOImpl;
import com.kiluet.jguitar.dao.jpa.ScaleDAOImpl;
import com.kiluet.jguitar.dao.jpa.SongDAOImpl;
import com.kiluet.jguitar.dao.jpa.TrackDAOImpl;

public class JGuitarDAOManager {

    private final EntityManagerFactory emf;

    private final EntityManager em;

    private static JGuitarDAOManager instance;

    private JGuitarDAOBean daoBean;

    public static JGuitarDAOManager getInstance() {
        if (instance == null) {
            instance = new JGuitarDAOManager();
        }
        return instance;
    }

    private JGuitarDAOManager() {
        super();
        Properties properties = new Properties();
        String userName = System.getProperty("user.name");
        String userHome = System.getProperty("user.home");
        File jguitarDir = new File(userHome, ".jguitar");
        File jguitarDatabaseDir = new File(jguitarDir, "db");

        System.setProperty("derby.system.home", jguitarDir.getAbsolutePath());

        if (!jguitarDatabaseDir.exists()) {
            properties.put("javax.persistence.jdbc.url",
                    String.format("jdbc:derby:db;create=true;user=%s;password=jguitar", userName));
            properties.put("openjpa.jdbc.SynchronizeMappings", "buildSchema(ForeignKeys=true)");
        } else {
            properties.put("javax.persistence.jdbc.url",
                    String.format("jdbc:derby:db;user=%s;password=jguitar", userName));
        }

        this.emf = Persistence.createEntityManagerFactory("jguitar", properties);
        this.em = emf.createEntityManager();

        this.daoBean = new JGuitarDAOBean();

        SongDAOImpl songDAO = new SongDAOImpl();
        songDAO.setEntityManager(em);
        daoBean.setSongDAO(songDAO);

        ScaleDAOImpl scaleDAO = new ScaleDAOImpl();
        scaleDAO.setEntityManager(em);
        daoBean.setScaleDAO(scaleDAO);

        TrackDAOImpl trackDAO = new TrackDAOImpl();
        trackDAO.setEntityManager(em);
        daoBean.setTrackDAO(trackDAO);

        MeasureDAOImpl measureDAO = new MeasureDAOImpl();
        measureDAO.setEntityManager(em);
        daoBean.setMeasureDAO(measureDAO);

        BeatDAOImpl beatDAO = new BeatDAOImpl();
        beatDAO.setEntityManager(em);
        daoBean.setBeatDAO(beatDAO);

        InstrumentDAOImpl instrumentDAO = new InstrumentDAOImpl();
        instrumentDAO.setEntityManager(em);
        daoBean.setInstrumentDAO(instrumentDAO);

        NoteDAOImpl noteDAO = new NoteDAOImpl();
        noteDAO.setEntityManager(em);
        daoBean.setNoteDAO(noteDAO);

    }

    public JGuitarDAOBean getDaoBean() {
        return daoBean;
    }

    public void setDaoBean(JGuitarDAOBean daoBean) {
        this.daoBean = daoBean;
    }

}
