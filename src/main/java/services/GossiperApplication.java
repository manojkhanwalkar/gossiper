package services;

import constants.Constants;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import util.AppHealthCheck;
import util.DiscoveryLifeCycle;


public class GossiperApplication extends Application<GossiperConfiguration> {
    public static void main(String[] args) throws Exception {
        new GossiperApplication().run(args);
    }

    @Override
    public String getName() {
        return "Mitek Application";
    }

    @Override
    public void initialize(Bootstrap<GossiperConfiguration> bootstrap) {

    //    IDStatusPollManager.getInstance().start();

        // nothing to do yet
    }

    @Override
    public void run(GossiperConfiguration configuration,
                    Environment environment) {
        final GossiperResource resource = new GossiperResource(
                configuration.getTemplate(),
                configuration.getDefaultName()
        );
        DiscoveryLifeCycle myManagedObject = new DiscoveryLifeCycle(Constants.BureauServiceUrl,Constants.BureauServiceType, Constants.BureauServiceHealthUrl);
        environment.lifecycle().manage(myManagedObject);

        environment.jersey().register(resource);
        environment.healthChecks().register("APIHealthCheck", new AppHealthCheck());

    }

}