path=/usr/local/cl/search
tempfile=/usr/local/cl/search/tmp.txt
log=/usr/local/cl/search/createIndex.log

export JAVA_HOME=/usr/local/java/jdk1.7.0_45
export CLASSPATH=.:$JAVA_HOME/jre/lib/rt.jar:$JAVA_HOME/lib/dt.jar:$JAVA_HOME/lib/tools.jar
export PATH=$PATH:$JAVA_HOME/bin

export LC_ALL=zh_CN.UTF-8

date >> "$log"

if [ -f "$tempfile" ]; then
        echo '索引正在生成，尚未完成' >> "$log"
        echo '' >> "$log"
else
        echo '索引准备生成 ' >> "$log"
        echo '' >> "$log"
        touch "$tempfile"
        cd "$path"

        CLASSPATH=.

        for f in ./*.jar; do
                CLASSPATH=${CLASSPATH}:$f;
        done

        for f in lib/*.jar; do
                CLASSPATH=${CLASSPATH}:$f;
        done

        echo '索引开始生成......' >> "$log"
        echo '' >> "$log"
        java -Xms1024m -Xmx4096m -classpath $CLASSPATH com.cl.search.CreateIndex >> "$log"
        sleep 10
        rm "$tempfile"
        echo '索引生成完毕' >> "$log"
        echo '' >> "$log"
fi