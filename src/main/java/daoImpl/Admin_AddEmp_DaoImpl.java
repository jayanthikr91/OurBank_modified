package daoImpl;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.classic.Session;

import actionForm.Admin_AddEmp;
import actionForm.Emp_Login;
import util.HibernateUtil;

/**
 * @author VS60001724
 * 
 */
public class Admin_AddEmp_DaoImpl extends HibernateUtil {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6421383781999253906L;
	private static Logger logger= Logger.getLogger(Admin_AddEmp_DaoImpl.class);
	/**
	 * @param addEmp
	 *            Admin Add Employee
	 * @return
	 */
	public Admin_AddEmp addEmp(Admin_AddEmp addEmp) {

		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();

		try {
			session.save(addEmp);
		} catch (HibernateException e) {
			logger.error("Error while adding employee"+e);
			session.getTransaction().rollback();
		}
		session.getTransaction().commit();

		return addEmp;
	}

	/**
	 * @param addEmps
	 *            Admin Add Employee method
	 * @return
	 */
	public Emp_Login addEmps(Emp_Login addEmps) {

		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		
		Emp_Login abcd =new Emp_Login();
		String bank_id= addEmps.getBank_id();
		abcd.setBank_id(bank_id);
		String uname=addEmps.getUserName();
		abcd.setUserName(uname);
		String pw =addEmps.getPassword();
		String mpw=md5(pw);
		abcd.setPassword(mpw);
		try {
			
			session.save(abcd);
		} catch (HibernateException e) {
			logger.error("Error while adding employee"+e);
			session.getTransaction().rollback();
		}
		session.getTransaction().commit();

		return addEmps;
	}

}
