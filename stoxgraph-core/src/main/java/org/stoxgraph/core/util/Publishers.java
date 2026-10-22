package org.stoxgraph.core.util;

import java.util.Objects;
import java.util.concurrent.Flow;
import java.util.function.Function;

public final class Publishers {

    private Publishers() {}

    public static <T, R> Flow.Publisher<R> map(Flow.Publisher<T> source, Function<? super T, ? extends R> mapper) {
        Objects.requireNonNull(source);
        Objects.requireNonNull(mapper);
        return subscriber -> source.subscribe(new MappingSubscriber<>(subscriber, mapper));
    }

    private static final class MappingSubscriber<T, R> implements Flow.Subscriber<T> {

        private final Flow.Subscriber<? super R> downstream;
        private final Function<? super T, ? extends R> mapper;

        private Flow.Subscription subscription;
        private boolean done;

        private MappingSubscriber(Flow.Subscriber<? super R> downstream, Function<? super T, ? extends R> mapper) {
            this.downstream = downstream;
            this.mapper = mapper;
        }

        @Override
        public void onSubscribe(Flow.Subscription subscription) {
            this.subscription = subscription;
            this.downstream.onSubscribe(subscription);
        }

        @Override
        public void onNext(T item) {
            if (!done) {
                try {
                    R mapped = mapper.apply(item);
                    if (mapped == null) {
                        throw new NullPointerException();
                    }

                    downstream.onNext(mapped);
                } catch (Throwable t) {
                    done = true;
                    subscription.cancel();
                    downstream.onError(t);
                }
            }
        }

        @Override
        public void onError(Throwable throwable) {
            if (!done) {
                done = true;
                downstream.onError(throwable);
            }
        }

        @Override
        public void onComplete() {
            if (!done) {
                done = true;
                downstream.onComplete();
            }
        }
    }
}
