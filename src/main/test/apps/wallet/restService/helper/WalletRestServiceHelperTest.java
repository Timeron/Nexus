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
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
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
import com.timeron.nexus.apps.wallet.exception.ValidationException;
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
	
	private static final int RECORDS_START_NUMBER = 13;

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
		
		assertEquals("2015-09", object.getKayValues().get(0).getKey());
		KeyValueDTO kv = new KeyValueDTO("Sodexo", "6.80");
		assertTrue(object.getKayValues().get(0).getValues().contains(kv));
		kv = new KeyValueDTO("Bank", "0");
		assertTrue(object.getKayValues().get(0).getValues().contains(kv));
		
		assertEquals("2015-10", object.getKayValues().get(1).getKey());
		kv = new KeyValueDTO("Sodexo", "0");
		assertTrue(object.getKayValues().get(1).getValues().contains(kv));
		kv = new KeyValueDTO("Bank", "0");
		assertTrue(object.getKayValues().get(1).getValues().contains(kv));

		assertEquals("2015-11", object.getKayValues().get(2).getKey());
		kv = new KeyValueDTO("Sodexo", "0");
		assertTrue(object.getKayValues().get(2).getValues().contains(kv));
		kv = new KeyValueDTO("Bank", "8.00");
		assertTrue(object.getKayValues().get(2).getValues().contains(kv));
		
		assertEquals("2015-12", object.getKayValues().get(3).getKey());
		kv = new KeyValueDTO("Sodexo", "62.05");
		assertTrue(object.getKayValues().get(3).getValues().contains(kv));
		kv = new KeyValueDTO("Bank", "0");
		assertTrue(object.getKayValues().get(3).getValues().contains(kv));
		
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
		assertEquals(recordDTO.getDate(), record.getDate().getMillis());
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
		assertTrue(record.getDate().getMillis() > 0);
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
		assertTrue(record.getDate().getMillis()> 0);
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
		assertTrue(record.getDate().getMillis() > 0);
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
		assertTrue(record.getDate().getMillis() > 0);
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
		
		assertEquals(recordDTO.getDate(), destinationRecord.getDate().getMillis());
		assertEquals(recordDTO.getDescription(), destinationRecord.getDescription());
		assertEquals(2, destinationRecord.getWalletAccount().getId());
		assertTrue(destinationRecord.isIncome());
		assertEquals(null, destinationRecord.getWalletType());
		assertTrue(destinationRecord.isTransfer());
		assertEquals((Float)recordDTO.getValue(), (Float)destinationRecord.getValue());
		assertEquals(1, destinationRecord.getSourceWalletAccount().getId());
		assertEquals(2, destinationRecord.getDestinationWalletAccount().getId());
		
		assertEquals(recordDTO.getDate(), SourceRecord.getDate().getMillis());
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
		
		assertEquals(recordDTO.getDate(), destinationRecord.getDate().getMillis());
		assertEquals(recordDTO.getDescription(), destinationRecord.getDescription());
		assertEquals(2, destinationRecord.getWalletAccount().getId());
		assertTrue(destinationRecord.isIncome());
		assertEquals(null, destinationRecord.getWalletType());
		assertTrue(destinationRecord.isTransfer());
		assertEquals((Float)recordDTO.getValue(), (Float)destinationRecord.getValue());
		assertEquals(1, destinationRecord.getSourceWalletAccount().getId());
		assertEquals(2, destinationRecord.getDestinationWalletAccount().getId());
		
		assertEquals(recordDTO.getDate(), SourceRecord.getDate().getMillis());
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
	
	@Test
	public void getAllUserAccounts() {
		List<AccountForDropdownDTO> accounts = walletRestSeriveHelper.getAllUserAccounts(principal);
		
		assertEquals(3, accounts.size());
		assertEquals("Moje konto 1", accounts.get(0).getName());
		assertEquals("Prywatne konto 1", accounts.get(0).getDescription());
		assertEquals("Moje konto 2", accounts.get(1).getName());
		assertEquals("Prywatne konto 2", accounts.get(1).getDescription());
		assertEquals("Moje konto 3", accounts.get(2).getName());
		assertEquals("Prywatne konto 3", accounts.get(2).getDescription());
		
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
		assertEquals(3, getAccount(accounts, 3).getRecords().size());
	}

	@Test
	public void getRecordsForAccountByDay() {
		List<KeyValueDTO> data = walletRestSeriveHelper.getRecordsForAccountByDay(1, principal);
		DateTime date = new DateTime();
		DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MMM-dd");
		String dateStr = fmt.print(date);
		
		assertEquals(7, data.size());
		assertEquals("4993.20", data.get(0).getValue());
		assertEquals("2015-Sep-02", data.get(0).getKey());
		assertEquals("3993.20", data.get(1).getValue());
		assertEquals("2015-Oct-02", data.get(1).getKey());
		assertEquals("3935.45", data.get(2).getValue());
		assertEquals("2015-Nov-02", data.get(2).getKey());
		assertEquals("3877.70", data.get(3).getValue());
		assertEquals("2015-Dec-02", data.get(3).getKey());
		assertEquals("3873.40", data.get(4).getValue());
		assertEquals("2015-Dec-04", data.get(4).getKey());
		assertEquals("8066.90", data.get(5).getValue());
		assertEquals("2015-Dec-09", data.get(5).getKey());
		assertEquals("8066.90", data.get(6).getValue());
		assertEquals(dateStr, data.get(6).getKey());
	}
	
	@Test
	public void getSumForAccountByType() {
		SumForAccountByType dto = new SumForAccountByType();
		dto.setId(1);
		dto.setIncome(false);
		List<PieChartDTO> data = walletRestSeriveHelper.getSumForAccountByType(dto, principal);
		assertEquals(3, data.size());
		assertEquals("8.00", data.get(0).getValue());
		assertEquals("#DD0", data.get(0).getColor());
		assertEquals("Bank", data.get(0).getKey());
		assertEquals("49.75", data.get(1).getValue());
		assertEquals("#F00", data.get(1).getColor());
		assertEquals("Artykuły spożywcze", data.get(1).getKey());
		assertEquals("68.85", data.get(2).getValue());
		assertEquals("#F50", data.get(2).getColor());
		assertEquals("Sodexo", data.get(2).getKey());
	}
	
	@Test
	public void getSumForAccountByType_Income() {
		SumForAccountByType dto = new SumForAccountByType();
		dto.setId(1);
		dto.setIncome(true);
		List<PieChartDTO> data = walletRestSeriveHelper.getSumForAccountByType(dto, principal);
		assertEquals(1, data.size());
		assertEquals("9193.50", data.get(0).getValue());
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
	
	@Test
	public void getSumForTypeInTypeHierarchy_expenditure() {
		SumForAccountByType dto = new SumForAccountByType();
		dto.setId(1);
		dto.setIncome(false);
		List<HierarchyPieChartDTO> data = walletRestSeriveHelper.getSumForTypeInTypeHierarchy(dto, principal);
		
		assertEquals(2, data.size());
		
		HierarchyPieChartDTO tree1 = data.get(0);
		HierarchyPieChartDTO tree2 = data.get(1);
		
		assertEquals("118.60", tree1.getValue());
		assertEquals("Artykuły spożywcze", tree1.getKey());
		assertEquals("#F00", tree1.getColor());
		assertEquals(6, tree1.getOrder());
		assertEquals(4, tree1.getChildren().size());
		
		assertEquals("Sodexo", tree1.getChildren().get(0).getKey());
		assertEquals("68.85", tree1.getChildren().get(0).getValue());
		assertEquals("#F50", tree1.getChildren().get(0).getColor());
		assertEquals(4, tree1.getChildren().get(0).getOrder());
		assertEquals(0, tree1.getChildren().get(0).getChildren().size());
		
		assertEquals("Artykuły spożywcze", tree1.getChildren().get(1).getKey());
		assertEquals("49.75", tree1.getChildren().get(1).getValue());
		assertEquals("#F00", tree1.getChildren().get(1).getColor());
		assertEquals(6, tree1.getChildren().get(1).getOrder());
		assertEquals(0, tree1.getChildren().get(1).getChildren().size());
		
		assertEquals("Pizza", tree1.getChildren().get(2).getKey());
		assertEquals("0", tree1.getChildren().get(2).getValue());
		assertEquals("#FA0", tree1.getChildren().get(2).getColor());
		assertEquals(5, tree1.getChildren().get(2).getOrder());
		assertEquals(0, tree1.getChildren().get(2).getChildren().size());
		
		assertEquals("Jedzenie poza domem", tree1.getChildren().get(3).getKey());
		assertEquals("0", tree1.getChildren().get(3).getValue());
		assertEquals("#F80", tree1.getChildren().get(3).getColor());
		assertEquals(12, tree1.getChildren().get(3).getOrder());
		assertEquals(0, tree1.getChildren().get(3).getChildren().size());
		
		//tree 2
		
		assertEquals("8.00", tree2.getValue());
		assertEquals("Administracja", tree2.getKey());
		assertEquals("#642", tree2.getColor());
		assertEquals(20, tree2.getOrder());
		assertEquals(3, tree2.getChildren().size());
		
		assertEquals("Bank", tree2.getChildren().get(0).getKey());
		assertEquals("8.00", tree2.getChildren().get(0).getValue());
		assertEquals("#DD0", tree2.getChildren().get(0).getColor());
		assertEquals(1, tree2.getChildren().get(0).getOrder());
		assertEquals(0, tree1.getChildren().get(0).getChildren().size());
		
		assertEquals("Podatek", tree2.getChildren().get(1).getKey());
		assertEquals("0", tree2.getChildren().get(1).getValue());
		assertEquals("#000", tree2.getChildren().get(1).getColor());
		assertEquals(24, tree2.getChildren().get(1).getOrder());
		assertEquals(0, tree1.getChildren().get(1).getChildren().size());
		
		assertEquals("Administracja", tree2.getChildren().get(2).getKey());
		assertEquals("0", tree2.getChildren().get(2).getValue());
		assertEquals("#642", tree2.getChildren().get(2).getColor());
		assertEquals(20, tree2.getChildren().get(2).getOrder());
		assertEquals(0, tree1.getChildren().get(2).getChildren().size());
	}
	
	@Test
	public void getSumForTypeInTypeHierarchy_income() {
		SumForAccountByType dto = new SumForAccountByType();
		dto.setId(1);
		dto.setIncome(true);
		List<HierarchyPieChartDTO> data = walletRestSeriveHelper.getSumForTypeInTypeHierarchy(dto, principal);
		
		assertEquals(1, data.size());
		
		HierarchyPieChartDTO tree1 = data.get(0);
		
		assertEquals("9193.50", tree1.getValue());
		assertEquals("Wynagrodzenie, przychód", tree1.getKey());
		assertEquals("#090", tree1.getColor());
		assertEquals(11, tree1.getOrder());
		assertEquals(1, tree1.getChildren().size());
		
		assertEquals("Wynagrodzenie, przychód", tree1.getChildren().get(0).getKey());
		assertEquals("9193.50", tree1.getChildren().get(0).getValue());
		assertEquals("#090", tree1.getChildren().get(0).getColor());
		assertEquals(11, tree1.getChildren().get(0).getOrder());
		assertEquals(0, tree1.getChildren().get(0).getChildren().size());
		
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
	
	@Test
	public void getTypesValidForParent() {
		List<RecordTypeDTO> data = walletRestSeriveHelper.getTypesValidForParent(principal);
		
		assertEquals(18, data.size());
		assertEquals("Administracja", data.get(0).getName());
	}
	
	//TODO zmieniłem zrwacany typ na serviceResult
	@SuppressWarnings("unchecked")
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
		
		ServiceResult result = walletRestSeriveHelper.updateTypes(typeList);
		List<RecordTypeDTO> types = (List<RecordTypeDTO>) result.getObject();
		
		assertEquals(29, types.size());
		assertEquals("newName", types.get(3).getName());
		assertEquals(type1.getColor(), types.get(3).getColor());
		assertEquals(type1.getDefaultValue(), types.get(3).getDefaultValue());
		assertEquals(type1.getIcon(), types.get(3).getIcon());
		assertEquals(type1.getId(), types.get(3).getId());
		assertEquals(new Integer(type1.getParentType().getId()), types.get(3).getParentId());
		assertEquals(type1.getTimestamp(), types.get(3).getTimestamp());
		assertTrue(types.get(3).getUpdated().after(type1.getTimestamp()));
		assertEquals("newColor", types.get(8).getColor());
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void updateTypes_failOnNotExistingType() {
		WalletType type1;
		WalletType type2;
		RecordTypeDTO typeDto;
		RecordTypeListDTO typeList = new RecordTypeListDTO();
		type1 = walletTypeDAO.getById(4);
		typeDto = new RecordTypeDTO(type1);
		typeDto.setId(50);
		typeDto.setName("newName");
		typeList.getTypes().add(typeDto);
		type2 = walletTypeDAO.getById(5);
		typeDto = new RecordTypeDTO(type2);
		typeDto.setColor("newColor");
		typeList.getTypes().add(typeDto);
		
		ServiceResult result = walletRestSeriveHelper.updateTypes(typeList);
		List<RecordTypeDTO> types = (List<RecordTypeDTO>) result.getObject();
		
		assertFalse(result.isSuccess());
		assertEquals(3, result.getErrors().size());
		assertTrue(result.getErrors().contains(ResultMessagesWallet.TYPE_ID_MISSING));
		assertTrue(result.getErrors().contains(ResultMessagesWallet.TYPE_TIMESTAMP_MISSING));
		
		assertEquals(29, types.size());
		assertEquals("Sodexo", types.get(3).getName());
		assertEquals(type1.getColor(), types.get(3).getColor());
		assertEquals(type1.getDefaultValue(), types.get(3).getDefaultValue());
		assertEquals(type1.getIcon(), types.get(3).getIcon());
		assertEquals(type1.getId(), types.get(3).getId());
		assertEquals(new Integer(type1.getParentType().getId()), types.get(3).getParentId());
		assertEquals(type1.getTimestamp(), types.get(3).getTimestamp());
		assertTrue(types.get(3).getUpdated().after(type1.getTimestamp()));
		assertEquals("#FA0", types.get(8).getColor());
	}
	
	@Test
	public void getSumForTypeForStatistics() {
		TypeForStatistics type = new TypeForStatistics();
		type.setAccount(1);
		type.setIncome(false);
		type.setType(4);
		List<KeyValueDTO> data = walletRestSeriveHelper.getSumForTypeForStatistics(type, principal);
		
		DateTime tempDate = new DateTime(2015, 9, 1, 0, 0);
		DateTime months = new DateTime();
		int numberOfMonths = 0;
		while(tempDate.isBefore(months)){
			numberOfMonths++;
			tempDate = tempDate.plusMonths(1);
		}
		
		assertEquals(numberOfMonths, data.size());
		assertEquals("6.80", data.get(0).getValue());
		assertEquals("62.05", data.get(3).getValue());
		assertEquals("0", data.get(1).getValue());
		assertEquals("0", data.get(2).getValue());
		assertEquals("0", data.get(4).getValue());
		assertEquals("0", data.get(5).getValue());
		assertEquals("0", data.get(6).getValue());
		assertEquals("0", data.get(7).getValue());
		assertEquals("0", data.get(8).getValue());
		assertEquals("0", data.get(9).getValue());
	}
	
	@Test
	public void updateRecord_transfer() {
		WalletRecord transfer1 = walletRecordDAO.getById(3);
		WalletRecord transfer2 = walletRecordDAO.getById(4);
		RecordDTO recordBefore1 = new RecordDTO(transfer1);
		RecordDTO recordBefore2 = new RecordDTO(transfer2);
		
		assertEquals(0, recordBefore1.getAccountId());
		assertEquals(1443796800000L, recordBefore1.getDate());
		assertEquals(null, recordBefore1.getDescription());
		assertEquals(2, recordBefore1.getDestynationAccountId());
		assertEquals(1, recordBefore1.getSourceWalletAccountId());
		assertEquals(1445035106000L, recordBefore1.getUpdated());
		assertTrue(1000.0F == recordBefore1.getValue());
		assertTrue(recordBefore1.isTransfer());
		assertEquals(false, recordBefore1.isIncome());
		
		assertEquals(0, recordBefore2.getAccountId());
		assertEquals(1443796800000L, recordBefore2.getDate());
		assertEquals(null, recordBefore2.getDescription());
		assertEquals(2, recordBefore2.getDestynationAccountId());
		assertEquals(1, recordBefore2.getSourceWalletAccountId());
		assertEquals(1445035106000L, recordBefore2.getUpdated());
		assertTrue(1000.0F == recordBefore2.getValue());
		assertTrue(recordBefore2.isTransfer());
		assertEquals(true, recordBefore2.isIncome());
		
		recordBefore1.setDate(1444648390000L);
		recordBefore1.setDescription("update");
		recordBefore1.setDestynationAccountId(3);
		recordBefore1.setSourceWalletAccountId(1);
		recordBefore1.setValue(5000);
		
		ServiceResult result = walletRestSeriveHelper.updateRecord(recordBefore1);
		
		WalletRecord transferAfter1 = walletRecordDAO.getById(3);
		WalletRecord transferAfter2 = walletRecordDAO.getById(4);
		RecordDTO recordAfter1 = new RecordDTO(transferAfter1);
		RecordDTO recordAfter2 = new RecordDTO(transferAfter2);
		
		assertEquals(true, result.isSuccess());
		assertEquals(0, result.getMessages().size());

		assertEquals(recordBefore1.getAccountId(), recordAfter1.getAccountId());
		assertEquals(recordBefore1.getDate(), recordAfter1.getDate());
		assertEquals(recordBefore1.getDescription(), recordAfter1.getDescription());
		assertEquals(recordBefore1.getDestynationAccountId(), recordAfter1.getDestynationAccountId());
		assertEquals(recordBefore1.getId(), recordAfter1.getId());
		assertEquals(recordBefore1.getRecordTypeId(), recordAfter1.getRecordTypeId());
		assertEquals(recordBefore1.getSourceWalletAccountId(), recordAfter1.getSourceWalletAccountId());
		assertTrue(recordBefore1.getUpdated() < recordAfter1.getUpdated());
		assertEquals(recordBefore1.getValue(), recordAfter1.getValue(), 0);
		assertEquals(recordBefore1.isIncome(), recordAfter1.isIncome());
		assertEquals(recordBefore1.isTransfer(), recordAfter1.isTransfer());
		
		assertEquals(recordBefore2.getAccountId(), recordAfter2.getAccountId());
		assertEquals(1444648390000L, recordAfter2.getDate());
		assertEquals("update", recordAfter2.getDescription());
		assertEquals(3, recordAfter2.getDestynationAccountId());
		assertEquals(recordBefore2.getId(), recordAfter2.getId());
		assertEquals(recordBefore2.getRecordTypeId(), recordAfter2.getRecordTypeId());
		assertEquals(recordBefore2.getSourceWalletAccountId(), recordAfter2.getSourceWalletAccountId());
		assertTrue(recordBefore1.getUpdated() < recordAfter2.getUpdated());
		assertTrue(new Float(5000).equals(recordAfter2.getValue()));
		
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void transferToDataValueMap_perDay() throws ValidationException{
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
		
		List<RecordDTO> records = walletService.getRecordsByType(4, 1, false);
		
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
		assertEquals(3, result.size());
		assertTrue(result.containsKey("2015-09-02"));
		assertEquals(1, result.get("2015-09-02").size());
		assertEquals(new Integer(2), result.get("2015-09-02").get(0).getId());
		assertTrue(result.containsKey("2015-12-02"));
		assertEquals(2, result.get("2015-12-02").size());
		assertEquals(new Integer(11), result.get("2015-12-02").get(0).getId());
		assertEquals(new Integer(12), result.get("2015-12-02").get(1).getId());
		assertTrue(result.containsKey("2015-12-04"));
		assertEquals(1, result.get("2015-12-04").size());
		assertEquals(new Integer(10), result.get("2015-12-04").get(0).getId());
	}
	
	//*****************************************
	// Private methods
	//*****************************************
	
	@Test
	@SuppressWarnings("unchecked")
	public void transferToDataValueMap_perMonth() throws ValidationException{
		Map<String, List<RecordDTO>> result = null;
		Method method = null;
		try {
			method = WalletRestServiceHelper.class.getDeclaredMethod("transferToDataValueMap", List.class, SimpleDateFormat.class);
			method.setAccessible(true);
		} catch (NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
		List<RecordDTO> records = walletService.getRecordsByType(4, 1, false);
		
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
		assertEquals(3, result.get("2015-12").size());
		assertTrue(result.get("2015-12").contains(new RecordDTO(10)));
		assertTrue(result.get("2015-12").contains(new RecordDTO(11)));
		assertTrue(result.get("2015-12").contains(new RecordDTO(12)));
		assertTrue(result.containsKey("2015-09"));
		assertEquals(1, result.get("2015-09").size());
		assertTrue(result.get("2015-09").contains(new RecordDTO(2)));
	}
	
	@Test(expected = ValidationException.class)
	public void transferToDataValueMap_perMonth_failOnNoAccount() throws ValidationException{
		Method method = null;
		try {
			method = WalletRestServiceHelper.class.getDeclaredMethod("transferToDataValueMap", List.class, SimpleDateFormat.class);
			method.setAccessible(true);
		} catch (NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
		walletService.getRecordsByType(4, 10, false);
	}
	
	@Test(expected = ValidationException.class)
	public void transferToDataValueMap_perMonth_failOnNoType() throws ValidationException{
		Method method = null;
		try {
			method = WalletRestServiceHelper.class.getDeclaredMethod("transferToDataValueMap", List.class, SimpleDateFormat.class);
			method.setAccessible(true);
		} catch (NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
		walletService.getRecordsByType(40, 1, false);
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
