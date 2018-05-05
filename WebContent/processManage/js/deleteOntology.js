function deleteOnto(id,name) {
    if(confirm("是否将此本体删除?")){
        var url = "/BPEP/deleteOntologycheck?id="+id+"&name="+name;
        if (window.XMLHttpRequest) { 
            req = new XMLHttpRequest(); 
        }else if (window.ActiveXObject) { 
            req = new ActiveXObject("Microsoft.XMLHTTP"); 
        } 
	    if (req) {
		    req.open("POST", url, true);
		    req.onreadystatechange = complete_delete;	
		    req.send();
	    }
    }else{
        return false;
    } 
}

function complete_delete(){
      if (req.readyState == 4) {
              if (req.status == 200) {      
              var text = req.responseText;                 
                 alert(text);
                 document.execCommand('Refresh');
                  //var div=document.getElementById("Layer1");
                  //div.innerHTML=text;              
              }
              }
      }