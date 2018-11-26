package daoImpl;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.classic.Session;

import actionForm.Bank_Detail;
import dao.IBank_Detail_DAO;
import util.HibernateUtil;

/**
 * @author VS60001724
 *
 */
public class Bank_Detail_DaoImpl extends HibernateUtil implements IBank_Detail_DAO {

	private static final long serialVersionUID = -3335727850883870378L;
	private static Logger logger = Logger.getLogger(Admin_Login_DaoImpl.class);

	@SuppressWarnings("unchecked")
	public List<Bank_Detail> list() {

		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();

		List<Bank_Detail> details = null;

		try {

			details = (List<Bank_Detail>) session.createQuery("from Bank_Detail").list();

		} catch (HibernateException e) {
			logger.error("Error while listing:" + e);
			session.getTransaction().rollback();
		}

		session.getTransaction().commit();
		return details;
	}

	public Bank_Detail addBank(Bank_Detail add) {

		return add;
	}

}
