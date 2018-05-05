/**
 * @param {HTMLElement}
 *                content
 */
function Dialog(title, width, height) {
	this.title = title;
	this.width = width;
	this.height = height;
    this.init();
}
/**
 * @type {HTMLElement}
 */
Dialog.prototype.backgroud = null;
/**
 * @type {HTMLElement}
 */
Dialog.prototype.container = null;
/**
 * @type {HTMLElement}
 */
Dialog.prototype.head = null;
/**
 * @type {HTMLElement}
 */
Dialog.prototype.body = null;
/**
 * @type {HTMLElement}
 */
Dialog.prototype.content = null;
/**
 * @type {HTMLElement}
 */
Dialog.prototype.foot = null;
/**
 * @type {String}
 */
Dialog.prototype.title = null;
/**
 * @type {Number}
 */
Dialog.prototype.width = 0;
/**
 * @type {Number}
 */
Dialog.prototype.height = 0;
/**
 * @private
 */
Dialog.prototype.init = function() {
    this.background = document.createElement('div');
    this.background.style.background = '#000000';
    this.background.style.bottom = '0px';
    this.background.style.filter = 'alpha(opacity=50)';
    this.background.style.left = '0px';
    this.background.style.opacity = '0.5';
    this.background.style.position = 'absolute';
    this.background.style.right = '0px';
    this.background.style.top = '0px';
    this.background.style.zIndex = 10000 + 10 * Dialog.number;
    

    this.buttons = new Array();

    this.container = document.createElement('div');
    this.container.style.background = '#FFFFFF';
    this.container.style.border = '2px solid #0099FF';
    this.container.style.height = this.height + 'px';
    this.container.style.position = 'absolute';
    this.container.style.left = (document.documentElement.clientWidth - this.width)/ 2 + 'px';
    this.container.style.top = (document.documentElement.clientHeight - this.height)/ 2 + 'px';
    this.container.style.width = this.width + 'px';
    this.container.style.zIndex = 10000 + 10 * Dialog.number + 1;

    this.head = document.createElement('div');
    this.head.style.background = '#0099FF';
    this.head.style.borderBottom = '2px solid #0099FF';
    this.head.style.color = '#FFFFFF';
    this.head.style.cursor = 'default';
    this.head.style.fontSize = '17px';
    this.head.style.fontWeight = 'bold';
    this.head.style.height = '40px';
    this.head.style.left = '0px';
    this.head.style.lineHeight = '40px';
    this.head.style.paddingLeft = '12px';
    this.head.style.position = 'absolute';
    this.head.style.right = '0px';
    this.head.style.top = '0px';
    this.head.innerHTML = this.title;
    this.container.appendChild(this.head);

    this.body = document.createElement('div');
    this.body.style.background = '#FFFFFF';
    this.body.style.bottom = '0px';
    this.body.style.left = '0px';
    this.body.style.position = 'absolute';
    this.body.style.right = '0px';
    this.body.style.top = '42px';
    //this.body.appendChild(this.content);
    this.container.appendChild(this.body);

    this.foot = document.createElement('div');
    this.foot.style.background = '#FFFFFF';
    this.foot.style.bottom = '0px';
    this.foot.style.height = '0px';
    this.foot.style.left = '0px';
    this.foot.style.position = 'absolute';
    this.foot.style.right = '0px';
    this.container.appendChild(this.foot);

    
};
Dialog.prototype.open=function (){
    document.body.appendChild(this.background);
    document.body.appendChild(this.container);
    Dialog.number++;
};
/**
 * 
 */
Dialog.prototype.close = function() {
    document.body.removeChild(this.background);
    document.body.removeChild(this.container);
    Dialog.number--;
};
/**
 * @param {String}
 *                title
 */
Dialog.prototype.setTitle = function(title) {
    this.head.innerHTML = title;
};
/**
 * @static
 * @private
 * @type {Number}
 */
Dialog.number = 0;