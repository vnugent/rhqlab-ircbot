package org.rhq.lab.ircbot.gearbox;

import org.vnguyen.geard.Builders;

public class JON33 extends DefaultRHQGearBox {

	public JON33(Builders builders) {
		super(builders);
		String dataDir = System.getProperty("geard.data.dir", ".");
		this.withRHQ(dataDir + "/jon33.json");
		this.withPSQL(dataDir + "/rhq-psql.json");
	}
	
	

}
