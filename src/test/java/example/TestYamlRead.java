package example;

import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.Map;

public class TestYamlRead {
    public static void main(String[] args) {
        Yaml yaml = new Yaml();
        InputStream inputStream = TestYamlRead.class.getClassLoader().getResourceAsStream("weapon_data.yml");
        Map<String,Object> load = yaml.load(inputStream);
        System.out.println(load);
    }
}
