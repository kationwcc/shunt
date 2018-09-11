分流处理强任务 
------

* 注意:
* 1：默认占用cpu性能的80%。 （性能与cpu的核心数成正比）
* 2：要求单一处理的数据可以进行拆分。（如 List结构）
* 3：最终不乱序，处理之后会按原数据的顺序进行回调。
* 4：要求处理数据时需手动try可能会异常的代码，避免结果因异常的原因缺少。
* 5：使用0配置，但须要实现处理逻辑和回调逻辑。
* 6：依赖JDK1.8以上。

## 示例
执行逻辑实现 :
```java
public class ExecuterImpl extends Executer<String> {

    public List<String> execute(List<String> data) {
        for (int i = 0; i < data.size() ; i++ ){
            String str = data.get(i).toString() + " | handle success! ";
            data.set(i, str);
            try {
                Thread.sleep(1);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return data;
    }

}
```

分流测试示例：
```java
public class ShuntTest {

    public static long time = 0l;

    public static void main(String[] args) throws Exception{
        int size = 1 ;

        List<String> data = new ArrayList<String>(size);
        for (int i = 0; i < size ; i++ ){
            data.add("test data ("+ i +")");
        }

        ShuntTest.time = System.currentTimeMillis();
        ShuntTest.test1(data);
        //ShuntTest.test2(data);
    }

    /**
     * 高性能执行
     * @param data
     */
    public static void test1(List<String> data){

        ShuntRelay shuntRelay = (list) -> {
            for (int i = 0; i < list.size() ; i++ ){
                System.out.println(list.get(i));
            }
            System.out.println(System.currentTimeMillis() - ShuntTest.time);
            System.out.println(list.size());

        };

        ShuntExecuter<Integer, ExecuterImpl> shuntExecuter =
                new ShuntExecuter(data, ExecuterImpl.class, shuntRelay);
        shuntExecuter.shuntExecute();
    }
}
```
