package io.cde.authc.dao;

import java.util.List;
import java.util.Map;

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
public class UserDao {

  @Autowired
  private JdbcTemplate jdbcTemplate;

  @Value("${userdao.sql.getUserByName}")
  private String getUserByName;

  @Value("${userdao.sql.getUserByEmail}")
  private String getUserByEmail;

  List<Map<String, Object>> list = null;

  public List<Map<String, Object>> getByName (String principal) {
    try{
      this.list = this.jdbcTemplate.queryForList(this.getUserByName, principal, principal);

      return this.list;
    }catch(Exception e){
      return null;
    }
  }

  public List<Map<String, Object>> getByEmail (String principal) {
    try{
      this.list = this.jdbcTemplate.queryForList(this.getUserByEmail, principal);

      return this.list;
    }catch(Exception e){
      return null;
    }
  }

  public String getGetUserByName() {
    return this.getUserByName;
  }
  public void setGetUserByName(String getUserByName) {
    this.getUserByName = getUserByName;
  }
  public String getGetUserByEmail() {
    return this.getUserByEmail;
  }
  public void setGetUserByEmail(String getUserByEmail) {
    this.getUserByEmail = getUserByEmail;
  }
}
