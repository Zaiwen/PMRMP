function OWLModel() {

}
/**
 * @type {Map}
 */
OWLModel.ontology = {};
/**
 * @type {Process}
 */
OWLModel.compositeProcess = null;

/**
 * @type{Perform}
 */
OWLModel.theParentPerform = null;
/**
 * @param {String}
 *            cls 类名
 * @param {String}
 *            [id]
 * @returns {Object}
 */
OWLModel.create = function(cls, id) {
	if (window[cls]) {
		if (id == null) {
			id = OWLModel.getAvailableId(cls);
		}
		var obj = new window[cls](id);
		OWLModel.ontology[id] = obj;
		return obj;
	} else {
		return null;
	}
};
/**
 * 这里创建的是原子流程
 * 
 * @param {WSDLOperation}
 *            operation
 * @param {String}
 *            [id]
 * @returns {Process}
 */
OWLModel.createProcess = function(operation, id) {
	if (id == null) {
		id = OWLModel.getAvailableId(operation.getName());
	}
	var process = new AtomicProcess(id, operation);
	for ( var i = 0; i < operation.getInputs().length; i += 1) {
		var param = operation.getInput(i);
		var input = OWLModel.createInput(param.getName());
		var map = OWLModel.createInputMessageMap();
		input.setType(param.getType() + "");
		map.setGroundingParameter(param);
		map.setOwlsParameter(input);
		process.addInput(input);
		process.addInputMessageMap(map);
	}
	for ( var i = 0; i < operation.getOutputs().length; i += 1) {
		var param = operation.getOutput(i);
		var output = OWLModel.createOutput(param.getName());
		var map = OWLModel.createOutputMessageMap();
		output.setType(param.getType() + "");
		map.setGroundingParameter(param);
		map.setOwlsParameter(output);
		process.addOutput(output);
		process.addOutputMessageMap(map);
	}
	OWLModel.ontology[id] = process;
};
/**
 * @param {String}
 *            [name]
 * @param {String}
 *            [id]
 * @returns {Input}
 */
OWLModel.createInput = function(name, id) {
	if (id == null) {
		id = OWLModel.getAvailableId(name);
	}
	var input = new Input(id);
	OWLModel.ontology[id] = input;
	return input;
};
/**
 * @param {String}
 *            [name]
 * @param {String}
 *            [id]
 * @returns {Output}
 */
OWLModel.createOutput = function(name, id) {
	if (id == null) {
		id = OWLModel.getAvailableId(name);
	}
	var output = new Output(id);
	OWLModel.ontology[id] = output;
	return output;
};
/**
 * @param {String}
 *            [id]
 * @returns {InputBinding}
 */
OWLModel.createInputBinding = function(id) {
	if (id == null) {
		id = OWLModel.getAvailableId("InputBinding");
	}
	var binding = new InputBinding(id);
	OWLModel.ontology[id] = binding;
	return binding;
};
/**
 * @param {String}
 *            [id]
 * @returns {OutputBinding}
 */
OWLModel.createOutputBinding = function(id) {
	if (id == null) {
		id = OWLModel.getAvailableId("OutputBinding");
	}
	var binding = new OutputBinding(id);
	OWLModel.ontology[id] = binding;
	return binding;
};
/**
 * @param {String}
 *            [id]
 * @returns {InputMessageMap}
 */
OWLModel.createInputMessageMap = function(id) {
	if (id == null) {
		id = OWLModel.getAvailableId("InputMessageMap");
	}
	var map = new InputMessageMap(id);
	OWLModel.ontology[id] = map;
	return map;
};
/**
 * @param {String}
 *            [id]
 * @returns {OutputMessageMap}
 */
OWLModel.createOutputMessageMap = function(id) {
	if (id == null) {
		id = OWLModel.getAvailableId("OutputMessageMap");
	}
	var map = new OutputMessageMap(id);
	OWLModel.ontology[id] = map;
	return map;
};
/**
 * @param {String}
 *            [id]
 * @returns {IfThenElse}
 */
OWLModel.createIfThenElse = function(id) {
	if (id == null) {
		id = OWLModel.getAvailableId("Condition");
	}
	var ifThenElse = new IfThenElse(id);
	OWLModel.ontology[id] = ifThenElse;
	return ifThenElse;
};
/**
 * @param {String}
 *            [id]
 * @returns {Perform}
 */
