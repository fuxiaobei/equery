package client;

public class QueryClient {

	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	public void query(){
		
	}
	
	public static final String url = "http://localhost:8080/DRQuery/cdrAction?actionId=${actionId}";
	
	public static final String postXml =" <?xml version=\"1.0\" encoding=\"GB2312\"?>     "  
					+ "<MYROOT>                                                           "
					+ "    <PUB_INFO>                                                     "
					+ "		<PUBINFO_OPID>${PUBINFO_OPID}</PUBINFO_OPID>                  "
					+ "		<PUBINFO_IP>${PUBINFO_IP}</PUBINFO_IP>                        "
					+ "		<PUBINFO_REGIONCODE>${PUBINFO_REGIONCODE}</PUBINFO_REGIONCODE>"
					+ "	    <PUBINFO_OPPASSWORD>${PUBINFO_OPPASSWORD}</PUBINFO_OPPASSWORD>"
					+ "    </PUB_INFO>                                                    "
					+ "    <QUEST_PARAM>                                                  "
					+ "        <requestId>${request_id}</request_id >                    "
					+ "        <billId>${billId}</billId>                                 "
					+ "        <out_opId >${out_opId}</out_opId >                         "
					+ "        <out_opPassword >${out_opPassword}</out_opPassword >       "
					+ "        <out_ip >${out_ip}</out_ip >                               "
					+ "        <out_webPort >${out_webPort}</ out_webPort >               "
					+ "        <second_password >${second_password}</second_password >    "
					+ "        <channel_type >${channel_type}</channel_type >             "
					+ "        <billMonth>${billMonth}</billMonth>                        "
					+ "        <fromDate >${fromDate}</fromDate >                         "
					+ "        <thruDate >${thruDate}</thruDate >                         "
					+ "        <billType >${billType}</billType >                         "
					+ "        <regionCode >${regionCode}</regionCode >                   "
					+ "        <queryType >${queryType}</queryType >                      "
					+ "        <impType >${impType}</impType >                            "
					+ "        <pageIndex >${pageIndex}</pageIndex >                      "
					+ "        < pageSize >${pageSize}</pageSize >                        "
					+ "    </QUEST_PARAM>                                                 "
					+ "</MYROOT>                                                          ";

}
