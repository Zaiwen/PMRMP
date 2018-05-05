package org.sklse.owlseditor.json;

import com.google.gson.Gson;
import org.mindswap.owl.OWLClass;
import org.mindswap.owl.OWLDataProperty;
import org.mindswap.owl.OWLFactory;
import org.mindswap.owl.OWLOntology;
import org.mindswap.owl.vocabulary.XSD;
import org.mindswap.owls.generic.expression.Expression;
import org.mindswap.owls.generic.expression.LogicLanguage;
import org.mindswap.owls.grounding.Grounding;
import org.mindswap.owls.grounding.MessageMap;
import org.mindswap.owls.grounding.WSDLAtomicGrounding;
import org.mindswap.owls.grounding.WSDLOperationRef;
import org.mindswap.owls.process.*;
import org.mindswap.owls.profile.Profile;
import org.mindswap.owls.service.Service;
import org.mindswap.owls.vocabulary.OWLS_1_1;
import org.mindswap.swrl.Atom;
import org.mindswap.swrl.AtomList;
import org.mindswap.swrl.SWRLFactory;
import org.mindswap.swrl.SWRLFactoryCreator;
import org.mindswap.utils.URIUtils;

import java.io.File;
import java.io.FileReader;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OWLSWriter {

    public static final String BASE_URL = "http://www.ai.sri.com/daml/services/owl-s/1.2/Process.owl#";//fixme this base url is not correct

    public static final String CONCEPT_URL = "http://www.ai.sri.com/daml/services/owl-s/1.2/Process.owl#";//fixme this base url is not correct

    private JModel model = null;

    private OWLOntology ont = null;

    private ArrayList<WSDLAtomicGrounding> groundings = new ArrayList<WSDLAtomicGrounding>();

    private Map<String, Object> map = new HashMap<String, Object>();
    private SWRLFactory swrl;

    public OWLSWriter(JModel model) {
        this.model = model;
    }

    public Object getOntology(String id) throws Exception {
        if ("TheParentPerform".equals(id)) {
            return Perform.TheParentPerform;
        } else if (map.containsKey(id)) {
            return map.get(id);
        } else {
            Object obj = this.model.get(id);
            if (obj == null) {
                return null;
            } else if (obj instanceof JAnyOrder) {
                Object o = this.createAnyOrder((JAnyOrder) obj);
                map.put(id, o);
                return o;
            } else if (obj instanceof JChoice) {
                Object o = this.createChoice((JChoice) obj);
                map.put(id, o);
                return o;
            } else if (obj instanceof JSplitJoin) {
                Object o = this.createSplitJoin((JSplitJoin) obj);
                map.put(id, o);
                return o;
            } else if (obj instanceof JInput) {
                Object o = this.createInput((JInput) obj);
                map.put(id, o);
                return o;
            } else if (obj instanceof JOutput) {
                Object o = this.createOutput((JOutput) obj);
                map.put(id, o);
                return o;
            } else if (obj instanceof JSequence) {
                Object o = this.createSequence((JSequence) obj);
                map.put(id, o);
                return o;
            } else if (obj instanceof JPerform) {
                Object o = this.createPerform((JPerform) obj);
                map.put(id, o);
                return o;
            } else if (obj instanceof JAtomicProcess) {
                Object o = this.createAtomicProcess((JAtomicProcess) obj);
                map.put(id, o);
                return o;
            } else if (obj instanceof JProduce) {
                Object o = this.createProduce((JProduce) obj);
                map.put(id, o);
                return o;
            } else if (obj instanceof JInputMessageMap) {
                Object o = this.creatInputMessageMap((JInputMessageMap) obj);
                map.put(id, o);
                return o;
            } else if (obj instanceof JOutputMessageMap) {
                Object o = this.creatOutputMessageMap((JOutputMessageMap) obj);
                map.put(id, o);
                return o;
            }
        }
        return null;
    }

    private Object createInput(JInput i) throws Exception {
        Input input = ont.createInput(new URI(BASE_URL + i.getID()));
        if (i.getType() != null) {
            OWLClass cls = ont.createClass(new URI(i.getType()));
            input.setParamType(cls);
        }
        return input;
    }

    private Object createOutput(JOutput o) throws Exception {
        Output output = ont.createOutput(new URI(BASE_URL + o.getID()));
        if (o.getType() != null) {
            OWLClass cls = ont.createClass(new URI(o.getType()));
            output.setParamType(cls);
        }
        return output;
    }

    private Object createAnyOrder(JAnyOrder any) throws Exception {
        AnyOrder anyOrder = this.ont
                .createAnyOrder(new URI(BASE_URL + any.getID()));
        for (int i = 0; i < any.getComponents().length; i++) {
            Object obj = this.getOntology(any.getComponents()[i]);
            anyOrder.addComponent((ControlConstruct) obj);
        }
        return anyOrder;
    }

    private Object createSplitJoin(JSplitJoin sp) throws Exception {
        SplitJoin splitjoin = this.ont.createSplitJoin(new URI(BASE_URL
                + sp.getID()));
        for (int i = 0; i < sp.getComponents().length; i++) {
            Object obj = this.getOntology(sp.getComponents()[i]);
            splitjoin.addComponent((ControlConstruct) obj);
        }
        return splitjoin;
    }

    private Object createChoice(JChoice cho) throws Exception {
        Choice choice = this.ont.createChoice(new URI(BASE_URL + cho.getID()));
        for (int i = 0; i < cho.getComponents().length; i++) {
            Object obj = this.getOntology(cho.getComponents()[i]);
            choice.addComponent((ControlConstruct) obj);
        }
        return choice;
    }

    private Object createSequence(JSequence seq) throws Exception {
        Sequence sequence = this.ont
                .createSequence(new URI(BASE_URL + seq.getID()));
        for (int i = 0; i < seq.getComponents().length; i++) {
            Object obj = this.getOntology(seq.getComponents()[i]);
            sequence.addComponent((ControlConstruct) obj);
        }
        return sequence;
    }

    private Object createPerform(JPerform per) throws Exception {
        Perform perform = this.ont.createPerform(new URI(BASE_URL + per.getID()));
        Object process = this.getOntology(per.getProcess());
        if (process != null) {
            perform.setProcess((AtomicProcess) process);
        }

        if (per.getInputBinding() != null) {
            for (int i = 0; i < per.getInputBinding().length; i++) {
                JInputBinding binding = (JInputBinding) model.get(per
                        .getInputBinding()[i]);
                Object input = this.getOntology(binding.getToParam());
                Object pf = this.getOntology(binding.getFromProcess());

                Object param = this.getOntology(binding.getTheVar());
                if (input != null && pf != null && param != null) {
                    perform.addBinding((Input) input, (Perform) pf,
                            (Parameter) param);
                }
            }
        }
        return perform;
    }

    private Object createAtomicProcess(JAtomicProcess ato) throws Exception {
        AtomicProcess atomic = this.ont.createAtomicProcess(new URI(BASE_URL
                + ato.getID()));
        for (int i = 0; i < ato.getInput().length; i++) {
            atomic.addInput((Input) this.getOntology(ato.getInput()[i]));
        }
        for (int i = 0; i < ato.getOutput().length; i++) {
            atomic.addOutput((Output) this.getOntology(ato.getOutput()[i]));
        }
        WSDLAtomicGrounding grounding = ont.createWSDLAtomicGrounding();
        for (int i = 0; i < ato.getInputMessageMap().length; i++) {
            grounding.addInputMap((MessageMap) this.getOntology(ato
                    .getInputMessageMap()[i]));
        }
        for (int i = 0; i < ato.getOutputMessageMap().length; i++) {
            grounding.addOutputMap((MessageMap) this.getOntology(ato
                    .getOutputMessageMap()[i]));
        }

        WSDLOperationRef operationRef = ont.createWSDLOperationRef();
        operationRef.setOperation(new URI(ato.getOperation()));
        grounding.setOperationRef(operationRef);
        grounding.setProcess(atomic);
        atomic.setGrounding(grounding);
        this.groundings.add(grounding);

        //添加condtion 15-3-22
        try {
            JPreConditionList conditionList = ato.getConditionList();
            if (conditionList != null) {
                bindPreCondition(atomic, conditionList.getConditions());
            }
        } catch (Exception e) {
            e.printStackTrace();
            //todo log
        }
        //添加result 15-3-22
        //todo 绑定输出和perform    result.addBinding( outputBookstore, ComparePrices, CP_Bookstore );
        try {
            bindResult(atomic, ato.getResults());
        } catch (Exception e) {
            e.printStackTrace();
            //todo  log
        }
        return atomic;
    }

    private void bindResult(AtomicProcess atomic, List<JResult> jResult) throws URISyntaxException {
        if (jResult == null || jResult.size() == 0) {
            return;
        }
        for (JResult resultjson : jResult) {
            Result result = atomic.createResult();
            for (int i = 0; i < resultjson.getEffects().size(); i++) {
                JEffect effect = resultjson.getEffects().get(i);
                AtomList list = swrl.createList();
                for (JAtom atom : effect.getAtoms()) {
                    OWLDataProperty property = ont.createDataProperty(URIUtils.createURI(new URI(CONCEPT_URL), atom.getPredicate()));
                    List<JAtom.argument> arguments = atom.getArguments();
                    if (arguments != null && arguments.size() > 1) {
                        Local local1 = atomic.createLocal(URIUtils.createURI(new URI(BASE_URL), arguments.get(0).getName()), ont.getDataType(URIUtils.createURI(XSD.getURI(), atom.getPredicate())));
                        Local local2 = atomic.createLocal(URIUtils.createURI(new URI(BASE_URL), arguments.get(0).getName()), ont.getDataType(URIUtils.createURI(XSD.getURI(), atom.getPredicate())));
                        Atom atom1 = swrl.createDataPropertyAtom(property, local1, local2);
                        list = list.add(atom1);
                    }
                }
                if (list.size() > 0) {
                    Expression expression = ont.createSWRLExpression();
                    expression.setBody(list);
                    result.setEffect(expression);
                }
            }
            atomic.addResult(result);
        }
    }

    private void bindPreCondition(AtomicProcess atomic, List<JPreCondition> precondition) throws URISyntaxException {
        if (precondition == null || precondition.size() == 0) {
            return;
        }
        for (JPreCondition preCondition : precondition) {
            AtomList list = swrl.createList();
            for (JAtom atom : preCondition.getAtoms()) {
                OWLDataProperty property = ont.createDataProperty(URIUtils.createURI(new URI(CONCEPT_URL), atom.getPredicate()));
                List<JAtom.argument> arguments = atom.getArguments();
                if (arguments != null && arguments.size() > 1) {
                    Local local1 = atomic.createLocal(URIUtils.createURI(new URI(BASE_URL), arguments.get(0).getName()), ont.getDataType(URIUtils.createURI(XSD.getURI(), atom.getPredicate())));
                    Local local2 = atomic.createLocal(URIUtils.createURI(new URI(BASE_URL), arguments.get(1).getName()), ont.getDataType(URIUtils.createURI(XSD.getURI(), atom.getPredicate())));
                    Atom atom1 = swrl.createDataPropertyAtom(property, local1, local2);
                    list = list.add(atom1);
                }
            }
            if (list.size() > 0) {
                Condition condition = ont.createSWRLCondition();
                condition.setBody(list);
                atomic.addCondition(condition);
            }
        }
    }


    private Object creatInputMessageMap(JInputMessageMap im) throws Exception {
        MessageMap inputmessagemap = ont.createWSDLInputMessageMap(new URI(BASE_URL
                + im.getID()));

        inputmessagemap.setGroundingParameter(im.getGroundingParameter());
        inputmessagemap.setOWLSParameter((Parameter) this.getOntology(im
                .getOwlsParameter()));
        return inputmessagemap;
    }

    private Object creatOutputMessageMap(JOutputMessageMap im) throws Exception {
        MessageMap outputmessagemap = ont.createWSDLOutputMessageMap(new URI(
                BASE_URL + im.getID()));
        outputmessagemap.setGroundingParameter(im.getGroundingParameter());
        outputmessagemap.setOWLSParameter((Parameter) this.getOntology(im
                .getOwlsParameter()));
        return outputmessagemap;
    }

    private Object createProduce(JProduce p) throws Exception {
        Produce produce = ont.createProduce(new URI(BASE_URL + p.getID()));
        if (p.getOutputBinding() != null) {
            for (int i = 0; i < p.getOutputBinding().length; i++) {
                JOutputBinding binding = (JOutputBinding) model.get(p
                        .getOutputBinding()[i]);
                Object output = this.getOntology(binding.getToParam());
                Object pf = this.getOntology(binding.getFromProcess());
                Object param = this.getOntology(binding.getTheVar());
                if (output != null && pf != null && param != null) {
                    produce.addBinding((Output) output, (Perform) pf,
                            (Parameter) param);
                }
            }
        }
        return produce;
    }

    public boolean write(OutputStream out) {
        try {
            String root = model.getCompositeProcess().getComposeOf();
            this.ont = OWLFactory.createOntology(new URI(OWLSWriter.BASE_URL));
            swrl = SWRLFactoryCreator.createFactory();
            bulidCompositeProcess(out, root);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private void bulidCompositeProcess(OutputStream out, String root) throws Exception {
        CompositeProcess process = ont.createCompositeProcess(new URI(BASE_URL
                + this.model.getCompositeProcess().getID()));
        Service service = ont.createService();
        Profile profile = ont.createProfile();
        Grounding grounding = ont.createGrounding();
        Object construct = this.getOntology(root);
        if (construct != null) {
            process.setComposedOf((ControlConstruct) construct);
        }
        for (int i = 0; i < this.model.getCompositeProcess().getInput().length; i++) {
            Object obj = this.getOntology(this.model.getCompositeProcess()
                    .getInput()[i]);
            if (obj != null && obj instanceof Input) {
                process.addInput((Input) obj);
            }
        }
        for (int i = 0; i < this.model.getCompositeProcess().getOutput().length; i++) {
            Object obj = this.getOntology(this.model.getCompositeProcess()
                    .getOutput()[i]);
            if (obj != null && obj instanceof Output) {
                process.addOutput((Output) obj);
            }
        }
        for (int i = 0; i < this.groundings.size(); i++) {
            grounding.addGrounding(this.groundings.get(i));
        }
        service.setProfile(profile);
        service.setProcess(process);
        service.setGrounding(grounding);
        ont.write(out);

        ont.getKB().unload(ont);
    }

    public static void main(String args[]) throws Exception {
        Gson gson = new Gson();
        File file = new File("d:\\process1.json");
        JModel model = gson.fromJson(new FileReader(file), JModel.class);

        new OWLSWriter(model).write(System.out);
    }
}
