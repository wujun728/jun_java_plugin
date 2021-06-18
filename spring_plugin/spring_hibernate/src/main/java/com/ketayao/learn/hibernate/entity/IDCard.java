package com.ketayao.learn.hibernate.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="test_id_card")
public class IDCard extends IdEntity {  
    /** 描述  */
	private static final long serialVersionUID = 8486310294748647696L;

	@Column
    private String cardno;      
    
    @OneToOne  
    @JoinColumn(name="userId")    
    private User user;

	/**  
	 * 返回 cardno 的值   
	 * @return cardno  
	 */
	public String getCardno() {
		return cardno;
	}

	/**  
	 * 设置 cardno 的值  
	 * @param cardno
	 */
	public void setCardno(String cardno) {
		this.cardno = cardno;
	}

	/**  
	 * 返回 user 的值   
	 * @return user  
	 */
	public User getUser() {
		return user;
	}

	/**  
	 * 设置 user 的值  
	 * @param user
	 */
	public void setUser(User user) {
		this.user = user;
	}  

    
}  