<%@page contentType="text/html;charset=UTF-8" %>
<html>
<head>

<link rel="stylesheet" type="text/css" href="./css/default_v2.css"/>
<script type="text/javascript" src="./js/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="./js/aioci.js"></script>
<script type="text/javascript" src="./js/My97DatePicker/WdatePicker.js"></script>
<title>帐前清单</title>
<style type="text/css">

</style>
</head>
<body>
<%
java.util.Calendar calendar=java.util.Calendar.getInstance();
java.text.SimpleDateFormat df=new java.text.SimpleDateFormat("yyyy-MM-dd");
String thruDate=df.format(calendar.getTime());
calendar.set(java.util.Calendar.DATE, 1);
String fromDate=df.format(calendar.getTime());
%>
	 <div class="bform">
		<fieldset>
			<legend>帐前清单</legend>
		</fieldset>			
		    <input type="hidden" id="year" value="2012">
			<input type="hidden" id="input_bill_id" value=""/>
			<table class="tform">
				<tr>
					<td class="k">业务类型</td>
					<td class="v">
						<select id="busiType" style="width:170px;" class="select" onchange="changeBusi(this.value);">
							<option value=''>本地通话</option>
						</select>
					</td>
					<td class="k">起始日期</td>
					<td class="v">
						<input type="text" value="2012-07-21" class="text" id="fromDate" value="<%=fromDate%>" onFocus="WdatePicker({startDate:'%y-%M-01',dateFmt:'yyyy-MM-dd',alwaysUseStartDate:true})">
						--至--
						<input type="text" value="2012-07-21" class="text" id="thruDate" value="<%=thruDate%>" onFocus="WdatePicker({startDate:'%y-%M-%d',dateFmt:'yyyy-MM-dd',alwaysUseStartDate:true})">
					</td>
					<td class="k">
						<div id="extParam"></div>
					</td>
				</tr>
				
				<tr>
					<td class="k">手机号码</td>
					<td class="v">
						<input type="text" value="13454250971"  id="billId" style="width:170px;" title="" class="text"/>
					</td>
					<td class="k">操作原因</td>
					<td class="v" colspan="2">
						<input type="text" value=""  id="reason" size="30" title="" class="text"/>
						<input type="checkbox" id="smsflag" checked = "true"/> 下发短信通知
					</td>
				</tr>
				<tr>
					<td class="k">userId</td>
					<td class="v">
						<input type="text" value="5011867354"  id="userId" style="width:170px;" title="" class="text"/>
					</td>
					<td class="k">regionCode</td>
					<td class="v" >
						<input type="text" value="571"  id="regionCode" style="width:170px;" title="" class="text"/>
					</td>
					<td class="k">impType</td>
					<td class="v" >
						<select id="impType" style="width:170px;" class="select">
							<option value='HIS'>历史详单查询</option>
							<option value='CUR'>实时详单查询</option>
							<option value='HISLARGE'>超大历史详单查</option>
							<option value='CURPBX'>集团实时PBX详单查询</option>
						</select>
					</td>
				</tr>
				<tr>
					<td class="k">对端号码</td>
					<td class="v">
						<input type="text" class="text" value="" id="destId" style="width:170px;" title="输入多个号码时请用西文逗号分开"/>
					</td>
					<td class="k">queryType</td>
					<td class="v">
						<select id="queryType" style="width:170px;" class="select">
							<option value='00'>区分详单查询</option>
							<option value='09'>详单打印</option>
							<option value='90'>保密天使业务</option>
						</select>
					</td>
					<td colspan="2" class="v" style="padding-left:20px;">
						<a href="#" class="button"><input type="button" onclick="query1();" value="查询OCNosql" /></a>
						<!--  <a href="#" class="button"><input type="button" onclick="query2();" value="查询Gbase" /></a>
						<a href="#" class="button"><input type="button" onclick="query3()" value="查询prebill" /></a>
						<a href="#" class="button"><input type="button" onclick="query4()" value="查询ondemand" /></a>
						<a href="#" class="button"><input onclick="query();" type="button" value="查询"/></a>-->
					</td>
				</tr>				
			</table>
		</div>
		
	
		<div id="data" style="display:none">
			<div class="bform">
				<fieldset>
					<legend id="d1">本地通话清单</legend>
				</fieldset>	
			</div>	
			<div id="grid"></div>
		</div>
		
		<div id="data2" style="display:none">
			<div class="bform">
				<fieldset>
					<legend id="d2">本地通话清单统计</legend>
				</fieldset>
			</div>	
			<div id="grid2"></div>

			<table width="100%">
				<tr>
					<td width="40%"></td>
					<td width="60%">
						<a href="#" class="button"><input onclick="alert();" type="button" value="小区信息查询"/></a>
					</td>
				</tr>
			</table>
		</div>	
