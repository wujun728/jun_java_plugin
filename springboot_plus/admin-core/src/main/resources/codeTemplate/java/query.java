package ${package};

import org.apache.commons.lang3.StringUtils;
import com.ibeetl.admin.core.annotation.Query;
import com.ibeetl.admin.core.util.Tool;
import com.ibeetl.admin.core.util.enums.CoreDictType;
import com.ibeetl.admin.core.web.query.PageParam;
import java.util.Date;
/**
 *${entity.displayName}查询
 */
public class ${entity.name}Query extends PageParam {
    @for(attr in attrs) {
        @if(isNotEmpty(attr.dictType)) {
    \@Query(name = "${attr.displayName}", display = true,type=Query.TYPE_DICT,dict="${attr.dictType}")
    private ${attr.javaType} ${attr.name};
        @} else if(attr.dateRange) {
    \@Query(name = "${attr.displayName}", display = true,type=Query.TYPE_DATE_BETWEEN)
    private String ${attr.name};
    private Date ${strutil.replace (attr.name,"Range","")}Start;
    private Date ${strutil.replace (attr.name,"Range","")}End;
        @} else if(attr.dateTimeRange) {
    \@Query(name = "${attr.displayName}", display = true,type=Query.TYPE_DATETIME_BETWEEN)
    private String ${attr.name};
    private Date ${strutil.replace (attr.name,"Range","")}Start;
    private Date ${strutil.replace (attr.name,"Range","")}End;
        @} else {
    \@Query(name = "${attr.displayName}", display = true)
    private ${attr.javaType} ${attr.name};
        @}
    @}
    @for(attr in attrs) {
        @if(attr.dateRange) {
    public String get${upperFirst(attr.name)}(){
        return  ${attr.name};
    }
    public void set${upperFirst(attr.name)}(String ${attr.name} ){
        this.${attr.name} = ${attr.name};
        if(StringUtils.isEmpty(${attr.name})) {
            return ;
        }
        Date[] ds = Tool.parseDataRange(${attr.name});
        this.${strutil.replace (attr.name,"Range","")}Start=ds[0];
        this.${strutil.replace (attr.name,"Range","")}End =ds[1];
    }
    public Date get${upperFirst(strutil.replace (attr.name,"Range",""))}Start(){
        return  ${strutil.replace (attr.name,"Range","")}Start;
    }
    public void set${upperFirst(strutil.replace(attr.name,"Range",""))}Start(Date ${strutil.replace (attr.name,"Range","")}Start){
        this.${strutil.replace (attr.name,"Range","")}Start = ${strutil.replace (attr.name,"Range","")}Start;
    }
    public ${attr.javaType} get${upperFirst(strutil.replace (attr.name,"Range",""))}End(){
        return  ${strutil.replace (attr.name,"Range","")}End;
    }
    public void set${upperFirst(strutil.replace (attr.name,"Range",""))}End(${attr.javaType} ${strutil.replace (attr.name,"Range","")}End){
        this.${strutil.replace (attr.name,"Range","")}End = ${strutil.replace (attr.name,"Range","")}End;
    }
        @} else if(attr.dateTimeRange) {
    public String get${upperFirst(attr.name)}(){
        return  ${attr.name};
    }
    public void set${upperFirst(attr.name)}(String ${attr.name} ){
        this.${attr.name} = ${attr.name};
        if(StringUtils.isEmpty(${attr.name})) {
            return ;
        }
        Date[] ds = Tool.parseDataTimeRange(${attr.name});
        this.${strutil.replace (attr.name,"Range","")}Start=ds[0];
        this.${strutil.replace (attr.name,"Range","")}End =ds[1];
    }
    public Date get${upperFirst(strutil.replace (attr.name,"Range",""))}Start(){
        return  ${strutil.replace (attr.name,"Range","")}Start;
    }
    public void set${upperFirst(strutil.replace(attr.name,"Range",""))}Start(Date ${strutil.replace (attr.name,"Range","")}Start){
        this.${strutil.replace (attr.name,"Range","")}Start = ${strutil.replace (attr.name,"Range","")}Start;
    }
    public ${attr.javaType} get${upperFirst(strutil.replace (attr.name,"Range",""))}End(){
        return  ${strutil.replace (attr.name,"Range","")}End;
    }
    public void set${upperFirst(strutil.replace (attr.name,"Range",""))}End(${attr.javaType} ${strutil.replace (attr.name,"Range","")}End){
        this.${strutil.replace (attr.name,"Range","")}End = ${strutil.replace (attr.name,"Range","")}End;
    }
        @} else {
    public ${attr.javaType} get${upperFirst(attr.name)}(){
        return  ${attr.name};
    }
    public void set${upperFirst(attr.name)}(${attr.javaType} ${attr.name} ){
        this.${attr.name} = ${attr.name};
    }
        @}
    @}
 
}
