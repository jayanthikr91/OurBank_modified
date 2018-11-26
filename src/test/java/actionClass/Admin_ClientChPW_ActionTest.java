package actionClass;

import java.util.Collection;

import org.apache.struts2.StrutsTestCase;
import org.mockito.Mockito;

import com.opensymphony.xwork2.ActionProxy;

import daoImpl.Admin_ClientChPW_DaoImpl;

public class Admin_ClientChPW_ActionTest extends StrutsTestCase {

	Admin_ClientChPW_DaoImpl mockDAO;
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		// DAO layer mock
		mockDAO = Mockito.mock(Admin_ClientChPW_DaoImpl.class);
	}
	
	public void testChangepw() throws Exception {

		request.setParameter("chpw.oldpw", "test1");
        request.setParameter("chpw.newpw", "passnew");
        request.setParameter("chpw.cnewpw", "passnew");
        request.setParameter("chpw.bank_id", "bankid1");
        
        ActionProxy proxy = getActionProxy("/adm_user_changepw");
        Admin_ClientChPW_Action action = (Admin_ClientChPW_Action) proxy.getAction();
        
        Mockito.when(mockDAO.changepw(action.getChpw())).thenReturn(action.getChpw());
        
        String result = proxy.execute();
 
        assertEquals("Result returned form executing the action was not success but it should have been.", "success", result);
        
        Collection<String> actionMsgs = action.getActionMessages();
        assertTrue(actionMsgs.size()== 1);  
        
 	
	}

	
	public void testInvalidPassordsInChangepw() throws Exception {

		request.setParameter("chpw.oldpw", "test1");
        request.setParameter("chpw.newpw", "");
        request.setParameter("chpw.cnewpw", "");
        request.setParameter("chpw.bank_id", "bankid1");
        
        ActionProxy proxy = getActionProxy("/adm_user_changepw");
        Admin_ClientChPW_Action action = (Admin_ClientChPW_Action) proxy.getAction();
        
        Mockito.when(mockDAO.changepw(action.getChpw())).thenReturn(action.getChpw());
        
        String result = proxy.execute();
 
        assertEquals("error", result);
        
        Collection<String> actionMsgs = action.getActionErrors();
        assertTrue(actionMsgs.size()== 1);  
        assertTrue(actionMsgs.contains("Please Enter All Values"));  
 	
	}
	
	public void testMismatchInChangepw() throws Exception {

		request.setParameter("chpw.oldpw", "test1");
        request.setParameter("chpw.newpw", "test2");
        request.setParameter("chpw.cnewpw", "test3");
        request.setParameter("chpw.bank_id", "bankid1");
        
        ActionProxy proxy = getActionProxy("/adm_user_changepw");
        Admin_ClientChPW_Action action = (Admin_ClientChPW_Action) proxy.getAction();
        
        Mockito.when(mockDAO.changepw(action.getChpw())).thenReturn(action.getChpw());
        
        String result = proxy.execute();
 
        assertEquals("error", result);

        Collection<String> actionMsgs = action.getActionErrors();
        assertTrue(actionMsgs.size()== 1);  
        assertTrue(actionMsgs.contains("Password Not matching"));  
 	
	}
}
