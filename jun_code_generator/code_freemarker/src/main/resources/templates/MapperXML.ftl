<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
	PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
	"http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<#assign beanName = table.beanName/>
<#assign tableName = table.tableName/>
<#macro mapperEl value>${r"#{"}${value}}</#macro>
<#macro orderInfo value>
		${r"${"}${value}}
</#macro>
<#macro pageInfo value>
		${r"${"}${value}}
</#macro>
<#macro findInfo value>
			${r"${"}${value}}
</#macro>
<#--<#macro batchMapperEl value>${r"#{"}${value}}</#batchMapperEl>-->
<#if table.prefix!="">
<#assign bean = conf.entityPackage+"."+table.prefix+"."+beanName/>
<#assign mapper = conf.daoPackage+"."+table.prefix+"."+beanName+"Mapper"/>
<#else>
<#assign bean = conf.entityPackage+"."+beanName/>
<#assign mapper = conf.daoPackage+"."+beanName+"Mapper"/>
</#if>
<#assign propertiesAnColumns = table.propertiesAnColumns/>
<#assign keys = propertiesAnColumns?keys/>
<#assign primaryKey = table.primaryKey/>
<#assign keys2 = primaryKey?keys/>
<#assign insertPropertiesAnColumns = table.insertPropertiesAnColumns/>
<#assign keys3 = insertPropertiesAnColumns?keys/>
<mapper namespace="${mapper}">

	<sql id="basicSelectSql">
		<#list keys as key>
		`${propertiesAnColumns["${key}"]}` AS `${key}`<#if key_has_next>,</#if>
		</#list>
	</sql>
	
	<sql id="basicWhereColumn">
		<#list keys as key>
			<if test="${key} != null">
				AND `${propertiesAnColumns["${key}"]}` = <@mapperEl key/>
			</if>
		</#list>
	</sql>
	
	<sql id="basicWhereEntitySql">
		<where>
		<include refid="basicWhereColumn"/>
		</where>
	</sql>
	
	<sql id="basicWhereMapSql">
		<where>
		<include refid="basicWhereColumn"/>
		</where>
	</sql>

	<select id="queryInfoById" resultType="${bean}">
		SELECT
		<include refid="basicSelectSql"/>
		FROM `${tableName}`
		<where>
		<#list keys2 as key>
			`${key}` = <@mapperEl primaryKey["${key}"]/>
		</#list>
		</where>
		LIMIT 1;
	</select>

	<select id="selectCount" resultType="Integer">
		SELECT COUNT(*)
		FROM `${tableName}`
		;
	</select>

    <select id="selectByCondition" resultType="Integer">
        SELECT COUNT(*)
        FROM `${tableName}`
        <include refid="basicWhereEntitySql"/>
        ;
    </select>

<select id="getList" resultType="${bean}">
    SELECT
    <include refid="basicSelectSql"/>
    FROM `${tableName}`
    ;
</select>

<#--
<select id="getListByMapCount" resultType="Integer">
    SELECT COUNT(*)
    FROM `${tableName}`
    <include refid="basicWhereMapSql"/>
    ;
</select>

	<select id="getListByMap" resultType="${bean}">
		SELECT
		<include refid="basicSelectSql"/>
		FROM `${tableName}`
		<include refid="basicWhereMapSql"/>
		;
	</select>-->

	<update id="update">
		UPDATE `${tableName}`
		<set>
			<#list keys3 as key>
			<#if key != "isDeleted" && key != "addTime" && key != "id">
			<if test="${key} != null">
				`${propertiesAnColumns["${key}"]}` = <@mapperEl key/>,
			</if>
			</#if>
			</#list>
		</set>
		<where>
		<#list keys2 as key>
			`${key}` = <@mapperEl primaryKey["${key}"]/>
		</#list>
		</where>
	</update>

	<update id="deleteById">
		UPDATE `${tableName}`
		SET `isDeleted`=1
		<where>
		<#list keys2 as key>
			`${key}` = <@mapperEl primaryKey["${key}"]/>
		</#list>
		</where>
	</update>

	<insert id="add" useGeneratedKeys="true" keyProperty="id">
		INSERT INTO 
		`${tableName}`
		(<#list keys3 as key>`${insertPropertiesAnColumns["${key}"]}`<#if key_has_next>,</#if></#list>)
		VALUES 
		(<#list keys3 as key><@mapperEl key/><#if key_has_next>,</#if></#list>)
	</insert>

	<insert id="addList">
		INSERT INTO
		`${tableName}`
		(<#list keys3 as key>`${insertPropertiesAnColumns["${key}"]}`<#if key_has_next>,</#if></#list>)
		VALUES
		<foreach collection="list" item="item" index="index" separator="," >
		(<#list keys3 as key><@mapperEl "item."+key/><#if key_has_next>,</#if></#list>)
		</foreach>
	</insert>

	<sql id="orderSql">
		<if test="orderSql != null and orderSql != ''">
			<@orderInfo "orderSql" />
		</if>
	</sql>

	<#-- pageSql 前面能随便加点字符串的话就能正常换行，TODO -->
	<sql id="pageSql">
		<if test="pageSql != null and pageSql != ''">
			<@pageInfo "pageSql" />
		</if>
	</sql>
	<#--
	<sql id="whereContentAll">
		<where>
		<include refid="basicWhereColumn"/>
			<if test="findContent != null and findContentColumn != null" >
				AND (
					LOWER(`<@mapperEl "findContentColumn"/>`) LIKE CONCAT("%",<@mapperEl "findContent"/>,"%")
				)
			</if>
		</where>
	</sql>
	-->
	
	<sql id="whereContentAll">
		<where>
		<include refid="basicWhereColumn"/>
			<if test="findSql != null" >
				<@findInfo "findSql" />
			</if>
		</where>
	</sql>

	<select id="findByPage" resultType="${bean}" >
		SELECT
			<include refid="basicSelectSql" />
		FROM `${tableName}`
			<include refid="whereContentAll"/>
			<include refid="orderSql" />
			<include refid="pageSql" />
	</select>

	<select id="findByPageCount" >
		SELECT COUNT(`id`)
		FROM `${tableName}`
		<include refid="whereContentAll"/>
		<include refid="orderSql" />
	</select>

</mapper>