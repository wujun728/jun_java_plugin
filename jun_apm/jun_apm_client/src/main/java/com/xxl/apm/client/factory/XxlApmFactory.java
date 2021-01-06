package com.xxl.apm.client.factory;

import com.xxl.apm.client.XxlApm;
import com.xxl.apm.client.admin.XxlApmMsgService;
import com.xxl.apm.client.message.XxlApmMsg;
import com.xxl.apm.client.message.impl.XxlApmEvent;
import com.xxl.apm.client.message.impl.XxlApmHeartbeat;
import com.xxl.apm.client.message.impl.XxlApmTransaction;
import com.xxl.apm.client.util.FileUtil;
import com.xxl.rpc.registry.impl.XxlRegistryServiceRegistry;
import com.xxl.rpc.remoting.invoker.XxlRpcInvokerFactory;
import com.xxl.rpc.remoting.invoker.call.CallType;
import com.xxl.rpc.remoting.invoker.reference.XxlRpcReferenceBean;
import com.xxl.rpc.remoting.invoker.route.LoadBalance;
import com.xxl.rpc.remoting.net.NetEnum;
import com.xxl.rpc.serialize.Serializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author xuxueli 2019-01-15
 */
public class XxlApmFactory {
    private static final Logger logger = LoggerFactory.getLogger(XxlApmFactory.class);

    // ---------------------- field ----------------------

    private String appname;
    private String address;
    private String adminAddress;
    private String accessToken;
    private String msglogpath = "/data/applogs/xxl-apm/msglogpath";

