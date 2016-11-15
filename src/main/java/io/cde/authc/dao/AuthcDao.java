package io.cde.authc.dao;

import java.util.List;
import java.util.Map;

import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
* @author 作者 Fangcai Liao
* @version 创建时间：Nov 7, 2016 3:12:54 PM
* 类说明
*/
@Repository
public class AuthcDao {

  @Autowired
  public JdbcTemplate jdbcTemplate;

  @Value("${userdao.sql.getAccountByPrincipal}")
  public String getAccountByPrincipal;

  List<Map<String, Object>> list = null;

  public Map<String, Object> getAccountByPrincipal (String principal) {
    try{
      this.list = this.jdbcTemplate.queryForList(this.getAccountByPrincipal, principal, principal);

      return this.list.get(0);
    } catch (Exception e) {
      e.printStackTrace();
      throw new UnknownAccountException();
    }
  }
}
