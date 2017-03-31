package com.timeron.nexus.apps.wallet.service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.timeron.NexusDatabaseLibrary.Entity.WalletType;
import com.timeron.NexusDatabaseLibrary.dao.WalletTypeDAO;
import com.timeron.NexusDatabaseLibrary.dto.IdOrderDTO;
import com.timeron.nexus.apps.wallet.constant.ResultMessagesWallet;
import com.timeron.nexus.apps.wallet.exception.ValidationException;
import com.timeron.nexus.apps.wallet.service.dto.RecordTypeDTO;
import com.timeron.nexus.apps.wallet.service.dto.RecordTypeListDTO;
import com.timeron.nexus.apps.wallet.validator.TypeValidator;
import com.timeron.nexus.common.service.ServiceResult;

@Component
public class WalletTypeService {

	@Autowired
	private WalletTypeDAO walletTypeDAO;

	static Logger LOG = Logger.getLogger(WalletTypeService.class);

	public Object getById(int parentId) {
		walletTypeDAO.getById(parentId);
		return null;
	}

	public ServiceResult addNewType(RecordTypeDTO typeDTO) {
		ServiceResult result = new ServiceResult();
		TypeValidator validator = new TypeValidator();
		
		WalletType type = new WalletType();
		type.setColor(typeDTO.getColor());
		type.setDefaultValue(typeDTO.getDefaultValue());
		type.setIcon(typeDTO.getIcon());
		type.setName(typeDTO.getName());
		type.setParentType(walletTypeDAO.getById(typeDTO.getParentId()));
		type.setTimestamp(new Date());
		type.setUpdated(new Date());
		try {
			if (typeDTO.getParentId() != null) {
				result = validator.validateNewTypeWithParent(type, result);
			} else {
				result = validator.validateNewType(type, result);
			}
			if (result.isSuccess()) {
				walletTypeDAO.save(type);
				result.addMessage(ResultMessagesWallet.RECORD_ADDED);
			}

		} catch (Exception e) {
			result.addError(ResultMessagesWallet.DATABASE_ISSUE);
			LOG.error(e.getMessage());
		}
		return result;
	}

	public ServiceResult updateTypes(RecordTypeListDTO typeListDTO) {
		ServiceResult result = new ServiceResult();
		TypeValidator validator = new TypeValidator();
		List<RecordTypeDTO> uTypes = new ArrayList<RecordTypeDTO>();
		WalletType walletType;
		WalletType walletTypeParent;
		for (RecordTypeDTO type : typeListDTO.getTypes()) {
			walletType = walletTypeDAO.getById(type.getId());
			walletTypeParent = walletTypeDAO.getById(type.getParentId());
			if(walletType == null){
				walletType = new WalletType();
			}
			walletType.setParentType(walletTypeParent);
			walletType.setColor(type.getColor());
			walletType.setDefaultValue(type.getDefaultValue());
			walletType.setIcon(type.getIcon());
			walletType.setName(type.getName());
			walletType.setUpdated(new Date());
			
			if(type.getParentId() != null){
				result = validator.validateUpdateTypeWithParent(walletType, result);
			}else{
				result = validator.validateUpdateType(walletType, result);
			}
			if(result.isSuccess()){
				walletTypeDAO.update(walletType);
			}
		}
		RecordTypeDTO uType;
		for (WalletType wType : walletTypeDAO.getAll()) {
			uType = new RecordTypeDTO(wType);
			uTypes.add(uType);
		}
		result.setObject(uTypes);
		return result;
	}

	/**
	 * Show which types can be set as parentType
	 * @param principal
	 * @return
	 */
	public List<RecordTypeDTO> getTypesValidForParent(Principal principal) {
		List<WalletType> walletTypes = walletTypeDAO.getAllTypesAvailableForParent();
		List<RecordTypeDTO> recordTypeDTOs = new ArrayList<RecordTypeDTO>();
		RecordTypeDTO typeDTO;
		for (WalletType type : walletTypes) {
			typeDTO = new RecordTypeDTO(type);
			recordTypeDTOs.add(typeDTO);
		}
		return recordTypeDTOs;
	}

	public List<RecordTypeDTO> getAllRecordTypes() {
		List<RecordTypeDTO> recordTypeDTOs = new ArrayList<RecordTypeDTO>();
		List<WalletType> walletTypes = walletTypeDAO.getAll();
		List<IdOrderDTO> typeOrder = walletTypeDAO.getIdOrder();
		for (WalletType type : walletTypes) {
			RecordTypeDTO recordTypeDTO = new RecordTypeDTO();
			recordTypeDTO.setId(type.getId());
			recordTypeDTO.setColor(type.getColor());
			recordTypeDTO.setDefaultValue(type.getDefaultValue());
			recordTypeDTO.setIcon(type.getIcon());
			recordTypeDTO.setName(type.getName());
			recordTypeDTO.setTimestamp(type.getTimestamp());
			recordTypeDTO.setUpdated(type.getUpdated());
			if (type.getParentType() != null) {
				recordTypeDTO.setParentId(type.getParentType().getId());
			}
			recordTypeDTOs.add(recordTypeDTO);
		}
		recordTypeDTOs = getTypeByOrder(recordTypeDTOs, typeOrder);
		return recordTypeDTOs;
	}

	private List<RecordTypeDTO> getTypeByOrder(
			List<RecordTypeDTO> recordTypeDTOs, List<IdOrderDTO> typeOrder) {
		List<RecordTypeDTO> typeByOrderDTOs = new ArrayList<RecordTypeDTO>();
		List<RecordTypeDTO> typeWithoutSortOrder = new ArrayList<RecordTypeDTO>();
		boolean added = false;
		for (RecordTypeDTO record : recordTypeDTOs) {
			for (IdOrderDTO entry : typeOrder) {
				if (entry.getId() == record.getId()) {
					typeByOrderDTOs.add(record);
					added = true;
				}
			}
			if (!added) {
				typeWithoutSortOrder.add(record);
			}
			added = false;
		}
		typeByOrderDTOs.addAll(typeWithoutSortOrder);
		return typeByOrderDTOs;
	}

}
