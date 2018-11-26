package actionClass;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.struts2.StrutsTestCase;
import org.mockito.Mockito;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionProxy;

import actionForm.Client_Login;
import daoImpl.Client_Login_DaoImpl;

public class Client_Login_ActionTest extends StrutsTestCase {

	private Client_Login_DaoImpl loginDAO;
	private Client_Login_DaoImpl xyz;
	private Map<String, Object> session;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		// DAO layer mock
		loginDAO = Mockito.mock(Client_Login_DaoImpl.class);
		xyz = Mockito.mock(Client_Login_DaoImpl.class);
		session = new HashMap();
	}

	public void testChangepw() throws Exception {
		request.setParameter("chpw.oldpw", "test1");
		request.setParameter("chpw.newpw", "passnew");
		request.setParameter("chpw.cnewpw", "passnew");
		request.setParameter("chpw.bank_id", "bankid1");
		request.setParameter("chpw.test", "good");

		ActionProxy proxy = getActionProxy("/changepw");
		Client_Login_Action action = (Client_Login_Action) proxy.getAction();
		action.setXyz(xyz);
		action.setSession(session);
		action.setLoginDAO(loginDAO);

		Client_Login actionForm = action.getLogin();

		Mockito.when(xyz.changepw(action.getChpw())).thenReturn(actionForm);

		String result = proxy.execute();

		assertEquals("Result returned form executing the action was not success but it should have been.", "success",
				result);

		Collection<String> actionMsgs = action.getActionMessages();
		assertTrue(actionMsgs.size() == 1);
		assertTrue(actionMsgs.contains("Password changed Successfully. Account will be Logout"));
	}

	public void testBlankInChangepw() throws Exception {
		request.setParameter("chpw.oldpw", "test1");
		request.setParameter("chpw.newpw", "test2");
		request.setParameter("chpw.cnewpw", "test2");
		request.setParameter("chpw.bank_id", "bankid1");
		request.setParameter("chpw.test", "not");

		ActionProxy proxy = getActionProxy("/changepw");
		Client_Login_Action action = (Client_Login_Action) proxy.getAction();
		action.setXyz(xyz);
		action.setSession(session);
		action.setLoginDAO(loginDAO);

		Client_Login actionForm = action.getLogin();

		Mockito.when(xyz.changepw(action.getChpw())).thenReturn(actionForm);

		String result = proxy.execute();

		assertEquals("error", result);

		Collection<String> actionMsgs = action.getActionErrors();
		assertTrue(actionMsgs.size() == 1);
		assertTrue(actionMsgs.contains("Old Password not matching"));
	}

	public void testPasswordNotMatchInChangepw() throws Exception {
		request.setParameter("chpw.oldpw", "test1");
		request.setParameter("chpw.newpw", "");
		request.setParameter("chpw.cnewpw", "");
		request.setParameter("chpw.bank_id", "bankid1");

		ActionProxy proxy = getActionProxy("/changepw");
		Client_Login_Action action = (Client_Login_Action) proxy.getAction();
		action.setXyz(xyz);
		action.setSession(session);
		action.setLoginDAO(loginDAO);

		Client_Login actionForm = action.getLogin();

		Mockito.when(xyz.changepw(action.getChpw())).thenReturn(actionForm);

		String result = proxy.execute();

		assertEquals("error", result);

		Collection<String> actionMsgs = action.getActionErrors();
		assertTrue(actionMsgs.size() == 1);
		assertTrue(actionMsgs.contains("Please Enter All Values"));
	}

	public void testLogout() throws Exception {
		session.put("user", "admin");
		session.put("user0", "user0");
		session.put("user1", "user1");
		ActionContext.getContext().setSession(session);
		ActionProxy proxy = getActionProxy("/logout");
		String result = proxy.execute();

		assertEquals("success", result);
		assertEquals(ActionContext.getContext().getSession().size(), 0);

	}

	public void testCheckLogin() {
		//fail("Not yet implemented");
	}

}
