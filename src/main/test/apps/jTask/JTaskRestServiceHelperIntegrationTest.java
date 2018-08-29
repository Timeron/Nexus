package apps.jTask;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.timeron.NexusDatabaseLibrary.Entity.JNote;
import com.timeron.NexusDatabaseLibrary.Entity.JRelease;
import com.timeron.NexusDatabaseLibrary.Entity.JTask;
import com.timeron.NexusDatabaseLibrary.dao.JHistoryDAO;
import com.timeron.NexusDatabaseLibrary.dao.JNoteDAO;
import com.timeron.NexusDatabaseLibrary.dao.JProjectDAO;
import com.timeron.NexusDatabaseLibrary.dao.JTaskDAO;
import com.timeron.NexusDatabaseLibrary.dao.NexusVersionDAO;
import com.timeron.nexus.apps.jTask.constant.ResultMessagesJTask;
import com.timeron.nexus.apps.jTask.dto.bean.AssignUserTaskDTO;
import com.timeron.nexus.apps.jTask.dto.bean.JHistoryDTO;
import com.timeron.nexus.apps.jTask.dto.bean.JNoteDTO;
import com.timeron.nexus.apps.jTask.dto.bean.JProjectDTO;
import com.timeron.nexus.apps.jTask.dto.bean.JReleaseDTO;
import com.timeron.nexus.apps.jTask.dto.bean.JTaskDTO;
import com.timeron.nexus.apps.jTask.dto.bean.MainTaskDTO;
import com.timeron.nexus.apps.jTask.dto.bean.NexusVersionDTO;
import com.timeron.nexus.apps.jTask.rest.helper.JTaskRestServiceHelper;
import com.timeron.nexus.apps.jTask.service.JProjectService;
import com.timeron.nexus.common.dto.NexusPersonDTO;
import com.timeron.nexus.common.service.ResultMessages;
import com.timeron.nexus.common.service.ServiceResult;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/simple-job-launcher-context.xml" })
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
		DbUnitTestExecutionListener.class })
@DatabaseSetup({ "/dbMock/JTask.xml", "/dbMock/Nexus.xml" })
public class JTaskRestServiceHelperIntegrationTest {

	@Autowired
	JProjectDAO jProjectDAO;
	@Autowired
	JTaskDAO jTaskDAO;
	@Autowired
	JNoteDAO jNoteDAO;
	@Autowired
	JHistoryDAO jHistoryDAO;
	@Autowired
	NexusVersionDAO nexusVersionDAO;
	@Autowired
	JTaskRestServiceHelper testClass;
	@Autowired
	JProjectService jProjectService;

	private Principal principal = new Principal() {

		@Override
		public String getName() {
			return "timeron";
		}
	};
	
	private Principal dummyPrincipal = new Principal() {

		@Override
		public String getName() {
			return "dummy";
		}
	};

	@Test
	public void getProjectList() {
		List<JProjectDTO> result = testClass.getProjectList(principal);
		assertNotNull(result);
		assertEquals(2, result.size());
	}
	
	@Test
	public void getProjectList_withoutUserInSession() {
		List<JProjectDTO> result = testClass.getProjectList(null);
		assertNotNull(result);
		assertEquals(0, result.size());
	}
	
	@Test
	public void getProjectList_forNotExistingUser() {
		List<JProjectDTO> result = testClass.getProjectList(dummyPrincipal);
		assertNotNull(result);
		assertEquals(0, result.size());
	}

	@Test
	public void addNewProject() {
		JProjectDTO jProjectDTO = new JProjectDTO();
		jProjectDTO.setDescription("testDescription");
		jProjectDTO.setName("testName");
		jProjectDTO.setPrefix("testPrefix");

		ServiceResult result = new ServiceResult();

		assertEquals(2, testClass.getProjectList(principal).size());

		result = testClass.addNewProject(jProjectDTO, result, principal);

		assertNotNull(result);
		assertTrue(result.isSuccess());
		assertNotNull(result.getObject());
		assertEquals(3, testClass.getProjectList(principal).size());
		assertEquals("testDescription", testClass.getProjectList(principal)
				.get(2).getDescription());

		jProjectDTO = new JProjectDTO();
		jProjectDTO.setDescription("testDescription");
		jProjectDTO.setName("testName");
		jProjectDTO.setPrefix("testPrefix");
	}
	
