package ru.itis.activerewards.forms;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;


@Data
public class RewardForm {
    private String title;
    private String description;
    private Double price;
    private MultipartFile image;
}
