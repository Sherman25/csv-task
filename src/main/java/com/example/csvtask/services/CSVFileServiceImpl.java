package com.example.csvtask.services;

import com.example.csvtask.domain.Card;
import com.example.csvtask.repositories.CardRepository;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * CSV file service manages csv files in the system. Allows upload and fetch.
 *
 * @author Otabek Sherman
 * @since 2022/09/20
 */
@Service
@RequiredArgsConstructor
public class CSVFileServiceImpl implements CSVFileService {

    private final CardRepository cardRepository;

    @Override
    public List<Card> readFile(MultipartFile file) {
        List<Card> cards = new ArrayList<>();
        try (InputStreamReader isr = new InputStreamReader(file.getInputStream(), "UTF-8");
             BufferedReader reader = new BufferedReader(isr)) {

            CsvToBean<Card> cb = new CsvToBeanBuilder<Card>(reader)
                    .withType(Card.class)
                    .build();
            cards.addAll(cb.parse());
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return cards;
    }

    public void storeCardsDataToDB(List<Card> cards) {
        cardRepository.saveAllAndFlush(cards);
    }

    @Override
    public void validateFile(MultipartFile file)  {
        String fileType = file.getContentType();
        if (!"text/csv".equals(fileType)) {
            throw new IllegalArgumentException(String.format(
                    "%s is not a legal type. File must be of type CSV", fileType));
        }
    }

    @Override
    public List<Card> getCardsFromDbSortedAlphabetically() {
        List<Card> cardsFromDB = getCardsFromDB();
        return sortCardsAlphabetically(cardsFromDB);
    }

    @Override
    public List<Card> getCardsFromDbSortedNumerically() {
        List<Card> cardsFromDB = getCardsFromDB();
        return sortCardsNumerically(cardsFromDB);
    }

    private List<Card> getCardsFromDB() {
        return cardRepository.findAll();
    }

    private List<Card> sortCardsAlphabetically(List<Card> cards) {
        return cards.stream()
                .sorted(Comparator.comparing(Card::getHolderName))
                .collect(Collectors.toList());
    }

    private List<Card> sortCardsNumerically(List<Card> cards) {
        return cards.stream()
                .sorted(Comparator.comparing(Card::getCardNumber))
                .collect(Collectors.toList());
    }

    public List<String> sortNames(List<String> names) {
        return sortColumnData(names);
    }

    public List<Integer> sortCardNumbers(List<Integer> numbers) {
        return sortColumnData(numbers);
    }

    private <T> List<T> sortColumnData(List<T> data) {
        return data.stream()
                .sorted()
                .collect(Collectors.toList());
    }
}
