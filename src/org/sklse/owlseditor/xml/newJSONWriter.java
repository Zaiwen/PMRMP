package org.sklse.owlseditor.xml;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mindswap.owl.OWLOntology;
import org.sklse.owlseditor.json.JAnyOrder;
import org.sklse.owlseditor.json.JAtomicProcess;
import org.sklse.owlseditor.json.JChoice;
import org.sklse.owlseditor.json.JInput;
import org.sklse.owlseditor.json.JInputBinding;
import org.sklse.owlseditor.json.JInputMessageMap;
import org.sklse.owlseditor.json.JModel;
import org.sklse.owlseditor.json.JOutput;
import org.sklse.owlseditor.json.JOutputBinding;
import org.sklse.owlseditor.json.JOutputMessageMap;
import org.sklse.owlseditor.json.JPerform;
import org.sklse.owlseditor.json.JProduce;
import org.sklse.owlseditor.json.JSequence;
import org.sklse.owlseditor.json.JSplitJoin;
import org.sklse.owlseditor.json.OWLSWriter;
import org.sklse.owlseditor.sparqldl.VariableQuery;

import com.google.gson.Gson;

public class newJSONWriter {
	private JModel model = null;
	private XPlugin plugin = null;
	// 用于存放查找到所有input output id
	String[] selsectedInput = null;
	String[] selectedOutput = null;

	String[] selectedperform = null;// 存放所有查到的perform sparql-dl
	String[] selectedprocess = null;// 存放通过perform查到的process 一一对应
	int point = 0;// 插入点个数 与perform process 控制结构个数相同
	String[] selectedstructure = null;// 存放通过perform查到的控制结构 一一对应
	ArrayList<ArrayList<String>> selectbindinginput = new ArrayList<ArrayList<String>>();// 用于存放通过process
	// 查到的跟本体标注对应的input
	ArrayList<ArrayList<String>> selectbindingoutput = new ArrayList<ArrayList<String>>(); // 用于存放通过process
	// 查到的跟本体标注对应的output

	ArrayList<ArrayList<String>> selectinputbindinginParam = new ArrayList<ArrayList<String>>();;// 用于存放与realinput对应的属性为para的inputbinding
	ArrayList<ArrayList<String>> selectoutputbindinginVar = new ArrayList<ArrayList<String>>();// 用于存放于realoutput对应属性为var的outputbinding
	ArrayList<ArrayList<ArrayList<String>>> selectinputbindinginVar = new ArrayList<ArrayList<ArrayList<String>>>();// 用于存放于realoutput对应属性为var的inputbinding

	// 一个realoutput可以对应多个outputbinding 和多个inputbinding

	public newJSONWriter(String path, JModel model, XPlugin plugin) {

		// 初始化
		this.model = model;
		this.plugin = plugin;
		// 插入点的输入输出
		selsectedInput = this.getselectedInput();
		selectedOutput = this.getselectedOutput();

		this.selectedperform = this.getselectPerform(path, selsectedInput,
				selectedOutput);

		point = this.selectedperform.length;// 插入点个数

		this.selectedstructure = this
				.getselectcontrolstructure(selectedperform);

		this.selectedprocess = this.getseletedProcess(selectedperform);

		this.selectbindinginput = this.getrealinput(this.selectedprocess);

		this.selectbindingoutput = this.getrealoutput(this.selectedprocess);

		this.selectinputbindinginParam = this
				.getInputBindinginParam(this.selectbindinginput);
		this.selectoutputbindinginVar = this
				.getOutputBindinginVar(this.selectbindingoutput);
		this.selectinputbindinginVar = this
				.getInputBindinginVar(this.selectbindingoutput);
		this.addintoCP();

	}

	// 获得本体标注的所有的输入 xplugin
	public String[] getselectedInput() {
		String[] ID = null;

		ID = this.plugin.getExtensional_Point().getVariable_Query().getInput();
		// System.out.println(ID[0]);
		return ID;
	}

	// 输出 xplugin
	public String[] getselectedOutput() {
		String[] ID = null;

		ID = this.plugin.getExtensional_Point().getVariable_Query().getOutput();
		// System.out.println(ID[0]);

		return ID;
	}

	// perfrom xplugin
	public String[] getselectPerform(String path, String[] input,
			String[] output) {
		String[] ID = null;

		try {
			ID = VariableQuery.query(path, input, output);
			// System.out.println(ID[0]);
		} catch (Exception e) {

			e.printStackTrace();
		}
		return ID;
	}

	// 控制结构
	public String[] getselectcontrolstructure(String[] perform) {
		String[] ID = new String[perform.length];
		for (int m = 0; m < perform.length; m++) {
			for (int i = 0; i < this.model.getSequence().length; i++) {
				for (int j = 0; j < this.model.getSequence()[i].getComponents().length; j++)
					if (perform[m].equals(this.model.getSequence()[i]
							.getComponents()[j])) {
						ID[m] = this.model.getSequence()[i].getID();
						// System.out.println(ID[m]);

					}
			}
			for (int i = 0; i < this.model.getSplitJoin().length; i++) {
				for (int j = 0; j < this.model.getSplitJoin()[i]
						.getComponents().length; j++)
					if (perform[m].equals(this.model.getSplitJoin()[i]
							.getComponents()[j])) {
						ID[m] = this.model.getSplitJoin()[i].getID();

					}
			}
			for (int i = 0; i < this.model.getAnyOrder().length; i++) {
				for (int j = 0; j < this.model.getAnyOrder()[i].getComponents().length; j++)
					if (perform[m].equals(this.model.getAnyOrder()[i]
							.getComponents()[j])) {
						ID[m] = this.model.getAnyOrder()[i].getID();

					}
			}
			for (int i = 0; i < this.model.getChoice().length; i++) {
				for (int j = 0; j < this.model.getChoice()[i].getComponents().length; j++)
					if (perform[m].equals(this.model.getChoice()[i]
							.getComponents()[j])) {
						ID[m] = this.model.getChoice()[i].getID();

					}
			}
		}
		return ID;
	}

	// process
	public String[] getseletedProcess(String[] perform) {
		String[] process = new String[perform.length];
		for (int i = 0; i < perform.length; i++) {
			JPerform sperform = (JPerform) this.model.get(perform[i]);
			process[i] = sperform.getProcess();
		}
		return process;
	}

	// /realinput
	public ArrayList<ArrayList<String>> getrealinput(String[] process) {
		ArrayList<ArrayList<String>> input = new ArrayList<ArrayList<String>>();

		for (int k = 0; k < process.length; k++) {

			ArrayList<String> element = new ArrayList<String>();
			JAtomicProcess sprocess = (JAtomicProcess) this.model
					.get(process[k]);
			for (int j = 0; j < this.plugin.getExtensional_Fragment()
					.getInput().length; j++) {
				String in = null;
				for (int i = 0; i < sprocess.getInput().length; i++) {
					// 初始化

					JInput sinput = (JInput) this.model
							.get(sprocess.getInput()[i]);

					if (sinput.getType().equals(
							this.plugin.getExtensional_Fragment().getInput()[j].getBinding())) {
						in = sinput.getID();

					}
				}
				element.add(in);
			}
			input.add(element);
		}

		return input;
	}

