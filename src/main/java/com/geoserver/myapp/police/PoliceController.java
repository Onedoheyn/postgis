package com.geoserver.myapp.police;

import java.util.List;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping(value = "/police")
public class PoliceController {
	
	@Autowired
	private PoliceDao dao;
	
	// deprecated
	@ResponseBody
	@RequestMapping(value = "/selectAll", method = RequestMethod.GET)
	public String selectAll() {
		
		List<PoliceDto> list = null;
		
		list = dao.selectAll();
		JSONArray jArray = listToJArray(list);
		
		System.out.println("selectAll "+jArray.size());
		return jArray.toJSONString();
	}


	@ResponseBody
	@RequestMapping(value = "/select", method = RequestMethod.GET)
	public String select(@RequestParam("detail") String detail, @RequestParam("area") String area) {
		// detail: field inspurpose
		// area:   field address
		
		List<PoliceDto> list = null;
	//	area = "~ '"+area+"'";
		
		System.out.println("recv "+detail+" "+area);
		
		
		// if both
		if(area!="") {
			System.out.println("if both");
			list = dao.selectBoth(detail, area);
		// if detail only
		}else{
			System.out.println("if detail");
			list = dao.selectPurpose(detail);
		//	list = dao.selectAll(); // temp
		}
		
		
		JSONArray jArray = listToJArray(list);
		System.out.println("select "+jArray.size());
		
		return jArray.toJSONString();
	}
	
	
	
	private JSONArray listToJArray(List<PoliceDto> list) {
		
		JSONArray jArray = new JSONArray();
		
		for (int i=0; i<list.size(); i++) {
			JSONObject jObject = new JSONObject();
			jObject.put("x", list.get(i).getX());
			jObject.put("y", list.get(i).getY());
			jArray.add(jObject);
		}
		
		return jArray;
	}
	
}
