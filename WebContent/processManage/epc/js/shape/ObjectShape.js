function ObjectShape() {

}

ObjectShape.prototype = new mxCylinder();

ObjectShape.prototype.constructor = ObjectShape;

ObjectShape.prototype.redrawPath = function(path, x, y, w, h, isForeground) {
	var _x, _y, _w, _h;
	if (w > 2 * h) {
		_x = w / 2 - h;
		_y = 0;
		_w = 2 * h;
		_h = h;
	} else {
		_x = 0;
		_y = h / 2 - w / 4;
		_w = w;
		_h = w / 2;
	}
	if (!isForeground) {
		path.moveTo(_x, _y);
		path.lineTo(_x + _w, _y);
		path.lineTo(_x + _w, _y + _h);
		path.lineTo(_x, _y + _h);
		path.close();
	}
};

mxCellRenderer.prototype.defaultShapes['object'] = ObjectShape;