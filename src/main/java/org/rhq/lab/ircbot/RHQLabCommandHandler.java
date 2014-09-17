package org.rhq.lab.ircbot;

import org.apache.commons.lang3.StringUtils;
import org.pircbotx.PircBotX;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.PrivateMessageEvent;
import org.rhq.lab.ircbot.gearbox.GearBox;
import org.rhq.lab.ircbot.gearbox.JON33;
import org.rhq.lab.ircbot.gearbox.JON33ER1;
import org.rhq.lab.ircbot.gearbox.JON33ER2;
import org.rhq.lab.ircbot.gearbox.JON33ER3;
import org.rhq.lab.ircbot.gearbox.RHQManual;
import org.rhq.lab.ircbot.gearbox.RHQMaster;
import org.vnguyen.geard.Builders;
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
	
	       		case "!jon33er3": 
	       			gearBox = new JON33ER3(builders)
	       								.createdBy(safeNick)
										.build();
	       		break;	       	
	       	
	       		case "!jon33er2": 
	       			gearBox = new JON33ER2(builders)
	       								.createdBy(safeNick)
										.build();
	       		break;
	       	
	       		case "!jon33er1": 
	       			gearBox = new JON33ER1(builders)
	       								.createdBy(safeNick)
										.build();
	       		break;
	       		
	       		case "!jon33dr1": 
		       		gearBox = new JON33(builders)
		       								.createdBy(safeNick)
											.build();
		       		break;
		       		
		       	case "!rhqmaster": 
		       		gearBox = new RHQMaster(builders)
		       								.createdBy(safeNick)
		       								.build();
		       		break;
		       		
		       	default: 
					event.respond("** Welcome to RHQLAB Geard + Docker experimental project **");
					event.respond("   Available commands:  (response time is approximately 2 minutes)");
					event.respond("     !jon33er3");					
					event.respond("     !jon33er2");
					event.respond("     !jon33er1");
					event.respond("     !jon33dr1");
					event.respond("     !rhqmaster");		
					
					return;
	       			
	       		}// switch
 	       
		       	for(String s : gearBox.toMultilineString()) {
		       		event.respond(s);
		       	}	
		       	event.respond("Enjoy!");
       	} catch(Exception e) {
       		event.respond(e.toString());
       		e.printStackTrace();
       	}
	}
    
}
