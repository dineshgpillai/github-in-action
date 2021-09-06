package com.db.code.examples.sampledemo.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLParser;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.SafeConstructor;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

public class YamlParser {

    public static void main (String []args) throws Exception{
        YAMLFactory yaml = new YAMLFactory();
        ObjectMapper mapper = new ObjectMapper(yaml);

        YAMLParser yamlParser = yaml.createParser("file-with-multiple-docs.yaml");
        List<JsonNode> docs = mapper.readValues(yamlParser, new TypeReference<JsonNode>() {
        }).readAll();

        docs.forEach(System.out::println);
        System.out.println(docs);
//                .readValues<ObjectNode>(yamlParser, new TypeReference<ObjectNode> {})
//      .readAll();

        try (InputStream input = new FileInputStream("/Users/dineshpillai/github-in-action/src/main/resources/file-with-multiple-docs.yaml")) {
            Yaml yaml2 = new Yaml(new SafeConstructor());
            yaml2.loadAll(input).forEach( System.out::println );

        } catch (Throwable e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }
}
