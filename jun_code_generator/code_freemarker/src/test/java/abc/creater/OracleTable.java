package abc.creater;

public class OracleTable
{
  private String COLUMN_NAME;
  private String DATA_TYPE;

  public String getDATA_TYPE()
  {
    return this.DATA_TYPE;
  }

  public void setDATA_TYPE(String dATA_TYPE) {
    this.DATA_TYPE = dATA_TYPE;
  }

  public String getCOLUMN_NAME() {
    return this.COLUMN_NAME;
  }

  public void setCOLUMN_NAME(String cOLUMN_NAME) {
    this.COLUMN_NAME = cOLUMN_NAME;
  }
}
