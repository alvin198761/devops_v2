//package org.alvin.opsdev.monitor.service;
//
//import org.alvin.opsdev.monitor.bean.collector.ICollector;
//
//public class InjectService {
//
//    private Map<ObjectType, ICollector> collectorInjectMap = new HashMap<>();
//    private Map<ObjectType, Method> defaultMetricMethodMap = new HashMap<>();
//
//    @Autowired
//    private ApplicationContext applicationContext;
//
//    @PostConstruct
//    public void init() {
//        try {
//            List<Class> collectorClass = scanPackages("com.snda.sysdev.gpush.processor.bean.collect.impl");
//            List<Class> parserClass = scanPackages("com.snda.sysdev.gpush.processor.bean.parser");
//            injectCollector(collectorClass, parserClass);
//            scanDefaultMetricMethods();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    private void scanDefaultMetricMethods() {
//        Method[] methods = MetricHelper.class.getMethods();
//        for (Method m : methods) {
//            Annotation annotation = m.getAnnotation(InitMetrics.class);
//            if (annotation == null) {
//                continue;
//            }
//            ObjectType objectType = getAnnotationValue(annotation);
//            if (objectType == null) {
//                continue;
//            }
//            defaultMetricMethodMap.put(objectType, m);
//        }
//    }
//
//    private void injectCollector(List<Class> collectorClass, List<Class> parserClass) {
//        final Map<ObjectType, Class> parserMap = fromClassMap(parserClass, Parser.class, AbstractParser.class);
//        Map<ObjectType, Class> collectorMap = fromClassMap(collectorClass, Collector.class, BaseCollector.class);
//        for (final Map.Entry<ObjectType, Class> entry : collectorMap.entrySet()) {
//            final ICollector collector = (ICollector) applicationContext.getBean(entry.getValue());
//            if (collector == null) {
//                continue;
//            }
//            AccessController.doPrivileged(new PrivilegedAction<Void>() {
//
//                @Override
//                public Void run() {
//                    try {
//                        Method method = ICollector.class.getMethod("setParser", AbstractParser.class);
//                        AbstractParser parser = (AbstractParser) applicationContext.getBean(parserMap.get(entry.getKey()));
//                        method.invoke(collector, parser);
//                        injectFiled(collector);
//                        collectorInjectMap.put(entry.getKey(), collector);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                    return null;
//                }
//                //为了简单，这里写死为BaseCollector ,如果以后子类需要定义，就自己赋值
//                private void injectFiled(ICollector collector) throws IllegalAccessException {
//                    for (Field f : BaseCollector.class.getDeclaredFields()) {
//                        Annotation annotation = f.getAnnotation(InjectFiled.class);
//                        if (annotation == null) {
//                            continue;
//                        }
//                        boolean access = f.isAccessible();
//                        Object instance = applicationContext.getBean(f.getType());
//                        f.setAccessible(true);
//                        f.set(collector, instance);
//                        f.setAccessible(access);
//                    }
//                }
//            });
//        }
//    }
//
//    private Map<ObjectType, Class> fromClassMap(List<Class> classList, Class annotationClass, Class checkClass) {
//        Map<ObjectType, Class> parserMap = new HashMap<>();
//        loop:
//        for (int i = 0 ; i < classList.size() ;i++) {
//            //递归获取上级class
//            Class cls = classList.get(i);
//            //忽略抽象类
//            if(Modifier.isAbstract(cls.getModifiers())){
//                continue ;
//            }
//            //类型检查
//            while (true) {
//                Class baseClass = cls.getSuperclass();
//                if (baseClass == null || baseClass.equals(Object.class)) {
//                     continue loop;
//                }
//                if (baseClass.equals(checkClass)) {
//                    break;
//                }
//                cls = baseClass;
//            }
//
//            Annotation annotation = classList.get(i).getAnnotation(annotationClass);
//            if (annotation == null) {
//                continue;
//            }
//            ObjectType type = getAnnotationValue(annotation);
//            if (type != null) {
//                parserMap.put(type, classList.get(i));
//            }
//        }
//        return parserMap;
//    }
//
//    private ObjectType getAnnotationValue(final Annotation annotation) {
//        return AccessController.doPrivileged(new PrivilegedAction<ObjectType>() {
//
//            @Override
//            public ObjectType run() {
//                try {
//                    Method method = annotation.getClass().getMethod("value");
//                    boolean access = method.isAccessible();
//                    try {
//                        method.setAccessible(true);
//                        return (ObjectType) method.invoke(annotation);
//                    } finally {
//                        method.setAccessible(access);
//                    }
//                } catch (NoSuchMethodException e) {
//                    e.printStackTrace();
//                } catch (InvocationTargetException e) {
//                    e.printStackTrace();
//                } catch (IllegalAccessException e) {
//                    e.printStackTrace();
//                }
//                return null;
//            }
//        });
//
//
//    }
//
//
//    /**
//     * 扫描业务类
//     *
//     * @param packages
//     * @throws Exception
//     */
//    public List<Class> scanPackages(String packages) throws Exception {
//        List<Class> list = Lists.newArrayList();
//        try {
//            String packageName = packages;
//            packages = packages.replaceAll("[.]", "/");
//            URL baseURL = Thread.currentThread().getContextClassLoader().getResource(packages);
//            if ("file".equals(baseURL.getProtocol())) {
//                list.addAll(doDevScan(baseURL, packageName));
//            } else if ("jar".equals(baseURL.getProtocol())) {
//                list.addAll(doRuntimeScan(baseURL, packageName));
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return list;
//    }
//
//
//    /**
//     * 运行时只会通过这个方法调用
//     *
//     * @param baseURL
//     * @param packageName
//     * @throws IOException
//     * @throws ClassNotFoundException
//     */
//    private static List<Class> doRuntimeScan(URL baseURL, String packageName)
//            throws IOException, ClassNotFoundException {
//        List<Class> classList = new ArrayList<Class>();
//        //
//        JarFile jar = ((JarURLConnection) baseURL.openConnection()).getJarFile();
//        Enumeration<JarEntry> entries = jar.entries();
//        while (entries.hasMoreElements()) {
//            JarEntry je = entries.nextElement();
//            String urlName = je.getName().replace('/', '.');
//            if (!urlName.startsWith(packageName)) {
//                continue;
//            }
//            if (!urlName.endsWith(".class")) {
//                continue;
//            }
//            String classUrl = urlName.substring(0, urlName.length() - 6);
//            classList.add(Class.forName(classUrl));
//        }
//        return classList;
//    }
//
//    /**
//     * 开发时会用到的方法
//     *
//     * @param baseURL
//     * @param packageName
//     * @throws Exception
//     */
//    private static List<Class> doDevScan(URL baseURL, String packageName) throws Exception {
//        String filePath = URLDecoder.decode(baseURL.getFile(), "UTF-8");
//        List<Class> classList = new ArrayList<>();
//
//        LinkedList<File> files = new LinkedList<File>();
//        File dir = new File(filePath);
//        if (!dir.exists() || !dir.isDirectory()) {
//            throw new Exception("没有找到对应的包");
//        }
//        files.add(dir);
//        // 循环读取目录以及子目录下的所有类文件
//        while (!files.isEmpty()) {
//            File f = files.removeFirst();
//            if (f.isDirectory()) {
//                File[] fs = f.listFiles();
//                int i = 0;
//                for (File childFile : fs) {
//                    files.add(i++, childFile);
//                }
//                continue;
//            }
//            String subPath = f.getAbsolutePath().substring(dir.getAbsolutePath().length());
//            String classUrl = packageName + subPath.replace(File.separatorChar, '.');
//            classUrl = classUrl.substring(0, classUrl.length() - 6);
//            classList.add(Class.forName(classUrl));
//        }
//        return classList;
//    }
//
//    public ICollector getCollector(ObjectType type) {
//        return this.collectorInjectMap.get(type);
//    }
//
//    public List<Metric> getDefaultMetricByType(final ObjectType type) {
//        final MetricHelper metricHelper = applicationContext.getBean(MetricHelper.class);
//        return AccessController.doPrivileged(new PrivilegedAction<List<Metric>>() {
//
//            @Override
//            public List<Metric> run() {
//                Method m = defaultMetricMethodMap.get(type);
//                if (m == null) {
//                    return null;
//                }
//                boolean access = m.isAccessible();
//                try {
//                    m.setAccessible(true);
//                    return (List<Metric>) m.invoke(metricHelper);
//                } catch (IllegalAccessException e) {
//                    e.printStackTrace();
//                } catch (InvocationTargetException e) {
//                    e.printStackTrace();
//                } finally {
//                    m.setAccessible(access);
//                }
//                return null;
//            }
//
//        });
//    }
//}