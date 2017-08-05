<%@page import="com.asiainfo.billing.drquery.web.servlet.RoleGrandInfo"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html;charset=UTF-8" %>
<%
String billId=request.getParameter("billId");
String billMonth=request.getParameter("billMonth");
String fromDate=request.getParameter("fromDate");
String thruDate=request.getParameter("thruDate");
String busiType=request.getParameter("busiType");
String destId=request.getParameter("destId");
String startIndex=request.getParameter("startIndex");
String stopIndex=request.getParameter("stopIndex");
 HttpSession sess=request.getSession();
    List<RoleGrandInfo> roleGrandList=(ArrayList<RoleGrandInfo>) sess.getAttribute("roleGrandList");
%>
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
        <div class="bform">
            <fieldset>
                <legend>帐前清单</legend>
            </fieldset>			
            <table class="tform">
                <tr>
                    <td class="k"></td>
                    <td class="v"></td>
                    <td colspan="3" class="v" style="padding-left:20px;">
                        <a href="#" class="button"><input type="button" onclick="exportlSave()" value="导出清单" /></a>
                    </td>
                </tr>				
            </table>
        </div>


        <div id="data" style="display:none">
            <div class="bform">
                <fieldset>
                    <legend id="d1">清单</legend>
                </fieldset>	
            </div>	
            <div id="grid"></div>
        </div>

        <div id="data2" style="display:none">
            <div class="bform">
                <fieldset>
                    <legend id="d2">清单统计</legend>
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
    function exportlSave() {
        window.open("./servlet/export?billId=<%=billId%>&fromDate=<%=fromDate%>&billMonth=<%=fromDate.substring(0,6)%>&thruDate=<%=thruDate%>&busiType=<%=busiType%>&destId=<%=destId%>"); 
    }
    function query(){
        $("#data").show();$("#data2").show();
	$("#grid").html(""); $("#grid2").html("");
        var url="./data/QueryCommon.jsp?billId=<%=billId%>&fromDate=<%=fromDate%>&billMonth=<%=fromDate.substring(0,6)%>&thruDate=<%=thruDate%>&busiType=<%=busiType%>&destId=<%=destId%>";
        loadData(url, 1, 20);
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
    $(document).ready(function(){
        query();
    });
</script>