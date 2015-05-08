package com.kiluet.jguitar.config;

import java.io.IOException;
import java.util.Properties;
import java.util.prefs.Preferences;

public class Config {

    private static Config instance = null;

    private Properties properties;

    private Preferences preferences;

    public static Config getInstance() {
        if (instance == null) {
            instance = new Config();
        }
        return instance;
    }

    private Config() {
        super();
        properties = new Properties();
        preferences = Preferences.userRoot().node(this.getClass().getName());
        try {
            this.properties.load(this.getClass().getResourceAsStream("jguitar.properties"));
            // overload with System props
            this.properties.putAll(System.getProperties());
            this.properties.putAll(System.getenv());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Preferences getPreferences() {
        return preferences;
    }

    public void setPreferences(Preferences preferences) {
        this.preferences = preferences;
    }

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }
}
