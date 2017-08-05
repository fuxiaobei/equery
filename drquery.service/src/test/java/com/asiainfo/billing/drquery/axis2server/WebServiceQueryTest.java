package com.asiainfo.billing.drquery.axis2server;

import org.apache.axis2.AxisFault;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.rpc.client.RPCServiceClient;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;

import javax.xml.namespace.QName;

/**
 * Created by IntelliJ IDEA.
 * User: lile3
 * Date: 13-12-9
 * Time: 下午4:47
 * To change this template use File | Settings | File Templates.
 */
public class WebServiceQueryTest {

    /**
    F1:
    http://localhost:8080/common?phoneNo=18601134210&&interfaceType=F1

    F2:
    http://localhost:8080/common?phoneNo=18601134210&&interfaceType=F2&dataMonth=201505

    F3:
    http://localhost:8080/common?phoneNo=18601134210&startDate=20150501&endDate=20150505&interfaceType=F3&startIndex=1&offset=-1

    F4:
    http://localhost:8080/common?phoneNo=18601134210&startDate=20150501&endDate=20150505&interfaceType=F4&startIndex=1&offset=-1

    F:5
    http://localhost:8080/common?phoneNo=18601134210&startDate=20150501&endDate=20150505&interfaceType=F5&startIndex=1&offset=-1

    F6:
    http://localhost:8080/common?phoneNo=18601134210&startDate=20150401&endDate=20150530&interfaceType=F6&startIndex=1&offset=10

    F7:
    http://localhost:8080/common?phoneNo=18601134210&dataDate=20150501&chargingId=1000&startTime=20150501182625&interfaceType=F7&startIndex=1&offset=10

    F8:
    http://localhost:8080/common?phoneNo=18601134210&dataTime=2015050118&chargingId=1000&busiId=1000&startTime=20150501182625&interfaceType=F8&startIndex=1&offset=10
    */

    @Test
    public void testWebService() throws Exception {
        RPCServiceClient client = new RPCServiceClient();
        Options options = client.getOptions();
        String address = "http://localhost:8080/services/Axis2ServerAdaptor";
        EndpointReference epf = new EndpointReference(address);
        options.setTo(epf);
        QName qname = new QName("http://axis2server.drquery.billing.asiainfo.com", "getAxis2Result");
        String f1 = "{phoneNo:'8613501203380', interfaceType:'F13', startTime:'20150901',endTime:'20150902',startIndex:'7',offset:'1'}";
        Object[] result = client.invokeBlocking(qname, new String[]{f1}, new Class[]{String.class});
    }
}