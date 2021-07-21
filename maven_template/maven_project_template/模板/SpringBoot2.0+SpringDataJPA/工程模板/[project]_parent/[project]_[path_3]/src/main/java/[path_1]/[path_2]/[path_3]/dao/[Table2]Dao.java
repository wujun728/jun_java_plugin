package [package].dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import [package].pojo.[Table2];
/**
 * [comment]数据访问接口
 * @author Administrator
 *
 */
public interface [Table2]Dao extends JpaRepository<[Table2],[keyType]>,JpaSpecificationExecutor<[Table2]>{
	
}
