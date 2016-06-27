package apps.jTask;

import static org.mockito.Mockito.when;

import java.security.Principal;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.timeron.NexusDatabaseLibrary.Entity.JTask;
import com.timeron.NexusDatabaseLibrary.dao.JProjectDAO;
import com.timeron.NexusDatabaseLibrary.dao.JTaskDAO;
import com.timeron.NexusDatabaseLibrary.dao.NexusPersonDAO;
import com.timeron.nexus.apps.jTask.dto.bean.JTaskDTO;
import com.timeron.nexus.apps.jTask.rest.helper.JTaskRestServiceHelper;
import com.timeron.nexus.common.service.ServiceResult;

@RunWith(MockitoJUnitRunner.class)
public class JTaskRestServiceHelperTest {

	@Mock
	JTaskRestServiceHelper jTaskRestServiceHelper;
	
	@Mock
	JTaskDAO jTaskDAO;
	
	@Mock
	NexusPersonDAO nexusPersonDAO;
	
	@Mock
	JProjectDAO jProjectDAO;
	
	@Mock
	Principal principal;

	@Test
	public void addNewTask_withoutUser() {
		Principal principalMock = Mockito.mock(Principal.class);
		ServiceResult result = new ServiceResult();

		JTaskDTO jTaskDto = createTaskDto();
		JTask jTask = Mockito.mock(JTask.class);

		// when
		result = new ServiceResult();
		when(principalMock.getName()).thenReturn(null);
		// then
		result = jTaskRestServiceHelper.addNewTask(jTaskDto, result, principalMock);
		Mockito.verify(jTaskDAO, Mockito.never()).save(jTask);
	}

	@Test
	public void addNewTask_userWithNotExistingName() {
		ServiceResult result = new ServiceResult();

		JTaskDTO jTaskDto = createTaskDto();
		JTask jTask = Mockito.mock(JTask.class);

		// when
		result = new ServiceResult();
		when(principal.getName()).thenReturn("notValid_timeron");
		// then
		result = jTaskRestServiceHelper.addNewTask(jTaskDto, result, principal);
		Mockito.verify(jTaskDAO, Mockito.never()).save(jTask);
	}

	private JTaskDTO createTaskDto() {
		JTaskDTO jTask = new JTaskDTO();
		jTask.setCreated(new Date());
		jTask.setDescription("description");
		jTask.setEndDate(null);
		jTask.setEndDateLong(new Date().getTime() + 1000000);
		jTask.setName("name");
		jTask.setPriority(5);
		jTask.setProjectId(2);
		jTask.setStatus(3);
		jTask.setSummary("summary");
		jTask.setTaskTypeId(2);
		jTask.setUpdated(new Date());
		jTask.setUser(null);
		jTask.setWorkExpected(100000);
		return jTask;
	}
}
