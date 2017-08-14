package com.btcdata.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import com.btcdata.entity.DemoInfo;

@Component
public class DemoDao {

	@Autowired
    private MongoTemplate mongoTemplate;

	/**
     * 创建对象
     * @param user
     */
    public void saveDemoInfo(DemoInfo demo) {
        mongoTemplate.save(demo);
    }
    
    /**
     * 根据用户名查询对象
     * @param userName
     * @return
     */
    public DemoInfo findUserByUserName(String name) {
        Query query=new Query(Criteria.where("name").is(name));
        DemoInfo user =  mongoTemplate.findOne(query , DemoInfo.class);
        return user;
    }

    /**
     * 更新对象
     * @param user
     */
    public void updateUser(DemoInfo user) {
        Query query=new Query(Criteria.where("name").is(user.getName()));
        Update update= new Update().set("name", user.getName())
        						   .set("age", user.getAge());
        //更新查询返回结果集的第一条
        mongoTemplate.updateFirst(query,update,DemoInfo.class);
        //更新查询返回结果集的所有
        // mongoTemplate.updateMulti(query,update,UserEntity.class);
    }

    /**
     * 删除对象
     * @param id
     */
    public void deleteUserById(Long id) {
        Query query=new Query(Criteria.where("id").is(id));
        mongoTemplate.remove(query,DemoInfo.class);
    }
}
