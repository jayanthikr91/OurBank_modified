package actionClass;

import java.util.ArrayList;

import org.apache.struts2.StrutsTestCase;
import org.mockito.Mockito;

import com.opensymphony.xwork2.ActionProxy;

import actionForm.Bank_Detail;
import daoImpl.Bank_Detail_DaoImpl;

public class Bank_Detail_ActionTest extends StrutsTestCase {

	private Bank_Detail_DaoImpl detailMan;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		detailMan = Mockito.mock(Bank_Detail_DaoImpl.class);
		Mockito.when(detailMan.list()).thenReturn(new ArrayList<Bank_Detail>());
	}

	public void testExecute() throws Exception {
		ActionProxy proxy = getActionProxy("/detail");
		String result = proxy.execute();

		assertEquals("Result returned form executing the action was not success but it should have been.", "success",
				result);

	}

}
