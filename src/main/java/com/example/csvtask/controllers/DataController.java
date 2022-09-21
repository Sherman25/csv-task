package com.example.csvtask.controllers;

import com.example.csvtask.domain.Card;
import com.example.csvtask.services.CSVFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author Otabek Sherman
 * @since 2022/09/20
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/test")
public class DataController {

    private final CSVFileService csvFileService;

    @PostMapping("upload")
    public void uploadCSV(@RequestParam(value = "file") MultipartFile file) {
        csvFileService.validateFile(file);

        List<Card> cards = csvFileService.readFile(file);

        csvFileService.storeCardsDataToDB(cards);
    }

    @GetMapping("sort/alphabetically")
    public List<Card> getCardsSortedByAbc() {
        return csvFileService.getCardsFromDbSortedAlphabetically();
    }

    @GetMapping("sort/numerically")
    public List<Card> getCardsSortedBy123() {
        return csvFileService.getCardsFromDbSortedNumerically();
    }
}
