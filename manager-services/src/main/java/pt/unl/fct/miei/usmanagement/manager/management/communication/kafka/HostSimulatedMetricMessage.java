package pt.unl.fct.miei.usmanagement.manager.management.communication.kafka;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import pt.unl.fct.miei.usmanagement.manager.fields.Field;
import pt.unl.fct.miei.usmanagement.manager.hosts.cloud.CloudHost;
import pt.unl.fct.miei.usmanagement.manager.hosts.edge.EdgeHost;
import pt.unl.fct.miei.usmanagement.manager.metrics.simulated.HostSimulatedMetric;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
public class HostSimulatedMetricMessage {

	private Long id;
	private String name;
	private Field field;
	private double minimumValue;
	private double maximumValue;
	private boolean generic;
	private boolean override;
	private boolean active;
	private Set<CloudHost> cloudHosts;
	private Set<EdgeHost> edgeHosts;
	/*@JsonProperty("isNew")
	private boolean isNew; */

	public HostSimulatedMetricMessage(Long id) {
		this.id = id;
	}

	public HostSimulatedMetricMessage(HostSimulatedMetric hostSimulatedMetric) {
		this.id = hostSimulatedMetric.getId();
		this.name = hostSimulatedMetric.getName();
		this.field = hostSimulatedMetric.getField();
		this.minimumValue = hostSimulatedMetric.getMinimumValue();
		this.maximumValue = hostSimulatedMetric.getMaximumValue();
		this.generic = hostSimulatedMetric.isGeneric();
		this.override = hostSimulatedMetric.isOverride();
		this.active = hostSimulatedMetric.isActive();
		this.cloudHosts = hostSimulatedMetric.getCloudHosts();
		this.edgeHosts = hostSimulatedMetric.getEdgeHosts();
		/*this.isNew = hostSimulatedMetric.isNew();*/
	}

	public HostSimulatedMetric get() {
		HostSimulatedMetric hostSimulatedMetric = HostSimulatedMetric.builder()
			.id(id)
			.name(name)
			.field(field)
			.minimumValue(minimumValue)
			.maximumValue(maximumValue)
			.generic(generic)
			.override(override)
			.active(active)
			.cloudHosts(cloudHosts != null ? cloudHosts : new HashSet<>())
			.edgeHosts(edgeHosts != null ? edgeHosts : new HashSet<>())
			.build();
		/*hostSimulatedMetric.setNew(isNew);*/
		return hostSimulatedMetric;
	}
}