	@Test
	public void addNewProject_withoutUserInSession() {
		JProjectDTO jProjectDTO = new JProjectDTO();
		jProjectDTO.setDescription("testDescription");
		jProjectDTO.setName("testName");
		jProjectDTO.setPrefix("testPrefix");

		ServiceResult result = new ServiceResult();

		assertEquals(2, testClass.getProjectList(principal).size());

		result = testClass.addNewProject(jProjectDTO, result, null);

		assertNotNull(result);
		assertFalse(result.isSuccess());
		assertEquals(1, result.getMessages().size());
		assertEquals(ResultMessages.PERSON_NOT_DETECTED, result.getMessages().get(0));
		assertEquals(2, testClass.getProjectList(principal).size());
		
	}
	
	@Test
	public void addNewProject_forNotExistingUser() {
		JProjectDTO jProjectDTO = new JProjectDTO();
		jProjectDTO.setDescription("testDescription");
		jProjectDTO.setName("testName");
		jProjectDTO.setPrefix("testPrefix");

		ServiceResult result = new ServiceResult();

		assertEquals(2, testClass.getProjectList(principal).size());

		result = testClass.addNewProject(jProjectDTO, result, dummyPrincipal);

		assertNotNull(result);
		assertFalse(result.isSuccess());
		assertEquals(1, result.getMessages().size());
		assertEquals(ResultMessages.PERSON_NOT_EXIST, result.getMessages().get(0));
		assertEquals(2, testClass.getProjectList(principal).size());
		
	}
	
	@Test
	public void addNewTask() {
		ServiceResult result = new ServiceResult();
		JTaskDTO jTaskDto = createTaskDto();
		
		assertEquals(3, testClass.getTaskList().size());
		
		result = testClass.addNewTask(jTaskDto, result, principal);
		
		assertTrue(result.isSuccess());
		assertEquals(0, result.getMessages().size());
		assertEquals(4, testClass.getTaskList().size());
		//pobieraj zawsze ostatnio dodany task
		JTaskDTO jTaskResult = testClass.getTaskList().get(3);
		assertEquals("description", jTaskResult.getDescription());
		assertEquals("PREF2-2", jTaskResult.getName());
		assertNotNull(jTaskResult.getEndDateLong());
		assertEquals(4, jTaskResult.getId());
		assertEquals("PREF-1", jTaskResult.getMainTaskName());
		assertEquals("to do", jTaskResult.getStatusDescription());
		assertNull(jTaskResult.getUpdateMessage());
		assertEquals(100000, jTaskResult.getWorkExpected());
		assertNotNull(jTaskResult.getCreated());
		assertNotNull(jTaskResult.getEndDate());
		assertEquals(new Integer(1), jTaskResult.getMainTaskId());
		assertEquals(new Integer(5), jTaskResult.getPriority());
		assertEquals(new Integer(2), jTaskResult.getProjectId());
		assertEquals(new Integer(2), jTaskResult.getStatus());
		assertEquals(new Integer(2), jTaskResult.getTaskTypeId());
		assertNotNull(jTaskResult.getUpdated());
		assertNull(jTaskResult.getUpdateMessageStatus());
		assertEquals("timeron", jTaskResult.getUser().getNick());
	}
	
	@Test
	public void addNewTask_withoutUserInSession() {
		ServiceResult result = new ServiceResult();
		JTaskDTO jTaskDto = createTaskDto();
		
		assertEquals(3, testClass.getTaskList().size());
		
		result = testClass.addNewTask(jTaskDto, result, null);
		
		assertFalse(result.isSuccess());
		assertEquals(1, result.getMessages().size());
		assertEquals(3, testClass.getTaskList().size());
		assertEquals(ResultMessages.PERSON_NOT_DETECTED, result.getMessages().get(0));
	}
	
	@Test
	public void addNewTask_forNotExistingUser() {
		ServiceResult result = new ServiceResult();
		JTaskDTO jTaskDto = createTaskDto();
		
		assertEquals(3, testClass.getTaskList().size());
		
		result = testClass.addNewTask(jTaskDto, result, dummyPrincipal);
		
		assertFalse(result.isSuccess());
		assertEquals(1, result.getMessages().size());
		assertEquals(3, testClass.getTaskList().size());
		assertEquals(ResultMessages.PERSON_NOT_EXIST, result.getMessages().get(0));
	}

