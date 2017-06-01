package org.alvin.opsdev.monitor.system.utils;

import org.apache.log4j.Logger;
import org.assertj.core.util.Strings;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by tangzhichao on 2016/10/14.
 */
public class PropertiesUtils {

    private static Logger logger = Logger.getLogger(PropertiesUtils.class);

    public static Properties getProperties(String data) {
        Properties properties = new Properties();
        try {
            properties.load(new ByteArrayInputStream(data.trim().getBytes()));
        } catch (IOException e) {
            logger.error(e);
           // e.printStackTrace();
        }
        return properties;
    }

    public static Properties getPropertiesByFile(String fileName) {
        Properties properties = new Properties();
        Path path = Paths.get(fileName);
        if(Files.exists(path)){
            try(InputStream is = Files.newInputStream(path)){
                properties.load(is);
            }catch (Exception ex){
                logger.error(ex);
            }
        }else{
            try(InputStream is = PropertiesUtils.class.getResourceAsStream("/" + fileName)){
                properties.load(is);
            }catch (Exception ex){
                logger.error(ex);
            }
        }
        return properties;
    }


    public static long getValue(Properties properties, String key) {
        return Long.parseLong(properties.getProperty(key, "0"));
    }

    private static Pattern pattern = Pattern.compile("(\\d+),(\\d+)");

    public static long[] getValues(Properties properties, String key) {
        String value = properties.getProperty(key);
        if (Strings.isNullOrEmpty(value)) {
            return null;
        }
        Matcher matcher = pattern.matcher(value.trim());
        if (matcher.find()) {
            long item1 = Long.parseLong(matcher.group(1));
            long item2 = Long.parseLong(matcher.group(2));
            return new long[]{item1, item2};
        }
        return null;
    }
}
