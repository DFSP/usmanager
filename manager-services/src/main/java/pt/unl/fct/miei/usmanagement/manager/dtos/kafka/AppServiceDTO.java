package pt.unl.fct.miei.usmanagement.manager.dtos.kafka;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import pt.unl.fct.miei.usmanagement.manager.apps.AppService;
import pt.unl.fct.miei.usmanagement.manager.apps.AppServiceKey;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AppServiceDTO {

	private AppServiceKey id;
	private int launchOrder;
	@JsonManagedReference
	private AppDTO app;
	@JsonManagedReference
	private ServiceDTO service;
	/*@JsonProperty("isNew")
	private boolean isNew;*/

	/*public AppServiceDTO(AppServiceKey id) {
		this.id = id;
	}

	public AppServiceDTO(AppService appService) {
		this.id = appService.getId();
		this.launchOrder = appService.getLaunchOrder();
		this.app = new AppDTO(appService.getApp());
		//this.service = new ServiceDTO(appService.getService());
		*//*this.isNew = app.isNew();*//*
	}

	@JsonIgnore
	public AppService toEntity() {
		AppService appService = AppService.builder()
			.id(id)
			.launchOrder(launchOrder)
			.app(app.toEntity())
			//.service(service.toEntity())
			.build();
		*//*appService.setNew(isNew);*//*
		return appService;
	}*/

	@Override
	public String toString() {
		return "AppServiceDTO{" +
			"id=" + id +
			", launchOrder=" + launchOrder +
			", app=" + (app != null ? app.getId() : "null") +
			", service=" + (service != null ? service.getId() : "null") +
			'}';
	}
}
