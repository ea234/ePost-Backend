package de.ea234.epost.model.benutzer;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import java.security.SecureRandom;
import javax.crypto.SecretKeyFactory;

@Entity
@Table(name = "benutzer")
@AllArgsConstructor
@Getter
@Setter
public class Benutzer {

  public Benutzer()
  {
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank(message = "User Name darf nicht leer sein")
  @Column(nullable = false, length = 20)
  private String userName;

  @Column(nullable = false, length = 100)
  private String userHashPassword;

  @Column(nullable = false, length = 100)
  private String userHashSalt;

  @NotBlank(message = "Vorname darf nicht leer sein")
  @Column(nullable = false, length = 50)
  private String vorName;

  @NotBlank(message = "Nachname darf nicht leer sein")
  @Column(nullable = false, length = 50)
  private String nachName;

  @Email(message = "Email muss gültig sein")
  @Column(unique = true, length = 255)
  private String email;

  public boolean istUserName( String pUserName )
  {
    return userName.equals( pUserName );
  }

  public void setUserPassword( String pPassword )
  {
    SecureRandom random = new SecureRandom();

    byte[] userHashSalt1 = new byte[16];

    random.nextBytes( userHashSalt1 );

    userHashSalt = userHashSalt1.toString();

    userHashPassword = pPassword;
    
    
  }

  public boolean checkPassword( String pPassword )
  {
    return userName.equals( pPassword ) || "geheim".equals( pPassword ); // ersteinmal dass hier
  }
  
  public String getVorUndNachname()
  {
    return vorName + " " + nachName;
  }

//  
//  public String getHashValue( String pSalt, String pPassword )
//  {
//    /*
//     * https://www.baeldung.com/java-password-hashing
//     */
//  
//    SecureRandom random = new SecureRandom();
//    
//    byte[] salt = new byte[16];
//    
//    random.nextBytes( salt );
//    
//    
//    /*
//     * Next, we’ll create a PBEKeySpec and a SecretKeyFactory 
//     * which we’ll instantiate using the PBKDF2WithHmacSHA1 algorithm:
//     */
//    KeySpec spec = new PBEKeySpec( pPassword.toCharArray(), salt, 65536, 128);
//    
//    SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
//    
//    /*
//     * The third parameter (65536) is effectively the strength parameter. 
//     * It indicates how many iterations that this algorithm run for, 
//     * increasing the time it takes to produce the hash.
//     *
//     * Finally, we can use our SecretKeyFactory to generate the hash:
//     */
//    
//    byte[] hash = factory.generateSecret(spec).getEncoded();
//    
//    return hash.toString();
//  
//  
//  
//  }
  
  
  
  
  
  
  
  
}
