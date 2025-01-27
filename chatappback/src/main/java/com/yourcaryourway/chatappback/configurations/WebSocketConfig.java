package com.yourcaryourway.chatappback.configurations;

import com.yourcaryourway.chatappback.security.services.TokenService;
import com.yourcaryourway.chatappback.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
@Order(Ordered.HIGHEST_PRECEDENCE + 99)
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    private final TokenService jwtTokenUtil;

    private final UserService userDetailsService;

    @Autowired
    public WebSocketConfig(TokenService jwtTokenUtil, UserService userDetailsService) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.userDetailsService = userDetailsService;
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic"); // Topic for broadcasting messages
        config.setApplicationDestinationPrefixes("/app"); // Prefix for app messages
    }

    /*
    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
// We have to register an authorizationInterceptor that will be responsible for
// validating access-token and user access rights for any incoming CONNECTION
// attempts.
        registration.interceptors(new ChannelInterceptor() {
            @Override
            public Message<?> preSend(Message<?> message, MessageChannel channel) {
                StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);

                if (StompCommand.CONNECT.equals(accessor.getCommand())) {

                    String authorizationHeader = accessor.getFirstNativeHeader("X-Authorization");
                    String token = authorizationHeader.substring(7);

                    String username = jwtTokenUtil.decodeTokenUsername(token);
                    UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

                    accessor.setUser(usernamePasswordAuthenticationToken);
                }
                return message;
                //return MessageBuilder.createMessage(message.getPayload(), accessor.getMessageHeaders());
            }
        });
    }
*/


    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/chat-websocket").setAllowedOriginPatterns("http://localhost:*").withSockJS();
    }
}

