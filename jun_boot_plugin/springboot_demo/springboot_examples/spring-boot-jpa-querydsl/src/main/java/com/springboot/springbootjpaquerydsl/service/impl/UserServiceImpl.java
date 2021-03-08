package com.springboot.springbootjpaquerydsl.service.impl;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.springboot.springbootjpaquerydsl.dto.UserDTO;
import com.springboot.springbootjpaquerydsl.model.QLessonModel;
import com.springboot.springbootjpaquerydsl.model.QUserModel;
import com.springboot.springbootjpaquerydsl.model.UserModel;
import com.springboot.springbootjpaquerydsl.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Date: 2019/9/25
 * @Time: 23:11
 * @email: inwsy@hotmail.com
 * Description:
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    JPAQueryFactory queryFactory;

    @Override
    public Long update(String id, String nickName) {
        QUserModel userModel = QUserModel.userModel;
        // 更新
        return queryFactory.update(userModel).set(userModel.nickName, nickName).where(userModel.id.eq(id)).execute();
    }

    @Override
    public Long delete(String id) {
        QUserModel userModel = QUserModel.userModel;
        // 删除
        return queryFactory.delete(userModel).where(userModel.id.eq(id)).execute();
    }

    @Override
    public List<String> selectAllNameList() {
        QUserModel userModel = QUserModel.userModel;
        // 查询字段
        return queryFactory.select(userModel.nickName).from(userModel).fetch();
    }

    @Override
    public List<UserModel> selectAllUserModelList() {
        QUserModel userModel = QUserModel.userModel;
        // 查询实体
        return queryFactory.selectFrom(userModel).fetch();
    }

    @Override
    public List<UserDTO> selectAllUserDTOList() {
        QUserModel userModel = QUserModel.userModel;
        QLessonModel lessonModel = QLessonModel.lessonModel;
        // 连表查询实体并将结果封装至DTO
        return queryFactory
                .select(
                        Projections.bean(UserDTO.class, userModel.nickName, userModel.age, lessonModel.startDate, lessonModel.address, lessonModel.name)
                )
                .from(userModel)
                .leftJoin(lessonModel)
                .on(userModel.id.eq(lessonModel.userId))
                .fetch();
    }

    @Override
    public List<String> selectDistinctNameList() {
        QUserModel userModel = QUserModel.userModel;
        // 去重查询
        return queryFactory.selectDistinct(userModel.nickName).from(userModel).fetch();
    }

    @Override
    public UserModel selectFirstUser() {
        QUserModel userModel = QUserModel.userModel;
        // 查询首个实体
        return queryFactory.selectFrom(userModel).fetchFirst();
    }

    @Override
    public UserModel selectUser(String id) {
        QUserModel userModel = QUserModel.userModel;
        // 查询单个实体，如果结果有多个，会抛`NonUniqueResultException`。
        return queryFactory.selectFrom(userModel).fetchOne();
    }

    @Override
    public String mysqlFuncDemo(String id, String nickName, int age) {

        QUserModel userModel = QUserModel.userModel;

        // Mysql 聚合函数示例

        // 聚合函数-avg()
        Double averageAge = queryFactory.select(userModel.age.avg()).from(userModel).fetchOne();

        // 聚合函数-sum()
        Integer sumAge = queryFactory.select(userModel.age.sum()).from(userModel).fetchOne();

        // 聚合函数-concat()
        String concat = queryFactory.select(userModel.nickName.concat(nickName)).from(userModel).fetchOne();

        // 聚合函数-contains()
        Boolean contains = queryFactory.select(userModel.nickName.contains(nickName)).from(userModel).where(userModel.id.eq(id)).fetchOne();

        // 聚合函数-DATE_FORMAT()
        String date = queryFactory.select(Expressions.stringTemplate("DATE_FORMAT({0},'%Y-%m-%d')", userModel.createDate)).from(userModel).fetchOne();

        return null;
    }
}
