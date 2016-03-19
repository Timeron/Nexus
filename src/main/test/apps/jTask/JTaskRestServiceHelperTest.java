package apps.jTask;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.List;

import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

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
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, DbUnitTestExecutionListener.class })
@WebAppConfiguration
public class JTaskRestServiceHelperTest {

	@Autowired
	JProjectDAO jProjectDAO;
	@Autowired
	JTaskDAO jTaskDAO;
	@Autowired
	JTaskRestServiceHelper testClass;
	
//	@SuppressWarnings("unused")
//	@BeforeClass
//	public void importDataSet() throws Exception {
//		IDataSet dataSet = readDataSet();
////		cleanlyInsertDataset(dataSet);
//	}
//	
//	private IDataSet readDataSet() throws Exception {
//		return new FlatXmlDataSetBuilder().build(new File("/dataset.xml"));
//	}
//	
//	private void cleanlyInsertDataset(IDataSet dataSet) throws Exception {
//		IDatabaseTester databaseTester = new JdbcDatabaseTester(
//			"org.h2.Driver", "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1", "sa", "");
//		databaseTester.setSetUpOperation(DatabaseOperation.CLEAN_INSERT);
//		databaseTester.setDataSet(dataSet);
//		databaseTester.onSetup();
//	}
	
//	@Test
//	public void testRemove() throws Exception {
//		jProjectDAO
//	}
	
	@Test
	@DatabaseSetup("/dbMock/JProject.xml")
	public void getProjectList() {
		List<JProjectDTO> result = testClass.getProjectList();
		assertNotNull(result);
		assertEquals(4, result.size());
	}
	
	@Test
	@DatabaseSetup("/dbMock/JProject.xml")
	public void addNewProject(){
		JProjectDTO jProjectDTO = new JProjectDTO();
		jProjectDTO.setDescription("testDescription");
		jProjectDTO.setName("testName");
		jProjectDTO.setPrefix("testPrefix");
		
		ServiceResult result = new ServiceResult();
		result = testClass.addNewProject(jProjectDTO, result);
		
		assertNotNull(result);
		assertTrue(result.isSuccess());
		assertNotNull(result.getObject());
		assertEquals(jProjectDTO, testClass.getProjectList().get(testClass.getProjectList().size()-1));
		
		jProjectDTO = new JProjectDTO();
		jProjectDTO.setDescription("testDescription");
		jProjectDTO.setName("testName");
		jProjectDTO.setPrefix("testPrefix");
	}
	
	@Test
	public void addNewTask(){
		//TODO
		assertTrue(true);
//		testClass.addNewTask(jTaskDTO, result, principal)
	}
	
	@Test
	public void getProjectTasksList(){
		//TODO
		List<JTaskDTO> tasks = testClass.getProjectTasksList(1);
		assertNotNull(tasks);
		assertTrue(tasks.size() > 0 ? false : true);
		
	}
	
	@Test
	public void getTaskList(){
		//TODO
		List<JTaskDTO> tasks = testClass.getTaskList();
		assertNotNull(tasks);
		assertTrue(tasks.size() > 0 ? true : false);
	}
	
	@Test
	public void getTask(){
		//TODO
		JTaskDTO task = testClass.getTask(92);
		assertNotNull(task);
	}

	@Test
	public void getProjectPrefix(){
		//TODO
		String prefix = testClass.getProjectPrefix(21);
		assertEquals("NEXUS", prefix);
		
	}
	
	@Test
	public void updateTask(){
		//TODO
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
		//TODO
		List<JHistoryDTO> history = testClass.getTaskHistory(92);
		System.out.println(history);
		assertNotNull(history);
	}
	
	@Test
	public void getTaskNotes(){
		//TODO
		List<JNoteDTO> notes = testClass.getTaskNotes(1);
		assertNotNull(notes);
		
	}
	
	@Test
	public void saveNote(){
		//TODO
		assertTrue(true);
	}
	
	@Test
	public void getNextName(){
		//TODO
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
		//TODO
		assertTrue(true);
//		testClass.getAppVersion("Wallet");
	}
	
	@Test
	public void getAllUsers(){
		//TODO
		List<NexusPersonDTO> users = testClass.getAllUsers();
		assertNotNull(users);
		assertTrue(users.size() > 0 ? true : false);
	}
	
	@Test
	public void assignTaskToUser(){
		//TODO
		assertTrue(true);
	}
	
	@Test
	public void setMainTask(){
		//TODO
		assertTrue(true);
	}
}
