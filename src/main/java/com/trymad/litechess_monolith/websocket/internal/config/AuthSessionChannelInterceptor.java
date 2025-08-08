package com.trymad.litechess_monolith.websocket.internal.config;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AuthSessionChannelInterceptor implements ChannelInterceptor {

	private final AuthenticationManager authenticationManager;
	private final JwtDecoder jwtDecoder;

	// for now access only if bearer in header
	@Override
	public Message<?> preSend(Message<?> message, MessageChannel channel) {

		StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

		if(accessor.getCommand() == StompCommand.CONNECT) {
			try {
				final String bearerToken = 
					accessor.getNativeHeader("Authorization").getFirst().substring(7);
				final Jwt jwt = jwtDecoder.decode(bearerToken);
				final Authentication authentication = authenticationManager.authenticate(new JwtAuthenticationToken(jwt));
				accessor.setUser(authentication);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return message;
	}
}