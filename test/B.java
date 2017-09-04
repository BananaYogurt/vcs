import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by nice on 2017/8/25.
 */
public class B extends A{

    private void print(String word){
        try {
            System.out.println(getClass());
            Method[] methods = getClass().getMethods();
            for(int i = 0 ; i < methods.length;i++)
                System.out.println(methods[i].getName());
            Class clazz = Class.forName("B");
            System.out.println(clazz == getClass());
            Method m = clazz.getMethod("say2",String.class);
            System.out.println("m ==== "+m);
            Method method =  getClass().getMethod("say2",String.class);
            method.invoke(clazz.newInstance(),word);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }  catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void say(String s) {
        super.say(s);
        System.out.println("/////");
        //throw new RuntimeException();
    }

    public void say2(String word){
        System.out.println(word+".....");
    }


    public static void main(String[] args) {
        B b = new B();
        b.print("mmnkljl");
        //http://tieba.baidu.com/p/5297572674?share=9105&fr=share
        //b.print(".......................iouo");
    }
}
