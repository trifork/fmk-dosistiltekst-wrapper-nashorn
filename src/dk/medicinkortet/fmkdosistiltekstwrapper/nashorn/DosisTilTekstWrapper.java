package dk.medicinkortet.fmkdosistiltekstwrapper.nashorn;

import java.io.Reader;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.script.Bindings;
import javax.script.Compilable;
import javax.script.CompiledScript;
import javax.script.Invocable;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.script.SimpleBindings;

import com.fasterxml.jackson.core.JsonProcessingException;

import dk.medicinkortet.fmkdosistiltekstwrapper.*;
import dk.medicinkortet.fmkdosistiltekstwrapper.vowrapper.*;
import jdk.nashorn.api.scripting.JSObject;
import jdk.nashorn.api.scripting.ScriptObjectMirror;

public class DosisTilTekstWrapper {

	public class ConsoleObject {
		public void log(String level, String msg) {
			System.out.println(msg);
		}
	}
	
	public enum TextOptions {
		BASIC,
		VKA
	}
	
	private static ScriptEngine engine = null;
	private static CompiledScript script;
	private static Invocable invocable;
	private static Object combinedTextConverterObj;
	private static Object shortTextConverterObj;
	private static Object longTextConverterObj;
	private static Object dosageTypeCalculatorObj;
	private static Object dosageTypeCalculator144Obj;
	private static Object dailyDosisCalculatorObj;
	private static Object dosageProposalXMLGeneratorObj;
	
	public static void initialize(Reader javascriptFileReader) throws ScriptException {
			ScriptEngineManager factory = new ScriptEngineManager();
			engine = factory.getEngineByName("JavaScript");
			
			Compilable compilingEngine = (Compilable) engine;
			
			Bindings bindings = new SimpleBindings();
			bindings.put("console", new DosisTilTekstWrapper().new ConsoleObject());
			engine.setBindings(bindings, ScriptContext.GLOBAL_SCOPE);
			
			script = compilingEngine.compile(javascriptFileReader);
			script.eval();
			
			combinedTextConverterObj = engine.eval("dosistiltekst.CombinedTextConverter");
			longTextConverterObj = engine.eval("dosistiltekst.Factory.getLongTextConverter()");
			shortTextConverterObj = engine.eval("dosistiltekst.Factory.getShortTextConverter()");
			dosageTypeCalculatorObj = engine.eval("dosistiltekst.DosageTypeCalculator");
			dosageTypeCalculator144Obj = engine.eval("dosistiltekst.DosageTypeCalculator144");
			dailyDosisCalculatorObj = engine.eval("dosistiltekst.DailyDosisCalculator");
			dosageProposalXMLGeneratorObj = engine.eval("dosistiltekst.DosageProposalXMLGenerator");
			invocable = (Invocable) engine;
	}
	
	private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

	public static DosageProposalResult getDosageProposalResult(String type, String iteration, String mapping, String unitTextSingular, String unitTextPlural, String supplementaryText, List<Date> beginDates, List<Date> endDates, FMKVersion version, int dosageProposalVersion, Integer shortTextMaxLength) {
		if(engine == null) {
			throw new RuntimeException("DosisTilTekstWrapper not initialized - call initialize() method before invoking any of the methods");
		}
		
		ScriptObjectMirror res = null;
		
		try {
			JSObject arrayConstructor = (JSObject)engine.eval("Array");
			JSObject beginDateArray = (JSObject)arrayConstructor.newObject(null);
			JSObject endDateArray = (JSObject)arrayConstructor.newObject(null);
			
			JSObject dateConstructor = (JSObject) engine.eval("Date");
			for(int i = 0; i < beginDates.size(); i++) {
				Object beginDateJS = dateConstructor.newObject(new Double(beginDates.get(i).getTime()));
				((Invocable)engine).invokeMethod(beginDateArray, "push", beginDateJS);
				Object endDateJS = endDates.get(i) != null ? dateConstructor.newObject(new Double(endDates.get(i).getTime())) : null;
				((Invocable)engine).invokeMethod(endDateArray, "push", endDateJS);
			 }
			    
			 if(shortTextMaxLength != null) {
				 res = (ScriptObjectMirror) invocable.invokeMethod(dosageProposalXMLGeneratorObj, "generateXMLSnippet", type, iteration, mapping, unitTextSingular, unitTextPlural, supplementaryText, beginDateArray, endDateArray, version.toString(), dosageProposalVersion, shortTextMaxLength.intValue());
			 }
			 else {
				 res = (ScriptObjectMirror) invocable.invokeMethod(dosageProposalXMLGeneratorObj, "generateXMLSnippet", type, iteration, mapping, unitTextSingular, unitTextPlural, supplementaryText, beginDateArray, endDateArray, version.toString(), dosageProposalVersion);
			 }
		} catch (ScriptException e) {
			e.printStackTrace();
			throw new RuntimeException("ScriptException in DosisTilTekstWrapper.getDosageProposalResult()", e);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
			throw new RuntimeException("NoSuchMethodException in DosisTilTekstWrapper.getDosageProposalResult()", e);
		} 
		
		if(res == null) {
			return null;
		}
		
		String shortText = (String)res.callMember("getShortDosageTranslation");
		String longText = (String)res.callMember("getLongDosageTranslation");
		String xml = (String)res.callMember("getXml");
		
		return new DosageProposalResult(xml, shortText, longText);
	}	

