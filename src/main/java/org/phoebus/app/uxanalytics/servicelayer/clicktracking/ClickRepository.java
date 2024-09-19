package org.phoebus.app.uxanalytics.servicelayer.clicktracking;

import org.phoebus.app.uxanalytics.servicelayer.clicktracking.ClickBean;
import org.springframework.data.repository.CrudRepository;


//CRUD operations are implemented automagically by Spring Data JPA
public interface ClickRepository extends CrudRepository<ClickBean, Integer>{
}
