package klg.db.info.model;

import java.util.Date;

public class Table {
	String TABLE_CATALOG;
	String TABLE_SCHEMA;
	String TABLE_NAME;
	String TABLE_TYPE;
	String ENGINE;
	long VERSION;
	String ROW_FORMAT;
	long TABLE_ROWS;
	long AVG_ROW_LENGTH;
	long DATA_LENGTH;
	long MAX_DATA_LENGTH;
	long INDEX_LENGTH;
	long DATA_FREE;
	long AUTO_INCREMENT;
	Date CREATE_TIME;
	Date UPDATE_TIME;
	Date CHECK_TIME;
	String TABLE_COLLATION;
	long CHECKSUM;
	String CREATE_OPTIONS;
	String TABLE_COMMENT;
	public String getTABLE_CATALOG() {
		return TABLE_CATALOG;
	}
	public void setTABLE_CATALOG(String tABLE_CATALOG) {
		TABLE_CATALOG = tABLE_CATALOG;
	}
	public String getTABLE_SCHEMA() {
		return TABLE_SCHEMA;
	}
	public void setTABLE_SCHEMA(String tABLE_SCHEMA) {
		TABLE_SCHEMA = tABLE_SCHEMA;
	}
	public String getTABLE_NAME() {
		return TABLE_NAME;
	}
	public void setTABLE_NAME(String tABLE_NAME) {
		TABLE_NAME = tABLE_NAME;
	}
	public String getTABLE_TYPE() {
		return TABLE_TYPE;
	}
	public void setTABLE_TYPE(String tABLE_TYPE) {
		TABLE_TYPE = tABLE_TYPE;
	}
	public String getENGINE() {
		return ENGINE;
	}
	public void setENGINE(String eNGINE) {
		ENGINE = eNGINE;
	}
	public long getVERSION() {
		return VERSION;
	}
	public void setVERSION(long vERSION) {
		VERSION = vERSION;
	}
	public String getROW_FORMAT() {
		return ROW_FORMAT;
	}
	public void setROW_FORMAT(String rOW_FORMAT) {
		ROW_FORMAT = rOW_FORMAT;
	}
	public long getTABLE_ROWS() {
		return TABLE_ROWS;
	}
	public void setTABLE_ROWS(long tABLE_ROWS) {
		TABLE_ROWS = tABLE_ROWS;
	}
	public long getAVG_ROW_LENGTH() {
		return AVG_ROW_LENGTH;
	}
	public void setAVG_ROW_LENGTH(long aVG_ROW_LENGTH) {
		AVG_ROW_LENGTH = aVG_ROW_LENGTH;
	}
	public long getDATA_LENGTH() {
		return DATA_LENGTH;
	}
	public void setDATA_LENGTH(long dATA_LENGTH) {
		DATA_LENGTH = dATA_LENGTH;
	}
	public long getMAX_DATA_LENGTH() {
		return MAX_DATA_LENGTH;
	}
	public void setMAX_DATA_LENGTH(long mAX_DATA_LENGTH) {
		MAX_DATA_LENGTH = mAX_DATA_LENGTH;
	}
	public long getINDEX_LENGTH() {
		return INDEX_LENGTH;
	}
	public void setINDEX_LENGTH(long iNDEX_LENGTH) {
		INDEX_LENGTH = iNDEX_LENGTH;
	}
	public long getDATA_FREE() {
		return DATA_FREE;
	}
	public void setDATA_FREE(long dATA_FREE) {
		DATA_FREE = dATA_FREE;
	}
	public long getAUTO_INCREMENT() {
		return AUTO_INCREMENT;
	}
	public void setAUTO_INCREMENT(long aUTO_INCREMENT) {
		AUTO_INCREMENT = aUTO_INCREMENT;
	}
	public Date getCREATE_TIME() {
		return CREATE_TIME;
	}
	public void setCREATE_TIME(Date cREATE_TIME) {
		CREATE_TIME = cREATE_TIME;
	}
	public Date getUPDATE_TIME() {
		return UPDATE_TIME;
	}
	public void setUPDATE_TIME(Date uPDATE_TIME) {
		UPDATE_TIME = uPDATE_TIME;
	}
	public Date getCHECK_TIME() {
		return CHECK_TIME;
	}
	public void setCHECK_TIME(Date cHECK_TIME) {
		CHECK_TIME = cHECK_TIME;
	}
	public String getTABLE_COLLATION() {
		return TABLE_COLLATION;
	}
	public void setTABLE_COLLATION(String tABLE_COLLATION) {
		TABLE_COLLATION = tABLE_COLLATION;
	}
	public long getCHECKSUM() {
		return CHECKSUM;
	}
	public void setCHECKSUM(long cHECKSUM) {
		CHECKSUM = cHECKSUM;
	}
	public String getCREATE_OPTIONS() {
		return CREATE_OPTIONS;
	}
	public void setCREATE_OPTIONS(String cREATE_OPTIONS) {
		CREATE_OPTIONS = cREATE_OPTIONS;
	}
	public String getTABLE_COMMENT() {
		return TABLE_COMMENT;
	}
	public void setTABLE_COMMENT(String tABLE_COMMENT) {
		TABLE_COMMENT = tABLE_COMMENT;
	}
	
	
	
}
