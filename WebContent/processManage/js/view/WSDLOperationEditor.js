/**
 * @requires {WSDLOperation}
 * @param {WSDLOperation}
 *                operation
 */
function WSDLOperationEditor(operation) {
    this.operation = operation;
}
/**
 * @type {HTMLElement}
 */
WSDLOperationEditor.prototype.container = null;
/**
 * @returns {HTMLElement}
 */
WSDLOperationEditor.prototype.getHtml = function() {
    if (this.operation != null && this.operation instanceof WSDLOperation) {
	this.container = document.createElement('div');
	this.container.className = 'WSDLOperationEditor';
	this.container.appendChild(this.createNameView());
	this.container.appendChild(this.createInputView());
	this.container.appendChild(this.createOutputView());
	return this.container;
    } else {
	return document.createTextNode('');
    }
};
/**
 * @private
 */
WSDLOperationEditor.prototype.createNameView = function() {
    var label = document.createElement('span');
    var name = document.createElement('input');
    var change = document.createElement('button');
    label.innerHTML = '名称:';
    name.setAttribute('disabled', 'disabled');
    name.setAttribute('value', this.operation.getDefName());
    change.innerHTML = '更改';
    change.onclick = function() {

    };
    var container = document.createElement('div');
    container.className = 'NameEditor';
    container.appendChild(label);
    container.appendChild(name);
    container.appendChild(change);
    return container;
};
/**
 * @private
 */
WSDLOperationEditor.prototype.createInputView = function() {
    var h3 = document.createElement('h3');
    h3.innerHTML = 'Input';
    var table = document.createElement('table');
    var tr = document.createElement('tr');
    var name = document.createElement('th');
    var type = document.createElement('th');
    var model = document.createElement('th');
    table.style.borderCollapse = 'collapse';
    name.style.border = '1px solid #999999';
    name.style.padding = '4px 12px';
    name.style.textAlign = 'left';
    name.appendChild(document.createTextNode('Name'));
    type.style.border = '1px solid #999999';
    type.style.padding = '4px 12px';
    type.style.textAlign = 'left';
    type.appendChild(document.createTextNode('Type'));
    model.style.border = '1px solid #999999';
    model.style.padding = '4px 12px';
    model.style.textAlign = 'left';
    model.appendChild(document.createTextNode('Model'));
    tr.appendChild(name);
    tr.appendChild(type);
    tr.appendChild(model);
    table.appendChild(tr);
    for ( var i = 0; i < this.operation.getInputs().length; i++) {
	var param = this.operation.getInput(i);
	tr = document.createElement('tr');
	name = document.createElement('td');
	name.style.border = '1px solid #999999';
	name.style.color = '#009900';
	name.style.padding = '4px 12px';
	name.style.textAlign = 'left';
	name.appendChild(document.createTextNode(param.getName()));
	type = document.createElement('td');
	type.style.border = '1px solid #999999';
	type.style.padding = '4px 12px';
	type.style.textAlign = 'left';
	type.appendChild(new QNameView(param.getType()).getHtml());
	var model = document.createElement('td');
	model.innerHTML = param.model == null ? '' : param.model;
	model.style.border = '1px solid #999999';
	model.style.padding = '4px 12px';
	model.style.textAlign = 'left';
	model.onclick = function() {
	    new SelectOntClassDialog(mxUtils.bind(this, function(text) {
		if (text != null && text != "") {
		    param.model = text;
		    this.innerHTML = text;
		}
	    }));
	};
	tr.appendChild(name);
	tr.appendChild(type);
	tr.appendChild(model);
	table.appendChild(tr);
    }
    var container = document.createElement('div');
    container.appendChild(h3);
    container.appendChild(table);
    return container;
};
/**
 * @private
 */
WSDLOperationEditor.prototype.createOutputView = function() {
    var h3 = document.createElement('h3');
    h3.innerHTML = 'Output';
    var table = document.createElement('table');
    var tr = document.createElement('tr');
    var name = document.createElement('th');
    var type = document.createElement('th');
    var model = document.createElement('th');
    table.style.borderCollapse = 'collapse';
    name.style.border = '1px solid #999999';
    name.style.padding = '4px 12px';
    name.style.textAlign = 'left';
    name.appendChild(document.createTextNode('Name'));
    type.style.border = '1px solid #999999';
    type.style.padding = '4px 12px';
    type.style.textAlign = 'left';
    type.appendChild(document.createTextNode('Type'));
    model.style.border = '1px solid #999999';
    model.style.padding = '4px 12px';
    model.style.textAlign = 'left';
    model.appendChild(document.createTextNode('Model'));
    tr.appendChild(name);
    tr.appendChild(type);
    tr.appendChild(model);
    table.appendChild(tr);
    for ( var i = 0; i < this.operation.getOutputs().length; i++) {
	var param = this.operation.getOutput(i);
	tr = document.createElement('tr');
	name = document.createElement('td');
	name.style.border = '1px solid #999999';
	name.style.color = '#009900';
	name.style.padding = '4px 12px';
	name.style.textAlign = 'left';
	name.appendChild(document.createTextNode(param.getName()));
	type = document.createElement('td');
	type.style.border = '1px solid #999999';
	type.style.padding = '4px 12px';
	type.style.textAlign = 'left';
	type.appendChild(new QNameView(param.getType()).getHtml());
	var model = document.createElement('td');
	model.innerHTML = param.model == null ? '' : param.model;
	model.style.border = '1px solid #999999';
	model.style.padding = '4px 12px';
	model.style.textAlign = 'left';
	model.onclick = function() {
	    new SelectOntClassDialog(mxUtils.bind(this, function(text) {
		if (text != null && text != "") {
		    param.model = text;
		    this.innerHTML = text;
		}
	    }));
	};
	tr.appendChild(name);
	tr.appendChild(type);
	tr.appendChild(model);
	table.appendChild(tr);
    }
    var container = document.createElement('div');
    container.appendChild(h3);
    container.appendChild(table);
    return container;
};