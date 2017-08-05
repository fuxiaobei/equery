<%@page import="com.asiainfo.billing.drquery.web.servlet.RoleGrandInfo"%>
<%@page import="java.util.List"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html;charset=UTF-8" %>
<%
String billId=null,billMonth=null,fromDate=null,thruDate=null,busiType=null,destId=null,startIndex=null,stopIndex=null;
        billId = request.getParameter("billId");
        billMonth = request.getParameter("billMonth");
        fromDate = request.getParameter("fromDate");
        thruDate = request.getParameter("thruDate");
        busiType = request.getParameter("busiType");
        destId = request.getParameter("destId");
        startIndex = request.getParameter("startIndex");
        stopIndex = request.getParameter("stopIndex");
 HttpSession sess=request.getSession();
 List<RoleGrandInfo> roleGrandList=(ArrayList<RoleGrandInfo>) sess.getAttribute("roleGrandList");
%>
<html>
    <head>
        <link rel="stylesheet" type="text/css" href="./css/default_v2.css"/>
        <script type="text/javascript" src="./js/jquery-1.4.2.min.js"></script>
        <script type="text/javascript" src="./js/aioci.js"></script>
        <script type="text/javascript" src="./js/My97DatePicker/WdatePicker.js"></script>
        <title>历史详单</title>
    </head>
    <body>

        <div class="bform">
            <fieldset>
                <legend>
                    详单信息查询
                </legend>
            </fieldset>		
            <table class="tform">
                <tr>
                </tr>

                <tr>
                </tr>

                <tr>
                    <td></td>
                    <td colspan="3" class="v">
                        <input type="button" onclick="exportSave()" value="导出"/>
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
    var billId="<%=billId%>";
    var billMonth="<%=billMonth%>";
    var fromDate="<%=fromDate%>";
    var thruDate="<%=thruDate%>";
    var busiType="<%=busiType%>";
    var destId="<%=destId%>";
    var startIndex="<%=startIndex%>";
    var stopIndex="<%=stopIndex%>";
    function exportSave() {
        billMonth=fromDate.substring(0, 6);
        window.open("./servlet/export?billId=billId&fromDate=fromDate&billMonth=billMonth&thruDate=thruDate&busiType=busiType&destId=destId"); 
    }
    function query(){
        document.getElementById("data").style.display="";
        billMonth=fromDate.substring(0, 6);
        var url="./data/QueryCommon.jsp?billId=billId&fromDate=fromDate&billMonth=billMonth&thruDate=thruDate&busiType=busiType&destId=destId";
        loadData(url, 1, 20);
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

    $(document).ready(function(){
        query();
    });
</script>