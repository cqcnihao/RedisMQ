package git.mq_redis.msg;

import com.alibaba.fastjson.JSON;
import java.util.Map;

/**
 * Created by poan on 2017/06/07.
 * 该基类其实是为了反射能用到，，
 * 1 “事件”这种抽象的行为往消息队列中插入时，只能
 * 先将其转换为json形式
 * 2 消息队列中json事件，会被消费者来处理，而消费者
 * 取出来的只能是一个json字符串。。。 所以需要将其
 * 转换为实际的“事件”，
 */
public abstract class AbsMsg {

    protected Map<String,Object> jsonMap;

    // 子类要通过无参构造器来发射创建实例，而子类的无参构造器调用时会默认调用父类的无参构造器
    // 所以父类中提供一个无参构造器来供自类反射创建时，不会报错
    public AbsMsg(){

    }

    //1 通过子类来为jsonMap赋值
    public AbsMsg(Map<String, Object> jsonMap) {
        this.jsonMap = jsonMap;
    }

    //2 为子类提供一个将Map转为json的方法
    public String map2Json(){
        return String.valueOf(JSON.toJSON(jsonMap));
    }

    //3 要求子类消息需绑定对应事件类型，即属于哪个redis的List；
    public abstract MsgType listenType();

    public abstract void deal(Map jsonMap);

}
