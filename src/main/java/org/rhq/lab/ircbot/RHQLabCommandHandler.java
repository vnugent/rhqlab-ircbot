package org.rhq.lab.ircbot;

import java.net.URISyntaxException;

import org.apache.commons.lang3.StringUtils;
import org.pircbotx.PircBotX;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.PrivateMessageEvent;
import org.rhq.lab.ircbot.gearbox.GearBox;
import org.rhq.lab.ircbot.gearbox.JON33;
import org.rhq.lab.ircbot.gearbox.RHQMaster;
import org.vnguyen.geard.Builders;
import org.vnguyen.geard.GearAPI;
import org.vnguyen.geard.GeardClient;


public class RHQLabCommandHandler extends ListenerAdapter<PircBotX> {
	public static final String RHQLAB = "!lab";

	private GeardClient geard;
	private Builders builders;
	
	public RHQLabCommandHandler(GeardClient geard) {
		this.geard = geard;
		builders = new Builders(geard);
	}
	
	@Override
	public void onPrivateMessage(PrivateMessageEvent<PircBotX> event) throws Exception {
		if (StringUtils.isBlank(event.getMessage())) {
			return;
		}

		String userNick = event.getUser().getNick();
		String safeNick = userNick.replaceAll("[^a-zA-Z0-9.-]", "_");
		
		String rhqlabCmd = "-o " + safeNick + " " + event.getMessage();
	
       	System.out.println(rhqlabCmd);
       	try {
	       	GearBox gearBox;
	       	switch (event.getMessage()) {
		       	case "!jon33": 
		       		gearBox = new JON33(builders)
											.withPrefix(safeNick +"-")
											.build();
		       		break;
		       		
		       	case "!rhqmaster": 
		       		gearBox = new RHQMaster(builders)
		       								.withPrefix(safeNick +"-")
		       								.build();
		       		break;
		       		
		       	default: 
					event.respond("** Welcome to RHQLAB Geard + Docker experimental project **");
					event.respond("   Available commands:  ");
					event.respond("     !jon33  ");
					event.respond("     !rhqmaster  ");		
					return;
	       			
	       		}// switch
 	       
		       	for(String s : gearBox.toMultilineString()) {
		       		event.respond(s);
		       	}	
       	} catch(Exception e) {
       		event.respond(e.toString());
       		System.out.println(e);
       	}
	}
    
}
