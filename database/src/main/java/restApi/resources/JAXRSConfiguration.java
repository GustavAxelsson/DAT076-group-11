package restApi.resources;

import org.eclipse.microprofile.auth.LoginConfig;
import org.glassfish.jersey.media.multipart.MultiPartFeature;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

@LoginConfig(authMethod = "MP-JWT")
@ApplicationPath("api")
public class JAXRSConfiguration extends Application {
    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> classes = new HashSet<>();
        classes.add(MultiPartFeature.class);
        classes.add(ProductResource.class);
        classes.add(CategoryResource.class);
        classes.add(CorsFilter.class);
        classes.add(AuthService.class);
        classes.add(UserService.class);
        return classes;
    }
}
