package com.jun.plugin.oauth.repostiory;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jun.plugin.oauth.entity.SysUser;

import java.util.Optional;

/**
 * 用户信息仓库.
 *
 * @author Wujun
 * @date 2020/1/6 下午1:08
 */
public interface SysUserRepository extends JpaRepository<SysUser, Long> {

    /**
     * 通过用户名查找用户.
     *
     * @param username 用户名
     * @return 结果
     */
    Optional<SysUser> findFirstByUsername(String username);

}
