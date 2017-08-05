#redis config via Spring-data-redis
redis.host=10.10.140.170
redis.port=6379
redis.timeout=30000
#reids\u8d85\u65f6\u65f6\u95f4\uff0c\u6ce8\u610f\uff0c\u4ee5\u79d2\u4e3a\u5355\u4f4d\uff0c\u5f53\u503c\u4e3a-1\u65f6\uff0c\u4e0d\u8bbe\u5b9a\u8d85\u65f6\u65f6\u95f4
redis.expiretime=30
#\u53ef\u4ee5\u4ece\u5bf9\u8c61\u6c60\u4e2d\u53d6\u51fa\u7684\u5bf9\u8c61\u6700\u5927\u4e2a\u6570\uff0c\u4e3a0\u5219\u8868\u793a\u6ca1\u6709\u9650\u5236\uff0c\u9ed8\u8ba4\u4e3a8
#The cap on the total number of object instances managed by the pool
redis.pool.maxActive=200
#\u5bf9\u8c61\u6c60\u4e2d\u5bf9\u8c61\u6700\u5927\u4e2a\u6570
redis.pool.maxIdle=100
#\u5bf9\u8c61\u6c60\u4e2d\u5bf9\u8c61\u6700\u5c0f\u4e2a\u6570
redis.pool.minIdle=0
#\u82e5\u5728\u5bf9\u8c61\u6c60\u7a7a\u65f6\u8c03\u7528borrowObject\u65b9\u6cd5\u7684\u884c\u4e3a\u88ab\u8bbe\u5b9a\u6210\u7b49\u5f85\uff0c\u6700\u591a\u7b49\u5f85\u591a\u5c11\u6beb\u79d2\u3002\u5982\u679c\u7b49\u5f85\u65f6\u95f4\u8d85\u8fc7\u4e86\u8fd9\u4e2a\u6570\u503c\uff0c\u5219\u4f1a\u629b\u51fa\u4e00\u4e2ajava.util.NoSuchElementException\u5f02\u5e38\u3002\u5982\u679c\u8fd9\u4e2a\u503c\u4e0d\u662f\u6b63\u6570\uff0c\u8868\u793a\u65e0\u9650\u671f\u7b49\u5f85\u3002 
redis.pool.maxWait=100000
redis.pool.testOnBorrow=false


busiType=content_code_28,cp_code_6,free_res_code1_67,operator_code_35,oper_code_7,opp_home_areacode_5,plan_id_49,plan_id_50,plan_id_51,plan_id_52,service_code_23,service_code_24,service_code_36,sp_code_6,vplmn1_4,vplmn2_4
#busiType=cp_code_6,free_res_code1_67

#dsmp@newdsmpops21
content_code_28.0=CONTENT_CODE-28
content_code_28.0.username=dsmp
content_code_28.0.password=pwd_dsmp
content_code_28.0.driverClass=oracle.jdbc.OracleDriver
content_code_28.0.url=jdbc:oracle:oci:@newdsmpops21
content_code_28.0.sql=select biz_code,ext2 from dsmp_service_info where rec_status=1

#dsmp@newdsmpops21
cp_code_6.0=CP_CODE-6
cp_code_6.0.username=dsmp
cp_code_6.0.password=pwd_dsmp
cp_code_6.0.driverClass=oracle.jdbc.OracleDriver
cp_code_6.0.url=jdbc:oracle:oci:@newdsmpops21
cp_code_6.0.sql=select SP_ID,SP_NAME from dsmp_sp_info where REC_STATUS ='1'
cp_code_6.1=CP_CODE-6
cp_code_6.1.username=dsmp
cp_code_6.1.password=pwd_dsmp
cp_code_6.1.driverClass=oracle.jdbc.OracleDriver
cp_code_6.1.url=jdbc:oracle:oci:@newdsmpops21
cp_code_6.1.sql=SELECT sp_code,sp_name FROM dsmp_spinfo_def
cp_code_6.2=CP_CODE-6
cp_code_6.2.username=jf
cp_code_6.2.password=pwd_jf
cp_code_6.2.driverClass=oracle.jdbc.OracleDriver
cp_code_6.2.url=jdbc:oracle:oci:@shjfops21
cp_code_6.2.sql=select cp_code,cp_name from stream_cp_info

