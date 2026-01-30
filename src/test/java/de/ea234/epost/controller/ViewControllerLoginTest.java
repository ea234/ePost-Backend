package de.ea234.epost.controller;

import de.ea234.epost.model.RequestContext;
import de.ea234.epost.model.benutzer.Benutzer;
import de.ea234.epost.services.ServiceListBenutzer;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.ui.Model;

public class ViewControllerLoginTest {

  private ServiceListBenutzer mockServiceListBenutzer;
  private ViewControllerLogin controller;
  private HttpSession mockSession;
  private Model mockModel;
  private RequestContext fakeContext;

  @BeforeEach
  void setup()
  {
    mockServiceListBenutzer = Mockito.mock( ServiceListBenutzer.class );
    
    controller = new ViewControllerLogin( mockServiceListBenutzer );
    
    mockSession = Mockito.mock( HttpSession.class );
    
    mockModel = Mockito.mock( Model.class );
    
    fakeContext = new RequestContext();
  }

  @Test
  @DisplayName("Login - Benutzer nicht gefunden setzt loginError")
  void loginUserNotFoundSetsLoginError()
  {
    String userName = "unknown";
    String userPassword = "any";

    Mockito.when( mockServiceListBenutzer.getBenutzerByUserName( userName ) ).thenReturn( null );

    String view = controller.doUserLogIn( userName, userPassword, mockModel, mockSession, fakeContext );

    Assertions.assertEquals( "epost-login", view, "Expected to stay on login page when user not found" );

    Mockito.verify( mockModel ).addAttribute( Mockito.eq( "loginError" ), Mockito.eq( "Benutzer nicht gefunden" ) );
  }

  @Test
  @DisplayName("Login - Kennwort falsch setzt loginError")
  void loginWrongPasswordSetsLoginError()
  {
    String userName = "existing";
    String userPassword = "wrong";

    Benutzer mockBenutzer = Mockito.mock( Benutzer.class );

    Mockito.when( mockServiceListBenutzer.getBenutzerByUserName( userName ) ).thenReturn( mockBenutzer );

    Mockito.when( mockBenutzer.checkPassword( userPassword ) ).thenReturn( false );

    String view = controller.doUserLogIn( userName, userPassword, mockModel, mockSession, fakeContext );

    Assertions.assertEquals( "epost-login", view, "Expected to stay on login page when password wrong" );

    Mockito.verify( mockModel ).addAttribute( Mockito.eq( "loginError" ), Mockito.eq( "Kennwort stimmt nicht" ) );
  }

  @Test
  @DisplayName("Logout setzt loginInfo")
  void logoutSetsLoginInfo()
  {
    String view = controller.doUserLogOut( mockModel, mockSession );

    Assertions.assertEquals( "epost-login", view, "Expected to stay on login page after logout" );

    Mockito.verify( mockModel ).addAttribute( Mockito.eq( "loginInfo" ), Mockito.eq( "Benutzer wurde ausgeloggt" ) );
  }
}