	private JTaskDTO createTaskDto() {
		JTaskDTO jTask = new JTaskDTO();
		jTask.setCreated(new Date());
		jTask.setDescription("description");
		jTask.setEndDate(null);
		jTask.setEndDateLong(new Date().getTime() + 1000000);
		jTask.setName("name");
		jTask.setMainTaskId(1);
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

	@Test
	public void getProjectTasksList() {
		List<JTaskDTO> tasks = testClass.getProjectTasksList(1);
		
		assertNotNull(tasks);
		assertTrue(tasks.size() > 0 ? true : false);
		assertEquals(2, tasks.size());
		assertEquals("testowy task z wszystkimi danymi", tasks.get(0)
				.getSummary());
	}
	
	@Test
	public void getProjectTasksList_dummyProject() {
		List<JTaskDTO> tasks = testClass.getProjectTasksList(999);
		assertNotNull(tasks);
		assertFalse(tasks.size() > 0 ? true : false);
	}

	@Test
	public void getTaskList() {
		List<JTaskDTO> tasks = testClass.getTaskList();
		assertNotNull(tasks);
		assertTrue(tasks.size() > 0 ? true : false);
		assertEquals(3, tasks.size());
	}

	@Test
	public void getTask() {
		JTaskDTO task = testClass.getTask(1);
		assertNotNull(task);
		assertEquals("testowy task z wszystkimi danymi", task.getSummary());
	}
	
	@Test
	public void getTask_dummyTask() {
		JTaskDTO task = testClass.getTask(999);
		assertNull(task);
	}
	
	@Test
	public void getProjectPrefix() {
		String prefix = testClass.getProjectPrefix(1);
		assertEquals("PREF1", prefix);
	}

	@Test
	public void getProjectPrefix_dummyProject() {
		String prefix = testClass.getProjectPrefix(999);
		assertNull(prefix);
	}
	
	@Test
	public void updateTask() {
		ServiceResult result = new ServiceResult();
		JTaskDTO jTaskDTO = testClass.getTask(1);
		String desc = "Nowy desc";
		jTaskDTO.setDescription(desc);
		result = testClass.updateTask(jTaskDTO, result);

		assertNotNull(result);
		assertTrue(result.isSuccess());
		JTaskDTO task = (JTaskDTO) result.getObject();
		assertEquals(desc, task.getDescription());
		assertFalse(task.getUpdated().toString().equals(jTaskDTO.getUpdated().toString()));
	}
	
	@Test
	public void updateTask_dummyTask() {
		ServiceResult result = new ServiceResult();
		JTaskDTO jTaskDTO = testClass.getTask(1);
		String desc = "Nowy desc";
		jTaskDTO.setDescription(desc);
		jTaskDTO.setId(999);
		result = testClass.updateTask(jTaskDTO, result);

		assertNotNull(result);
		assertFalse(result.isSuccess());
		assertEquals(1, result.getMessages().size());
		assertEquals(ResultMessagesJTask.TASK_CANNOT_BE_FOUND_TASK, result.getMessages().get(0));
	}
	
	@Test
	public void getTaskHistory() {
		List<JHistoryDTO> history = testClass.getTaskHistory(1);
		assertNotNull(history);
		assertEquals("in review", history.get(0).getStatus());
		assertEquals(2, history.size());
	}
	
	@Test
	public void getTaskHistory_dummyTask() {
		List<JHistoryDTO> history = testClass.getTaskHistory(999);
		assertNotNull(history);
		assertEquals(0, history.size());
	}
	
	@Test
	public void getTaskNotes() {
		List<JNoteDTO> notes = testClass.getTaskNotes(1);
		assertNotNull(notes);
		assertEquals("Testowa notka", notes.get(0).getContent());
	}
	
	@Test
	public void getTaskNotes_dummyTask() {
		List<JNoteDTO> notes = testClass.getTaskNotes(999);
		assertNotNull(notes);
		assertEquals(0, notes.size());
	}
	
	@Test
	public void saveNote() {
		ServiceResult result = new ServiceResult();
		JNoteDTO note = new JNoteDTO();
		note.setContent("content");
		note.setCreated(new Date());
		note.setName("name");
		note.setTaskId(1);
		
		assertEquals(1, jNoteDAO.getAll().size());
		assertEquals(2, jHistoryDAO.getAll().size());
		
		result = testClass.saveNote(note, "timeron", result);

		int id = jNoteDAO.getLastId();
		JNote newNote = jNoteDAO.getById(id);

		assertTrue(result.isSuccess());
		assertEquals("content", newNote.getContent());
		assertEquals("name", newNote.getName());
		assertNotNull(note.getCreated());
		assertEquals(2, jNoteDAO.getAll().size());
		assertEquals(3, jHistoryDAO.getAll().size());
		assertEquals("content", newNote.getContent());
		assertEquals(new Integer(1), newNote.getTask().getId());
		assertEquals("timeron", newNote.getUser().getNick());
	}
	
	@Test
	public void saveNote_dummyTask() {
		ServiceResult result = new ServiceResult();
		JNoteDTO note = new JNoteDTO();
		note.setContent("content");
		note.setCreated(new Date());
		note.setName("name");
		note.setTaskId(999);
		
		assertEquals(1, jNoteDAO.getAll().size());
		assertEquals(2, jHistoryDAO.getAll().size());
		
		result = testClass.saveNote(note, "timeron", result);

		assertFalse(result.isSuccess());
		assertEquals(2, jHistoryDAO.getAll().size());
		assertEquals(1, jNoteDAO.getAll().size());
	}
	
	@Test
	public void getNextName() {
		int nextName = testClass.getNextName(jTaskDAO.getById(1), "PREF1");
		assertEquals(2, nextName);
	}
	
	@Test
	public void getNextName_dummyTask() {
		String pref = "PREF1";
		int nextName = testClass.getNextName(jTaskDAO.getById(999), pref);
		assertEquals(1, nextName);
	}
	
	@Test
	public void getNextName_NoTask() {
		int nextName = testClass.getNextName(null, "TEST");
		assertEquals(1, nextName);
	}

	@Test
	public void getAppVersion() {
		NexusVersionDTO version = testClass.getAppVersion("Nexus");
		assertEquals("1.0.0", version.getVersion());
	}
	
	@Test
	public void getAppVersion_dummyApp() {
		NexusVersionDTO version = testClass.getAppVersion("Dummy");
		assertNull(version);
	}
	
	@Test
	public void getAllUsers() {
		List<NexusPersonDTO> users = testClass.getAllUsers();
		assertNotNull(users);
		assertTrue(users.size() > 0 ? true : false);
		assertEquals("timeron", users.get(0).getNick());
	}
	
	@Test
	public void assignTaskToUser() {
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
	public void assignTaskToUser_noUser() {
		ServiceResult result = new ServiceResult();
		AssignUserTaskDTO assignUserTaskDTO = new AssignUserTaskDTO();
		assignUserTaskDTO.setTaskId(1);
		
		result = testClass.assignTaskToUser(assignUserTaskDTO, result);
		JTask task = (JTask) result.getObject();
		
		assertFalse(result.isSuccess());
		assertNull(task);
	}

	@Test
	public void setMainTask() {
		ServiceResult result = new ServiceResult();
		MainTaskDTO jMainTask = new MainTaskDTO();
		jMainTask.setMainTaskId(1);
		jMainTask.setTaskId(2);
		
		assertNull(jTaskDAO.getById(2).getMainTask());
		assertEquals(1, jProjectService.getProjectByTaskId(1).getId());
		assertEquals(1, jProjectService.getProjectByTaskId(2).getId());
		
		result = testClass.setMainTask(jMainTask, result);
		
		assertTrue(result.isSuccess());
		assertNotNull(jTaskDAO.getById(2).getMainTask());
		assertEquals(new Integer(1), jTaskDAO.getById(2).getMainTask().getId());
	}
	
	@Test
	public void setMainTask_differentProjects() {
		ServiceResult result = new ServiceResult();
		MainTaskDTO jMainTask = new MainTaskDTO();
		jMainTask.setMainTaskId(1);
		jMainTask.setTaskId(3);
		
		assertNull(jTaskDAO.getById(2).getMainTask());
		assertEquals(1, jProjectService.getProjectByTaskId(1).getId());
		assertEquals(2, jProjectService.getProjectByTaskId(3).getId());
		
		result = testClass.setMainTask(jMainTask, result);
		
		assertNull(jTaskDAO.getById(2).getMainTask());
		assertFalse(result.isSuccess());
		assertEquals(1, result.getMessages().size());
		assertEquals(ResultMessagesJTask.PROJECTS_ARE_DIFFERENT, result.getMessages().get(0));
	}
	
	@Test
	public void setMainTask_noMainTask() {
		ServiceResult result = new ServiceResult();
		MainTaskDTO jMainTask = new MainTaskDTO();
		jMainTask.setTaskId(2);
		
		assertNull(jTaskDAO.getById(2).getMainTask());
		assertEquals(1, jProjectService.getProjectByTaskId(1).getId());
		assertEquals(1, jProjectService.getProjectByTaskId(2).getId());
		
		result = testClass.setMainTask(jMainTask, result);
		
		assertNull(jTaskDAO.getById(2).getMainTask());
		assertFalse(result.isSuccess());
		assertEquals(1, result.getMessages().size());
		assertEquals(ResultMessagesJTask.TASK_CANNOT_BE_FOUND_TASK, result.getMessages().get(0));
	}
	
	@Test
	public void setMainTask_noTask() {
		ServiceResult result = new ServiceResult();
		MainTaskDTO jMainTask = new MainTaskDTO();
		jMainTask.setMainTaskId(1);
		
		result = testClass.setMainTask(jMainTask, result);
		
		assertNull(jTaskDAO.getById(2).getMainTask());
		assertFalse(result.isSuccess());
		assertEquals(1, result.getMessages().size());
		assertEquals(ResultMessagesJTask.TASK_CANNOT_BE_FOUND_TASK, result.getMessages().get(0));
	}
	
	@Test
	public void getRelease(){
		ServiceResult result = new ServiceResult();
		result = testClass.getAllReleases(result, 1);
		
		JRelease r1 = (JRelease) testClass.getRelease(new ServiceResult(), 1).getObject();
		
		assertEquals(new Integer(1), r1.getId());
		assertEquals("some comment", r1.getComment());
		assertEquals(1, r1.getProject().getId());
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void getAllReleases(){
		ServiceResult result = new ServiceResult();
		result = testClass.getAllReleases(result, 1);
		List<JRelease> releases = (List<JRelease>) result.getObject();
		
		JRelease r1 = (JRelease) testClass.getRelease(new ServiceResult(), 1).getObject();
		JRelease r2 = (JRelease) testClass.getRelease(new ServiceResult(), 2).getObject();
		
		assertTrue(result.isSuccess());
		assertEquals(2, releases.size());
		assertTrue(releases.contains(r1));
		assertTrue(releases.contains(r2));
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void getAllReleases_notExistingProject(){
		ServiceResult result = new ServiceResult();
		result = testClass.getAllReleases(result, 10);
		List<JRelease> releases = (List<JRelease>) result.getObject();
		
		JRelease r1 = (JRelease) testClass.getRelease(new ServiceResult(), 1).getObject();
		JRelease r2 = (JRelease) testClass.getRelease(new ServiceResult(), 2).getObject();
		
		assertFalse(result.isSuccess());
		assertEquals(ResultMessagesJTask.PROJECT_NOT_FOUND, result.getFirstError());
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void saveRelease(){
		ServiceResult saveResult = new ServiceResult();
		saveResult = testClass.saveRelease(new JReleaseDTO(null, "1.2", "new comment", 1), saveResult);
		List<JRelease> releases = (List<JRelease>) testClass.getAllReleases(new ServiceResult(), 1).getObject();
		
		JRelease r1 = (JRelease) testClass.getRelease(new ServiceResult(), 3).getObject();
		
		assertEquals(3, releases.size());
		assertNotNull(r1);
		assertEquals("1.2", r1.getVersion());
		assertTrue(releases.contains(r1));
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void updateRelease(){
		ServiceResult saveResult = new ServiceResult();
		saveResult = testClass.updateRelease(new JReleaseDTO(1, "1.2", "new comment", 2), saveResult);
		List<JRelease> releases1 = (List<JRelease>) testClass.getAllReleases(new ServiceResult(), 1).getObject();
		List<JRelease> releases2 = (List<JRelease>) testClass.getAllReleases(new ServiceResult(), 2).getObject();
		
		JRelease r1 = (JRelease) testClass.getRelease(new ServiceResult(), 1).getObject();
		
		assertEquals(1, releases1.size());
		assertNotNull(r1);
		assertEquals("1.2", r1.getVersion());
		assertEquals("new comment", r1.getComment());
		assertEquals(2, r1.getProject().getId());
		assertFalse(releases1.contains(r1));
		assertTrue(releases2.contains(r1));
	}

}
