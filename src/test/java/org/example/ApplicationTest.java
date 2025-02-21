package org.example;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.ConstVal;
import com.baomidou.mybatisplus.generator.engine.VelocityTemplateEngine;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.test.context.SpringBootTest;
import java.nio.file.Paths;

@SpringBootTest
public class ApplicationTest {
    @Autowired
    private DataSourceProperties ds;

    @Test
    public void generator() {
        // @formatter:off
        FastAutoGenerator.create(ds.getUrl(), ds.getUsername(), ds.getPassword())
            .globalConfig(builder -> builder
                .author("TeaHeart")
                .outputDir(Paths.get(System.getProperty("user.dir")) + "/src/main/java")
                .commentDate("yyyy-MM-dd"))
            .packageConfig(builder -> builder.parent("org.example"))
            .strategyConfig(builder -> builder
                .controllerBuilder()
                .enableRestStyle()
                .serviceBuilder()
                .convertServiceFileName(entityName -> entityName + ConstVal.SERVICE)
                .mapperBuilder()
                .entityBuilder()
                .enableLombok()
            ).templateEngine(new VelocityTemplateEngine())
            .execute();
        // @formatter:on
    }
}
