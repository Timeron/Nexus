package apps.jTask;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.security.Principal;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.web.WebAppConfiguration;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.nexus.apps.jTask.dto.bean.AssignUserTaskDTO;
import com.nexus.apps.jTask.dto.bean.JHistoryDTO;
import com.nexus.apps.jTask.dto.bean.JNoteDTO;
import com.nexus.apps.jTask.dto.bean.JProjectDTO;
import com.nexus.apps.jTask.dto.bean.JTaskDTO;
import com.nexus.apps.jTask.dto.bean.NexusPersonDTO;
import com.nexus.apps.jTask.dto.bean.NexusVersionDTO;
import com.nexus.apps.jTask.service.rest.helper.JTaskRestServiceHelper;
import com.nexus.common.service.ResultMessages;
import com.nexus.common.service.ServiceResult;
import com.timeron.NexusDatabaseLibrary.Entity.JNote;
import com.timeron.NexusDatabaseLibrary.Entity.JTask;
import com.timeron.NexusDatabaseLibrary.Entity.NexusVersion;
import com.timeron.NexusDatabaseLibrary.dao.JNoteDAO;
import com.timeron.NexusDatabaseLibrary.dao.JProjectDAO;
import com.timeron.NexusDatabaseLibrary.dao.JTaskDAO;
import com.timeron.NexusDatabaseLibrary.dao.NexusVersionDAO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/simple-job-launcher-context.xml" })
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
		DbUnitTestExecutionListener.class })
@WebAppConfiguration
@DatabaseSetup({"/dbMock/JTask.xml", "/dbMock/Nexus.xml"})
public class JTaskRestServiceHelperTest {

	@Autowired
	JProjectDAO jProjectDAO;
	@Autowired
	JTaskDAO jTaskDAO;
	@Autowired
	JNoteDAO jNoteDAO;
	@Autowired
	NexusVersionDAO nexusVersionDAO;
	@Autowired
	JTaskRestServiceHelper testClass;

	@Before
	public void load(){
		
	}
	
	@Test
	public void getProjectList() {
		System.out.println("-> TEST: getProjectList");
		List<JProjectDTO> result = testClass.getProjectList();
		assertNotNull(result);
		assertEquals(4, result.size());
	}

	@Test
	public void addNewProject() {
		System.out.println("-> TEST: addNewProject");
		JProjectDTO jProjectDTO = new JProjectDTO();
		jProjectDTO.setDescription("testDescription");
		jProjectDTO.setName("testName");
		jProjectDTO.setPrefix("testPrefix");

		ServiceResult result = new ServiceResult();
		result = testClass.addNewProject(jProjectDTO, result);

		assertNotNull(result);
		assertTrue(result.isSuccess());
		assertNotNull(result.getObject());
		assertEquals("testDescription",
				testClass.getProjectList().get(4).getDescription());

		jProjectDTO = new JProjectDTO();
		jProjectDTO.setDescription("testDescription");
		jProjectDTO.setName("testName");
		jProjectDTO.setPrefix("testPrefix");
	}

	@Test
	
	public void addNewTask() {
		System.out.println("-> TEST: addNewTask");
		Principal principal = Mockito.mock(Principal.class);
		ServiceResult result = new ServiceResult();

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

		//when
		when(principal.getName()).thenReturn("timeron");
		//then
		result = testClass.addNewTask(jTask, result, principal);
		assertTrue(result.isSuccess());
		
		//when
		result = new ServiceResult();
		when(principal.getName()).thenReturn("notValid_timeron");
		//then
		result = testClass.addNewTask(jTask, result, principal);
		assertTrue(!result.isSuccess());
		assertEquals(ResultMessages.PERSON_NOT_EXIST, result.getMessages().get(0));

		//when
		result = new ServiceResult();
		when(principal.getName()).thenReturn(null);
		//then
		result = testClass.addNewTask(jTask, result, principal);
		assertTrue(!result.isSuccess());
		assertEquals(ResultMessages.PERSON_NOT_DETECTED, result.getMessages().get(0));
	}

