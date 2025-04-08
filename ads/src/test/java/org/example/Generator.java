package org.example;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;

import java.nio.file.Paths;

public class Generator {
    public static void main(String[] args) {
        FastAutoGenerator.create("jdbc:mysql:///ads", "root", "root")
                .globalConfig(builder -> builder
                        .outputDir(Paths.get(System.getProperty("user.dir")) + "/src/main/java")
                        .author("teaheart")
                )
                .packageConfig(builder -> builder
                        .parent("org.example")
                )
                .strategyConfig(builder -> {
                    builder.entityBuilder()
                            .enableLombok();
                    builder.controllerBuilder()
                            .enableRestStyle();
                })
                .execute();
    }
}
