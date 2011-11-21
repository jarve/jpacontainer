package com.vaadin.addon.jpacontainer.integration.hibernate;

import java.io.IOException;

import javax.persistence.EntityManagerFactory;

import org.hibernate.ejb.Ejb3Configuration;
import org.junit.Ignore;
import org.junit.Test;

import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.addon.jpacontainer.JPAContainerFactory;
import com.vaadin.addon.jpacontainer.integration.AbstractComponentIntegrationTest;
import com.vaadin.addon.jpacontainer.testdata.Address;
import com.vaadin.addon.jpacontainer.testdata.EmbeddedIdPerson;
import com.vaadin.addon.jpacontainer.testdata.Name;
import com.vaadin.addon.jpacontainer.testdata.Person;
import com.vaadin.addon.jpacontainer.testdata.PersonSkill;
import com.vaadin.addon.jpacontainer.testdata.Skill;

public class HibernateComponentIntegrationTest extends
        AbstractComponentIntegrationTest {

    public HibernateComponentIntegrationTest() throws IOException {
        super();
    }

    @Override
    public EntityManagerFactory getTestFactory(String dburl) {
        Ejb3Configuration cfg = new Ejb3Configuration()
                .setProperty("hibernate.dialect",
                        "org.hibernate.dialect.HSQLDialect")
                .setProperty("hibernate.connection.driver_class",
                        "org.hsqldb.jdbcDriver")
                .setProperty("hibernate.connection.url", dburl)
                .setProperty("hibernate.connection.username", "sa")
                .setProperty("hibernate.connection.password", "")
                .setProperty("hibernate.connection.pool_size", "1")
                .setProperty("hibernate.connection.autocommit", "true")
                .setProperty("hibernate.cache.provider_class",
                        "org.hibernate.cache.HashtableCacheProvider")
                .setProperty("hibernate.hbm2ddl.auto", "create")
                .setProperty("hibernate.show_sql", "false")
                .addAnnotatedClass(Person.class)
                .addAnnotatedClass(Address.class)
                .addAnnotatedClass(EmbeddedIdPerson.class)
                .addAnnotatedClass(Name.class)
                .addAnnotatedClass(PersonSkill.class)
                .addAnnotatedClass(Skill.class);
        EntityManagerFactory emf = cfg.buildEntityManagerFactory();
        return emf;
    }

    // TODO: entities should be detached, but this causes lazy loading
    // exceptions with hibernate. (note that cached entity providers cannot have
    // non-detached entities).
    @Override
    protected JPAContainer<Person> getPersonContainer() {
        JPAContainer<Person> container = null;
        try {
            container = JPAContainerFactory.makeNonCached(Person.class,
                    getEntityManager());
            container.getEntityProvider().setEntitiesDetached(false);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return container;
    }

    @Override
    @Ignore(value = "Re-enable when lazy loading works")
    @Test
    public void testValueChangeEventsFromEntityProperty() {
    }
}