#jf@shjfops21
free_res_code1_67.0=FREE_RES_CODE1-67
free_res_code1_67.0.username=jf
free_res_code1_67.0.password=pwd_jf
free_res_code1_67.0.driverClass=oracle.jdbc.OracleDriver
free_res_code1_67.0.url=jdbc:oracle:oci:@shjfops21
free_res_code1_67.0.sql=SELECT ITEM_KEY, ITEM_NAME FROM PM_ITEMS WHERE ITEM_CODE LIKE '6%'

#aicbs@shyzops21
operator_code_35.0=OPERATOR_CODE-35
operator_code_35.0.username=aicbs
operator_code_35.0.password=pwd_aicbs
operator_code_35.0.driverClass=oracle.jdbc.OracleDriver
operator_code_35.0.url=jdbc:oracle:oci:@shyzops21
operator_code_35.0.sql=SELECT MERCHANT_ID,MERCHANT_NAME FROM MOBILEPAY_MERCHANT_INFO

#dsmp@newdsmpops21
oper_code_7.0=OPER_CODE-7
oper_code_7.0.username=dsmp
oper_code_7.0.password=pwd_dsmp
oper_code_7.0.driverClass=oracle.jdbc.OracleDriver
oper_code_7.0.url=jdbc:oracle:oci:@newdsmpops21
oper_code_7.0.sql=select distinct SP_ID||'|'||biz_code,ext2 from dsmp_service_info where rec_status=1 and expired_date>= sysdate and valid_date<=sysdate
oper_code_7.1=OPER_CODE-7
oper_code_7.1.username=jf
oper_code_7.1.password=pwd_jf
oper_code_7.1.driverClass=oracle.jdbc.OracleDriver
oper_code_7.1.url=jdbc:oracle:oci:@shjfops21
oper_code_7.1.sql=select cp_code||'|'||oper_code,oper_name from stream_oper_info
oper_code_7.2=OPER_CODE-7
oper_code_7.2.username=dsmp
oper_code_7.2.password=pwd_dsmp
oper_code_7.2.driverClass=oracle.jdbc.OracleDriver
oper_code_7.2.url=jdbc:oracle:oci:@newdsmpops21
oper_code_7.2.sql=select sp_id||'|'||sp_service_id,operator_name from dsmp_bizscope_def

#jf@shjfops21
plan_id_49.0=PLAN_ID-49
plan_id_49.0.username=jf
plan_id_49.0.password=pwd_jf
plan_id_49.0.driverClass=oracle.jdbc.OracleDriver
plan_id_49.0.url=jdbc:oracle:oci:@shjfops21
plan_id_49.0.sql=select lac_id||'|'||cell_id, min(valid_date) from gsm_border_roam where TO_CHAR(sysdate, 'yyyymmdd') > valid_date and TO_CHAR(sysdate, 'yyyymmdd') < expire_date group by lac_id, cell_id

#jf@shjfops21
plan_id_50.0=PLAN_ID-50
plan_id_50.0.username=jf
plan_id_50.0.password=pwd_jf
plan_id_50.0.driverClass=oracle.jdbc.OracleDriver
plan_id_50.0.url=jdbc:oracle:oci:@shjfops21
plan_id_50.0.sql=select lac_id||'|'||cell_id, min(valid_date) from gsm_border_roam where TO_CHAR(sysdate, 'yyyymmdd') > valid_date  and TO_CHAR(sysdate, 'yyyymmdd') < expire_date group by lac_id, cell_id

#aicbs@shyzops21
plan_id_51.0=PLAN_ID-51
plan_id_51.0.username=aicbs
plan_id_51.0.password=pwd_aicbs
plan_id_51.0.driverClass=oracle.jdbc.OracleDriver
plan_id_51.0.url=jdbc:oracle:oci:@shyzops21
plan_id_51.0.sql=select lac_id || '|' || cell_id || '|' || TO_CHAR(ADD_DATE, 'yyyymm'), source_type  from like_gsm_border_roam

