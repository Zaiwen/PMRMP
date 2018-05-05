function ChangeStatus(o) {
	if (o.parentNode) {
		if (o.parentNode.className == "Opened") {
			o.parentNode.className = "Closed";
		} else {
			o.parentNode.className = "Opened";
		}
	}
}

function getResult(uri, type) {
	var url = "/BusinessProcessExtensionProject/Detail_Ontology?uri=" + uri + "&type=" + type + "&fresh=" + Math.random();
	if (window.XMLHttpRequest) {
		req = new XMLHttpRequest();
	} else {
		if (window.ActiveXObject) {
			req = new ActiveXObject("Microsoft.XMLHTTP");
		}
	}
	if (req) {
		req.open("POST", url, true);
		req.onreadystatechange = complete;	
		req.send();
	}
}

function complete() {
	if (req.readyState == 4) {
		if (req.status == 200) {
			showDiv(req.responseText);
		}
	}
}

function showDiv(divcontent) {
	YAHOO.example.container.panel1.setBody(divcontent);
	YAHOO.example.container.panel1.hide();
	YAHOO.example.container.panel1.show();
}

