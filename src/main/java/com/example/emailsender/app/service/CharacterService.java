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
import java.time.Period;
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

    public String buildAttributesMessage() {

        CharacterJPA character = getMainCharacter();
        AttributesJPA attributes = character.getAttributes();
        double age = calculateAge(character.getBirthDate(), LocalDate.now());

        float sparringHours = attributes.getSparringMinutes() / 60f;
        float sparringDays = attributes.getSparringMinutes() / (60f * 24f);

        float minutesReading = attributes.getPagesRead() > 0 ? attributes.getPagesRead() * 120 : 0;
        float readingHours = minutesReading / 60f;
        float readingDays = minutesReading / (60f * 24f);

        float meditationHours = attributes.getMinutesMeditating() / 60f;
        float meditationDays = attributes.getMinutesMeditating() / (60f * 24f);

        float runningHours = attributes.getMinutesRun() / 60f;
        float runningDays = attributes.getMinutesRun() / (60f * 24f);

        String message = String.format(
                "Hello %s, you are %.2f years old.\n\n" +
                        "Sparring Minutes: %.1f (%.2f hours, %.2f days)\n\n" +
                        "Pages Read: %d\n\n" +
                        "Averages Minutes Reading: %.1f (%.2f hours, %.2f days)\n\n" +
                        "Meditation Streak: %d days\n\n" +
                        "Minutes Meditating: %d (%.2f hours, %.2f days)\n\n" +
                        "KMs Run: %.2f\n\n" +
                        "Minutes Running: %d (%.2f hours, %.2f days)",
                character.getName(),
                age,
                attributes.getSparringMinutes(), sparringHours, sparringDays,
                attributes.getPagesRead(),
                minutesReading, readingHours, readingDays,
                attributes.getMeditationStreak(),
                attributes.getMinutesMeditating(), meditationHours, meditationDays,
                attributes.getKmsRun(),
                attributes.getMinutesRun(), runningHours, runningDays
        );

        return message;
    }
    private double calculateAge(LocalDate birthdate, LocalDate currentDate) {
        if (birthdate != null) {
            Period period = Period.between(birthdate, currentDate);
            int years = period.getYears();
            int months = period.getMonths();

            double fractionalYears = months / 12.0;
            return years + fractionalYears;
        } else {
            return 0;
        }
    }

}
