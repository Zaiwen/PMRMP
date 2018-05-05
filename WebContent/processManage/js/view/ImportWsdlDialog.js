/**
 * @requires jQuery
 * @requires {WSDLOperation}
 * @requires {WSDLService}
 */
function ImportWsdlDialog(handler) {
    this.handler = handler;
    this.init();
}
/**
 * @type {HTMLElement}
 */
ImportWsdlDialog.prototype.container = null;
/**
 * @type {HTMLElement}
 */
ImportWsdlDialog.prototype.urlBox = null;
/**
 * @type {WSDLService}
 */
ImportWsdlDialog.prototype.service = null;
/**
 * @type {WSDLOperation}
 */
ImportWsdlDialog.prototype.selectedOperation = null;
/**
 * @private
 * @returns {String}
 */
ImportWsdlDialog.prototype.getUrl = function() {
    return $(this.urlBox).val();
};
/**
 * @private
 */
ImportWsdlDialog.prototype.init = function() {
    this.url = document.createElement('div');
    this.url.className = 'url';
    this.url.appendChild(function() {
	var span = document.createElement('span');
	span.innerHTML = 'WSDL URL:';
	return span;
    }());
    this.url.appendChild(mxUtils.bind(this, function() {
	this.urlBox = document.createElement('input');
	this.urlBox.setAttribute('type', 'text');
	return this.urlBox;
    })());
    this.url.appendChild(mxUtils.bind(this, function() {
	var button = document.createElement('button');
	button.innerHTML = '解析';
	button.onclick = mxUtils.bind(this, function() {
	    this.parseWsdl();
	});
	return button;
    })());

    this.operation = document.createElement('div');
    this.operation.className = 'operation';

    this.detail = document.createElement('div');
    this.detail.className = 'detail';

    this.container = document.createElement('div');
    this.container.setAttribute('id', 'import-wsdl-dialog');
    this.container.appendChild(this.url);
    this.container.appendChild(this.operation);
    this.container.appendChild(this.detail);

    this.dialog = new Dialog(this.container, '导入WSDL', 720, 420);
    this.dialog.addButton('关闭', mxUtils.bind(this, function() {
	this.dialog.close();
	WSDLPage.refresh();
    }));
    this.dialog.addButton('导入', mxUtils.bind(this, function() {
	this.importOperation();
    }));
};
/**
 * @private
 */
ImportWsdlDialog.prototype.parseWsdl = function() {
    try {
	lock_browser();
	this.service = WSDLService.createService(this.getUrl());
	var operations = this.service.getOperations();
	this.operation.innerHTML = '';
	this.detail.innerHTML = '';
	for ( var i = 0; i < operations.length; i++) {
	    this.addItem(operations[i].getName());
	}
	unlock_browser();
    } catch (e) {
	alert('解析WSDL文件时出错！');
    }

};
/**
 * @private
 */
ImportWsdlDialog.prototype.importOperation = function() {
    if (this.service == null) {
	alert('请先进行解析');
    } else if (this.selectedOperation == null) {
	alert('请选择一个操作！');
    } else {
	var op = this.service.getOperation(this.selectedOperation);
	this.dialog.close();
	this.handler(op);
    }
};
/**
 * @private
 * @param {String}
 *                name
 */
ImportWsdlDialog.prototype.addItem = function(name) {
    var p = document.createElement('p');
    p.className = 'item';
    p.innerHTML = name;
    p.onclick = mxUtils.bind(this, function() {
	// 用原生的API实现起来太麻烦，暂时用jQUery的API吧
	$(this.operation).children().css({
	    'background-color' : 'transparent',
	    'color' : '#000000'
	});
	p.style.background = '#0099FF';
	p.style.color = '#FFFFFF';
	var operation = this.service.getOperation($(p).text());
	$(this.detail).html(new WSDLOperationViewer(operation).getHTML());
	this.selectedOperation = name;
    });
    this.operation.appendChild(p);
};