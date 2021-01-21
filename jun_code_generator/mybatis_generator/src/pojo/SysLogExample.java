package pojo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SysLogExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public SysLogExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andLog_titleIsNull() {
            addCriterion("log_title is null");
            return (Criteria) this;
        }

        public Criteria andLog_titleIsNotNull() {
            addCriterion("log_title is not null");
            return (Criteria) this;
        }

        public Criteria andLog_titleEqualTo(String value) {
            addCriterion("log_title =", value, "log_title");
            return (Criteria) this;
        }

        public Criteria andLog_titleNotEqualTo(String value) {
            addCriterion("log_title <>", value, "log_title");
            return (Criteria) this;
        }

        public Criteria andLog_titleGreaterThan(String value) {
            addCriterion("log_title >", value, "log_title");
            return (Criteria) this;
        }

        public Criteria andLog_titleGreaterThanOrEqualTo(String value) {
            addCriterion("log_title >=", value, "log_title");
            return (Criteria) this;
        }

        public Criteria andLog_titleLessThan(String value) {
            addCriterion("log_title <", value, "log_title");
            return (Criteria) this;
        }

        public Criteria andLog_titleLessThanOrEqualTo(String value) {
            addCriterion("log_title <=", value, "log_title");
            return (Criteria) this;
        }

        public Criteria andLog_titleLike(String value) {
            addCriterion("log_title like", value, "log_title");
            return (Criteria) this;
        }

        public Criteria andLog_titleNotLike(String value) {
            addCriterion("log_title not like", value, "log_title");
            return (Criteria) this;
        }

        public Criteria andLog_titleIn(List<String> values) {
            addCriterion("log_title in", values, "log_title");
            return (Criteria) this;
        }

        public Criteria andLog_titleNotIn(List<String> values) {
            addCriterion("log_title not in", values, "log_title");
            return (Criteria) this;
        }

        public Criteria andLog_titleBetween(String value1, String value2) {
            addCriterion("log_title between", value1, value2, "log_title");
            return (Criteria) this;
        }

        public Criteria andLog_titleNotBetween(String value1, String value2) {
            addCriterion("log_title not between", value1, value2, "log_title");
            return (Criteria) this;
        }

        public Criteria andLog_contentIsNull() {
            addCriterion("log_content is null");
            return (Criteria) this;
        }

        public Criteria andLog_contentIsNotNull() {
            addCriterion("log_content is not null");
            return (Criteria) this;
        }

        public Criteria andLog_contentEqualTo(String value) {
            addCriterion("log_content =", value, "log_content");
            return (Criteria) this;
        }

        public Criteria andLog_contentNotEqualTo(String value) {
            addCriterion("log_content <>", value, "log_content");
            return (Criteria) this;
        }

        public Criteria andLog_contentGreaterThan(String value) {
            addCriterion("log_content >", value, "log_content");
            return (Criteria) this;
        }

        public Criteria andLog_contentGreaterThanOrEqualTo(String value) {
            addCriterion("log_content >=", value, "log_content");
            return (Criteria) this;
        }

        public Criteria andLog_contentLessThan(String value) {
            addCriterion("log_content <", value, "log_content");
            return (Criteria) this;
        }

        public Criteria andLog_contentLessThanOrEqualTo(String value) {
            addCriterion("log_content <=", value, "log_content");
            return (Criteria) this;
        }

        public Criteria andLog_contentLike(String value) {
            addCriterion("log_content like", value, "log_content");
            return (Criteria) this;
        }

        public Criteria andLog_contentNotLike(String value) {
            addCriterion("log_content not like", value, "log_content");
            return (Criteria) this;
        }

        public Criteria andLog_contentIn(List<String> values) {
            addCriterion("log_content in", values, "log_content");
            return (Criteria) this;
        }

        public Criteria andLog_contentNotIn(List<String> values) {
            addCriterion("log_content not in", values, "log_content");
            return (Criteria) this;
        }

        public Criteria andLog_contentBetween(String value1, String value2) {
            addCriterion("log_content between", value1, value2, "log_content");
            return (Criteria) this;
        }

        public Criteria andLog_contentNotBetween(String value1, String value2) {
            addCriterion("log_content not between", value1, value2, "log_content");
            return (Criteria) this;
        }

        public Criteria andCreatetimeIsNull() {
            addCriterion("createtime is null");
            return (Criteria) this;
        }

        public Criteria andCreatetimeIsNotNull() {
            addCriterion("createtime is not null");
            return (Criteria) this;
        }

        public Criteria andCreatetimeEqualTo(Date value) {
            addCriterion("createtime =", value, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeNotEqualTo(Date value) {
            addCriterion("createtime <>", value, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeGreaterThan(Date value) {
            addCriterion("createtime >", value, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeGreaterThanOrEqualTo(Date value) {
            addCriterion("createtime >=", value, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeLessThan(Date value) {
            addCriterion("createtime <", value, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeLessThanOrEqualTo(Date value) {
            addCriterion("createtime <=", value, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeIn(List<Date> values) {
            addCriterion("createtime in", values, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeNotIn(List<Date> values) {
            addCriterion("createtime not in", values, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeBetween(Date value1, Date value2) {
            addCriterion("createtime between", value1, value2, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeNotBetween(Date value1, Date value2) {
            addCriterion("createtime not between", value1, value2, "createtime");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}