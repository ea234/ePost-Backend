package de.ea234.epost.model;

import java.time.LocalDateTime;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import java.util.UUID;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

@Component
@Data
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class RequestContext {

  private static final Logger log = LoggerFactory.getLogger( RequestContext.class );

  private String requestId;
  private String userName;
  private LocalDateTime requestTime;

  @PostConstruct
  public void init()
  {
    this.requestId = UUID.randomUUID().toString();

    this.requestTime = LocalDateTime.now();

    log.info( "------------------ Created RequestContext: {}", requestId );
  }

  @PreDestroy
  public void cleanup()
  {
    log.info( "------------------- Destroying RequestContext: {}", requestId );
  }

}
