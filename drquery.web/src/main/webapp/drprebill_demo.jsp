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
					<td class="k">手机号码</td>
					<td class="v">
						<input type="text" value="13910177261"  id="billId" style="width:170px;" title="" class="text" disabled="disabled"/>
					</td>
					
					<td class="k">起始日期</td>
					<td class="v" colspan="2">
						<select class="select" onchange="setTime(this.value)" id="month">
							<option value="2012-10-15:2012-10-28">2012年10月</option>
							<option value="2012-09-15:2012-10-14">2012年9月(0915－1014)</option>
							<option value="2012-09-01:2012-09-14">2012年9月(0901－0914)</option>
							<option value="2012-08-01:2012-08-31">2012年8月(0801－0831)</option>
							<option value="2012-07-01:2012-07-31">2012年7月(0701－0731)</option>
							<option value="2012-06-01:2012-06-30">2012年6月(0601－0630)</option>
							<option value="2012-05-01:2012-05-31">2012年5月(0501－0531)</option>
						</select>
						<span id="fromDateDiv"></span>
						--至--
						<span id="thruDateDiv"></span>
					</td>
					<td class="k">
						<div id="extParam"></div>
					</td>
				</tr>
				
				<tr>
					<td class="k">业务类型</td>
					<td class="v">
						<select id="busiType" style="width:170px;" class="select" onchange="changeBusi(this.value);">
							<option value=''>本地通话</option>
						</select>
					</td>
					
					<td class="k">对端号码</td>
					<td class="v">
						<input type="text" class="text" value="" id="destId" style="width:170px;" title="输入多个号码时请用西文逗号分开"/>
					</td>
					<td class="v" style="padding-left:20px;">
						<a href="#" class="button"><input onclick="query();" type="button" value="查询"/></a>
						<a href="#" class="button"><input type="button" onclick="queryrec();" value="查询日志" /></a>
						<a href="#" class="button"><input type="button" onclick="exportlSave()" value="导出清单" /></a>
					</td>
					<td class="k"></td>
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
function setTime(time){
	if(time.indexOf(":")>0){
		var fromDate=time.substring(0,time.indexOf(":"));
		var thruDate=time.substring(time.indexOf(":")+1);
		$("#fromDateDiv").html("<input type=\"text\" class=\"Wdate\" style=\"width:100px;\" id=\"fromDate\" value=\""+fromDate+"\" onfocus=\"WdatePicker({dateFmt:'yyyy-MM-dd',opposite:true,minDate:'"+fromDate+"',maxDate:'#F{$dp.$D(thruDate)}',isShowClear:false,readOnly:true})\"/>");
		$("#thruDateDiv").html("<input type=\"text\" class=\"Wdate\" style=\"width:100px;\" id=\"thruDate\" value=\""+thruDate+"\" onfocus=\"WdatePicker({dateFmt:'yyyy-MM-dd',opposite:true,minDate:'#F{$dp.$D(fromDate)}',maxDate:'"+thruDate+"',isShowClear:false,readOnly:true})\"/>");
		if(time.indexOf("2012-10")==0 || time.indexOf("2012-09")==0){
		setBusi({"1":"语音清单","2":"短彩清单","3":"上网清单","4":"增值清单","5":"代收清单"});
		}else{
			setBusi({"GSM_L":"本地通话","GSM_T":"本地长途通话","GSM_R":"漫游通话","GSM_HZ":"国内呼转通话","SMS":"短信","ISMG":"梦网短信","MMS":"彩信","GPRS":"移动数据详单","IGPRS":"国际漫游移动数据详单","WAP":"手机上网","KJAVA":"百宝箱","WLAN":"无线局域网WLAN","SX":"声讯电话","YYZX":"移动沙龙(娱音在线)","YXHD":"语音杂志(音信互动)","LMT":"流媒体业务","BIP":"IP直通车","GWBH":"17201固网拨号上网","PIP":"预付费IP电话","SMS_ZMZL":"手机桌面助理短信","MMS_ZMZL":"手机桌面助理彩信","GAMEGP":"游戏点数充值和消费明细查询","GAMEDKXF":"游戏点数支付与充值记录对应关系查询","23":"INBOSS监控-语音","VPMN":"集团大客户业务","GIP":"企业IP通","VOIP":"企业VOIP业务","SMS_GRP":"企业短信通","VGSM":"可视电话","HGPRS":"家庭网关","MMK":"移动应用商场","SMS_MPAY":"通信账户支付","LGSM":"语音长话单分话单","WLANXY":"校园无线局域网WLAN","WLANJT":"家庭无线局域网WLAN","SMS":"短信","WLAN_IO":"WLAN国际漫游","IMS_L":"IMS家庭固话本地通话","IMS_D":"IMS家庭固话国内长途","IMS_I":"IMS家庭固话国际长途","IMS_HZ":"IMS家庭固话呼叫转移","IMS_SX":"IMS家庭固话声讯电话","WLANCERT":"家庭宽带详单","WLAN_PH":"WLAN自动认证专属资费"});
		}
	}
}

