package com.erp.model;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

/**
 * Test entity. @author Wujun
 */
@Entity
@Table(name = "SYS_TEST")
@DynamicUpdate(true)
@DynamicInsert(true)
public class Test implements java.io.Serializable
{
	private static final long serialVersionUID = -6488196741315215104L;
	private Integer id;
	private Test test;
	private String username;
	private String userpwd;
	private Set<Test> tests = new HashSet<Test>(0);

	// Constructors

	/** default constructor */
	public Test()
	{
	}

	/** full constructor */
	public Test(Test test, String username, String userpwd, Set<Test> tests)
	{
		this.test = test;
		this.username = username;
		this.userpwd = userpwd;
		this.tests = tests;
	}

	// Property accessors
	@Id
	@GeneratedValue
	@Column(name = "ID", unique = true, nullable = false)
	public Integer getId()
	{
		return this.id;
	}

	public void setId(Integer id )
	{
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PID")
	public Test getTest()
	{
		return this.test;
	}

	public void setTest(Test test )
	{
		this.test = test;
	}

	@Column(name = "USERNAME", length = 100)
	public String getUsername()
	{
		return this.username;
	}

	public void setUsername(String username )
	{
		this.username = username;
	}

	@Column(name = "USERPWD", length = 100)
	public String getUserpwd()
	{
		return this.userpwd;
	}

	public void setUserpwd(String userpwd )
	{
		this.userpwd = userpwd;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "test")
	public Set<Test> getTests()
	{
		return this.tests;
	}

	public void setTests(Set<Test> tests )
	{
		this.tests = tests;
	}

}