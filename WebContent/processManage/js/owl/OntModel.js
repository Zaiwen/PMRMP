/**
 * @param {String}
 *                url
 */
function OntModel() {
    this.classes = new Array();
    this.read();
}
/**
 * @type {OntCLass[]}
 */
OntModel.prototype.classes = null;
/**
 * @param {String}
 *                uri
 */
OntModel.prototype.createClass = function(uri) {
    for ( var i = 0; i < this.classes.length; i++) {
	if (uri == this.classes[i].getURI()) {
	    return this.classes[i];
	}
    }
    var cls = new OntClass(uri);
    this.classes.push(cls);
    return cls;
};
/**
 * @returns {OntClass[]}
 */
OntModel.prototype.getAllClasses = function() {
    return this.classes;
};
/**
 * @private
 */
OntModel.prototype.read = function() {
    var obj = this;
    var success = mxUtils.bind(this, function(str) {
	$($.trim(str)).find('item').each(function() {
	    var cls = obj.createClass($(this).text());
	    var parent = $(this).attr('parent');
	    if (parent != null && parent != '' && parent != 'null') {
		cls.setSuperClass(obj.createClass(parent));
	    }
	});
    });
    var error = mxUtils.bind(this, function() {
	throw new Error('Error when get OWL classes :');
    });
    $.ajax({
	async : false,
	url : "GetOWLClasses.jsp",
	mimeType : 'text/plain',
	success : success,
	error : error
    });
};