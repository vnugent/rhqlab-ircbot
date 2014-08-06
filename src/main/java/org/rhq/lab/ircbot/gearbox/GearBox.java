package org.rhq.lab.ircbot.gearbox;

import java.util.List;

import org.vnguyen.geard.ServiceEndpoint;

public interface GearBox {
	public GearBox withPrefix(String prefix);
	public GearBox build();
	public List<ServiceEndpoint> endpoints();
	public String[] toMultilineString();
}
