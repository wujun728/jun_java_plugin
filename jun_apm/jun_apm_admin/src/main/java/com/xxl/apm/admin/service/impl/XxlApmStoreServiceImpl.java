package com.xxl.apm.admin.service.impl;

import com.xxl.apm.admin.conf.XxlApmMsgServiceImpl;
import com.xxl.apm.admin.core.model.XxlApmEventReport;
import com.xxl.apm.admin.core.model.XxlApmHeartbeatReport;
import com.xxl.apm.admin.core.model.XxlApmTransactionReport;
import com.xxl.apm.admin.dao.IXxlApmEventReportDao;
import com.xxl.apm.admin.dao.IXxlApmHeartbeatReportDao;
import com.xxl.apm.admin.dao.IXxlApmTransactionReportDao;
import com.xxl.apm.admin.service.XxlApmStoreService;
import com.xxl.apm.client.message.XxlApmMsg;
import com.xxl.apm.client.message.impl.XxlApmEvent;
import com.xxl.apm.client.message.impl.XxlApmHeartbeat;
import com.xxl.apm.client.message.impl.XxlApmTransaction;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author xuxueli 2019-01-17
 */
@Service
public class XxlApmStoreServiceImpl implements XxlApmStoreService {


    @Resource
    private IXxlApmHeartbeatReportDao xxlApmHeartbeatReportDao;
    @Resource
    private IXxlApmEventReportDao xxlApmEventReportDao;
    @Resource
    private IXxlApmTransactionReportDao xxlApmTransactionReportDao;


    @Override
    public boolean processMsg(List<XxlApmMsg> messageList) {
        // dispatch msg
        List<XxlApmHeartbeat> heartbeatList = null;
        List<XxlApmEvent> eventList = null;
        List<XxlApmTransaction> transactionList = null;

        for (XxlApmMsg apmMsg: messageList) {
            if (apmMsg instanceof XxlApmTransaction) {      //  child class should before super class
                if (transactionList == null) {
                    transactionList = new ArrayList<>();
                }
                transactionList.add((XxlApmTransaction) apmMsg);
            } else if (apmMsg instanceof XxlApmEvent) {
                if (eventList == null) {
                    eventList = new ArrayList<>();
                }
                eventList.add((XxlApmEvent) apmMsg);
            } else if (apmMsg instanceof XxlApmHeartbeat) {
                if (heartbeatList == null) {
                    heartbeatList = new ArrayList<>();
                }
                heartbeatList.add((XxlApmHeartbeat) apmMsg);
            }
        }

        // dispatch process
        if (heartbeatList!=null && heartbeatList.size() > 0) {

            List<XxlApmHeartbeatReport> heartbeatReportList = new ArrayList<>();
            for (XxlApmHeartbeat heartbeat: heartbeatList) {

                // addtime -> min
                heartbeat.setAddtime((heartbeat.getAddtime()/60000)*60000);
                byte[] heartbeat_data = XxlApmMsgServiceImpl.getSerializer().serialize(heartbeat);

                XxlApmHeartbeatReport heartbeatReport = new XxlApmHeartbeatReport();
                heartbeatReport.setAppname(heartbeat.getAppname());
                heartbeatReport.setAddtime(heartbeat.getAddtime());
                heartbeatReport.setAddress(heartbeat.getAddress());
                heartbeatReport.setHostname(heartbeat.getHostname());

                heartbeatReport.setHeartbeat_data(heartbeat_data);

                heartbeatReportList.add(heartbeatReport);
            }

            xxlApmHeartbeatReportDao.addMult(heartbeatReportList);  // for minute
        }

        if (eventList!=null && eventList.size()>0) {

            Map<String, XxlApmEventReport> eventReportMap = new HashMap<>();
            for (XxlApmEvent event:eventList) {

                // addtime -> min
                long min = (event.getAddtime()/60000)*60000;

                // match report key
                String eventKey = event.getAppname()
                        .concat(String.valueOf(min))
                        .concat(event.getAddress())
                        .concat(event.getType())
                        .concat(event.getName());
                boolean success = XxlApmEvent.SUCCESS_STATUS.equals(event.getStatus());

                // make report
                XxlApmEventReport eventReport = eventReportMap.get(eventKey);
                if (eventReport == null) {
                    eventReport = new XxlApmEventReport();
                    eventReport.setAppname(event.getAppname());
                    eventReport.setAddtime(min);
                    eventReport.setAddress(event.getAddress());
                    eventReport.setHostname(event.getHostname());

                    eventReport.setType(event.getType());
                    eventReport.setName(event.getName());

                    eventReportMap.put(eventKey, eventReport);
                }

                eventReport.setTotal_count(eventReport.getTotal_count() + 1);   // just new count
                if (!success) {
                    eventReport.setFail_count(eventReport.getFail_count() + 1);
                }

                // make logview
                bindLogView(event);

            }

            for (XxlApmEventReport eventReport: eventReportMap.values()) {
                /*int ret = xxlApmEventReportDao.update(eventReport);
                if (ret < 1) {
                    xxlApmEventReportDao.add(eventReport);
                }*/
                xxlApmEventReportDao.addOrUpdate(eventReport);
            }
            
        }

        if (transactionList!=null && transactionList.size()>0) {

            Map<String, XxlApmTransactionReport> transactionReportMap = new HashMap<>();
            for (XxlApmTransaction transaction: transactionList) {

                // addtime -> min
                long min = (transaction.getAddtime()/60000)*60000;

                // match report key
                String transactionKey = transaction.getAppname()
                        .concat(String.valueOf(min))
                        .concat(transaction.getAddress())
                        .concat(transaction.getType())
                        .concat(transaction.getName());
                boolean success = XxlApmEvent.SUCCESS_STATUS.equals(transaction.getStatus());

                // make report
                XxlApmTransactionReport transactionReport = transactionReportMap.get(transactionKey);
                if (transactionReport == null) {
                    transactionReport = new XxlApmTransactionReport();
                    transactionReport.setAppname(transaction.getAppname());
                    transactionReport.setAddtime(min);
                    transactionReport.setAddress(transaction.getAddress());
                    transactionReport.setHostname(transaction.getHostname());

                    transactionReport.setType(transaction.getType());
                    transactionReport.setName(transaction.getName());

                    transactionReportMap.put(transactionKey, transactionReport);
                }

                transactionReport.setTotal_count(transactionReport.getTotal_count() + 1);       // just new count
                if (!success) {
                    transactionReport.setFail_count(transactionReport.getFail_count() + 1);
                }

                // for transaction
                transactionReport.setTime_max(transaction.getTime_max());
                transactionReport.setTime_avg(transaction.getTime_avg());
                transactionReport.setTime_tp90(transaction.getTime_tp90());
                transactionReport.setTime_tp95(transaction.getTime_tp95());
                transactionReport.setTime_tp99(transaction.getTime_tp99());
                transactionReport.setTime_tp999(transaction.getTime_tp999());

                // make logview
                bindLogView(transaction);
            }

            for (XxlApmTransactionReport transactionReport: transactionReportMap.values()) {
                /*int ret = xxlApmTransactionReportDao.update(transactionReport);
                if (ret < 1) {
                    xxlApmTransactionReportDao.add(transactionReport);
                }*/
                xxlApmTransactionReportDao.addOrUpdate(transactionReport);

            }

        }

        return true;
    }


