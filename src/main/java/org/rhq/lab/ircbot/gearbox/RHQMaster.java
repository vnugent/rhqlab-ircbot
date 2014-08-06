package org.rhq.lab.ircbot.gearbox;

import org.vnguyen.geard.Builders;

public class RHQMaster extends DefaultRHQGearBox {

	public RHQMaster(Builders builders) {
		super(builders);
		String dataDir = System.getProperty("geard.data.dir", ".");
		this.withRHQ(dataDir + "/rhq-master.json");
		this.withPSQL(dataDir + "/rhq-psql.json");
	}
	
	

}
