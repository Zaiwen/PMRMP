/**
 * @requires {WSDLOperation}
 * @param {WSDLOperation}
 *            operation
 */
function WSDLOperationView(operation) {
	this.operation = operation;
}
/**
 * @returns {HTMLElement}
 */
WSDLOperationView.prototype.getHtml = function() {
	if (this.operation != null) {
		var container = document.createElement("div");
		var _in = document.createElement("h4");
		var _out = document.createElement("h4");
		var in_table = document.createElement('table');
		var out_table = document.createElement('table');
		_in.innerHTML = "Inputs";
		_out.innerHTML = "Outputs";
		in_table.appendChild(this.createHeadView());
		for ( var i = 0; i < this.operation.getInputs().length; i++) {
			var param = this.operation.getInput(i);
			var tr = this.createInputView(param);
			in_table.appendChild(tr);
		}
		out_table.appendChild(this.createHeadView());
		for ( var i = 0; i < this.operation.getOutputs().length; i++) {
			var param = this.operation.getOutput(i);
			var tr = this.createOutputView(param);
			out_table.appendChild(tr);
		}
		container.className = "WSDLOperationView";
		container.appendChild(_in);
		container.appendChild(in_table);
		container.appendChild(_out);
		container.appendChild(out_table);
		return container;
	} else {
		return document.createTextNode('');
	}
};
/**
 * @private
 * @returns {HTMLElement}
 */
WSDLOperationView.prototype.createHeadView = function() {
	var tr = document.createElement('tr');
	var name = document.createElement('th');
	var type = document.createElement('th');
	name.innerHTML = 'Name';
	type.innerHTML = 'Type';
	tr.appendChild(name);
	tr.appendChild(type);
	return tr;
};
/**
 * @private
 * @param {WSDLParameter}
 *            param
 * @returns {HTMLElement}
 */
WSDLOperationView.prototype.createInputView = function(param) {
	var tr = document.createElement('tr');
	var name = document.createElement('td');
	var type = document.createElement('td');
	name.className = "Name";
	type.className = "Type";
	name.appendChild(document.createTextNode(param.getName()));
	type.appendChild(new QNameView(param.getType()).getHtml());
	tr.appendChild(name);
	tr.appendChild(type);
	return tr;
};
/**
 * @private
 * @param {WSDLParameter}
 *            param
 * @returns {HTMLElement}
 */
WSDLOperationView.prototype.createOutputView = function(param) {
	var tr = document.createElement('tr');
	var name = document.createElement('td');
	var type = document.createElement('td');
	name.className = "Name";
	type.className = "Type";
	name.appendChild(document.createTextNode(param.getName()));
	type.appendChild(new QNameView(param.getType()).getHtml());
	tr.appendChild(name);
	tr.appendChild(type);
	return tr;
};