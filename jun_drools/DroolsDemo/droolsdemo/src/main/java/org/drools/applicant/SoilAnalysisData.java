package org.drools.applicant;

/**
 * 土壤分析数据
 * 
 * @author lixl
 * @Date 2016年11月21日下午4:08:12
 */
public class SoilAnalysisData {

	private AgroType agroType;// 土壤类型

	private String soilProfile;// 土壤剖面

	private String soilTexture;// 土壤质地

	private String ploughLayer;// 耕作层

	private SoilNutrient soilNutrient;// 土壤养分

	private Float phValue; // PH值

	private Double moisture;// 含水量

	private String landId;// 所属地块id

	public AgroType getAgroType() {
		return agroType;
	}

	public void setAgroType(AgroType agroType) {
		this.agroType = agroType;
	}

	public String getSoilProfile() {
		return soilProfile;
	}

	public void setSoilProfile(String soilProfile) {
		this.soilProfile = soilProfile;
	}

	public String getSoilTexture() {
		return soilTexture;
	}

	public void setSoilTexture(String soilTexture) {
		this.soilTexture = soilTexture;
	}

	public String getPloughLayer() {
		return ploughLayer;
	}

	public void setPloughLayer(String ploughLayer) {
		this.ploughLayer = ploughLayer;
	}

	public SoilNutrient getSoilNutrient() {
		return soilNutrient;
	}

	public void setSoilNutrient(SoilNutrient soilNutrient) {
		this.soilNutrient = soilNutrient;
	}

	public Float getPhValue() {
		return phValue;
	}

	public void setPhValue(Float phValue) {
		this.phValue = phValue;
	}

	public Double getMoisture() {
		return moisture;
	}

	public void setMoisture(Double moisture) {
		this.moisture = moisture;
	}

	public String getLandId() {
		return landId;
	}

	public void setLandId(String landId) {
		this.landId = landId;
	}

	public enum AgroType {
		SANDY_SOIL, // 砂质土
		CLAY_SOIL, // 黏质土
		LOAMY_SOIL// 壤土
	}

	public enum SoilNutrient {
		GEIN, // 土壤有机质
		NITROGEN, // 全氮
		PHOSPHORUS, // 全磷
		POTASSIUM, // 全钾
		AVAILABLE_NITROGEN, // 碱解氮
		RAPID_AVAILABLE_PHOSPHORUS, // 速效磷
		RAPID_AVAILABLE_POTASSIUM // 速效钾
	}
}