	public static DosageProposalResult getDosageProposalResult(String type, String iteration, String mapping, String unitTextSingular, String unitTextPlural, String supplementaryText, List<Date> beginDates, List<Date> endDates, FMKVersion version, int dosageProposalVersion) {
		return getDosageProposalResult(type, iteration, mapping, unitTextSingular, unitTextPlural, supplementaryText, beginDates, endDates, version, dosageProposalVersion, null);
	}
	
	public static DosageTranslationCombined convertCombined(DosageWrapper dosage) {
		return convertCombined(dosage, TextOptions.BASIC);
	}
	
	public static DosageTranslationCombined convertCombined(DosageWrapper dosage, TextOptions options) {
		
		if(engine == null) {
			throw new RuntimeException("DosisTilTekstWrapper not initialized - call initialize() method before invoking any of the methods");
		}
				
		String json = "(unset)";
		ScriptObjectMirror res;
		try {
			json = JSONHelper.toJsonString(dosage);
	        res = (ScriptObjectMirror) invocable.invokeMethod(combinedTextConverterObj, "convertStr", json, options.toString());
		} catch (ScriptException e) {
			e.printStackTrace();
			throw new RuntimeException("ScriptException in DosisTilTekstWrapper.convertCombined() with json " + json, e);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
			throw new RuntimeException("NoSuchMethodException in DosisTilTekstWrapper.convertCombined() with json " + json, e);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			throw new RuntimeException("JsonProcessingException inDosisTilTekstWrapper.convertCombined() with json " + json, e);
		}
		
		if(res == null) {
			return new DosageTranslationCombined(new DosageTranslation(null, null, new DailyDosis()), new LinkedList<DosageTranslation>());
		}
		
		String combinedShortText = (String)res.get("combinedShortText");
		String combinedLongText = (String)res.get("combinedLongText");
		
		DailyDosis combinedDD = getDailyDosisFromJS((ScriptObjectMirror)res.get("combinedDailyDosis"));
		ScriptObjectMirror  periodTexts = (ScriptObjectMirror)res.get("periodTexts");
		ScriptObjectMirror[] periodTextArray = periodTexts.to(ScriptObjectMirror[].class);
		LinkedList<DosageTranslation> translations = new LinkedList<DosageTranslation>(); 
		for(ScriptObjectMirror periodText: periodTextArray) {
			translations.add(new DosageTranslation((String)periodText.getSlot(0), (String)periodText.getSlot(1), getDailyDosisFromJS((ScriptObjectMirror) periodText.getSlot(2))));
		}
		return new DosageTranslationCombined(new DosageTranslation(combinedShortText, combinedLongText, combinedDD), translations);
	}
	
	public static String convertLongText(DosageWrapper dosage) {
		return convertLongText(dosage, TextOptions.BASIC);
	}
	
	public static String convertLongText(DosageWrapper dosage, TextOptions options) {
		
		if(engine == null) {
			throw new RuntimeException("DosisTilTekstWrapper not initialized - call initialize() method before invoking any of the methods");
		}
		
		String json = "(unset)";
		Object res;
		try {
			json = JSONHelper.toJsonString(dosage);
	        res = invocable.invokeMethod(longTextConverterObj, "convertStr", json, options.toString());

		} catch (ScriptException e) {
			e.printStackTrace();
			throw new RuntimeException("ScriptException in DosisTilTekstWrapper.convertLongText() with json " + json, e);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
			throw new RuntimeException("NoSuchMethodException in DosisTilTekstWrapper.convertLongText() with json " + json, e);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			throw new RuntimeException("JsonProcessingException in DosisTilTekstWrapper.convertLongText() with json " + json, e);
		}
		
		return (String)res;
	}
	