OWLModel.createPerform = function(id) {
	if (id == null) {
		id = OWLModel.getAvailableId("Perform");
	}
	var perform = new Perform(id);
	OWLModel.ontology[id] = perform;
	return perform;
};
/**
 * @param {String}
 *            [id]
 * @returns {Produce}
 */
OWLModel.createProduce = function(id) {
	if (id == null) {
		id = OWLModel.getAvailableId("Produce");
	}
	var produce = new Produce(id);
	OWLModel.ontology[id] = produce;
	return produce;
};
/**
 * @param {String}
 *            [id]
 * @returns {Sequence}
 */
OWLModel.createSequence = function(id) {
	if (id == null) {
		id = OWLModel.getAvailableId("Sequence");
	}
	var sequence = new Sequence(id);
	OWLModel.ontology[id] = sequence;
	return sequence;
};
/**
 * @param {String}
 *            [id]
 * @returns {SplitJoin}
 */
OWLModel.createSplitJoin = function(id) {
	if (id == null) {
		id = OWLModel.getAvailableId("Split-Join");
	}
	var splitJoin = new SplitJoin(id);
	OWLModel.ontology[id] = splitJoin;
	return splitJoin;
};
OWLModel.get = function(id) {
	if (id == "TheParentPerform") {
		return OWLModel.getTheParentPerform();
	} else {
		return OWLModel.ontology[id];
	}
};
/**
 * 只是删除在ontology中的引用，在其它地方的引用可能没有改变
 * 
 * @param {String}
 *            id
 * @returns {Boolean}
 */
OWLModel.remove = function(id) {
	if (OWLModel.ontology[id] == null) {
		return false;
	} else {
		delete OWLModel.ontology[id];
		return true;
	}
};
/**
 * @return {Process}
 */
OWLModel.getCompositeProcess = function() {
	if (OWLModel.compositeProcess == null) {
		var id = OWLModel.getAvailableId("CompositeProcess");
		OWLModel.compositeProcess = new CompositeProcess(id);
		OWLModel.ontology[id] = OWLModel.compositeProcess;
	}
	return OWLModel.compositeProcess;
};
/**
 * @returns {Process[]}
 */
OWLModel.getAllAtomicProcess = function() {
	var arr = new Array();
	for ( var i in OWLModel.ontology) {
		var item = OWLModel.ontology[i];
		if (item instanceof AtomicProcess) {
			arr.push(item);
		}
	}
	return arr;
};
/**
 * @returns {Perform}
 */
OWLModel.getTheParentPerform = function() {
	if (OWLModel.theParentPerform == null) {
		var id = "TheParentPerform";
		OWLModel.theParentPerform = new Perform(id);
		OWLModel.theParentPerform.setProcess(OWLModel.getCompositeProcess());
		OWLModel.ontology[id] = OWLModel.theParentPerform;
	}
	return OWLModel.theParentPerform;
};
/**
 * @param {Input|Output}
 *            要绑定到的参数
 * @returns {InputBinding|OutputBinding}
 */
OWLModel.getBindingForParameter = function(param) {
	for ( var i = 0; i < OWLModel.getAllInputBinding().length; i += 1) {
		var binding = OWLModel.getAllInputBinding()[i];
		if (binding.getToParam() == param) {
			return binding;
		}
	}
	for ( var i = 0; i < OWLModel.getAllOutputBinding().length; i += 1) {
		var binding = OWLModel.getAllOutputBinding()[i];
		if (binding.getToParam() == param) {
			return binding;
		}
	}
	return null;
};
/**
 * @param {Input|Output}
 * @returns {InputMessageMap|OutputMessageMap}
 */
OWLModel.getMessageMapForParameter = function(param) {
	for ( var i = 0; i < OWLModel.getAllInputMessageMap().length; i += 1) {
		var map = OWLModel.getAllInputMessageMap()[i];
		if (map.getOwlsParameter() == param) {
			return map;
		}
	}
	for ( var i = 0; i < OWLModel.getAllOutputMessageMap().length; i += 1) {
		var map = OWLModel.getAllOutputMessageMap()[i];
		if (map.getOwlsParameter() == param) {
			return map;
		}
	}
	// 如果是组合流程的输入输出则返回为空
	return null;
};
/**
 * @returns {Perform[]}
 */
