package com.github.greengerong.app;

import com.github.greengerong.item.ItemService;
import com.github.greengerong.item.ItemServiceImpl1;
import com.github.greengerong.item.ItemServiceImpl2;
import com.github.greengerong.named.NamedService;
import com.github.greengerong.named.NamedServiceImpl1;
import com.github.greengerong.named.NamedServiceImpl2;
import com.github.greengerong.order.OrderService;
import com.github.greengerong.order.OrderServiceImpl;
import com.github.greengerong.price.PriceService;
import com.github.greengerong.runtime.RuntimeService;
import com.github.greengerong.runtime.RuntimeServiceImpl;
import com.google.common.collect.ImmutableList;
import com.google.inject.*;
import com.google.inject.multibindings.Multibinder;
import com.google.inject.name.Named;
import com.google.inject.name.Names;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static com.github.greengerong.app.ExceptionMethodInterceptor.exception;
import static com.google.common.collect.ImmutableList.of;
import static com.google.inject.Scopes.SINGLETON;
import static com.google.inject.matcher.Matchers.any;

/**
 * ***************************************
 * *
 * Auth: green gerong                     *
 * Date: 2014                             *
 * blog: http://greengerong.github.io/    *
 * github: https://github.com/greengerong *
 * *
 * ****************************************
 */
public class AppModule extends AbstractModule {
    private static final Logger LOGGER = LoggerFactory.getLogger(AppModule.class);
    private final RuntimeServiceImpl runtimeService;

    public AppModule(RuntimeServiceImpl runtimeService) {
        this.runtimeService = runtimeService;
    }

    @Override
    public void configure() {
        final Binder binder = binder();
        if (LOGGER.isDebugEnabled()) {
            binder.bindInterceptor(any(), any(), exception());
        }
        //TODO: bind interface
        binder.bind(OrderService.class).to(OrderServiceImpl.class).in(SINGLETON);
        //TODO: bind self class(without interface or base class)
        binder.bind(PriceService.class).in(Scopes.SINGLETON);

        //TODO: Multibinder
        final Multibinder<ItemService> itemServiceMultibinder = Multibinder.newSetBinder(binder, ItemService.class);
        itemServiceMultibinder.addBinding().to(ItemServiceImpl1.class);
        itemServiceMultibinder.addBinding().to(ItemServiceImpl2.class);

        //TODO: bind instance not class.
        binder.bind(RuntimeService.class).toInstance(runtimeService);

        //TODO: bind named instance;
        binder.bind(NamedService.class).annotatedWith(Names.named("impl1")).to(NamedServiceImpl1.class);
        binder.bind(NamedService.class).annotatedWith(Names.named("impl2")).to(NamedServiceImpl2.class);
    }

    @Provides
    public List<NamedService> getAllItemServices(@Named("impl1") NamedService nameService1,
                                                 @Named("impl2") NamedService nameService2) {
        return of(nameService1, nameService2);
    }


}
