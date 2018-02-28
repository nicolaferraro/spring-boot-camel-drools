/**
 *  Copyright 2005-2016 Red Hat, Inc.
 *
 *  Red Hat licenses this file to you under the Apache License, version
 *  2.0 (the "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 *  implied.  See the License for the specific language governing
 *  permissions and limitations under the License.
 */
package io.fabric8.quickstarts.camel.drools;

import org.kie.camel.KieComponent;
import org.kie.camel.KieConfiguration;
import org.openshift.quickstarts.decisionserver.hellorules.Greeting;
import org.openshift.quickstarts.decisionserver.hellorules.Person;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;

import java.util.HashSet;
import java.util.Set;


@SpringBootApplication
@ImportResource({"classpath:spring/camel-context.xml"})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean(name = "kie-component")
    public KieComponent kieComponent(@Value("${kieserver.username}") String username, @Value("${kieserver.password}") String password) {
        KieConfiguration configuration = new KieConfiguration();
        configuration.setUsername(username);
        configuration.setPassword(password);
        configuration.setKieServicesConfigurationCustomizer(serviceConfig -> {
            Set<Class<?>> extraClasses = new HashSet<>();
            extraClasses.add(Greeting.class);
            extraClasses.add(Person.class);
            serviceConfig.addExtraClasses(extraClasses);
            return serviceConfig;
        });

        return new KieComponent(configuration);
    }

}
