package org.rhq.lab.ircbot;

import java.util.Date;

import org.pircbotx.PircBotX;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.MessageEvent;
import org.pircbotx.hooks.events.PrivateMessageEvent;

public class TimeCommand extends ListenerAdapter<PircBotX>  {
        public void onMessage(MessageEvent<PircBotX> event) {
                if (event.getMessage().equals(".time"))
                        event.respond("The current time is " + new Date());
        }

        public void onPrivateMessage(PrivateMessageEvent<PircBotX> event)
                throws Exception {
        	System.out.println(event);
        }
}
