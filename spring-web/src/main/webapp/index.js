/**
 * 
 */

$(document).ready(function(){
	
	$("#getChannelSMS").click(function(){
		
		$.ajax({
			url:'api/getchannelpaymentsms',
			type:'post',
			contentType:"application/json;charset=utf-8",
			datatype:'json',
			success:function(data){
				console.log(data);
				
				var str=JSON.stringify(data.content);
				$("#resultDesc").val(str);
			}
			
		});
		
	});
	
	
	
});