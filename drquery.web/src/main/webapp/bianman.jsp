<%@page import="java.util.ArrayList"%>
<%@page import="com.asiainfo.billing.drquery.web.servlet.RoleGrandInfo"%>
<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="java.util.List" %>
<%@ page import="java.util.Calendar" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
    HttpSession sess = request.getSession();
    List<RoleGrandInfo> roleGrandList = (ArrayList<RoleGrandInfo>) sess.getAttribute("roleGrandList");
%>
<%
    String query12month = (String) request.getParameter("query12month");
    if (query12month == null) {
        query12month = "0";
    }

    String multiquery = (String) request.getParameter("multiquery");
    if (multiquery == null) {
        multiquery = "0";
    }
String printFlag=(String)request.getParameter("printFlag");
	if(printFlag==null ){
		printFlag="0";
	}
	if(!printFlag.equals("1")){
		printFlag="0";
	}

       String  billId=request.getParameter("billId");
       if(billId==null)billId="13564326330";
%>
<%!
    String checkMonth(int month) {
        return month < 10 ? ("0" + month) : String.valueOf(month);
    }
%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <link rel="stylesheet" type="text/css" href="./css/default_v2.css"/>
        <script type="text/javascript" src="./js/jquery-1.4.2.min.js"></script>
        <script type="text/javascript" src="./js/aioci.js"></script>
        <script type="text/javascript" src="./js/My97DatePicker/WdatePicker.js"></script>
        <title>边界漫游详单查询</title>
        <style>
            <!--
            .black_overlay{
                display: none;
                position: absolute;
                top: 0%;
                left: 0%;
                width: 100%;
                height: 100%;
                background-color: black;
                z-index:1001;
                -moz-opacity: 0.8;
                opacity:.80;
                filter: alpha(opacity=80);
            }
            .white_content {
                display: none;
                position: absolute;
                top: 25%;
                left: 25%;
                width: 50%;
                height: 65%;
                padding: 16px;
                border: 5px solid #EBEBEB;
                background-color: #ECE9D8;
                z-index:1002;
                overflow: hidden;

            }
            -->
        </style>

    </head>
    <body>

        <div class="bform" style="display: s">
            <fieldset>
                <legend>边界漫游详单查询</legend>
            </fieldset>
        </div>		
    <li>				
        <label>起始日期</label>
            <select class="select" onchange="setTime(this.value)" id="month">
<!--		<option value="2012-10-15:2012-10-28">2012年10月(1015－1028)</option>
                <option value="2012-09-15:2012-10-14">2012年9月(0915－1014)</option>
		<option value="2012-09-01:2012-09-14">2012年9月(0901－0914)</option>
		<option value="2012-08-01:2012-08-31">2012年8月(0801－0831)</option>
		<option value="2012-07-01:2012-07-31">2012年7月(0701－0731)</option>
		<option value="2012-06-01:2012-06-30">2012年6月(0601－0630)</option>
		<option value="2012-05-01:2012-05-31">2012年5月(0501－0531)</option>-->
		</select>
        
        <span id="fromDateDiv"></span>
			--至--
	<span id="thruDateDiv"></span>
        
    <li>
        <label>手机号码</label>
        <input type="text" value="<%=billId%>"  id="billId" style="width:120px;" title="" maxlength="11" disabled="disabled"/>
        <input type="hidden" value="GSM_R"  id="busiType" />
    </li>    
    <p>
   
        <a href="#" class="button"><input onclick="query();" type="button" style="cursor: pointer;" value="查询"/></a>
     <%if( printFlag.equals("1") ) {%>
     	<label></label><label></label>
     	<a href="#no" class="button">
        <input name="" type="button" hidefocus="true"  onclick="toexclquery(this.parentNode);" value="导出"/></a> 
        
        <label></label><label></label>
     	<a href="#no" class="button">
        <input name="" id="printbt" type="button" hidefocus="true"   onclick="toprintquery();" value="打印"/></a>
     	<%}else{ %>
     	<label></label><label></label>
	     <a href="#no" class="button">
        <input name="" type="button" hidefocus="true"  disabled onclick="toexclquery(this.parentNode);" value="导出"/></a> 
     	<label></label><label></label>
     	<a href="#no" class="button">
     	<input name="" id="printbt" type="button" hidefocus="true" disabled  onclick="toprintquery();" value="打印"/></a>
     	<% }%>
        
        
    
    <div id="data" class="bform"   style="float: left;display:none;">
        <div class="bform">
            <fieldset>
                <legend id="d1">通话详单</legend>
            </fieldset>	
        </div>	
        <div id="loading" style="position:absolute;text-align:center;float: right;display:none;z-index:1000;width:100%;height:100px;background-color:E7E7E7;"><img src="<%=path%>/images/load.gif" /></div>
        <div class="bform" id="grid" style="height: 0px;"></div>
    </div>

