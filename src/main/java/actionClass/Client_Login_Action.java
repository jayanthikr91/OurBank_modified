package actionClass;

import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import actionForm.Client_Login;
import daoImpl.Client_Login_DaoImpl;

public class Client_Login_Action extends ActionSupport implements ModelDriven<Object>, SessionAware {

	private Map<String, Object> session;

	private static final long serialVersionUID = -8971757688097732500L;
	private static Logger logger = Logger.getLogger(Client_Login_Action.class);

	private Client_Login login = null;
	private Client_Login chpw;
	private Client_Login_DaoImpl loginDAO = new Client_Login_DaoImpl();
	private Client_Login_DaoImpl xyz = new Client_Login_DaoImpl();

	@Override
	public String execute() {

		return SUCCESS;

	}

	public String checkLogin() {

		if (login.getUserName().equals("") || login.getPassword().equals("") || login.getBank_id().equals("")) {
			addActionError("Please enter all values");

			return ERROR;

		} else {

			login = loginDAO.checkLogin(login);

			if (null != login.getBank_id()) {

				String other = login.getBank_id();
				String uname = login.getUserName();

				session.put("user", "client");
				session.put("user0", uname);
				session.put("user1", other);

				return SUCCESS;

			} else {

				addActionError("Invalid user id/password/ Bank _Id");

				return ERROR;

			}

		}

	}

	public String changepw()

	{
		String a = chpw.getOldpw();
		String b = chpw.getNewpw();
		String c = chpw.getCnewpw();
		if (a.equals("") || b.equals("") || c.equals("")) {
			addActionError("Please Enter All Values");
			return ERROR;
		} else {

			if (b.equals(c)) {

				xyz.changepw(chpw);

				String ss = chpw.getTest();

				logger.debug("From Action Class:  " + ss);
				if ("not".equals(chpw.getTest())) {
					addActionError("Old Password not matching");
					return ERROR;

				}
				if ("good".equals(chpw.getTest())) {
					addActionMessage("Password changed Successfully. Account will be Logout");
				}

			} else {
				addActionError("Password not matching");
				return ERROR;
			}
			return SUCCESS;
		}
	}

	public String logout() throws Exception {

		Map<String, Object> session = ActionContext.getContext().getSession();
		session.remove("user");
		session.remove("user0");
		session.remove("user1");
		session.remove("user2");

		return SUCCESS;
	}

	public Client_Login getChpw() {
		return chpw;
	}

	public void setChpw(Client_Login chpw) {
		this.chpw = chpw;
	}

	public Client_Login_DaoImpl getLoginDAO() {
		return loginDAO;
	}

	public void setLoginDAO(Client_Login_DaoImpl loginDAO) {
		this.loginDAO = loginDAO;
	}

	public Object getModel() {

		login = new Client_Login();

		return login;

	}

	public Client_Login getLogin() {

		return login;

	}

	public void setLogin(Client_Login login) {

		this.login = login;

	}

	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

	public Client_Login_DaoImpl getXyz() {
		return xyz;
	}

	public void setXyz(Client_Login_DaoImpl xyz) {
		this.xyz = xyz;
	}

}
