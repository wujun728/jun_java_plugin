package boot.spring.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import boot.spring.po.Picture;

public interface PictureRepository extends MongoRepository<Picture, String>{
	Picture findById(String id);	//根据id获取PO
	Page<Picture> findAll(Pageable pageable);//获取所有数据，带分页排序
	Page<Picture> findByFilenameContaining(String filename,Pageable pageable);//根据文件名过滤，带分页排序
	List<Picture> findByFilenameContaining(String filename);//根据文件名过滤的结果总数
	
}
