package isolation;

import java.util.List;

/**
 * Created by qianjia on 2017/1/23.
 */
public interface LooService {

  Loo getById(Long id);

  void update(Long id, String name);

  void delete(Long id);

  void insert(Long id, String name);

  void deleteAll();

  List<Loo> getByName(String name);

}
