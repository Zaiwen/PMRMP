package org.sklse.owlseditor.json;


public class JModel {

	private JAnyOrder anyOrder[] = null;

	private JChoice choice[] = null;

	private JSequence sequence[] = null;

	private JSplitJoin splitJoin[] = null;

	private JPerform perform[] = null;

	private JProduce produce[] = null;

	private JAtomicProcess atomicProcess[] = null;

	private JCompositeProcess compositeProcess = null;

	private JInput input[] = null;

	private JOutput output[] = null;

	private JInputBinding inputBinding[] = null;

	private JOutputBinding outputBinding[] = null;

	private JInputMessageMap inputMessageMap[] = null;
	private JOutputMessageMap outputMessageMap[] = null;

	public void setAnyOrder(JAnyOrder[] anyOrder) {
		this.anyOrder = anyOrder;
	}

	public void setChoice(JChoice[] choice) {
		this.choice = choice;
	}

	public void setSequence(JSequence[] sequence) {
		this.sequence = sequence;
	}

	public void setSplitJoin(JSplitJoin[] splitJoin) {
		this.splitJoin = splitJoin;
	}

	public void setPerform(JPerform[] perform) {
		this.perform = perform;
	}

	public void setProduce(JProduce[] produce) {
		this.produce = produce;
	}

	public void setAtomicProcess(JAtomicProcess[] atomicProcess) {
		this.atomicProcess = atomicProcess;
	}

	public void setCompositeProcess(JCompositeProcess compositeProcess) {
		this.compositeProcess = compositeProcess;
	}

	public void setInput(JInput[] input) {
		this.input = input;
	}

	public void setOutput(JOutput[] output) {
		this.output = output;
	}

	public void setInputBinding(JInputBinding[] inputBinding) {
		this.inputBinding = inputBinding;
	}

	public void setOutputBinding(JOutputBinding[] outputBinding) {
		this.outputBinding = outputBinding;
	}

	public JInputMessageMap[] getInputMessageMap() {
		return inputMessageMap;
	}

	public void setInputMessageMap(JInputMessageMap[] inputMessageMap) {
		this.inputMessageMap = inputMessageMap;
	}

	public JOutputMessageMap[] getOutputMessageMap() {
		return outputMessageMap;
	}

	public void setOutputMessageMap(JOutputMessageMap[] outputMessageMap) {
		this.outputMessageMap = outputMessageMap;
	}

	

	public JAnyOrder[] getAnyOrder() {
		return anyOrder;
	}

	public JChoice[] getChoice() {
		return choice;
	}

	public JSequence[] getSequence() {
		return sequence;
	}

	public JSplitJoin[] getSplitJoin() {
		return splitJoin;
	}

	public JPerform[] getPerform() {
		return perform;
	}

	public JProduce[] getProduce() {
		return produce;
	}

	public JAtomicProcess[] getAtomicProcess() {
		return atomicProcess;
	}

	public JCompositeProcess getCompositeProcess() {
		return compositeProcess;
	}

	public JInput[] getInput() {
		return input;
	}

	public JOutput[] getOutput() {
		return output;
	}

	public JInputBinding[] getInputBinding() {
		return inputBinding;
	}

	public JOutputBinding[] getOutputBinding() {
		return outputBinding;
	}

	public Object get(String id) {
		for (int i = 0; i < this.anyOrder.length; i++) {
			if (this.anyOrder[i].getID().equals(id)) {
				return this.anyOrder[i];
			}
		}
		for (int i = 0; i < this.atomicProcess.length; i++) {
			if (this.atomicProcess[i].getID().equals(id)) {
				return this.atomicProcess[i];
			}
		}
		for (int i = 0; i < this.choice.length; i++) {
			if (this.choice[i].getID().equals(id)) {
				return this.choice[i];
			}
		}
		if (this.compositeProcess.getID().equals(id)) {
			return this.compositeProcess;
		}
		for (int i = 0; i < this.input.length; i++) {
			if (this.input[i].getID().equals(id)) {
				return this.input[i];
			}
		}
		for (int i = 0; i < this.inputBinding.length; i++) {
			if (this.inputBinding[i].getID().equals(id)) {
				return this.inputBinding[i];
			}
		}
		for (int i = 0; i < this.output.length; i++) {
			if (this.output[i].getID().equals(id)) {
				return this.output[i];
			}
		}
		for (int i = 0; i < this.outputBinding.length; i++) {
			if (this.outputBinding[i].getID().equals(id)) {
				return this.outputBinding[i];
			}
		}
		for (int i = 0; i < this.perform.length; i++) {
			if (this.perform[i].getID().equals(id)) {
				return this.perform[i];
			}
		}
		for (int i = 0; i < this.produce.length; i++) {
			if (this.produce[i].getID().equals(id)) {
				return this.produce[i];
			}
		}
		for (int i = 0; i < this.sequence.length; i++) {
			if (this.sequence[i].getID().equals(id)) {
				return this.sequence[i];
			}
		}
		for (int i = 0; i < this.splitJoin.length; i++) {
			if (this.splitJoin[i].getID().equals(id)) {
				return this.splitJoin[i];
			}
		}
		for (int i = 0; i < this.inputMessageMap.length; i++) {
			if (this.inputMessageMap[i].getID().equals(id)) {
				return this.inputMessageMap[i];
			}
		}
		for (int i = 0; i < this.outputMessageMap.length; i++) {
			if (this.outputMessageMap[i].getID().equals(id)) {
				return this.outputMessageMap[i];
			}
		}
		return null;
	}
}
