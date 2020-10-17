package me.kakaopay.homework.common.lock;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = Lock.class)
public class DistributeLockConfiguration {
}
