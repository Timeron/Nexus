package com.nexus.controller.wallet;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.nexus.controller.wallet.form.WalletAccountForm;
import com.nexus.controller.wallet.form.WalletAddAccountForm;
import com.nexus.controller.wallet.form.WalletAddAccountResultForm;
import com.nexus.controller.wallet.form.WalletAddRecordForm;
import com.nexus.controller.wallet.form.WalletAddRecordResultForm;
import com.nexus.controller.wallet.form.WalletAddTypeForm;
import com.nexus.controller.wallet.form.WalletAddTypeResultForm;
import com.nexus.controller.wallet.form.WalletEditRecordForm;
import com.nexus.controller.wallet.form.WalletMainSiteForm;
import com.nexus.controller.wallet.form.WalletRemoveRecordForm;
import com.nexus.dao.Implementation.WalletAccountDAO;
import com.nexus.dao.Implementation.WalletDAO;
import com.nexus.dao.Implementation.WalletRecordDAO;
import com.nexus.dao.Implementation.WalletTypeDAO;
import com.nexus.dao.entity.WalletAccount;
import com.nexus.dao.entity.WalletRecord;
import com.nexus.dao.entity.WalletType;

@Controller
@RequestMapping("/wallet")
public class WalletController {

	@Autowired
	private WalletDAO walletDAO;
	@Autowired
	private WalletRecordDAO walletRecordDAO;
	@Autowired
	private WalletTypeDAO walletTypeDAO;
	@Autowired
	private WalletAccountDAO walletAccountDAO;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String walletMainSite(ModelMap model){
		WalletMainSiteForm walletMainSiteForm = new WalletMainSiteForm();
		List<Float> recordsValue = new ArrayList<Float>();
		
		walletMainSiteForm.setAccounts(walletDAO.getAll());
		walletMainSiteForm.setRecords(walletRecordDAO.getAll());
		for(WalletRecord walletRecord : walletMainSiteForm.getRecords() ){
			recordsValue.add(walletRecord.getValue());
		}
		walletMainSiteForm.setRecordsValue(recordsValue);
		
		model.addAttribute("form", walletMainSiteForm);

		return "walletMainSite";
	}
	
	@RequestMapping(value = "/addAccount")
	public String walletAddAccount(ModelMap model){
		WalletAddAccountForm walletAddAccountForm = new WalletAddAccountForm();
		
		model.addAttribute("form", walletAddAccountForm);

		return "walletAddAccount";
	}
	
	@RequestMapping(value = "/addAccountResult", method = RequestMethod.POST)
	public String walletAddAccountResult(ModelMap model, @ModelAttribute("form") WalletAddAccountForm walletAddAccountForm){
		WalletAddAccountResultForm walletAddAccountResultForm = new WalletAddAccountResultForm();
		
		walletAddAccountForm.getWalletAccount().setTimestamp(new Date());
		walletDAO.save(walletAddAccountForm.getWalletAccount());
		
		model.addAttribute("form", walletAddAccountResultForm);

		return "walletAddAccountResult";
	}
	
	@RequestMapping(value = "/walletAccout", method = RequestMethod.GET)
	public String walletAccount(ModelMap model, HttpServletRequest request, HttpServletResponse response){
		WalletAccountForm walletAccountForm = new WalletAccountForm();
		WalletAccount currentAccount = walletDAO.getById(Integer.parseInt(request.getParameter("id")));
		
		walletAccountForm.setWalletAccount(currentAccount);
		walletAccountForm.setWalletRecords(walletRecordDAO.getRecordsFromAccount(currentAccount));
		
		model.addAttribute("form", walletAccountForm);

		return "walletAccout";
	}
	
	@RequestMapping(value = "/addType")
	public String walletAddType(ModelMap model){
		WalletAddTypeForm walletAddTypetForm = new WalletAddTypeForm();
		
		walletAddTypetForm.setWalletType(new WalletType());
		walletAddTypetForm.setWalletTypes(walletTypeDAO.getAll());
		
		model.addAttribute("form", walletAddTypetForm);

		return "walletAddType";
	}
	
	@RequestMapping(value = "/addTypeResult", method = RequestMethod.POST)
	public String walletAddTypeResult(ModelMap model, @ModelAttribute("form") WalletAddTypeForm walletAddTypeForm){
		WalletAddTypeResultForm walletAddTypetResultForm = new WalletAddTypeResultForm();
		
		WalletType newWalletType = walletAddTypeForm.getWalletType();
		newWalletType.setTimestamp(new Date());
		if(walletAddTypeForm.getWalletTypeId()!=null){
			newWalletType.setParentType(walletTypeDAO.getById(walletAddTypeForm.getWalletTypeId()));
		}
		
		walletTypeDAO.save(walletAddTypeForm.getWalletType());
		
		model.addAttribute("form", walletAddTypetResultForm);

		return "walletAddTypeResult";
	}
	