	public static String convertShortText(DosageWrapper dosage) {
		return convertShortText(dosage, TextOptions.BASIC);
	}
	
	public static String convertShortText(DosageWrapper dosage, TextOptions options) {
		
		if(engine == null) {
			throw new RuntimeException("DosisTilTekstWrapper not initialized - call initialize() method before invoking any of the methods");
		}
			
		Object res;
		String json = "(unset)";
		try {
			json = JSONHelper.toJsonString(dosage);
	        res = invocable.invokeMethod(shortTextConverterObj, "convertStr", json, options);
		} catch (ScriptException e) {
			e.printStackTrace();
			throw new RuntimeException("ScriptException in DosisTilTekstWrapper.convertShortText() with json " + json, e);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
			throw new RuntimeException("NoSuchMethodException in DosisTilTekstWrapper.convertShortText() with json " + json, e);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			throw new RuntimeException("JsonProcessingException in DosisTilTekstWrapper.convertShortText() with json " + json, e);
		}
		
		return (String)res;
	}
	
	public static String convertShortText(DosageWrapper dosage, int maxLength) {
	
		if(engine == null) {
			throw new RuntimeException("DosisTilTekstWrapper not initialized - call initialize() method before invoking any of the methods");
		}
		
		String json = "(unset)";
		Object res;
		try {
			json = JSONHelper.toJsonString(dosage);
	        res = invocable.invokeMethod(shortTextConverterObj, "convertStr", json, maxLength);

		} catch (ScriptException e) {
			e.printStackTrace();
			throw new RuntimeException("ScriptException in DosisTilTekstWrapper.convertShortText() with json " + json, e);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
			throw new RuntimeException("NoSuchMethodException in DosisTilTekstWrapper.convertShortText() with json " + json, e);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			throw new RuntimeException("JsonProcessingException in DosisTilTekstWrapper.convertShortText() with json " + json, e);
		}
		
		return (String)res;
	}
	
	public static String getShortTextConverterClassName(DosageWrapper dosage, int maxLength) {
		
		if(engine == null) {
			throw new RuntimeException("DosisTilTekstWrapper not initialized - call initialize() method before invoking any of the methods");
		}
		
		String json = "(unset)";
		Object res;
		try {
			json = JSONHelper.toJsonString(dosage);
			res = engine.eval("dosistiltekst.Factory.getShortTextConverter().getConverterClassName(" + json + "," + maxLength + ")");
		} catch (ScriptException e) {
			e.printStackTrace();
			throw new RuntimeException("ScriptException in getShortTextConverterClassName()", e);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			throw new RuntimeException("JsonProcessingException in DosisTilTekstWrapper.getShortTextConverterClassName() with json " + json, e);
		}
		
		return (String)res;
	}
	
	public static String getShortTextConverterClassName(DosageWrapper dosage) {
		
		if(engine == null) {
			throw new RuntimeException("DosisTilTekstWrapper not initialized - call initialize() method before invoking any of the methods");
		}
		
		String json = "(unset)";
		Object res;
		try {
			json = JSONHelper.toJsonString(dosage);
			res = engine.eval("dosistiltekst.Factory.getShortTextConverter().getConverterClassName(" + json + ")");
		} catch (ScriptException e) {
			e.printStackTrace();
			throw new RuntimeException("ScriptException in getShortTextConverterClassName()", e);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			throw new RuntimeException("JsonProcessingException in DosisTilTekstWrapper.getShortTextConverterClassName() with json " + json, e);
		}
		
		return (String)res;
	}
	
	public static String getLongTextConverterClassName(DosageWrapper dosage) {
		if(engine == null) {
			throw new RuntimeException("DosisTilTekstWrapper not initialized - call initialize() method before invoking any of the methods");
		}
		
		String json = "(unset)";
		Object res;
		try {
			json = JSONHelper.toJsonString(dosage);
			res = engine.eval("dosistiltekst.Factory.getLongTextConverter().getConverterClassName(" + json + ")");
		} catch (ScriptException e) {
			e.printStackTrace();
			throw new RuntimeException("ScriptException in getLongTextConverterClassName()", e);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			throw new RuntimeException("JsonProcessingException in DosisTilTekstWrapper.getLongTextConverterClassName() with json " + json, e);
		}
		
		return (String)res;
	}
	
