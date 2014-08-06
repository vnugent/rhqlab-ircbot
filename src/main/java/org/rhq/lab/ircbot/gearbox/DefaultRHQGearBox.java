package org.rhq.lab.ircbot.gearbox;

import java.util.List;

import org.vnguyen.geard.Builders;
import org.vnguyen.geard.Gear;
import org.vnguyen.geard.ServiceEndpoint;

import com.google.common.collect.ImmutableList;

public class DefaultRHQGearBox implements GearBox {
	protected String psqlJSONFile;
	protected String rhqServerJSONFile;
	protected Gear psqlGear;
	protected Gear rhqServerGear;
	protected String prefix = "";
	protected Builders  builders;
	
	public DefaultRHQGearBox(Builders builders) {
		this.builders = builders;
	}
	
	public DefaultRHQGearBox withPrefix(String prefix) {
		this.prefix = prefix;
		return this;
	}
	
	public DefaultRHQGearBox withPSQL(String psql) {
		this.psqlJSONFile = psql;
		return this;
	}
	
	public DefaultRHQGearBox withRHQ(String rhq) {
		this.rhqServerJSONFile = rhq;
		return this;
	}
	
	public DefaultRHQGearBox build() {
		psqlGear = builders.fromTemplate(psqlJSONFile)
				.withNamePrefix(prefix)
				.build();


		rhqServerGear = builders.fromTemplate(rhqServerJSONFile)
			.withNamePrefix(prefix + "-jon-")
			.linkTo(psqlGear)
			.build();	
		return this;
	}

	
	public List<ServiceEndpoint> endpoints() {
		System.out.println(psqlGear.endpoints());
		System.out.println(rhqServerGear.endpoints());
		return ImmutableList.<ServiceEndpoint>builder()
								.addAll(psqlGear.endpoints().values())
								.addAll(rhqServerGear.endpoints().values())
								.build();
	}
	
	public String[] toMultilineString() {
		final String psql = "psql " + psqlGear.endpoints().get(5432).toStringSimple();
		final String rhq = "rhq server: " + rhqServerGear.endpoints().get(7080).toStringSimple();
		return new  String[] {psql, rhq};
	}

}
