package org.rhq.lab.ircbot.gearbox;

import org.vnguyen.geard.Builders;

public class JON33ER2 extends DefaultRHQGearBox {

	public JON33ER2(Builders builders) {
		super(builders);
		String dataDir = System.getProperty("geard.data.dir", ".");
		this.withRHQ(dataDir + "/jon33er2.json");
		this.withPSQL(dataDir + "/rhq-psql.json");
	}
	
	

}
