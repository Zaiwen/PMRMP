(function (){
    var dlg=null;
    window.lock_browser=function (){
	if(dlg==null){
	    var div = document.createElement('div');
	    div.innerHTML='正在处理当前操作，请等待……';
	    div.style.fontSize='13px';
	    div.style.lineHeight='54px';
	    div.style.textAlign='center';
	    dlg=new Dialog(div,'提示',320,100);
	}
    };
    window.unlock_browser=function (){
	if(dlg!=null){
	    dlg.close();
	}
    };
    
    
})();