package MsgTest;

import com.google.common.collect.ImmutableMap;
import git.mq_redis.msg.msginstance.MakeFriendMsg;

/**
 * Created by poan on 2017/06/07.
 */
public class TestMsgExtention {

    public static void main(String[] args) {

        MakeFriendMsg makeFriendMsg = new MakeFriendMsg(ImmutableMap.builder().put("gender", 1).put("name", "张三").put("age", 15).build());

        System.out.println(makeFriendMsg.map2Json());
    }
}
