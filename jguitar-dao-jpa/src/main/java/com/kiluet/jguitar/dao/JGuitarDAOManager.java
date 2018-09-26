package com.kiluet.jguitar.dao;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

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
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);

        // ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:/dao-context.xml");
        this.daoBean = ctx.getBean(JGuitarDAOService.class);
    }

    public JGuitarDAOService getDaoBean() {
        return daoBean;
    }

    public void setDaoBean(JGuitarDAOService daoBean) {
        this.daoBean = daoBean;
    }

}
