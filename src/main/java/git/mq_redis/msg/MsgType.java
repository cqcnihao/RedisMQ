package git.mq_redis.msg;

/**
 * 消息队列的类型，每个类型对应一个Redis的List
 */
public enum MsgType {
    MakeFriend,//加好友
    Statistics,//统计
    ;
}
