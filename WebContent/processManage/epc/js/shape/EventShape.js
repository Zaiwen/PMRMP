/**
 * @class EventShape
 * @author 艾培东(aipeidong@vip.qq.com)
 * @constructor EventShape
 */
function EventShape() {

}
/**
 * @extends <mxCylinder>
 */
EventShape.prototype = new mxCylinder();
EventShape.prototype.constructor = EventShape;
/**
 * @override
 */
EventShape.prototype.redrawPath = function(path, x, y, w, h, isForeground) {
	var r = Math.min(w, h);
	if (!isForeground) {
		path.moveTo(0, h / 2);
		path.lineTo(r / 5, 0);
		path.lineTo(w - r / 5, 0);
		path.lineTo(w, h / 2);
		path.lineTo(w - r / 5, h);
		path.lineTo(r / 5, h);
		path.close();
	}
};

mxCellRenderer.prototype.defaultShapes['event'] = EventShape;