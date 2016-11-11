package io.cde.authc.server;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.cde.authc.dao.UserDao;

/**
* @author 作者 Fangcai Liao
* @version 创建时间：Oct 26, 2016 11:16:00 AM
* 类说明
*/
@Service
public class UserService {

  @Autowired
  private UserDao  userDao;

  private List<Map<String, Object>> list = null;

  private Map<String, String> map = new HashMap<>();

  public Map<String, String> signin(String principal, String password) {
    this.list = this.userDao.getByName(principal);
    if(this.list.size() == 0){
      this.map.put("error", "0010005");

      return this.map;
    }else{
      String email = (String) this.list.get(0).get("email");
      if(this.userDao.getByEmail(email).get(0).get("verify").equals("0")){
        this.map.put("error", "0010010");

        return this.map;
      }else{
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(principal,password);
        try {
          subject.login(token);
          this.map.put("error", "null");

          return this.map;
        } catch (IncorrectCredentialsException ex) {
          ex.printStackTrace();
          this.map.put("error", "0010004");

          return this.map;
        }catch (Exception ex) {
          ex.printStackTrace();
          this.map.put("error", "9999999");

          return this.map;
        }
      }
    }
  }

  public Map<String, String> signout() {
    Subject subject = SecurityUtils.getSubject();
    subject.logout();
    this.map.put("error", "null");

    return this.map;
  }

  public Map<String, Object> getUser(String principal) {
    this.list = this.userDao.getByName(principal);
    
    return this.list.get(0);
  }
}