#aicbs@shyzops21
plan_id_52.0=PLAN_ID-52
plan_id_52.0.username=aicbs
plan_id_52.0.password=pwd_aicbs
plan_id_52.0.driverClass=oracle.jdbc.OracleDriver
plan_id_52.0.url=jdbc:oracle:oci:@shyzops21
plan_id_52.0.sql=select lac_id || '|' || cell_id || '|' || TO_CHAR(ADD_DATE, 'yyyymm'), source_type from like_gsm_border_roam

#jf@shjfops21
service_code_23.0=SERVICE_CODE-23
service_code_23.0.username=jf
service_code_23.0.password=pwd_jf
service_code_23.0.driverClass=oracle.jdbc.OracleDriver
service_code_23.0.url=jdbc:oracle:oci:@shjfops21
service_code_23.0.sql=select code_id, case when deal_flag = 1 then '\u4f18\u60e0' when deal_flag = 2 then '' else '' end from translation_for_cdrquery

#jf@shjfops21
service_code_24.0=SERVICE_CODE-24
service_code_24.0.username=jf
service_code_24.0.password=pwd_jf
service_code_24.0.driverClass=oracle.jdbc.OracleDriver
service_code_24.0.url=jdbc:oracle:oci:@shjfops21
service_code_24.0.sql=select code_id, case when deal_flag = 1 then '\u4f18\u60e0' when deal_flag = 2 then '\u5176\u5b83' else '' end from translation_for_cdrquery

#aicbs@shyzops21
service_code_36.0=SERVICE_CODE-36
service_code_36.0.username=aicbs
service_code_36.0.password=pwd_aicbs
service_code_36.0.driverClass=oracle.jdbc.OracleDriver
service_code_36.0.url=jdbc:oracle:oci:@shyzops21
service_code_36.0.sql=SELECT COMMODITY_ID,COMMODITY_NAME FROM MOBILEPAY_COMMODITY_INFO

#dsmp@newdsmpops21
sp_code_6.0=SP_CODE-6
sp_code_6.0.username=dsmp
sp_code_6.0.password=pwd_dsmp
sp_code_6.0.driverClass=oracle.jdbc.OracleDriver
sp_code_6.0.url=jdbc:oracle:oci:@newdsmpops21
sp_code_6.0.sql=SELECT sp_code,sp_name FROM dsmp_spinfo_def
sp_code_6.1=SP_CODE-6
sp_code_6.1.username=dsmp
sp_code_6.1.password=pwd_dsmp
sp_code_6.1.driverClass=oracle.jdbc.OracleDriver
sp_code_6.1.url=jdbc:oracle:oci:@newdsmpops21
sp_code_6.1.sql=select SP_ID,SP_NAME from dsmp_sp_info where REC_STATUS ='1'

#jf@shjfops21
opp_home_areacode_5.0=OPP_HOME_AREACODE-5
opp_home_areacode_5.0.username=jf
opp_home_areacode_5.0.password=pwd_jf
opp_home_areacode_5.0.driverClass=oracle.jdbc.OracleDriver
opp_home_areacode_5.0.url=jdbc:oracle:oci:@newdsmpops21
opp_home_areacode_5.0.sql=select 'COUNTRY_'||country_code,country_name from sys_country
opp_home_areacode_5.1=OPP_HOME_AREACODE-5
opp_home_areacode_5.1.username=jf
opp_home_areacode_5.1.password=pwd_jf
opp_home_areacode_5.1.driverClass=oracle.jdbc.OracleDriver
opp_home_areacode_5.1.url=jdbc:oracle:oci:@newdsmpops21
opp_home_areacode_5.1.sql=select 'PROVINCE_'||prov_code,prov_name from sys_prov
opp_home_areacode_5.2=OPP_HOME_AREACODE-5
opp_home_areacode_5.2.username=jf
opp_home_areacode_5.2.password=pwd_jf
opp_home_areacode_5.2.driverClass=oracle.jdbc.OracleDriver
opp_home_areacode_5.2.url=jdbc:oracle:oci:@newdsmpops21
opp_home_areacode_5.2.sql=select 'CITY_'||city_code,city_name from sys_city
opp_home_areacode_5.3=OPP_HOME_AREACODE-5
opp_home_areacode_5.3.username=jf
opp_home_areacode_5.3.password=pwd_jf
opp_home_areacode_5.3.driverClass=oracle.jdbc.OracleDriver
opp_home_areacode_5.3.url=jdbc:oracle:oci:@newdsmpops21
opp_home_areacode_5.3.sql=select 'PROV_CITY_'||t2.city_code as city_code,t1.prov_name || '|' || t2.city_name as prov_city from sys_prov t1, sys_city t2 where t1.prov_code = t2.prov_code

