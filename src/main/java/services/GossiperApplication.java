package services;


import io.dropwizard.Application;
import io.dropwizard.configuration.EnvironmentVariableSubstitutor;
import io.dropwizard.configuration.SubstitutingSourceProvider;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import manager.UserManager;
import persistence.DynamoDBManager;


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

        // Resolve ${VAR} references in gossiper.yml from the environment (strict:
        // an unset variable is a startup error, so secrets never fall back to defaults).
        bootstrap.setConfigurationSourceProvider(new SubstitutingSourceProvider(
                bootstrap.getConfigurationSourceProvider(),
                new EnvironmentVariableSubstitutor(true)));

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
   //     DiscoveryLifeCycle myManagedObject = new DiscoveryLifeCycle(Constants.BureauServiceUrl,Constants.BureauServiceType, Constants.BureauServiceHealthUrl);
     //   environment.lifecycle().manage(myManagedObject);


        DynamoDBManager dynamoDBManager = new DynamoDBManager();

        dynamoDBManager.recover();


        environment.jersey().register(resource);
       // environment.healthChecks().register("APIHealthCheck", new AppHealthCheck());

    }

}