package apps.wallet.restService.helper;

import static org.junit.Assert.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.timeron.NexusDatabaseLibrary.Entity.WalletRecord;
import com.timeron.NexusDatabaseLibrary.Entity.WalletType;
import com.timeron.NexusDatabaseLibrary.dao.WalletAccountDAO;
import com.timeron.NexusDatabaseLibrary.dao.WalletRecordDAO;
import com.timeron.NexusDatabaseLibrary.dao.WalletTypeDAO;
import com.timeron.nexus.apps.wallet.constant.ResultMessagesWallet;
import com.timeron.nexus.apps.wallet.rest.helper.WalletRestServiceHelper;
import com.timeron.nexus.apps.wallet.service.WalletService;
import com.timeron.nexus.apps.wallet.service.dto.AccountDTO;
import com.timeron.nexus.apps.wallet.service.dto.AccountForDropdownDTO;
import com.timeron.nexus.apps.wallet.service.dto.HierarchyPieChartDTO;
import com.timeron.nexus.apps.wallet.service.dto.NewAccountDTO;
import com.timeron.nexus.apps.wallet.service.dto.PieChartDTO;
import com.timeron.nexus.apps.wallet.service.dto.RecordDTO;
import com.timeron.nexus.apps.wallet.service.dto.RecordTypeDTO;
import com.timeron.nexus.apps.wallet.service.dto.RecordTypeListDTO;
import com.timeron.nexus.apps.wallet.service.dto.SumForAccountByType;
import com.timeron.nexus.apps.wallet.service.dto.TypeForStatistics;
import com.timeron.nexus.apps.wallet.service.dto.TypesForStatistics;
import com.timeron.nexus.apps.wallet.service.dto.graph.GraphListOfKeyValuesAndProperties;
import com.timeron.nexus.apps.wallet.service.dto.graph.KeyValueDTO;
import com.timeron.nexus.common.service.ResultMessages;
import com.timeron.nexus.common.service.ServiceResult;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/simple-job-launcher-context.xml" })
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
		DbUnitTestExecutionListener.class })
@DatabaseSetup("/dbMock/Wallet.xml")
public class WalletRestServiceHelperTest {
	
	@Autowired
	private WalletRestServiceHelper walletRestSeriveHelper;
	
	@Autowired
	private WalletAccountDAO walletAccountDAO;
	
	@Autowired
	private WalletTypeDAO walletTypeDAO;
	
	@Autowired
	private WalletRecordDAO walletRecordDAO;
	
	@Autowired
	private WalletService walletService;
	
	private Principal principal = new Principal() {

		@Override
		public String getName() {
			return "timeron";
		}
	};
	
	@SuppressWarnings("unchecked")
	@Test
	public void transferToDataValueMap_perMonth(){
		Map<String, List<RecordDTO>> result = null;
		Method method = null;
		try {
			method = WalletRestServiceHelper.class.getDeclaredMethod("transferToDataValueMap", List.class, SimpleDateFormat.class);
			method.setAccessible(true);
		} catch (NoSuchMethodException e) {
			
			e.printStackTrace();
		} catch (SecurityException e) {
			
			e.printStackTrace();
		}
		
		List<RecordDTO> records = walletService.getRecordsByType(4, false);
		
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
			result = (Map<String, List<RecordDTO>>) method.invoke(walletRestSeriveHelper, records, format);
		} catch (IllegalAccessException e) {
			
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			
			e.printStackTrace();
		}
		
