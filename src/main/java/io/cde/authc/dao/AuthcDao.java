package io.cde.authc.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.data.mongodb.core.MongoOperations;

import org.apache.shiro.authc.UnknownAccountException;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

/**
* @author 作者 Fangcai Liao.
* @version 创建时间：Nov 7, 2016 3:12:54 PM.
* 类说明.
*/
@Repository
public class AuthcDao {

    @Autowired
    private MongoOperations mongoOperations;

    /**
     * 根据用户登录账号查询用户信息.
     * @param principal 登录账号.
     * @return this DBObject.
     */
    public DBObject getAccountByPrincipal(final String principal) {
        final BasicDBList dbList = new BasicDBList();
        dbList.add(new BasicDBObject("name", principal));
        dbList.add(new BasicDBObject("email", principal));
        try {
            final DBObject account = mongoOperations.getCollection("account").findOne(new BasicDBObject("$or", dbList));
            return account;
        } catch (Exception e) {
            e.printStackTrace();
            throw new UnknownAccountException();
        }
    }

    /**
     * 根据邮箱名获取邮箱信息.
     * @param name 邮箱名.
     * @return this DBObject.
     */
    public  DBObject getEmailByname(final String name) {
        BasicDBObject conn = new BasicDBObject();
        conn.put("email", name);
        try {
            DBObject email = mongoOperations.getCollection("email").findOne(conn);
            return email;
        } catch (Exception e) {
            e.printStackTrace();
            throw new UnknownAccountException();
        }
    }

}
