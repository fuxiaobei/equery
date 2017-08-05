var AIOX={};
AIOX['buildGrid']=function(options){
	var div=options.id;
	var url=options.url;
	var ds=options.ds;
	var width=options.width;
        var height=options.height;
	var page=options.page;
	var showNO=options.showNO;
	var table=$("#"+div);
	var pageHeight=0;
	var pageSize=20;
	var totalCount=ds.totalCount;var startIndex=ds.startIndex;var stopIndex=ds.stopIndex;
	var pageNum=1;var nowPage=1;
        if(options.pageSize)pageSize=options.pageSize;
 
	if(!ds && url){
		$.post(url,function(data){
			options["ds"]=data;
			AIOX.buildGrid(options);
		},json)
		return ;
	}
	if(page)pageHeight=20;
	var tableHtml="";
	var hasData=false;
	try{
		
		
		tableHtml+="<div style='width:"+width+";height:"+(height-pageHeight)+"' id='"+div+"_grid_body' class='ai-grid-data'>"
		//thead
		tableHtml+="<div style='position:relative' id='"+div+"_grid_head'>";
		
		var fields=ds.fields;
		tableHtml+="<table class='ai-grid-table'><tr class='ai-grid-table-head' id='"+div+"_grid_htr'>";
		if(fields){
			if(showNO)tableHtml+="<td class='ai-g-h-td'>序号</td>";
			for(var i=0;i<fields.length;i++){
				tableHtml+="<td class='ai-g-h-td'>"+fields[i]+"</td>";
			}
		}
		tableHtml+="</tr>";
		tableHtml+="</table></div>";
		//tbody
		tableHtml+="<table class='ai-grid-table'>";
		tableHtml+="<tbody class='ai-grid-table-body'>";
		var list=ds.contents;
		if(list){
			if(list.length>0)hasData=true;
			for(var k=0;k<list.length;k++){
				var d=list[k];
				tableHtml+="<tr id='"+div+"_grid_data_"+k+"'>";
				if(showNO)tableHtml+="<td>"+(k+1)+"</td>";
				for(var i=0;i<d.length;i++){
					tableHtml+="<td>"+d[i]+"</td>";
				}
				tableHtml+="</tr>";
			}
		}
		tableHtml+="</tbody></table></div>";
		
		if(pageHeight>0){
			if(!totalCount)totalCount=0;
			try{
				nowPage=Math.ceil(startIndex/pageSize);pageNum=Math.ceil(totalCount/pageSize);
                                if(nowPage==0)nowPage=1;
			}catch(e){}
			tableHtml+="<div class='ai-grid-foot' style='width:"+width+"'>";
			tableHtml+="<table><tr><td>共"+pageNum+"页  "+totalCount+"条 第<input type='text' class='ai-grid-page-num' value='"+nowPage+"'/>页   每页<span id='"+div+"_selectSpan'></span>条</td>";
			tableHtml+="<td align='right'><span class='ai-grid-page-first' id='"+div+"_page_first'></span> <span class='ai-grid-page-prev' id='"+div+"_page_prev'></span> <span class='ai-grid-page-next' id='"+div+"_page_next'></span> <span class='ai-grid-page-last' id='"+div+"_page_last'></span></td>";
			tableHtml+="</tr></table></div>";
		}
	}catch(e){}
	table.html(tableHtml);
	//head
	if(hasData){
		var htds=$("#"+div+"_grid_htr").find("td");
		var dtds=$("#"+div+"_grid_data_0").find("td");
		for(var i=0;i<htds.length;i++){
			var htd=$(htds[i]);var dtd=$(dtds[i]);
			if(htd.width()>dtd.width()){
				dtd.html("<div style='width:"+htd.width()+";white-space:nowrap;'>"+dtd.html()+"</div>");
			}else{
				htd.html("<div style='width:"+dtd.width()+";white-space:nowrap;'>"+htd.html()+"</div>");
			}
		}
	}
        
            
            $("#"+div+"_grid_body tr:odd").addClass("bgeaeff1");
			$("#"+div+"_grid_body tr:even").addClass("bgfefefe");
			$("#"+div+"_grid_body tr").mouseover(function(){
                            $(this).addClass("bgc0d0e0");
				});
			$("#"+div+"_grid_body tr").mouseout(function(){
                        	$(this).removeClass("bgc0d0e0");
			});
        
	$("#"+div+"_grid_body").scroll(function(){
		$("#"+div+"_grid_head").css("top",$(this).scrollTop());
	});
	//
        
        $("#"+div+"_selectSpan").html("<select id='"+div+"_pageSize'><option value='20'>20</option><option value='50'>50</option><option value='100'>100</option></select>");
        $("#"+div+"_pageSize").val(pageSize);
        $("#"+div+"_pageSize").change(function(){
            try{
                    pageSize=this.value;
                    page(1,pageSize,pageSize);
		}catch(e){}
        });
	$("#"+div+"_page_first").click(function(){
		try{
			if(parseInt(startIndex)<=1)return;
			page(1,pageSize,pageSize);
		}catch(e){}
	});
	$("#"+div+"_page_prev").click(function(){
		try{
			if(startIndex<=1)return;
			startIndex=parseInt(startIndex)-parseInt(pageSize);
			if(startIndex<0)startIndex=1;
			stopIndex=parseInt(startIndex)+parseInt(pageSize)-1;
			page(startIndex, stopIndex,pageSize);
		}catch(e){}
	});
	$("#"+div+"_page_next").click(function(){
		try{
			if(parseInt(stopIndex)>=parseInt(totalCount))return;
			startIndex=parseInt(startIndex)+parseInt(pageSize);
			stopIndex=parseInt(startIndex)+parseInt(pageSize)-1;
			page(startIndex, stopIndex,pageSize);
		}catch(e){}
	});
	$("#"+div+"_page_last").click(function(){
		try{
			if(parseInt(stopIndex)>=parseInt(totalCount))return;
			startIndex=Math.floor(parseInt(totalCount)/parseInt(pageSize))*parseInt(pageSize)+1;
			stopIndex=parseInt(startIndex)+parseInt(pageSize)-1;
			page(startIndex-pageSize,stopIndex-pageSize,pageSize);
		}catch(e){}
	});
};

