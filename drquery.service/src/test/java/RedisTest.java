import com.asiainfo.billing.drquery.cache.redis.*;
import com.asiainfo.billing.drquery.cache.support.*;
import com.google.common.collect.Lists;
import org.junit.Test;
import org.springframework.context.*;
import org.springframework.context.support.*;

import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: lile3
 * Date: 13-12-13
 * Time: 下午4:38
 * To change this template use File | Settings | File Templates.
 */
public class RedisTest {


    @Test
	public void testCacheMap(){
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath*:drquery.service/spring/*.xml");
		RedisCache cache = (RedisCache)context.getBean("redisCache");
		RedisSwitch redisSwitch = (RedisSwitch)context.getBean("redisSwitch");
		redisSwitch.checkRedisStatus();
		String key = "privTest";
		Map map = new HashMap();
        map.put("8001", "test1");
        map.put("8002", "test2");
        map.put("8003", "test3");
        cache.putData2Cache(key, map, 20);
		System.out.println("done!");

        List<String> list = cache.getValue(key, Lists.asList("8001", new String[]{"8002", "8003"}));
        System.out.println(list.size());

	}
}
