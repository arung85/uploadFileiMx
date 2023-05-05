package com.imatrix.files.upload.db.model;


import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.lang.NonNull;

@Entity
@Table(name = "tblfile")
public class FileDB {
	@Id
	@GeneratedValue
	private Long ID;
	@NonNull
	@Column(length=345)
	private String Referance;
	@Column(length=95)
	private String PartyName;
	@Column(length=105)
	private String FileDescription;
	@Column(length=145)
	private String FileName;
	@Column(length=45)
	private String DocType;
	private String Type;
	@Lob
	private byte[] file;
	@Column(length=145)
	private String VoucherType;
	@Column(length=145)
	private String OrderNo;
	@Column(length=145)
	private String VoucherNo;
	@Column(length=145)
	private String comGuid;
	@Column(length=145)
	private String guid;
	@CreationTimestamp
	private Timestamp createdDt;
	@Column(length=1)	
	private int activeStatus;
	@UpdateTimestamp
	private Timestamp updatedDt;
	public FileDB() {
	}

	public FileDB(String referance, String partyName, String fileDescription, String fileName, String docType,
			String type, byte[] file, String voucherType, String orderNo, String voucherNo, String comGuid, String guid,
			int activeStatus) {
		super();
		Referance = referance;
		PartyName = partyName;
		FileDescription = fileDescription;
		FileName = fileName;
		DocType = docType;
		Type = type;
		this.file = file;
		VoucherType = voucherType;
		OrderNo = orderNo;
		VoucherNo = voucherNo;
		this.comGuid = comGuid;
		this.guid = guid;
		this.activeStatus = activeStatus;
	}

	public Long getID() {
		return ID;
	}

	public void setID(Long iD) {
		ID = iD;
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

	public byte[] getFile() {
		return file;
	}

	public void setFile(byte[] file) {
		this.file = file;
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
	public String getType() {
		return Type;
	}

	public void setType(String type) {
		Type = type;
	}

	public Timestamp getCreatedDt() {
		return createdDt;
	}

	public void setCreatedDt(Timestamp createdDt) {
		this.createdDt = createdDt;
	}

	public Timestamp getUpdatedDt() {
		return updatedDt;
	}

	public void setUpdatedDt(Timestamp updatedDt) {
		this.updatedDt = updatedDt;
	}
	


}
