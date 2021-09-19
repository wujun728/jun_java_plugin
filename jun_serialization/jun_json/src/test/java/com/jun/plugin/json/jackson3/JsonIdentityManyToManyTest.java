package com.jun.plugin.json.jackson3;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jun.plugin.json.jackson3.jsonxml.manytomany.Group;
import com.jun.plugin.json.jackson3.jsonxml.manytomany.Role;
import com.jun.plugin.json.jackson3.jsonxml.manytomany.User;

import org.junit.Assert;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.IOException;
import java.util.HashSet;

/**
 * Created by dzy on 2018/9/2
 * 多对多关系的序列化
 * Ref : https://github.com/edwinquaihoi/jsonidentitymanytomany.git
 * 结论：
 * 1. 使用 JsonIdentityInfo 可以解决对象相互引用的问题
 *
 */
public class JsonIdentityManyToManyTest {

  @Test
  public void testWithObjectReuse() throws JsonParseException, JsonMappingException, IOException {
    // create a group
    Group group = new Group();
    group.setId("GROUP");

    // a group which is not related to another role
    Group group2 = new Group();
    group2.setId("GROUP2");

    // create a role
    Role role = new Role();
    role.setId("ROLE");

    // link roles to group
    group.setRoles(new HashSet<Role>());
    group.getRoles().add(role);

    //link group to roles
    role.setGroups(new HashSet<Group>());
    role.getGroups().add(group);

    // create a user
    User user = new User();
    user.setId("USER");

    // link roles & groups to user
    user.setGroups(new HashSet<Group>());
    user.getGroups().add(group);
    user.getGroups().add(group2);
    user.setRoles(new HashSet<Role>());
    user.getRoles().add(role);

    // output to string works here
    System.out.println(user);
    System.out.println("=============================================================");

    // store the User json for desrialization
    String userString = user.toString();

    // deserialize works here
    User u2 = new ObjectMapper().readValue(userString, User.class);
    System.out.println(" print u2 :");
    System.out.println(u2);

    User u3 = new ObjectMapper().readValue(u2.toString(), User.class);
    System.out.println(" print u3 :");
    System.out.println(u3);

    String userString2 = u2.toString();
    assertThat("user1 not equal user2",userString2,equalTo(userString));

  }

}
