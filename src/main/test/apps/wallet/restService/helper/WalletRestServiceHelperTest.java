package apps.wallet.restService.helper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;
import com.timeron.NexusDatabaseLibrary.Entity.WalletRecord;
import com.timeron.NexusDatabaseLibrary.Entity.WalletType;
import com.timeron.NexusDatabaseLibrary.dao.WalletAccountDAO;
import com.timeron.NexusDatabaseLibrary.dao.WalletRecordDAO;
import com.timeron.NexusDatabaseLibrary.dao.WalletTypeDAO;
import com.timeron.nexus.apps.wallet.constant.ResultMessagesWallet;
import com.timeron.nexus.apps.wallet.rest.helper.impl.WalletRestServiceHelper;
import com.timeron.nexus.apps.wallet.service.WalletRecordService;
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
import com.timeron.nexus.common.service.ServiceResult;

import dbUnit.ColumnSensingFlatXMLDataSetLoader;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/simple-job-launcher-context.xml" })
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
		DbUnitTestExecutionListener.class })
@DbUnitConfiguration(dataSetLoader = ColumnSensingFlatXMLDataSetLoader.class)
@DatabaseSetup("/dbMock/Wallet.xml")
public class WalletRestServiceHelperTest {
	
	private static final int RECORDS_START_NUMBER = 12;

	@Autowired
	private WalletRestServiceHelper walletRestSeriveHelper;
	
	@Autowired
	private WalletAccountDAO walletAccountDAO;
	
	@Autowired
	private WalletTypeDAO walletTypeDAO;
	
	@Autowired
	private WalletRecordDAO walletRecordDAO;
	
	@Autowired
	private WalletRecordService walletService;
	
	private Principal principal = new Principal() {

		@Override
		public String getName() {
			return "timeron";
		}
	};
	
	@Ignore
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
		assertEquals(3, walletRestSeriveHelper.getAllUserAccounts(principal).size());
		
		NewAccountDTO accountDTO = new NewAccountDTO();
		accountDTO.setName(name);
		accountDTO.setDescription(desc);
		ServiceResult result = walletRestSeriveHelper.addAccount(accountDTO, principal);
		
		List<AccountForDropdownDTO> accounts = walletRestSeriveHelper.getAllUserAccounts(principal);
		