    public void setAppname(String appname) {
        this.appname = appname;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setAdminAddress(String adminAddress) {
        this.adminAddress = adminAddress;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public void setMsglogpath(String msglogpath) {
        this.msglogpath = msglogpath;
    }

    public String getAppname() {
        return appname;
    }

    public String getAddress() {
        return address;
    }

    // ---------------------- start、stop ----------------------

    public void start(){
        // valid
        if (appname==null || appname.trim().length()==0) {
            throw new RuntimeException("xxl-apm, appname cannot be empty.");
        }
        if (msglogpath==null || msglogpath.trim().length()==0) {
            throw new RuntimeException("xxl-apm, msglogpath cannot be empty.");
        }

        // msglogpath
        File msglogpathDir = new File(msglogpath);
        if (!msglogpathDir.exists()) {
            msglogpathDir.mkdirs();
        }

        // start XxlApmMsgService
        startApmMsgService(adminAddress, accessToken);

        // generate XxlApm
        XxlApm.setInstance(this);

        logger.info(">>>>>>>>>>> xxl-apm start.");
    }

    public void stop(){
        // stop XxlApmMsgService
        stopApmMsgService();

        logger.info(">>>>>>>>>>> xxl-apm stop.");
    }


    // ---------------------- MsgId ----------------------

    public final ThreadLocal<String> parentMsgId = new ThreadLocal<String>();

    public String generateMsgId(){
        String newMsgId = appname.concat("-").concat(UUID.randomUUID().toString().replaceAll("-", ""));
        return newMsgId;
    }


    // ---------------------- apm msg service ----------------------

    private XxlRpcInvokerFactory xxlRpcInvokerFactory;
    private XxlApmMsgService xxlApmMsgService;
    private Serializer serializer;

    private ExecutorService innerThreadPool = Executors.newCachedThreadPool();
    public volatile boolean innerThreadPoolStoped = false;

    private LinkedBlockingQueue<XxlApmMsg> newMessageQueue = new LinkedBlockingQueue<>();
    private int newMessageQueueMax = 10000;
    private int batchReportNum = 500;

    private volatile File msgFileDir = null;
    private Object msgFileDirLock = new Object();

    /**
     * async report msg
     *
     * @param msgList
     * @return
     */
    public boolean report(List<XxlApmMsg> msgList) {
        newMessageQueue.addAll(msgList);
        return true;
    }


    // start stop
    private void startApmMsgService(final String adminAddress, final String accessToken){
        // start invoker factory
        xxlRpcInvokerFactory = new XxlRpcInvokerFactory(XxlRegistryServiceRegistry.class, new HashMap<String, String>(){{
            put(XxlRegistryServiceRegistry.XXL_REGISTRY_ADDRESS, adminAddress);
            put(XxlRegistryServiceRegistry.ACCESS_TOKEN, accessToken);
        }});
        try {
            xxlRpcInvokerFactory.start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // apm msg service
        xxlApmMsgService = (XxlApmMsgService) new XxlRpcReferenceBean(
                NetEnum.NETTY,
                Serializer.SerializeEnum.HESSIAN.getSerializer(),
                CallType.SYNC,
                LoadBalance.ROUND,
                XxlApmMsgService.class,
                null,
                10000,
                null,
                null,
                null,
                xxlRpcInvokerFactory).getObject();
        serializer = Serializer.SerializeEnum.HESSIAN.getSerializer();


        // apm msg remote report thread, report-fail or queue-max, write msg-file
        for (int i = 0; i < 5; i++) {
            innerThreadPool.execute(new Runnable() {
                @Override
                public void run() {

                    while (!innerThreadPoolStoped) {
                        List<XxlApmMsg> messageList = new ArrayList<>();
                        try {
                            XxlApmMsg message = newMessageQueue.take();
                            if (message != null) {

                                // load
                                messageList.clear();
                                messageList.add(message);

                                // attempt to process mult msg
                                List<XxlApmMsg> messageList_tmp = new ArrayList<>();
                                int drainToNum = newMessageQueue.drainTo(messageList_tmp, batchReportNum);
                                if (drainToNum > 0) {
                                    messageList.addAll(messageList_tmp);
                                }

                                // msg too small, just wait 1s, avoid process too quick
                                if (messageList.size() < batchReportNum) {
                                    TimeUnit.SECONDS.sleep(1);

                                    messageList_tmp.clear();
                                    drainToNum = newMessageQueue.drainTo(messageList_tmp, batchReportNum-messageList.size());
                                    if (drainToNum > 0) {
                                        messageList.addAll(messageList_tmp);
                                    }
                                }

                                // queue small to process；queue large, quick move to msg-file
                                if (newMessageQueue.size() < newMessageQueueMax) {
                                    // report
                                    boolean ret = xxlApmMsgService.report(messageList);
                                    if (ret) {
                                        messageList.clear();
                                    }
                                }

                            }
                        } catch (Exception e) {
                            if (!innerThreadPoolStoped) {
                                logger.error(e.getMessage(), e);
                            }
                        } finally {

                            // process-fail or queue-max, write msg-file
                            if (messageList!=null && messageList.size()>0) {

                                writeMsgFile(messageList);
                                messageList.clear();
                            }
                        }
                    }

                    // finally total write msg-file
                    List<XxlApmMsg> messageList = new ArrayList<>();
                    int drainToNum = newMessageQueue.drainTo(messageList);
                    if (drainToNum> 0) {
                        boolean ret = xxlApmMsgService.report(messageList);
                        if (!ret) {

                            // app stop, write msg-file
                            writeMsgFile(messageList);
                            messageList.clear();
                        }
                    }

                }
            });
        }

        // apm msg-file retry remote report thread, cycle retry
        innerThreadPool.execute(new Runnable() {
            @Override
            public void run() {

                while (!innerThreadPoolStoped) {

                    int waitTim = 3;
                    try {
                        boolean beatResult = xxlApmMsgService.beat();

                        File msglogpathDir = new File(msglogpath);
                        if (beatResult && msglogpathDir.list()!=null && msglogpathDir.list().length>0) {
                            waitTim = 3;

                            // {msglogpath}/{timestamp}
                            for (File msgFileDir : msglogpathDir.listFiles()) {

                                // clean invalid file
                                if (msgFileDir.isFile()) {
                                    msgFileDir.delete();
                                    continue;
                                }
                                // clean empty dir
                                if (!(msgFileDir.list()!=null && msgFileDir.list().length>0)) {
                                    msgFileDir.delete();
                                    continue;
                                }

                                // {msglogpath}/{timestamp}/xxxxxx
                                for (File fileItem: msgFileDir.listFiles()) {

                                    Class<? extends XxlApmMsg> msgType = null;
                                    if (fileItem.getName().startsWith(XxlApmEvent.class.getSimpleName())) {
                                        msgType = XxlApmEvent.class;
                                    } else if (fileItem.getName().startsWith(XxlApmTransaction.class.getSimpleName())) {
                                        msgType = XxlApmTransaction.class;
                                    } else if (fileItem.getName().startsWith(XxlApmHeartbeat.class.getSimpleName())) {
                                        msgType = XxlApmHeartbeat.class;
                                    } else {
                                        fileItem.delete();
                                        continue;
                                    }

                                    try {

                                        // read msg-file
                                        byte[] serialize_data = FileUtil.readFileContent(fileItem);
                                        List<XxlApmMsg> messageList = (List<XxlApmMsg>) serializer.deserialize(serialize_data, msgType);

                                        // retry report
                                        boolean ret = xxlApmMsgService.report(messageList);

                                        // delete
                                        if (ret) {
                                            fileItem.delete();
                                        }
                                    } catch (Exception e) {
                                        if (!innerThreadPoolStoped) {
                                            logger.error(e.getMessage(), e);
                                        }
                                    }

                                }

                            }

                        } else {
                            waitTim = (waitTim+5<=60)?(waitTim+5):60;
                        }

                    } catch (Exception e) {
                        if (!innerThreadPoolStoped) {
                            logger.error(e.getMessage(), e);
                        }

                    }

                    // wait
                    try {
                        TimeUnit.SECONDS.sleep(waitTim);
                    } catch (Exception e) {
                        if (!innerThreadPoolStoped) {
                            logger.error(e.getMessage(), e);
                        }
                    }

                }

            }
        });


        // heartbeat thread, cycle report for 1min ("heartbeat" for 1min, other "event、transaction" for real-time)
        innerThreadPool.execute(new Runnable() {
            @Override
            public void run() {

                // align to minute
                try {
                    long sleepSecond = 0;
                    Calendar nextMin = Calendar.getInstance();
                    nextMin.add(Calendar.MINUTE, 1);
                    nextMin.set(Calendar.SECOND, 0);
                    nextMin.set(Calendar.MILLISECOND, 0);
                    sleepSecond = (nextMin.getTime().getTime() - System.currentTimeMillis())/1000;
                    if (sleepSecond>0 && sleepSecond<60) {
                        TimeUnit.SECONDS.sleep(sleepSecond);
                    }
                } catch (Exception e) {
                    if (!innerThreadPoolStoped) {
                        logger.error(e.getMessage(), e);
                    }
                }

                while (!innerThreadPoolStoped) {
                    // heartbeat report
                    try {
                        XxlApm.report(new XxlApmHeartbeat());
                    } catch (Exception e) {
                        if (!innerThreadPoolStoped) {
                            logger.error(e.getMessage(), e);
                        }
                    }
                    // wait
                    try {
                        TimeUnit.MINUTES.sleep(1);
                    } catch (Exception e) {
                        if (!innerThreadPoolStoped) {
                            logger.error(e.getMessage(), e);
                        }
                    }
                }
            }
        });


        // transaction thread, cycle fresh time data for TP
        innerThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                while (!innerThreadPoolStoped) {
                    // wait
                    long lastMinTim = System.currentTimeMillis()/60000;     // ms > min
                    try {
                        // computing TP snapshot data
                        if (timeOriginData.size() > 0) {
                            for (String transactionKey : timeOriginData.keySet()) {

                                // time data, parse to arr-2
                                ConcurrentHashMap<Long, AtomicInteger> timeMap = timeOriginData.get(transactionKey);
                                long[] timeArr = new long[timeMap.size() * 2];
                                int index = 0;

                                long totalCost = 0;
                                long totalCount = 0;
                                long maxCost = 0;
                                for (Long costKey : new TreeMap<Long, AtomicInteger>(timeMap).keySet()) {
                                    long cost = costKey;
                                    long count = timeMap.get(costKey).get();

                                    timeArr[index++] = cost;
                                    timeArr[index++] = count;

                                    totalCost += cost*count;
                                    totalCount += count;
                                    if (cost > maxCost) {
                                        maxCost = cost;
                                    }
                                }

                                // computing TP data
                                long[] tp_index_arr = {
                                        (int) Math.ceil(totalCount * 0.90),
                                        (int) Math.ceil(totalCount * 0.95),
                                        (int) Math.ceil(totalCount * 0.99)
                                        ,(int) Math.ceil(totalCount * 0.999)
                                };
                                long[] tp_val_arr = new long[tp_index_arr.length];
                                int stepCount = 0;
                                int tpIndex = 0;

                                for (int costIndex = 0; costIndex < timeArr.length/2; costIndex++) {
                                    long cost = timeArr[costIndex*2];
                                    long count = timeArr[costIndex*2 + 1];

                                    stepCount += count;
                                    while (stepCount >= tp_index_arr[tpIndex]) {
                                        tp_val_arr[tpIndex] = cost;
                                        tpIndex++;
                                        if (tpIndex >= tp_index_arr.length) {
                                            break;
                                        }
                                    }
                                    if (tpIndex >= tp_index_arr.length) {
                                        break;
                                    }
                                }

                                // report
                                XxlApmTransaction transaction = new XxlApmTransaction();

                                transaction.setTime_max( maxCost );
                                transaction.setTime_avg( totalCost/totalCount );
                                transaction.setTime_tp90( tp_val_arr[0] );
                                transaction.setTime_tp95( tp_val_arr[1] );
                                transaction.setTime_tp99( tp_val_arr[2] );
                                transaction.setTime_tp999( tp_val_arr[3] );

                                timeOriginSnapshot.put(transactionKey, transaction);
                            }
                        }
                        
                        // refresh TP origin data, for each minute
                        long lastMinTim_new = System.currentTimeMillis()/60000;     // ms > min
                        if (lastMinTim_new != lastMinTim) {
                            timeOriginData.clear();
                            timeOriginSnapshot.clear();
                            lastMinTim = lastMinTim_new;
                        }

                        TimeUnit.SECONDS.sleep(1);
                    } catch (Exception e) {
                        if (!innerThreadPoolStoped) {
                            logger.error(e.getMessage(), e);
                        }
                    }
                }

            }
        });

    }

