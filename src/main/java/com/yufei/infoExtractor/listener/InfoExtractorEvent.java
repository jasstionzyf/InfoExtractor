package com.yufei.infoExtractor.listener;

import java.util.EventObject;

public class InfoExtractorEvent extends EventObject {

	public InfoExtractorEvent(Object source) {
		super(source);
		// TODO Auto-generated constructor stub
	}
	private EventType eventType=null;
	public EventType getEventType() {
		return eventType;
	}
	public void setEventType(EventType eventType) {
		this.eventType = eventType;
	}
}