<p>
<div id="billheader" style="display: none;">

        <div style="padding-top: -10px;">
            <div style="background-color: #FABF8F">
                【橙色底色的】：局数据生效日期对应月份=详单月份（指查询时选择的月份，不是详单具体开始时间）。此类详单，应由系统进行边漫追溯调整。<br/>
            </div>
            <div style="background-color: #FFFF00">
                【黄色底色的】：局数据生效日期对应月份>详单月份（指查询时选择的月份，不是详单具体开始时间）。此类详单，由于边漫局数据生效比较晚，故用户的边漫详单没有得到优惠。<br/>
            </div>
            <div style="background-color: #CCC0CF">
                【紫色底色的】：此类详单，对应小区在本月疑似边漫小区中（尚未添加到系统中），请自行判断是否还要派单给网管。<br/>
            </div style="background-color: #000000">
            对边漫局数据生效月份=详单月份的，应由系统进行调整，月底最后1天生效的局数据除外

        </div>   
    </div>
</body>
</html>

<script type="text/javascript">
    var path="<%=path%>";
    
    function query(){
        if(window.confirm("确定要执行此操作吗？")){
            loadData();
        }
    
}
   
    function loadData(){
        $("#data").hide();
          $("#billheader").hide();
        var _url=path+"/servlet/queryGSMRList?date="+new Date();
        var billId=$("#billId").val();
        var busiType=$("#busiType").val();
        var fromDate=$("#fromDate").val();
        var thruDate=$("#thruDate").val();
        var param="&billId="+billId+"&busiType="+busiType+"&fromDate="+fromDate+"&thruDate="+thruDate;
        $("#data").show();
        $("#loading").fadeIn();
        jQuery.get(_url, param, function(data){
            var pageWidth=document.body.clientWidth;
            var pageHeight=document.body.clientHeight;
            var gridHeight=pageHeight-251;
            if(data!=""&&null!=data){
                try{
                    data=eval("("+data+")");
                    if(data.result=="success"){
                        var ds=data.data;
                        AIOX.buildGrid({id:'grid',ds:ds,width:pageWidth,height:gridHeight,showNO:true});
                        $("#billheader").show();
                        }
                  }catch(e){
                    $("#loading").fadeOut();
                  }
               }
               $("#loading").fadeOut();
            
        },"string");
                 
    }
                 
    
                   
                   
    function  exportData(){
        var param=getParam();
        window.open("./servlet/drrbossBill?method=export&1=1&"+param); 
                       
    } 
                    
  

function initTime(){
	var date1=$("#options_date1"); var date2=$("#options_date2");
	date1.html("");date2.html("");
	for(var i=1;i<=30;i++){
		var dstr=i;if(i<10)dstr="0"+i;
		date1.append("<option value='"+dstr+"'>"+dstr+"</option>");
		if(i==30)
			date2.append("<option value='"+dstr+"' selected='selected'>"+dstr+"</option>");
		else
			date2.append("<option value='"+dstr+"' >"+dstr+"</option>");
	}
}


function setTime(time){
	if(time.indexOf(":")>0){
		var fromDate=time.substring(0,time.indexOf(":"));
		var thruDate=time.substring(time.indexOf(":")+1);
		$("#fromDateDiv").html("<input type=\"text\" class=\"Wdate\" style=\"width:100px;\" id=\"fromDate\" value=\""+fromDate+"\" onfocus=\"WdatePicker({dateFmt:'yyyy-MM-dd',opposite:true,minDate:'"+fromDate+"',maxDate:'#F{$dp.$D(thruDate)}',isShowClear:false,readOnly:true})\"/>");
		$("#thruDateDiv").html("<input type=\"text\" class=\"Wdate\" style=\"width:100px;\" id=\"thruDate\" value=\""+thruDate+"\" onfocus=\"WdatePicker({dateFmt:'yyyy-MM-dd',opposite:true,minDate:'#F{$dp.$D(fromDate)}',maxDate:'"+thruDate+"',isShowClear:false,readOnly:true})\"/>");
		
	}
}

function initTimeValue(){
     var billId=$("#billId").val();
     var busiType=$("#busiType").val();
     var url="<%=path%>/servlet/initGSMRTime?1=1";
     var param="&billId="+billId+"&busiType="+busiType;
     try{
           $.post(url, param, function(data){
               data=eval("("+data+")");
               if(data.baseResponse.result=="success"){
                    var list=data.baseResponse.data;
                    var date1=$("#month");
                    date1.html("");
                    for(var i=0;i<list.length;i++){
                        var s1=new Array();
                        var o=list[i];
                        for(var key in o){
                            var y=key.substring(0,4);
                            var beginM=key.substring(5,7);
                            var beginDate=key.substring(8,10);
                            var endM=o[key].substring(5,7);
                            var endDate=o[key].substring(8,10);
                           
                            var text=y+"年"+beginM+"月("+beginM+beginDate+"—"+endM+endDate+")";
                            date1.append("<option value='"+key+":"+o[key]+"'>"+text+"</option>")
                            
                        }
                    }
                    setTime($("#month").val());
                    
               }
           }, "string");
     
     }catch(e){}
    
    
}

  $(document).ready(function(){
        initTimeValue();     
    });



</script>