java -server -Xmx2048m -Xms2048m -XX:+UseParallelGC -XX:ParallelGCThreads=8 -jar redis-proxy-server.jar -group default -min_threads=50 -max_threads=200 &
echo $!>pid.cache
