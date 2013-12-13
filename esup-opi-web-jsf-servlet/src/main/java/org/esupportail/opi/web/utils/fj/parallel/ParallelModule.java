package org.esupportail.opi.web.utils.fj.parallel;

import fj.Effect;
import fj.Unit;
import fj.control.parallel.ParModule;
import fj.control.parallel.Strategy;

import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static fj.Bottom.error;

public final class ParallelModule {
    private ParallelModule() { throw error("forbidden"); }

    private static final int PROCS = Runtime.getRuntime().availableProcessors();

    public static final ExecutorService pool = Executors.newFixedThreadPool(PROCS);

    public static final Strategy<Unit> strategy =
            Strategy.<Unit>executorStrategy(pool).errorStrategy(new Effect<Error>() {
                public void e(Error error) {
                    error.printStackTrace();
                }
            });

    public static final Strategy<Unit> completionStrategy =
            Strategy.<Unit>completionStrategy(new ExecutorCompletionService<Unit>(pool))
            .errorStrategy(new Effect<Error>() {
                public void e(Error error) {
                    error.printStackTrace();
                }
            });

    public static final ParModule parMod = ParModule.parModule(strategy);
}
