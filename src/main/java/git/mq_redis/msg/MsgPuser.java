package git.mq_redis.msg;

import com.lambdaworks.redis.api.StatefulRedisConnection;
import com.lambdaworks.redis.api.sync.RedisCommands;
import git.mq_redis.redis.RedisClientUtils;

/**
 * 消息推送器，将消息lpush到一个消息类型的队列中
 */
public class MsgPuser {

    public static void push(MsgType msgType, AbsMsg msg) {
        StatefulRedisConnection connection = RedisClientUtils.getSingleRedisConnection();
        RedisCommands sync = connection.sync();
        String jsonValue = msg.map2Json();
        sync.lpush(msgType.name(), jsonValue);
    }


}
