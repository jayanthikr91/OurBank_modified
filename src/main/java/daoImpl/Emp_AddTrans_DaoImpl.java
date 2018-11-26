package daoImpl;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.classic.Session;

import actionForm.Emp_AddTrans;

import java.sql.Timestamp;
import java.util.List;

import util.HibernateUtil;

/**
 * @author VS60001724
 * 
 */
public class Emp_AddTrans_DaoImpl extends HibernateUtil {

	private static Logger logger = Logger.getLogger(Emp_AddTrans_DaoImpl.class);
	private static final long serialVersionUID = 7897804576169374943L;

	/**
	 * @param depo
	 *            Employee Deposit Amount
	 * @return
	 */
	public Emp_AddTrans deposit(Emp_AddTrans depo) {

		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();

		Emp_AddTrans abc = new Emp_AddTrans();

		String clid = depo.getClid();
		abc.setClid(clid);

		String detail = depo.getDetails();
		abc.setDetails(detail);

		String depos = depo.getAmount();
		abc.setDeposit(depos);
		java.util.Date date = new java.util.Date();

		date = new Timestamp(date.getTime());
		try {
			String SQL_QUERY = "SELECT depo.amount FROM Emp_AddTrans depo WHERE depo.clid =:clid"
					+ " ORDER BY depo.id DESC LIMIT 1";

			Query query = session.createQuery(SQL_QUERY);
			query.setParameter("clid", clid);
			
			@SuppressWarnings("rawtypes")
			List results = query.list();
			try {
				String f_amount = (String) results.get(0);

				if (f_amount == null) {
					abc.setAmount(depos);
				} else {

					int sa = Integer.parseInt(f_amount);
					int sb = Integer.parseInt(depos);

					int xy = sa + sb;
					String xyz = Integer.toString(xy);
					abc.setAmount(xyz);
				}
			} catch (Exception e) {
				abc.setAmount(depos);
				logger.error("Error:" + e);
			}
		} catch (HibernateException e) {
			logger.error("Error:" + e);
			abc.setAmount(depos);
			// session.getTransaction().rollback();

		}
		abc.setCreated(date);
		session.save(abc);

		session.getTransaction().commit();
		return depo;
	}

	/**
	 * @param taken
	 *            Employee Withdraw Amount
	 * @return
	 */
	public Emp_AddTrans withdrawn(Emp_AddTrans taken) {

		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();

		Emp_AddTrans abc = new Emp_AddTrans();

		String clid = taken.getClid();
		abc.setClid(clid);

		String detail = taken.getDetails();
		abc.setDetails(detail);

		String withd = taken.getAmount();
		abc.setWithdrawn(withd);

		java.util.Date date = new java.util.Date();

		date = new Timestamp(date.getTime());

		String SQL_QUERY = "SELECT taken.amount FROM Emp_AddTrans taken WHERE taken.clid =:clid"
				+ " ORDER BY taken.id DESC LIMIT 1";

		Query query = session.createQuery(SQL_QUERY);
		query.setParameter("clid", clid);
		
		@SuppressWarnings("rawtypes")
		List results = query.list();
		String f_amount = (String) results.get(0);

		int sa = Integer.parseInt(f_amount);
		int sb = Integer.parseInt(withd);
		int xy = sa - sb;
		taken.setTest("yes");
		taken.setPamount(f_amount);

		if (sa < 1500 || xy < 1500) {
			taken.setTest("not");
			session.getTransaction().rollback();
		} else {
			String xyz = Integer.toString(xy);
			taken.setNamount(xyz);
			abc.setCreated(date);
			abc.setAmount(xyz);
			session.save(abc);
			session.getTransaction().commit();
		}

		return taken;
	}

	/**
	 * @param view
	 *            Employee view Client transactions
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Emp_AddTrans> list(Emp_AddTrans view) {

		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();

		String clid = view.getClid();

		List<Emp_AddTrans> details = null;

		try {

			Query query= session.createQuery("From Emp_AddTrans WHERE clid=:clid");
			query.setParameter("clid", clid);
			details = query.list();

		} catch (HibernateException e) {
			logger.error("Error:" + e);
			session.getTransaction().rollback();
		}

		session.getTransaction().commit();

		return details;

	}

	/**
	 * @param views
	 *            Employee View Client current Balance
	 * @return
	 */
	public Emp_AddTrans vish(Emp_AddTrans views) {

		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();

		String clid = views.getClid();

		try {
			String SQL_QUERY = "SELECT depo.amount FROM Emp_AddTrans depo WHERE depo.clid =:clid"
					+ " ORDER BY depo.id DESC LIMIT 1";

			Query query = session.createQuery(SQL_QUERY);
			query.setParameter("clid", clid);
			@SuppressWarnings("rawtypes")
			List results = query.list();
			String f_amount = (String) results.get(0);

			views.setNamount(f_amount);

		} catch (HibernateException e) {
			logger.error("Error:" + e);
			session.getTransaction().rollback();

		}

		session.getTransaction().commit();
		return views;
	}

}