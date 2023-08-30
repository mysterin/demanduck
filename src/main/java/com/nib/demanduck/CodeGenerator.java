package com.nib.demanduck;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.fill.Column;

/**
 * @author linxiaobin
 * @Description 生成代码
 * @date 2023/8/17 19:36
 */
public class CodeGenerator {
    public static void main(String[] args) {
        String username = args.length > 0 ? args[0] : "root";
        String password = args.length > 1 ? args[1] : "root";
        FastAutoGenerator.create("jdbc:mysql://localhost:3306/demanduck?useUnicode=true&characterEncoding=utf8", username, password)
                .globalConfig(builder -> {
                    builder.author("linxiaobin")
                            .commentDate("yyyy-MM-dd hh:mm:ss")
                            .fileOverride()
                            .outputDir("src/main/java")
                            .disableOpenDir();
                })
                .packageConfig(builder -> {
                    builder.parent("com.nib.demanduck")
                            .controller("controller")
                            .service("service")
                            .entity("entity")
                            .mapper("mapper");
                })
                .strategyConfig(builder -> {
                    builder
                            // 表名
                            .addInclude(
                                    "nib_assign_user",
                                    "nib_comment",
                                    "nib_company",
                                    "nib_content",
                                    "nib_demand",
                                    "nib_flaw",
                                    "nib_mission",
                                    "nib_project",
                                    "nib_user",
                                    "nib_user_role")
                            // 前缀
                            .addTablePrefix("nib_")

                            // 实体
                            .entityBuilder()
                            .idType(IdType.ASSIGN_ID)
                            .addTableFills(new Column("deleted", FieldFill.INSERT))
                            .addTableFills(new Column("create_time", FieldFill.INSERT))
                            .addTableFills(new Column("update_time", FieldFill.INSERT_UPDATE))
                            .addTableFills(new Column("create_user", FieldFill.INSERT))
                            .addTableFills(new Column("update_user", FieldFill.INSERT_UPDATE))
                            .enableTableFieldAnnotation()
                            .enableLombok()
                            .enableFileOverride()
                            .logicDeleteColumnName("deleted")

                            // mapper
                            .mapperBuilder()
                            .formatMapperFileName("%sMapper")
                            .enableMapperAnnotation()
//                            .enableFileOverride()

                            // service
                            .serviceBuilder()
                            .formatServiceFileName("%sService")
                            .formatServiceImplFileName("%sServiceImpl")
//                            .enableFileOverride()

                            // controller
                            .controllerBuilder()
                            .formatFileName("%sController")
//                            .enableFileOverride()
                            .enableRestStyle();
                }).templateConfig(builder -> {
                    builder.xml(null);
                }).execute();
    }
}
