package banking;


import io.dropwizard.Application;
import io.dropwizard.setup.Environment;

import javax.inject.Singleton;

@Singleton
public class BankingApplication extends Application<BankingConfiguration> {

    @Override
    public void run(BankingConfiguration bankingConfiguration, Environment environment) throws Exception {

    }
}
