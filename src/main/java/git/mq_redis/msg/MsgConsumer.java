package git.mq_redis.msg;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.ImmutableSet;
import com.google.common.reflect.ClassPath;
import com.lambdaworks.redis.KeyValue;
import com.lambdaworks.redis.RedisCommandTimeoutException;
import com.lambdaworks.redis.api.sync.RedisCommands;
import git.mq_redis.redis.RedisClientUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 由于多个类型的消息队列要同时消费，这里用多线程从每个
 * 类型的队列中取，
 * <p>
 * 然而取出来要怎么处理呢？？？？
 * 所以还需要一个处理方法，针对每个类型的消息进行处理。
 */
public class MsgConsumer {

    public static void main(String[] args) throws IOException, NoSuchMethodException, IllegalAccessException, InstantiationException {

        //①绑定事件类型和事件实体  即绑定队列名和队列元素
        Map<MsgType,List<AbsMsg>> msgTypeMap = new HashMap<>();
        // 扫描com.msg.msginstance下的包，获取消息实例
        ImmutableSet<ClassPath.ClassInfo> msgInstances = ClassPath.from(ClassLoader.getSystemClassLoader())
                .getTopLevelClasses("git.mq_redis.msg.msginstance");
        for (ClassPath.ClassInfo msgInstance : msgInstances) {
            AbsMsg absMsg = (AbsMsg)msgInstance.load().newInstance();
            MsgType msgType = absMsg.listenType();
            List<AbsMsg> orDefault = msgTypeMap.getOrDefault(msgType, new ArrayList<>());
            orDefault.add(absMsg);
            msgTypeMap.put(msgType,orDefault);
        }

        // 获取目前的队列个数
        int msgListLength = MsgType.values().length;
        ExecutorService executorService = Executors.newFixedThreadPool(msgListLength);
        // 开启多线程同时从这些消息队列中取
        for (MsgType msgType : MsgType.values()) {
            executorService.execute(() -> {
                RedisCommands sync = RedisClientUtils.getSingleRedisConnection().sync();
                while (true) {
                    KeyValue jsonValue;
                    try {
                        jsonValue = sync.brpop(10, msgType.toString());
                    }catch (RedisCommandTimeoutException e){
                        continue;
                    }
                    if(jsonValue==null) continue;
                    // 将jsonValue转为Msg对象 ：Json->Map->AbsMsg(多态)
                    Map objMap = (Map) JSONObject.parse(jsonValue.value.toString()); //
                    // ①现在有队列名，需要再外层将队列名与事件做绑定
                    List<AbsMsg> absMsgs = msgTypeMap.get(msgType);
                    for (AbsMsg absMsg : absMsgs) {
                        absMsg.deal(objMap);
                    }
                }
            });
        }

    }

}
