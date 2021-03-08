package com.zhaodui.springboot.data.datacontroller;

public class CTXDSQLCONSTANT {
    public static  String   BASEINFO01 ="select custname,certcode,indiv_houh_reg_add,address,telephone from CRD_CASEAPINDYINFO  where casecode= ?  and RECORDSTATE='0'  ";
    public static  String   BASEINFO02 ="select custname,regcode,legal_name,linkmobile,reg_addr from CRD_CASEAPCORPINFO where   casecode= ?  and RECORDSTATE='0' ";
    public static  String   BASEINFO03 ="select custtype,custname,address,case when CUSTTYPE='1' then telephone else mobile end as  telephone,certcode,indiv_houh_reg_add,reg_addr,regcode,legal_name from CRD_CASECAUTIONER  where casecode= ?  and RECORDSTATE='0'  order by rid";
    public static  String   BUSEINFO01 ="select * from (" +
            " select ci.custname,ci.casecode,ci.custtype, case when ci.supermode=2  then CRD_SUPERCONTRACT.contractname else CRD_GRT_CONTRACT.contractname end as contractname," +
            "   case when ci.supermode=2  then CRD_SUPERCONTRACT.begintime else CRD_GRT_CONTRACT.begindate end as begindate," +
            "   case when ci.supermode=2  then CRD_SUPERCONTRACT.endtime else CRD_GRT_CONTRACT.enddate end as enddate," +
            "   case when ci.supermode=2  then CRD_SUPERCONTRACT.contractmoney else CRD_GRT_CONTRACT.contractmoney end as contractmoney, " +
            "   case when ci.supermode=2  then CRD_SUPERCONTRACT.copies else CRD_GRT_CONTRACT.copies end as copies, " +
            "   case when ci.supermode=2  then CRD_SUPERCONTRACT.first_copies else CRD_GRT_CONTRACT.first_copies end as first_copies, " +
            "   case when ci.supermode=2  then CRD_SUPERCONTRACT.second_copies else CRD_GRT_CONTRACT.second_copies end as second_copies " +
            "   from  CRD_CASEINFO ci" +
            "   left outer join CRD_SUPERCONTRACT on  CRD_SUPERCONTRACT.contractcode=ci.SUPERCONTRACTCODE  and CONTRACTSTATUS=1" +
            "   left outer join CRD_GRT_CONTRACT on  crd_grt_contract.casecode=ci.casecode and  CRD_GRT_CONTRACT.contracttype='050' and CRD_GRT_CONTRACT.recordstate=0   and CRD_GRT_CONTRACT.CONTRACTSTATUS=1" +
            "   where   ci.recordstate='0') sc1 where sc1.contractname is not null and sc1.casecode= ?";


}
