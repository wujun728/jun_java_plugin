package com.ibeetl.admin.console.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.util.CellReference;
import org.beetl.sql.core.engine.PageQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ibeetl.admin.console.dao.DictConsoleDao;
import com.ibeetl.admin.console.web.dto.DictExcelImportData;
import com.ibeetl.admin.core.entity.CoreDict;
import com.ibeetl.admin.core.entity.CoreUser;
import com.ibeetl.admin.core.service.CoreBaseService;
import com.ibeetl.admin.core.util.ExcelError;
import com.ibeetl.admin.core.util.PlatformException;

/**
 * CoreDict Service
 */

@Service
@Transactional
public class DictConsoleService extends CoreBaseService<CoreDict> {

    @Autowired private DictConsoleDao dictDao;

    public PageQuery<CoreDict>queryByCondition(PageQuery query){
        PageQuery ret =  dictDao.queryByCondition(query);
        queryListAfter(ret.getList());
        return ret;
    }

    public void batchDelCoreDict(List<Long> ids){
        try {
        	//TODO,找到数据字典所有子类，设置删除标记
            dictDao.batchDelCoreDictByIds(ids);
        } catch (Exception e) {
            throw new PlatformException("批量删除CoreDict失败", e);
        }
    }
    public List<CoreDict> queryExcel(PageQuery<CoreUser> query) {
        //同查询，不需要额外数据
        PageQuery ret =  dictDao.queryByCondition(query);
        queryListAfter(ret.getList());
        return ret.getList();
        
    }
    /**
     *  参考：dict_mapping.xml
     * @param list
     * @return
     */
    public void batchInsert(List<DictExcelImportData> list) {
       int dataStartRow = 2;
       final Map<Integer,DictExcelImportData> map = new HashMap<>();
       list.forEach((item)->map.put(item.getExcelId(), item));
       //逐个按照顺序导入
       for(DictExcelImportData item:list) {
           CoreDict dict = new  CoreDict();
           dict.setName(item.getName());
           dict.setRemark(item.getRemark());
           dict.setType(item.getType());
           dict.setTypeName(item.getTypeName());
           dict.setValue(item.getValue());
           
           //设置父字典
           if(item.getParentExcelId()!=0) {
               DictExcelImportData parentItem =  map.get(item.getParentExcelId());
               if(parentItem==null) {
                   //硬编码，TODO,用reader缺少校验，替换手写导入
                   int row = item.getExcelId()+dataStartRow;
                   throwImporError(row,5,"未找到父字典");
               }
               if(parentItem.getId()==null) {
                   int row = item.getExcelId()+dataStartRow;
                   throwImporError(row,5,"父字典未被导入，请先导入父字典");
               }
               dict.setParent(parentItem.getId());
           }
           dict.setCreateTime(new Date());
           //导入前先查找是否有此值
           CoreDict template = new CoreDict();
           template.setType(dict.getType());
           template.setValue(dict.getValue());
           CoreDict dbDict = dictDao.templateOne(template);
           if(dbDict!=null) {
               int row = item.getExcelId()+dataStartRow;
               throwImporError(row,0,"字典数据已经存在");
           }
           dictDao.insert(dict);
           
           item.setId(dict.getId());
           dataStartRow++;
       }
       
       
    }
    
    private void throwImporError(int row,int col,String msg) {
        ExcelError error = new ExcelError();
        CellReference cr = new CellReference(row,col,false,false);
        error.setCell(cr.formatAsString());
        error.setMsg(msg);
        throw new PlatformException("导入错误在:"+error.getCell()+","+msg);
    }
    
    
}