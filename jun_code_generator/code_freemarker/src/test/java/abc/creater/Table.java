package abc.creater;

public class Table
{
  private String Field;
  private String Type;
  private String Null;
  private String Key;
  private String Default;
  private String Extra;

  public String getField()
  {
    return this.Field;
  }

  public void setField(String field) {
    this.Field = field;
  }

  public String getType() {
    return this.Type;
  }

  public void setType(String type) {
    this.Type = type;
  }

  public String getNull() {
    return this.Null;
  }

  public void setNull(String null1) {
    this.Null = null1;
  }

  public String getKey() {
    return this.Key;
  }

  public void setKey(String key) {
    this.Key = key;
  }

  public String getDefault() {
    return this.Default;
  }

  public void setDefault(String default1) {
    this.Default = default1;
  }

  public String getExtra() {
    return this.Extra;
  }

  public void setExtra(String extra) {
    this.Extra = extra;
  }
}
