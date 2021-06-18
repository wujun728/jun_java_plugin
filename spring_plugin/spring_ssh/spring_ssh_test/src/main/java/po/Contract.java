package po;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;


//合同表
@Entity
@Table(name="合同信息表")
public class Contract {
	@Id @Column(name="contractid")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	@Column(name="合同编码")
	String htbm;
	@Column(name="原合同编码")
	String yhtbm;
	@Column(name="合同名称")
	String htmc;
	@Column(name="合同性质")
	String htxz;
	@Column(name="合同类型")
	String htlx;
	@Column(name="委托单位")
	String wtdw;
	@Column(name="合同签订日期")
	String htqdrq;
	@Column(name="合同开始日期")
	String htksrq;
	@Column(name="合同结束日期")
	String htjsrq;
	@Column(name="合同授权人")
	String htsqr;
	@Column(name="业务来源")
	String ywly;
	@Column(name="业务分类")
	String ywfl;
	@Column(name="合同总金额")
	Float htzje;
	@Column(name="合同总数量")
	Integer htzsl;
	@Column(name="质保金")
	Float zbj;
	@Column(name="合同执行金额")
	Float htzxje;
	@Column(name="合同执行数量")
	Integer htzxsl;
	@Column(name="制单人")
	String zdr;
	@Column(name="生效人")
	String sxr;
	@Column(name="结案人")
	String jar;
	@Column(name="制单日期")
	String zdrq;
	@Column(name="生效日期")
	String sxrq;
	@Column(name="结案日期")
	String jarq;
	@Column(name="币种")
	String bz;
	@Column(name="汇率")
	String hl;
	@Column(name="收付计划方向")
	String sfjhfx;
	@Column(name="主合同编码")
	String zhtbm;
	@Column(name="对方负责人")
	String dffzr;
	@Column(name="应收付款总额")
	Float yingshoufkze;
	@Column(name="预收付款总额")
	Float yushoufkze;
	@Column(name="税金总额")
	Float sjze;
	@Column(name="质量保证金总额")
	Float zlbzjze;
	@Column(name="扣款总额")
	Float kkze;
	@Column(name="审批结果")
	Integer spjg;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getHtbm() {
		return htbm;
	}
	public void setHtbm(String htbm) {
		this.htbm = htbm;
	}
	public String getYhtbm() {
		return yhtbm;
	}
	public void setYhtbm(String yhtbm) {
		this.yhtbm = yhtbm;
	}
	public String getHtmc() {
		return htmc;
	}
	public void setHtmc(String htmc) {
		this.htmc = htmc;
	}
	public String getHtxz() {
		return htxz;
	}
	public void setHtxz(String htxz) {
		this.htxz = htxz;
	}
	public String getHtlx() {
		return htlx;
	}
	public void setHtlx(String htlx) {
		this.htlx = htlx;
	}
	public String getWtdw() {
		return wtdw;
	}
	public void setWtdw(String wtdw) {
		this.wtdw = wtdw;
	}
	public String getHtqdrq() {
		return htqdrq;
	}
	public void setHtqdrq(String htqdrq) {
		this.htqdrq = htqdrq;
	}
	public String getHtksrq() {
		return htksrq;
	}
	public void setHtksrq(String htksrq) {
		this.htksrq = htksrq;
	}
	public String getHtjsrq() {
		return htjsrq;
	}
	public void setHtjsrq(String htjsrq) {
		this.htjsrq = htjsrq;
	}
	public String getHtsqr() {
		return htsqr;
	}
	public void setHtsqr(String htsqr) {
		this.htsqr = htsqr;
	}
	public String getYwly() {
		return ywly;
	}
	public void setYwly(String ywly) {
		this.ywly = ywly;
	}
	public String getYwfl() {
		return ywfl;
	}
	public void setYwfl(String ywfl) {
		this.ywfl = ywfl;
	}
	public Float getHtzje() {
		return htzje;
	}
	public void setHtzje(Float htzje) {
		this.htzje = htzje;
	}
	public Integer getHtzsl() {
		return htzsl;
	}
	public void setHtzsl(Integer htzsl) {
		this.htzsl = htzsl;
	}
	public Float getZbj() {
		return zbj;
	}
	public void setZbj(Float zbj) {
		this.zbj = zbj;
	}
	public Float getHtzxje() {
		return htzxje;
	}
	public void setHtzxje(Float htzxje) {
		this.htzxje = htzxje;
	}
	public Integer getHtzxsl() {
		return htzxsl;
	}
	public void setHtzxsl(Integer htzxsl) {
		this.htzxsl = htzxsl;
	}
	public String getZdr() {
		return zdr;
	}
	public void setZdr(String zdr) {
		this.zdr = zdr;
	}
	public String getSxr() {
		return sxr;
	}
	public void setSxr(String sxr) {
		this.sxr = sxr;
	}
	public String getJar() {
		return jar;
	}
	public void setJar(String jar) {
		this.jar = jar;
	}
	public String getZdrq() {
		return zdrq;
	}
	public void setZdrq(String zdrq) {
		this.zdrq = zdrq;
	}
	public String getSxrq() {
		return sxrq;
	}
	public void setSxrq(String sxrq) {
		this.sxrq = sxrq;
	}
	public String getJarq() {
		return jarq;
	}
	public void setJarq(String jarq) {
		this.jarq = jarq;
	}
	public String getBz() {
		return bz;
	}
	public void setBz(String bz) {
		this.bz = bz;
	}
	public String getHl() {
		return hl;
	}
	public void setHl(String hl) {
		this.hl = hl;
	}
	public String getSfjhfx() {
		return sfjhfx;
	}
	public void setSfjhfx(String sfjhfx) {
		this.sfjhfx = sfjhfx;
	}
	public String getZhtbm() {
		return zhtbm;
	}
	public void setZhtbm(String zhtbm) {
		this.zhtbm = zhtbm;
	}
	public String getDffzr() {
		return dffzr;
	}
	public void setDffzr(String dffzr) {
		this.dffzr = dffzr;
	}
	public Float getYingshoufkze() {
		return yingshoufkze;
	}
	public void setYingshoufkze(Float yingshoufkze) {
		this.yingshoufkze = yingshoufkze;
	}
	public Float getYushoufkze() {
		return yushoufkze;
	}
	public void setYushoufkze(Float yushoufkze) {
		this.yushoufkze = yushoufkze;
	}
	public Float getSjze() {
		return sjze;
	}
	public void setSjze(Float sjze) {
		this.sjze = sjze;
	}
	public Float getZlbzjze() {
		return zlbzjze;
	}
	public void setZlbzjze(Float zlbzjze) {
		this.zlbzjze = zlbzjze;
	}
	public Float getKkze() {
		return kkze;
	}
	public void setKkze(Float kkze) {
		this.kkze = kkze;
	}
	
	public Integer getSpjg() {
		return spjg;
	}
	public void setSpjg(Integer spjg) {
		this.spjg = spjg;
	}
	
	
}
