/**
 * @class FunctionShape
 * @author 艾培东(aipeidong@vip.qq.com)
 * @constructor FunctionShape
 */
function FunctionShape() {
	
}
/**
 * @extends <mxCylinder>
 */
FunctionShape.prototype = new mxCylinder();
FunctionShape.prototype.constructor = FunctionShape;
/**
 * @override
 */
FunctionShape.prototype.redrawPath = function(path, x, y, w, h, isForeground) {
	var r = Math.min(w, h);
	if (!isForeground) {
		path.moveTo(0, r / 6);
		path.quadTo(r / 32, r / 32, r / 6, 0);
		path.lineTo(w - r / 6, 0);
		path.quadTo(w - r / 32, r / 32, w, r / 6);
		path.lineTo(w, h - r / 6);
		path.quadTo(w - r / 32, h - r / 32, w - r / 6, h);
		path.lineTo(r / 6, h);
		path.quadTo(r / 32, h - r / 32, 0, h - r / 6);
		path.close();
	}
};

mxCellRenderer.prototype.defaultShapes['function'] = FunctionShape;