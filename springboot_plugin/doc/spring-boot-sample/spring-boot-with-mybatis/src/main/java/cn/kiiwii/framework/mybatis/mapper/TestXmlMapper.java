package cn.kiiwii.framework.mybatis.mapper;

import cn.kiiwii.framework.mybatis.model.Account;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

/**
 * Created by zhong on 2016/12/22.
 */
public interface TestXmlMapper {

    public int addMoney(int userId, float money);

    public int minusMoney(int userId, float money);

    //@CacheEvict(value = {"indexCache"},allEntries = true,beforeInvocation = true)
    public int insertAccount(Account account);

    //@Cacheable(value = "indexCache",key = "'xmlgetAccountById'+#id")
    public Account getAccountById(int id);

    //@Cacheable(value = "indexCache",key = "'xmlfindAccountsById'+#id")
    public List<Account> findAccountsById(int id);
}
