package MsgTest;

import com.google.common.collect.ImmutableMap;
import git.mq_redis.msg.msginstance.MakeFriendMsg;

import java.util.Date;

/**
 * Created by poan on 2017/06/07.
 */
public class TestMsgExtention {

    public static void main(String[] args) {

        MakeFriendMsg makeFriendMsg = new MakeFriendMsg(ImmutableMap.builder()
                .put(MakeFriendMsg.Key.fromuser, "张三")
                .put(MakeFriendMsg.Key.touser, "李四")
                .build());

        System.out.println(makeFriendMsg.map2Json());
    }
}