		assertFalse(result.isEmpty());
		assertEquals(2, result.size());
		assertTrue(result.containsKey("2015-10"));
		assertEquals(4, result.get("2015-10").size());
		assertEquals(new Integer(6), result.get("2015-10").get(0).getId());
		assertEquals(new Integer(9), result.get("2015-10").get(1).getId());
		assertEquals(new Integer(7), result.get("2015-10").get(2).getId());
		assertEquals(new Integer(10), result.get("2015-10").get(3).getId());
		assertTrue(result.containsKey("2015-11"));
		assertEquals(2, result.get("2015-11").size());
		assertEquals(new Integer(11), result.get("2015-11").get(0).getId());
		assertEquals(new Integer(12), result.get("2015-11").get(1).getId());
		
		
	}
	
	@Test
	public void getSumForTypesForStatisticsPerMonth(){
		List<Integer> typeIds = new ArrayList<Integer>();
		typeIds.add(4);
		typeIds.add(1);
		TypesForStatistics sumForAccountByTypes = new TypesForStatistics();
		sumForAccountByTypes.setAccount(1);
		sumForAccountByTypes.setIncome(false);
		sumForAccountByTypes.setTypes(typeIds);
		
		ServiceResult result = walletRestSeriveHelper.getSumForTypesForStatisticsPerMonth(sumForAccountByTypes, principal.getName());
		
		assertNotNull(result);
		assertTrue(result.isSuccess());
		GraphListOfKeyValuesAndProperties object = (GraphListOfKeyValuesAndProperties) result.getObject();
		assertTrue(object.getKayValues().size() >= 10);
		assertEquals(2, object.getProperty().size());
		
		assertEquals("2015-10", object.getKayValues().get(0).getKey());
		KeyValueDTO kv = new KeyValueDTO("Sodexo", "39.98");
		assertTrue(object.getKayValues().get(0).getValues().contains(kv));
		kv = new KeyValueDTO("Bank", "0");
		assertTrue(object.getKayValues().get(0).getValues().contains(kv));
		
		assertEquals("2015-11", object.getKayValues().get(1).getKey());
		kv = new KeyValueDTO("Sodexo", "28,49");
		assertTrue(object.getKayValues().get(1).getValues().contains(kv));
		kv = new KeyValueDTO("Bank", "0");
		assertTrue(object.getKayValues().get(1).getValues().contains(kv));

		assertEquals("2015-12", object.getKayValues().get(2).getKey());
		kv = new KeyValueDTO("Sodexo", "0");
		assertTrue(object.getKayValues().get(2).getValues().contains(kv));
		kv = new KeyValueDTO("Bank", "0");
		assertTrue(object.getKayValues().get(2).getValues().contains(kv));
		
	}
	
	@Test
	public void addAccount() {
		String name = "newProject";
		String desc = "project description";
		assertEquals(2, walletRestSeriveHelper.getAllUserAccounts(principal).size());
		
		NewAccountDTO accountDTO = new NewAccountDTO();
		accountDTO.setName(name);
		accountDTO.setDescription(desc);
		walletRestSeriveHelper.addAccount(accountDTO, principal);
		
		List<AccountForDropdownDTO> accounts = walletRestSeriveHelper.getAllUserAccounts(principal);
		
		assertEquals(3, accounts.size());
		assertEquals(name, accounts.get(accounts.size()-1).getName());
		assertEquals(desc, accounts.get(accounts.size()-1).getDescription());
	}
	
	@Test
	public void getAllRecordTypes() {
		List<RecordTypeDTO> types = walletRestSeriveHelper.getAllRecordTypes();
		
		assertEquals(walletTypeDAO.getAll().size(), types.size());
		assertEquals("Bank", types.get(0).getName());
		assertEquals("Sodexo", types.get(1).getName());
	}
	
	@Test
	public void addNewRecord() {
		RecordDTO recordDTO = new RecordDTO();
		recordDTO.setDate(new Date().getTime());
		recordDTO.setDescription("description");
		recordDTO.setAccountId(1);
		recordDTO.setIncome(false);
		recordDTO.setRecordTypeId(4);
		recordDTO.setTransfer(false);
		recordDTO.setUpdated(new Date().getTime());
		recordDTO.setValue(20.56F);

		ServiceResult result = walletRestSeriveHelper.addNewRecord(recordDTO);
		assertTrue(result.isSuccess());
		assertEquals(1, result.getMessages().size());
		assertTrue(result.getMessages().get(0).equals(ResultMessagesWallet.RECORD_ADDED));
		
		WalletRecord record = walletRecordDAO.getById(walletRecordDAO.getLastId()); 

		assertEquals(record.getDate().getTime(), recordDTO.getDate());
		assertEquals(record.getDescription(), recordDTO.getDescription());
		assertEquals(record.getWalletAccount().getId(), recordDTO.getAccountId());
		assertFalse(record.isIncome());
		assertEquals(record.getWalletType().getId(), recordDTO.getRecordTypeId());
		assertFalse(record.isTransfer());
		assertEquals((Float)record.getValue(), (Float)recordDTO.getValue());
		
	}
	
	@Test
	public void addNewRecord_income() {
		RecordDTO recordDTO = new RecordDTO();
		recordDTO.setDate(new Date().getTime());
		recordDTO.setDescription("description");
		recordDTO.setAccountId(1);
		recordDTO.setIncome(true);
		recordDTO.setRecordTypeId(4);
		recordDTO.setTransfer(false);
		recordDTO.setUpdated(new Date().getTime());
		recordDTO.setValue(20.56F);

		ServiceResult result = walletRestSeriveHelper.addNewRecord(recordDTO);
		assertTrue(result.isSuccess());
		assertEquals(1, result.getMessages().size());
		assertTrue(result.getMessages().get(0).equals(ResultMessagesWallet.RECORD_ADDED));
		
		WalletRecord record = walletRecordDAO.getById(walletRecordDAO.getLastId()); 

		assertEquals(record.getDate().getTime(), recordDTO.getDate());
		assertEquals(record.getDescription(), recordDTO.getDescription());
		assertEquals(record.getWalletAccount().getId(), recordDTO.getAccountId());
		assertTrue(record.isIncome());
		assertEquals(record.getWalletType().getId(), recordDTO.getRecordTypeId());
		assertFalse(record.isTransfer());
		assertEquals((Float)record.getValue(), (Float)recordDTO.getValue());
		
	}
	
	@Test
	public void addNewRecord_transfer() {
		RecordDTO recordDTO = new RecordDTO();
		recordDTO.setDate(new Date().getTime());
		recordDTO.setDescription("description");
		recordDTO.setDestynationAccountId(2);
		recordDTO.setAccountId(1);
		recordDTO.setIncome(true);
		recordDTO.setRecordTypeId(4);
		recordDTO.setSourceWalletAccountId(1);
		recordDTO.setTransfer(true);
		recordDTO.setUpdated(new Date().getTime());
		recordDTO.setValue(20.56F);

		ServiceResult result = walletRestSeriveHelper.addNewRecord(recordDTO);
		assertTrue(result.isSuccess());
		assertEquals(1, result.getMessages().size());
		assertTrue(result.getMessages().get(0).equals(ResultMessagesWallet.RECORD_ADDED));
		
		WalletRecord record = walletRecordDAO.getById(walletRecordDAO.getLastId()); 

		assertEquals(record.getDate().getTime(), recordDTO.getDate());
		assertEquals(record.getDescription(), recordDTO.getDescription());
		assertEquals(2, record.getWalletAccount().getId());
		assertTrue(record.isIncome());
		assertEquals(record.getWalletType().getId(), recordDTO.getRecordTypeId());
		assertTrue(record.isTransfer());
		assertEquals((Float)record.getValue(), (Float)recordDTO.getValue());
		assertEquals(1, record.getSourceWalletAccount().getId());
		assertEquals(2, record.getDestinationWalletAccount().getId());
		
	}
	
	@Test
	public void getAllUserAccounts() {
		List<AccountForDropdownDTO> accounts = walletRestSeriveHelper.getAllUserAccounts(principal);
		
		assertEquals(2, accounts.size());
		assertEquals("Moje konto 1", accounts.get(0).getName());
		assertEquals("Prywatne konto 1", accounts.get(0).getDescription());
		assertEquals("Moje konto 2", accounts.get(1).getName());
		assertEquals("Prywatne konto 2", accounts.get(1).getDescription());
		
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void getAllAccountsAndRecords() {
		ServiceResult result = walletRestSeriveHelper.getAllAccountsAndRecords(principal);
		assertTrue(result.isSuccess());
		List<AccountDTO> accounts = (List<AccountDTO>) result.getObject();
		assertEquals(2, accounts.size());
		assertEquals(11, accounts.get(0).getRecords().size());
		assertEquals(1, accounts.get(1).getRecords().size());
	}
	
	@Test
	public void getRecordsForAccountByDay() {
		List<KeyValueDTO> data = walletRestSeriveHelper.getRecordsForAccountByDay(1, principal);
		
		assertEquals(8, data.size());
		assertEquals("69.49", data.get(0).getValue());
		assertEquals("93.03", data.get(1).getValue());
		assertEquals("85.15", data.get(2).getValue());
		assertEquals("63.66", data.get(3).getValue());
		assertEquals("49.51", data.get(4).getValue());
		assertEquals("18.09", data.get(5).getValue());
	}
	
	@Test
	public void getSumForAccountByType() {
		SumForAccountByType dto = new SumForAccountByType();
		dto.setId(1);
		dto.setIncome(false);
		List<PieChartDTO> data = walletRestSeriveHelper.getSumForAccountByType(dto, principal);
		assertEquals(2, data.size());
		assertEquals("17.08", data.get(0).getValue());
		assertEquals("68.47", data.get(1).getValue());
	}
	
	@Test
	public void getSumForAccountByType_Income() {
		SumForAccountByType dto = new SumForAccountByType();
		dto.setId(1);
		dto.setIncome(true);
		List<PieChartDTO> data = walletRestSeriveHelper.getSumForAccountByType(dto, principal);
		assertEquals(1, data.size());
		assertEquals("93.03", data.get(0).getValue());
	}
	
	@Test
	public void getSumForAccountByParentType() {
		SumForAccountByType dto = new SumForAccountByType();
		dto.setId(1);
		dto.setIncome(false);
		List<PieChartDTO> data = walletRestSeriveHelper.getSumForAccountByParentType(dto, principal);
		assertEquals(1, data.size());
		assertEquals("85.55", data.get(0).getValue());
	}
	
	@Test
	public void getSumForTypeInTypeHierarchy() {
		SumForAccountByType dto = new SumForAccountByType();
		dto.setId(1);
		dto.setIncome(false);
		List<HierarchyPieChartDTO> data = walletRestSeriveHelper.getSumForTypeInTypeHierarchy(dto, principal);
		
		assertEquals(1, data.size());
		assertEquals("85.55", data.get(0).getValue());
		assertEquals(4, data.get(0).getChildren().size());
		assertEquals("68.47", data.get(0).getChildren().get(0).getValue());
		assertEquals("17.08", data.get(0).getChildren().get(1).getValue());
		assertEquals("0", data.get(0).getChildren().get(2).getValue());
		assertEquals("0", data.get(0).getChildren().get(3).getValue());
	}
	
	@Test
	public void addNewType() {
		RecordTypeDTO type = new RecordTypeDTO();
		type.setColor("color");
		type.setDefaultValue(true);
		type.setIcon("icon");
		type.setName("name");
		type.setParentId(1);
		type.setTimestamp(new Date());
		type.setUpdated(new Date());
		ServiceResult result = walletRestSeriveHelper.addNewType(type);
		
		assertTrue(result.isSuccess());
		assertEquals(1, result.getMessages().size());
		assertEquals(ResultMessagesWallet.RECORD_ADDED, result.getFirstMessage());
	}
	
	@Test
	public void addNewType_notExistingParent() {
		RecordTypeDTO type = new RecordTypeDTO();
		type.setColor("color");
		type.setDefaultValue(true);
		type.setIcon("icon");
		type.setName("name");
		type.setParentId(999);
		type.setTimestamp(new Date());
		type.setUpdated(new Date());
		ServiceResult result = walletRestSeriveHelper.addNewType(type);
		
		assertFalse(result.isSuccess());
		assertEquals(1, result.getErrors().size());
		assertEquals(ResultMessagesWallet.TYPE_NOT_ADDED_PARENT_DOESNOT_EXIST, result.getFirstError());
	}
	
	@Test
	public void getTypesValidForParent() {
		List<RecordTypeDTO> data = walletRestSeriveHelper.getTypesValidForParent(principal);
		
		assertEquals(18, data.size());
		assertEquals("Administracja", data.get(0).getName());
	}
	
	@Test
	public void updateTypes() {
		WalletType type1;
		WalletType type2;
		RecordTypeDTO typeDto;
		RecordTypeListDTO typeList = new RecordTypeListDTO();
		type1 = walletTypeDAO.getById(4);
		typeDto = new RecordTypeDTO(type1);
		typeDto.setName("newName");
		typeList.getTypes().add(typeDto);
		type2 = walletTypeDAO.getById(5);
		typeDto = new RecordTypeDTO(type2);
		typeDto.setColor("newColor");
		typeList.getTypes().add(typeDto);
		
		List<RecordTypeDTO> result = walletRestSeriveHelper.updateTypes(typeList);
		
		assertEquals(29, result.size());
		assertEquals("newName", result.get(3).getName());
		assertEquals(type1.getColor(), result.get(3).getColor());
		assertEquals(type1.getDefaultValue(), result.get(3).getDefaultValue());
		assertEquals(type1.getIcon(), result.get(3).getIcon());
		assertEquals(type1.getId(), result.get(3).getId());
		assertEquals(type1.getParentType().getId(), result.get(3).getParentId());
		assertEquals(type1.getTimestamp(), result.get(3).getTimestamp());
		assertTrue(result.get(3).getUpdated().after(type1.getTimestamp()));
		assertEquals("newColor", result.get(8).getColor());
	}
	
	@Test
	public void getSumForTypeForStatistics() {
		//TODO sprawdzić czy wynik jest napewno poprawny
		TypeForStatistics type = new TypeForStatistics();
		type.setAccount(1);
		type.setIncome(false);
		type.setType(4);
		List<KeyValueDTO> data = walletRestSeriveHelper.getSumForTypeForStatistics(type, principal);
		
		DateTime tempDate = new DateTime(2015, 11, 1, 0, 0);
		DateTime months = new DateTime();
		int numberOfMonths = 1;
		while(tempDate.isBefore(months)){
			numberOfMonths++;
			tempDate = tempDate.plusMonths(1);
			System.out.println(tempDate);
			System.out.println(months);
			System.out.println(numberOfMonths);
		}
		
		assertEquals(numberOfMonths, data.size());
		assertEquals("39.98", data.get(0).getValue());
		assertEquals("28.49", data.get(1).getValue());
		assertEquals("0", data.get(2).getValue());
		assertEquals("0", data.get(3).getValue());
		assertEquals("0", data.get(4).getValue());
		assertEquals("0", data.get(5).getValue());
		assertEquals("0", data.get(6).getValue());
		assertEquals("0", data.get(7).getValue());
		assertEquals("0", data.get(8).getValue());
		assertEquals("0", data.get(9).getValue());
		
	}
	
	@Test
	public void updateRecord_transfer() {
		WalletRecord record = walletRecordDAO.getById(1);
		RecordDTO recordBefore = new RecordDTO(record);
		
		assertEquals(0, recordBefore.getAccountId());
		assertEquals(1444648380000L, recordBefore.getDate());
		assertEquals(null, recordBefore.getDescription());
		assertEquals(2, recordBefore.getDestynationAccountId());
		assertEquals(1, recordBefore.getSourceWalletAccountId());
		assertEquals(1445035106000L, recordBefore.getUpdated());
		assertTrue(1000.0F == recordBefore.getValue());
		assertTrue(recordBefore.isTransfer());
		
		long update = new Date().getTime();
		
		recordBefore.setAccountId(1);
		recordBefore.setDate(1444648390000L);
		recordBefore.setDescription("description");
		recordBefore.setDestynationAccountId(0);
		recordBefore.setSourceWalletAccountId(2);
		recordBefore.setUpdated(update);
		recordBefore.setValue(5000);
		
		recordBefore.setIncome(!record.isIncome());
		
		ServiceResult result = walletRestSeriveHelper.updateRecord(recordBefore);
		
		assertTrue(result.isSuccess());
		assertEquals(0, result.getMessages().size());
		
		assertEquals(1, recordBefore.getAccountId());
		assertEquals(1444648390000L, recordBefore.getDate());
		assertEquals("description", recordBefore.getDescription());
		assertEquals(0, recordBefore.getDestynationAccountId());
		assertEquals(2, recordBefore.getSourceWalletAccountId());
		assertEquals(update, recordBefore.getUpdated());
		assertTrue(5000.0F == recordBefore.getValue());
		assertTrue(recordBefore.isTransfer());
		
	}
	
	@Test
	public void getSumForTypesForStatistics() {
		TypesForStatistics types = new TypesForStatistics();
		types.setAccount(1);
		types.setIncome(false);
		types.addType(1);
		types.addType(4);
//		ServiceResult result = walletRestSeriveHelper.getSumForTypesForStatistics(types, principal);
		//TODO dokończyc metoda nie działa jeszcze
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void transferToDataValueMap_perDay(){
		Map<String, List<RecordDTO>> result = null;
		Method method = null;
		try {
			method = WalletRestServiceHelper.class.getDeclaredMethod("transferToDataValueMap", List.class, SimpleDateFormat.class);
			method.setAccessible(true);
		} catch (NoSuchMethodException e) {
			
			e.printStackTrace();
		} catch (SecurityException e) {
			
			e.printStackTrace();
		}
		
		List<RecordDTO> records = walletService.getRecordsByType(4, false);
		
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			result = (Map<String, List<RecordDTO>>) method.invoke(walletRestSeriveHelper, records, format);
		} catch (IllegalAccessException e) {
			
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			
			e.printStackTrace();
		}
		
		assertFalse(result.isEmpty());
		assertEquals(4, result.size());
		assertTrue(result.containsKey("2015-10-13"));
		assertEquals(2, result.get("2015-10-13").size());
		assertEquals(new Integer(6), result.get("2015-10-13").get(0).getId());
		assertEquals(new Integer(9), result.get("2015-10-13").get(1).getId());
		assertTrue(result.containsKey("2015-10-24"));
		assertEquals(2, result.get("2015-10-24").size());
		assertEquals(new Integer(7), result.get("2015-10-24").get(0).getId());
		assertEquals(new Integer(10), result.get("2015-10-24").get(1).getId());
		assertEquals(1, result.get("2015-11-13").size());
		assertEquals(new Integer(11), result.get("2015-11-13").get(0).getId());
		assertEquals(1, result.get("2015-11-24").size());
		assertEquals(new Integer(12), result.get("2015-11-24").get(0).getId());
	}
	


}
