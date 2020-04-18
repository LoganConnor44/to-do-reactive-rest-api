package com.loganconnor44.todoreactiverestapi.models;

import com.loganconnor44.todoreactiverestapi.enums.Difficulty;
import com.loganconnor44.todoreactiverestapi.enums.Importance;
import com.loganconnor44.todoreactiverestapi.enums.Status;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "tasks")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Task {
    @Id
    private String id;
    private String name;
    private String owner;
    private Status status;
    private Long deadline;
    private Difficulty difficulty;
    private Importance importance;
    private Long lastModified;
    private Long created;
}
