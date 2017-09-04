import com.vcs.util.Result;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * created by 吴震煌 on 2017/3/13 17:05
 */
public class TestEnum {

    public static final String PACKAGE_PREFIX = "com.vcs.command.";

    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException, InstantiationException {
        System.out.println(test("Ls",""));

    }

    public static String test(String instanceName,String cmd) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException, InstantiationException {
        Class<?> clazz = Class.forName(PACKAGE_PREFIX+ instanceName);
        Object o = clazz.newInstance();
        Method execute = clazz.getMethod("execute",String.class);
        Object args = new String(cmd);
        Result result = (Result)execute.invoke(o,args);
        if(result.isError){
            return  result.getMessage();
        }else{
            return  result.getOutPut();
        }
    }
}