	// realoutput
	public ArrayList<ArrayList<String>> getrealoutput(String[] process) {
		ArrayList<ArrayList<String>> output = new ArrayList<ArrayList<String>>();
		for (int k = 0; k < process.length; k++) {
			ArrayList<String> element = new ArrayList<String>();
			JAtomicProcess sprocess = (JAtomicProcess) this.model
					.get(process[k]);

			for (int j = 0; j < this.plugin.getExtensional_Fragment()
					.getInput().length; j++) {
				String out = null;
				for (int i = 0; i < sprocess.getOutput().length; i++) {

					JOutput soutput = (JOutput) this.model.get(sprocess
							.getOutput()[i]);

					if (soutput.getType()
							.equals(
									this.plugin.getExtensional_Fragment()
											.getInput()[j].getBinding())) {

						out = soutput.getID();

					}

				}
				element.add(out);
			}

			output.add(element);
		}
		return output;
	}

	// 查找toParam为realinput的inputbinding
	public ArrayList<ArrayList<String>> getInputBindinginParam(
			ArrayList<ArrayList<String>> realinput) {
		ArrayList<ArrayList<String>> inputBinding = new ArrayList<ArrayList<String>>();

		for (int i = 0; i < realinput.size(); i++) {
			ArrayList<String> element = new ArrayList<String>();
			for (int j = 0; j < realinput.get(i).size(); j++) {
				String inbinding = null;

				for (int k = 0; k < this.model.getInputBinding().length; k++) {
					JInputBinding binding = this.model.getInputBinding()[k];
					if (realinput.get(i).get(j) != null) {
						if (realinput.get(i).get(j)
								.equals(binding.getToParam())) {
							inbinding = binding.getID();

						}
					}
				}
				element.add(inbinding);

			}
			inputBinding.add(element);
		}

		return inputBinding;
	}

	// 查找theVar属性为output的inputbinding
	public ArrayList<ArrayList<ArrayList<String>>> getInputBindinginVar(
			ArrayList<ArrayList<String>> realoutput) {

		ArrayList<ArrayList<ArrayList<String>>> inputBinding = new ArrayList<ArrayList<ArrayList<String>>>();
		for (int i = 0; i < realoutput.size(); i++) {
			// 对应一个process的realoutput
			ArrayList<ArrayList<String>> element = new ArrayList<ArrayList<String>>();
			for (int j = 0; j < realoutput.get(i).size(); j++) {
				ArrayList<String> soutput = new ArrayList<String>();
				for (int k = 0; k < this.model.getInputBinding().length; k++) {
					JInputBinding binding = this.model.getInputBinding()[k];
					

					if (binding.getTheVar().equals(realoutput.get(i).get(j))) {

						soutput.add(binding.getID());
					}
					

				}
				element.add(soutput);
			}
			inputBinding.add(element);
		}

		return inputBinding;
	}

	// 查找thevar属性为output的Outputbinding
	public ArrayList<ArrayList<String>> getOutputBindinginVar(
			ArrayList<ArrayList<String>> realoutput) {
		ArrayList<ArrayList<String>> outputBinding = new ArrayList<ArrayList<String>>();
		for (int i = 0; i < realoutput.size(); i++) {
			ArrayList<String> element = new ArrayList<String>();
			for (int j = 0; j < realoutput.get(i).size(); j++) {
				String soutput = null;
				for (int k = 0; k < this.model.getOutputBinding().length; k++) {

					JOutputBinding binding = this.model.getOutputBinding()[k];

					if (binding.getTheVar().equals(realoutput.get(i).get(j))) {
						soutput = binding.getID();

					}
				}
				element.add(soutput);
			}
			outputBinding.add(element);

		}

		return outputBinding;
	}

	// 插入插件的input
	public ArrayList<String> addInput() {
		ArrayList<String> ID = new ArrayList<String>();
		ArrayList<JInput> jinput = new ArrayList<JInput>();
		for (int i = 0; i < this.model.getInput().length; i++) {
			jinput.add(this.model.getInput()[i]);
		}
		for (int i = 0; i < this.plugin.getExtensional_Fragment().getInput().length; i++) {
			JInput input = new JInput();
			String name = this.plugin.getExtensional_Fragment().getInput()[i]
					.getName();
			String apname = this.getname(name);

			input.setID(apname);
			jinput.add(input);

			ID.add(apname);

		}
		JInput[] input = new JInput[jinput.size()];
		for (int i = 0; i < jinput.size(); i++) {
			input[i] = jinput.get(i);
		}

		this.model.setInput(input);

		return ID;

	}

	// 插入插件的output
	public ArrayList<String> addOutput() {
		ArrayList<String> ID = new ArrayList<String>();
		ArrayList<JOutput> joutput = new ArrayList<JOutput>();
		for (int i = 0; i < this.model.getOutput().length; i++) {
			joutput.add(this.model.getOutput()[i]);
		}
		for (int i = 0; i < this.plugin.getExtensional_Fragment().getOutput().length; i++) {
			JOutput output = new JOutput();
			String name = this.plugin.getExtensional_Fragment().getOutput()[i]
					.getName();
			String apname = this.getname(name);
			output.setID(apname);
			joutput.add(output);

			ID.add(apname);

		}
		JOutput[] output = new JOutput[joutput.size()];
		for (int i = 0; i < joutput.size(); i++) {
			output[i] = joutput.get(i);
		}

		this.model.setOutput(output);
		return ID;

	}

	// 插入inputbinding
	public String addInputBinding(String theVar, String toParam,
			String fromProcess) {

		String inputBinding = this.getname("InputBinding");
		ArrayList<JInputBinding> jinputBinding = new ArrayList<JInputBinding>();
		for (int i = 0; i < this.model.getInputBinding().length; i++) {
			jinputBinding.add(this.model.getInputBinding()[i]);
		}

		JInputBinding element = new JInputBinding();

		// 添加inputBinding
		element.setID(inputBinding);
		element.setFromProcess(fromProcess);
		element.setTheVar(theVar);
		element.setToParam(toParam);
		jinputBinding.add(element);
		JInputBinding[] sinputBinding = new JInputBinding[jinputBinding.size()];
		for (int i = 0; i < jinputBinding.size(); i++) {
			sinputBinding[i] = jinputBinding.get(i);
		}
		this.model.setInputBinding(sinputBinding);
		// System.out.println(inputBinding);

		return inputBinding;
	}

	// 插入outputbinding
	public String addOutputBinding(String theVar, String toParam,
			String fromProcess) {

		String OutputBinding = this.getname("OutputBinding");

		ArrayList<JOutputBinding> joutputBinding = new ArrayList<JOutputBinding>();
		for (int i = 0; i < this.model.getOutputBinding().length; i++) {
			joutputBinding.add(this.model.getOutputBinding()[i]);
		}
		JOutputBinding element = new JOutputBinding();
		// 添加inputBinding
		element.setID(OutputBinding);
		element.setFromProcess(fromProcess);
		element.setTheVar(theVar);
		element.setToParam(toParam);

		joutputBinding.add(element);
		JOutputBinding[] soutputBinding = new JOutputBinding[joutputBinding
				.size()];
		for (int i = 0; i < joutputBinding.size(); i++) {
			soutputBinding[i] = joutputBinding.get(i);
		}
		this.model.setOutputBinding(soutputBinding);

		return OutputBinding;
	}

