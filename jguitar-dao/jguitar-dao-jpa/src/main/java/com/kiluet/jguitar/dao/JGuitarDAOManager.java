package com.kiluet.jguitar.dao;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class JGuitarDAOManager {

    private static JGuitarDAOManager instance;

    private JGuitarDAOService daoBean;

    public static JGuitarDAOManager getInstance() {
        if (instance == null) {
            instance = new JGuitarDAOManager();
        }
        return instance;
    }

    private JGuitarDAOManager() {
        super();
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:/dao-context.xml");
        this.daoBean = ctx.getBean(JGuitarDAOService.class);
    }

    public JGuitarDAOService getDaoBean() {
        return daoBean;
    }

    public void setDaoBean(JGuitarDAOService daoBean) {
        this.daoBean = daoBean;
    }

}
