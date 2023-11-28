package com.piggy.util;

import com.piggy.pojo.WeaponRoot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Paths;

public class ConfigLoader {
    private static final Logger logger = LoggerFactory.getLogger(ConfigLoader.class);

    public static WeaponRoot loadWeaponYml() {
        InputStream inputStream = null;
        WeaponRoot weaponRoot = null;

        try {
            // 尝试加载外部YAML文件
            String jarDir = new File(ConfigLoader.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getParent();
            File externalYamlFile = Paths.get(jarDir, "weapon_data.yml").toFile();
            if (externalYamlFile.exists() && externalYamlFile.isFile()) {
                logger.info("尝试加载外部配置文件...");
                inputStream = new FileInputStream(externalYamlFile);
            } else {
                logger.info("加载默认配置文件...");
                // 如果外部文件不存在，加载资源目录中的YAML文件
                inputStream = ConfigLoader.class.getClassLoader().getResourceAsStream("weapon_data.yml");
            }
            
            // 使用YAML解析库来解析inputStream中的内容
            Yaml yaml = new Yaml();
            weaponRoot = yaml.loadAs(inputStream, WeaponRoot.class);
            logger.info("武器配置加载成功！");
            return weaponRoot;
        } catch (Exception e) {
            logger.error("配置文件加载失败！",e);
            return weaponRoot;
        } finally {

            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
