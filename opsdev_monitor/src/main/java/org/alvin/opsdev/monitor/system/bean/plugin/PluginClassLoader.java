package org.alvin.opsdev.monitor.system.bean.plugin;

import com.google.common.collect.Maps;
import org.alvin.opsdev.monitor.system.bean.collector.impl.PluginCollector;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Map;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;

/**
 * Created by tangzhichao on 2017/5/31.
 */
@Component
public class PluginClassLoader extends URLClassLoader {

    public static final String PLUGIN_CONFIG = "plugin.xml";
    private int fileCount = 0;
    private int loaded = 0;                                         //  fileCount ： 文件个数， loaded  :  加载进度

    public PluginClassLoader() {
        super(new URL[]{});
    }

    /**
     * 到用户目录下加载文件
     *
     * @author zhxu
     * @date 2012.8.6
     */
    private void addLibraries() {
        try {
            String dir = System.getProperty("user.dir");
            File file = new File(dir,"plugins");
            // 计算用户目录下文件的个数
            calculateFileCount(file);
            // 加载文件
            loadFile(file);
        } catch (Exception ex) {
            Logger.getLogger(PluginClassLoader.class.getName()).log(Level.ERROR, null, ex);
        }
    }

    /**
     * 计算用户目录下文件的个数
     *
     * @param file
     */
    private void calculateFileCount(File file) {
        if (file.isFile()) {
            fileCount++;
        } else {
            File[] files = file.listFiles();
            if (files != null) {
                for (File f : files) {
                    calculateFileCount(f);
                }
            }
        }
    }

    /**
     * 加载资源文件。如果是server则提取服务相关信息。
     *
     * @param file 资源文件
     * @throws MalformedURLException 错误的URL
     */
    protected void addFile(File file) throws MalformedURLException {
        loaded++;
        addURL(file.toURI().toURL());
        System.out.println(file.getAbsolutePath());
        if (file.getName().endsWith("jar")) {
            try {
                JarFile jarFile = new JarFile(file);
                ZipEntry entry = jarFile.getEntry(PLUGIN_CONFIG);
                if (entry != null) {
                    InputStream inputStream = jarFile.getInputStream(entry);
                    parseConfiguration(inputStream);
                }

            } catch (Exception ex) {
                Logger.getLogger(PluginClassLoader.class.getName()).log(Level.ERROR, null, ex);
            }
        }
    }

    /**
     * 解析config.xml中的配置信息
     *
     * @param inputStream
     */
    public void parseConfiguration(InputStream inputStream) {
//        try {
//            DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
//            Document document = documentBuilder.parse(inputStream);
//            NodeList modules = document.getElementsByTagName("module");
//            for (int i = 0; i < modules.getLength(); i++) {
//                Node node = modules.item(i);
//                Node attribute = node.getAttributes().getNamedItem("class");
//                if (attribute != null) {
//                    moduleList.add(attribute.getNodeValue());
//                }
//            }
//            NodeList components = document.getElementsByTagName("component");
//            for (int i = 0; i < components.getLength(); i++) {
//                Node node = components.item(i);
//                Node attribute = node.getAttributes().getNamedItem("class");
//                if (attribute != null) {
//                    componentList.add(attribute.getNodeValue());
//                }
//            }
//            NodeList actions = document.getElementsByTagName("action");
//            for (int i = 0; i < actions.getLength(); i++) {
//                Node node = actions.item(i);
//                String[] strs = new String[5];
//                Node attribute = node.getAttributes().getNamedItem("class");
//                if (attribute != null) {
//                    strs[0] = attribute.getNodeValue();
//                }
//                attribute = node.getAttributes().getNamedItem("type");
//                if (attribute != null) {
//                    strs[1] = attribute.getNodeValue();
//                }
//                attribute = node.getAttributes().getNamedItem("catelog");
//                if (attribute != null) {
//                    strs[2] = attribute.getNodeValue();
//                }
//                attribute = node.getAttributes().getNamedItem("index");
//                if (attribute != null) {
//                    strs[3] = attribute.getNodeValue();
//                }
//                attribute = node.getAttributes().getNamedItem("separator");
//                if (attribute != null) {
//                    strs[4] = attribute.getNodeValue();
//                }
//                getActionList().add(strs);
//            }
//        } catch (Exception ex) {
//            Logger.getLogger(PluginClassLoader.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }

    /**
     * 开始加载文件
     *
     * @param file
     * @throws MalformedURLException
     */
    private void loadFile(File file) throws MalformedURLException {
        if (file.isFile()) {
            // 加载文件
            addFile(file);
        } else {
            // 加载子目录下的文件
            File[] files = file.listFiles();
            if (files != null) {
                for (File f : files) {
                    loadFile(f);
                }
            }
        }
    }


    /**
     * 插件加载
     */
    public Map<Long,PluginCollector> loadPlugins() {
        addLibraries();
        return Maps.newHashMap();
    }
}
