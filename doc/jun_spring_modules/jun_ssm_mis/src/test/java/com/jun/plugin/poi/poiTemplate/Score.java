package com.jun.plugin.poi.poiTemplate;

public class Score {
    private Integer scoreId;

    private Integer studentId;

    private String course;

    private Integer score;

    public Integer getScoreId() {
        return scoreId;
    }

    public Score() {
		super();
	}

	public Score(Integer scoreId, Integer studentId, String course,
			Integer score) {
		super();
		this.scoreId = scoreId;
		this.studentId = studentId;
		this.course = course;
		this.score = score;
	}
    public void setScoreId(Integer scoreId) {
        this.scoreId = scoreId;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course == null ? null : course.trim();
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

	@Override
	public String toString() {
		return "Score [scoreId=" + scoreId + ", studentId=" + studentId
				+ ", course=" + course + ", score=" + score + "]";
	}
}