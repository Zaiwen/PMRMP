/**
 * @param {QName}
 *                qname
 */
function QNameView(qname) {
    this.qname = qname;
}
/**
 * @type {QName}
 */
QNameView.prototype.qname = null;
/**
 * @returns {HTMLElement}
 */
QNameView.prototype.getHtml = function() {
    if (this.qname != null & this.qname instanceof QName) {
	var p = document.createElement('p');
	p.className = 'QNameView';
	var localPart = document.createElement('span');
	localPart.className = 'LocalPart';
	localPart.innerHTML = this.qname.localPart;
	if (this.qname.namespcaeURI == '') {
	    p.appendChild(localPart);
	} else {
	    var namespace = document.createElement('span');
	    p.appendChild(document.createTextNode('{'));
	    namespace.innerHTML = this.qname.namespaceURI;
	    namespace.className = 'NamespaceURI';
	    p.appendChild(namespace);
	    p.appendChild(document.createTextNode('}'));
	    p.appendChild(localPart);
	}
	return p;
    }
};