	@Test
	public void getProjectTasksList() {
		System.out.println("-> TEST: getProjectTasksList");
		List<JTaskDTO> tasks = testClass.getProjectTasksList(1);
		assertNotNull(tasks);
		assertTrue(tasks.size() > 0 ? true : false);
		assertEquals("testowy task z wszystkimi danymi", tasks.get(0).getSummary());
	}

	@Test
	public void getTaskList() {
		System.out.println("-> TEST: getTaskList");
		List<JTaskDTO> tasks = testClass.getTaskList();
		assertNotNull(tasks);
		assertTrue(tasks.size() > 0 ? true : false);
	}

	@Test
	public void getTask() {
		System.out.println("-> TEST: getTask");
		JTaskDTO task = testClass.getTask(1);
		assertNotNull(task);
		assertEquals("testowy task z wszystkimi danymi", task.getSummary());
		assertTrue(true);
	}

	@Test
	public void getProjectPrefix() {
		System.out.println("-> TEST: getProjectPrefix");
		String prefix = testClass.getProjectPrefix(1);
		assertEquals("PREF1", prefix);
	}

	@Test
	public void updateTask() {
		System.out.println("-> TEST: updateTask");
		ServiceResult result = new ServiceResult();
		JTaskDTO jTaskDTO = testClass.getTask(1);
		String desc = "Nowy desc";
		jTaskDTO.setDescription(desc);
		result = testClass.updateTask(jTaskDTO, result);

		assertNotNull(result);
		assertTrue(result.isSuccess());
		JTaskDTO task = (JTaskDTO) result.getObject();
		assertEquals(desc, task.getDescription());
	}

	@Test
	public void getTaskHistory() {
		System.out.println("-> TEST: getTaskHistory");
		List<JHistoryDTO> history = testClass.getTaskHistory(1);
		assertNotNull(history);
		assertEquals("in review", history.get(0).getStatus());
	}

	@Test
	public void getTaskNotes() {
		System.out.println("-> TEST: getTaskNotes");
		List<JNoteDTO> notes = testClass.getTaskNotes(1);
		assertNotNull(notes);
		assertEquals("Testowa notka", notes.get(0).getContent());
	}

	@Test
	public void saveNote() {
		System.out.println("-> TEST: saveNote");
		JNote note = new JNote();
		note.setContent("content");
		note.setCreated(new Date());
		note.setName("name");
		note.setTask(jTaskDAO.getById(1));
		boolean result = jNoteDAO.save(note);
		
		int id = jNoteDAO.getLastId();
		JNote newNote = jNoteDAO.getById(id);
		
		assertTrue(result);
		assertEquals("content", newNote.getContent());
		
	}

	@Test
	public void getNextName() {
		System.out.println("-> TEST: getNextName");
		
		String nextName = testClass.getNextName(jTaskDAO.getById(1), "PREF1");
		assertEquals("PREF1-2", nextName);
		
		nextName = testClass.getNextName(null, "TEST");
		assertEquals("TEST-1", nextName);
	}

	@Test
	public void getAppVersion() {
		System.out.println("-> TEST: getAppVersion");
		NexusVersionDTO version = testClass.getAppVersion("Nexus");
		assertEquals("1.0.0", version.getVersion());
	}

	@Test
	public void getAllUsers() {
		System.out.println("-> TEST: getAllUsers");
		List<NexusPersonDTO> users = testClass.getAllUsers();
		assertNotNull(users);
		assertTrue(users.size() > 0 ? true : false);
		assertEquals("timeron", users.get(0).getNick());
	}

	@Test
	public void assignTaskToUser() {
		System.out.println("-> TEST: assignTaskToUser");
		ServiceResult result = new ServiceResult();
		AssignUserTaskDTO assignUserTaskDTO = new AssignUserTaskDTO();
		assignUserTaskDTO.setTaskId(1);
		assignUserTaskDTO.setUserId(1);
		result = testClass.assignTaskToUser(assignUserTaskDTO, result);
		JTask task = (JTask) result.getObject();
		assertTrue(result.isSuccess());
		assertNotNull(task);
		assertEquals((Integer) 1, task.getUser().getId());
	}

	@Test
	public void setMainTask() {
		System.out.println("-> TEST: setMainTask");
		// TODO
		assertTrue(true);
	}
}
