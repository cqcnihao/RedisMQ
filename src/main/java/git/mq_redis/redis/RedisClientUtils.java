package git.mq_redis.redis;

import com.lambdaworks.redis.RedisClient;
import com.lambdaworks.redis.api.StatefulRedisConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by poan on 2017/06/07.
 */
public class RedisClientUtils {

    // lettuce是线程安全的，可以使用一个链接进行CRUD操作，所以不用连接池。直接用单例来创建一个连接

    private static StatefulRedisConnection connection;
    public static ReadWriteLock lock = new ReentrantReadWriteLock();

    private static Logger logger = LoggerFactory.getLogger(RedisClientUtils.class);

    public static StatefulRedisConnection getSingleRedisConnection() {
        if (connection == null) {
            try {
                lock.writeLock().lock();
                if (connection == null) {
                    // 新建客户端
                    RedisClient redisClient = RedisClient.create("redis://Pbx199299@47.93.9.228:6379");
                    connection = redisClient.connect();// 连接
                }
            } catch (Exception e) {
                logger.error("Ops error {}", e.getCause());
            } finally {
                lock.writeLock().unlock();
            }
        }
        return connection;
    }

}
