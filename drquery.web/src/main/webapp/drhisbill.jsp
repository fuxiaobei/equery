<%@page contentType="text/html;charset=UTF-8" %>
<html>
<head>

<link rel="stylesheet" type="text/css" href="./css/default_v2.css"/>
<script type="text/javascript" src="./js/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="./js/aioci.js"></script>
<script type="text/javascript" src="./js/My97DatePicker/WdatePicker.js"></script>
<title>历史详单</title>
</head>
<body>
<%
java.util.Calendar calendar=java.util.Calendar.getInstance();
java.text.SimpleDateFormat df=new java.text.SimpleDateFormat("yyyy-MM-dd");
calendar.set(java.util.Calendar.DATE, 1);
calendar.add(java.util.Calendar.DATE, -1);
String thruDate=df.format(calendar.getTime());
calendar.set(java.util.Calendar.DATE, 1);
String fromDate=df.format(calendar.getTime());
%>
	 <div class="bform">
		<fieldset>
			<legend>
			详单信息查询
			</legend>
		</fieldset>		
		    <input type="hidden" id="year" value="2012">
			<input type="hidden" id="input_bill_id" value=""/>
			<table class="tform">
				<tr>
					<td class="k">起始日期</td>
					<td class="v">
						<input type="text" class="text" id="fromDate" value="<%=fromDate%>" onFocus="WdatePicker({startDate:'%y-%M-01',dateFmt:'yyyy-MM-dd',alwaysUseStartDate:true})">
					</td>
					<td class="k">结束日期</td>
					<td class="v">
						<input type="text" class="text" id="thruDate" value="<%=thruDate%>" onFocus="WdatePicker({startDate:'%y-%M-%d',dateFmt:'yyyy-MM-dd',alwaysUseStartDate:true})">
					</td>
				</tr>
				
				<tr>
					<td class="k">手机号码</td>
					<td class="v">
						<input type="text" class="text" value="" id="billId" style="width:170px;" title="" />
					</td>
					<td class="k">话单类型</td>
					<td class="v">
						<select id="billType" style="width:170px;" class="select">
							<option value=''>企业VOIP</option>
						</select>
					</td>
				</tr>
				
				<tr>
					<td></td>
					<td colspan="3" class="v">
						<a href="#" class="button"><input onclick="query();" type="button" value="查询"/></a>
						<a href="#" class="disable"><input type="button" onclick="queryrec();" value="导出" disabled="disabled"/></a>
						<a href="#" class="disable"><input type="button" onclick="prebillSave()" value="打印" disabled="disabled"/></a>

					</td>
				</tr>				
			</table>
	</div>
	
	<div id="data" style="display:none">
		<div class="bform">
			<fieldset>
				<legend>详单明细</legend>
			</fieldset>
		</div>	
		
		<div id="grid"></div>
		
		<div>
			<input type="radio" name="r1" checked="checked"/>在现有数据中过滤
			<input type="radio" name="r1"/>不过滤
		</div>
	</div>
	
	<div id="data" style="display:none">
		<div class="bform">
			<fieldset>
				<legend>详单明细</legend>
			</fieldset>
		</div>	
		
		<div id="grid"></div>
		
		<div>
			<input type="radio" name="r1" checked="checked"/>在现有数据中过滤
			<input type="radio" name="r1"/>不过滤
		</div>
	</div>
</div>	
	

</BODY>
</HTML>
<script type="text/javascript">
function query(url){
	document.getElementById("data").style.display="";

	url="./data/QueryCommon.jsp?billId="+$("#billId").val()+"&billType="+$("#billType").val();

	loadData(url);
	/*
	document.getElementById("test").innerHTML="<table class='ai-grid-table' id='grid2'><thead>"
		+document.getElementById("head").innerHTML
		+"</thead></table>";
		
	$.each($("#data_grid").find("thead th"), function(i,item){
		$("#grid2").find("thead th").eq(i).width($(item).width());  
		$(item).width($(item).width());
	});


	
	$("#grid").scroll(function(){ 
		document.getElementById("headtr").style.top=$(this).scrollTop();
		//$("#test").css("top":$(this).scrollTop());
	});	
	*/
}

