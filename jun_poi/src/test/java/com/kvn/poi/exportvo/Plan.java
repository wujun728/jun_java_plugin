package com.kvn.poi.exportvo;

import java.math.BigDecimal;
import java.util.Date;

/**
* @author wzy
* @date 2017年7月7日 上午11:41:25
*/
public class Plan {
	private String projectName;
	private String projectShortName;
	private String contractCode;
	private Date startDate;
	private Date endDate;
	private int planTimes;
	private String timesDesc;
	private long trustPlanScale;
	private BigDecimal trustPlanShare;
	private BigDecimal beneficiaryTrustShare;
	private Integer termValue;
	private double price;
	private int lowestRate;
	private int higtestRate;
	private BigDecimal contractAmount;
	private Date signContractDate;
	private String contractProvince;
	private String fundsRegionProvince;
	private String contractCity;
	private String foundedConditionDesc;
	private String fundsRegionCity;
	private String fundsUsedDesc;
	private String custodianBankName;
	private String custodianBankOrgCode;
	private String advisorName;
	private String advisorOrgCode;
	private String propertyAccountName;
	private String propertyAccount;
	private String propertyAccountBankName;
	private String remark1;
	private String remark2;
	private String bailorName;
	private String bailorCardCode;
	private String bailorFaxCode;
	private String bailorMobile;
	private String bailorEmail;
	private String bailorPostCode;
	private String bailorAddress;
	private String beneficiaryName;
	private String beneficiaryCardCode;
	private String beneficiaryMobile;
	private String beneficiaryFaxCode;
	private String beneficiaryEmail;
	private String beneficiaryPostCode;
	private String beneficiaryAddress;
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getProjectShortName() {
		return projectShortName;
	}
	public void setProjectShortName(String projectShortName) {
		this.projectShortName = projectShortName;
	}
	public String getContractCode() {
		return contractCode;
	}
	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public int getPlanTimes() {
		return planTimes;
	}
	public void setPlanTimes(int planTimes) {
		this.planTimes = planTimes;
	}
	public String getTimesDesc() {
		return timesDesc;
	}
	public void setTimesDesc(String timesDesc) {
		this.timesDesc = timesDesc;
	}
	public long getTrustPlanScale() {
		return trustPlanScale;
	}
	public void setTrustPlanScale(long trustPlanScale) {
		this.trustPlanScale = trustPlanScale;
	}
	public BigDecimal getTrustPlanShare() {
		return trustPlanShare;
	}
	public void setTrustPlanShare(BigDecimal trustPlanShare) {
		this.trustPlanShare = trustPlanShare;
	}
	public Integer getTermValue() {
		return termValue;
	}
	public void setTermValue(Integer termValue) {
		this.termValue = termValue;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public int getLowestRate() {
		return lowestRate;
	}
	public void setLowestRate(int lowestRate) {
		this.lowestRate = lowestRate;
	}
	public int getHigtestRate() {
		return higtestRate;
	}
	public void setHigtestRate(int higtestRate) {
		this.higtestRate = higtestRate;
	}
	public BigDecimal getContractAmount() {
		return contractAmount;
	}
	public void setContractAmount(BigDecimal contractAmount) {
		this.contractAmount = contractAmount;
	}
	public Date getSignContractDate() {
		return signContractDate;
	}
	public void setSignContractDate(Date signContractDate) {
		this.signContractDate = signContractDate;
	}
	public String getContractProvince() {
		return contractProvince;
	}
	public void setContractProvince(String contractProvince) {
		this.contractProvince = contractProvince;
	}
	public String getContractCity() {
		return contractCity;
	}
	public void setContractCity(String contractCity) {
		this.contractCity = contractCity;
	}
	public String getFoundedConditionDesc() {
		return foundedConditionDesc;
	}
	public BigDecimal getBeneficiaryTrustShare() {
		return beneficiaryTrustShare;
	}
	public void setBeneficiaryTrustShare(BigDecimal beneficiaryTrustShare) {
		this.beneficiaryTrustShare = beneficiaryTrustShare;
	}
	public String getFundsRegionProvince() {
		return fundsRegionProvince;
	}
	public void setFundsRegionProvince(String fundsRegionProvince) {
		this.fundsRegionProvince = fundsRegionProvince;
	}
	public void setFoundedConditionDesc(String foundedConditionDesc) {
		this.foundedConditionDesc = foundedConditionDesc;
	}
	public String getFundsRegionCity() {
		return fundsRegionCity;
	}
	public void setFundsRegionCity(String fundsRegionCity) {
		this.fundsRegionCity = fundsRegionCity;
	}
	public String getFundsUsedDesc() {
		return fundsUsedDesc;
	}
	public void setFundsUsedDesc(String fundsUsedDesc) {
		this.fundsUsedDesc = fundsUsedDesc;
	}
	public String getCustodianBankName() {
		return custodianBankName;
	}
	public void setCustodianBankName(String custodianBankName) {
		this.custodianBankName = custodianBankName;
	}
	public String getCustodianBankOrgCode() {
		return custodianBankOrgCode;
	}
	public void setCustodianBankOrgCode(String custodianBankOrgCode) {
		this.custodianBankOrgCode = custodianBankOrgCode;
	}
	public String getAdvisorName() {
		return advisorName;
	}
	public void setAdvisorName(String advisorName) {
		this.advisorName = advisorName;
	}
	public String getAdvisorOrgCode() {
		return advisorOrgCode;
	}
	public void setAdvisorOrgCode(String advisorOrgCode) {
		this.advisorOrgCode = advisorOrgCode;
	}
	public String getPropertyAccountName() {
		return propertyAccountName;
	}
	public void setPropertyAccountName(String propertyAccountName) {
		this.propertyAccountName = propertyAccountName;
	}
	public String getPropertyAccount() {
		return propertyAccount;
	}
	public void setPropertyAccount(String propertyAccount) {
		this.propertyAccount = propertyAccount;
	}
	public String getPropertyAccountBankName() {
		return propertyAccountBankName;
	}
	public void setPropertyAccountBankName(String propertyAccountBankName) {
		this.propertyAccountBankName = propertyAccountBankName;
	}
	public String getRemark1() {
		return remark1;
	}
	public void setRemark1(String remark1) {
		this.remark1 = remark1;
	}
	public String getRemark2() {
		return remark2;
	}
	public void setRemark2(String remark2) {
		this.remark2 = remark2;
	}
	public String getBailorName() {
		return bailorName;
	}
	public void setBailorName(String bailorName) {
		this.bailorName = bailorName;
	}
	public String getBailorCardCode() {
		return bailorCardCode;
	}
	public void setBailorCardCode(String bailorCardCode) {
		this.bailorCardCode = bailorCardCode;
	}
	public String getBailorFaxCode() {
		return bailorFaxCode;
	}
	public void setBailorFaxCode(String bailorFaxCode) {
		this.bailorFaxCode = bailorFaxCode;
	}
	public String getBailorMobile() {
		return bailorMobile;
	}
	public void setBailorMobile(String bailorMobile) {
		this.bailorMobile = bailorMobile;
	}
	public String getBailorEmail() {
		return bailorEmail;
	}
	public void setBailorEmail(String bailorEmail) {
		this.bailorEmail = bailorEmail;
	}
	public String getBailorPostCode() {
		return bailorPostCode;
	}
	public void setBailorPostCode(String bailorPostCode) {
		this.bailorPostCode = bailorPostCode;
	}
	public String getBailorAddress() {
		return bailorAddress;
	}
	public void setBailorAddress(String bailorAddress) {
		this.bailorAddress = bailorAddress;
	}
	public String getBeneficiaryName() {
		return beneficiaryName;
	}
	public void setBeneficiaryName(String beneficiaryName) {
		this.beneficiaryName = beneficiaryName;
	}
	public String getBeneficiaryCardCode() {
		return beneficiaryCardCode;
	}
	public void setBeneficiaryCardCode(String beneficiaryCardCode) {
		this.beneficiaryCardCode = beneficiaryCardCode;
	}
	public String getBeneficiaryMobile() {
		return beneficiaryMobile;
	}
	public void setBeneficiaryMobile(String beneficiaryMobile) {
		this.beneficiaryMobile = beneficiaryMobile;
	}
	public String getBeneficiaryFaxCode() {
		return beneficiaryFaxCode;
	}
	public void setBeneficiaryFaxCode(String beneficiaryFaxCode) {
		this.beneficiaryFaxCode = beneficiaryFaxCode;
	}
	public String getBeneficiaryEmail() {
		return beneficiaryEmail;
	}
	public void setBeneficiaryEmail(String beneficiaryEmail) {
		this.beneficiaryEmail = beneficiaryEmail;
	}
	public String getBeneficiaryPostCode() {
		return beneficiaryPostCode;
	}
	public void setBeneficiaryPostCode(String beneficiaryPostCode) {
		this.beneficiaryPostCode = beneficiaryPostCode;
	}
	public String getBeneficiaryAddress() {
		return beneficiaryAddress;
	}
	public void setBeneficiaryAddress(String beneficiaryAddress) {
		this.beneficiaryAddress = beneficiaryAddress;
	}
}
