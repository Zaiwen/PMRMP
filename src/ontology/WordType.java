package ontology;
public class WordType {
	public static final int INNER_DEF = 0;
	public static final int INNER_REF = 1;
	public static final int OUTER = 2;
	public static final int INGOREDATTR = 3;

	public static final int SELFTDEFINED = 0;
	public static final int INGOREDSCHEMA = 1;
	public static final int DEFINEDSCHEMA = 2;

	// 用于定义的Schema名,需要处理它的属性
	private static String[] DefinedSchemaWords = { "Class","Object", "ObjectProperty",
			"subClassOf", "subPropertyOf", "domain", "range",
			"FunctionalProperty", "InverseFunctionalProperty",
			"ObjectProperty", "DatatypeProperty", "DataRange", "first",
			"disjointWith", "allValuesFrom" };

	// 此结点不做任何处理
	private static String[] IngoredSchemaWords = { "label", "comment",
			"versionInfo", "imports", "minCardinality", "maxCardinality",
			"cardinality", "Restriction", "onProperty", "type", "rest",
			"oneOf", "unionOf" };

	// 以下属性名不处理
	private static String[] IngoredAttrWords = { "int", "date", "string",
			"float", "dateTime", "boolean", "Resource", "nil" };

	public static boolean isDefinedSchema(String input) {
		return search(input, DefinedSchemaWords);
	}

	public static boolean isIngoredSchema(String input) {
		return search(input, IngoredSchemaWords);
	}

	private static boolean search(String input, String[] arr) {
		boolean flag = false;
		for (String word : arr) {
			if (input.equals(word)) {
				return true;
			}
		}
		return flag;
	}

	public static int getNodeType(String name) {
		if (isIngoredSchema(name)) {
			return INGOREDSCHEMA;
		} else if (isDefinedSchema(name)) {
			return DEFINEDSCHEMA;
		} else {
			return SELFTDEFINED;
		}
	}

	public static int getAttrType(String word,String ns) {

		if (word.startsWith("#")||word.startsWith(ns)) {// 内部引用
			return INNER_REF;
		} else if (word.indexOf("#") > 0) {// 外部引用
			// &ref;metre等价于http://www.sklse.org/location.owl#metre
			if (search(word.substring(word.indexOf('#') + 1), IngoredAttrWords)) {
				return INGOREDATTR;
			}
			return OUTER;
		} else {
			return INNER_DEF;
		}
	}

	// public static void main(String []s){
	// System.out.println("Resource".substring("Resource".indexOf('#') + 1));
	// }
}