	// 插入InputMessageMap
	public ArrayList<String> addInputMessageMap(ArrayList<String> input) {
		ArrayList<String> InputMessageMap = new ArrayList<String>();
		ArrayList<JInputMessageMap> jinputMessageMap = new ArrayList<JInputMessageMap>();
		for (int i = 0; i < this.model.getInputMessageMap().length; i++) {
			jinputMessageMap.add(this.model.getInputMessageMap()[i]);
		}

		for (int i = 0; i < input.size(); i++) {
			JInputMessageMap inputMessageMap = new JInputMessageMap();
			String name = this.getname("InputMessageMap");

			inputMessageMap.setID(name);
			inputMessageMap
					.setGroundingParameter(this.plugin
							.getExtensional_Fragment().getWsdl_Grounding()
							.getWsdl_Location()
							+ "#"
							+ this.plugin.getExtensional_Fragment().getInput()[i]);
			inputMessageMap.setOwlsParameter(input.get(i));
			jinputMessageMap.add(inputMessageMap);

			InputMessageMap.add(name);

		}
		JInputMessageMap[] inputMessageMap = new JInputMessageMap[jinputMessageMap
				.size()];
		for (int i = 0; i < jinputMessageMap.size(); i++) {
			inputMessageMap[i] = jinputMessageMap.get(i);
		}

		this.model.setInputMessageMap(inputMessageMap);

		return InputMessageMap;
	}

	// 插入OutputMessageMap

	public ArrayList<String> addOutputMessageMap(ArrayList<String> output) {
		ArrayList<String> OutputMessageMap = new ArrayList<String>();

		ArrayList<JOutputMessageMap> joutputMessageMap = new ArrayList<JOutputMessageMap>();
		for (int i = 0; i < this.model.getOutputMessageMap().length; i++) {
			// System.out.println(this.model.getInputMessageMap().length);
			joutputMessageMap.add(this.model.getOutputMessageMap()[i]);
		}

		for (int i = 0; i < output.size(); i++) {
			JOutputMessageMap outputMessageMap = new JOutputMessageMap();
			String name = this.getname("OutputMessageMap");
			outputMessageMap.setID(name);
			outputMessageMap.setGroundingParameter(this.plugin
					.getExtensional_Fragment().getWsdl_Grounding()
					.getWsdl_Location()
					+ "#"
					+ this.plugin.getExtensional_Fragment().getOutput()[i]);
			outputMessageMap.setOwlsParameter(output.get(i));
			joutputMessageMap.add(outputMessageMap);

			OutputMessageMap.add(name);

		}
		JOutputMessageMap[] outputMessageMap = new JOutputMessageMap[joutputMessageMap
				.size()];
		for (int i = 0; i < joutputMessageMap.size(); i++) {
			outputMessageMap[i] = joutputMessageMap.get(i);
		}
		this.model.setOutputMessageMap(outputMessageMap);
		return OutputMessageMap;
	}

	// 插入process
	public String addProcess(ArrayList<String> input, ArrayList<String> output,
			ArrayList<String> inputMessageMap,
			ArrayList<String> outputMessageMap) {

		ArrayList<JAtomicProcess> jatomicProcess = new ArrayList<JAtomicProcess>();
		for (int i = 0; i < this.model.getAtomicProcess().length; i++) {
			jatomicProcess.add(this.model.getAtomicProcess()[i]);
		}
		String[] sinput = new String[input.size()];
		for (int i = 0; i < input.size(); i++) {
			sinput[i] = input.get(i);
		}
		String[] soutput = new String[output.size()];
		for (int i = 0; i < output.size(); i++) {
			soutput[i] = output.get(i);
		}
		String[] sinputMessageMap = new String[inputMessageMap.size()];
		for (int i = 0; i < inputMessageMap.size(); i++) {
			sinputMessageMap[i] = inputMessageMap.get(i);
		}
		String[] soutputMessageMap = new String[outputMessageMap.size()];
		for (int i = 0; i < outputMessageMap.size(); i++) {
			soutputMessageMap[i] = outputMessageMap.get(i);
		}

		JAtomicProcess atomicProcess = new JAtomicProcess();
		String name = this.plugin.getExtensional_Fragment().getWsdl_Grounding()
				.getOperation();
		String process = this.getname(name);

		atomicProcess.setID(process);
		atomicProcess.setOperation(this.plugin.getExtensional_Fragment()
				.getWsdl_Grounding().getWsdl_Location()
				+ "#" + name);
		atomicProcess.setPortType(this.plugin.getExtensional_Fragment()
				.getWsdl_Grounding().getWsdl_Location()
				+ "#"
				+ this.plugin.getExtensional_Fragment().getWsdl_Grounding()
						.getPortType());
		atomicProcess.setInputMessageName(this.plugin.getExtensional_Fragment()
				.getWsdl_Grounding().getInputMessageName());
		atomicProcess.setOutputMessageName(this.plugin
				.getExtensional_Fragment().getWsdl_Grounding()
				.getOutputMessageName());

		atomicProcess.setInput(sinput);

		atomicProcess.setOutput(soutput);

		atomicProcess.setInputMessageMap(sinputMessageMap);

		atomicProcess.setOutputMessageMap(soutputMessageMap);
		jatomicProcess.add(atomicProcess);

		JAtomicProcess[] satomicProcess = new JAtomicProcess[jatomicProcess
				.size()];
		for (int i = 0; i < jatomicProcess.size(); i++) {
			satomicProcess[i] = jatomicProcess.get(i);
		}

		this.model.setAtomicProcess(satomicProcess);

		return process;

	}

	// 插入perform
	public String addPerform(String process) {
		String perform = this.getname(this.plugin.getName());

		ArrayList<JPerform> jperform = new ArrayList<JPerform>();
		for (int i = 0; i < this.model.getPerform().length; i++) {
			jperform.add(this.model.getPerform()[i]);
		}
		JPerform sperform = new JPerform();

		sperform.setID(perform);
		sperform.setProcess(process);
		sperform.setExtended(true);

		jperform.add(sperform);
		JPerform[] performs = new JPerform[jperform.size()];
		for (int i = 0; i < jperform.size(); i++) {
			performs[i] = jperform.get(i);
		}

		this.model.setPerform(performs);

		return perform;
	}

	public String performaddinputBinding(String performname,
			ArrayList<String> inputBinding) {
		JPerform perform = (JPerform) this.model.get(performname);

		String[] sinputBinding = new String[inputBinding.size()];
		for (int i = 0; i < inputBinding.size(); i++) {
			sinputBinding[i] = inputBinding.get(i);
		}

		perform.setInputBinding(sinputBinding);

		return performname;

	}

