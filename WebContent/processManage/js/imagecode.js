function change() {
		var img = document.getElementById("picture");
		img.src = "imgcode?t=" + (new Date().getTime());
	} 