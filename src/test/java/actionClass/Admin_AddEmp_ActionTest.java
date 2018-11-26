package actionClass;

import org.apache.struts2.StrutsTestCase;
import org.mockito.Mockito;

import com.opensymphony.xwork2.ActionProxy;

import daoImpl.Admin_AddEmp_DaoImpl;

public class Admin_AddEmp_ActionTest extends StrutsTestCase {

	public void testAddEmp() throws Exception {

		request.setParameter("add.bank_id", "testBankId");
		request.setParameter("add.branch", "branch 1");
		request.setParameter("add.role_name", "Manager");
		request.setParameter("add.firstName", "Name 1");
		request.setParameter("add.middleName", "Name 2");
		request.setParameter("add.lastName", "name 3");
		request.setParameter("add.gender", "Male");
		request.setParameter("add.dob", "01/01/1970");
		request.setParameter("add.landLine", "123456789");
		request.setParameter("add.mobile", "9876543210");
		request.setParameter("add.email", "test@test.com");
		request.setParameter("add.address", "BLR");
		request.setParameter("add.city", "BLR");
		request.setParameter("add.state", "KA");

		ActionProxy proxy = getActionProxy("/addEmp");
		Admin_AddEmp_Action empAct = (Admin_AddEmp_Action) proxy.getAction();
		// DAO layer mock
		Admin_AddEmp_DaoImpl mockDAO = Mockito.mock(Admin_AddEmp_DaoImpl.class);
		Mockito.when(mockDAO.addEmp(empAct.getAdd())).thenReturn(empAct.getAdd());
		empAct.setXyz(mockDAO);

		String result = proxy.execute();

		assertEquals("Result returned form executing the action was not success but it should have been.", "success",
				result);

	}

	public void testAddEmps() throws Exception {

		request.setParameter("adds.userName", "test123");
		request.setParameter("adds.password", "test1");
		request.setParameter("adds.bank_id", "bankid2");

		ActionProxy proxy = getActionProxy("/addEmps");

		Admin_AddEmp_Action empAct = (Admin_AddEmp_Action) proxy.getAction();

		// DAO layer mock
		Admin_AddEmp_DaoImpl mockDAO = Mockito.mock(Admin_AddEmp_DaoImpl.class);
		Mockito.when(mockDAO.addEmps(empAct.getAdds())).thenReturn(empAct.getAdds());
		empAct.setXyz(mockDAO);

		String result = proxy.execute();

		assertEquals(true, "success".equals(result));

		assertTrue(empAct.getActionMessages().size() == 1);
	}

}
