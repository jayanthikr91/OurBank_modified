package daoImpl;

import java.sql.Timestamp;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.SessionAware;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.classic.Session;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;

import actionForm.Client_Login;
import actionForm.Client_LoginMan;
import util.HibernateUtil;

/**
 * @author VS60001724
 * 
 */
public class Client_Login_DaoImpl extends HibernateUtil implements ModelDriven<Object>, SessionAware {

	/**
	 * 
	 */
	private static final long serialVersionUID = -491146277143196725L;
	private static Logger logger = Logger.getLogger(Client_Login_DaoImpl.class);
	private Map<String, Object> usersession;

	/**
	 * @param login
	 *            Client Login
	 * @return
	 */
	public Client_Login checkLogin(Client_Login login)

	{
		usersession = ActionContext.getContext().getSession();
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();

		String userName = null;
		String password = null;
		String bank_id = null;

		userName = login.getUserName();
		password = login.getPassword();
		bank_id = login.getBank_id();

		password = md5(password);
		String SQL_QUERY = "SELECT login FROM Client_Login login WHERE login.userName = :userName"
				+ " AND login.password =:password AND login.bank_id = :bank_id";

		try {

			logger.debug(SQL_QUERY);

			session.beginTransaction();

			Query query = session.createQuery(SQL_QUERY);
			query.setParameter("password", password);
			query.setParameter("bank_id", bank_id);
			query.setParameter("userName", userName);
			
			@SuppressWarnings("rawtypes")
			Iterator it = query.iterate();

			if (it.hasNext()) {

				login = (Client_Login) it.next();

				Client_LoginMan da = new Client_LoginMan();
				da.setBank_id(bank_id);

				java.util.Date date = new java.util.Date();

				date = new Timestamp(date.getTime());

				da.setCreated(date);

				session.save(da);

				session.getTransaction().commit();

				session = HibernateUtil.getSessionFactory().getCurrentSession();
				session.beginTransaction();

				String SQL_QUERY1 = "SELECT depo.created FROM Client_LoginMan depo WHERE depo.bank_id =:bank_id"
						+ " ORDER BY depo.id DESC";

				Query query1 = session.createQuery(SQL_QUERY1);
				query1.setParameter("bank_id", bank_id);
				@SuppressWarnings("rawtypes")
				List results = query1.list();

				try {
					String se = results.get(1).toString();
					usersession.put("user2", se);
				} catch (Exception e) {
					logger.error(e);
				}

			} else {
				login.setBank_id(null);
			}

		} catch (HibernateException e) {
			logger.error("Error while client login check:" + e);
			session.getTransaction().rollback();

		}
		session.getTransaction().commit();

		return login;

	}

	/**
	 * @param chpw
	 *            Client Change Password
	 * @return
	 */
	public Client_Login changepw(Client_Login chpw) {

		usersession = ActionContext.getContext().getSession();

		String abcd = (String) usersession.get("user1");

		String test = null;
		test = chpw.getOldpw();
		test = md5(test);
		String test2 = null;
		test2 = chpw.getNewpw();
		test2 = md5(test2);
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();

		String SQL_QUERY = "SELECT chpw.password FROM Client_Login chpw WHERE chpw.bank_id =:bank_id"
				+ " AND chpw.password=:password ";
		try {
			Query query = session.createQuery(SQL_QUERY);
			query.setParameter("bank_id",abcd );
			query.setParameter("password",test );
			
			
			@SuppressWarnings("rawtypes")
			List results = query.list();
			String f_amount = (String) results.get(0);

			if (f_amount != null) {

				chpw.setTest("good");

				String SQL_QUERY2 = "UPDATE Client_Login chpw set chpw.password = :password WHERE chpw.bank_id = :abcd";
				Query query2 = session.createQuery(SQL_QUERY2);
				query2.setParameter("password", test2);
				query2.setParameter("abcd", abcd);
				int result = query2.executeUpdate();
				logger.debug("Rows affected: " + result);

			} else {
				chpw.setTest("not");
			}
		} catch (Exception e) {
			chpw.setTest("not");
			logger.error("Error while client password change:" + e);
		}

		session.getTransaction().commit();

		return chpw;
	}

	public Object getModel() {
		return null;
	}

	public void setSession(Map<String, Object> arg0) {
		usersession=arg0;
		
	}

	

}
