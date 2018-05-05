function RoleShape(){
	
}

RoleShape.prototype=new mxCylinder();

RoleShape.prototype.constructor=RoleShape;

RoleShape.prototype.redrawPath=function (path,x,y,w,h,isForeground){
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
	if(isForeground){
		path.moveTo(w/12,h/2-Math.sqrt(11)*h/12);
		path.lineTo(w/12,h/2+Math.sqrt(11)*h/12);	
		path.end();
	}else{
		path.ellipse(0,0,w,h);
		path.end();
	}
};

mxCellRenderer.prototype.defaultShapes['role']=RoleShape;