		assertEquals(4, accounts.size());
		assertEquals(name, accounts.get(accounts.size()-1).getName());
		assertEquals(desc, accounts.get(accounts.size()-1).getDescription());
		assertEquals(result.getFirstMessage(), ResultMessagesWallet.ACCOUNT_ADDED);
		assertEquals(0, result.getErrors().size());
		assertEquals(1, result.getMessages().size());
	}
	
	@Test
	public void addAccount_failOnNameAlreadyExists() {
		String name = "Moje konto 1";
		String desc = "project description";
		assertEquals(3, walletRestSeriveHelper.getAllUserAccounts(principal).size());
		
		NewAccountDTO accountDTO = new NewAccountDTO();
		accountDTO.setName(name);
		accountDTO.setDescription(desc);
		ServiceResult result = walletRestSeriveHelper.addAccount(accountDTO, principal);
		
		List<AccountForDropdownDTO> accounts = walletRestSeriveHelper.getAllUserAccounts(principal);
		
		assertEquals(3, accounts.size());
		assertEquals(result.getFirstError(), ResultMessagesWallet.ACCOUNT_ADD_ERROR);
		assertEquals(1, result.getErrors().size());
		assertEquals(0, result.getMessages().size());
	}
	
	@Ignore
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

		assertEquals(RECORDS_START_NUMBER, walletRecordDAO.getAll().size());
		
		ServiceResult result = walletRestSeriveHelper.addNewRecord(recordDTO);
		assertTrue(result.isSuccess());
		assertEquals(1, result.getMessages().size());
		assertTrue(result.getMessages().get(0).equals(ResultMessagesWallet.RECORD_ADDED));
		
		WalletRecord record = walletRecordDAO.getById(walletRecordDAO.getLastId()); 

		assertEquals(RECORDS_START_NUMBER+1, walletRecordDAO.getAll().size());
		assertEquals(recordDTO.getDate(), record.getDate().getTime());
		assertEquals(recordDTO.getDescription(), record.getDescription());
		assertEquals(recordDTO.getAccountId(), record.getWalletAccount().getId());
		assertFalse(record.isIncome());
		assertEquals(recordDTO.getRecordTypeId(), record.getWalletType().getId());
		assertFalse(record.isTransfer());
		assertEquals((Float)recordDTO.getValue(), (Float)record.getValue());
	}
	
	@Test
	public void addNewRecord_OnNoData() {
		RecordDTO recordDTO = new RecordDTO();
		recordDTO.setDate(0);
		recordDTO.setDescription("description");
		recordDTO.setAccountId(1);
		recordDTO.setIncome(false);
		recordDTO.setRecordTypeId(4);
		recordDTO.setTransfer(false);
		recordDTO.setUpdated(new Date().getTime());
		recordDTO.setValue(20.56F);
		
		assertEquals(RECORDS_START_NUMBER, walletRecordDAO.getAll().size());
		
		ServiceResult result = walletRestSeriveHelper.addNewRecord(recordDTO);
		assertTrue(result.isSuccess());
		assertEquals(1, result.getMessages().size());
		assertTrue(result.getMessages().get(0).equals(ResultMessagesWallet.RECORD_ADDED));
		
		WalletRecord record = walletRecordDAO.getById(walletRecordDAO.getLastId()); 
		
		assertEquals(RECORDS_START_NUMBER+1, walletRecordDAO.getAll().size());
		assertTrue(record.getDate().getTime() > 0);
		assertEquals(recordDTO.getDescription(), record.getDescription());
		assertEquals(recordDTO.getAccountId(), record.getWalletAccount().getId());
		assertFalse(record.isIncome());
		assertEquals(recordDTO.getRecordTypeId(), record.getWalletType().getId());
		assertFalse(record.isTransfer());
		assertEquals((Float)recordDTO.getValue(), (Float)record.getValue());
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

		assertEquals(RECORDS_START_NUMBER, walletRecordDAO.getAll().size());
		
		ServiceResult result = walletRestSeriveHelper.addNewRecord(recordDTO);
		assertTrue(result.isSuccess());
		assertEquals(1, result.getMessages().size());
		assertTrue(result.getMessages().get(0).equals(ResultMessagesWallet.RECORD_ADDED));
		
		WalletRecord record = walletRecordDAO.getById(walletRecordDAO.getLastId()); 
		
		assertEquals(RECORDS_START_NUMBER+1, walletRecordDAO.getAll().size());
		assertTrue(record.getDate().getTime() > 0);
		assertEquals(recordDTO.getDescription(), record.getDescription());
		assertEquals(recordDTO.getAccountId(), record.getWalletAccount().getId());
		assertTrue(record.isIncome());
		assertEquals(recordDTO.getRecordTypeId(), record.getWalletType().getId());
		assertFalse(record.isTransfer());
		assertEquals((Float)recordDTO.getValue(), (Float)record.getValue());
		
	}
	
	@Test
	public void addNewRecord_expenditure() {
		RecordDTO recordDTO = new RecordDTO();
		recordDTO.setDate(new Date().getTime());
		recordDTO.setDescription("description");
		recordDTO.setAccountId(1);
		recordDTO.setIncome(false);
		recordDTO.setRecordTypeId(4);
		recordDTO.setTransfer(false);
		recordDTO.setUpdated(new Date().getTime());
		recordDTO.setValue(20.56F);

		assertEquals(RECORDS_START_NUMBER, walletRecordDAO.getAll().size());
		
		ServiceResult result = walletRestSeriveHelper.addNewRecord(recordDTO);
		assertTrue(result.isSuccess());
		assertEquals(1, result.getMessages().size());
		assertTrue(result.getMessages().get(0).equals(ResultMessagesWallet.RECORD_ADDED));
		
		WalletRecord record = walletRecordDAO.getById(walletRecordDAO.getLastId()); 
		
		assertEquals(RECORDS_START_NUMBER+1, walletRecordDAO.getAll().size());
		assertTrue(record.getDate().getTime() > 0);
		assertEquals(recordDTO.getDescription(), record.getDescription());
		assertEquals(recordDTO.getAccountId(), record.getWalletAccount().getId());
		assertFalse(record.isIncome());
		assertEquals(recordDTO.getRecordTypeId(), record.getWalletType().getId());
		assertFalse(record.isTransfer());
		assertEquals((Float)recordDTO.getValue(), (Float)record.getValue());
		
	}

	@Test
	public void addNewRecord_OnToLongDescription() {
		RecordDTO recordDTO = new RecordDTO();
		recordDTO.setDate(0);
		recordDTO.setDescription("to long description to long description to long description to long description to long description to long description to long description to long description to long description to long description to long description to long description to long description to long description to long description to long description");
		recordDTO.setAccountId(1);
		recordDTO.setIncome(false);
		recordDTO.setRecordTypeId(4);
		recordDTO.setTransfer(false);
		recordDTO.setUpdated(new Date().getTime());
		recordDTO.setValue(20.56F);
		
		assertEquals(RECORDS_START_NUMBER, walletRecordDAO.getAll().size());
		
		ServiceResult result = walletRestSeriveHelper.addNewRecord(recordDTO);
		assertTrue(result.isSuccess());
		assertEquals(1, result.getMessages().size());
		assertTrue(result.getMessages().get(0).equals(ResultMessagesWallet.RECORD_ADDED));
		
		WalletRecord record = walletRecordDAO.getById(walletRecordDAO.getLastId()); 

		assertEquals(RECORDS_START_NUMBER+1, walletRecordDAO.getAll().size());
		assertTrue(record.getDate().getTime() > 0);
		assertEquals(recordDTO.getDescription(), record.getDescription());
		assertEquals(recordDTO.getAccountId(), record.getWalletAccount().getId());
		assertFalse(record.isIncome());
		assertEquals(recordDTO.getRecordTypeId(), record.getWalletType().getId());
		assertFalse(record.isTransfer());
		assertEquals((Float)recordDTO.getValue(), (Float)record.getValue());
	}
	
	@Test
	public void addNewRecord_failOnNotExistingType() {
		RecordDTO recordDTO = new RecordDTO();
		recordDTO.setDate(new Date().getTime());
		recordDTO.setDescription("description");
		recordDTO.setAccountId(1);
		recordDTO.setIncome(false);
		recordDTO.setRecordTypeId(40);
		recordDTO.setTransfer(false);
		recordDTO.setUpdated(new Date().getTime());
		recordDTO.setValue(20.56F);
		
		assertEquals(RECORDS_START_NUMBER, walletRecordDAO.getAll().size());
		
		ServiceResult result = walletRestSeriveHelper.addNewRecord(recordDTO);
		assertFalse(result.isSuccess());
		assertEquals(0, result.getMessages().size());
		assertEquals(1, result.getErrors().size());
		assertTrue(result.getFirstError().equals(ResultMessagesWallet.TYPE_NOT_EXISTS));
		
		assertEquals(RECORDS_START_NUMBER, walletRecordDAO.getAll().size());
	}
	
	@Test
	public void addNewRecord_failOn0Value() {
		RecordDTO recordDTO = new RecordDTO();
		recordDTO.setDate(new Date().getTime());
		recordDTO.setDescription("description");
		recordDTO.setAccountId(1);
		recordDTO.setIncome(false);
		recordDTO.setRecordTypeId(4);
		recordDTO.setTransfer(false);
		recordDTO.setUpdated(new Date().getTime());
		recordDTO.setValue(0);
		
		assertEquals(RECORDS_START_NUMBER, walletRecordDAO.getAll().size());
		
		ServiceResult result = walletRestSeriveHelper.addNewRecord(recordDTO);
		assertFalse(result.isSuccess());
		assertEquals(0, result.getMessages().size());
		assertEquals(1, result.getErrors().size());
		assertEquals(result.getFirstError(), ResultMessagesWallet.VALUE_NEGATIVE);
		
		assertEquals(RECORDS_START_NUMBER, walletRecordDAO.getAll().size());
	}
	
	@Test
	public void addNewRecord_failNotExistingAccount() {
		RecordDTO recordDTO = new RecordDTO();
		recordDTO.setDate(new Date().getTime());
		recordDTO.setDescription("description");
		recordDTO.setAccountId(10);
		recordDTO.setIncome(false);
		recordDTO.setRecordTypeId(4);
		recordDTO.setTransfer(false);
		recordDTO.setUpdated(new Date().getTime());
		recordDTO.setValue(20.56F);
		
		assertEquals(RECORDS_START_NUMBER, walletRecordDAO.getAll().size());
		
		ServiceResult result = walletRestSeriveHelper.addNewRecord(recordDTO);
		assertFalse(result.isSuccess());
		assertEquals(0, result.getMessages().size());
		assertEquals(1, result.getErrors().size());
		assertEquals(result.getFirstError(), ResultMessagesWallet.RECORD_ADD_NO_ACCOUNT);
		
		assertEquals(RECORDS_START_NUMBER, walletRecordDAO.getAll().size());
	}
	
	@Test
	public void addNewTransferRecord() {
		RecordDTO recordDTO = new RecordDTO();
		recordDTO.setDate(new Date().getTime());
		recordDTO.setDescription("description");
		recordDTO.setDestynationAccountId(2);
		recordDTO.setAccountId(1);
		recordDTO.setIncome(true);
		recordDTO.setRecordTypeId(0);
		recordDTO.setSourceWalletAccountId(1);
		recordDTO.setTransfer(true);
		recordDTO.setUpdated(new Date().getTime());
		recordDTO.setValue(20.56F);

		assertEquals(RECORDS_START_NUMBER, walletRecordDAO.getAll().size());
		
		ServiceResult result = walletRestSeriveHelper.addNewRecord(recordDTO);
		assertTrue(result.isSuccess());
		assertEquals(1, result.getMessages().size());
		assertEquals(0, result.getErrors().size());
		assertTrue(result.getMessages().get(0).equals(ResultMessagesWallet.RECORD_ADDED));
		
		WalletRecord destinationRecord = walletRecordDAO.getById(walletRecordDAO.getLastId()); 
		WalletRecord SourceRecord = walletRecordDAO.getById(walletRecordDAO.getLastId()-1); 

		assertEquals(RECORDS_START_NUMBER+2, walletRecordDAO.getAll().size());
		
		assertEquals(recordDTO.getDate(), destinationRecord.getDate().getTime());
		assertEquals(recordDTO.getDescription(), destinationRecord.getDescription());
		assertEquals(2, destinationRecord.getWalletAccount().getId());
		assertTrue(destinationRecord.isIncome());
		assertEquals(null, destinationRecord.getWalletType());
		assertTrue(destinationRecord.isTransfer());
		assertEquals((Float)recordDTO.getValue(), (Float)destinationRecord.getValue());
		assertEquals(1, destinationRecord.getSourceWalletAccount().getId());
		assertEquals(2, destinationRecord.getDestinationWalletAccount().getId());
		
		assertEquals(recordDTO.getDate(), SourceRecord.getDate().getTime());
		assertEquals(recordDTO.getDescription(), SourceRecord.getDescription());
		assertEquals(1, SourceRecord.getWalletAccount().getId());
		assertFalse(SourceRecord.isIncome());
		assertEquals(null, SourceRecord.getWalletType());
		assertTrue(SourceRecord.isTransfer());
		assertEquals((Float)recordDTO.getValue(), (Float)SourceRecord.getValue());
		assertEquals(1, SourceRecord.getSourceWalletAccount().getId());
		assertEquals(2, SourceRecord.getDestinationWalletAccount().getId());
	}
	
	@Test
	public void addNewTransferRecord_OnNotExistingAccountSourceAccount() {
		RecordDTO recordDTO = new RecordDTO();
		recordDTO.setDate(new Date().getTime());
		recordDTO.setDescription("description");
		recordDTO.setDestynationAccountId(2);
		recordDTO.setAccountId(1);
		recordDTO.setIncome(true);
		recordDTO.setRecordTypeId(0);
		recordDTO.setSourceWalletAccountId(10);
		recordDTO.setTransfer(true);
		recordDTO.setUpdated(new Date().getTime());
		recordDTO.setValue(20.56F);

		assertEquals(RECORDS_START_NUMBER, walletRecordDAO.getAll().size());
		
		ServiceResult result = walletRestSeriveHelper.addNewRecord(recordDTO);
		assertTrue(result.isSuccess());
		assertEquals(1, result.getMessages().size());
		assertEquals(0, result.getErrors().size());
		assertTrue(result.getMessages().get(0).equals(ResultMessagesWallet.RECORD_ADDED));
		
		WalletRecord destinationRecord = walletRecordDAO.getById(walletRecordDAO.getLastId()); 
		WalletRecord SourceRecord = walletRecordDAO.getById(walletRecordDAO.getLastId()-1); 

		assertEquals(RECORDS_START_NUMBER+2, walletRecordDAO.getAll().size());
		
		assertEquals(recordDTO.getDate(), destinationRecord.getDate().getTime());
		assertEquals(recordDTO.getDescription(), destinationRecord.getDescription());
		assertEquals(2, destinationRecord.getWalletAccount().getId());
		assertTrue(destinationRecord.isIncome());
		assertEquals(null, destinationRecord.getWalletType());
		assertTrue(destinationRecord.isTransfer());
		assertEquals((Float)recordDTO.getValue(), (Float)destinationRecord.getValue());
		assertEquals(1, destinationRecord.getSourceWalletAccount().getId());
		assertEquals(2, destinationRecord.getDestinationWalletAccount().getId());
		
		assertEquals(recordDTO.getDate(), SourceRecord.getDate().getTime());
		assertEquals(recordDTO.getDescription(), SourceRecord.getDescription());
		assertEquals(1, SourceRecord.getWalletAccount().getId());
		assertFalse(SourceRecord.isIncome());
		assertEquals(null, SourceRecord.getWalletType());
		assertTrue(SourceRecord.isTransfer());
		assertEquals((Float)recordDTO.getValue(), (Float)SourceRecord.getValue());
		assertEquals(1, SourceRecord.getSourceWalletAccount().getId());
		assertEquals(2, SourceRecord.getDestinationWalletAccount().getId());
	}
	
	@Test
	public void addNewTransferRecord_failOnValue() {
		RecordDTO recordDTO = new RecordDTO();
		recordDTO.setDate(new Date().getTime());
		recordDTO.setDescription("description");
		recordDTO.setDestynationAccountId(2);
		recordDTO.setAccountId(1);
		recordDTO.setIncome(true);
		recordDTO.setRecordTypeId(0);
		recordDTO.setSourceWalletAccountId(1);
		recordDTO.setTransfer(true);
		recordDTO.setUpdated(new Date().getTime());
		recordDTO.setValue(0);

		assertEquals(RECORDS_START_NUMBER, walletRecordDAO.getAll().size());
		
		ServiceResult result = walletRestSeriveHelper.addNewRecord(recordDTO);
		assertFalse(result.isSuccess());
		assertEquals(0, result.getMessages().size());
		assertEquals(2, result.getErrors().size());
		assertTrue(result.getErrors().get(0).equals(ResultMessagesWallet.VALUE_NEGATIVE));
		assertTrue(result.getErrors().get(1).equals(ResultMessagesWallet.VALUE_NEGATIVE));
		
		assertEquals(RECORDS_START_NUMBER, walletRecordDAO.getAll().size());
	}
	
	@Test
	public void addNewTransferRecord_failOnSameAccount() {
		RecordDTO recordDTO = new RecordDTO();
		recordDTO.setDate(new Date().getTime());
		recordDTO.setDescription("description");
		recordDTO.setDestynationAccountId(1); //same as account and source account
		recordDTO.setAccountId(1);
		recordDTO.setIncome(true);
		recordDTO.setRecordTypeId(0);
		recordDTO.setSourceWalletAccountId(1);
		recordDTO.setTransfer(true);
		recordDTO.setUpdated(new Date().getTime());
		recordDTO.setValue(20.56F);

		assertEquals(RECORDS_START_NUMBER, walletRecordDAO.getAll().size());
		
		ServiceResult result = walletRestSeriveHelper.addNewRecord(recordDTO);
		assertFalse(result.isSuccess());
		assertEquals(0, result.getMessages().size());
		assertEquals(3, result.getErrors().size());
		assertEquals(result.getErrors().get(0), ResultMessagesWallet.DATA_ERROR);
		assertEquals(result.getErrors().get(1), ResultMessagesWallet.DATA_ERROR);
		assertEquals(result.getErrors().get(2), ResultMessagesWallet.DATA_ERROR);
		
		assertEquals(RECORDS_START_NUMBER, walletRecordDAO.getAll().size());
	}
	
	@Test
	public void addNewTransferRecord_failOnNotExistingAccount() {
		RecordDTO recordDTO = new RecordDTO();
		recordDTO.setDate(new Date().getTime());
		recordDTO.setDescription("description");
		recordDTO.setDestynationAccountId(2);
		recordDTO.setAccountId(10);
		recordDTO.setIncome(true);
		recordDTO.setRecordTypeId(0);
		recordDTO.setSourceWalletAccountId(1);
		recordDTO.setTransfer(true);
		recordDTO.setUpdated(new Date().getTime());
		recordDTO.setValue(20.56F);

		assertEquals(RECORDS_START_NUMBER, walletRecordDAO.getAll().size());
		
		ServiceResult result = walletRestSeriveHelper.addNewRecord(recordDTO);
		assertFalse(result.isSuccess());
		assertEquals(0, result.getMessages().size());
		assertEquals(1, result.getErrors().size());
		assertEquals(result.getFirstError(), ResultMessagesWallet.RECORD_ADD_NO_ACCOUNT);
		
		assertEquals(RECORDS_START_NUMBER, walletRecordDAO.getAll().size());
	}
	
	public void addNewTransferRecord_failOnNotExistingAccountDestinationAccount() {
		RecordDTO recordDTO = new RecordDTO();
		recordDTO.setDate(new Date().getTime());
		recordDTO.setDescription("description");
		recordDTO.setDestynationAccountId(20);
		recordDTO.setAccountId(1);
		recordDTO.setIncome(true);
		recordDTO.setRecordTypeId(0);
		recordDTO.setSourceWalletAccountId(1);
		recordDTO.setTransfer(true);
		recordDTO.setUpdated(new Date().getTime());
		recordDTO.setValue(20.56F);

		assertEquals(RECORDS_START_NUMBER, walletRecordDAO.getAll().size());
		
		ServiceResult result = walletRestSeriveHelper.addNewRecord(recordDTO);
		assertFalse(result.isSuccess());
		assertEquals(0, result.getMessages().size());
		assertEquals(1, result.getErrors().size());
		assertEquals(result.getFirstError(), ResultMessagesWallet.RECORD_ADD_NO_ACCOUNT);
		
		assertEquals(RECORDS_START_NUMBER, walletRecordDAO.getAll().size());
	}
	
	@Ignore
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
		assertEquals(3, accounts.size());
		assertEquals(9, getAccount(accounts, 1).getRecords().size());
		assertEquals(1, getAccount(accounts, 2).getRecords().size());
		assertEquals(2, getAccount(accounts, 3).getRecords().size());
	}

	
	@Ignore
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
	
	@Ignore
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
	
	@Ignore
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
		assertEquals(2, data.size());
		assertEquals("118.60", data.get(0).getValue());
		assertEquals("8.00", data.get(1).getValue());
	}
	
	@Ignore
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
		assertEquals(29, walletTypeDAO.getAll().size());
		
		ServiceResult result = walletRestSeriveHelper.addNewType(type);
		
		assertTrue(result.isSuccess());
		assertEquals(1, result.getMessages().size());
		assertEquals(ResultMessagesWallet.RECORD_ADDED, result.getFirstMessage());
		assertEquals(30, walletTypeDAO.getAll().size());
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
		assertEquals(29, walletTypeDAO.getAll().size());
		ServiceResult result = walletRestSeriveHelper.addNewType(type);
		
		assertFalse(result.isSuccess());
		assertEquals(1, result.getErrors().size());
		assertEquals(ResultMessagesWallet.TYPE_NOT_ADDED_PARENT_DOESNOT_EXIST, result.getFirstError());
		assertEquals(29, walletTypeDAO.getAll().size());
	}
	
	@Ignore
	@Test
	public void getTypesValidForParent() {
		List<RecordTypeDTO> data = walletRestSeriveHelper.getTypesValidForParent(principal);
		
		assertEquals(18, data.size());
		assertEquals("Administracja", data.get(0).getName());
	}
	
	@Ignore
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
		assertEquals(new Integer(type1.getParentType().getId()), result.get(3).getParentId());
		assertEquals(type1.getTimestamp(), result.get(3).getTimestamp());
		assertTrue(result.get(3).getUpdated().after(type1.getTimestamp()));
		assertEquals("newColor", result.get(8).getColor());
	}
	
	@Ignore
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
	
	@Ignore
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
	
	@Ignore
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
	
	//*****************************************
	// Private methods
	//*****************************************
	
	@Ignore
	@SuppressWarnings("unchecked")
	@Test
	public void transferToDataValueMap_perMonth(){
		Map<String, List<RecordDTO>> result = null;
		Method method = null;
		try {
			method = WalletRestServiceHelper.class.getDeclaredMethod("transferToDataValueMap", List.class, SimpleDateFormat.class);
			method.setAccessible(true);
		} catch (NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
		//TODO pobieramy dla wszystkich kont zamiast dla wybranego konta (przy kilku userach pobierzemy typ z wszystkich userów)
		//TODO getRecordsByType powinno być przetestowane (nie ma testu)
		List<RecordDTO> records = walletService.getRecordsByType(4, false);
		
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
			result = (Map<String, List<RecordDTO>>) method.invoke(walletRestSeriveHelper, records, format);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
		
		assertFalse(result.isEmpty());
		assertEquals(2, result.size());
		assertTrue(result.containsKey("2015-09"));
		assertTrue(result.containsKey("2015-12"));
		assertEquals(4, result.get("2015-12").size());
		assertTrue(result.get("2015-12").contains(new RecordDTO(8)));
		assertTrue(result.get("2015-12").contains(new RecordDTO(10)));
		assertTrue(result.get("2015-12").contains(new RecordDTO(11)));
		assertTrue(result.get("2015-12").contains(new RecordDTO(12)));
		assertTrue(result.containsKey("2015-09"));
		assertEquals(1, result.get("2015-09").size());
		assertTrue(result.get("2015-09").contains(new RecordDTO(2)));
	}
	
	private AccountDTO getAccount(List<AccountDTO> accounts, int id) {
		for(AccountDTO account : accounts){
			if(account.getId() == id){
				return account;
			}
		}
		return null;
	}


}
