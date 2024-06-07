package com.jun.plugin.compile.compile;

import cn.hutool.core.io.FileUtil;
import com.jun.plugin.compile.util.OSDetect;
//import com.ibeetl.olexec.util.OSDetect;
//import org.beetl.core.misc.BeetlUtil;

import javax.tools.*;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringSourceCompilerExtend {
    private Map<String, JavaFileObject> fileObjectMap = new HashMap<>();

    private String mainClassName;

    /** 使用 Pattern 预编译功能 */
    private Pattern CLASS_PATTERN = Pattern.compile("class\\s+([$_a-zA-Z][$_a-zA-Z0-9]*)\\s*");



    public boolean compile(String source, DiagnosticCollector<JavaFileObject> compileCollector) {


        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        if(compiler==null){
            throw new RuntimeException("JavaCompiler is null,please set JAVA_HOME to jdk/bin,not JRE");
        }

        JavaFileManager javaFileManager =
                new TmpJavaFileManager(fileObjectMap,compiler.getStandardFileManager(compileCollector, null, null));

        // 从源码字符串中匹配类名
        Matcher matcher = CLASS_PATTERN.matcher(source);
        if (matcher.find()) {
            mainClassName = matcher.group(1);
        } else {
            throw new IllegalArgumentException("No valid class");
        }

        // 把源码字符串构造成JavaFileObject，供编译使用
        JavaFileObject sourceJavaFileObject = new TmpJavaFileObject(mainClassName, source);

		List<String> optionList = new ArrayList<String>();

        String classPath = getClassPath();
        if(classPath!=null){
            //ide 开发环境为空，tomcat下classPath有值
            optionList.addAll(Arrays.asList("-classpath",classPath));
        }

        optionList.add("-parameters");

		Boolean result = compiler.getTask(null, javaFileManager, compileCollector,
				optionList, null, Arrays.asList(sourceJavaFileObject)).call();

        JavaFileObject bytesJavaFileObject = fileObjectMap.get(mainClassName);
        if (result && bytesJavaFileObject != null) {
            return true;
        }
        return false;
    }



    public Map<String, JavaFileObject> getFileObjectMap() {
        return fileObjectMap;
    }

    public void setFileObjectMap(Map<String, JavaFileObject> fileObjectMap) {
        this.fileObjectMap = fileObjectMap;
    }

    public String getMainClassName() {
        return mainClassName;
    }

    public void setMainClassName(String mainClassName) {
        this.mainClassName = mainClassName;
    }


    protected String getClassPath(){


        String libPath   = null;
        String limiter = OSDetect.PATH_DELEMETER;
        try{
            libPath = getWebRoot() + File.separator+"WEB-INF/lib";
            if(!new File(libPath).exists()){
                return null;
            }else{
                List<String> libPathList =  new ArrayList<String>();
                String classesPath = getWebRoot() + File.separator+"WEB-INF/classes";
                List<File> fileList = FileUtil.loopFiles(FileUtil.file(libPath));
                for (File file : fileList) {
                    libPathList.add(file.getPath());
                }
                String base =  System.getProperty("java.class.path");
                StringBuilder pathBuilder = new StringBuilder(base);
                pathBuilder.append(limiter).append(classesPath);
                libPathList.forEach(p->{
                    pathBuilder.append(limiter).append(p);
                });
                return pathBuilder.toString();
            }

        }catch (RuntimeException runtimeException){
            //忽略，也许不是标准的WEB目录，比如在IDEA里
            return null;

        }

    }

    public static String getWebRoot() {
        try {
            String path = StringSourceCompilerExtend.class.getClassLoader().getResource("").toURI().getPath();
            if (path == null) {
                throw new RuntimeException("未能自动检测到WebRoot，请手工指定WebRoot路径");
            } else {
                File file = new File(path);
                String root = null;
                if (file.getParentFile() != null && file.getParentFile().getParentFile() != null) {
                    root = file.getParentFile().getParentFile().getCanonicalPath();
                    return root;
                } else {
                    throw new RuntimeException("检测到Beetl.jar位于" + path + "但未能自动检测到WebRoot，请手工指定WebRoot路径");
                }
            }
        } catch (URISyntaxException | IOException var3) {
            throw new RuntimeException(var3);
        }
    }

    /**
     * 管理JavaFileObject对象的工具
     */
    public static class TmpJavaFileManager extends ForwardingJavaFileManager<JavaFileManager> {
        Map<String, JavaFileObject> fileObjectMap = null;
        protected TmpJavaFileManager(Map<String, JavaFileObject> fileObjectMap,JavaFileManager fileManager) {
            super(fileManager);
            this.fileObjectMap = fileObjectMap;
        }

        @Override
        public JavaFileObject getJavaFileForInput(Location location, String className, JavaFileObject.Kind kind) throws IOException {
            JavaFileObject javaFileObject = fileObjectMap.get(className);
            if (javaFileObject == null) {
                return super.getJavaFileForInput(location, className, kind);
            }
            return javaFileObject;
        }

        @Override
        public JavaFileObject getJavaFileForOutput(Location location, String className, JavaFileObject.Kind kind, FileObject sibling) throws IOException {
            JavaFileObject javaFileObject = new TmpJavaFileObject(className, kind);
            fileObjectMap.put(className, javaFileObject);
            return javaFileObject;
        }
    }



    /**
     * 用来封装表示源码与字节码的对象
     */
    public static class TmpJavaFileObject extends SimpleJavaFileObject {
        private String source;
        private ByteArrayOutputStream outputStream;

        /**
         * 构造用来存储源代码的JavaFileObject
         * 需要传入源码source，然后调用父类的构造方法创建kind = Kind.SOURCE的JavaFileObject对象
         */
        public TmpJavaFileObject(String name, String source) {
            super(URI.create("String:///" + name + Kind.SOURCE.extension), Kind.SOURCE);
            this.source = source;
        }

        /**
         * 构造用来存储字节码的JavaFileObject
         * 需要传入kind，即我们想要构建一个存储什么类型文件的JavaFileObject
         */
        public TmpJavaFileObject(String name, Kind kind) {
            super(URI.create("String:///" + name + Kind.SOURCE.extension), kind);
            this.source = null;
        }

        @Override
        public CharSequence getCharContent(boolean ignoreEncodingErrors) throws IOException {
            if (source == null) {
                throw new IllegalArgumentException("source == null");
            }
            return source;
        }

        @Override
        public OutputStream openOutputStream() throws IOException {
            outputStream = new ByteArrayOutputStream();
            return outputStream;
        }

        public byte[] getCompiledBytes() {
            return outputStream.toByteArray();
        }


    }
}
