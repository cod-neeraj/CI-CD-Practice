package com.example.demo.Model;

import jakarta.validation.constraints.NotBlank;

public record CreateTaskRequest(
        @NotBlank(message = "Title must not be blank")
        String title
) {
}
