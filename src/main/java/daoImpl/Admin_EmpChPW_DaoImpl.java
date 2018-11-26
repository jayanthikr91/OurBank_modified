package daoImpl;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.classic.Session;

import actionForm.Admin_EmpChPW;
import util.HibernateUtil;

/**
 * @author VS60001724
 */
public class Admin_EmpChPW_DaoImpl extends HibernateUtil {

	private static Logger logger= Logger.getLogger(Admin_EmpChPW_DaoImpl.class);
	/**
	 * 
	 */
	private static final long serialVersionUID = 2562718248934914287L;

	/**
	 * @param chpw
	 *            Admin Can change Employee password
	 * @return
	 */
	public Admin_EmpChPW changepw(Admin_EmpChPW chpw) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();

		String bank_id = chpw.getBank_id();
		String newpw = chpw.getNewpw();
		//Code added to encrpyt password- Changes begins
		newpw=md5(newpw);
		//Changes ends
		try {
			String SQL_QUERY2 = "UPDATE Admin_EmpChPW set password = :password WHERE bank_id = :bank_id";
			Query query2 = session.createQuery(SQL_QUERY2);
			query2.setParameter("password", newpw);
			query2.setParameter("bank_id", bank_id);
			int result = query2.executeUpdate();
			logger.debug("Rows affected: " + result);
		} catch (HibernateException e) {
			logger.error("Error while changing password of employee:"+e);
			session.getTransaction().rollback();
		}

		session.getTransaction().commit();
		return chpw;
	}

}
