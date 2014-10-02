package org.rhq.lab.ircbot.gearbox;

import org.vnguyen.geard.Builders;

public class JON33ER4 extends DefaultRHQGearBox {

	public JON33ER4(Builders builders) {
		super(builders);
		String dataDir = System.getProperty("geard.data.dir", ".");
		this.withRHQ(dataDir + "/jon33er4.json");
		this.withPSQL(dataDir + "/rhq-psql.json");
	}
	
	

}