function query(){
	if($("#billType").val()=="-1"){alert("请选择业务类型");return;}
	if($("#billId").val()==""){alert("请输入手机号码");return;}
	
	$("#data").show();$("#data2").show();
	var busiName=$("#busiType").find("option:selected").text();
	$("#d1").html(busiName+"清单");
	$("#d2").html(busiName+"清单统计");
	$("#grid").html(""); $("#grid2").html("");
	
	var url="./data/QueryCommon_demo.jsp?billId="+$("#billId").val()+"&fromDate="+$("#fromDate").val()+"&thruDate="+$("#thruDate").val()+"&busiType="+$("#busiType").val()+"&destId="+$("#destId").val();
	loadData(url);
}
function loadData(url,startIndex,stopIndex){
	var newUrl=url;
	if(startIndex)newUrl+="&startIndex="+startIndex;
	if(stopIndex)newUrl+="&stopIndex="+stopIndex;
	var pageWidth=document.body.clientWidth;
	var pageHeight=document.body.clientHeight;
	
	$.post(newUrl,function(data){
		try{
			var gridHeight=pageHeight-250;
			AIOX.buildGrid({id:'grid',ds:data.data,width:pageWidth,height:gridHeight,showNO:true, page:function(startIndex, stopIndex){
				loadData(url, startIndex, stopIndex);
			}});
			//
			var stats=data.data.stats;
			var ds2_fields=[]; var ds2_contents_0=[];
			var index=0;
			for(var k in stats){
				ds2_fields[index]=k; ds2_contents_0[index]=stats[k];
				index++;
			}
			var ds2={fields:ds2_fields, contents:[ds2_contents_0]};
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
	var ds={"GSM_L":"本地通话","GSM_T":"本地长途通话","GSM_R":"漫游通话","GSM_HZ":"国内呼转通话","SMS":"短信","ISMG":"梦网短信","MMS":"彩信","GPRS":"移动数据详单","IGPRS":"国际漫游移动数据详单","WAP":"手机上网","KJAVA":"百宝箱","WLAN":"无线局域网WLAN","SX":"声讯电话","YYZX":"移动沙龙(娱音在线)","YXHD":"语音杂志(音信互动)","LMT":"流媒体业务","BIP":"IP直通车","GWBH":"17201固网拨号上网","PIP":"预付费IP电话","SMS_ZMZL":"手机桌面助理短信","MMS_ZMZL":"手机桌面助理彩信","GAMEGP":"游戏点数充值和消费明细查询","GAMEDKXF":"游戏点数支付与充值记录对应关系查询","23":"INBOSS监控-语音","VPMN":"集团大客户业务","GIP":"企业IP通","VOIP":"企业VOIP业务","SMS_GRP":"企业短信通","VGSM":"可视电话","HGPRS":"家庭网关","MMK":"移动应用商场","SMS_MPAY":"通信账户支付","LGSM":"语音长话单分话单","WLANXY":"校园无线局域网WLAN","WLANJT":"家庭无线局域网WLAN","SMS":"短信","WLAN_IO":"WLAN国际漫游","IMS_L":"IMS家庭固话本地通话","IMS_D":"IMS家庭固话国内长途","IMS_I":"IMS家庭固话国际长途","IMS_HZ":"IMS家庭固话呼叫转移","IMS_SX":"IMS家庭固话声讯电话","WLANCERT":"家庭宽带详单","WLAN_PH":"WLAN自动认证专属资费"};
	for(var k in ds){
		$("#busiType").append("<option value='"+k+"'>"+ds[k]+"</option>");
	}
}
function setBusi(ds){
	$("#busiType").html("<option value='-1'>--请选择--</option>");
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
	//initBusi();
	setTime($("#month").val());
	//initTime();
});
</script>