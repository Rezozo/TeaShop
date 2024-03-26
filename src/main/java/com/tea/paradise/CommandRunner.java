package com.tea.paradise;

import com.tea.paradise.service.InitService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class CommandRunner implements CommandLineRunner {
    InitService initService;
    @Override
    public void run(String... args) {
        initService.initRoles();
        initService.initOrderStatuses();
        initService.initVariants();
    }
}