</BODY>
</HTML>
<script type="text/javascript">
function query(){
	if($("#billType").val()=="-1"){alert("请选择业务类型");return;}
	if($("#billId").val()==""){alert("请输入手机号码");return;}
	
	$("#data").show();$("#data2").show();
	var busiName=$("#busiType").find("option:selected").text();
	$("#d1").html(busiName+"清单");
	$("#d2").html(busiName+"清单统计");
	$("#grid").html(""); $("#grid2").html("");
	
	var url="./data/QueryCommon.jsp?billId="+$("#billId").val()+"&fromDate="+$("#fromDate").val()+"&thruDate="+$("#thruDate").val()+"&busiType="+$("#busiType").val()+"&destId="+$("#destId").val();
	loadData(url);
}
function query1(){
	if($("#billType").val()=="-1"){alert("请选择业务类型");return;}
	if($("#billId").val()==""){alert("请输入手机号码");return;}
	
	$("#data").show();$("#data2").show();
	var busiName=$("#busiType").find("option:selected").text();
	$("#d1").html(busiName+"清单");
	$("#d2").html(busiName+"清单统计");
	$("#grid").html(""); $("#grid2").html("");
	
	var url="./data/QueryCommon.jsp?db=ocnosqlDataSource&billId="+$("#billId").val()+"&fromDate="+$("#fromDate").val()+"&thruDate="+$("#thruDate").val()+"&busiType="
			+$("#busiType").val()+"&destId="+$("#destId").val() + "&userId=" + $("#userId").val() + "&regionCode=" + $("#regionCode").val() + "&impType=" + $("#impType").val()
			+ "&queryType=" + $("#queryType").val();
	loadData(url);
}
function query2(){
	if($("#billType").val()=="-1"){alert("请选择业务类型");return;}
	if($("#billId").val()==""){alert("请输入手机号码");return;}
	
	$("#data").show();$("#data2").show();
	var busiName=$("#busiType").find("option:selected").text();
	$("#d1").html(busiName+"清单");
	$("#d2").html(busiName+"清单统计");
	$("#grid").html(""); $("#grid2").html("");
	
	var url="./data/QueryCommon.jsp?db=gBDataSource&billId="+$("#billId").val()+"&fromDate="+$("#fromDate").val()+"&thruDate="+$("#thruDate").val()+"&busiType="+$("#busiType").val()+"&destId="+$("#destId").val();
	loadData(url);
}
function query3(){
	if($("#billType").val()=="-1"){alert("请选择业务类型");return;}
	if($("#billId").val()==""){alert("请输入手机号码");return;}
	
	$("#data").show();$("#data2").show();
	var busiName=$("#busiType").find("option:selected").text();
	$("#d1").html(busiName+"清单");
	$("#d2").html(busiName+"清单统计");
	$("#grid").html(""); $("#grid2").html("");
	
	var url="./data/QueryCommon.jsp?db=prebillSource&billId="+$("#billId").val()+"&fromDate="+$("#fromDate").val()+"&thruDate="+$("#thruDate").val()+"&busiType="+$("#busiType").val()+"&destId="+$("#destId").val();
	loadData(url);
}
function query4(){
	if($("#billType").val()=="-1"){alert("请选择业务类型");return;}
	if($("#billId").val()==""){alert("请输入手机号码");return;}
	
	$("#data").show();$("#data2").show();
	var busiName=$("#busiType").find("option:selected").text();
	$("#d1").html(busiName+"清单");
	$("#d2").html(busiName+"清单统计");
	$("#grid").html(""); $("#grid2").html("");
	
	var url="./data/QueryCommon.jsp?db=ondemandDataSource&billId="+$("#billId").val()+"&fromDate="+$("#fromDate").val()+"&thruDate="+$("#thruDate").val()+"&busiType="+$("#busiType").val()+"&destId="+$("#destId").val();
	loadData(url);
}
function loadData(url,startIndex,stopIndex,pSize){
	var newUrl=url;
	if(startIndex){
            newUrl+="&startIndex="+startIndex;
        }else{
              newUrl+="&pageIndex=1&pageSize=20";
        }
	if(stopIndex){
            newUrl+="&stopIndex="+stopIndex;
        }else{
            newUrl+="&stopIndex=20";
        }
        if(pSize){
             newUrl+="&pageSize="+pSize;
          
        }
	var pageWidth=document.body.clientWidth;
	var pageHeight=document.body.clientHeight;
	

	$.post(newUrl,function(data){
		try{
			
			if(data.result != 0){
				alert("查询失败，错误：" + data.resMsg.errorMsg);
				return;
			}else{
				if(!data.replyDisInfo){
					alert("查询成功，记录数为0");
					return;
				}
			}
			
			var gridHeight=pageHeight-250;
            var startIndex=data.stats.startIndex;
            var stopIndex=data.stats.stopIndex;
            var pageSize= 20;
            var fields = [];
            var contents = [];
            for(var i=0; i < data.replyDisInfo.cdrDisplays.length; i++){
            	record = data.replyDisInfo.cdrDisplays[i];
            	var content = [];
            	for(var j = 0; j < record.length; j++){
            		field = record[j];
            		if(i == 0){
            			fields[j] = field.name;
            		}
            		content.push(field.value);
            	}
           		contents.push(content);
            }
            //"count":20,"startIndex":1,"stopIndex":20,
            var ds = {count: data.stats.totalCount, startIndex: startIndex, stopIndex: stopIndex, fields: fields, contents: contents};
			AIOX.buildGrid({id:'grid',ds: ds,width:pageWidth,height:gridHeight,pageSize:pageSize,showNO:true, page:function(startIndex, stopIndex,ps){
                                loadData(url, startIndex, stopIndex,ps);
			}});
			
			var fields_sum = [];
            var contents_sum = [];
            for(var i=0; i < data.replyDisInfo.sums.length; i++){
            	record = data.replyDisInfo.sums[i];
            	fields_sum.push(record.accName);
            	contents_sum.push(record.fee);
            }
			
			var ds2={fields: fields_sum, contents:[contents_sum]};
			
			AIOX.buildGrid({id:'grid2',ds:ds2,width:pageWidth,height:70});
		}catch(e){}	
	},"json");
} 

