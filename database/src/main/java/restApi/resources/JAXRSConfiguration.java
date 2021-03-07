package restApi.resources;

import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.ApplicationPath;
import javax.xml.crypto.OctetStreamData;

@ApplicationPath("api")
public class JAXRSConfiguration extends ResourceConfig {
   public JAXRSConfiguration() {
       packages("restApi.resources");
       packages("restApi.resources").register(MultiPartFeature.class);
       packages("restApi.resources").register(OctetStreamData.class);
   }

    /* Intentionally left blank */
}
