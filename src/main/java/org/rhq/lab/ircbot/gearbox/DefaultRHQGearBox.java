package org.rhq.lab.ircbot.gearbox;

import java.util.List;

import org.vnguyen.geard.Builders;
import org.vnguyen.geard.EnvVariable;
import org.vnguyen.geard.Environment;
import org.vnguyen.geard.Gear;
import org.vnguyen.geard.ServiceEndpoint;

import com.google.common.collect.ImmutableList;

public class DefaultRHQGearBox implements GearBox {
	protected String creator;
	protected String psqlJSONFile;
	protected String rhqServerJSONFile;
	protected Gear psqlGear;
	protected Gear rhqServerGear;
	protected String prefix = "";
	protected List<EnvVariable> envVars = ImmutableList.<EnvVariable>of();
	protected Builders  builders;
	
	public DefaultRHQGearBox(Builders builders) {
		this.builders = builders;
	}
	
	@Override
	public DefaultRHQGearBox createdBy(String id) {
		this.creator = id;
		return this;
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
		EnvVariable envVar = new EnvVariable("SERVICE_TAGS", creator);
		Environment env = Environment.create("psql-env", ImmutableList.<EnvVariable>of(envVar));
		psqlGear = builders.fromTemplate(psqlJSONFile)
				.withNamePrefix(creator+"-psql-")
				.withEnvironment(env)
				.build();

		EnvVariable envVar2 = new EnvVariable("RHQ_SERVER_DEBUG", "true");		
		Environment env2 = Environment.create("rhq-server-env", ImmutableList.<EnvVariable>of(envVar2));

		rhqServerGear = builders.fromTemplate(rhqServerJSONFile)
			.withNamePrefix(creator + "-rhq-")
			.withEnvironment(env2)
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

	@Override
	public GearBox withEnv(List<EnvVariable> envs) {
		envVars = envs;
		return this;
	}


}
