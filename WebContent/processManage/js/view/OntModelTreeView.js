/**
 * @param {OntModel}
 *                model
 */
function OntModelTreeView(model) {
    this.model = model;
}
/**
 * @type {OntModel}
 */
OntModelTreeView.prototype.model = null;
/**
 * @returns {HTMLElement}
 */
OntModelTreeView.prototype.getHtml = function() {
    var jstree = document.createElement("ul");
    var arr1 = new Array();
    var arr2 = new Array();
    var clss = this.model.getAllClasses();
    // 先是根节点（不止一个，或者说这是第二层次）
    for ( var i = 0; i < clss.length; i++) {
	var cls = clss[i];
	if (cls.getSuperClass() == null) {
	    var item = $('<li><a href="#"></a><ul></ul></li>');
	    item.children('a').text(cls.getURI());
	    arr1.push(item);
	    jstree.appendChild(item[0]);
	}
    }
    // 之后每两层之间的处理方法都是一样的
    while (arr1.length > 0) {
	arr2 = new Array();
	for ( var i = 0; i < arr1.length; i++) {
	    for ( var j = 0; j < clss.length; j++) {
		var cls = clss[j];
		if (cls.getSuperClass() != null) {
		    var sup = cls.getSuperClass();
		    var str = arr1[i].children('a').text();
		    if (sup.getURI() == str) {
			var item = $('<li><a href="#"></a><ul></ul></li>');
			item.children('a').text(cls.getURI());
			arr2.push(item);
			arr1[i].children('ul').append(item);
		    }
		}
	    }
	}
	arr1 = arr2;
    }
    return jstree;
};