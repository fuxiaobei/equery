<%@page import="java.util.ArrayList"%>
<%@page import="com.asiainfo.billing.drquery.web.servlet.RoleGrandInfo"%>
<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="java.util.List" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
    HttpSession sess=request.getSession();
    List<RoleGrandInfo> roleGrandList=(ArrayList<RoleGrandInfo>) sess.getAttribute("roleGrandList");
   
    %>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <link rel="stylesheet" type="text/css" href="./css/default_v2.css"/>
        <script type="text/javascript" src="./js/jquery-1.4.2.min.js"></script>
        <script type="text/javascript" src="./js/aioci.js"></script>
        <script type="text/javascript" src="./js/My97DatePicker/WdatePicker.js"></script>
        <title>融合计费详单查询</title>
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
                <legend>融合计费详单查询</legend>
            </fieldset>
        </div>		

        <table class="tform">
            <tr>
                <td class="k">业务类型</td>
                <td class="v">
                    <select id="busiList" onchange="toggleType(this);" style="width:140px;" class="select">
                                
                    </select>
                </td>
                <td class="k">查询号码</td>
                <td class="v">
                    <%--
                    <select id="options_month"   class="select">
                                <option value='13482000195'>13482000195</option>
                        </select>
                    --%>
                    <input id="phoneNo" style="width:110px;">
                </td>

                <td class="k">交换机代码</td>
                <td class="v">
                    <select id="switchCode"  style="width:75px;" class="select">
                        <option value=''></option>
                    </select>
                </td>
                <td class="k">归属局</td>
                <td class="v">
                    <select id="attributionProv"  style="width:50px;" class="select">
                        <option value=''></option>
                    </select>
                    <select id="attributionCity"  style="width:50px;" class="select">
                        <option value=''></option>
                    </select>
                </td>

                <td class="k">漫游局</td>
                <td class="v">
                    <select id="roamProv"  style="width:50px;" class="select">
                        <option value=''></option>
                    </select>
                    <select id="roamCity"   style="width:50px;" class="select">
                        <option value=''></option>
                    </select>
                </td>
            </tr>
            <table>
              


                <table class="ai-grid-simple" width="100%">
                    <tr>
                        <td width="10%">
                            <div class="field">
                                <fieldset>
                                    <legend>时间类别</legend>
                                    <br>
                                    <input type="radio" name="timeType" checked="checked" value="telTime"/> 话单时间<br>
                                    <br><br>
                                    <input type="radio" name="timeType" value="checkinTime"/> 入库时间<br>
                                    <br>
                                </fieldset>
                            </div>
                        </td>
                        <td width="10%">
                            <div class="field">
                                <fieldset>
                                    <legend>查询依据</legend>
                                    <input type="radio" name="queryBasis" id="phoneNumber" checked="checked" value="phoneNumber"/> 手机号<br>
                                    <input type="radio" name="queryBasis" id="MIN" value="MIN"/> IMSI/MIN号<br>
                                    <input type="radio" name="queryBasis" id="peerNumber" value="peerNumber"/> 对端号码<br>
                                    <input type="radio" name="queryBasis" id="account" value="account"/> 帐号<br>
                                    <input type="radio" name="queryBasis" id="IMEL" value="IMEI" /> 手机串号
                                </fieldset>
                            </div>
                        </td>
                        <td width="20%">
                            <div class="field">
                                <fieldset>
                                    <legend>时间范围</legend>
                                    起始:<input type="text" id="fromTime" style="width:160px;" class="text" onFocus="WdatePicker({startDate:'%y-%M-01 00:00:00',dateFmt:'yyyy-MM-dd HH:mm:ss',alwaysUseStartDate:true})"/><br>
                                    <br>
                                    终止:<input type="text" id="thruTime" style="width:160px;" class="text" onFocus="WdatePicker({startDate:'%y-%M-01 00:00:00',dateFmt:'yyyy-MM-dd HH:mm:ss',alwaysUseStartDate:true})"/><br>
                                </fieldset>
                                <br>
                                小区代码<input type="text" id="districtNumber" style="width:50px;" class="text"/>
                                基站代码<input type="text" id="baseStationNumber" style="width:50px;" class="text"/>
                            </div>
                        </td>
                        <td width="10%">
                            <div class="field">
                                <fieldset>
                                    <legend>长话类型</legend>
                                    <input type="checkbox" name="longDistanceType" value="localCall"/> 市话<br>
                                    <input type="checkbox" name="longDistanceType"  value="longdistanceCall"/> 长话<br>
                                    <br><br><br><br>
                                </fieldset>
                            </div>
                        </td>
                        <td width="10%">
                            <div class="field">
                                <fieldset>
                                    <legend>对端号码</legend>
                                    <input type="checkbox" name="PeerNumber" value="IP"/> IP<br>
                                    <input type="checkbox" name="PeerNumber" value="WAP"/> WAP<br>
                                    <input type="checkbox" name="PeerNumber" value="OTHER"/> 其他<br>
                                    <br><br><br>
                                </fieldset>
                            </div>
                        </td>
                        <td width="10%">
                            <div class="field">
                                <fieldset>
                                    <legend>漫游类型</legend>
                                    <input type="checkbox" name="RoamingType" value="0"/> 本地<br>
                                    <input type="checkbox" name="RoamingType" value="2"/> 省际漫入<br>
                                    <input type="checkbox" name="RoamingType" value="4"/> 省际漫出<br>
                                    <input type="checkbox" name="RoamingType" value="3"/> 国际漫入<br>
                                    <input type="checkbox" name="RoamingType" value="5"/> 国际漫出<br>
                                    <%-- <input type="checkbox" name="RoamingType" value="parseWrongdoc"/> 解析错单<br>--%>
                                </fieldset>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2"></td>
                        <td colspan="4">
                            <a href="#" class="button"><input onclick="query();" type="button" value="查询" style="cursor: pointer;"/></a>
                            <%--<a href="#" class="disable"><input type="button" onclick="preExport();" style="cursor: pointer;" value="保存" disabled="disabled"/></a>--%>
                             <a href="#" class="disable"><input type="button" onclick="preExport();" style="cursor: pointer;" value="保存" /></a>
                        </td>
                    </tr>
                </table>
                                <input type="hidden" id="opType" value="">
                <div id="data" style="display:none">
                    <div class="bform">
                        <fieldset>
                            <legend>清单</legend>
                        </fieldset>
                    </div>
                    <div id="loading" style="position:absolute;text-align:center;z-index:100;width:100%;height:100%;background-color:E7E7E7;"><img src="<%=path %>/images/load.gif" /></div>
                    <div id="grid"></div>
                </div>

                <div id="reason"  style="display:none;z-index: 1000;" class="white_content">
                    
                     <div class="bform">
                          <fieldset>
                          <legend>敏感信息查询</legend>
                        </fieldset>
                     </div>	
                    <div class="field">
                        <fieldset>
                            <legend>请输入查询原因</legend>
                       
                    
                      注意：如果没有输入原因，则以下拉框选择的原因录入
                    <div style="margin-left: 8px; padding-bottom: 2px;">
                        <div>
                            <textarea cols="45" rows="9" id="reasonText">  </textarea>                        
                       </div>
                         <p></p>
                        <div>
                            <select id="reasonSelect" style="width:380px;" class="select">
                                <option value=""></option>
                                <option value=""></option>
                                <option value=""></option>
                                <option value=""></option>
                                <option value=""></option>
                            </select>   
                            
                    </div>
                        <p></p>
                        <div>
                            <a href="#" class="button"><input onclick="saveReason();" type="button" style="cursor: pointer;" value="确定"/></a>
                                <a href="#" class="button"><input type="button" onclick="cancel();" style="cursor: pointer;"  value="取消" /></a>
                        </div>
                        </div>
                       </fieldset>
                </div>

                </BODY>
                </HTML>
                
                <script type="text/javascript">
                    var path="<%=path%>";
                    var url=path+"/servlet/drrbossBill?1=1";
               
                function getParam(){
                    
                     var param="";
                         var busi=$("#busiList").val();
                         if(busi==""){
                            alert("请选择业务类型");
                            $("#busiList").focus();
                            return false;
                       }else{
                           param+="businessType="+busi;
                       }
                        var phoneNo=$("#phoneNo").val();
                        if(phoneNo!=""){
                            param+="&phoneNo="+encodeURIComponent(encodeURIComponent(phoneNo));
                       }
                        var switchCode=$("#switchCode").val();
                        if(switchCode!="")param+="&switchCode="+switchCode;
                        
                        var attributionProv=$("#attributionProv").val();
                        if(attributionProv!="")param+="&attributionProv="+attributionProv;
                        
                        var attributionCity=$("#attributionCity").val();
                        if(attributionCity!="")param+="&attributionCity="+attributionCity;
                        
                        var roamProv=$("#roamProv").val();
                        if(roamProv&&roamProv!="")param+="&roamProv="+roamProv;
                        
                        var roamCity=$("#roamCity").val();
                        if(roamCity&&roamCity!="")param+="&roamCity="+roamCity;
                        
                        var timeType=$("input[name='timeType']:checked").val();
                        if(timeType&&timeType!="")param+="&timeType="+timeType;
                        
                        var queryBasis=$("input[name='queryBasis']:checked").val();
                        if(queryBasis&&queryBasis!="")param+="&queryBasis="+queryBasis;
                        
                       
                        
                        var fromTime=$("#fromTime").val();
                        if(fromTime==""){
                            alert("请选择起始时间");
                            return false;
                       }else{
                            if(fromTime&&fromTime!="")param+="&fromTime="+encodeURIComponent(fromTime);
                       }
                       
                        var thruTime=$("#thruTime").val();
                        if(thruTime==""){
                            alert("请选择终止时间");
                            return false;
                       }else{
                            if(thruTime&&thruTime!="")param+="&thruTime="+encodeURIComponent(thruTime);
                       }
                       if(thruTime<fromTime){alert("终止时间要大于开始时间");return false;}
                        var districtNumber=$("#districtNumber").val();
                         if(districtNumber&&districtNumber!="")param+="&districtNumber="+districtNumber;
                         
                        var baseStationNumber=$("#baseStationNumber").val();
                         if(baseStationNumber&&baseStationNumber!="")param+="&baseStationNumber="+baseStationNumber;
                         
                          if($.trim(phoneNo)==""&&$.trim(switchCode)==""&&$.trim(districtNumber)==""&&$.trim(baseStationNumber)==""){
                            alert("查询号码，交换机代码，小区代码，基站代码至少选填一个");
                            return false;
                        }
                        var longDistanceType="";
                        var PeerNumber="";
                        var RoamingType="";
                        
                        $("input[name='longDistanceType']:checkbox:checked").each(function(){ 
                                    longDistanceType+=$(this).val()+",";
                        })
                        longDistanceType=longDistanceType.substring(0,longDistanceType.length-1);
                        
                         if(longDistanceType&&longDistanceType!="")param+="&longDistanceType="+encodeURIComponent(longDistanceType);
                        
                         $("input[name='PeerNumber']:checkbox:checked").each(function(){ 
                                    PeerNumber+=$(this).val()+",";
                        })
                        PeerNumber=PeerNumber.substring(0,PeerNumber.length-1);
                        
                         if(PeerNumber&&PeerNumber!="")param+="&PeerNumber="+encodeURIComponent(PeerNumber);
                         
                        $("input[name='RoamingType']:checkbox:checked").each(function(){ 
                                   RoamingType+=$(this).val()+",";
                        })
                        RoamingType=RoamingType.substring(0,RoamingType.length-1);
                        
                     if(RoamingType&&RoamingType!="")param+="&RoamingType="+encodeURIComponent(RoamingType);
                      return param;
                }
                
                   function query(){
                        var f=getParam();
                        if(f==false)return;
                        $("#opType").val("query")
                      if(window.confirm("详单查询将产生日志记录，确定要做此操作吗？")){
                            $("#reason").show();
                            $("#reasonText").val("");
                            
//                        document.getElementById("data").style.display="";
//
//                        var ds={fields:["序号","登记类型","发送时间","呼叫类型","对端号码","信息费","消息长度(字节)"], 
//                            contents:[["1","2012/03/28 01:53:36","2012/03/28 01:53:36","发送","8652195","0.1","3"],
//                                ["1","2012/03/28 01:53:36","2012/03/28 01:53:36","发送","8652195","0.1","3"],
//                                ["1","2012/03/28 01:53:36","2012/03/28 01:53:36","发送","8652195","0.1","3"],
//                                ["1","2012/03/28 01:53:36","2012/03/28 01:53:36","发送","8652195","0.1","3"],
//                                ["1","2012/03/28 01:53:36","2012/03/28 01:53:36","发送","8652195","0.1","3"],
//                                ["1","2012/03/28 01:53:36","2012/03/28 01:53:36","发送","8652195","0.1","3"]]};
//		
//                        var gridHeight=pageHeight-251;	
//                        AIOX.buildGrid({id:'grid',ds:ds,width:pageWidth,height:gridHeight});
                    }

                   }
                   
                    function initBusiType(){
                        var busiList=$("#busiList");
                        var method="businessType";
                        var _url=url+"&method="+method;
                        try{
                            jQuery.get(_url, function(data){
                                if(data!=null){
                                    if(data.response.result=="success"){
                                        var list=data.response.list;
                                        createSelect(list,busiList);
                                    }
                                    
                                }
            
                            },"json");
                        }catch(e){
                           
                        }
        
                    }
    
                 function cancel(){
                    document.getElementById('reason').style.display='none';
                     return;
                 }
                 
                 function saveReason(){
                     
                     var reason=$("#reasonText").val();
                     var reasonSel=$("#reasonSelect").val();
                     var saveUrl=path+"/servlet/saveReason?date="+new Date();
                     if($.trim(reason)==""&&$.trim(reasonSel)==""){
                         alert("请填写查询原因");
                         return;
                     }
                     
                     if($.trim(reason)!=""){
                         saveUrl+="&reason="+encodeURIComponent(encodeURIComponent(reason));
                     }else{
                          saveUrl+="&reason="+encodeURIComponent(encodeURIComponent(reasonSel));
                     }
                     
                     //document.getElementById('reason').style.display='none';
                     $("#reason").hide();
                     jQuery.post(saveUrl,function(data){
                         if(data=="success"){
                             alert(data);
                            if($("#opType").val()=="query"){
                             $("#grid").html("");
                            document.getElementById("data").style.display="";
                            $("#loading").fadeIn();
                                loadData();
                            }else if($("#opType").val()=="export"){
                                exportData();
                            }
                         }else{
                             alert("保存失败!");
                             $("#loading").fadeOut();
                             return;
                         }
                         
                     },"string");
                    
                 }
                 
                 function loadData(){
                   var _url=path+"/servlet/drrbossBill?date="+new Date();
                         _url+="&method=queryByItems";
                      var param=getParam();
                      
                 jQuery.get(_url, param, function(data){
                      var pageWidth=document.body.clientWidth;
                      var pageHeight=document.body.clientHeight;
                      var ds=data;
                      var gridHeight=pageHeight-251;
                      AIOX.buildGrid({id:'grid',ds:ds,width:pageWidth,height:gridHeight,showNO:true});
                         $("#loading").fadeOut();
                     },"json");
                 
                 }
                 
                    function initSwtichType(){
                        var _url=path+"/servlet/drrbossBill?1=1";
                        var switchList=$("#switchCode");
                        var method="switchType";
                        _url+="&method="+method;
                        try{
                            jQuery.get(_url, function(data){
                                if(data!=null){
                                    if(data.response.result=="success"){
                                        var list=data.response.list;
                                        createSelect(list,switchList);

                                    }
            
                                }
            
                            },"json");
                        }catch(e){
                            
                        }
        
                    }
    
                    function  createSelect(list,selectId){
                        selectId.empty();
                        for(var i=0;i<list.length;i++){
                            var obj=list[i];
                            var option=$("<option ></option>");
                            
                                for(var key in obj ){
                                    option.attr("value",key);
                                    option.text(obj[key]);
                                }
                            selectId.append(option);
                        }
                            selectId.prepend("<option value=''></option>");
                            selectId.val("");
                            
                    }


                    function initAreaCode(prov,city){
                        var _url=path+"/servlet/drrbossBill?1=1&method=attribution";
                        $.getJSON(_url,function(data){
                            if(data!=null){
                                if(data.response.result=="success"){
                                    var list=data.response.list;
                                    createSelect(list,prov);
                                 
                                }
                                    
                            }
                        });
    
    
                        //省份change事件  当省份中的值发生改变后，响应请求
                       prov.change(function(){
                            var provCode=prov.val();
                            
                            var _cityUrl=path+"/servlet/drrbossBill?1=1&method=attribution&provCode="+provCode;
                            $.getJSON(_cityUrl,function(data){
                               
                                if(data!=null){
                                if(data.response.result=="success"){
                                    var list=data.response.list;
                                   
                                    createSelect(list,city);
                                 }
                                }
                            });
                        });
                    }
                    
                    function  toggleType(o){
                        var v=o.value;
                        if(v=="message" || v=="mmessage"){
                            $("#IMEL").attr("disabled", true);
                            $("#peerNumber").attr("disabled", false);
                            $("#switchCode").attr("disabled",true); 
                            $("input[name='RoamingType']").attr("disabled",true);
                            $("input[name='longDistanceType']").attr("disabled",true);
                            $("input[name='PeerNumber']").attr("disabled",true);
                        }else if(v=="localCall"){
                            $("#IMEL").attr("disabled", false);
                            $("#peerNumber").attr("disabled", false);
                            $("input[name='RoamingType']").attr("disabled",false);
                            $("input[name='longDistanceType']").attr("disabled",false);
                            $("input[name='PeerNumber']").attr("disabled",false);
                            $("#switchCode").attr("disabled",false);
                        } else if(v=="ggprs"){
                            $("#IMEL").attr("disabled", true);
                            $("#peerNumber").attr("disabled", true);
                            $("input[name='RoamingType']").attr("disabled",true);
                            $("input[name='longDistanceType']").attr("disabled",true);
                            $("input[name='PeerNumber']").attr("disabled",true);
                            $("#switchCode").attr("disabled",true);
                        }else if(v=="wlan"){
                            $("#IMEL").attr("disabled", true);
                            $("#peerNumber").attr("disabled", true);
                            $("input[name='longDistanceType']").attr("disabled",true);
                            $("input[name='PeerNumber']").attr("disabled",true);
                            $("#switchCode").attr("disabled",true);
                        }else if(v=="kjava"){
                            $("#IMEL").attr("disabled", true);
                            $("#peerNumber").attr("disabled", false);
                            $("input[name='RoamingType']").attr("disabled",false);
                            $("input[name='longDistanceType']").attr("disabled",true);
                            $("input[name='PeerNumber']").attr("disabled",true);
                            $("#switchCode").attr("disabled",true);
                        }else{
                            $("#IMEL").attr("disabled", false);
                            $("#peerNumber").attr("disabled", false);
                            $("input[name='RoamingType']").attr("disabled",false);
                            $("input[name='longDistanceType']").attr("disabled",false);
                            $("input[name='PeerNumber']").attr("disabled",false);
                            $("#switchCode").attr("disabled",false);
                        }
                    
                    }
                    
                    
                  function preExport() {
                      var f=getParam();
                        if(f==false)return;
                        $("#opType").val("export")
                         if(window.confirm("详单导出将产生日志记录，确定要做此操作吗？")){
                            $("#reason").show();
                            $("#reasonText").val("");
                         }     
                      
                   }
                   
                   
                   function  exportData(){
                        var param=getParam();
                        window.open("./servlet/drrbossBill?method=export&1=1&"+param); 
                       
                   } 
                    
                    $(document).ready(function(){
                        initBusiType();
                        initSwtichType();
                        initAreaCode($("#attributionProv"),$("#attributionCity"));
                        initAreaCode($("#roamProv"),$("#roamCity"));
                    });

                </script>