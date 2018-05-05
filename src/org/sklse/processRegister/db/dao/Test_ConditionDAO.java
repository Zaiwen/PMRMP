package org.sklse.processRegister.db.dao;

import org.sklse.processRegister.db.dto.ConditionDTO;
import org.sklse.processRegister.db.services.ConditionService;

/*Created by Zaiwen FENG
 * @7 Nov. 2015*/

public class Test_ConditionDAO {
	public static void main (String args[]) throws Exception{
		
		ConditionService conditionService = ConditionService.instance;
		
		ConditionDTO conditionDTO = conditionService.getRecursiveConditionById(533769028863672401L);
		
	}
}
