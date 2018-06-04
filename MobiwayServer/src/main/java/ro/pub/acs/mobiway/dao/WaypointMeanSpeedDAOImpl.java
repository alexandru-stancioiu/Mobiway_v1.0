package ro.pub.acs.mobiway.dao;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Transactional;
import ro.pub.acs.mobiway.model.StreetMeanSpeed;
import ro.pub.acs.mobiway.model.Waypoint;
import ro.pub.acs.mobiway.model.WaypointMeanSpeed;

import java.util.List;

public class WaypointMeanSpeedDAOImpl implements WaypointMeanSpeedDAO {

    private SessionFactory sessionFactory;

    public WaypointMeanSpeedDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    @Transactional
    public List<WaypointMeanSpeed> getAllWaypointMeanSpeeds() {
        List<WaypointMeanSpeed> waypointMeanSpeeds = (List<WaypointMeanSpeed>) sessionFactory.getCurrentSession()
                .createCriteria(WaypointMeanSpeed.class)
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();

        return waypointMeanSpeeds;
    }

    @Override
    @Transactional
    public WaypointMeanSpeed getWaypointMeanSpeed(Double latitude, Double longitude) {

        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(WaypointMeanSpeed.class).
                add(Restrictions.and(
                        Restrictions.eq("waypointLat", latitude),
                        Restrictions.eq("waypointLong", longitude)));

        if (criteria.uniqueResult() != null) {
            return (WaypointMeanSpeed) criteria.uniqueResult();
        } else {
            return null;
        }
    }

    @Override
    public List<WaypointMeanSpeed> getWaypointMeanSpeedByStreetName(String streetName) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(WaypointMeanSpeed.class).
                add(Restrictions.eq("streetName", streetName));
        return criteria.list();
    }

    @Override
    @Transactional
    public void saveOrUpdateWaypointMeanSpeed(WaypointMeanSpeed waypointMeanSpeed) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(waypointMeanSpeed);
    }

    @Override
    @Transactional
    public void clearAllRows() {
        Session session = sessionFactory.getCurrentSession();
        List<WaypointMeanSpeed> waypointMeanSpeeds = getAllWaypointMeanSpeeds();
        for (WaypointMeanSpeed waypointMeanSpeed : waypointMeanSpeeds) {
            session.delete(waypointMeanSpeed);
        }
    }
}
