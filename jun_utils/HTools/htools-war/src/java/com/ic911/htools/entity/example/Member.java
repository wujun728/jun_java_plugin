package com.ic911.htools.entity.example;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.ic911.core.domain.BaseEntity;

@Entity
@Table(name = "t_member")
public class Member extends BaseEntity{

	private static final long serialVersionUID = -5161363564402877494L;

	@NotNull(message="成员姓名不能为空!")
	private String name;
	
	private String type;
	
	private Boolean teamLeader;
	
	@ManyToOne
	@JoinColumn(name = "team_id")
	private Team team;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Boolean getTeamLeader() {
		return teamLeader;
	}

	public void setTeamLeader(Boolean teamLeader) {
		this.teamLeader = teamLeader;
	}

	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}
	
}
