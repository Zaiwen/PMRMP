
function TreeView(container){
    this.container=container;
}
/**
 * @type {HTMLElement}
 */
TreeView.prototype.container=null;

TreeView.prototype.createTreeNode=function (text){
    return new TreeNode(this.container,text);
};

TreeView.prototype.setRootNode=function(node){
    this.rootNode=node;
    node.setParent(null)
};