package banking;


import banking.api.controller.impl.AccountResourceImpl;
import banking.api.controller.impl.ProfileResourceImpl;
import com.google.inject.Guice;
import com.google.inject.Injector;
import io.dropwizard.Application;
import io.dropwizard.setup.Environment;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class BankingApplication extends Application<BankingConfiguration> {

    private final ProfileResourceImpl profileResource;
    private final AccountResourceImpl accountResource;

    @Inject
    public BankingApplication(ProfileResourceImpl profileResource, AccountResourceImpl accountResource) {
        this.profileResource = profileResource;
        this.accountResource = accountResource;
    }

    @Override
    public void run(BankingConfiguration bankingConfiguration, Environment environment) throws Exception {
        environment.jersey().register(profileResource);
        environment.jersey().register(accountResource);
    }

    public static void main(String[] args) throws Exception {
        Injector injector = Guice.createInjector(new BankingModule());
        injector.getInstance(BankingApplication.class).run(args);
    }
}