OWLModel.getAllPerform = function() {
	var arr = new Array();
	for ( var i in OWLModel.ontology) {
		var item = OWLModel.ontology[i];
		if (item instanceof Perform && item != OWLModel.getTheParentPerform()) {
			arr.push(item);
		}
	}
	return arr;
};
/**
 * @returns {IfThenElse[]}
 */
OWLModel.getAllIfThenElse = function() {
	var arr = new Array();
	for ( var i in OWLModel.ontology) {
		var item = OWLModel.ontology[i];
		if (item instanceof IfThenElse) {
			arr.push(item);
		}
	}
	return arr;
};
/**
 * @returns {Produce[]}
 */
OWLModel.getAllProduce = function() {
	var arr = new Array();
	for ( var i in OWLModel.ontology) {
		var item = OWLModel.ontology[i];
		if (item instanceof Produce) {
			arr.push(item);
		}
	}
	return arr;
};
/**
 * @returns {Sequence[]}
 */
OWLModel.getAllSequence = function() {
	var arr = new Array();
	for ( var i in OWLModel.ontology) {
		var item = OWLModel.ontology[i];
		if (item instanceof Sequence) {
			arr.push(item);
		}
	}
	return arr;
};
/**
 * @returns {SplitJoin[]}
 */
OWLModel.getAllSplitJoin = function() {
	var arr = new Array();
	for ( var i in OWLModel.ontology) {
		var item = OWLModel.ontology[i];
		if (item instanceof SplitJoin) {
			arr.push(item);
		}
	}
	return arr;
};

OWLModel.getAllInput = function() {
	var arr = [];
	for ( var i in OWLModel.ontology) {
		var obj = OWLModel.ontology[i];
		if (obj instanceof Input) {
			arr.push(obj);
		}
	}
	return arr;
};

OWLModel.getAllOutput = function() {
	var arr = [];
	for ( var i in OWLModel.ontology) {
		var obj = OWLModel.ontology[i];
		if (obj instanceof Output) {
			arr.push(obj);
		}
	}
	return arr;
};

OWLModel.getAllInputBinding = function() {
	var arr = [];
	for ( var i in OWLModel.ontology) {
		var obj = OWLModel.ontology[i];
		if (obj instanceof InputBinding) {
			arr.push(obj);
		}
	}
	return arr;
};
/**
 * @returns {Binding[]}
 */
OWLModel.getAllOutputBinding = function() {
	var arr = [];
	for ( var i in OWLModel.ontology) {
		var obj = OWLModel.ontology[i];
		if (obj instanceof OutputBinding) {
			arr.push(obj);
		}
	}
	return arr;
};
/**
 * @returns {InputMessageMap[]}
 */
OWLModel.getAllInputMessageMap = function() {
	var arr = [];
	for ( var i in OWLModel.ontology) {
		var obj = OWLModel.ontology[i];
		if (obj instanceof InputMessageMap) {
			arr.push(obj);
		}
	}
	return arr;
};
/**
 * @returns {OutputMessageMap[]}
 */
OWLModel.getAllOutputMessageMap = function() {
	var arr = [];
	for ( var i in OWLModel.ontology) {
		var obj = OWLModel.ontology[i];
		if (obj instanceof OutputMessageMap) {
			arr.push(obj);
		}
	}
	return arr;
};
/**
 * 一个名称是否可用
 * 
 * @param {String}
 *            id
 * @returns {Boolean}
 */
OWLModel.isIdAvailable = function(id) {
	var arr = ["任务","序列","条件结构", "Perform", "Produce", "AnyOrder", "Any_Order", "SplitJoin",
			"Split-Join", "Choice", "Sequence", "Condition", "Process",
			"AtomicProcess", "CompositeProcess", "Perform", "Binding",
			"InputBinding", "OutputBinding", "Parameter", "Input", "Output",
			"InputMessageMap", "OutputMessageMap" ];
	for ( var i = 0; i < arr.length; i += 1) {
		if (id == arr[i]) {
			return false;
		}
	}
	return OWLModel.ontology[id] == null;
};
/**
 * 确定一个唯一的名称
 * 
 * @param {String}id
 *            原本的名称
 * @returns {String} 可以使用的名称
 */