    private void stopApmMsgService(){

        // stop thread
        innerThreadPoolStoped = true;
        innerThreadPool.shutdownNow();

        // stop invoker factory
        if (xxlRpcInvokerFactory != null) {
            try {
                xxlRpcInvokerFactory.stop();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }


    // ---------------------- time data ----------------------

    /**
     *  1、real-time data, for : avg、max、tp99
     *  {
     *      "transaction-key(min)" : {
     *          "time-1" : 10,
     *          "time-2" : 20
     *          ……
     *      }
     *  }
     *
     *  2、async thread computing, delay one second, generate tp snapshot
     *      - async clear map
     *      - tp snapshot, fresh each second, delay one second, may lost last second data
     *      {
     *          "transaction-key(min)": tp data
     *      }
     *
     *  3、get snapshot
     */
    private ConcurrentHashMap<String, ConcurrentHashMap<Long, AtomicInteger>> timeOriginData = new ConcurrentHashMap<>();
    private ConcurrentHashMap<String, XxlApmTransaction> timeOriginSnapshot = new ConcurrentHashMap<>();

    public void computingTP(XxlApmTransaction transaction){

        // addtime -> min
        long min = (transaction.getAddtime()/60000)*60000;

        // match report key
        String transactionKey = String.valueOf(min).concat(transaction.getType()).concat(transaction.getName());

        // push real-time data
        ConcurrentHashMap<Long, AtomicInteger> timeOriginDataItem = timeOriginData.get(transactionKey);
        if (timeOriginDataItem == null) {
            timeOriginData.putIfAbsent(transactionKey, new ConcurrentHashMap<Long, AtomicInteger>());
            timeOriginDataItem = timeOriginData.get(transactionKey);
        }
        AtomicInteger timeCount = timeOriginDataItem.putIfAbsent(transaction.getTime(), new AtomicInteger(1));
        if (timeCount != null) {    // null means set new, no-none means exist old node
            timeCount.incrementAndGet();
        }

        // load snapshot data
        long time_max = transaction.getTime();
        long time_avg = time_max;
        long time_tp90 = time_max;
        long time_tp95 = time_max;
        long time_tp99 = time_max;
        long time_tp999 = time_max;

        XxlApmTransaction snapshot = timeOriginSnapshot.get(transactionKey);
        if (snapshot != null) {
            time_max = snapshot.getTime_max();
            time_avg = snapshot.getTime_avg();
            time_tp90 = snapshot.getTime_tp90();
            time_tp95 = snapshot.getTime_tp95();
            time_tp99 = snapshot.getTime_tp99();
            time_tp999 = snapshot.getTime_tp999();
        }

        transaction.setTime_max( time_max );
        transaction.setTime_avg( time_avg );
        transaction.setTime_tp90( time_tp90 );
        transaction.setTime_tp95( time_tp95 );
        transaction.setTime_tp99( time_tp99 );
        transaction.setTime_tp999( time_tp999 );
    }

    /**
     * 1、collection：timeMap
     *
     *      {
     *          "time" : "count",
     *          ……
     *      }
     *
     *  2、data analyze：
     *      ["time01", "time01_count", "time02", "time02_count"……]
     *  3、logic analyze
     *      ["time01_count", ……, "time01_count"（No：count）……]
     *
     *  ---------
     *  1、real-time data, for : avg、max、tp99
     *  {
     *      "transaction-key-yyyyMMddHHmm" : {
     *          "time-1" : 10,
     *          "time-2" : 20
     *          ……
     *      }
     *  }
     *  2、async thread computing, delay one second, generate tp snapshot
     *      - async clear map
     *      - tp snapshot, fresh each second, delay one second, may lost last second data
     *      {
     *          transaction-key-yyyyMMddHHmm: tp data
     *      }
     *
     *
     *  3、get snapshot
     *
     *
     */

    /*private Map<String, List<Long>> periodTPMap = new ConcurrentHashMap<>();
    private Map<String, Long> periodTPTotalMap = new ConcurrentHashMap<>();
    private static long tp(List<Long> ascSortedTimes, float percent) {
        float percentF = percent/100;
        int index = (int)(percentF * ascSortedTimes.size() - 1);
        return ascSortedTimes.get(index);
    }

    public void computingTP(XxlApmTransaction transaction){

        // addtime -> min
        long min = (transaction.getAddtime()/60000)*60000;

        // match report key
        *//*String transactionKey = transaction.getAppname()
                .concat(String.valueOf(min))
                .concat(transaction.getAddress())
                .concat(transaction.getType())
                .concat(transaction.getName());*//*
        String transactionKey = String.valueOf(min).concat(transaction.getType()).concat(transaction.getName());

        // valid time
        List<Long> timeList = periodTPMap.get(transactionKey);
        if (timeList == null) {
            timeList = Collections.synchronizedList(new ArrayList<Long>());
        }
        Long timeListAll = periodTPTotalMap.get(transactionKey);
        if (timeListAll == null) {
            timeListAll = 0L;
        }

        // computing
        if (timeList.size() <= 200000) {     // avoid "large" time data , limit 20w(~3.3k/qps) per item
            timeList.add(transaction.getTime());
            periodTPMap.put(transactionKey, timeList);
            timeListAll += transaction.getTime();
            periodTPTotalMap.put(transactionKey, timeListAll);

            Collections.sort(timeList); // tod-apm, concurrent fresh problem
        }

        transaction.setTime_max( timeList.get(timeList.size()-1) );
        transaction.setTime_avg( timeListAll/timeList.size() );
        transaction.setTime_tp90( tp(timeList, 90f) );
        transaction.setTime_tp95( tp(timeList, 95f) );
        transaction.setTime_tp99( tp(timeList, 99f) );
        transaction.setTime_tp999( tp(timeList, 99.9f) );

    }*/


    // ---------------------- msg-file ----------------------

    // msg-file
    private boolean writeMsgFile(List<XxlApmMsg> msgList){

        // dispatch msg
        List<XxlApmEvent> eventList = null;
        List<XxlApmTransaction> transactionList = null;
        List<XxlApmHeartbeat> heartbeatList = null;

        for (XxlApmMsg apmMsg: msgList) {
            if (apmMsg instanceof XxlApmEvent) {
                if (eventList == null) {
                    eventList = new ArrayList<>();
                }
                eventList.add((XxlApmEvent) apmMsg);
            } else if (apmMsg instanceof XxlApmTransaction) {
                if (transactionList == null) {
                    transactionList = new ArrayList<>();
                }
                transactionList.add((XxlApmTransaction) apmMsg);
            } else if (apmMsg instanceof XxlApmHeartbeat) {
                if (heartbeatList == null) {
                    heartbeatList = new ArrayList<>();
                }
                heartbeatList.add((XxlApmHeartbeat) apmMsg);
            }
        }

        // make msg-file dir
        if (msgFileDir==null || msgFileDir.list()!=null && msgFileDir.list().length>10000) {
            synchronized (msgFileDirLock) {
                // {msglogpath}/{timestamp}/xxxxxx
                msgFileDir = new File(msglogpath, String.valueOf(System.currentTimeMillis()));
                msgFileDir.mkdirs();
            }
        }

        // write msg-file
        writeMsgList(eventList,  XxlApmEvent.class.getSimpleName(), msgFileDir);
        writeMsgList(transactionList, XxlApmTransaction.class.getSimpleName(), msgFileDir);
        writeMsgList(heartbeatList, XxlApmHeartbeat.class.getSimpleName(), msgFileDir);

        return true;
    }


    private void writeMsgList(List<? extends XxlApmMsg> msgList, String filePrefix, File msgFileDir ){
        if (msgList == null) {
            return;
        }

        String msgListFileName = filePrefix.concat("-").concat(msgList.get(0).getMsgId());
        File msgFile = new File(msgFileDir, msgListFileName);

        byte[] serialize_data = serializer.serialize(msgList);
        FileUtil.writeFileContent(msgFile, serialize_data);
    }

}
