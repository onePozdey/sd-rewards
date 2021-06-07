package ru.itis.activerewards.services;

import ru.itis.activerewards.dto.SpecCountDto;
import ru.itis.activerewards.dto.SpecDto;
import ru.itis.activerewards.forms.SpecForm;

import java.util.List;

public interface UserSpecsService {
    List<SpecDto> getAllSpecs();

    List<SpecCountDto> getUserSpecs(Long id);

    void addSpec(SpecForm form);
}
