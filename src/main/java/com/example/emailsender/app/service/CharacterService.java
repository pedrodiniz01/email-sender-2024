package com.example.emailsender.app.service;

import com.example.emailsender.app.dtos.CreateCharacterDto;
import com.example.emailsender.app.dtos.UpdateAttributesDto;
import com.example.emailsender.app.mapper.Mapper;
import com.example.emailsender.app.repository.CharacterJpaRepository;
import com.example.emailsender.app.repository.tables.AttributesJPA;
import com.example.emailsender.app.repository.tables.CharacterJPA;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CharacterService {

    private final CharacterJpaRepository characterJpaRepository;

    private Mapper mapper = Mappers.getMapper(Mapper.class);

    public CreateCharacterDto createCharacter(CreateCharacterDto characterDto) {

        characterJpaRepository.save(mapper.toCharacterJPA(characterDto));

        return characterDto;
    }

    public void deleteCharacter(Long id) {
        if (characterJpaRepository.existsById(id)) {
            characterJpaRepository.deleteById(id);
            log.info("Character with id {} successfully deleted", id);
        } else {
            throw new IllegalArgumentException("Character with id " + id + " not found");
        }
    }

    public List<CharacterJPA> getAllCharacters() {
        log.info("Retrieving all characters.");
        return characterJpaRepository.findAll();
    }

    public CharacterJPA updateAttributes(Long id, UpdateAttributesDto updateAttributesDto) {
        CharacterJPA characterJPA = null;
        if (characterJpaRepository.existsById(id)) {
            characterJPA = characterJpaRepository.findById(id).get();

            AttributesJPA attributesJPA = characterJPA.getAttributes();

            if (updateAttributesDto.getSparringMinutes() != null) {
                attributesJPA.setSparringMinutes(attributesJPA.getSparringMinutes() + updateAttributesDto.getSparringMinutes());
            }

            if (updateAttributesDto.getPagesRead() != null) {
                attributesJPA.setPagesRead(attributesJPA.getPagesRead() + updateAttributesDto.getPagesRead());
            }

            if (updateAttributesDto.getLastMeditation() != null) {
                LocalDate lastLastMeditation = attributesJPA.getLastMeditation();

                attributesJPA.setLastMeditation(updateAttributesDto.getLastMeditation());

                LocalDate lastMeditation = updateAttributesDto.getLastMeditation();

                if (ChronoUnit.DAYS.between(lastLastMeditation, lastMeditation) <= 1) {
                    attributesJPA.setMeditationStreak(updateAttributesDto.getMeditationStreak() + 1);
                }
            }

            if (updateAttributesDto.getMeditationStreak() != null) {
                attributesJPA.setMeditationStreak(updateAttributesDto.getMeditationStreak());
            }

            if (updateAttributesDto.getMinutesMeditating() != null) {
                attributesJPA.setMinutesMeditating(attributesJPA.getMinutesMeditating() + updateAttributesDto.getMinutesMeditating());
            }

            if (updateAttributesDto.getKmsRun() != null) {
                attributesJPA.setKmsRun(attributesJPA.getKmsRun() + updateAttributesDto.getKmsRun());
            }

            if (updateAttributesDto.getMinutesRun() != null) {
                attributesJPA.setMinutesRun(attributesJPA.getMinutesRun() + updateAttributesDto.getMinutesRun());
            }

            characterJpaRepository.save(characterJPA);

            log.info("Character with id {} successfully deleted", id);
        }
        return characterJPA;
    }

    public CharacterJPA getMainCharacter() {
        List<CharacterJPA>  list = characterJpaRepository.findAll();

        return list.get(0);
    }
}