OWLModel.getAvailableId = function(id) {
	var n = 0;
	var _id = id;
	while (true) {
		if (OWLModel.isIdAvailable(_id)) {
			break;
		} else {
			n += 1;
			_id = id + "-" + n;
		}
	}
	return _id;
};
/**
 * @param {String}
 *            oldId
 * @param {String}
 *            newId
 */
OWLModel.changeId = function(oldId, newId) {
	var obj = OWLModel.get(oldId);
	if (obj == null) {
		return false;
	} else if (!OWLModel.isIdAvailable(newId)) {
		return false;
	} else {
		delete OWLModel.ontology[oldId];
		OWLModel.ontology[newId] = obj;
		return true;
	}
};
/**
 * @returns {String}
 */
OWLModel.toJson = function() {
	$("#editor>.statusbar").text("begin to save...");
	var ont = {};
	ont.anyOrder = [];
	ont.choice = [];
	ont.sequence = [];
	ont.splitJoin = [];
	ont.perform = [];
	ont.produce = [];
	ont.atomicProcess = [];
	ont.compositeProcess = OWLModel.getCompositeProcess().toJson();
	ont.input = [];
	ont.output = [];
	ont.inputBinding = [];
	ont.outputBinding = [];
	ont.inputMessageMap = [];
	ont.outputMessageMap = [];
	
	$("#editor>.statusbar").text("pass...");

/**	
	for ( var i = 0; i < OWLModel.getAllAnyOrder().length; i += 1) {
		
		var anyOrder = OWLModel.getAllAnyOrder()[i];
		ont.anyOrder.push(anyOrder.toJson());
	}
**/	
	
/**	
	for ( var i = 0; i < OWLModel.getAllChoice().length; i += 1) {
		var choice = OWLModel.getAllChoice()[i];
		ont.choice.push(choice.toJson());
	}
**/

	for ( var i = 0; i < OWLModel.getAllSequence().length; i += 1) {
		var sequence = OWLModel.getAllSequence()[i];
		ont.sequence.push(sequence.toJson());
	}
	
	
	
	for ( var i = 0; i < OWLModel.getAllSplitJoin().length; i += 1) {
		var splitJoin = OWLModel.getAllSplitJoin()[i];
		ont.splitJoin.push(splitJoin.toJson());
	}
	
	
	
	for ( var i = 0; i < OWLModel.getAllPerform().length; i += 1) {
		var perform = OWLModel.getAllPerform()[i];
		ont.perform.push(perform.toJson());
	}
	for ( var i = 0; i < OWLModel.getAllProduce().length; i += 1) {
		var produce = OWLModel.getAllProduce()[i];
		ont.produce.push(produce.toJson());
	}
	
	
	for ( var i = 0; i < OWLModel.getAllAtomicProcess().length; i += 1) {
		var process = OWLModel.getAllAtomicProcess()[i];
		ont.atomicProcess.push(process.toJson());
	}
	for ( var i = 0; i < OWLModel.getAllInput().length; i += 1) {
		var input = OWLModel.getAllInput()[i];
		ont.input.push(input.toJson());
	}
	for ( var i = 0; i < OWLModel.getAllOutput().length; i += 1) {
		var output = OWLModel.getAllOutput()[i];
		ont.output.push(output.toJson());
	}
	for ( var i = 0; i < OWLModel.getAllInputBinding().length; i += 1) {
		var inputBinding = OWLModel.getAllInputBinding()[i];
		ont.inputBinding.push(inputBinding.toJson());
	}
	for ( var i = 0; i < OWLModel.getAllOutputBinding().length; i += 1) {
		var outputBinding = OWLModel.getAllOutputBinding()[i];
		ont.outputBinding.push(outputBinding.toJson());
	}
	for ( var i = 0; i < OWLModel.getAllInputMessageMap().length; i += 1) {
		var map = OWLModel.getAllInputMessageMap()[i];
		ont.inputMessageMap.push(map.toJson());
	}
	for ( var i = 0; i < OWLModel.getAllOutputMessageMap().length; i += 1) {
		var map = OWLModel.getAllOutputMessageMap()[i];
		ont.outputMessageMap.push(map.toJson());
	}
	
	
	return JSON.stringify(ont);
		
};
/**
 * 将JSON数据还原
 * 
 * @param {Object}
 *            _json
 */
