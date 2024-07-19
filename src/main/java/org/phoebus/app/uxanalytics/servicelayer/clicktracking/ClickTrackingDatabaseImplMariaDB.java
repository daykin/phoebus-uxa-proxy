package org.phoebus.app.uxanalytics.servicelayer.clicktracking;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class ClickTrackingDatabaseImplMariaDB implements ClickTrackingDatabase{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void recordClick(ClickBean clickBean) {
        entityManager.persist(clickBean);
    }

}
