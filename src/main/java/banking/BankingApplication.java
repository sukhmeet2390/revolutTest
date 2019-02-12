package banking;


import banking.api.controller.impl.AccountResourceImpl;
import banking.api.controller.impl.ProfileResourceImpl;
import banking.api.controller.impl.TransactionResourceImpl;
import com.google.inject.Guice;
import com.google.inject.Injector;
import io.dropwizard.Application;
import io.dropwizard.Configuration;
import io.dropwizard.setup.Environment;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class BankingApplication extends Application<Configuration> {
    private final ProfileResourceImpl profileResource;
    private final AccountResourceImpl accountResource;
    private final TransactionResourceImpl transactionResource;

    @Inject
    public BankingApplication(ProfileResourceImpl profileResource,
                              AccountResourceImpl accountResource,
                              TransactionResourceImpl transactionResource) {
        this.profileResource = profileResource;
        this.accountResource = accountResource;
        this.transactionResource = transactionResource;
    }

    @Override // register REST resources here
    public void run(Configuration configuration, Environment environment) throws Exception {
        environment.jersey().register(profileResource);
        environment.jersey().register(accountResource);
        environment.jersey().register(transactionResource);
    }

    public static void main(String[] args) throws Exception {
        Injector injector = Guice.createInjector(new BankingModule());
        injector.getInstance(BankingApplication.class).run(args);
    }
}
