package com.btcdata.service;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Component;

import com.btcdata.entity.DemoInfo;

/**
 * 继承自MongoRepository接口，MongoRepository接口包含了常用的CRUD操作，
 * 例如：save、insert、findall等等。我们可以定义自己的查找接口，
 * 例如根据demoInfo的name搜索，具体的DemoInfoRepository接口代码如下：
 * @author hzf
 *
 */
//@Component
public interface DemoInfoInterface extends MongoRepository<DemoInfo, Long>{

	@Query(value="{'name':?0}")
	DemoInfo findByName(String name);
}
