package io.cde.authc.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.data.mongodb.core.MongoOperations;

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


    /**
     * 引入mongodb的.
     */
    @Autowired
    private MongoOperations mongoOperations;

    /**
     * 根据用户登录账号查询用户信息.
     * @param principal 登录账号.
     * @return this DBObject.
     */
    public DBObject getAccountByPrincipal(final String principal) {
        final BasicDBList dbList = new BasicDBList();
        final DBObject account;
        dbList.add(new BasicDBObject("name", principal));
        dbList.add(new BasicDBObject("email", principal));
        account = mongoOperations.getCollection("account").findOne(new BasicDBObject("$or", dbList));
        return account;

    }

    /**
     * 根据邮箱名获取邮箱信息.
     * @param name 邮箱名.
     * @return this DBObject.
     */
    public  DBObject getEmailByname(final String name) {
        final BasicDBObject conn = new BasicDBObject();
        final DBObject email;
        conn.put("email", name);
        email = mongoOperations.getCollection("email").findOne(conn);
        return email;
    }

}
