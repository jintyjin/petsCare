package com.petsCare.petsCare.websocket;

import com.petsCare.petsCare.response.message.ErrorMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
@RequiredArgsConstructor
public class WebSocketService {

	private final SimpMessagingTemplate template;
	private final MessageSource messageSource;

	public void makeNotice(String url, int count, int totalCount) {
		String message = messageSource.getMessage("notice.memory.adopt.message", new Integer[]{count, totalCount},
				Locale.KOREAN);
		template.convertAndSend(url, message);
	}

	public void exceptionNotice(String url) {
		template.convertAndSend(url, messageSource.getMessage(ErrorMessage.EXCEPTION,null, Locale.KOREAN));
	}
}
