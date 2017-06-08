package MsgTest;

import com.google.common.collect.ImmutableMap;
import git.mq_redis.msg.MsgPuser;
import git.mq_redis.msg.MsgType;
import git.mq_redis.msg.msginstance.MakeFriendMsg;
import git.mq_redis.msg.msginstance.StatisticsMsg;

import java.util.Date;

/**
 * Created by poan on 2017/06/07.
 * produce ：
 */
public class TestMsgPush {
    public static void main(String[] args) {
        int i = 0;
        while (i++ < 30) {
            MsgPuser.push(MsgType.MakeFriend, new MakeFriendMsg(ImmutableMap.builder()
                    .put(MakeFriendMsg.Key.fromuser.toString(), "用户" + i)
                    .put(MakeFriendMsg.Key.touser.toString(), "大波浪美女")
                    .build()));

            MsgPuser.push(MsgType.Statistics, new StatisticsMsg(ImmutableMap.builder().put("requesttime", new Date()).build()));
        }
    }
}
