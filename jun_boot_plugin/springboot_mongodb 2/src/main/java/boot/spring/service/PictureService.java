package boot.spring.service;

import java.util.List;

import boot.spring.po.Picture;

public interface PictureService{
	List<Picture> getpiclist(int current,int rowCount,String sortid);//获取一页记录
	int getpicturenum();//获取总数目
	Picture getPictureByid(String id);//使用主键获取记录
	void SaveorUpdatePicture(Picture p);
	void deletePicture(String id);
	List<Picture> getsearchresult(int current,int rowCount,String sortid,String search);//获取一页搜索结果
	int getsearchresulttotal(String search);//获取搜索结果总数
}
