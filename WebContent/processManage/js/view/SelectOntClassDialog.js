/**
 * @param {Function}
 *                handler 当选择了一个OWL Class 之后执行的操作，该函数接受OWL Class的URI
 */
function SelectOntClassDialog(handler) {
    this.handler = handler;
    this.init();
} 
/**
 * @type {HTMLElement}
 */
SelectOntClassDialog.prototype.urlBox = null;
/**
 * @private
 */
SelectOntClassDialog.prototype.init = function() {
    var content = document.createElement('div');
    content.setAttribute('id', 'SelectOntClassDialog');
    content.appendChild(this.createUrlView());
    content.appendChild(this.createTreeView());
    this.dialog = new Dialog(content, '选择语义标识', 720, 480);
    this.dialog.addButton('取消', mxUtils.bind(this, function() {
	this.dialog.close();
    }));
    this.dialog.addButton('确定', mxUtils.bind(this, function() {
	var nodes = $(this.tree).jstree('get_selected');
	if (nodes.length == 0) {
	    alert('请先进行选择！');
	} else { 
	    var text = nodes.eq(0).children('a').text();
	    if (this.handler != null && typeof (this.handler) == 'function') {
		this.handler(text);
	    }
	    this.dialog.close();
	}
    }));

};
/**
 * @private
 * @returns {HTMLElement}
 */
SelectOntClassDialog.prototype.createUrlView = function() {
    var url = document.createElement('div');
    url.className = 'url';
    var label = document.createElement('span');
    label.innerHTML = 'URL:';
    url.appendChild(label);
    this.urlBox = document.createElement('input');
    this.urlBox.setAttribute('type', 'text');
    url.appendChild(this.urlBox);
    var button = document.createElement('button');
    button.innerHTML = '解析';
    button.onclick = mxUtils.bind(this, function() {
	this.parseModel();
    });
    url.appendChild(button);
    return url;
};
/**
 * @private
 * @returns {HTMLElement}
 */
SelectOntClassDialog.prototype.createTreeView = function() {
    this.tree = document.createElement('div');
    this.tree.className = 'Tree';
    return this.tree;
};
/**
 * 
 */
SelectOntClassDialog.prototype.parseModel = function() {
    try {
	var model = new OntModel(this.urlBox.value);
	$(this.tree).html(new OntModelTreeView(model).getHtml());
	$(this.tree).jstree({
	    "themes" : {
		"theme" : "classic",
		"dots" : true,
		"icons" : false
	    },
	    "ui" : {
		"select_limit" : 1,
		"select_prev_on_delete" : true
	    },
	    "core" : {
		"animation" : 0,
	    },
	    "plugins" : [ "themes", "html_data", "crrm", "ui" ]
	});
    } catch (e) {
	alert('解析时出错！');
    }
};
