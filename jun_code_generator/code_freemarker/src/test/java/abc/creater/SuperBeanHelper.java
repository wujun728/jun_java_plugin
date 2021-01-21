package abc.creater;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class SuperBeanHelper<T>
{
  private final Class<T> clazz;

  public SuperBeanHelper(Class<T> clazz)
  {
    this.clazz = clazz;
  }

  public List<String> getPropertyName(Object obj) {
    List<String> list = new ArrayList<String>();
    Field[] fs = obj.getClass().getDeclaredFields();
    for (Field f : fs) {
      list.add(f.getName());
    }
    return list;
  }

  public List<T> convert(List<Map<String, Object>> res)
  {
    List list = new ArrayList<String>();
    try {
      for (Map<String, Object> m : res) {
        Object tempClass = this.clazz.newInstance();
        List propertyName = getPropertyName(tempClass);
        for (int i = 0; i < propertyName.size(); i++) {
          Field f = tempClass.getClass().getDeclaredField(
            ((String)propertyName.get(i)).toString());
          f.setAccessible(true);
          try {
            if (f.getType().getName().equals("int")) {
              f.set(tempClass, Integer.valueOf(Integer.parseInt(m.get(((String)propertyName.get(i)).toString().trim()).toString())));
            } else if (f.getType().getName().equals("java.util.Date")) {
              SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
              Date d = formatter.parse(m.get(((String)propertyName.get(i)).toString().trim()).toString());
              f.set(tempClass, d);
            } else {
              f.set(tempClass, m.get(((String)propertyName.get(i)).toString().trim()));
            }
          } catch (Exception e) {
            System.out.println(e.getMessage());
          }
        }
        list.add(tempClass);
      }
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
    return list;
  }
}