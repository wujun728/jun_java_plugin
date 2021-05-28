package com.itstyle.jpa.service;

import java.util.List;
import java.util.Map;

import com.itstyle.jpa.model.Student;
public interface IStudentService {
	 /**
	  * 返回List<Object[]>
	  * @author Wujun
	  * @return  List<Object[]>
	  * @Date	2018年3月28日
	  * 更新日志
	  * 2018年3月28日  科帮网 首次创建
	  *
	  */
	 List<Object[]> listStudent();
	 /**
	  * 返回List<Student>
	  * @author Wujun
	  * @return  List<Student>
	  * @Date	2018年3月28日
	  * 更新日志
	  * 2018年3月28日  科帮网 首次创建
	  *
	  */
     List<Student> listStudentModel();
     /**
      * List<Map<Object, Object>>
      * @author Wujun
      * @return  List<Map<Object,Object>>
      * @Date	2018年3月28日
      * 更新日志
      * 2018年3月28日  科帮网 首次创建
      *
      */
     List<Map<Object, Object>> listStudentMap();
}
