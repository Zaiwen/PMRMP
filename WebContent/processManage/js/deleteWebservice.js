function deleteWS(id){
	if(confirm("是否将此服务删除?")){
		var url = "/BPEP/wsManage/deleteWS.jsp?id="+id;   
		 window.location.href=url; 
	}else{
		return false;
		}
}
 