#jf@shjfops21
vplmn1_4.0=VPLMN1-4
vplmn1_4.0.username=jf
vplmn1_4.0.password=pwd_jf
vplmn1_4.0.driverClass=oracle.jdbc.OracleDriver
vplmn1_4.0.url=jdbc:oracle:oci:@shjfops21
vplmn1_4.0.sql=select 'COUNTRY_'||country_code,country_name from sys_country
vplmn1_4.1=VPLMN1-4
vplmn1_4.1.username=jf
vplmn1_4.1.password=pwd_jf
vplmn1_4.1.driverClass=oracle.jdbc.OracleDriver
vplmn1_4.1.url=jdbc:oracle:oci:@shjfops21
vplmn1_4.1.sql=select 'PROVINCE_'||prov_code,prov_name from sys_prov
vplmn1_4.2=VPLMN1-4
vplmn1_4.2.username=jf
vplmn1_4.2.password=pwd_jf
vplmn1_4.2.driverClass=oracle.jdbc.OracleDriver
vplmn1_4.2.url=jdbc:oracle:oci:@shjfops21
vplmn1_4.2.sql=select 'CITY_'||city_code,city_name from sys_city
vplmn1_4.3=VPLMN1-4
vplmn1_4.3.username=jf
vplmn1_4.3.password=pwd_jf
vplmn1_4.3.driverClass=oracle.jdbc.OracleDriver
vplmn1_4.3.url=jdbc:oracle:oci:@shjfops21
vplmn1_4.3.sql=select 'PROV_CITY_'||t2.city_code as city_code,t1.prov_name as prov_city from sys_prov t1, sys_city t2 where t1.prov_code = t2.prov_code

#jf@shjfops21
vplmn2_4.0=VPLMN2-4
vplmn2_4.0.username=jf
vplmn2_4.0.password=pwd_jf
vplmn2_4.0.driverClass=oracle.jdbc.OracleDriver
vplmn2_4.0.url=jdbc:oracle:oci:@shjfops21
vplmn2_4.0.sql=select 'COUNTRY_'||country_code,country_name from sys_country
vplmn2_4.1=VPLMN2-4
vplmn2_4.1.username=jf
vplmn2_4.1.password=pwd_jf
vplmn2_4.1.driverClass=oracle.jdbc.OracleDriver
vplmn2_4.1.url=jdbc:oracle:oci:@shjfops21
vplmn2_4.1.sql=select 'PROVINCE_'||prov_code,prov_name from sys_prov
vplmn2_4.2=VPLMN2-4
vplmn2_4.2.username=jf
vplmn2_4.2.password=pwd_jf
vplmn2_4.2.driverClass=oracle.jdbc.OracleDriver
vplmn2_4.2.url=jdbc:oracle:oci:@shjfops21
vplmn2_4.2.sql=select 'CITY_'||city_code,city_name from sys_city
vplmn2_4.3=VPLMN2-4
vplmn2_4.3.username=jf
vplmn2_4.3.password=pwd_jf
vplmn2_4.3.driverClass=oracle.jdbc.OracleDriver
vplmn2_4.3.url=jdbc:oracle:oci:@shjfops21
vplmn2_4.3.sql=select 'PROV_CITY_'||t2.city_code as city_code, t2.city_name as prov_city from sys_prov t1, sys_city t2 where t1.prov_code = t2.prov_code