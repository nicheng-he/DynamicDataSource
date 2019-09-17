package com.liu;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.liu.generator.entity.MyBatisPlusConfigEntity;
import com.liu.generator.utils.MyBatisPlusUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DynamicDataSourceApplicationTests {
    @Autowired
    MyBatisPlusUtil myBatisPlusUtil;
    @Autowired
    MyBatisPlusConfigEntity myBatisPlusConfigEntity;

    @Test
    public void contextLoads() {
//        myBatisPlusUtil.init();
        JSONObject jsonObject=JSON.parseObject(JSON.toJSONString(myBatisPlusConfigEntity));
        jsonObject.keySet().forEach(k -> {
            System.out.println(k+"\t"+jsonObject.get(k));
        });
    }
}
