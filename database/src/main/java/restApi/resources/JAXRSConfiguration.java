package restApi.resources;

import org.eclipse.microprofile.auth.LoginConfig;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@LoginConfig(authMethod="MP-JWT")
@ApplicationPath("api")
public class JAXRSConfiguration extends Application {
    /* Intentionally left blank */
}
