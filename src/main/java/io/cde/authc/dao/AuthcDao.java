package io.cde.authc.dao;

import java.util.List;
import java.util.Map;

import com.mongodb.*;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.data.mongodb.core.MongoOperations;

/**
* @author 作者 Fangcai Liao
* @version 创建时间：Nov 7, 2016 3:12:54 PM
* 类说明
*/
@Repository
public class AuthcDao {

  @Autowired
  MongoOperations mongoOperations;

  public DBObject getAccountByPrincipal (String principal) {
    BasicDBList dbList = new BasicDBList();
    dbList.add(new BasicDBObject("name", principal));
    dbList.add(new BasicDBObject("email", principal));
      try{
      DBObject account = mongoOperations.getCollection("account").findOne(new BasicDBObject("$or", dbList));
      return account;
    } catch (Exception e) {
      e.printStackTrace();
      throw new UnknownAccountException();
    }
  }
  public  DBObject getEmailByname (String name) {
    BasicDBObject conn = new BasicDBObject();
    conn.put("email", name);
    try{
      DBObject email = mongoOperations.getCollection("email").findOne(conn);
      return email;
    } catch (Exception e) {
      e.printStackTrace();
      throw new UnknownAccountException();
    }
  }

}