(function($){ 
$.chromatable = { 
	defaults: { 
		width: "900px", 
		height: "300px", 
		scrolling: "yes" 
	} 
}; 
$.fn.chromatable = function(options){ 
	var options = $.extend({}, $.chromatable.defaults, options); 
	return this.each(function(){ 
		var $this = $(this); 
		var $uniqueID = $(this).attr("ID") + ("wrapper"); 
		$(this).css('width', options.width).addClass("_scrolling"); 
		$(this).wrap('<div class="scrolling_outer"><div id="'+$uniqueID+'" class="scrolling_inner"></div></div>'); 
		$(".scrolling_outer").css({'position':'relative'}); 
		$("#"+$uniqueID).css( 
			{'border':'1px solid #CCCCCC', 
			'overflow-x':'hidden', 
			'overflow-y':'auto', 
			'padding-right':'17px' 
			}); 
		$("#"+$uniqueID).css('height', options.height); 
		$("#"+$uniqueID).css('width', options.width); 
		$(this).before($(this).clone().attr("id", "").addClass("_thead").css( 
			{'width' : 'auto', 
			'display' : 'block', 
			'position':'absolute', 
			'border':'none', 
			'border-bottom':'1px solid #CCC', 
			'top':'1px' 
		})); 
		$('._thead').children('tbody').remove(); 
		$(this).each(function( $this ){ 
			if (options.width == "100%" || options.width == "auto") { 
				$("#"+$uniqueID).css({'padding-right':'0px'}); 
			} 
			if (options.scrolling == "no") { 
				$("#"+$uniqueID).before('<a href="#" class="expander" style="width:100%;">Expand table</a>'); 
				$("#"+$uniqueID).css({'padding-right':'0px'}); 
				$(".expander").each( 
					function(int){ 
					$(this).attr("ID", int); 
					$( this ).bind ("click",function(){ 
						$("#"+$uniqueID).css({'height':'auto'}); 
						$("#"+$uniqueID+" ._thead").remove(); 
						$(this).remove(); 
					}); 
				}); 
				$("#"+$uniqueID).resizable({handles: 's'}).css("overflow-y", "hidden"); 
			} 
		}); 
		$curr = $this.prev(); 
		$("thead:eq(0)>tr th",this).each( function (i) { 
			$("thead:eq(0)>tr th:eq("+i+")", $curr).width( $(this).width()); 
		}); 
		if (options.width == "100%" || "auto"){ 
			$(window).resize(function(){ 
			resizer($this); 
		}); 
	} 
}); 
}; 
function resizer($this) { 
	$curr = $this.prev(); 
	$("thead:eq(0)>tr th", $this).each( function (i) { 
		$("thead:eq(0)>tr th:eq("+i+")", $curr).width( $(this).width()); 
	}); 
}; 
})(jQuery); 