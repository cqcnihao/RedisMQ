package git.mq_redis.msg.msginstance;


import git.mq_redis.msg.AbsMsg;
import git.mq_redis.msg.MsgType;

import java.util.Map;

/**
 * Created by poan on 2017/06/07.
 */
public class StatisticsMsg extends AbsMsg {

    public enum Key{

    }

    public StatisticsMsg() {

    }

    public StatisticsMsg(Map jsonMap) {
        super(jsonMap);
    }

    @Override
    public MsgType listenType() {
        return MsgType.Statistics;
    }

    @Override
    public void deal(Map jsonMap) {
        System.out.println("统计奥，，" + jsonMap.get("requesttime"));
    }
}
