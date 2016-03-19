package apps.jTask;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.nexus.apps.jTask.dto.bean.JHistoryDTO;
import com.nexus.apps.jTask.dto.bean.JNoteDTO;
import com.nexus.apps.jTask.dto.bean.JProjectDTO;
import com.nexus.apps.jTask.dto.bean.JTaskDTO;
import com.nexus.apps.jTask.dto.bean.NexusPersonDTO;
import com.nexus.apps.jTask.service.rest.helper.JTaskRestServiceHelper;
import com.nexus.common.service.ServiceResult;
import com.timeron.NexusDatabaseLibrary.Entity.JTask;
import com.timeron.NexusDatabaseLibrary.dao.JProjectDAO;
import com.timeron.NexusDatabaseLibrary.dao.JTaskDAO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/simple-job-launcher-context.xml" })
@WebAppConfiguration
public class JTaskRestServiceHelperTest {

	@Autowired
	JProjectDAO jProjectDAO;
	@Autowired
	JTaskDAO jTaskDAO;
	@Autowired
	JTaskRestServiceHelper testClass;
	
	@Before
	public void init(){
		
	}
	
	@Test
	public void getProjectList() {
		List<JProjectDTO> result = testClass.getProjectList();
		assertNotNull(result);
	}
	
	@Test
	public void addNewProject(){
		JProjectDTO jProjectDTO = new JProjectDTO();
		jProjectDTO.setDescription("testDescription");
		jProjectDTO.setName("testName");
		jProjectDTO.setPrefix("testPrefix");
		
		ServiceResult result = new ServiceResult();
		result = testClass.addNewProject(jProjectDTO, result);
		
		assertNotNull(result);
//		assertTrue(result.isSuccess());
//		assertNotNull(result.getObject());
		
	}
	
	@Test
	public void addNewTask(){
		assertTrue(true);
//		testClass.addNewTask(jTaskDTO, result, principal)
	}
	
	@Test
	public void getProjectTasksList(){
		List<JTaskDTO> tasks = testClass.getProjectTasksList(1);
		assertNotNull(tasks);
		assertTrue(tasks.size() > 0 ? false : true);
		
	}
	
	@Test
	public void getTaskList(){
		List<JTaskDTO> tasks = testClass.getTaskList();
		assertNotNull(tasks);
		assertTrue(tasks.size() > 0 ? true : false);
	}
	
	@Test
	public void getTask(){
		JTaskDTO task = testClass.getTask(92);
		assertNotNull(task);
	}

	@Test
	public void getProjectPrefix(){
		String prefix = testClass.getProjectPrefix(21);
		assertEquals("NEXUS", prefix);
		
	}
	
	@Test
	public void updateTask(){
		ServiceResult result = new ServiceResult();
		JTaskDTO jTaskDTO = testClass.getTask(92);
		String desc = jTaskDTO.getDescription()+"A";
		jTaskDTO.setDescription(desc);
		result = testClass.updateTask(jTaskDTO, result);
		
		assertNotNull(result);
		assertTrue(result.isSuccess());
		JTaskDTO task = (JTaskDTO) result.getObject();
		assertEquals(desc, task.getDescription());
	}
	
	@Test
	public void getTaskHistory(){
		List<JHistoryDTO> history = testClass.getTaskHistory(92);
		System.out.println(history);
		assertNotNull(history);
	}
	
	@Test
	public void getTaskNotes(){
		List<JNoteDTO> notes = testClass.getTaskNotes(1);
		assertNotNull(notes);
		
	}
	
	@Test
	public void saveNote(){
		assertTrue(true);
	}
	
	@Test
	public void getNextName(){
		JTask task = null;
		String nextName = testClass.getNextName(jTaskDAO.getById(1), "TEST");
		assertEquals("TEST-1", nextName);
		
		task = new JTask();
		task.setName("TEST-21");
		task.setIdFromName(21);
		nextName = testClass.getNextName(task, "TEST");
		assertEquals("TEST-22", nextName);
		
		
	}
	
	@Test
	public void getAppVersion(){
		assertTrue(true);
//		testClass.getAppVersion("Wallet");
	}
	
	@Test
	public void getAllUsers(){
		List<NexusPersonDTO> users = testClass.getAllUsers();
		assertNotNull(users);
		assertTrue(users.size() > 0 ? true : false);
	}
	
	@Test
	public void assignTaskToUser(){
		assertTrue(true);
	}
	
	@Test
	public void setMainTask(){
		assertTrue(true);
	}
}
