package com.example.emailsender.app.controller;

import com.example.emailsender.app.dtos.CreateCharacterDto;
import com.example.emailsender.app.dtos.UpdateAttributesDto;
import com.example.emailsender.app.repository.tables.CharacterJPA;
import com.example.emailsender.app.service.CharacterService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/character")
public class CharacterController {

    @Autowired
    private CharacterService characterService;

    @PostMapping("/create")
    public ResponseEntity<?> createCharacter(@RequestBody CreateCharacterDto characterDto) {
            return new ResponseEntity<>(characterService.createCharacter(characterDto), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCharacter(@PathVariable Long id) {
        characterService.deleteCharacter(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/all")
    public ResponseEntity<List<CharacterJPA>> getAllCharacters() {
        return new ResponseEntity<>(characterService.getAllCharacters(), HttpStatus.OK);
    }

    @PutMapping("/attributes/{id}")
    public ResponseEntity<?> updateAttributes(@PathVariable Long id, @RequestBody UpdateAttributesDto updateAttributesDto) {
        try {
            return new ResponseEntity<>(characterService.updateAttributes(id, updateAttributesDto), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
