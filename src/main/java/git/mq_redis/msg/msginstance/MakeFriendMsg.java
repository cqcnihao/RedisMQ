package git.mq_redis.msg.msginstance;


import git.mq_redis.msg.AbsMsg;
import git.mq_redis.msg.MsgType;

import java.util.Map;

/**
 * Created by poan on 2017/06/07.
 * 加好的消息类型
 */
public class MakeFriendMsg extends AbsMsg {

    public enum Key{
        fromuser,
        touser,
    }

    public MakeFriendMsg() {

    }

    public MakeFriendMsg(Map jsonMap) {
        super(jsonMap);
    }

    public void deal(Map jsonMap) {
        //
        System.out.println(jsonMap.get(Key.fromuser.toString()) + "想和" + jsonMap.get(Key.touser.toString())
                + "成为好友" );

    }

    public MsgType listenType(){
        return MsgType.MakeFriend;
    }

}
