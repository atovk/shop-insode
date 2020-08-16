package distributed;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Date;
import java.util.Random;


@Service
public class TaskService {

    private static Logger logger = LoggerFactory.getLogger(TaskService.class);

    @Autowired
    private RedisTemplate<String, String> strRedisTemplate;
    @Autowired
    private RedisTemplate<String, Serializable> serializableRedisTemplate;

    @Async
    @Scheduled(fixedRate = 500000)
    public void getTask() {

        ZSetOperations<String, String> strZSet = strRedisTemplate.opsForZSet();

        SetOperations<String, String> strSet = strRedisTemplate.opsForSet();

        HashOperations<String, Object, Object> hash = strRedisTemplate.opsForHash();
        hash.put("MAIN:task_12123124_892417", "9N72", new Date().toString());

        ListOperations<String, String> strList = strRedisTemplate.opsForList();
        String scheduledStr = strList.rightPop("Scheduled List");
        if (scheduledStr != null)
            logger.warn("exec get : {}, {}", new Date(), scheduledStr);
    }

    @Async
    //@Scheduled(fixedRate = 10000)
    public void putTask() {

        ZSetOperations<String, String> strZSet = strRedisTemplate.opsForZSet();

        SetOperations<String, String> strSet = strRedisTemplate.opsForSet();

        ListOperations<String, String> strList = strRedisTemplate.opsForList();

        strList.leftPush("Scheduled List", new Random().nextLong() + "");
        logger.warn("exec put : {}", new Date());


    }

}