	public String addProduce(ArrayList<String> OutputBinding) {
		String produce = null;

		if (this.model.getProduce().length == 0) {
			produce = this.getname("Produce");

			JProduce sproduce = new JProduce();
			JProduce[] Sproduce = new JProduce[1];

			sproduce.setID(produce);
			String[] outputBinding = new String[OutputBinding.size()];
			for (int i = 0; i < OutputBinding.size(); i++) {
				outputBinding[i] = OutputBinding.get(i);
			}

			sproduce.setOutputBinding(outputBinding);
			Sproduce[0] = sproduce;
			this.model.setProduce(Sproduce);

		} else if (this.model.getProduce().length > 0) {
			produce = this.model.getProduce()[0].getID();
			ArrayList<String> joutputBinding = new ArrayList<String>();
			for (int i = 0; i < this.model.getProduce()[0].getOutputBinding().length; i++) {
				joutputBinding.add(this.model.getProduce()[0]
						.getOutputBinding()[i]);
			}
			for (int i = 0; i < OutputBinding.size(); i++) {
				joutputBinding.add(OutputBinding.get(i));
			}

			String[] soutputBinding = new String[joutputBinding.size()];
			for (int i = 0; i < joutputBinding.size(); i++) {
				soutputBinding[i] = joutputBinding.get(i);
			}

			this.model.getProduce()[0].setOutputBinding(soutputBinding);

		}
		return produce;
	}

	public void addStructure(String type, String stype, String perform, int i) {
		if ("JSequence".equals(stype)) {
			ArrayList<JSequence> sequences = new ArrayList<JSequence>();
			for (int k = 0; k < this.model.getSequence().length; k++) {
				if (!this.selectedstructure[i]
						.equals(this.model.getSequence()[k].getID())) {
					sequences.add(this.model.getSequence()[k]);
				}

			}

			JSequence sequence = (JSequence) this.model
					.get(this.selectedstructure[i]);
			int size = sequence.getComponents().length;

			int inpoint = 0;
			for (int j = 0; j < sequence.getComponents().length; j++) {
				if (this.selectedperform[i].equals(sequence.getComponents()[j])) {
					inpoint = j;
				}
			}

			if ("preposition".equals(type) || "prefix".equals(type)) {
				String[] component = new String[size + 1];
				for (int j = 0; j < inpoint; j++) {
					component[j] = sequence.getComponents()[j];

				}
				component[inpoint] = perform;

				for (int j = inpoint; j < size; j++) {
					component[j + 1] = sequence.getComponents()[j];
				}
				sequence.setComponents(component);

			} else if ("postposition".equals(type) || "postfix".equals(type)) {
				String[] component = new String[size + 1];
				for (int j = 0; j <= inpoint; j++) {
					component[j] = sequence.getComponents()[j];
				}
				component[inpoint + 1] = perform;

				for (int j = inpoint + 1; j < size; j++) {
					component[j + 1] = sequence.getComponents()[j];
				}
				sequence.setComponents(component);
			}

			JSequence[] ssequence = new JSequence[sequences.size() + 1];
			for (int k = 0; k < sequences.size(); k++) {
				ssequence[k] = sequences.get(k);
			}
			ssequence[sequences.size()] = sequence;

			this.model.setSequence(ssequence);

		} else if ("JChoice".equals(stype)) {
			ArrayList<JAnyOrder> anyorders = new ArrayList<JAnyOrder>();
			for (int k = 0; k < this.model.getAnyOrder().length; k++) {
				if (!this.selectedstructure[i]
						.equals(this.model.getAnyOrder()[k].getID()))
					anyorders.add(this.model.getAnyOrder()[k]);
			}

			JAnyOrder anyOrder = (JAnyOrder) this.model
					.get(this.selectedstructure[i]);

			JSequence sequence = new JSequence();
			String sename = this.getname("Sequence");
			sequence.setID(sename);

			if ("preposition".equals(type) || "prefix".equals(type)) {
				String[] com = { perform, this.selectedperform[i] };
				sequence.setComponents(com);
			} else if ("postposition".equals(type) || "postfix".equals(type)) {
				String[] com = { this.selectedperform[i], perform };
				sequence.setComponents(com);
			}

			for (int j = 0; j < anyOrder.getComponents().length; j++) {
				if (this.selectedperform[i].equals(anyOrder.getComponents()[j])) {
					anyOrder.getComponents()[j] = sename;
				}
			}

			JSequence[] sequences = new JSequence[this.model.getSequence().length + 1];
			for (int j = 0; j < this.model.getSequence().length; j++) {
				sequences[j] = this.model.getSequence()[j];
			}
			sequences[this.model.getSequence().length] = sequence;

			this.model.setSequence(sequences);
			JAnyOrder[] sanyorder = new JAnyOrder[anyorders.size() + 1];
			for (int k = 0; k < anyorders.size(); k++) {
				sanyorder[k] = anyorders.get(k);
			}
			sanyorder[anyorders.size()] = anyOrder;
			this.model.setAnyOrder(sanyorder);

		} else if ("JAnyOrder".equals(stype)) {
			ArrayList<JChoice> choices = new ArrayList<JChoice>();
			for (int k = 0; k < this.model.getAnyOrder().length; k++) {
				if (!this.selectedstructure[i].equals(this.model.getChoice()[k]
						.getID()))
					choices.add(this.model.getChoice()[k]);
			}

			JChoice choice = (JChoice) this.model
					.get(this.selectedstructure[i]);

			JSequence sequence = new JSequence();
			String sename = this.getname("Sequence");
			sequence.setID(sename);

			if ("preposition".equals(type) || "prefix".equals(type)) {
				String[] com = { perform, this.selectedperform[i] };
				sequence.setComponents(com);
			} else if ("postposition".equals(type) || "postfix".equals(type)) {
				String[] com = { this.selectedperform[i], perform };
				sequence.setComponents(com);
			}

			for (int j = 0; j < choice.getComponents().length; j++) {
				if (this.selectedperform[i].equals(choice.getComponents()[j])) {
					choice.getComponents()[j] = sename;
				}
			}

			JSequence[] sequences = new JSequence[this.model.getSequence().length + 1];
			for (int j = 0; j < this.model.getSequence().length; j++) {
				sequences[j] = this.model.getSequence()[j];
			}
			sequences[this.model.getSequence().length] = sequence;
			this.model.setSequence(sequences);

			JChoice[] schoice = new JChoice[choices.size() + 1];
			for (int k = 0; k < choices.size(); k++) {
				schoice[k] = choices.get(k);
			}
			schoice[choices.size()] = choice;
			this.model.setChoice(schoice);

		} else if ("JSplitJoin".equals(stype)) {
			ArrayList<JSplitJoin> splitJoins = new ArrayList<JSplitJoin>();
			for (int k = 0; k < this.model.getSplitJoin().length; k++) {
				if (!this.selectedstructure[i]
						.equals(this.model.getSplitJoin()[k].getID()))
					splitJoins.add(this.model.getSplitJoin()[k]);
			}

			JSplitJoin splitJoin = (JSplitJoin) this.model
					.get(this.selectedstructure[i]);

			JSequence sequence = new JSequence();
			String sename = this.getname("Sequence");
			sequence.setID(sename);

			if ("preposition".equals(type) || "prefix".equals(type)) {
				String[] com = { perform, this.selectedperform[i] };
				sequence.setComponents(com);
			} else if ("postposition".equals(type) || "postfix".equals(type)) {
				String[] com = { this.selectedperform[i], perform };
				sequence.setComponents(com);
			}

			for (int j = 0; j < splitJoin.getComponents().length; j++) {
				if (this.selectedperform[i]
						.equals(splitJoin.getComponents()[j])) {
					splitJoin.getComponents()[j] = sename;
				}
			}

			JSequence[] sequences = new JSequence[this.model.getSequence().length + 1];
			for (int j = 0; j < this.model.getSequence().length; j++) {
				sequences[j] = this.model.getSequence()[j];
			}
			sequences[this.model.getSequence().length] = sequence;
			this.model.setSequence(sequences);

			JSplitJoin[] ssplitJoin = new JSplitJoin[splitJoins.size() + 1];
			for (int k = 0; k < splitJoins.size(); k++) {
				ssplitJoin[k] = splitJoins.get(k);
			}
			ssplitJoin[splitJoins.size()] = splitJoin;
			this.model.setSplitJoin(ssplitJoin);

		}
	}

