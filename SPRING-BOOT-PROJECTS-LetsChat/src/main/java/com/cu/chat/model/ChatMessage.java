package com.cu.chat.model;




public class ChatMessage {
	
   
	private Long id;
    
    private String from;
    private String text;
    
    
    
	public ChatMessage(String from, String text, Long id) {
		super();
		this.id = id;
		this.from = from;
		this.text = text;
	}
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	
	@Override
	public String toString() {
		return "ChatMessage [id=" + id + ", from=" + from + ", text=" + text + "]";
	}

  
}
