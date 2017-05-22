package in.ukd.mustache.example.bean;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.MustacheFactory;

/**
 * Created by udadh on 5/22/2017.
 */
public class Util {

    private static final MustacheFactory mf = new DefaultMustacheFactory();

    public static MustacheFactory getMustacheFactory(){
        return mf;
    }


}