OWLModel.fromJson = function(_json) {
	
	
	for ( var i = 0; i < _json.sequence.length; i += 1) {
		OWLModel.create("Sequence", _json.sequence[i].ID);
	}
	for ( var i = 0; i < _json.splitJoin.length; i += 1) {
		OWLModel.create("SplitJoin", _json.splitJoin[i].ID);
	}
	for ( var i = 0; i < _json.perform.length; i += 1) {
		OWLModel.create("Perform", _json.perform[i].ID);
	}
	for ( var i = 0; i < _json.produce.length; i += 1) {
		OWLModel.create("Produce", _json.produce[i].ID);
	}
	for ( var i = 0; i < _json.atomicProcess.length; i += 1) {
		OWLModel.create("AtomicProcess", _json.atomicProcess[i].ID);
	}
	for ( var i = 0; i < _json.input.length; i += 1) {
		OWLModel.create("Input", _json.input[i].ID);
	}
	for ( var i = 0; i < _json.output.length; i += 1) {
		OWLModel.create("Output", _json.output[i].ID);
	}
	for ( var i = 0; i < _json.inputBinding.length; i += 1) {
		OWLModel.create("InputBinding", _json.inputBinding[i].ID);
	}
	for ( var i = 0; i < _json.outputBinding.length; i += 1) {
		OWLModel.create("OutputBinding", _json.outputBinding[i].ID);
	}
	for ( var i = 0; i < _json.inputMessageMap.length; i += 1) {
		OWLModel.create("InputMessageMap", _json.inputMessageMap[i].ID);
	}
	for ( var i = 0; i < _json.outputMessageMap.length; i += 1) {
		OWLModel.create("OutputMessageMap", _json.outputMessageMap[i].ID);
	}
	OWLModel.getCompositeProcess().setId(_json.compositeProcess.ID);
	// 第二层的结构
	for ( var i = 0; i < _json.anyOrder.length; i += 1) {
		for ( var j = 0; j < _json.anyOrder[i].components.length; j += 1) {
			OWLModel.get(_json.anyOrder[i].ID).addComponent(
					OWLModel.get(_json.anyOrder[i].components[j]));
		}
	}
	for ( var i = 0; i < _json.choice.length; i += 1) {
		for ( var j = 0; j < _json.choice[i].components.length; j += 1) {
			OWLModel.get(_json.choice[i].ID).addComponent(
					OWLModel.get(_json.choice[i].components[j]));
		}
	}
	for ( var i = 0; i < _json.sequence.length; i += 1) {
		for ( var j = 0; j < _json.sequence[i].components.length; j += 1) {
			OWLModel.get(_json.sequence[i].ID).addComponent(
					OWLModel.get(_json.sequence[i].components[j]));
		}
	}
	for ( var i = 0; i < _json.splitJoin.length; i += 1) {
		for ( var j = 0; j < _json.splitJoin[i].components.length; j += 1) {
			OWLModel.get(_json.splitJoin[i].ID).addComponent(
					OWLModel.get(_json.splitJoin[i].components[j]));
		}
	}
	for ( var i = 0; i < _json.perform.length; i += 1) {
		OWLModel.get(_json.perform[i].ID).setProcess(
				OWLModel.get(_json.perform[i].process));
		OWLModel.get(_json.perform[i].ID)
				.setExtended(_json.perform[i].extended);
		for ( var j = 0; j < _json.perform[i].inputBinding.length; j += 1) {
			OWLModel.get(_json.perform[i].ID).addInputBinding(
					OWLModel.get(_json.perform[i].inputBinding[j]));
		}
	}
	for ( var i = 0; i < _json.produce.length; i += 1) {
		for ( var j = 0; j < _json.produce[i].outputBinding.length; j += 1) {
			OWLModel.get(_json.produce[i].ID).addOutputBinding(
					OWLModel.get(_json.produce[i].outputBinding[j]));
		}
	}
	for ( var i = 0; i < _json.atomicProcess.length; i += 1) {
		var _process=OWLModel.get(_json.atomicProcess[i].ID);
		OWLModel.get(_json.atomicProcess[i].ID).setOperation(
				WSDLOperation.valueOf(_json.atomicProcess[i].operation));
		for ( var j = 0; j < _json.atomicProcess[i].input.length; j += 1) {
			OWLModel.get(_json.atomicProcess[i].ID).addInput(
					OWLModel.get(_json.atomicProcess[i].input[j]));
		}
		for ( var j = 0; j < _json.atomicProcess[i].output.length; j += 1) {
			OWLModel.get(_json.atomicProcess[i].ID).addOutput(
					OWLModel.get(_json.atomicProcess[i].output[j]));
		}
		for ( var j = 0; j < _json.atomicProcess[i].inputMessageMap.length; j += 1) {
			OWLModel.get(_json.atomicProcess[i].ID).addInputMessageMap(
					OWLModel.get(_json.atomicProcess[i].inputMessageMap[j]));
		}
		for ( var j = 0; j < _json.atomicProcess[i].inputMessageMap.length; j += 1) {
			OWLModel.get(_json.atomicProcess[i].ID).addOutputMessageMap(
					OWLModel.get(_json.atomicProcess[i].inputMessageMap[j]));
		}
		// 判断是为了兼容以前版本的格式
		if(_json.atomicProcess[i].conditionList){
			for(var j=0;j<_json.atomicProcess[i].conditionList.conditions[0].atoms.length;j++){
				
				var _condition=Condition.fromJson(_json.atomicProcess[i].conditionList.conditions[0].atoms[i]);
				_process.addPrecondition(_condition);
			}
		//}
		//if(_process.results){
			for(var j=0;j<_json.atomicProcess[i].results.effects[0].atoms.length;j++){
				var _condition=Condition.fromJson(_json.atomicProcess[i].results.effects[0].atoms[i]);
		
				_process.addPostcondition(_condition);
			}
		}
		
		
		
	}
	for ( var i = 0; i < _json.input.length; i += 1) {
		OWLModel.get(_json.input[i].ID).setType(_json.input[i].type);
	}
	for ( var i = 0; i < _json.output.length; i += 1) {
		OWLModel.get(_json.output[i].ID).setType(_json.output[i].type);
	}
	for ( var i = 0; i < _json.inputBinding.length; i += 1) {
		var binding = OWLModel.get(_json.inputBinding[i].ID);
		binding.setFromProcess(OWLModel.get(_json.inputBinding[i].fromProcess));
		binding.setTheVar(OWLModel.get(_json.inputBinding[i].theVar));
		binding.setToParam(OWLModel.get(_json.inputBinding[i].toParam));
	}
	for ( var i = 0; i < _json.outputBinding.length; i += 1) {
		var binding = OWLModel.get(_json.outputBinding[i].ID);
		binding
				.setFromProcess(OWLModel
						.get(_json.outputBinding[i].fromProcess));
		binding.setTheVar(OWLModel.get(_json.outputBinding[i].theVar));
		binding.setToParam(OWLModel.get(_json.outputBinding[i].toParam));
	}
	for ( var i = 0; i < _json.inputMessageMap.length; i += 1) {
		var map = _json.inputMessageMap[i];
		var param = WSDLParameter.valueOf(map.groundingParameter);
		OWLModel.get(map.ID).setGroundingParameter(param);
		OWLModel.get(map.ID).setOwlsParameter(OWLModel.get(map.owlsParameter));
	}
	for ( var i = 0; i < _json.outputMessageMap.length; i += 1) {
		var map = _json.outputMessageMap[i];
		var param = WSDLParameter.valueOf(map.groundingParameter);
		OWLModel.get(map.ID).setGroundingParameter(param);
		OWLModel.get(map.ID).setOwlsParameter(OWLModel.get(map.owlsParameter));
	}
	// 组合流程
	OWLModel.getCompositeProcess().setComposeOf(
			OWLModel.get(_json.compositeProcess.composeOf));
	for ( var i = 0; i < _json.compositeProcess.input.length; i += 1) {
		OWLModel.getCompositeProcess().addInput(
				OWLModel.get(_json.compositeProcess.input[i]));
	}
	for ( var i = 0; i < _json.compositeProcess.output.length; i += 1) {
		OWLModel.getCompositeProcess().addOutput(
				OWLModel.get(_json.compositeProcess.output[i]));
	}
};
