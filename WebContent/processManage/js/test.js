$(function() {
    var arr = [ 0, 0, 0, 0, 0, 0, 0, 0, 0 ];
    var graph = [ 0, 0, 0, 0, 0 ];
    var end = true;
    var $html = $('<div style="display: none; position: absolute;'
	    + ' height: 50px; bottom: 0px; left: 0px;">'
	    + '<img src="img/meng.gif" style="display: inline;">'
	    + '<p style="display: inline; vertical-align: bottom;'
	    + 'padding: 0px; margin: 0px; line-height: 22px; '
	    + 'font-size: 13px; color: rgb(153, 0, 255);">偶是来卖萌滴~~~</p></div>');
    $html.appendTo($("body"));
    $(document).keydown(function(e) {
	// 一些尚在测试中的功能
	for ( var i = 1; i < arr.length; i++) {
	    arr[i - 1] = arr[i];
	}
	arr[arr.length - 1] = e.keyCode;
	if (arr.join("-") == "65-73-80-69-73-68-79-78-71") {
	    if (end) {
		end = false;
		$html.css("left", "0px").show();
		$html.animate({
		    left : $("body").width() + "px"
		}, $("body").width() * 12, function() {
		    $html.hide();
		    end = true;
		});
	    }
	} else if (arr.join("-") == "71-82-65-80-72-66-69-84-45") {
	    alert("啦啦啦~");
	}

    });

    window
});