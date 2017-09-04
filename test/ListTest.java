import java.util.ArrayList;
import java.util.List;

/**
 * created by wuzh on 2017/5/10 13:45
 */
public class ListTest {
    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        for(int i = list.size() - 1; i >=0 ;i--){
            System.out.println(list.get(i));
        }
    }
}
