package io;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.Operation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Stream;

public class InputHandler {
    private final ObjectMapper mapper;

    public InputHandler(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    public Stream<List<Operation>> readLines() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        return reader.lines()
                .filter(line -> !line.isBlank())
                .map(this::parseLine);
    }

    private List<Operation> parseLine(String line) {
        try {
            return mapper.readValue(line, new TypeReference<>() {});
        } catch (IOException e) {
            throw new RuntimeException("Erro ao processar linha: " + line, e);
        }
    }
}
