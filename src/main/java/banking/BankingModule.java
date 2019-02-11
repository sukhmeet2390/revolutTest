package banking;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;

public class BankingModule extends AbstractModule {

    public static void main(String[] args) throws Exception {
        Injector injector = Guice.createInjector(new BankingModule());
        injector.getInstance(BankingApplication.class).run(args);
    }

    @Override
    protected void configure() {
        bind(BankingApplication.class).asEagerSingleton();
    }
}
