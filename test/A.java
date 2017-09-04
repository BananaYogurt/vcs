import java.util.Objects;

/**
 * Created by nice on 2017/8/25.
 */
public class A {

    public void say(String s){
        /*System.out.println("...");*/
        A a = new A();
        int i = 15;
        int b = i >> 2;
        //System.out.println(Objects.requireNonNull(a));
        System.out.println("b = "+b);
    }


}
