package com.asiainfo.billing.drquery.process.dto.support;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.asiainfo.billing.drquery.utils.DateUtil;

public class MergeTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testToList() {
//		CommonMergeOperation op = new CommonMergeOperation();
//		//execute(List<Map<String, String>> mapList, String[] keyFields,String[] mergeFields)
//		Map<String, String> map1 = new HashMap();
//		map1.put("SRC_CALL_TYPE", "11");
//		map1.put("SRC_START_TIME", "10");
//		map1.put("field1", "test1");
//		map1.put("key1", "11");
//		map1.put("key2", "22");
//		map1.put("mergeKey1", "100");
//		map1.put("mergeKey2", "150");
//		
//		Map map2 = new HashMap();
//		map2.put("SRC_CALL_TYPE", "11");
//		map2.put("SRC_START_TIME", "30");
//		map2.put("field1", "test11ppp");
//		map2.put("key1", "11");
//		map2.put("key2", "22");
//		map2.put("mergeKey1", "120");
//		map2.put("mergeKey2", "200");
//		
//		Map map3 = new HashMap();
//		map3.put("SRC_CALL_TYPE", "16");
//		map3.put("SRC_START_TIME", "30");
//		map3.put("field1", "test11ppp");
//		map3.put("key1", "11");
//		map3.put("key2", "22");
//		map3.put("mergeKey1", "120");
//		map3.put("mergeKey2", "200");
//		
//		Map map4 = new HashMap();
//		map4.put("SRC_CALL_TYPE", "16");
//		map4.put("SRC_START_TIME", "30");
//		map4.put("field1", "test11ppp");
//		map4.put("key1", "11");
//		map4.put("key2", "22");
//		map4.put("mergeKey1", "300");
//		map4.put("mergeKey2", "500");
//		
//		
//		
//		List<Map<String, String>> mapList = new ArrayList<Map<String,String>>();
//		mapList.add(map1);
//		
//		
//		
//		mapList.add(map2);
//		mapList.add(map3);
//		mapList.add(map4);
//		
//		
//		String[] keyFields = new String[]{"key1", "key2", "SRC_CALL_TYPE"};
//		String[] mergeFields = new String[]{"mergeKey1", "mergeKey2"};
//		
//		List<Map<String, String>> resultList = op.execute(mapList, keyFields, mergeFields);
//		System.out.println(resultList);
	}
	
	@SuppressWarnings("rawtypes")
	@Test
	public void testHistorySummary(){
		System.out.println(DateUtil.getCurrentTimestamp("yyyyMMddHH"));
		
//		DRProcessRequest request = new CommonDRProcessRequest();
//		request.setRegionCode("057");
//		request.setBillMonth("20120812");
//		request.setUserId("wangkai");
//		SummaryRule rule = new CommonSummaryRule();
//		StatMetaDescriptor stat = new StatMetaDescriptor();
//		stat.addHisItem("0001", "description1", "1", "GPRS");
//		stat.addHisItem("0001", "description2", "1", "GPRS");
//		List result = rule.getResultFromHisConfig(stat, request);
//		System.out.println(result);
//		//rule.historyRule(rowData, request);
//		
//		stat.addCurItem(new StatMetaDescriptor("desc1", 1, "sum1", "0001", "GPRS"));
//		stat.addCurItem(new StatMetaDescriptor("desc1", 12, "sum1", "0001", "GPRS"));
//		System.out.println(stat.getCurCodeProperties());
	}
	
	@Test
	public void restClasspath(){
//		InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("applicationContext.xml");
//		System.out.println(inputStream);
//		String path = PropertiesUtil.getProperty("file:/C:/workspace/drquery/drquery.service/src/main/webapp/drquery.config/drquery.service/runtime.properties","javaSource.config.path");
//		System.out.println("---> " + path);
		//ApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
		//context.getBean("propertyConfigurer");
	}
	
	@Test
	public void testSort(){
		List<Map<String, String>> rowData = createData();
		Collections.sort(rowData, new Comparator<Map<String, String>>(){

			@Override
			public int compare(Map<String, String> o1, Map<String, String> o2) {
				String start_time_1 = o1.get("SRC_START_TIME");
				String start_time_2 = o2.get("SRC_START_TIME");
				if(start_time_1.compareTo(start_time_2) > 0){
					return 1;
				}else if(start_time_1.compareTo(start_time_2) < 0){
					return -1;
				}else{
					return o2.get("SRC_BILL_MONTH").compareTo(o1.get("SRC_BILL_MONTH"));
				}
			}
		});
		//for(Map map : rowData)
		//System.out.println(map);
	}
	
	public List<Map<String,String>> createData(){
		List<Map<String, String>> rowData = new ArrayList<Map<String, String>>();
		Map<String, String> map1 = new LinkedHashMap<String, String>();
		map1.put("SRC_START_TIME", "20120904175847");
		map1.put("SRC_BILL_MONTH", "20120904175847");
		map1.put("SRC_FREERES_CODE", "600100:10,600101:20,600102:30");
		
		Map<String, String> map2 = new LinkedHashMap<String, String>();
		map2.put("SRC_START_TIME", "20120903175847");
		map2.put("SRC_BILL_MONTH", "20120903275847");
		map2.put("SRC_FREERES_CODE", "600101:20,600102:30");
		
		Map<String, String> map3 = new LinkedHashMap<String, String>();
		map3.put("SRC_START_TIME", "20120905175847");
		map3.put("SRC_BILL_MONTH", "20120904178847");
		map3.put("SRC_FREERES_CODE", "600100:10,600101:20,600102:30");
		
		Map<String, String> map4 = new LinkedHashMap<String, String>();
		map4.put("SRC_START_TIME", "20120903175847");
		map4.put("SRC_BILL_MONTH", "20120903175847");
		map4.put("SRC_FREERES_CODE", "600100:10,600101:20,600102:30");
		
		rowData.add(map1);
		rowData.add(map2);
		rowData.add(map3);
		rowData.add(map4);
		
		return rowData;
	}
	
	@Test
	public void testMerge(){
//		ApplicationContext context = new ClassPathXmlApplicationContext("classpath*:drquery.service/spring/*.xml");
//		CommonMergeOperation op = (CommonMergeOperation)context.getBean("commonfieldEscape");
//		//System.out.println(op);
//		List<Map<String,String>> list = new ArrayList<Map<String,String>>();
//		Map<Integer, String> repeatCheckMap = new HashMap<Integer, String>();
//		int index = 0;
//		for(Map<String, String> map : createData()){
//			op.merge(list, map, repeatCheckMap, index);
//		}
//		for(Map map : list){
//			System.out.println(map);
//		}
	}
	
	@Test
	public void testSort2(){
		ArrayList sumList = new ArrayList();
		Map map1 = new LinkedHashMap();
		map1.put("type", 1);
		map1.put("sortId", 1);
		map1.put("code", 201301);
		
		Map map2 = new LinkedHashMap();
		map2.put("type", 1);
		map2.put("sortId", 2);
		map2.put("code", 201302);
		
		Map map3 = new LinkedHashMap();
		map3.put("type", 1);
		map3.put("sortId", 3);
		map3.put("code", 201303);
		
		Map map4 = new LinkedHashMap();
		map4.put("type", 1);
		map4.put("sortId", 4);
		map4.put("code", 201304);
		
		Map map5 = new LinkedHashMap();
		map5.put("type", 1);
		map5.put("sortId", 2);
		map5.put("code", 201305);
		
		Map map6 = new LinkedHashMap();
		map6.put("type", 2);
		map6.put("sortId", 1);
		map6.put("code", 201306);
		
		Map map7 = new LinkedHashMap();
		map7.put("type", 2);
		map7.put("sortId", 2);
		map7.put("code", 201307);
		
		sumList.add(map1);
		sumList.add(map2);
		sumList.add(map3);
		sumList.add(map4);
		sumList.add(map5);
		sumList.add(map6);
		sumList.add(map7);
		
		Collections.sort(sumList, new Comparator<Map>(){
			@Override
			public int compare(Map o1, Map o2) {
				Integer type1 = (Integer) o1.get("type");
				Integer type2 = (Integer) o2.get("type");
				if(type1 > type2){
					return 1;
				}else if(type1 < type2){
					return -1;
				}else{
					Integer sortId1 = (Integer) o1.get("sortId");
					Integer sortId2 = (Integer) o2.get("sortId");
					return sortId1 - sortId2;
				}
			}
		});
		System.out.println(sumList);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