function changeBusi(value){
	if(value=="8"){
		$("#extParam").html("固定电话 <input type='text' class='text' id=''/>");
	}else if(value=="9"){
		$("#extParam").html("企业代码 <input type='text' class='text' id=''/>");
	}else{
		$("#extParam").html("");
	}
}
function initBusi(){
	$("#busiType").html("<option value='-1'>--请选择--</option>");
	var ds={"0":"全部话单",
			"2":"数据业务",
			"3":"月基本费",
			"1":"GSM",
			"11":"  VPMN",
			"111":"    本地VPMN",
			"1111":"      本地主叫VPMN",
			"1112":"      本地被叫VPMN",
			"112":"    VPMN省内漫游",
			"113":"    VPMN省际漫游",
			"12":"  普通语音",
			"121":"    本地普通语音",
			"1211":"      本地普通网内语音",
			"12111":"         本地普通网内语音主叫",
			"12112":"         本地普通网内语音被叫",
			"1212":"      本地普通网外语音",
			"12121":"         本地普通网外语音主叫",
			"12122":"         本地普通网外语音被叫",
			"122":"    省内普通语音",
			"123":"    省际普通语音",
			"124":"    国际普通语音",
			"13":"  自助服务",
			"24":"GPRS",
		    "241":"  省内GPRS",
		    "242":"  省际GPRS",
			"243":"  国际GPRS",
			"21":"SMS",
			"211":"  国际SMS",
			"212":"  网内SMS",
			"213":"  网外SMS",
			"214":"  梦网SMS",
			"22":"MMS",
			"221":"  国际MMS",
			"222":"  普通MMS",
			"25":"增值业务",
			"23":"SP",
			"231":"  SP按次",
			"232":"  SP包月",
			"5":"集团PBX详单",
			"6":"企业信息机详单",
			"7":"集团400业务详单",
			"9":"集团WLAN详单"
			};
	for(var k in ds){
		$("#busiType").append("<option value='"+k+"'>"+ds[k]+"</option>");
	}
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

$(document).ready(function(){
	initBusi();
	//initTime();
});
</script>