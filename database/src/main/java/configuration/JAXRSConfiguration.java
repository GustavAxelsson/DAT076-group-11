package configuration;

import org.eclipse.microprofile.auth.LoginConfig;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import services.*;

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
        classes.add(ProductService.class);
        classes.add(CategoryService.class);
        classes.add(CorsFilter.class);
        classes.add(AuthService.class);
        classes.add(UserService.class);
        classes.add(SaleService.class);
        classes.add(OrderService.class);
        return classes;
    }
}
