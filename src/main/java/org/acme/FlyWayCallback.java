package org.acme;

import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.callback.Callback;
import org.flywaydb.core.api.callback.Context;
import org.flywaydb.core.api.callback.Event;

import javax.enterprise.inject.spi.CDI;

import java.util.Objects;

import static org.flywaydb.core.api.callback.Event.BEFORE_MIGRATE;

public class FlyWayCallback implements Callback {
    @Override
    public boolean supports(Event event, Context context) {
        return BEFORE_MIGRATE.equals(event);
    }

    @Override
    public boolean canHandleInTransaction(Event event, Context context) {
        return true;
    }

    @Override
    public void handle(Event event, Context context) {
        Flyway flyway = CDI.current().select(Flyway.class).get();
        if (Objects.isNull(flyway.info().current())) {
            flyway.baseline();
        }
    }

    @Override
    public String getCallbackName() {
        return "baseline empty environments";
    }
}
