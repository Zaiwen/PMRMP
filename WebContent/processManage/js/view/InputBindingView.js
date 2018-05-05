function InputBindingView(operation) {
    this.operation = operation;
}

InputBindingView.prototype.getHtml = function() {
    if (this.operation != null) {
	var table = document.createElement('table');
	var tr = document.createElement("tr");
	var from = document.createElement('th');
	from.innerHTML = "From";
	tr.appendChild(from);
	var to = document.createElement("To");
	to.innerHTML = "To";
	tr.appendChild(to);
	table.appendChild(tr);
	for(var i=0;i<operation.getInputs().length;i++){
	    var input=operation.getInput(i);
	    var tr=document.createElement('tr');
	    var from=document.createElement('td');
	    from.appendChild(new SelectOutputView(input).getHtml());
	    var to=document.createElement("td");
	    to.innerHTML=input.getName();
	    tr.appendChild(from);
	    tr.appendChild(to);
	    table.appendChild(tr);
	}
	return table;
    }
};