mxGraph.prototype.undoManager = null;

mxGraph.prototype.installUndoManager = function ()
{		
	var graph=this;
	
	var listener = mxUtils.bind(this,function (sender,evt)
	{
		var edit = evt.getProperty('edit');
		graph.undoManager.undoableEditHappened(edit);
	});
	
	var undoHandler = function (sender,evt)
	{
		var changes=evt.getProperty('edit').changes;
		graph.setSelectionCells(graph.getSelectionCellsForChanges(changes));
	}; 
	
	this.undoManager = new mxUndoManager();
	
	this.getModel().addListener(mxEvent.UNDO,listener);
	this.getView().addListener(mxEvent.UNDO,listener);
	
	this.undoManager.addListener(mxEvent.UNDO,undoHandler);
	this.undoManager.addListener(mxEvent.REDO,undoHandler);	
};

mxGraph.prototype.getSelectionVertexes = function ()
{
	var selectionCells = this.getSelectionCells();
	
	var selectionVertexes = new Array();
	
	for(var i = 0;i < selectionCells.length;i++)
	{
		var cell = selectionCells[i];
		
		if(cell.vertex)
		{
			selectionVertexes.push(cell);
		}
	}
	return selectionVertexes;
};

mxGraph.prototype.getSelectionEdges = function ()
{
	var selectionCells = this.getSelectionCells();
	
	var selectionEdges = new Array();
	
	for(var i =0;i < selectionCells.length;i++)
	{
		var cell = selectionCells[i];
		
		if(cell.edge)
		{
			selectionEdges.push(cell);
		}
	}
	return selectionEdges;
};

mxUtils.getElementsByClassName = function (targetClass,ancestorNode)
{
	if(typeof ancestorNode == 'undefined')
	{
		ancestorNode = document;
	}
	
	if(!document.getElementsByClassName)
	{
		var descendantElements = null;
		
		var correspondingElements = new Array();
		
		var i,j;
	
		descendantElements = ancestorNode.getElementsByTagName('*');
		
		for(i = 0;i < descendantElements.length;i++)
		{
			var element = descendantElements[i];
			
			var classNames = element.className.split(' ');
			
			for(j = 0;j < classNames.length;j++)
			{
				if(classNames[j] == targetClass)
				{
					correspondingElements.push(element);
					break;
				}
			}
			
		}
		return correspondingElements;
		
	}
	else
	{
		return ancestorNode.getElementsByClassName(targetClass);
	}
};