    /*// mock start
    List<Long> timeList = getTpMap().get(transactionKey);
    if (timeList == null) {
        timeList = new ArrayList<>();
        getTpMap().put(transactionKey, timeList);
    } {

    }
    timeList.add(transaction.getTime());
    long totalTime = 0;
    long maxTime = 0;
    for (long item:timeList) {
        totalTime += item;
        if (item>maxTime) {
            maxTime = item;
        }
    }
    transactionReport.setTime_max(maxTime);
    transactionReport.setTime_avg( totalTime/timeList.size() );
    transactionReport.setTime_tp90( tp(timeList, 90f) );
    transactionReport.setTime_tp95( tp(timeList, 95f) );
    transactionReport.setTime_tp99( tp(timeList, 99f) );
    transactionReport.setTime_tp999( tp(timeList, 99.9f) );
    // mock end*/

    /*private static Map<String, List<Long>> tpMap_0 = new ConcurrentHashMap<>();
    private static Map<String, List<Long>> tpMap_1 = new ConcurrentHashMap<>();
    private static Map<String, List<Long>> getTpMap(){
        int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        if (hour%2 == 0) {
            tpMap_1.clear();
            return tpMap_0;
        } else {
            tpMap_0.clear();
            return tpMap_1;
        }
    }
    private static long tp(List<Long> times, float percent) {
        float percentF = percent/100;
        Collections.sort(times);

        int index = (int)(percentF * times.size() - 1);
        return times.get(index);
    }*/


    /**
     * bindLogView
     *
     * @param xxlApmMsg
     */
    private void bindLogView(XxlApmMsg xxlApmMsg){
        /**
         *  todo-apm:
         *
         *      - fresh report, in min (report simple data)
         *      - store logView (store file or es)
         *      - Report bind LogView, LogView write file
         *      - Error LogView, Alerm;
         *
         * @param xxlApmMsg
         */
    }

    @Override
    public boolean cleanMsg(int msglogStorageDay) {

        // timeout time
        Calendar timeoutCal = Calendar.getInstance();
        timeoutCal.add(Calendar.DAY_OF_MONTH, -1*msglogStorageDay);
        long timeoutTime = timeoutCal.getTimeInMillis();

        // clean timeout report
        xxlApmHeartbeatReportDao.clean(timeoutTime);
        xxlApmEventReportDao.clean(timeoutTime);
        xxlApmTransactionReportDao.clean(timeoutTime);

        // clean logView, todo-apm:

        return false;
    }

}
