/**
 * @class AndShape
 * @author 艾培东(aipeidong@vip.qq.com)
 * @constructor AndShape
 */
function AndShape() {

}
/**
 * @extends <mxCylinder>
 */
AndShape.prototype = new mxCylinder();
AndShape.prototype.constructor = AndShape;
/**
 * @override
 */
AndShape.prototype.redrawPath = function(path, x, y, w, h, isForeground) {
	if (isForeground) {
		path.moveTo(w / 4, h / 4);
		path.lineTo(w / 2, h *3/ 4);
		path.lineTo(w * 3 / 4, h / 4);
		path.end();
	} else {
		if(mxClient.IS_VML)
		{
			path.ellipse(0, 0, w * 2, h * 2);
		}
		else
		{
			path.ellipse(0, 0, w, h);
		}
	}
};

mxCellRenderer.prototype.defaultShapes['or'] = AndShape;