	public String addiotoCP(ArrayList<String> input, ArrayList<String> output) {
		String ID = this.model.getCompositeProcess().getID();
		ArrayList<String> cpinput = new ArrayList<String>();
		ArrayList<String> cpoutput = new ArrayList<String>();
		for (int i = 0; i < this.model.getCompositeProcess().getInput().length; i++) {
			cpinput.add(this.model.getCompositeProcess().getInput()[i]);
		}
		for (int i = 0; i < this.model.getCompositeProcess().getOutput().length; i++) {
			cpoutput.add(this.model.getCompositeProcess().getOutput()[i]);
		}
		cpinput.addAll(input);
		cpoutput.addAll(output);
		String[] scpinput = new String[cpinput.size()];
		for (int i = 0; i < cpinput.size(); i++) {
			scpinput[i] = cpinput.get(i);
		}
		String[] scpoutput = new String[cpoutput.size()];
		for (int i = 0; i < cpoutput.size(); i++) {
			scpoutput[i] = cpoutput.get(i);
		}

		this.model.getCompositeProcess().setInput(scpinput);
		this.model.getCompositeProcess().setOutput(scpoutput);

		return ID;
	}

	public void addintoCP() {
		 System.out.println(point);

		for (int i = 0; i < this.point; i++) {// 根据插入点个数插入信息
			ArrayList<String> input = this.addInput();
			ArrayList<String> output = this.addOutput();

			ArrayList<String> inputMessageMap = this.addInputMessageMap(input);
			ArrayList<String> outputMessageMap = this
					.addOutputMessageMap(output);
			String process = this.addProcess(input, output, inputMessageMap,
					outputMessageMap);
			String perform = this.addPerform(process);

			// 分情况处理

			/*
			 * preposition情况：不改变原有的数据流，将输入输出绑定在cp上 （sequence）不改变原有的控制结构
			 * 在查找点前面添加插件perform （choice splitjoin anyorder）先新建一个sequence控制结构
			 * 按照插件perform 查找点perform加入sequence结构中 再将原有控制结构中查找点perform改为sequence
			 */
			//
			if ("preposition".equals(this.plugin.getExtensional_Fragment()
					.getExtension_pattern())) {
				// 每一个插件perform对应一个cp输出输出
				ArrayList<String> cpinput = this.addInput();// 存放需要添加到cp的input
				ArrayList<String> cpoutput = this.addOutput();// 存放添加到cp的output
				ArrayList<String> inputBinding = new ArrayList<String>();// 存放inputbinding
				ArrayList<String> outputBinding = new ArrayList<String>();// 存放outputbinding

				this.addiotoCP(cpinput, cpoutput);

				for (int j = 0; j < input.size(); j++) {
					// cpinput与inputbangding perform为TheParentPerform
					inputBinding.add(this.addInputBinding(cpinput.get(j), input
							.get(j), "TheParentPerform"));
				}

				this.performaddinputBinding(perform, inputBinding);

				for (int j = 0; j < output.size(); j++) {

					// cpoutput与output绑定 perform为插件perform
					outputBinding.add(this.addOutputBinding(output.get(j),
							cpoutput.get(j), perform));
				}
				
				this.addProduce(outputBinding);
				// System.out.println(1);
				// 分 控制流程 不同讨论
				if (this.model.get(this.selectedstructure[i]) instanceof JSequence) {

					this.addStructure("preposition", "JSequence", perform, i);

				} else if (this.model.get(this.selectedstructure[i]) instanceof JAnyOrder) {
					this.addStructure("preposition", "JAnyOrder", perform, i);

				} else if (this.model.get(this.selectedstructure[i]) instanceof JChoice) {
					this.addStructure("preposition", "JChoice", perform, i);

				} else if (this.model.get(this.selectedstructure[i]) instanceof JSplitJoin) {
					this.addStructure("preposition", "JSplitJoin", perform, i);

				}

			}

			/*
			 * prefix : 改变原有的数据流 ，插件的perform输入为查找点的输入，查找点perform的输入参数变为
			 * 插件的perform （sequence）不改变原有的控制结构，插在查找点perform前面 （choice anyorder
			 * splitjoin）先新建一个sequence控制结构 按照 <插件perform> <查找点perform>
			 * 加入sequence结构中 再将原有控制结构中查找点perform改为sequence
			 */
			else if ("prefix".equals(this.plugin.getExtensional_Fragment()
					.getExtension_pattern())) {
				ArrayList<String> inputBinding = new ArrayList<String>();
				ArrayList<String> outputBinding = new ArrayList<String>();
				ArrayList<String> cpinput = new ArrayList<String>();
				ArrayList<String> cpoutput = new ArrayList<String>();
				int j = 0;
				for (j = 0; j < input.size(); j++) {

					if (this.selectinputbindinginParam.get(i).get(j) == null) {
						String cpinputname = this.getname(this.plugin
								.getExtensional_Fragment().getInput()[j]
								.getName());
						inputBinding.add(this.addInputBinding(cpinputname,
								input.get(j), "TheParentPerform"));
						cpinput.add(cpinputname);

					} else {
						JInputBinding binding = (JInputBinding) this.model
								.get(this.selectinputbindinginParam.get(i).get(
										j));

						inputBinding.add(this.addInputBinding(binding
								.getTheVar(), input.get(j), binding
								.getFromProcess()));

						binding.setFromProcess(perform);

						binding.setTheVar(output.get(j));

						JInputBinding[] bindings = new JInputBinding[this.model
								.getInputBinding().length];
						for (int k = 0; k < this.model.getInputBinding().length; k++) {
							if (this.model.getInputBinding()[k].getID().equals(
									binding.getID())) {

								bindings[k] = binding;
							} else {
								bindings[k] = this.model.getInputBinding()[k];
							}
						}
						this.model.setInputBinding(bindings);

					}
				}
				if (j < output.size()) {
					for (int m = j; m < output.size(); m++) {

						String cpoutputname = this.getname(this.plugin
								.getExtensional_Fragment().getOutput()[m]
								.getName());
						outputBinding.add(this.addOutputBinding(output.get(m),
								cpoutputname, perform));

						cpoutput.add(cpoutputname);
					}
				}

				this.performaddinputBinding(perform, inputBinding);
				this.addiotoCP(cpinput, cpoutput);
				String cp = this.addProduce(outputBinding);
				if (this.model.get(this.selectedstructure[i]) instanceof JSequence) {

					this.addStructure("prefix", "JSequence", perform, i);

				} else if (this.model.get(this.selectedstructure[i]) instanceof JAnyOrder) {

					this.addStructure("prefix", "JAnyOrder", perform, i);

				} else if (this.model.get(this.selectedstructure[i]) instanceof JChoice) {
					this.addStructure("prefix", "JChoice", perform, i);

				} else if (this.model.get(this.selectedstructure[i]) instanceof JSplitJoin) {
					this.addStructure("prefix", "JSplitJoin", perform, i);

				}

			}

			/*
			 * postposition :不 改变原有的数据流， 插件输入输出绑定cp的输入输出
			 * （sequence）不改变原有的控制结构，插在查找点perform后面 （choice anyorder
			 * splitjoin）先新建一个sequence控制结构 按照 <查找点perform>
			 * <插件perform>加入sequence结构中 再将原有控制结构中查找点perform改为sequence
			 */

			else if ("postposition".equals(this.plugin
					.getExtensional_Fragment().getExtension_pattern())) {
				ArrayList<String> cpinput = this.addInput();
				ArrayList<String> cpoutput = this.addOutput();
				ArrayList<String> inputBinding = new ArrayList<String>();
				ArrayList<String> outputBinding = new ArrayList<String>();
				String[] out = new String[this.model.getCompositeProcess()
						.getOutput().length
						+ cpoutput.size()];
				String[] in = new String[this.model.getCompositeProcess()
						.getInput().length + 1];

				this.addiotoCP(cpinput, cpoutput);

				for (int j = 0; j < input.size(); j++) {

					if (this.selectbindingoutput.get(i).get(j) == null) {
						String cpinputname = this.getname(this.plugin
								.getExtensional_Fragment().getInput()[j]
								.getName());
						inputBinding.add(this.addInputBinding(cpinputname,
								input.get(j), "TheParentPerform"));
						cpinput.add(cpinputname);

					} else {

						inputBinding.add(this.addInputBinding(
								this.selectbindingoutput.get(i).get(j), input
										.get(j), this.selectedperform[i]));
					}
				}

				for (int j = 0; j < output.size(); j++) {
					String cpoutputname = this
							.getname(this.plugin.getExtensional_Fragment()
									.getOutput()[j].getName());
					outputBinding.add(this.addOutputBinding(output.get(j),
							cpoutputname, perform));

					cpoutput.add(cpoutputname);

				}
				this.performaddinputBinding(perform, inputBinding);
				this.addiotoCP(cpinput, cpoutput);
				this.addProduce(outputBinding);

				if (this.model.get(this.selectedstructure[i]) instanceof JSequence) {

					this.addStructure("postposition", "JSequence", perform, i);

				} else if (this.model.get(this.selectedstructure[i]) instanceof JAnyOrder) {

					this.addStructure("postposition", "JAnyOrder", perform, i);

				} else if (this.model.get(this.selectedstructure[i]) instanceof JChoice) {
					this.addStructure("postposition", "JChoice", perform, i);

				} else if (this.model.get(this.selectedstructure[i]) instanceof JSplitJoin) {
					this.addStructure("postposition", "JSplitJoin", perform, i);
				}

			}

			/*
			 * postfix : 改变原有的数据流， 插件输出为插件的输出，查找点perform输出改为插件的输出 ，多余的输入输出绑定在cp上
			 * （sequence）不改变原有的控制结构，插在查找点perform后面 （choice anyorder
			 * splitjoin）先新建一个sequence控制结构 按照 <查找点perform>
			 * <插件perform>加入sequence结构中 再将原有控制结构中查找点perform改为sequence
			 */

			else if ("postfix".equals(this.plugin.getExtensional_Fragment()
					.getExtension_pattern())) {
				ArrayList<String> inputBinding = new ArrayList<String>();
				ArrayList<String> outputBinding = new ArrayList<String>();
				ArrayList<String> cpinput = new ArrayList<String>();
				ArrayList<String> cpoutput = new ArrayList<String>();
				int l=0;
				for (l = 0; l < input.size(); l++) {
					if (this.selectbindingoutput.get(i).get(l) == null) {
						String cpinputname = this.getname(this.plugin
								.getExtensional_Fragment().getInput()[l]
								.getName());
						inputBinding.add(this.addInputBinding(cpinputname,
								input.get(l), "TheParentPerform"));
						cpinput.add(cpinputname);

					} else {
						for (int m = 0; m < this.selectinputbindinginVar.get(i)
								.get(l).size(); m++) {
							if (this.selectinputbindinginVar.get(i).get(l).get(
									m) != null) {
								JInputBinding binding = (JInputBinding) this.model
										.get(this.selectinputbindinginVar
												.get(i).get(l).get(m));

								inputBinding.add(this.addInputBinding(binding
										.getTheVar(), input.get(l), binding.getFromProcess()));

								binding.setTheVar(output.get(l));

								JInputBinding[] bindings = new JInputBinding[this.model
										.getInputBinding().length];
								for (int j = 0; j < this.model
										.getInputBinding().length; j++) {
									if (this.model.getInputBinding()[j].getID()
											.equals(binding.getID())) {
										// System.out.println(1);
										bindings[j] = binding;
									} else {
										bindings[j] = this.model
												.getInputBinding()[j];
									}
								}
								this.model.setInputBinding(bindings);
							}

						}
						if (this.selectoutputbindinginVar.get(i).get(l) != null) {
							for (int k = 0; k < this.model.getOutputBinding().length; k++) {
								if (this.model.getOutputBinding()[k].getID() == this.selectoutputbindinginVar
										.get(i).get(l)) {

									inputBinding.add(this.addInputBinding(
											 this.model
													.getOutputBinding()[k]
													.getTheVar(),input.get(l), this.model
													.getOutputBinding()[k].getFromProcess()));
									this.model.getOutputBinding()[k]
											.setTheVar(output.get(l));

								}
							}
						}
					}

				}

				
				if (l < output.size()) {
					for (int j = l+1; j < output.size(); j++) {

						String cpoutputname = this.getname(this.plugin
								.getExtensional_Fragment().getOutput()[j]
								.getName());
						outputBinding.add(this.addOutputBinding(output.get(j),
								cpoutputname, perform));

						cpoutput.add(cpoutputname);
					}
				}

				this.performaddinputBinding(perform, inputBinding);
				this.addProduce(outputBinding);
				if (this.model.get(this.selectedstructure[i]) instanceof JSequence) {

					this.addStructure("postfix", "JSequence", perform, i);

				} else if (this.model.get(this.selectedstructure[i]) instanceof JAnyOrder) {

					this.addStructure("postfix", "JAnyOrder", perform, i);

				} else if (this.model.get(this.selectedstructure[i]) instanceof JChoice) {
					this.addStructure("postfix", "JChoice", perform, i);

				} else if (this.model.get(this.selectedstructure[i]) instanceof JSplitJoin) {
					this.addStructure("postfix", "JSplitJoin", perform, i);

				}

			}

			/*
			 * synchronized : 不改变原有的数据流，插件输入输出绑定在cp输入输出上
			 * （splitjoin）不改变原有的控制结构，插在查找点perform后面 （choice anyorder
			 * sequence）先新建一个splitjoin控制结构 按照 <查找点perform>
			 * <插件perform>加入sequence结构中 （无顺序） 再将原有控制结构中查找点perform改为splitjoin
			 */
			else if ("synchronized".equals(this.plugin
					.getExtensional_Fragment().getExtension_pattern())) {
				ArrayList<String> cpinput = this.addInput();// 存放cp的input
				ArrayList<String> cpoutput = this.addOutput();// 存放cp的output
				ArrayList<String> inputBinding = new ArrayList<String>();// 存放inputbinding
				ArrayList<String> outputBinding = new ArrayList<String>();// 存放outputbinding

				this.addiotoCP(cpinput, cpoutput);

				for (int j = 0; j < input.size(); j++) {
					// cpinput与inputbangding perform为TheParentPerform
					inputBinding.add(this.addInputBinding(cpinput.get(j), input
							.get(j), "TheParentPerform"));
				}

				this.performaddinputBinding(perform, inputBinding);

				for (int j = 0; j < output.size(); j++) {

					// cpoutput与output绑定 perform为插件perform
					outputBinding.add(this.addOutputBinding(output.get(j),
							cpoutput.get(j), perform));
				}
				this.addProduce(outputBinding);

				// 分 控制流程 不同讨论
				if (this.model.get(this.selectedstructure[i]) instanceof JSequence) {
					ArrayList<JSequence> sequences = new ArrayList<JSequence>();

					// System.out.println(1);
					for (int k = 0; k < this.model.getSequence().length; k++) {
						if (!this.selectedstructure[i].equals(this.model
								.getSequence()[k].getID())) {
							sequences.add(this.model.getSequence()[k]);
						}

					}

					JSplitJoin[] splitJoins = new JSplitJoin[this.model
							.getSplitJoin().length + 1];
					for (int k = 0; k < this.model.getSplitJoin().length; k++) {
						splitJoins[k] = this.model.getSplitJoin()[k];
					}

					JSequence sequence = (JSequence) this.model
							.get(this.selectedstructure[i]);
					int size = sequence.getComponents().length;
					String[] component = new String[size];

					int inpoint = 0;
					for (int j = 0; j < sequence.getComponents().length; j++) {
						if (this.selectedperform[i].equals(sequence
								.getComponents()[j])) {
							inpoint = j;
						}
					}
					for (int j = 0; j < inpoint; j++) {
						component[j] = sequence.getComponents()[j];
					}

					String spname = this.getname("splitJoin");
					JSplitJoin splitJoin = new JSplitJoin();
					splitJoin.setID(spname);
					String[] spcom = { perform,
							sequence.getComponents()[inpoint] };
					splitJoin.setComponents(spcom);
					splitJoins[this.model.getSplitJoin().length] = splitJoin;
					this.model.setSplitJoin(splitJoins);
					component[inpoint] = spname;

					for (int j = inpoint + 1; j < size; j++) {
						component[j] = sequence.getComponents()[j];
					}
					sequence.setComponents(component);
					JSequence[] ssequence = new JSequence[sequences.size() + 1];
					for (int k = 0; k < sequences.size(); k++) {
						ssequence[k] = sequences.get(k);
					}
					ssequence[sequences.size()] = sequence;

					this.model.setSequence(ssequence);

				} else if (this.model.get(this.selectedstructure[i]) instanceof JAnyOrder) {

					ArrayList<JAnyOrder> anyorders = new ArrayList<JAnyOrder>();
					for (int k = 0; k < this.model.getAnyOrder().length; k++) {
						if (!this.selectedstructure[i].equals(this.model
								.getAnyOrder()[k].getID()))
							anyorders.add(this.model.getAnyOrder()[k]);
					}

					JSplitJoin[] splitJoins = new JSplitJoin[this.model
							.getSplitJoin().length + 1];
					for (int k = 0; k < this.model.getSplitJoin().length; k++) {
						splitJoins[k] = this.model.getSplitJoin()[k];
					}

					JAnyOrder anyOrder = (JAnyOrder) this.model
							.get(this.selectedstructure[i]);
					int size = anyOrder.getComponents().length;
					String[] component = new String[size + 1];
					int inpoint = 0;
					for (int j = 0; j < anyOrder.getComponents().length; j++) {
						if (this.selectedperform[i].equals(anyOrder
								.getComponents()[j])) {
							inpoint = j;
						}
					}
					for (int j = 0; j < inpoint; j++) {
						component[j] = anyOrder.getComponents()[j];
					}

					JSplitJoin splitJoin = new JSplitJoin();
					String sname = this.getname("splitJoin");
					splitJoin.setID(sname);
					String[] com = { perform, this.selectedperform[i] };
					splitJoin.setComponents(com);

					splitJoins[this.model.getSplitJoin().length] = splitJoin;

					component[inpoint] = sname;
					for (int j = inpoint + 1; j < size; j++) {
						component[j] = anyOrder.getComponents()[j];
					}
					anyOrder.setComponents(component);

					this.model.setSplitJoin(splitJoins);
					JAnyOrder[] sanyorder = new JAnyOrder[anyorders.size() + 1];
					for (int k = 0; k < anyorders.size(); k++) {
						sanyorder[k] = anyorders.get(k);
					}
					sanyorder[anyorders.size()] = anyOrder;
					this.model.setAnyOrder(sanyorder);

				} else if (this.model.get(this.selectedstructure[i]) instanceof JChoice) {
					ArrayList<JChoice> choices = new ArrayList<JChoice>();
					for (int k = 0; k < this.model.getAnyOrder().length; k++) {
						if (!this.selectedstructure[i].equals(this.model
								.getChoice()[k].getID()))
							choices.add(this.model.getChoice()[k]);
					}

					JChoice choice = (JChoice) this.model
							.get(this.selectedstructure[i]);
					int size = choice.getComponents().length;
					String[] component = new String[size + 1];
					int inpoint = 0;
					for (int j = 0; j < choice.getComponents().length; j++) {
						if (this.selectedperform[i].equals(choice
								.getComponents()[j])) {
							inpoint = j;
						}
					}
					for (int j = 0; j < inpoint; j++) {
						component[j] = choice.getComponents()[j];
					}

					JSplitJoin splitJoin = new JSplitJoin();
					String spname = this.getname("Sequence");
					splitJoin.setID(spname);
					String[] com = { perform, this.selectedperform[i] };
					splitJoin.setComponents(com);

					JSplitJoin[] splitJoins = new JSplitJoin[this.model
							.getSplitJoin().length + 1];
					for (int j = 0; j < this.model.getSplitJoin().length; j++) {
						splitJoins[j] = this.model.getSplitJoin()[j];
					}
					splitJoins[this.model.getSplitJoin().length] = splitJoin;

					component[inpoint] = spname;

					for (int j = inpoint + 1; j < size; j++) {
						component[j] = choice.getComponents()[j];
					}

					choice.setComponents(component);

					this.model.setSplitJoin(splitJoins);
					JChoice[] schoice = new JChoice[choices.size()];
					for (int k = 0; k < choices.size(); k++) {
						schoice[k] = choices.get(k);
					}
					schoice[choices.size()] = choice;
					this.model.setChoice(schoice);

				} else if (this.model.get(this.selectedstructure[i]) instanceof JSplitJoin) {
					ArrayList<JSplitJoin> splitJoins = new ArrayList<JSplitJoin>();
					for (int k = 0; k < this.model.getSplitJoin().length; k++) {
						if (!this.selectedstructure[i].equals(this.model
								.getSplitJoin()[k].getID()))
							splitJoins.add(this.model.getSplitJoin()[k]);
					}

					JSplitJoin splitJoin = (JSplitJoin) this.model
							.get(this.selectedstructure[i]);
					int size = splitJoin.getComponents().length;
					String[] component = new String[size + 1];

					for (int j = 0; j < splitJoin.getComponents().length; j++) {
						component[j] = splitJoin.getComponents()[j];
					}
					component[size] = perform;

					splitJoin.setComponents(component);

					JSplitJoin[] ssplitJoin = new JSplitJoin[splitJoins.size()];
					for (int k = 0; k < splitJoins.size(); k++) {
						ssplitJoin[k] = splitJoins.get(k);
					}
					ssplitJoin[splitJoins.size()] = splitJoin;
					this.model.setSplitJoin(ssplitJoin);

				}

			}

			/*
			 * substitute : 改变原有的数据流，插入点perform的输入输出绑定改为插件的输入输出
			 * 
			 * （choice anyorder sequencesplitjoin）不改变原有的控制结构
			 * 将控制结构中《插入点perform》改为《插件perform》
			 */
			else if ("substitute".equals(this.plugin.getExtensional_Fragment()
					.getExtension_pattern())) {
				ArrayList<String> inputBinding = new ArrayList<String>();
				ArrayList<String> outputBinding = new ArrayList<String>();
				ArrayList<String> cpinput = new ArrayList<String>();
				ArrayList<String> cpoutput = new ArrayList<String>();

				for (int l = 0; l < input.size(); l++) {
					if (l >= this.selectbindinginput.get(i).size()) {
						String cpinputname = this.getname(this.plugin
								.getExtensional_Fragment().getInput()[l]
								.getName());

						cpinput.add(cpinputname);
						inputBinding.add(this.addInputBinding(cpinputname,
								input.get(l), "TheParentPerform"));

					} else {
						for (int k = 0; k < this.model.getInputBinding().length; k++) {
							if (this.model.getInputBinding()[k].getToParam()
									.equals(
											this.selectinputbindinginParam.get(
													i).get(l))) {
								this.model.getInputBinding()[k]
										.setToParam(input.get(l));
								inputBinding
										.add(this.model.getInputBinding()[k]
												.getID());
							}

						}

					}
				}

				for (int l = 0; l < output.size(); l++) {
					if (l >= this.selectbindingoutput.get(i).size()) {
						String cpoutputname = this.getname(this.plugin
								.getExtensional_Fragment().getOutput()[l]
								.getName());
						outputBinding.add(this.addOutputBinding(output.get(l),
								cpoutputname, perform));
						cpoutput.add(cpoutputname);
					} else {
						for (int k = 0; k < this.model.getInputBinding().length; k++) {
							for (int m = 0; m < this.selectinputbindinginVar
									.get(i).get(l).size(); m++) {
								if (this.model.getInputBinding()[k]
										.equals(this.selectinputbindinginVar
												.get(i).get(l).get(m))) {
									this.model.getInputBinding()[k]
											.setTheVar(output.get(l));
									this.model.getInputBinding()[k]
											.setFromProcess(perform);
									inputBinding.add(this.model
											.getInputBinding()[k].getID());
								}
							}
						}
						for (int k = 0; k < this.model.getOutputBinding().length; k++) {
							if (this.model.getOutputBinding()[k].getTheVar()
									.equals(
											this.selectoutputbindinginVar
													.get(i).get(l))) {
								this.model.getOutputBinding()[k]
										.setTheVar(output.get(l));
								outputBinding
										.add(this.model.getOutputBinding()[k]
												.getID());
							}

						}
					}
				}
				this.performaddinputBinding(perform, inputBinding);
				this.addProduce(outputBinding);
				if (this.model.get(this.selectedstructure[i]) instanceof JSequence) {

					for (int j = 0; j < this.model.getSequence().length; j++) {
						for (int k = 0; k < this.model.getSequence()[j]
								.getComponents().length; k++) {
							if (this.model.getSequence()[j].getComponents()[k]
									.equals(this.selectedperform[i])) {
								this.model.getSequence()[j].getComponents()[k] = perform;
							}
						}
					}

				} else if (this.model.get(this.selectedstructure[i]) instanceof JAnyOrder) {

					for (int j = 0; j < this.model.getAnyOrder().length; j++) {
						for (int k = 0; k < this.model.getAnyOrder()[j]
								.getComponents().length; k++) {
							if (this.model.getAnyOrder()[j].getComponents()[k]
									.equals(this.selectedperform[i])) {
								this.model.getAnyOrder()[j].getComponents()[k] = perform;
							}
						}
					}

				} else if (this.model.get(this.selectedstructure[i]) instanceof JChoice) {

					for (int j = 0; j < this.model.getChoice().length; j++) {
						for (int k = 0; k < this.model.getChoice()[j]
								.getComponents().length; k++) {
							if (this.model.getChoice()[j].getComponents()[k]
									.equals(this.selectedperform[i])) {
								this.model.getChoice()[j].getComponents()[k] = perform;
							}
						}
					}
				} else if (this.model.get(this.selectedstructure[i]) instanceof JSplitJoin) {

					for (int j = 0; j < this.model.getSplitJoin().length; j++) {
						for (int k = 0; k < this.model.getSplitJoin()[j]
								.getComponents().length; k++) {
							if (this.model.getSplitJoin()[j].getComponents()[k]
									.equals(this.selectedperform[i])) {
								this.model.getSplitJoin()[j].getComponents()[k] = perform;
							}
						}
					}
				}

			}
		}
	}

	// 为添加的部分找一个唯一的ID;
	public String getname(String name) {

		for (int i = 1;; i++) {
			if (this.model.get(name + "_" + i) != null)
				continue;
			else
				return name + "_" + i;
		}
	}

	public static void main(String args[]) throws Exception {
		Gson gson = new Gson();
		File file1 = new File(
				"E:/project/summerproject/BPEP/WebContent/Process/chenliang/demo03/HashClass.json");
		File file2 = new File(
				"E:/project/summerproject/BPEP/WebContent/Process/chenliang/demo03/substitute.json");
		JModel model = gson.fromJson(new FileReader(file1), JModel.class);

		XPlugin plugin = gson.fromJson(new FileReader(file2), XPlugin.class);
		new newJSONWriter(
				"E:/project/summerproject/BPEP/WebContent/Process/chenliang/demo03/HashClass.owl",
				model, plugin);

		new getJSON(model);

	}

}
