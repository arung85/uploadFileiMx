package com.imatrix.files.upload.db.model;

import java.sql.Date;

public class FileDTO {

	private String Referance;
	private String PartyName;
	private String FileDescription;
	private String FileName;
	private String DocType;
	private String VoucherType;
	private String OrderNo;
	private String VoucherNo;
	private String comGuid;
	private String guid;
	private int activeStatus;
	private Date updatedDt;
	private String Type;
	
	public FileDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getReferance() {
		return Referance;
	}
	public void setReferance(String referance) {
		Referance = referance;
	}
	public String getPartyName() {
		return PartyName;
	}
	public void setPartyName(String partyName) {
		PartyName = partyName;
	}
	public String getFileDescription() {
		return FileDescription;
	}
	public void setFileDescription(String fileDescription) {
		FileDescription = fileDescription;
	}
	public String getFileName() {
		return FileName;
	}
	public void setFileName(String fileName) {
		FileName = fileName;
	}
	public String getDocType() {
		return DocType;
	}
	public void setDocType(String docType) {
		DocType = docType;
	}
	public String getVoucherType() {
		return VoucherType;
	}
	public void setVoucherType(String voucherType) {
		VoucherType = voucherType;
	}
	public String getOrderNo() {
		return OrderNo;
	}
	public void setOrderNo(String orderNo) {
		OrderNo = orderNo;
	}
	public String getVoucherNo() {
		return VoucherNo;
	}
	public void setVoucherNo(String voucherNo) {
		VoucherNo = voucherNo;
	}
	public String getComGuid() {
		return comGuid;
	}
	public void setComGuid(String comGuid) {
		this.comGuid = comGuid;
	}
	public String getGuid() {
		return guid;
	}
	public void setGuid(String guid) {
		this.guid = guid;
	}
	public int getActiveStatus() {
		return activeStatus;
	}
	public void setActiveStatus(int activeStatus) {
		this.activeStatus = activeStatus;
	}
	public Date getUpdatedDt() {
		return updatedDt;
	}
	public void setUpdatedDt(Date updatedDt) {
		this.updatedDt = updatedDt;
	}
	public String getType() {
		return Type;
	}
	public void setType(String type) {
		Type = type;
	}
	public FileDTO(String referance, String partyName, String fileDescription, String fileName, String docType,
			String voucherType, String orderNo, String voucherNo, String comGuid, String guid, int activeStatus,
			Date updatedDt, String type) {
		super();
		Referance = referance;
		PartyName = partyName;
		FileDescription = fileDescription;
		FileName = fileName;
		DocType = docType;
		VoucherType = voucherType;
		OrderNo = orderNo;
		VoucherNo = voucherNo;
		this.comGuid = comGuid;
		this.guid = guid;
		this.activeStatus = activeStatus;
		this.updatedDt = updatedDt;
		Type = type;
	}
	@Override
	public String toString() {
		return "FileDTO [Referance=" + Referance + ", PartyName=" + PartyName + ", FileDescription=" + FileDescription
				+ ", FileName=" + FileName + ", DocType=" + DocType + ", VoucherType=" + VoucherType + ", OrderNo="
				+ OrderNo + ", VoucherNo=" + VoucherNo + ", comGuid=" + comGuid + ", guid=" + guid + ", activeStatus="
				+ activeStatus + ", updatedDt=" + updatedDt + ", Type=" + Type + "]";
	}
	
}
