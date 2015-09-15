package org.rhq.lab.ircbot;

import org.apache.commons.lang3.StringUtils;
import org.pircbotx.PircBotX;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.PrivateMessageEvent;

import com.redhat.jonlab.cli.CmdLineExecutor;


public class RHQLabCommandHandler extends ListenerAdapter<PircBotX> {
	public static final String RHQLAB = "!lab";

	
	@Override
	public void onPrivateMessage(PrivateMessageEvent<PircBotX> event) throws Exception {
		if (StringUtils.isBlank(event.getMessage())) {
			return;
		}
		String userNick = event.getUser().getNick();
		String safeNick = userNick.replaceAll("[^a-zA-Z0-9.-]", "_");
		
		String rhqlabCmd = "-o " + safeNick + " " + event.getMessage();
	
		System.out.println(rhqlabCmd);
		event.respond("Processing your request. Please wait.");
		String[] output = new CmdLineExecutor().execute(rhqlabCmd.split(" "));
		for(String s:output) {
			event.respond(s);
		}
    }
}
