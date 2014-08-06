package org.rhq.lab.ircbot;

import java.util.ArrayList;
import java.util.List;

import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;
import org.pircbotx.Configuration;
import org.pircbotx.Configuration.Builder;
import org.pircbotx.PircBotX;
import org.pircbotx.hooks.Listener;
import org.pircbotx.hooks.ListenerAdapter;
import org.vnguyen.geard.Builders;
import org.vnguyen.geard.GeardClient;


public class App extends ListenerAdapter<PircBotX> implements Listener<PircBotX> {
	
	@Option(name="-s",usage="irc server", metaVar="string")
	private String ircServer;
	
	@Option(name="-n",usage="irc nick",  metaVar="string")
	private String ircNick="rhqlab-bot";
	
	@Argument(usage="supply a list of irc channels",  metaVar="main parameter")
	protected List<String> ircChannels = new ArrayList<String>();	
	
	public void start() {

		GeardClient geardClient = new GeardClient("http://localhost:43273");
		
    	Builder<PircBotX> config = new Configuration.Builder<PircBotX>()
    	        .setName(ircNick) 
    	        .setAutoNickChange(true)
    	        .setServer(ircServer, 6667)
    	        .addListener(new RHQLabCommandHandler(geardClient));
    	
    	for(String channel:ircChannels) {
    		config.addAutoJoinChannel(channel);
    	}
    	
    	PircBotX myBot = new PircBotX(config.buildConfiguration());
    	    
    	try {
    		myBot.startBot();
    	} catch (Exception e) {
   			e.printStackTrace(); 		
    	}
	}
	
	
    public static void main( String[] args ){

    	App app = new App();
    	CmdLineParser parser = new CmdLineParser(app);
    	try {
			parser.parseArgument(args);
			if (app.ircChannels.isEmpty()) {
				throw new CmdLineException(parser, "Missing irc channel(s)");
			}
	    	app.start();
		} catch (CmdLineException e) {
			System.err.println(e.getMessage());
			System.err.println("Usage: -s <server> -n <nick>  #channel1 #channel2...");
			System.err.println();
		}
    	
    }

}