function loadData(url, startIndex, stopIndex){
	var newUrl=url;
	if(startIndex)newUrl+="&startIndex="+startIndex;
	if(stopIndex)newUrl+="&stopIndex="+stopIndex;
	var pageWidth=document.body.clientWidth;
	var pageHeight=document.body.clientHeight;
	
	$.post(newUrl,function(data){
		try{
			var gridHeight=pageHeight-180;
			AIOX.buildGrid({id:'grid',ds:data.data,width:pageWidth,height:gridHeight,page:function(startIndex, stopIndex){
				loadData(url, startIndex, stopIndex);//
			}});
		}catch(e){}
	},"json")
}

function changeMonth(value){
	$("#options_month2").html("<option value='"+value+"'>"+value+"</option>");
	initTime(value);
}
function initBusi(){
	$("#billType").html("<option value='-1'>--请选择--</option>");
	var ds={"GSM_L":"本地通话","GSM_T":"本地长途通话","GSM_R":"漫游通话","GSM_HZ":"国内呼转通话","SMS":"短信","ISMG":"梦网短信","MMS":"彩信","GPRS":"移动数据详单","IGPRS":"国际漫游移动数据详单","WAP":"手机上网","KJAVA":"百宝箱","WLAN":"无线局域网WLAN","SX":"声询电话","YYZX":"移动沙龙(娱音在线)","YXHD":"语音杂志(音信互动)","LMT":"流媒体业务","BIP":"IP直通车","GWBH":"17201固网拨号上网","PIP":"预付费IP电话","SMS_ZMZL":"手机桌面助理短信","MMS_ZMZL":"手机桌面助理彩信","28":"游戏点数充值和消费明细查询","29":"游戏点数支付与充值记录对应关系查询","23":"INBOSS监控-语音","VPMN":"集团大客户业务","GIP":"企业IP通","VOIP":"企业VOIP业务","SMS_GRP":"企业短信通","VGSM":"可视电话","HGPRS":"家庭网关","MMK":"移动应用商场","SMS_MPAY":"通信账户支付","LGSM":"语音长话单分话单","WLANXY":"校园无线局域网WLAN","WLANJT":"家庭无线局域网WLAN","SMS":"短信","WLAN_IO":"WLAN国际漫游","IMS_L":"IMS家庭固话本地通话","IMS_D":"IMS家庭固话国内长途","IMS_I":"IMS家庭固话国际长途","IMS_HZ":"IMS家庭固话呼叫转移","IMS_SX":"IMS家庭固话声讯电话","WLANCERT":"家庭宽带详单","WLAN_PH":"WLAN自动认证专属资费"};
	for(var k in ds){
		$("#billType").append("<option value='"+k+"'>"+ds[k]+"</option>");
	}
}
function initTime(month){
	var date1=$("#options_date1"); var date2=$("#options_date2");
	date1.html("");date2.html("");
	var ms=31;
	if(month){
		if(month.indexOf("-04")>0 || month.indexOf("-06")>0 || month.indexOf("-09")>0 || month.indexOf("-11")>0)ms=30;
		else if(month.indexOf("-02")>0)ms=29;
	}
	for(var i=1;i<=ms;i++){
		var dstr=i;if(i<10)dstr="0"+i;
		date1.append("<option value='"+dstr+"'>"+dstr+"</option>");
		if(i==ms)
			date2.append("<option value='"+dstr+"' selected='selected'>"+dstr+"</option>");
		else
			date2.append("<option value='"+dstr+"' >"+dstr+"</option>");
	}
}

$(document).ready(function(){
	initBusi();
	//initTime('2012-04');
});
</script>