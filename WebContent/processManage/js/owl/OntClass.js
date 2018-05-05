/**
 * @param {String}
 *                uri
 */
function OntClass(uri) {
    this.uri = uri;
}
/**
 * @type {String}
 */
OntClass.prototype.uri = null;
/**
 * @type {OntClass}
 */
OntClass.prototype.superClass = null;
/**
 * @returns {OntClass}
 */
OntClass.prototype.getSuperClass = function() {
    return this.superClass;
};
/**
 * @returns {String}
 */
OntClass.prototype.getURI = function() {
    return this.uri;
};
/**
 * @param {OntClass}
 *                superClass
 */
OntClass.prototype.setSuperClass = function(superClass) {
    this.superClass = superClass;
};
/**
 */
OntClass.all=new Array();