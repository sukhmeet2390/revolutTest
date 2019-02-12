package banking;

import com.google.inject.AbstractModule;

public class BankingModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(BankingApplication.class).asEagerSingleton();
    }
}
