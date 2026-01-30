package de.ea234.epost.config;

import de.ea234.epost.util.FkDatum;
import de.ea234.epost.util.FkZahl;
import jakarta.annotation.PostConstruct;
import java.nio.file.Files;
import java.nio.file.Path;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import lombok.Data;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
@ConfigurationProperties(prefix = "app.epost")
@Data
@Getter
public class EPostConfig {

  private static final Logger log = LoggerFactory.getLogger( EPostConfig.class );

  private String name;
  private String version;
  private String verzeichnis;

  private String verzeichnis_input;
  private String verzeichnis_root;

  private String wfIntervallBackgrounder;
  private String wfIntervallVzUeberwachung;

  private Path pathVerzeichnisInput = null;
  private Path pathVerzeichnisRoot = null;
  private Path pathTagesverzeichnis = null;

  public Path getPathToInput( String pDateiName )
  {
    return pathVerzeichnisInput.resolve( Path.of( pDateiName ) );
  }

  public int getWfIntervallBackgrounder()
  {
    return FkZahl.parseInt( wfIntervallBackgrounder, 30 );
  }

  public int getWfIntervallVzUeberwachung()
  {
    return FkZahl.parseInt( wfIntervallVzUeberwachung, 30 );
  }

  @PostConstruct
  public void initApp()
  {
    log.info( "PostConstruct - Config" );

    log.info( "name " + name );

    log.info( "verzeichnis_input " + verzeichnis_input );

    log.info( "verzeichnis_root  " + verzeichnis_root );

    pathVerzeichnisInput = Path.of( verzeichnis_input );

    pathVerzeichnisRoot = Path.of( verzeichnis_root );

    pathTagesverzeichnis = pathVerzeichnisRoot.resolve( "" + FkDatum.getLong() );

    log.info( "EPost tagesverzeichnis ist " + pathTagesverzeichnis.toString() );

    try
    {
      Files.createDirectories( pathVerzeichnisInput );

      log.info( "path_app_verzeichnis_root erstellt " );
    }
    catch (Exception exp)
    {
      log.info( "fehler verzeichnis erstellung " );
      log.info( "Fehler ist " + exp.getLocalizedMessage() );
    }

    try
    {
      Files.createDirectories( pathVerzeichnisRoot );

      log.info( "path_app_verzeichnis_root erstellt " );
    }
    catch (Exception exp)
    {
      log.info( "fehler verzeichnis erstellung " );
      log.info( "Fehler ist " + exp.getLocalizedMessage() );
    }

    try
    {
      Files.createDirectories( pathTagesverzeichnis );

      log.info( "path_app_verzeichnis_root erstellt " );
    }
    catch (Exception exp)
    {
      log.info( "fehler verzeichnis erstellung " );
      log.info( "Fehler ist " + exp.getLocalizedMessage() );
    }
  }

}
