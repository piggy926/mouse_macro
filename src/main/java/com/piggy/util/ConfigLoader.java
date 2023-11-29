package com.piggy.util;

import com.piggy.config.MacroConfig;
import com.piggy.controller.MacroController;
import com.piggy.pojo.WeaponRoot;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;
import java.util.concurrent.CompletableFuture;

import static java.nio.file.StandardWatchEventKinds.*;

public class ConfigLoader {
    private static final Logger logger = LoggerFactory.getLogger(ConfigLoader.class);
    // jar包所在目录
    private static String jarDir;


    /**
     * 加载weapon配置文件
     */
    public static void loadWeaponYml() {
        InputStream inputStream = null;

        try {
            // 尝试加载外部YAML文件
            jarDir = new File(ConfigLoader.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getParent();
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
            WeaponRoot weaponRoot = yaml.loadAs(inputStream, WeaponRoot.class);
            logger.info("武器配置加载成功！");
            MacroConfig.weaponRoot = weaponRoot;
            // 武器归零
            MacroController.switchWeapon(NativeKeyEvent.VC_0);
        } catch (Exception e) {
            logger.error("配置文件加载失败！",e);
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


    /**
     * 监控文件变化
     */
    public static void watchFileChange(){
        if (null == jarDir) {
            logger.error("jar所在目录获取失败！");
            return;
        }
        logger.info("开始监控jar目录配置...");
        // 获取文件系统的WatchService对象
        WatchService watcher = null;
        // 监控的目录路径
        Path dir = Paths.get(jarDir);
        // 注册目录与事件
        try {
            watcher = FileSystems.getDefault().newWatchService();
            dir.register(watcher, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);
        } catch (IOException e) {
            logger.error("文件监控服务启动失败: ",e);
        }

        logger.info("当前监控文件夹名称: " + dir.getFileName());

        WatchService finalWatcher = watcher;
        CompletableFuture.runAsync(() -> {
            while (true) {
                if (null == finalWatcher) break;
                // 等待键发生
                WatchKey key;
                try {
                    key = finalWatcher.take();
                } catch (InterruptedException ex) {
                    return;
                }

                // 遍历发生的所有事件
                for (WatchEvent<?> event : key.pollEvents()) {
                    WatchEvent.Kind<?> kind = event.kind();

                    // 事件可能丢失或遗弃
                    if (kind == OVERFLOW) {
                        continue;
                    }

                    // 目录内的变化可能是文件或目录
                    WatchEvent<Path> ev = (WatchEvent<Path>)event;
                    Path fileName = ev.context();
                    if ("weapon_data.yml".equals(fileName.toString())){
                        if ("ENTRY_MODIFY".equals(kind.name()) || "ENTRY_CREATE".equals(kind.name())){
                            logger.info(kind.name() + ": " + fileName);
                            logger.info("已检测到: {}文件更新，重新加载配置",fileName);
                            // 重新加载配置文件
                            loadWeaponYml();
                        }
                    }
                }

                // 重置键并移除已经处理过的事件
                boolean valid = key.reset();
                if (!valid) {
                    break;
                }
            }
            logger.info("jar目录监控事件已结束！");
        });
    }
}