	public static String getLongTextConverterClassName(DosageWrapper dosage, int maxLength) {
		if(engine == null) {
			throw new RuntimeException("DosisTilTekstWrapper not initialized - call initialize() method before invoking any of the methods");
		}
		
		String json = "(unset)";
		Object res;
		try {
			json = JSONHelper.toJsonString(dosage);
			res = engine.eval("dosistiltekst.Factory.getLongTextConverter().getConverterClassName(" + json + "," + maxLength + ")");
		} catch (ScriptException e) {
			e.printStackTrace();
			throw new RuntimeException("ScriptException in getLongTextConverterClassName()", e);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			throw new RuntimeException("JsonProcessingException in DosisTilTekstWrapper.getLongTextConverterClassName() with json " + json, e);
		}
		
		return (String)res;
	}
	
		
	public static DosageType getDosageType(DosageWrapper dosage) {
				
		if(engine == null) {
			throw new RuntimeException("DosisTilTekstWrapper not initialized - call initialize() method before invoking any of the methods");
		}
		
		String json = "(unset)";
		Object res;
		try {
			json = JSONHelper.toJsonString(dosage);
	        res = invocable.invokeMethod(dosageTypeCalculatorObj, "calculateStr", json);
		} catch (ScriptException e) {
			e.printStackTrace();
			throw new RuntimeException("ScriptException in getDosageType()", e);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
			throw new RuntimeException("NoSuchMethodException in getDosageType()", e);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			throw new RuntimeException("JsonProcessingException in DosisTilTekstWrapper.getDosageType() with json " + json, e);
		}
		
		return DosageType.fromInteger((Integer)res);
	}
	
	public static DosageType getDosageType144(DosageWrapper dosage) {
		
		if(engine == null) {
			throw new RuntimeException("DosisTilTekstWrapper not initialized - call initialize() method before invoking any of the methods");
		}
		
		String json = "(unset)";
		Object res;
		try {
			json = JSONHelper.toJsonString(dosage);
	        res = invocable.invokeMethod(dosageTypeCalculator144Obj, "calculateStr", json);
		} catch (ScriptException e) {
			e.printStackTrace();
			throw new RuntimeException("ScriptException in getDosageType()", e);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
			throw new RuntimeException("NoSuchMethodException in getDosageType()", e);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			throw new RuntimeException("JsonProcessingException in DosisTilTekstWrapper.getDosageType144() with json " + json, e);
		}
		
		return DosageType.fromInteger((Integer)res);
	}
	
	public static DailyDosis calculateDailyDosis(DosageWrapper dosage) {
		
		if(engine == null) {
			throw new RuntimeException("DosisTilTekstWrapper not initialized - call initialize() method before invoking any of the methods");
		}
		
		ScriptObjectMirror res;
		String json = "(unset)";
		
		try {
			json = JSONHelper.toJsonString(dosage);
	        res = (ScriptObjectMirror)invocable.invokeMethod(dailyDosisCalculatorObj, "calculateStr", json);
		} catch (ScriptException e) {
			e.printStackTrace();
			throw new RuntimeException("ScriptException in calculateDailyDosis()", e);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
			throw new RuntimeException("NoSuchMethodException in calculateDailyDosis()", e);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			throw new RuntimeException("JsonProcessingException in DosisTilTekstWrapper.calculateDailyDosis() with json " + json, e);
		}
		
		return getDailyDosisFromJS(res);
	}
	
	private static DailyDosis getDailyDosisFromJS(ScriptObjectMirror res) {
		ScriptObjectMirror unitObject = (ScriptObjectMirror)res.get("unitOrUnits");
		if(unitObject == null) {
			return new DailyDosis();
		}
		UnitOrUnitsWrapper unitWrapper;
		if(unitObject.get("unit") != null) {
			unitWrapper = UnitOrUnitsWrapper.makeUnit((String)unitObject.get("unit"));
		}
		else {
			unitWrapper = UnitOrUnitsWrapper.makeUnits((String)unitObject.get("unitSingular"), (String)unitObject.get("unitPlural"));
		}
		Object value = res.get("value");
		if(value != null) {
			if(value instanceof Integer) {
				return new DailyDosis(BigDecimal.valueOf((Integer)value), unitWrapper);
			}
			else if(value instanceof Double) {
				return new DailyDosis(BigDecimal.valueOf((double)value), unitWrapper);
			}
			else {
				throw new RuntimeException("Unexpected type of dailydosis value: " + value);
			}
		}
		else {
			ScriptObjectMirror interval = (ScriptObjectMirror)res.get("interval");
			return new DailyDosis(new Interval<BigDecimal>(BigDecimal.valueOf((double)interval.get("minimum")), BigDecimal.valueOf((double)interval.get("maximum"))), unitWrapper);
		}
	}
}