	@RequestMapping(value = "/addRecord", method = RequestMethod.GET)
	public String walletAddRecord(ModelMap model, HttpServletRequest request, HttpServletResponse response){
		WalletAddRecordForm walletAddRecordForm = new WalletAddRecordForm();
		
		List<WalletAccount> otherWallet = walletDAO.getAll();
		Iterator<WalletAccount> otherWalletIterator = otherWallet.iterator();
		while(otherWalletIterator.hasNext()){
			WalletAccount wallet = otherWalletIterator.next();
			if(Integer.parseInt(request.getParameter("id")) == wallet.getId()){
				otherWalletIterator.remove();
			}
		}
		
		walletAddRecordForm.setWalletAccountId(Integer.parseInt(request.getParameter("id")));
		walletAddRecordForm.setWalletRecord(new WalletRecord());
		walletAddRecordForm.setWalletTypes(walletTypeDAO.getAll());
		walletAddRecordForm.setWalletAccounts(otherWallet);
		
		model.addAttribute("form", walletAddRecordForm);

		return "walletAddRecord";
	}
	

	@RequestMapping(value = "/walletAddRecordResult", method = RequestMethod.POST)
	public String walletAddRecordResult(ModelMap model, @ModelAttribute("form") WalletAddRecordForm walletAddRecordForm){
		WalletAddRecordResultForm walletAddRecordResultForm = new WalletAddRecordResultForm();
		WalletRecord newWalletRecord = new WalletRecord();
		float value = 0;
		
		if(walletAddRecordForm.getWalletRecord().getId()!=null){
			newWalletRecord.setId(walletAddRecordForm.getWalletRecord().getId());
		}
		
		value = walletAddRecordForm.getWalletRecord().getValue();
		if(!walletAddRecordForm.getWalletRecord().isIncome()){
			if(value > 0){
				value *= -1;
			}
		}else if(value <= 0){
			value *= -1;
		}
		
		newWalletRecord.setWalletAccount(walletAccountDAO.getById(walletAddRecordForm.getWalletAccountId()));
		newWalletRecord.setValue(value);
		newWalletRecord.setTransfer(walletAddRecordForm.getWalletRecord().isTransfer());
		newWalletRecord.setDescription(walletAddRecordForm.getWalletRecord().getDescription());
		newWalletRecord.setDate(walletAddRecordForm.getWalletRecord().getDate());
		
		if(newWalletRecord.isTransfer()){
			if(walletAddRecordForm.getDestinationAccountId()!=null){
				newWalletRecord.setDestinationWalletAccount(walletAccountDAO.getById(walletAddRecordForm.getDestinationAccountId()));
				newWalletRecord.setIncome(false);
			}
		}else{
			if(walletAddRecordForm.getWalletTypeId()!=null){
				WalletType newWalletType = walletTypeDAO.getById(walletAddRecordForm.getWalletTypeId());
				newWalletRecord.setWalletType(newWalletType);
			}
			newWalletRecord.setIncome(walletAddRecordForm.getWalletRecord().isIncome());
		}
		if(walletAddRecordForm.getWalletRecord().getId()==null){
			walletRecordDAO.save(newWalletRecord);
		}else{
			walletRecordDAO.update(newWalletRecord);
		}
		
		model.addAttribute("form", walletAddRecordResultForm);

		return "walletAddRecordResult";
	}
	
	
	@RequestMapping(value = "/walletEditRecord", method = RequestMethod.GET)
	public String walletEditRecord(ModelMap model, HttpServletRequest request, HttpServletResponse response){
		WalletEditRecordForm walletEditRecordForm = new WalletEditRecordForm();
		
		walletEditRecordForm.setWalletAccount(walletDAO.getAll());
		walletEditRecordForm.setWalletRecord(walletRecordDAO.getById(Integer.parseInt(request.getParameter("id"))));
		
		model.addAttribute("form", walletEditRecordForm);

		return "walletEditRecord";
	}
	
	@RequestMapping(value = "/walletRemoveRecord", method = RequestMethod.GET)
	public String walletRemoveRecord(ModelMap model, HttpServletRequest request, HttpServletResponse response){

		walletRecordDAO.removeById(Integer.parseInt(request.getParameter("id")));
		
		if(request.getParameter("account")!=null){
			WalletAccountForm walletAccountForm = new WalletAccountForm();
			WalletAccount currentAccount = walletDAO.getById(Integer.parseInt(request.getParameter("account")));
			
			walletAccountForm.setWalletAccount(currentAccount);
			walletAccountForm.setWalletRecords(walletRecordDAO.getRecordsFromAccount(currentAccount));
			
			model.addAttribute("form", walletAccountForm);

			return "walletAccout";
		}else{
			WalletMainSiteForm walletMainSiteForm = new WalletMainSiteForm();
			
			walletMainSiteForm.setAccounts(walletDAO.getAll());
			walletMainSiteForm.setRecords(walletRecordDAO.getAll());
			
			model.addAttribute("form", walletMainSiteForm);

			return "walletMainSite";
		}
	}
}