package com.sysgears.example

import org.springframework.context.annotation.AnnotationConfigApplicationContext
import org.springframework.core.env.MapPropertySource;

/**
 * Main class of the application that uses Spring framework
 * and JSR 330 annotations for dependency injection.
 */
class Main
{
    /**
     * Launches the application.
     * 
     * @param args command line arguments
     */
    public static void main(final String[] args) {

        // Use Spring annotation-based dependency injection
        def ctx = new AnnotationConfigApplicationContext()
       
        // Parse command line parameters and push
        // all the external configuration to application environment 
        def appProps = new MapPropertySource("appProps", [
                greetInfo: new GreetInfo(text: "Hello",
                        name: args.length > 0 ? args[0] : "world")
        ] as Map<String, Object>)

        // Push environment properties to Spring application context 
        ctx.getEnvironment().getPropertySources().addFirst(appProps)

        // Point Spring to IoC configuration of the application
        ctx.register(AppConfig.class)
        
        // Wire dependencies 
        ctx.refresh()
        
        // Register hook to close application context on JVM shutdown
        ctx.registerShutdownHook()
        
        // Launch the application
        def app = ctx.getBean(Application.class)

        app.run()
    }

}