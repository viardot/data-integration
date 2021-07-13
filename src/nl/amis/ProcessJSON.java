package nl.amis;

import org.json.JSONObject;
import org.json.JSONArray;

import java.util.Collections;
import java.util.Arrays;

public class ProcessJSON {

  public static Integer[] getNumbers(String JSONString) {
		
    JSONObject jsonObject = new JSONObject(JSONString);
    JSONArray jsonArray   = new JSONArray(jsonObject.getJSONArray("numbers"));
	  
    Integer[] intArray = new Integer[jsonArray.length()];
    for(int i=0; i < jsonArray.length(); i++) {
      intArray[i] = jsonArray.getInt(i);
    }
	  
    Arrays.sort(intArray, Collections.reverseOrder());
	  
    return intArray;
  }
}
