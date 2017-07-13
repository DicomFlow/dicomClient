package br.ufpb.dicomflow.gui.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import br.ufpb.dicomflow.gui.dao.bean.Persistent;

public class GenericDao {

	public static final int DESC = 1;
	public static final int ASC = 0;


	public static void save(Persistent persistent) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;

		try {
			transaction = session.beginTransaction();
			session.save(persistent);
			transaction.commit();

		} catch (HibernateException e) {
			transaction.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}

	}

	public static void update(Persistent persistent) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;

		try {
			transaction = session.beginTransaction();
			session.update(persistent);
			transaction.commit();

		} catch (HibernateException e) {
			transaction.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}

	}

	public static void delete(Persistent persistent) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;

		try {
			transaction = session.beginTransaction();
			session.delete(persistent);
			transaction.commit();

		} catch (HibernateException e) {
			transaction.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}

	}

	public static void deleteAll(List<Persistent> persistents) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;

		try {
			transaction = session.beginTransaction();

			Iterator<Persistent> it = persistents.iterator();
			while (it.hasNext()) {
				Persistent persistent = (Persistent) it.next();
				session.delete(persistent);
			}

			transaction.commit();

		} catch (HibernateException e) {
			transaction.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}

	}

	@SuppressWarnings("unchecked")
	public static <T> List<Persistent> selectAll(Class<T> persistentClass){

		List<Persistent> persistents = new ArrayList<Persistent>();

		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		Transaction transaction = null;

		try {
			transaction = session.beginTransaction();
			Criteria criteria = session.createCriteria(persistentClass);

			persistents = criteria.list();
			transaction.commit();

		} catch (HibernateException e) {
			transaction.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return persistents;

	}

	@SuppressWarnings("unchecked")
	public static <T> List<Persistent> selectAll(Class<T> persistentClass, String[] properties, Object[] values, int order, String propertyOrder, int page, int max){

		List<Persistent> persistents = new ArrayList<Persistent>();
		if(properties == null || properties.length == 0 || values == null || values.length == 0 || properties.length != values.length){
			return persistents;
		}

		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		Transaction transaction = null;

		try {
			transaction = session.beginTransaction();
			Criteria criteria = session.createCriteria(persistentClass);
			for (int i = 0; i < properties.length; i++) {
				criteria.add(Restrictions.eq(properties[i], values[i]));
			}


			if(order == ASC){
				criteria.addOrder(Order.asc(propertyOrder));
		    }
			if(order == DESC){
		    	criteria.addOrder(Order.desc(propertyOrder));
		    }

			if(page >= 0 && max >= 0){
		        criteria.setFirstResult(page*max);
		        criteria.setMaxResults(max);
		    }

			persistents = criteria.list();
			transaction.commit();

		} catch (HibernateException e) {
			transaction.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return persistents;

	}

	public static <T> Persistent select(Class<T> persistentClass, Integer id){

		Persistent persistent = null;

		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		Transaction transaction = null;

		try {
			transaction = session.beginTransaction();
			persistent = (Persistent) session.get(persistentClass, id);
			transaction.commit();

		} catch (HibernateException e) {
			transaction.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return persistent;

	}

	@SuppressWarnings("unchecked")
	public static <T> List<Persistent> selectAll(Class<T> persistentClass, String property, Object value){

		List<Persistent> persistents = new ArrayList<Persistent>();

		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		Transaction transaction = null;

		try {
			transaction = session.beginTransaction();
			Criteria criteria = session.createCriteria(persistentClass);
			criteria.add(Restrictions.eq(property, value));
			persistents = criteria.list();
			transaction.commit();

		} catch (HibernateException e) {
			transaction.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return persistents;

	}

	public static <T> Persistent select(Class<T> persistentClass, String property, Object value){

		Persistent persistent = null;

		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		Transaction transaction = null;

		try {
			transaction = session.beginTransaction();
			Criteria criteria = session.createCriteria(persistentClass);
			criteria.add(Restrictions.eq(property, value));
			persistent = (Persistent) criteria.uniqueResult();
			transaction.commit();

		} catch (HibernateException e) {
			transaction.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return persistent;

	}


}
