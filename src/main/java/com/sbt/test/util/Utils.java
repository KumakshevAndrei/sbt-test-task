package com.sbt.test.util;

import com.sbt.test.data.dao.CommonDao;
import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Collection;

public class Utils {

    private static final Logger log = LoggerFactory.getLogger(Utils.class);

    private static volatile Utils instance;
    private static ClassPathXmlApplicationContext applicationContext;

    public static Utils getInstance() {
        Utils localInstance = instance;
        if (instance == null) {
            synchronized (Utils.class) {
                if (localInstance == null) {
                    localInstance = instance = new Utils();
                }
            }
        }
        return localInstance;
    }

    private Utils() {
        applicationContext = new ClassPathXmlApplicationContext(Constants.SPRING_CONFIG_LOCATION);
    }

    public void closeResources(){
        applicationContext.close();
    }

    public void finalize() throws Throwable {
        closeResources();
        super.finalize();
    }

    public String objectAsJsonString(Object object) {
        return objectAsJsonString(object, true);
    }

    public String objectAsJsonString(Object object, boolean prettyPrint) {
        if (object == null) {
            return "null";
        }
        if (object instanceof Collection) {
            JSONArray jsonArray;
            try {
                jsonArray = JSONArray.fromObject(object);
            } catch (Throwable e) {
                return String.valueOf(object);
            }
            return prettyPrint ? jsonArray.toString(2) : jsonArray.toString();
        }
        JSONObject jsonObject;
        try {
            jsonObject = JSONObject.fromObject(object);
        } catch (Throwable e) {
            return String.valueOf(object);
        }
        return prettyPrint
                ? jsonObject.toString(2)
                : jsonObject.toString();
    }

    public <T> CommonDao<T> getDao(Class<? extends CommonDao<T>> entityClass) {
        return applicationContext.getBean(entityClass);
    }

}
